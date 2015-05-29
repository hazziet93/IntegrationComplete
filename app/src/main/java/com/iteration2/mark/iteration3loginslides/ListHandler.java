package com.iteration2.mark.iteration3loginslides;

/**
 * Created by Andy on 30/03/2015.
 *
 */
/*
Claritas: 'Clarity Through Innovation.'
///////////////////////////////////////////////////////////////////////////////////////////////////

Project: SocBox
Module: Browse Page
Code File Name: ListHandler.java
Primary Module Code File Name: LinstHandlerActivity.java

///////////////////////////////////////////////////////////////////////////////////////////////////

Description: This subclass contains methods for the UI for the filter function and the societies
list.

///////////////////////////////////////////////////////////////////////////////////////////////////

Initial Authors: Andrew Perry
				 Carlos Archila

///////////////////////////////////////////////////////////////////////////////////////////////////

Change History:
Version: 0.1
Author: Andrew Perry
Change: Created original version
Date: 11/03/15

///////////////////////////////////////////////////////////////////////////////////////////////////

Other Information:
- Tag functionality implemented but not used yet
- Clicking on a society will display a popup message with array information for that society for now
-Todo: Clicking on a society will take the user to that society's main page

///////////////////////////////////////////////////////////////////////////////////////////////////
*/

//Package

        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.content.Intent;

        import java.util.ArrayList;
        import java.util.Collections;




//This class allows the user to see an a-z list of societies and allows to filter them based on a category
public class ListHandler {

    //public Activity calledFrom;
    private  View calledFrom;

    public  ListView mainListView;
    public  ArrayAdapter<String> listAdapter;
    private  View filterLayout;
    private TextView societiesTitle;
    private  Button filterButton;


    //None of the categories are selected so the list is fully populated
    private boolean noneSelected = true;

