package com.iteration2.mark.iteration3loginslides;

        import android.app.Activity;
        import android.app.SearchManager;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Handler;
        import android.os.Message;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

/*
Claritas: 'Clarity Through Innovation.'
///////////////////////////////////////////////////////////////////////////////////////////////////

Project: SocBox
Module: Search Function
Code File Name: SearchResults.java (created by SearchPage activity)

///////////////////////////////////////////////////////////////////////////////////////////////////

Description: This class is the search results page activity which displays the results from
QueryXML based on the user query entered in the SearchView on the SearchPage. The page shows as
many results as are returned by the search, or "no results found" if no results were returned.
The ListView becomes scrollable if more than a page worth of results are returned. Selecting any
of the results will create an intent to start Society Home Page activity of the relevant society.

///////////////////////////////////////////////////////////////////////////////////////////////////

Initial Authors: Richard Millen
				 Leo Holt

///////////////////////////////////////////////////////////////////////////////////////////////////

Change History:
Version: 0.1
Author: Richard Millen
Change: Created original version
Date: 11/03/15

///////////////////////////////////////////////////////////////////////////////////////////////////

Traceability:

[D/SFI/06/01] When the Search ‘GO’ button is pressed the program will scan the
name/tag text file for any successfully searched word and any society with the
searched name or a tagged keyword will be returned for display. (Applies to QueryXML class)

[D/SRF/01/01] The Search results page is comprised of one main back panel containing two
components. In vertical order from top to bottom these components
are:
-[D/SRF/01-A/01] ‘SEARCH RESULTS’ in large text.
-[D/SRF/01-B/01] The Results Scroll Panel

[D/SRF/02/01] The Results Scroll Panel is stationary with respect to the back panel but
can be scrolled through independently by swiping the finger in the conventional
method.

[D/SRF/03/01] The Results Scroll Panel is comprised of a column of vertically aligned
cells. The number of cells corresponds to how many results were obtained from the
search [SF].

[D/SRI/01/01] The contents of the search box in the Navigator page [xx] that the user
entered in a search is saved and remains inside that box while the Search Results Page is
open and when the Navigator page is returned to (until something else is entered by the
user).

[D/SRI/03/01] Only the number of cells required are generated in the Results Scroll
Panel. Tapping a position on the panel that does not contain a cell (e.g. below the search
results) should have no effect.

[D/SRI/04/01] If there are not enough results to require scrolling because they all fit on
the single Search Results Panel then scrolling is locked.



///////////////////////////////////////////////////////////////////////////////////////////////////

Other Information:
Searchable configuration requires other documents such as AndroidManifest and searchable.xml to
be set up correctly.

Required Improvements:
- Results should be sorted by relevance as specified in the design spec [D/SFI/07/01]
- Results should also display which words of the query were matched to it and how many were matched.

///////////////////////////////////////////////////////////////////////////////////////////////////
*/

public class SearchResults extends Activity {

    //Handler must be static so that it can be referenced from QueryXML for message sending
    public static Handler mHandler;

    public String[] resultsArray;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //This Object handles communication between the main UI thread and the background thread
        //which runs QueryXML. The background thread is required in order to prevent the app from
        //becoming unresponsive by processing too much in the Main thread

        //The looper (which carries messages) used by this Handler is associated with whichever
        //Thread instantiates the Handler. Therefore the received messages can only be
        //accessed and handled by this Thread (main UI thread).

        mHandler = new Handler() {

            //This method instructs the Handler on what to do with messages when it receives them,
            //which in this case it will from the QueryXML thread once it has finished.
            public void handleMessage(Message msg) {

                //The only message sent from the QueryXML thread is the completed results array
                resultsArray = (String[]) msg.obj;

                //The global NO_RESULTS_FLAG is tested to see if results string was empty and if
                //it if it was, the "no results found" text view is displayed", otherwise the
                //list view of results is displayed.
                Log.i("STRING ARRAY SIZE", Integer.toString(resultsArray.length));
                if(QueryXML.NO_RESULTS_FLAG) {
                    findViewById(R.id.noresultstext).setVisibility(View.VISIBLE);
                    findViewById(R.id.mainlist).setVisibility(View.GONE);
                }
                else if(!QueryXML.NO_RESULTS_FLAG) {
                    findViewById(R.id.noresultstext).setVisibility(View.GONE);
                    findViewById(R.id.mainlist).setVisibility(View.VISIBLE);
                }

                //The string array of results is inserted into the ListView in this activity
                //via an ArrayAdapter which inserts each entry into a simple_list_item_1 view.
                ListView mainlist = (ListView)(findViewById(R.id.mainlist));
                mainlist.setAdapter(new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, resultsArray));

            }
        };

        //The actual search query from the SearchPage is combined with the intent as an
        //Extra. This method handles that intent by finding this query and passing it
        //to QueryXML to output a results array which is handled as described above.

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {

        //Determines whether the page was started with an ACTION_SEARCH intent and obtains
        //the search query if it was, passing it to QueryXML.
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //All that is required to run the xml search is to create a single instance of QueryXML
            new QueryXML(this, query); //runs on a new thread.
        }
    }

    //===========================================================================================%
    //Everything beyond this point is generated by the activity upon creation

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}