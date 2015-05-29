package com.iteration2.mark.iteration3loginslides;

        import android.content.Context;
        import android.content.res.Resources;
        import android.content.res.XmlResourceParser;
        import android.os.Message;
        import android.util.Log;

        import org.xmlpull.v1.XmlPullParser;
        import org.xmlpull.v1.XmlPullParserException;

        import java.io.IOException;

/*
Claritas: ‘Clarity through innovation’
///////////////////////////////////////////////////////////////////////////////////////////////////

Project: SocBox
Module: Search Functionality

Code File Name: QueryXML.java

Primary Module Code File Name: SearchResults.java

///////////////////////////////////////////////////////////////////////////////////////////////////

Description: Carries out the parsing to the XML database of societies. This Class is instantiated
in SearchResults and passed the query, or string of multiple queries to be searched. In this class
the user entered query string is prepared for database searching and custom written expressions
ensure that the runQuery method returns all the required and valid results from the search. This
Class runs in it's own background thread to avoid excessive processing on the UI thread and it
communicates with the main UI thread via sending messages to the Handler. The information sent
back to the SearchResults activity is single string array containing results.

///////////////////////////////////////////////////////////////////////////////////////////////////

Initial Authors: Richard Millen
                 Leo Holt

///////////////////////////////////////////////////////////////////////////////////////////////////

Change History:
Version: 0.1
Author: Richard Millen
Change: Created original version
Date: 11/03/2015

///////////////////////////////////////////////////////////////////////////////////////////////////

Additional Information:


///////////////////////////////////////////////////////////////////////////////////////////////////
*/


public class QueryXML extends Thread {

    //Global variable to flag for no results returned
    public static boolean NO_RESULTS_FLAG = false;

    //Class variables set in constructor.
    Resources res;
    String query;
    String[] queryArray;

    //===========================================================================================%

    //Creating QueryXML object with this constructor is the only interaction required with this
    //class from the SearchResults class.
    public QueryXML(Context context, String query) {

        //Resources grabbed form SearchResults context and query set to constructor argument.
        res = context.getResources();
        this.query = query;

        //Starts thread running.
        run();
    }
    //===========================================================================================%

    //All the code that executes in the thread resides in this method, thread is Garbage Collected
    //upon completion
    @Override
    public void run() {

        //Set thread to run in background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        //The next line prepares the user entered String for query with the following process:
        //Put all characters to lower case (as they are compared with lowercase database entries)
        //Remove the word society so that searching ______ Society won't return all entries
        //Do same as above for the word soc
        //Replace any number of spaces with a single space so that entering multiple spaces does
        //not cause QueryXml to search for a space and return every entry.
        //Trim all white space from either side of the string.

        String prepQuery = query.toLowerCase().replace("society"," ").replace("soc"," ").
                replaceAll(" +", " ").trim();

        Log.i("PREPPED QUERY", prepQuery);

        //Here the prepared query string is separated into it's constituent words
        //and each word is stored in a string array element to be queried individually (allowing
        //the user to input more than a single word)

        queryArray = prepQuery.split(" ");
        Log.i("WORDS TO QUERY: ", Boolean.toString(prepQuery.isEmpty()));

        //Empty the results string
        String results = "";

        //Only run the query if the prepared query had content
        if(!prepQuery.isEmpty()) {

            //Attempts to query the xml with query passed into constructor.
            try {
                results = runQuery();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }

        //Global variable for no results
        if(results.isEmpty() | prepQuery.isEmpty()){
            Log.i("RESULTS IS EMPTY", "no results found");
            NO_RESULTS_FLAG = true;
        }
        else {NO_RESULTS_FLAG = false;}

        //Results from query are stored in a single string separated by by "\n"s.
        //String is then split into a string array at every "\n" character
        String[] resultsArray = results.split("\n");


        //The resulting String array is sent in a message to the Handler on the main thread
        Message msg = Message.obtain();
        msg.obj = resultsArray;
        SearchResults.mHandler.sendMessage(msg);
    }

    //===========================================================================================%

    //Method searches xml for query and returns String of results as dealt with above.
    public String runQuery() throws IOException, XmlPullParserException {

        //StringBuilder set up to create single results string.
        StringBuilder resultStr = new StringBuilder();
        //xml database obtained from xml resource folder and loaded into parser
        XmlResourceParser xmlFile = res.getXml(R.xml.database);

        //Causes parser to parse the first very first event in the xml, and the type of this
        //event is recorded
        xmlFile.next();
        int eventType = xmlFile.getEventType();

        //Method variables (trackers) store information of current stage of parsing.
        String currentElement;
        String currentSociety = null;
        //notes whether the society has already been included in the results to avoid duplicates.
        boolean currentSocietyAdded = false;

        //I know there are a lot of loops here but it isn't too complicated really


        //Loop while there are still elements to parse
        while (eventType != XmlPullParser.END_DOCUMENT) {
            //Every time a start tag is reached...
            if (eventType == XmlPullParser.START_TAG) {
                //Set the currentElement tracker to the element that the start tag belongs to
                currentElement = xmlFile.getName();

                //It is first checked whether the query is contained in the society name itself...

                //If the start tag belongs to a society...
                if (currentElement.equals("society")) {


                    //Set the currentSociety tracker to the name of the society
                    currentSociety = (xmlFile.getAttributeValue(null, "name"));
                    //Record that the current society has not yet been added to the search results
                    currentSocietyAdded = false;

                    //Each separate word of the query is tested individually to see if it is
                    //contained within the society name
                    for (int i = 0; i < queryArray.length; i++) {
                        //If the currentSociety has any of the query strings in it anywhere
                        // nd is not already added...
                        if (currentSociety.toLowerCase().contains(queryArray[i]) &&
                                !currentSocietyAdded) {
                            //Record that the current society is now added to the results.
                            currentSocietyAdded = true;
                            //And finally append it to the the results String with
                            // a "\n" separator
                            resultStr.append(currentSociety + "\n");
                        }
                    }

                }

                //All keyword elements of the current society element are then checked for
                //the query

                //If the start tag belongs to a keyword element...
                //(currently the only other possibility)
                if (currentElement.equals("keyword")) {

                    //While still inside this element, go to the next event.
                    while (eventType != XmlPullParser.END_TAG) {
                        eventType = xmlFile.next();

                        //If a text event is reached... (it always should, this is the keyword)
                        if (eventType == XmlPullParser.TEXT) {

                            //If the the keyword contains any of the individual query strings
                            // and the current society element has not already been added
                            // to the results...
                            for (int i = 0; i < queryArray.length; i++) {
                                if (xmlFile.getText().toLowerCase().contains(queryArray[i]) &&
                                        !currentSocietyAdded) {
                                    //Add the society to the results string
                                    resultStr.append(currentSociety + "\n");
                                    //Note the society as added
                                    currentSocietyAdded = true;
                                }
                            }
                        }
                    }
                }
            }
            //Finally set the parser to the next event at the end of every loop
            eventType = xmlFile.next();
        }
        //Results string is returned from method.
        return resultStr.toString();
    }
    //===========================================================================================%
}