    //This method finds the views in the XML file and implements a click listener for the societies list
    public ListHandler(View callingView, ListView list){

        this.calledFrom = callingView;

        //Find the TextView resource.
//        societiesTitle = (TextView) this.calledFrom.findViewById(R.id.Societies);

        //Find the ListView resource.
        // mainListView = (ListView) this.calledFrom.findViewById(R.id.browseListView);
        mainListView = list;
        //Find the LinearLayoutView resource.
        filterLayout = this.calledFrom.findViewById(R.id.filterLayout);

        //Find the Button resource
        filterButton = (Button) this.calledFrom.findViewById(R.id.filterButton);

        //Find the TextView layout for the list entries
        listAdapter = new ArrayAdapter<>(this.calledFrom.getContext(),R.layout.list_row);


        //Generate a popup message or "toast" when a society is clicked on from the societies list
//        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
//                TextView clickedView = (TextView) view;
//
//                Toast.makeText(calledFrom.getContext(), "Item with id [" + id + "] - position [" + position + "] - Text [" + clickedView.getText() + "]", Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    public ArrayList updateCategories(View view, ArrayList<String> categories)
    {
        //If no categories have been selected previously then the all categories will be displayed currently.
        // Therefore it is necessary to clear the category list upon the selection of a category.
        // It is also necessary to ensure that the "noneSelected" boolean is set to false as a category has now been selected.
        if(noneSelected){
            categories.clear();
            noneSelected = false;
        }


        // Stores whether the clicked checkBox was selected or deselected
        boolean checked = ((CheckBox) view).isChecked();


        //Filter according to which CheckBox was selected,
        //If the Id matches a checkbox then the filter on that category is toggled.
        //If the user has selected a previously unselected category then the "checked" variable will be true
        // and the category added to the list of categories.
        // If the user has deselected a previously selected category then the "checked" variable will be false
        // and the category removed from the list
        switch(view.getId()) {
            case R.id.checkBox1:
                if (checked)
                    categories.add(calledFrom.getContext().getString(R.string.category_1));
                else
                    categories.remove(calledFrom.getContext().getString(R.string.category_1));
                break;
            case R.id.checkBox2:
                if (checked)
                    categories.add(calledFrom.getContext().getString(R.string.category_2));
                else
                    categories.remove(calledFrom.getContext().getString(R.string.category_2));

                break;
            case R.id.checkBox3:
                if (checked)
                    categories.add(calledFrom.getContext().getString(R.string.category_3));
                else
                    categories.remove(calledFrom.getContext().getString(R.string.category_3));

                break;
            case R.id.checkBox4:
                if (checked)
                    categories.add(calledFrom.getContext().getString(R.string.category_4));
                else
                    categories.remove(calledFrom.getContext().getString(R.string.category_4));

                break;
            case R.id.checkBox5:
                if (checked)
                    categories.add(calledFrom.getContext().getString(R.string.category_5));
                else
                    categories.remove(calledFrom.getContext().getString(R.string.category_5));

                break;
            case R.id.checkBox6:
                if (checked)
                    categories.add(calledFrom.getContext().getString(R.string.category_6));
                else
                    categories.remove(calledFrom.getContext().getString(R.string.category_6));

                break;
            case R.id.checkBox7:
                if (checked)
                    categories.add(calledFrom.getContext().getString(R.string.category_7));
                else
                    categories.remove(calledFrom.getContext().getString(R.string.category_7));

                break;
            case R.id.checkBox8:
                if (checked)
                    categories.add(calledFrom.getContext().getString(R.string.category_8));
                else
                    categories.remove(calledFrom.getContext().getString(R.string.category_8));

                break;

        }


        //if the user has deselected all categories then all categories must be displayed
        // which is why allCategories() is called to populate the category list.
        //However it must be clear that no categories have been selected which is why the noneSelected boolean is true
        if(categories.isEmpty()){
            categories = allCategories(categories);
            noneSelected = true;
        }

        return categories;
    }

    //Add categories to the Categories ArrayList
    public ArrayList<String> allCategories(ArrayList<String> categories){
        categories.add(calledFrom.getContext().getString(R.string.category_1));
        categories.add(calledFrom.getContext().getString(R.string.category_2));
        categories.add(calledFrom.getContext().getString(R.string.category_3));
        categories.add(calledFrom.getContext().getString(R.string.category_4));
        categories.add(calledFrom.getContext().getString(R.string.category_5));
        categories.add(calledFrom.getContext().getString(R.string.category_6));
        categories.add(calledFrom.getContext().getString(R.string.category_7));
        categories.add(calledFrom.getContext().getString(R.string.category_8));

        return categories;
    }

    //This method can add tags and categories to each society
    public String[][][] addSocieties(){

        String[][][] SocietyNames = new String[400][2][10];

        //Add "tag" as a tag for each society
        for(int a = 0; a < 400; a++) {
            SocietyNames[a][0][1] = "tag";
        }

        //Populate the SocietyNames array with society names and categories
        SocietyNames[0][0][0]="African and Caribbean Society (ACS)";
        SocietyNames[0][1][0]="Faith and Culture";
        SocietyNames[1][0][0]="Anglican Students' Society (AngSoc)";
        SocietyNames[1][1][0]="Faith and Culture";
        SocietyNames[2][0][0]="Arab Society";
        SocietyNames[2][1][0]="Faith and Culture";
        SocietyNames[3][0][0]="Brunei Society (Bru-York)";
        SocietyNames[3][1][0]="Faith and Culture";
        SocietyNames[4][0][0]="Catholic Students' Society (CaSSoc)";
        SocietyNames[4][1][0]="Faith and Culture";
        SocietyNames[5][0][0]="Chinese Society";
        SocietyNames[5][1][0]="Faith and Culture";
        SocietyNames[6][0][0]="Christian Union (CU)";
        SocietyNames[6][1][0]="Faith and Culture";
        SocietyNames[7][0][0]="Eastern Philosophy Society";
        SocietyNames[7][1][0]="Faith and Culture";
        SocietyNames[8][0][0]="Estonian Society";
        SocietyNames[8][1][0]="Faith and Culture";
        SocietyNames[9][0][0]="German Society (DeutschSoc)";
        SocietyNames[9][1][0]="Faith and Culture";
        SocietyNames[10][0][0]="Hellenic Society (YUHS)";
        SocietyNames[10][1][0]="Faith and Culture";
        SocietyNames[11][0][0]="Hong Kong Society";
        SocietyNames[11][1][0]="Faith and Culture";
        SocietyNames[12][0][0]="Hungarian Society";
        SocietyNames[12][1][0]="Faith and Culture";
        SocietyNames[13][0][0]="Indonesian Society (IndoSoc)";
        SocietyNames[13][1][0]="Faith and Culture";
        SocietyNames[14][0][0]="Irish Society";
        SocietyNames[14][1][0]="Faith and Culture";
        SocietyNames[15][0][0]="Islamic Society (YorkISOC)";
        SocietyNames[15][1][0]="Faith and Culture";
        SocietyNames[16][0][0]="Italian Society";
        SocietyNames[16][1][0]="Faith and Culture";
        SocietyNames[17][0][0]="Japanese Society";
        SocietyNames[17][1][0]="Faith and Culture";
        SocietyNames[18][0][0]="Jewish Society";
        SocietyNames[18][1][0]="Faith and Culture";
        SocietyNames[19][0][0]="Kazakhstan Society (Nomads)";
        SocietyNames[19][1][0]="Faith and Culture";
        SocietyNames[20][0][0]="Korean Cultural Society";
        SocietyNames[20][1][0]="Faith and Culture";
        SocietyNames[21][0][0]="Latin American Society";
        SocietyNames[21][1][0]="Faith and Culture";
        SocietyNames[22][0][0]="Lithuanian Society";
        SocietyNames[22][1][0]="Faith and Culture";
        SocietyNames[23][0][0]="Lusophone Society (LusoSoc)";
        SocietyNames[23][1][0]="Faith and Culture";
        SocietyNames[24][0][0]="Malaysian Society";
        SocietyNames[24][1][0]="Faith and Culture";
        SocietyNames[25][0][0]="Nigerian Society";
        SocietyNames[25][1][0]="Faith and Culture";
        SocietyNames[26][0][0]="Polish Society";
        SocietyNames[26][1][0]="Faith and Culture";
        SocietyNames[27][0][0]="Quakers on Campus";
        SocietyNames[27][1][0]="Faith and Culture";
        SocietyNames[28][0][0]="Romanian Society";
        SocietyNames[28][1][0]="Faith and Culture";
        SocietyNames[29][0][0]="Russian Speaking Society";
        SocietyNames[29][1][0]="Faith and Culture";
        SocietyNames[30][0][0]="Saudi Students Society";
        SocietyNames[30][1][0]="Faith and Culture";
        SocietyNames[31][0][0]="Scandinavian Society";
        SocietyNames[31][1][0]="Faith and Culture";
        SocietyNames[32][0][0]="Singapore Society";
        SocietyNames[32][1][0]="Faith and Culture";
        SocietyNames[33][0][0]="South Asian Society";
        SocietyNames[33][1][0]="Faith and Culture";
        SocietyNames[34][0][0]="Spanish Society";
        SocietyNames[34][1][0]="Faith and Culture";
        SocietyNames[35][0][0]="Spiritual Development Society (SDS)";
        SocietyNames[35][1][0]="Faith and Culture";
        SocietyNames[36][0][0]="Thai Society";
        SocietyNames[36][1][0]="Faith and Culture";
        SocietyNames[37][0][0]="Turkish Society";
        SocietyNames[37][1][0]="Faith and Culture";
        SocietyNames[38][0][0]="Vietnamese Society";
        SocietyNames[38][1][0]="Faith and Culture";
        SocietyNames[39][0][0]="WikiStage";
        SocietyNames[39][1][0]="Faith and Culture";
        SocietyNames[40][0][0]="Bloggers Society (BlogSoc)";
        SocietyNames[40][1][0]="Faith and Culture";
        SocietyNames[41][0][0]="Circulation Magazine";
        SocietyNames[41][1][0]="Music and Media";
        SocietyNames[42][0][0]="HARDzine";
        SocietyNames[42][1][0]="Music and Media";
        SocietyNames[43][0][0]="Lemon Press";
        SocietyNames[43][1][0]="Music and Media";
        SocietyNames[44][0][0]="Looking Glass Anthology";
        SocietyNames[44][1][0]="Music and Media";
        SocietyNames[45][0][0]="Nouse";
        SocietyNames[45][1][0]="Music and Media";
        SocietyNames[46][0][0]="Photography Society (PhotoSoc)";
        SocietyNames[46][1][0]="Music and Media";
        SocietyNames[47][0][0]="University Radio York (URY)";
        SocietyNames[47][1][0]="Music and Media";
        SocietyNames[48][0][0]="York Student Cinema (YSC)";
        SocietyNames[48][1][0]="Music and Media";
        SocietyNames[49][0][0]="York Student Television (YSTV)";
        SocietyNames[49][1][0]="Music and Media";
        SocietyNames[50][0][0]="York Vision";
        SocietyNames[50][1][0]="Music and Media";
        SocietyNames[51][0][0]="Zahir";
        SocietyNames[51][1][0]="Music and Media";
        SocietyNames[52][0][0]="Band Society";
        SocietyNames[52][1][0]="Music and Media";
        SocietyNames[53][0][0]="Bellringers";
        SocietyNames[53][1][0]="Music and Media";
        SocietyNames[54][0][0]="Big Band";
        SocietyNames[54][1][0]="Music and Media";
        SocietyNames[55][0][0]="Brass Band";
        SocietyNames[55][1][0]="Music and Media";
        SocietyNames[56][0][0]="Breakz";
        SocietyNames[56][1][0]="Music and Media";
        SocietyNames[57][0][0]="Concert Band";
        SocietyNames[57][1][0]="Music and Media";
        SocietyNames[58][0][0]="Concert Orchestra";
        SocietyNames[58][1][0]="Music and Media";
        SocietyNames[59][0][0]="Glee Singers";
        SocietyNames[59][1][0]="Music and Media";
        SocietyNames[60][0][0]="Music Production Society";
        SocietyNames[60][1][0]="Music and Media";
        SocietyNames[61][0][0]="Music Society";
        SocietyNames[61][1][0]="Music and Media";
        SocietyNames[62][0][0]="Opera Society";
        SocietyNames[62][1][0]="Music and Media";
        SocietyNames[63][0][0]="Revelation Rock-Gospel Choir";
        SocietyNames[63][1][0]="Music and Media";
        SocietyNames[64][0][0]="Rock and Alternative Music Society (The Fringe)";
        SocietyNames[64][1][0]="Music and Media";
        SocietyNames[65][0][0]="Samba Society";
        SocietyNames[65][1][0]="Music and Media";
        SocietyNames[66][0][0]="Amnesty International (UYAI)";
        SocietyNames[66][1][0]="Music and Media";
        SocietyNames[67][0][0]="Conservative and Unionist Association (York Tories)";
        SocietyNames[67][1][0]="Politics and Debating";
        SocietyNames[68][0][0]="Debating Society";
        SocietyNames[68][1][0]="Politics and Debating";
        SocietyNames[69][0][0]="English PEN Society (YorkPEN)";
        SocietyNames[69][1][0]="Politics and Debating";
        SocietyNames[70][0][0]="Green Party";
        SocietyNames[70][1][0]="Politics and Debating";
        SocietyNames[71][0][0]="International Development Society";
        SocietyNames[71][1][0]="Politics and Debating";
        SocietyNames[72][0][0]="Labour Society";
        SocietyNames[72][1][0]="Politics and Debating";
        SocietyNames[73][0][0]="Liberal Democrats";
        SocietyNames[73][1][0]="Politics and Debating";
        SocietyNames[74][0][0]="Oxfam Society";
        SocietyNames[74][1][0]="Politics and Debating";
        SocietyNames[75][0][0]="Palestinian Solidarity Society";
        SocietyNames[75][1][0]="Politics and Debating";
        SocietyNames[76][0][0]="People and Planet Society";
        SocietyNames[76][1][0]="Politics and Debating";
        SocietyNames[77][0][0]="Socialist Society (UoY Student Socialist Society)";
        SocietyNames[77][1][0]="Politics and Debating";
        SocietyNames[78][0][0]="Stop AIDS (York Student Stop AIDS)";
        SocietyNames[78][1][0]="Politics and Debating";
        SocietyNames[79][0][0]="Student Minds";
        SocietyNames[79][1][0]="Politics and Debating";
        SocietyNames[80][0][0]="Think Tank (York Student Think Tank)";
        SocietyNames[80][1][0]="Politics and Debating";
        SocietyNames[81][0][0]="UKIP Association (York UKIP)";
        SocietyNames[81][1][0]="Politics and Debating";
        SocietyNames[82][0][0]="United Nations Association (UNA)";
        SocietyNames[82][1][0]="Politics and Debating";
        SocietyNames[83][0][0]="Anime and Manga Society";
        SocietyNames[83][1][0]="Politics and Debating";
        SocietyNames[84][0][0]="Art Society";
        SocietyNames[84][1][0]="Unique";
        SocietyNames[85][0][0]="Astronomy Society (AstroSoc)";
        SocietyNames[85][1][0]="Unique";
        SocietyNames[86][0][0]="Carnival";
        SocietyNames[86][1][0]="Unique";
        SocietyNames[87][0][0]="Classics Society";
        SocietyNames[87][1][0]="Unique";
        SocietyNames[88][0][0]="Cocktail Society (YUCS)";
        SocietyNames[88][1][0]="Unique";
        SocietyNames[89][0][0]="Coffee Society (CoffeeSoc)";
        SocietyNames[89][1][0]="Unique";
        SocietyNames[90][0][0]="Creative Writing Society (York Inklings)";
        SocietyNames[90][1][0]="Unique";
        SocietyNames[91][0][0]="DIY Society";
        SocietyNames[91][1][0]="Unique";
        SocietyNames[92][0][0]="Doctor Who Society";
        SocietyNames[92][1][0]="Unique";
        SocietyNames[93][0][0]="Education Society (EdSoc)";
        SocietyNames[93][1][0]="Unique";
        SocietyNames[94][0][0]="Fashion Society (FashSoc)";
        SocietyNames[94][1][0]="Unique";
        SocietyNames[95][0][0]="Feminist Society (FemSoc)";
        SocietyNames[95][1][0]="Unique";
        SocietyNames[96][0][0]="Fetish Society (FetSoc)";
        SocietyNames[96][1][0]="Unique";
        SocietyNames[97][0][0]="Game Development Society (DevSoc)";
        SocietyNames[97][1][0]="Unique";
        SocietyNames[98][0][0]="Global Health Medsin";
        SocietyNames[98][1][0]="Unique";
        SocietyNames[99][0][0]="Gym Society (York Muscle)";
        SocietyNames[99][1][0]="Unique";
        SocietyNames[100][0][0]="HiFi and DIY Earphone Society (HiFiSoc)";
        SocietyNames[100][1][0]="Unique";
        SocietyNames[101][0][0]="HP Muggle Society";
        SocietyNames[101][1][0]="Unique";
        SocietyNames[102][0][0]="LGBTQ Social";
        SocietyNames[102][1][0]="Unique";
        SocietyNames[103][0][0]="Life Matters";
        SocietyNames[103][1][0]="Unique";
        SocietyNames[104][0][0]="Make-up Artistry Society";
        SocietyNames[104][1][0]="Unique";
        SocietyNames[105][0][0]="Marrow";
        SocietyNames[105][1][0]="Unique";
        SocietyNames[106][0][0]="Medieval Society";
        SocietyNames[106][1][0]="Unique";
        SocietyNames[107][0][0]="Midwifery Society (MidSoc)";
        SocietyNames[107][1][0]="Unique";
        SocietyNames[108][0][0]="Movie Society";
        SocietyNames[108][1][0]="Unique";
        SocietyNames[109][0][0]="Poetry Society";
        SocietyNames[109][1][0]="Unique";
        SocietyNames[110][0][0]="Real Ale Society";
        SocietyNames[110][1][0]="Unique";
        SocietyNames[111][0][0]="Rotaract Society";
        SocietyNames[111][1][0]="Unique";
        SocietyNames[112][0][0]="Scoop Student Co-operative";
        SocietyNames[112][1][0]="Unique";
        SocietyNames[113][0][0]="Scouting and Girl Guiding at York (SAGGY)";
        SocietyNames[113][1][0]="Unique";
        SocietyNames[114][0][0]="Sewing Society (SewSoc)";
        SocietyNames[114][1][0]="Unique";
        SocietyNames[115][0][0]="Shakespeare and Wine Society";
        SocietyNames[115][1][0]="Unique";
        SocietyNames[116][0][0]="Sherlock Society (SherSoc)";
        SocietyNames[116][1][0]="Unique";
        SocietyNames[117][0][0]="Society of Ice and Fire (GoTSoc)";
        SocietyNames[117][1][0]="Unique";
        SocietyNames[118][0][0]="Taylor Swift Society (SwiftSoc)";
        SocietyNames[118][1][0]="Unique";
        SocietyNames[119][0][0]="Tea Society";
        SocietyNames[119][1][0]="Unique";
        SocietyNames[120][0][0]="Theatregoers Society";
        SocietyNames[120][1][0]="Unique";
        SocietyNames[121][0][0]="Tolkien Society (TolkSoc)";
        SocietyNames[121][1][0]="Unique";
        SocietyNames[122][0][0]="Vegetarian and Vegan Society (VegSoc)";
        SocietyNames[122][1][0]="Unique";
        SocietyNames[123][0][0]="Wildlife Society (WildSoc)";
        SocietyNames[123][1][0]="Unique";
        SocietyNames[124][0][0]="Wine Appreciation Society York (WASY)";
        SocietyNames[124][1][0]="Unique";
        SocietyNames[125][0][0]="World Cinema Society";
        SocietyNames[125][1][0]="Unique";
        SocietyNames[126][0][0]="Aerobics Society";
        SocietyNames[126][1][0]="Unique";
        SocietyNames[127][0][0]="Airsoft and Paintball Society (APSoc)";
        SocietyNames[127][1][0]="Games and Activities";
        SocietyNames[128][0][0]="Baking Society";
        SocietyNames[128][1][0]="Games and Activities";
        SocietyNames[129][0][0]="Chess Society";
        SocietyNames[129][1][0]="Games and Activities";
        SocietyNames[130][0][0]="Douglas Adams Society (DougSoc)";
        SocietyNames[130][1][0]="Games and Activities";
        SocietyNames[131][0][0]="GO Society (York GO)";
        SocietyNames[131][1][0]="Games and Activities";
        SocietyNames[132][0][0]="HAZ Soc";
        SocietyNames[132][1][0]="Games and Activities";
        SocietyNames[133][0][0]="Juggle Society";
        SocietyNames[133][1][0]="Games and Activities";
        SocietyNames[134][0][0]="Knitting Society (KnitSoc)";
        SocietyNames[134][1][0]="Games and Activities";
        SocietyNames[135][0][0]="Off Campus Society";
        SocietyNames[135][1][0]="Games and Activities";
        SocietyNames[136][0][0]="Outdoor Society";
        SocietyNames[136][1][0]="Games and Activities";
        SocietyNames[137][0][0]="Pokemon Society (PokeSoc)";
        SocietyNames[137][1][0]="Games and Activities";
        SocietyNames[138][0][0]="Poker Society";
        SocietyNames[138][1][0]="Games and Activities";
        SocietyNames[139][0][0]="Pole Exercise Society (YUPE)";
        SocietyNames[139][1][0]="Games and Activities";
        SocietyNames[140][0][0]="Quite Interesting Society";
        SocietyNames[140][1][0]="Games and Activities";
        SocietyNames[141][0][0]="Science Fiction and Fantasy Society";
        SocietyNames[141][1][0]="Games and Activities";
        SocietyNames[142][0][0]="Slackline Society";
        SocietyNames[142][1][0]="Games and Activities";
        SocietyNames[143][0][0]="Yoga Society";
        SocietyNames[143][1][0]="Games and Activities";
        SocietyNames[144][0][0]="Ballet Society";
        SocietyNames[144][1][0]="Games and Activities";
        SocietyNames[145][0][0]="Caledonian Society";
        SocietyNames[145][1][0]="Performance";
        SocietyNames[146][0][0]="Central Hall Musical Society (CHMS)";
        SocietyNames[146][1][0]="Performance";
        SocietyNames[147][0][0]="Comedy Society";
        SocietyNames[147][1][0]="Performance";
        SocietyNames[148][0][0]="Dance Society";
        SocietyNames[148][1][0]="Performance";
        SocietyNames[149][0][0]="Dancesport";
        SocietyNames[149][1][0]="Performance";
        SocietyNames[150][0][0]="Drama Society";
        SocietyNames[150][1][0]="Performance";
        SocietyNames[151][0][0]="Fusion";
        SocietyNames[151][1][0]="Performance";
        SocietyNames[152][0][0]="Gilbert and Sullivan Society";
        SocietyNames[152][1][0]="Performance";
        SocietyNames[153][0][0]="Magic Society";
        SocietyNames[153][1][0]="Performance";
        SocietyNames[154][0][0]="Pantomime Society (PantSoc)";
        SocietyNames[154][1][0]="Performance";
        SocietyNames[155][0][0]="Swing Dance Society";
        SocietyNames[155][1][0]="Performance";
        SocietyNames[156][0][0]="Applied Social Sciences and Social Policy Society (ASSSP)";
        SocietyNames[156][1][0]="Performance";
        SocietyNames[157][0][0]="Archaeology Society (ArchSoc)";
        SocietyNames[157][1][0]="Academic and Careers";
        SocietyNames[158][0][0]="Art History Society";
        SocietyNames[158][1][0]="Academic and Careers";
        SocietyNames[159][0][0]="Biomedical Sciences Society (BiomedSoc)";
        SocietyNames[159][1][0]="Academic and Careers";
        SocietyNames[160][0][0]="Biosciences Society (BioSoc)";
        SocietyNames[160][1][0]="Academic and Careers";
        SocietyNames[161][0][0]="Bright Futures Society";
        SocietyNames[161][1][0]="Academic and Careers";
        SocietyNames[162][0][0]="Business, Accounting and Management Society (BAM Soc)";
        SocietyNames[162][1][0]="Academic and Careers";
        SocietyNames[163][0][0]="Chemistry Society (ChemSoc)";
        SocietyNames[163][1][0]="Academic and Careers";
        SocietyNames[164][0][0]="Criminology and Criminal Justice Society (CrimSoc)";
        SocietyNames[164][1][0]="Academic and Careers";
        SocietyNames[165][0][0]="Economics Society";
        SocietyNames[165][1][0]="Academic and Careers";
        SocietyNames[166][0][0]="Elevation Networks";
        SocietyNames[166][1][0]="Academic and Careers";
        SocietyNames[167][0][0]="Entrepreneurs Society";
        SocietyNames[167][1][0]="Academic and Careers";
        SocietyNames[168][0][0]="Environment Society (EnviroSoc)";
        SocietyNames[168][1][0]="Academic and Careers";
        SocietyNames[169][0][0]="French Society (FrenchSoc)";
        SocietyNames[169][1][0]="Academic and Careers";
        SocietyNames[170][0][0]="General Practitioner Society (GPsoc)";
        SocietyNames[170][1][0]="Academic and Careers";
        SocietyNames[171][0][0]="HackSoc";
        SocietyNames[171][1][0]="Academic and Careers";
        SocietyNames[172][0][0]="History Society";
        SocietyNames[172][1][0]="Academic and Careers";
        SocietyNames[173][0][0]="iGEM Society";
        SocietyNames[173][1][0]="Academic and Careers";
        SocietyNames[174][0][0]="Investment and Finance Society";
        SocietyNames[174][1][0]="Academic and Careers";
        SocietyNames[175][0][0]="Law Society";
        SocietyNames[175][1][0]="Academic and Careers";
        SocietyNames[176][0][0]="Linguistics Society";
        SocietyNames[176][1][0]="Academic and Careers";
        SocietyNames[177][0][0]="Literature Society (LitSoc)";
        SocietyNames[177][1][0]="Academic and Careers";
        SocietyNames[178][0][0]="Maths Society";
        SocietyNames[178][1][0]="Academic and Careers";
        SocietyNames[179][0][0]="MedSoc";
        SocietyNames[179][1][0]="Academic and Careers";
        SocietyNames[180][0][0]="Nursing Society (NurSoc)";
        SocietyNames[180][1][0]="Academic and Careers";
        SocietyNames[181][0][0]="Philosophy Society";
        SocietyNames[181][1][0]="Academic and Careers";
        SocietyNames[182][0][0]="Physics Society (PhysSoc)";
        SocietyNames[182][1][0]="Academic and Careers";
        SocietyNames[183][0][0]="Psychology Society (PsychSoc)";
        SocietyNames[183][1][0]="Academic and Careers";
        SocietyNames[184][0][0]="ShockSoc";
        SocietyNames[184][1][0]="Academic and Careers";
        SocietyNames[185][0][0]="Social and Political Sciences Society (SPSSoc)";
        SocietyNames[185][1][0]="Academic and Careers";
        SocietyNames[186][0][0]="Sociology Society (SocSoc)";
        SocietyNames[186][1][0]="Academic and Careers";
        SocietyNames[187][0][0]="Surgical Society (York SurgSoc)";
        SocietyNames[187][1][0]="Academic and Careers";
        SocietyNames[188][0][0]="TEDxUoY";
        SocietyNames[188][1][0]="Academic and Careers";
        SocietyNames[189][0][0]="Aikido";
        SocietyNames[189][1][0]="Sports";
        SocietyNames[190][0][0]="American Football";
        SocietyNames[190][1][0]="Sports";
        SocietyNames[191][0][0]="Archery";
        SocietyNames[191][1][0]="Sports";
        SocietyNames[192][0][0]="Athletics";
        SocietyNames[192][1][0]="Sports";
        SocietyNames[193][0][0]="Badminton";
        SocietyNames[193][1][0]="Sports";
        SocietyNames[194][0][0]="Barbell";
        SocietyNames[194][1][0]="Sports";
        SocietyNames[195][0][0]="Basketball";
        SocietyNames[195][1][0]="Sports";
        SocietyNames[196][0][0]="Boat";
        SocietyNames[196][1][0]="Sports";
        SocietyNames[197][0][0]="Boxing";
        SocietyNames[197][1][0]="Sports";
        SocietyNames[198][0][0]="Canoe";
        SocietyNames[198][1][0]="Sports";
        SocietyNames[199][0][0]="Canoe Polo";
        SocietyNames[199][1][0]="Sports";
        SocietyNames[200][0][0]="Capoeira";
        SocietyNames[200][1][0]="Sports";
        SocietyNames[201][0][0]="Cave & Potholing";
        SocietyNames[201][1][0]="Sports";
        SocietyNames[202][0][0]="Cheerleading (York Hornets)";
        SocietyNames[202][1][0]="Sports";
        SocietyNames[203][0][0]="Clay Pigeon Shooting Club";
        SocietyNames[203][1][0]="Sports";
        SocietyNames[204][0][0]="Cycling";
        SocietyNames[204][1][0]="Sports";
        SocietyNames[205][0][0]="Darts";
        SocietyNames[205][1][0]="Sports";
        SocietyNames[206][0][0]="Fencing Club";
        SocietyNames[206][1][0]="Sports";
        SocietyNames[207][0][0]="Futsal";
        SocietyNames[207][1][0]="Sports";
        SocietyNames[208][0][0]="Gliding";
        SocietyNames[208][1][0]="Sports";
        SocietyNames[209][0][0]="Golf";
        SocietyNames[209][1][0]="Sports";
        SocietyNames[210][0][0]="Gymnastics";
        SocietyNames[210][1][0]="Sports";
        SocietyNames[211][0][0]="Handball";
        SocietyNames[211][1][0]="Sports";
        SocietyNames[212][0][0]="Hockey";
        SocietyNames[212][1][0]="Sports";
        SocietyNames[213][0][0]="JiuJitsu";
        SocietyNames[213][1][0]="Sports";
        SocietyNames[214][0][0]="Judo";
        SocietyNames[214][1][0]="Sports";
        SocietyNames[215][0][0]="Karate";
        SocietyNames[215][1][0]="Sports";
        SocietyNames[216][0][0]="Karting";
        SocietyNames[216][1][0]="Sports";
        SocietyNames[217][0][0]="Kendo";
        SocietyNames[217][1][0]="Sports";
        SocietyNames[218][0][0]="Lacrosse";
        SocietyNames[218][1][0]="Sports";
        SocietyNames[219][0][0]="Men's Cricket";
        SocietyNames[219][1][0]="Sports";
        SocietyNames[220][0][0]="Men's Football";
        SocietyNames[220][1][0]="Sports";
        SocietyNames[221][0][0]="Men's Rugby Union";
        SocietyNames[221][1][0]="Sports";
        SocietyNames[222][0][0]="MMA & Thai Boxing";
        SocietyNames[222][1][0]="Sports";
        SocietyNames[223][0][0]="Mountaineering";
        SocietyNames[223][1][0]="Sports";
        SocietyNames[224][0][0]="Netball";
        SocietyNames[224][1][0]="Sports";
        SocietyNames[225][0][0]="Octopush";
        SocietyNames[225][1][0]="Sports";
        SocietyNames[226][0][0]="Orienteering";
        SocietyNames[226][1][0]="Sports";
        SocietyNames[227][0][0]="Polo";
        SocietyNames[227][1][0]="Sports";
        SocietyNames[228][0][0]="Pool and Snooker";
        SocietyNames[228][1][0]="Sports";
        SocietyNames[229][0][0]="Riding";
        SocietyNames[229][1][0]="Sports";
        SocietyNames[230][0][0]="Sailing and Windsurfing";
        SocietyNames[230][1][0]="Sports";
        SocietyNames[231][0][0]="Skydiving";
        SocietyNames[231][1][0]="Sports";
        SocietyNames[232][0][0]="Snowsports (YUsnow)";
        SocietyNames[232][1][0]="Sports";
        SocietyNames[233][0][0]="Squash";
        SocietyNames[233][1][0]="Sports";
        SocietyNames[234][0][0]="Sub-Aqua";
        SocietyNames[234][1][0]="Sports";
        SocietyNames[235][0][0]="Surf";
        SocietyNames[235][1][0]="Sports";
        SocietyNames[236][0][0]="Swimming and Water Polo Club";
        SocietyNames[236][1][0]="Sports";
        SocietyNames[237][0][0]="Table Tennis";
        SocietyNames[237][1][0]="Sports";
        SocietyNames[238][0][0]="Taekwondo";
        SocietyNames[238][1][0]="Sports";
        SocietyNames[239][0][0]="Tai Chi Chuan";
        SocietyNames[239][1][0]="Sports";
        SocietyNames[240][0][0]="Tennis";
        SocietyNames[240][1][0]="Sports";
        SocietyNames[241][0][0]="Trampolining";
        SocietyNames[241][1][0]="Sports";
        SocietyNames[242][0][0]="Triathlon";
        SocietyNames[242][1][0]="Sports";
        SocietyNames[243][0][0]="Ultimate Frisbee";
        SocietyNames[243][1][0]="Sports";
        SocietyNames[244][0][0]="Volleyball";
        SocietyNames[244][1][0]="Sports";
        SocietyNames[245][0][0]="VX";
        SocietyNames[245][1][0]="Sports";
        SocietyNames[246][0][0]="Wakeboard";
        SocietyNames[246][1][0]="Sports";
        SocietyNames[247][0][0]="Wing Chun";
        SocietyNames[247][1][0]="Sports";
        SocietyNames[248][0][0]="Women's Cricket";
        SocietyNames[248][1][0]="Sports";
        SocietyNames[249][0][0]="Women's Football";
        SocietyNames[249][1][0]="Sports";
        SocietyNames[250][0][0]="Women's Rugby Union";
        SocietyNames[250][1][0]="Sports";

        return SocietyNames;

    }

    //This method displays the categories checkboxes to filter the societies list when the filter button is pressed
    public void showOptions(){
        filterLayout.setVisibility(LinearLayout.VISIBLE);	//Display the categories checkboxes
        mainListView.setVisibility(LinearLayout.GONE);		//Remove the societies list
        filterButton.setVisibility(Button.GONE);			//Remove the filter button
    }

    //This method displays the societies list after the categories list has been displayed
    public void closeOptions(){
        filterLayout.setVisibility(LinearLayout.GONE);		//Remove the categories checkboxes
        mainListView.setVisibility(LinearLayout.VISIBLE);	//Display the societies list
        filterButton.setVisibility(Button.VISIBLE);			//Display the filter button
    }

    //This method adds all society names to the society names array to generate the societies list
    public void setSocieties(ArrayList<String> SocietyNames){
        listAdapter.clear();
        listAdapter.addAll(SocietyNames);
        mainListView.setAdapter( listAdapter );
    }

    //This method adds filtered societies to the society names array to generate a filtered societies list
    public void addSocieties(ArrayList<String> SocietyNames){
        listAdapter.addAll(SocietyNames);
        mainListView.setAdapter( listAdapter );
    }

    public ArrayList<String> filterByCategoryThenTag(String[][][] SocietyNames,ArrayList<String> categories,ArrayList<String> tags){

        ArrayList<String> filteredSocieties = new ArrayList<>();

        for(int society = 0; society<SocietyNames.length;society++)          					/*loops through societies*/
            for (int category = 0; category < categories.size(); category++)					/*loops through the filter categories*/
                if (categories.get(category).equals(SocietyNames[society][1][0]))				/*checks if the current filter category matches that of the current society*/
                    for(int tag = 0; tag < tags.size();tag++)									/*loops through the filter tags*/
                        for(int a = 0; a < SocietyNames[society][1].length; a++)				/*loops through the current societies tags*/
                            if(tags.get(tag).equals(SocietyNames[society][0][a]))				/*checks if the current filter tag matches any tags in the current society*/
                                if(!filteredSocieties.contains(SocietyNames[society][0][0]))    /*if society has already been filtered then ignore it*/
                                    filteredSocieties.add(SocietyNames[society][0][0]);

        //sort the list alphabetically and return it
        return alphabeticalSort(filteredSocieties);
    }

    //This method sorts an array list alphabetically
    public ArrayList<String> alphabeticalSort(ArrayList<String> list){
        Collections.sort(list);
        return list;
    }

}
