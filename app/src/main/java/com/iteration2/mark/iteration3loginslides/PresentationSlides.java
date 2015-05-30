package com.iteration2.mark.iteration3loginslides;
/*
Claritas: ‘Clarity through innovation’
Project: SocBox
Module: Presentation Slides
Code File Name: PresentationSlides.java
Description: This is the parent class of the Presentation Slides module. This class will receive
             all information and media content relevant to the presentation to be created from the
             server and pass said data into the slide templates in order for them to populate
             themselves. The user will be able to choose whether the view the presentation
             in automatic or manual progression modes.
Initial Authors: David Retief
                 Muhammad Zharif Hadi Mohd Khairuddin
Change History:
Version: 0.1
Author: David Retief
Change: Created original version. General Layout for all slides
Date: 10/03/2015
Version: 0.2
Authors: Mark Stonehouse (& David Retief)
Change: Slides progress using Timer
Date: 25/03/2015
Version: 0.3
Author: David Retief
Change: All slides obtain media content from parent class and progress as a constant presentation
            allowing any number of slides, in any order.
        Auto-embedding & Auto-scroll of scrolling image panels, delays timer until scroll complete.
        Audio player embedded in slides 2, 3, 6, 7, 9
        Video player embedded in slides 1, 5, 8
Date: 06/04/2015
Traceabilty:
Tag: U/PS/01/01
Requirement: The slides can have 9 possible layouts.
Tag: U/PS/02/01
Requirement: The user can view video, images, and text embedded within the slides.
Tag: U/PS/03/01
Requirement: The user will be able to view panels with auto-scroll capabilities.
Tag: U/PS/04/01
Requirement: The user can choose to progress through the slideshow manually or automatically.
Tag: U/PS/05/01
Requirement: The user can access additional content via linked text and images.
Tag: U/PS/06/01
Requirement: The user can control the audio and video on these slides.
Tag: U/PS/07/01
Requirement: The user will progress to the relevant society’s home page upon completion of the
             slideshow.
Tag: U/PS/08/01
Requirement: The user can restart or exit the slideshow at any point.
Tag: U/PS/09/01
Requirement: By visiting an additional content slide the user will pause the media in the previous
             slide.
Other Information:
Note: This file is still work in progress.
 Todo: General GUI tidy-up.
 Todo: Slide transitions.
 Todo: Back button functionality?
 Todo: Add ActionBar buttons to restart and exit the presentation.
 Todo: Add additional content functionality.
 Todo: Progress to society home page upon completion instead of parent class.
 Todo: Delay timer until Audio and Video are complete (requires the Handlers to be edited).
 Todo: Receive information from server/parser (requires creation server and XML parser).
 Todo: Slide not populated pop-up to negate "pack slide bundle may produce null pointer exception" warning on each slide.
*/


        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;


public class PresentationSlides extends ActionBarActivity {

    private String TAG = "PresentationSlidesTAG";

    // thisPackage will need to be changed if the project is imported to use another package.
    public final String thisPackage = "com.half";

    // The activity titles are passed into the slides in order for the intents to know what the
    //   next slide activity name is.

    public final String[] activityTitles = new String[] {"PresentationSlides", "SlideA",
            "SlideB", "SlideC", "SlideD",
            "SlideE", "SlideF", "SlideG",
            "SlideH", "SlideI"};

    // masterBundle will hold all information to be passed into the slides for the presentation.
    private Bundle masterBundle = new Bundle();

    // slideFuncBundle will hold information relevant to the operation of the presentation (not
    //   media content).
    private Bundle slideFuncBundle = new Bundle();

    // Temporary order of slides for the example presentation, will be replaced when server is
    //   working.
    public int[] slideOrder = new int[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    // Additional Content slides, 0 denotes no additional content present in slide.
    public int[] addContentOrder = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    //--------------------------------------------------------------------------------------------//
    //-----------------------------------  Functional Methods  -----------------------------------//
    //--------------------------------------------------------------------------------------------//
    // This method uses the slide numbers array and extracts the number of times each template is
    //   used throughout the presentation.
    private int extractSlideInfo(int slideNumber) {
        int slideInstances = 0;
        int slideOrderLength = slideOrder.length;
        for (int i = 0; i < slideOrderLength; i++) {
            if (slideOrder[i] == slideNumber) {
                slideInstances++;
            }
        }
        return slideInstances;
    }

    private int[] extractSlideArray(int slideNumber) {
        int[] slideInstanceArray = new int[slideOrder.length];
        int slideInstance = 0;
        int slideOrderLength = slideOrder.length;
        for (int i = 0; i < slideOrderLength; i++) {
            if (slideOrder[i] == slideNumber) {
                slideInstance++;
                slideInstanceArray[i] = slideInstance;
            }
            else {
                slideInstanceArray[i] = 0;
            }
        }
        return slideInstanceArray;
    }

    // This method uses the
    private int extractAddContentInfo(int slideNumber) {
        int addContentSlideInstances = 0;
        int addContentOrderLength = addContentOrder.length;
        for (int i = 0; i < addContentOrderLength; i++) {
            if (addContentOrder[i] == slideNumber) {
                addContentSlideInstances++;
            }
        }
        return addContentSlideInstances;
    }

    // This method uses the
    private int[] extractAddContentInstanceArray(int slideNumber) {
        int[] addContentInstanceArray = new int[addContentOrder.length];
        int addContentInstance = 0;
        int addContentOrderLength = addContentOrder.length;
        for (int i = 0; i < addContentOrderLength; i++) {
            if (addContentOrder[i] == slideNumber) {
                addContentInstance++;
                addContentInstanceArray[i] = addContentInstance;
            }
            else {
                addContentInstanceArray[i] = 0;
            }
        }
        return addContentInstanceArray;
    }

    //----------------------------------  Example Presentation  ----------------------------------//
    // This method sets up the example presentation for debugging purposes.
    private void examplePresentation(){
        // These pack all of the media content and slide information into the relevant bundles,
        //   allowing them to to be sent into the template activities.
        packFuncBundle();
        packSlide1(extractSlideInfo(1));
        packSlide2(extractSlideInfo(2));
        packSlide3(extractSlideInfo(3));
        packSlide4(extractSlideInfo(4));
        packSlide5(extractSlideInfo(5));
        packSlide6(extractSlideInfo(6));
        packSlide7(extractSlideInfo(7));
        packSlide8(extractSlideInfo(8));
        packSlide9(extractSlideInfo(9));

        // Sets Auto-progress mode, this will be changed to be user-selectable
        Boolean auto = true;
        slideFuncBundle.putBoolean("autoProgress", auto);
        masterBundle.putBundle("slideFuncBundle", slideFuncBundle);

        // Selects the first activity of the presentation.
        int[] slideOrder = slideFuncBundle.getIntArray("slideOrder");

        String nextSlideActivity = thisPackage + "." + activityTitles[slideOrder[0]];
//        String nextSlideActivity = "." + activityTitles[slideOrder[0]];
        Class<?> nextClass = null;
        try {
            nextClass = Class.forName(nextSlideActivity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Start new activity (the first slide of the presentation)
        Intent examplePresentationIntent = new Intent(PresentationSlides.this, nextClass);
        examplePresentationIntent.putExtra("masterBundle", masterBundle);
        startActivity(examplePresentationIntent);
    }

    //--------------------------------------------------------------------------------------------//
    //----------------------------------  Packing Slide Bundles  ---------------------------------//
    //--------------------------------------------------------------------------------------------//
    private void slide1ButtonPressed(){
        // Pack the relevant bundles (the functional slide data and media content respectively)
        packFuncBundle();
        packSlide1(1);

        // Start the slide activity.
        Intent slideOneIntent = new Intent(PresentationSlides.this, SlideA.class );
        slideOneIntent.putExtra("masterBundle", masterBundle);
        startActivity(slideOneIntent);
    }

    private void slide2ButtonPressed(){
        // Pack the relevant bundles (the functional slide data and media content respectively)
        packFuncBundle();
        packSlide2(1);

        // Start the slide activity.
        Intent slideTwoIntent = new Intent(PresentationSlides.this, SlideB.class );
        slideTwoIntent.putExtra("masterBundle", masterBundle);
        startActivity(slideTwoIntent);
    }

    private void slide3ButtonPressed(){
        // Pack the relevant bundles (the functional slide data and media content respectively)
        packFuncBundle();
        packSlide3(1);

        // Start the slide activity.
        Intent slideThreeIntent = new Intent(PresentationSlides.this, SlideC.class );
        slideThreeIntent.putExtra("masterBundle", masterBundle);
        startActivity(slideThreeIntent);
    }

    private void slide4ButtonPressed(){
        // Pack the relevant bundles (the functional slide data and media content respectively)
        packFuncBundle();
        packSlide4(1);

        // Start the slide activity.
        Intent slideFourIntent = new Intent(PresentationSlides.this, SlideD.class );
        slideFourIntent.putExtra("masterBundle", masterBundle);
        startActivity(slideFourIntent);
    }

    private void slide5ButtonPressed(){
        // Pack the relevant bundles (the functional slide data and media content respectively)
        packFuncBundle();
        packSlide5(1);

        // Start the slide activity.
        Intent slideFiveIntent = new Intent(PresentationSlides.this, SlideE.class );
        slideFiveIntent.putExtra("masterBundle", masterBundle);
        startActivity(slideFiveIntent);
    }

    private void slide6ButtonPressed(){
        // Pack the relevant bundles (the functional slide data and media content respectively)
        packFuncBundle();
        packSlide6(1);

        // Start the slide activity.
        Intent slideSixIntent = new Intent(PresentationSlides.this, SlideF.class );
        slideSixIntent.putExtra("masterBundle", masterBundle);
        startActivity(slideSixIntent);
    }

    private void slide7ButtonPressed(){
        // Pack the relevant bundles (the functional slide data and media content respectively)
        packFuncBundle();
        packSlide7(1);

        // Start the slide activity.
        Intent slideSevenIntent = new Intent(PresentationSlides.this, SlideG.class );
        slideSevenIntent.putExtra("masterBundle", masterBundle);
        startActivity(slideSevenIntent);
    }

    private void slide8ButtonPressed(){
        // Pack the relevant bundles (the functional slide data and media content respectively)
        packFuncBundle();
        packSlide8(1);

        // Start the slide activity.
        Intent slideEightIntent = new Intent(PresentationSlides.this, SlideH.class );
        slideEightIntent.putExtra("masterBundle", masterBundle);
        startActivity(slideEightIntent);
    }

    private void slide9ButtonPressed(){
        // Pack the relevant bundles (the functional slide data and media content respectively)
        packFuncBundle();
        packSlide9(1);

        // Start the slide activity.
        Intent slideNineIntent = new Intent(PresentationSlides.this, SlideI.class );
        slideNineIntent.putExtra("masterBundle", masterBundle);
        startActivity(slideNineIntent);
    }

    //--------------------------------------------------------------------------------------------//
    //----------------------------------  Packing Slide Bundles  ---------------------------------//
    //--------------------------------------------------------------------------------------------//

    // Packs the slides functional data, relating to the flow of the presentation.
    private void packFuncBundle(){

        slideFuncBundle.putIntArray("slideOrder", slideOrder);

        // slideNumber is the position of the presentation.
        slideFuncBundle.putInt("slideNumber", 0);

        // Setting Auto-Progress mode to false, for the individual slide selection. This is
        //   overwritten later if Auto-Progress mode is selected.
        slideFuncBundle.putBoolean("autoProgress", false);

        // Order of Additional Content slides
        slideFuncBundle.putIntArray("addContentOrder", addContentOrder);

        masterBundle.putBundle("slideFuncBundle", slideFuncBundle);
    }

    private void packSlide1(int numberOfInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide1Heading = {"Slide 1 Heading 1", "Slide 1 Heading 2"};

        // Video info to be passed into activity
        String[] slide1VideoURL = {"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"};
        //******************************************************************************************

        int totAddContentInstances = extractAddContentInfo(1);
        if (totAddContentInstances > 0) packSlide1AddContent(totAddContentInstances);

        int[] slide1InstanceArray = extractSlideArray(1);

        // Bundle all media information, ready to be sent via Intent
        Bundle slide1Bundle = new Bundle();
        for (int slideInstance = 0; slideInstance < numberOfInstances; slideInstance++) {
            slide1Bundle.putString("slide1Heading" + (slideInstance + 1), slide1Heading[slideInstance]);
            slide1Bundle.putString("slide1VideoURL" + (slideInstance + 1), slide1VideoURL[slideInstance]);
        }
        // Add instance info to pass into the slides
        slide1Bundle.putIntArray("slide1InstanceArray", slide1InstanceArray);
        masterBundle.putBundle("slide1Bundle", slide1Bundle);
    }

    private void packSlide1AddContent(int totalAddContInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide1Heading = {"Slide 1 Add Cont 1", "Slide 1 Add Cont 2"};

        // Video info to be passed into activity
        String[] slide1VideoURL = {"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"};
        //******************************************************************************************

        // Bundle all media information, ready to be sent via Intent
        Bundle slide1AddBundle = new Bundle();
        for (int addContInstance = 0; addContInstance < totalAddContInstances; addContInstance++) {
            slide1AddBundle.putString("slide1Heading" + (addContInstance + 1), slide1Heading[addContInstance]);
            slide1AddBundle.putString("slide1VideoURL" + (addContInstance + 1), slide1VideoURL[addContInstance]);
            slide1AddBundle.putInt("addContInstance", 0);
        }
        // Add instance info to pass into the slides
        slide1AddBundle.putIntArray("addContentInstanceArray", extractAddContentInstanceArray(1));
        masterBundle.putBundle("slide1AddBundle", slide1AddBundle);
    }

    private void packSlide2(int numberOfInstances){
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide2Heading = {"Slide 2 Heading 1", "Slide 2 Heading 2"};
        String[] slide2TextBody = {"Larger Body of Informative Text Included Here 1",
                "Larger Body of Informative Text Included Here 2"};

        // Image info to be passed into activity
        String[] slide2Image = {"ic_adventure", "ic_football"};

        // Audio info to be passed into activity
        Boolean[] slide2ActiveAudio = new Boolean[] {true, false};
        String[][] slide2Audio = new String[][] {
                {"http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3",
                        "https://www.elec.york.ac.uk/internal_web/meng/yr3/modules/Filters/Digital/sounds/breakfast.wav"},
                {null, null}
        };
        //******************************************************************************************

        int totAddContentInstances = extractAddContentInfo(2);
        if (totAddContentInstances > 0) packSlide2AddContent(totAddContentInstances);

        int[] slide2InstanceArray = extractSlideArray(2);
        // Bundle all media information, ready to be sent via Intent
        Bundle slide2Bundle = new Bundle();
        for (int slideInstance = 0; slideInstance < numberOfInstances; slideInstance++) {
            slide2Bundle.putString("slide2Heading" + (slideInstance + 1), slide2Heading[slideInstance]);
            slide2Bundle.putString("slide2TextBody" + (slideInstance + 1), slide2TextBody[slideInstance]);
            slide2Bundle.putString("slide2Image" + (slideInstance + 1), slide2Image[slideInstance]);
            slide2Bundle.putBoolean("slide2ActiveAudio" + (slideInstance + 1), slide2ActiveAudio[slideInstance]);
            if (slide2ActiveAudio[slideInstance])
                slide2Bundle.putStringArray("slide2Audio" + (slideInstance + 1), slide2Audio[slideInstance]);
        }

        // Add instance info to pass into the slides
        slide2Bundle.putIntArray("slide2InstanceArray", slide2InstanceArray);
        masterBundle.putBundle("slide2Bundle", slide2Bundle);
    }

    private void packSlide2AddContent(int totalAddContInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide2Heading = {"Slide 2 Add Cont 1", "Slide 2 Add Cont 2"};
        String[] slide2TextBody = {"Larger Body of Informative Text Included Here 1",
                "Larger Body of Informative Text Included Here 2"};

        // Image info to be passed into activity
        String[] slide2Image = {"socbox_logo", "ic_chemistry"};

        // Audio info to be passed into activity
        Boolean[] slide2ActiveAudio = new Boolean[] {false, true};
        String[][] slide2Audio = new String[][] {
                {null, null},
                {"http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3",
                        "https://www.elec.york.ac.uk/internal_web/meng/yr3/modules/Filters/Digital/sounds/breakfast.wav"}
        };
        //******************************************************************************************

        // Bundle all media information, ready to be sent via Intent
        Bundle slide2AddBundle = new Bundle();
        for (int addContInstance = 0; addContInstance < totalAddContInstances; addContInstance++) {
            slide2AddBundle.putString("slide2Heading" + (addContInstance + 1), slide2Heading[addContInstance]);
            slide2AddBundle.putString("slide2TextBody" + (addContInstance + 1), slide2TextBody[addContInstance]);
            slide2AddBundle.putString("slide2Image" + (addContInstance + 1), slide2Image[addContInstance]);
            slide2AddBundle.putBoolean("slide2ActiveAudio" + (addContInstance + 1), slide2ActiveAudio[addContInstance]);
            if (slide2ActiveAudio[addContInstance])
                slide2AddBundle.putStringArray("slide2Audio" + (addContInstance + 1), slide2Audio[addContInstance]);
        }
        // Add instance info to pass into the slides
        slide2AddBundle.putIntArray("addContentInstanceArray", extractAddContentInstanceArray(2));
        masterBundle.putBundle("slide2AddBundle", slide2AddBundle);
    }

    private void packSlide3(int numberOfInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        String[] slide3Headings = new String[] {"Slide 3 Heading 1", "Slide 3 Heading 2"};
        String[] slide3Images = new String[] {"ic_adventure", "ic_chemistry"};
        Boolean[] slide3ActiveAudio = new Boolean[] {true, false};
        String[][] slide3Audio = new String[][] {
                {"http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3",
                        "https://www.elec.york.ac.uk/internal_web/meng/yr3/modules/Filters/Digital/sounds/breakfast.wav"},
                {null, null}
        };
        //******************************************************************************************

        int totAddContentInstances = extractAddContentInfo(3);
        if (totAddContentInstances > 0) packSlide3AddContent(totAddContentInstances);

        int[] slide3InstanceArray = extractSlideArray(3);

        // Bundle all media information, ready to be sent via Intent
        Bundle slide3Bundle = new Bundle();
        for (int slideInstance = 0; slideInstance < numberOfInstances; slideInstance++)
        {
            slide3Bundle.putString("slide3Heading" + (slideInstance+1), slide3Headings[slideInstance]);
            slide3Bundle.putString("slide3Image" + (slideInstance + 1), slide3Images[slideInstance]);
            slide3Bundle.putBoolean("slide3ActiveAudio" + (slideInstance + 1), slide3ActiveAudio[slideInstance]);
            if (slide3ActiveAudio[slideInstance]) {
                slide3Bundle.putStringArray("slide3Audio" + (slideInstance+1), slide3Audio[slideInstance]);
            }
        }
        // Add instance info to pass into the slides
        slide3Bundle.putIntArray("slide3InstanceArray", slide3InstanceArray);
        masterBundle.putBundle("slide3Bundle", slide3Bundle);
    }

    private void packSlide3AddContent(int totalAddContInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide3Heading = {"Slide 3 Add Cont 1", "Slide 3 Add Cont 2"};

        // Image info to be passed into activity
        String[] slide3Image = {"socbox_logo", "ic_chemistry"};

        // Audio info to be passed into activity
        Boolean[] slide3ActiveAudio = new Boolean[] {false, true};
        String[][] slide3Audio = new String[][] {
                {null, null},
                {"http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3",
                        "https://www.elec.york.ac.uk/internal_web/meng/yr3/modules/Filters/Digital/sounds/breakfast.wav"}
        };
        //******************************************************************************************

        // Bundle all media information, ready to be sent via Intent
        Bundle slide3AddBundle = new Bundle();
        for (int addContInstance = 0; addContInstance < totalAddContInstances; addContInstance++) {
            slide3AddBundle.putString("slide3Heading" + (addContInstance + 1), slide3Heading[addContInstance]);
            slide3AddBundle.putString("slide3Image" + (addContInstance + 1), slide3Image[addContInstance]);
            slide3AddBundle.putBoolean("slide3ActiveAudio" + (addContInstance + 1), slide3ActiveAudio[addContInstance]);
            if (slide3ActiveAudio[addContInstance])
                slide3AddBundle.putStringArray("slide3Audio" + (addContInstance + 1), slide3Audio[addContInstance]);
        }
        // Add instance info to pass into the slides
        slide3AddBundle.putIntArray("addContentInstanceArray", extractAddContentInstanceArray(3));
        masterBundle.putBundle("slide3AddBundle", slide3AddBundle);
    }

    private void packSlide4(int numberOfInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide4Headings = {"Slide 4 Heading 1", "Slide 4 Heading 2"};

        // Image Names for the top scrolling image panel
        String[][] slide4TopScrollImages = new String[2][];
        slide4TopScrollImages[0] = new String[6];
        slide4TopScrollImages[0][0] = "ic_adventure";
        slide4TopScrollImages[0][1] = "ic_chemistry";
        slide4TopScrollImages[0][2] = "ic_football";
        slide4TopScrollImages[0][3] = "ic_happypaint";
        slide4TopScrollImages[0][4] = "ic_quidditch";
        slide4TopScrollImages[0][5] = "ic_mountain";

        slide4TopScrollImages[1] = new String[4];
        slide4TopScrollImages[1][0] = "ic_happypaint";
        slide4TopScrollImages[1][1] = "ic_football";
        slide4TopScrollImages[1][2] = "ic_quidditch";
        slide4TopScrollImages[1][3] = "socbox_logo";

        // Image Names for the bottom scrolling image panel
        String[][] slide4BottomScrollImages = new String[2][];
        slide4BottomScrollImages[0] = new String[4];
        slide4BottomScrollImages[0][0] = "socbox_logo";
        slide4BottomScrollImages[0][1] = "ic_quidditch";
        slide4BottomScrollImages[0][2] = "ic_mountain";
        slide4BottomScrollImages[0][3] = "ic_chemistry";

        slide4BottomScrollImages[1] = new String[5];
        slide4BottomScrollImages[1][0] = "ic_football";
        slide4BottomScrollImages[1][1] = "ic_adventure";
        slide4BottomScrollImages[1][2] = "ic_mountain";
        slide4BottomScrollImages[1][3] = "ic_chemistry";
        slide4BottomScrollImages[1][4] = "ic_happypaint";

        int[] addContentImageBottom = {0, 1};
        int[] addContentImageTop = {6, 0};
        //******************************************************************************************

        int totAddContentInstances = extractAddContentInfo(4);
        if (totAddContentInstances > 0) packSlide4AddContent(totAddContentInstances);

        int[] slide4InstanceArray = extractSlideArray(4);

        // Bundle all media information, ready to be sent via Intent
        Bundle slide4Bundle = new Bundle();
        for (int slideInstance = 0; slideInstance < numberOfInstances; slideInstance++) {
            slide4Bundle.putString("slide4Heading" + (slideInstance+1),
                    slide4Headings[slideInstance]);

            slide4Bundle.putStringArray("slide4TopScrollImages" + (slideInstance + 1),
                    slide4TopScrollImages[slideInstance]);
            slide4Bundle.putStringArray("slide4BottomScrollImages" + (slideInstance + 1),
                    slide4BottomScrollImages[slideInstance]);

            slide4Bundle.putInt("addContentImageBottom" + (slideInstance + 1),
                    addContentImageBottom[slideInstance]);
            slide4Bundle.putInt("addContentImageTop" + (slideInstance + 1),
                    addContentImageTop[slideInstance]);
        }
        // Add instance info to pass into the slides
        slide4Bundle.putIntArray("slide4InstanceArray", slide4InstanceArray);
        masterBundle.putBundle("slide4Bundle", slide4Bundle);
    }

    private void packSlide4AddContent(int totalAddContInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide4Headings = {"Slide 4 Add Cont 1", "Slide 4 Add Cont 2"};

        // Image Names for the top scrolling image panel
        String[][] slide4TopScrollImages = new String[2][];
        slide4TopScrollImages[0] = new String[6];
        slide4TopScrollImages[0][0] = "ic_adventure";
        slide4TopScrollImages[0][1] = "ic_chemistry";
        slide4TopScrollImages[0][2] = "ic_football";
        slide4TopScrollImages[0][3] = "ic_happypaint";
        slide4TopScrollImages[0][4] = "ic_quidditch";
        slide4TopScrollImages[0][5] = "ic_mountain";

        slide4TopScrollImages[1] = new String[4];
        slide4TopScrollImages[1][0] = "ic_happypaint";
        slide4TopScrollImages[1][1] = "ic_football";
        slide4TopScrollImages[1][2] = "ic_quidditch";
        slide4TopScrollImages[1][3] = "socbox_logo";

        // Image Names for the bottom scrolling image panel
        String[][] slide4BottomScrollImages = new String[2][];
        slide4BottomScrollImages[0] = new String[4];
        slide4BottomScrollImages[0][0] = "socbox_logo";
        slide4BottomScrollImages[0][1] = "ic_quidditch";
        slide4BottomScrollImages[0][2] = "ic_mountain";
        slide4BottomScrollImages[0][3] = "ic_chemistry";

        slide4BottomScrollImages[1] = new String[5];
        slide4BottomScrollImages[1][0] = "ic_football";
        slide4BottomScrollImages[1][1] = "ic_adventure";
        slide4BottomScrollImages[1][2] = "ic_mountain";
        slide4BottomScrollImages[1][3] = "ic_chemistry";
        slide4BottomScrollImages[1][4] = "ic_happypaint";
        //******************************************************************************************

        // Bundle all media information, ready to be sent via Intent
        Bundle slide4AddBundle = new Bundle();

        for (int addContInstance = 0; addContInstance < totalAddContInstances; addContInstance++) {
            slide4AddBundle.putString("slide4Heading" + (addContInstance + 1), slide4Headings[addContInstance]);
            slide4AddBundle.putStringArray("slide4TopScrollImages" + (addContInstance + 1),
                    slide4TopScrollImages[addContInstance]);
            slide4AddBundle.putStringArray("slide4BottomScrollImages" + (addContInstance + 1),
                    slide4BottomScrollImages[addContInstance]);
        }
        // Add instance info to pass into the slides
        slide4AddBundle.putIntArray("addContentInstanceArray", extractAddContentInstanceArray(4));
        masterBundle.putBundle("slide4AddBundle", slide4AddBundle);
    }

    private void packSlide5(int numberOfInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide5Heading = {"Slide 5 Heading 1", "Slide 5 Heading 2"};

        // Image info to be passed into activity
        String[][] slide5Images = new String[2][4];
        slide5Images[0][0] = "ic_adventure";  // Top Left
        slide5Images[0][1] = "ic_chemistry";  // Top Right
        slide5Images[0][2] = "ic_football";   // Bottom Left
        slide5Images[0][3] = "ic_happypaint"; // Bottom Right

        slide5Images[1][0] = "ic_happypaint"; // Top Left
        slide5Images[1][1] = "ic_football";   // Top Right
        slide5Images[1][2] = "ic_chemistry";  // Bottom Left
        slide5Images[1][3] = "ic_adventure";  // Bottom Right

        int[] addContentStaticImages = {2, 4};

        // Video info to be passed into activity
        String[] slide5VideoURL = {"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"};
        //******************************************************************************************

        int totAddContentInstances = extractAddContentInfo(5);
        if (totAddContentInstances > 0) packSlide5AddContent(totAddContentInstances);

        int[] slide5InstanceArray = extractSlideArray(5);

        // Bundle all media information, ready to be sent via Intent
        Bundle slide5Bundle = new Bundle();
        for (int slideInstance = 0; slideInstance < numberOfInstances; slideInstance++) {
            slide5Bundle.putString("slide5Heading" + (slideInstance + 1), slide5Heading[slideInstance]);
            slide5Bundle.putStringArray("slide5Images" + (slideInstance + 1), slide5Images[slideInstance]);
            slide5Bundle.putInt("addContentStaticImages" + (slideInstance + 1),
                    addContentStaticImages[slideInstance]);
            slide5Bundle.putString("slide5VideoURL" + (slideInstance + 1), slide5VideoURL[slideInstance]);
        }
        // Add instance info to pass into the slides
        slide5Bundle.putIntArray("slide5InstanceArray", slide5InstanceArray);
        masterBundle.putBundle("slide5Bundle", slide5Bundle);
    }

    private void packSlide5AddContent(int totalAddContInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide5Heading = {"Slide 5 Add 1", "Slide 5 Add 2"};

        // Image info to be passed into activity
        String[][] slide5Images = new String[2][4];
        slide5Images[0][0] = "ic_adventure";  // Top Left
        slide5Images[0][1] = "ic_chemistry";  // Top Right
        slide5Images[0][2] = "ic_football";   // Bottom Left
        slide5Images[0][3] = "ic_happypaint"; // Bottom Right

        slide5Images[1][0] = "ic_happypaint"; // Top Left
        slide5Images[1][1] = "ic_football";   // Top Right
        slide5Images[1][2] = "ic_chemistry";  // Bottom Left
        slide5Images[1][3] = "ic_adventure";  // Bottom Right

        // Video info to be passed into activity
        String[] slide5VideoURL = {"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"};
        //******************************************************************************************

        // Bundle all media information, ready to be sent via Intent
        Bundle slide5AddBundle = new Bundle();

        for (int addContInstance = 0; addContInstance < totalAddContInstances; addContInstance++) {
            slide5AddBundle.putString("slide5Heading" + (addContInstance + 1), slide5Heading[addContInstance]);
            slide5AddBundle.putStringArray("slide5Images" + (addContInstance + 1), slide5Images[addContInstance]);
            slide5AddBundle.putString("slide5VideoURL" + (addContInstance + 1), slide5VideoURL[addContInstance]);
        }
        // Add instance info to pass into the slides
        slide5AddBundle.putIntArray("addContentInstanceArray", extractAddContentInstanceArray(5));
        masterBundle.putBundle("slide5AddBundle", slide5AddBundle);
    }

    private void packSlide6(int numberOfInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide6Heading = new String[] {"Slide 6 Heading 1", "Slide 6 Heading 2"};
        String[] slide6BodyText = new String[] {"Medium Text Body for Description 1",
                "Medium Text Body for Description 2"};

        // Image info to be passed into activity
        String[] slide6StaticImage = new String[] {"ic_adventure", "ic_football"};

        // Image Names for Vertical Scrolling Image Panel
        String[][] slide6ScrollingImages = new String[2][];
        slide6ScrollingImages[0] = new String[5];
        slide6ScrollingImages[0][0] = "ic_chemistry";
        slide6ScrollingImages[0][1] = "ic_football";
        slide6ScrollingImages[0][2] = "ic_happypaint";
        slide6ScrollingImages[0][3] = "ic_mountain";
        slide6ScrollingImages[0][4] = "ic_quidditch";

        slide6ScrollingImages[1] = new String[4];
        slide6ScrollingImages[1][0] = "ic_quidditch";
        slide6ScrollingImages[1][1] = "ic_happypaint";
        slide6ScrollingImages[1][2] = "socbox_logo";
        slide6ScrollingImages[1][3] = "ic_mountain";

        int[] addContentScrollingImage = {0, 4};
        boolean[] addContentStaticImage = {true, false};

        // Audio info to be passed into activity
        Boolean[] slide6ActiveAudio = new Boolean[] {true, false};
        String[][] slide6Audio = new String[][] {
                {"http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3",
                        "https://www.elec.york.ac.uk/internal_web/meng/yr3/modules/Filters/Digital/sounds/breakfast.wav"},
                {null, null}
        };
        //******************************************************************************************

        int totAddContentInstances = extractAddContentInfo(6);
        if (totAddContentInstances > 0) packSlide6AddContent(totAddContentInstances);

        int[] slide6InstanceArray = extractSlideArray(6);

        // Bundle all media information, ready to be sent via Intent
        Bundle slide6Bundle = new Bundle();
        for (int slideInstance = 0; slideInstance < numberOfInstances; slideInstance++) {
            slide6Bundle.putString("slide6Heading" + (slideInstance+1), slide6Heading[slideInstance]);
            slide6Bundle.putString("slide6BodyText" + (slideInstance+1), slide6BodyText[slideInstance]);
            slide6Bundle.putString("slide6StaticImage" + (slideInstance + 1), slide6StaticImage[slideInstance]);
            slide6Bundle.putStringArray("slide6ScrollingImages" + (slideInstance + 1), slide6ScrollingImages[slideInstance]);
            slide6Bundle.putInt("addContentScrollingImage" + (slideInstance + 1),
                    addContentScrollingImage[slideInstance]);
            slide6Bundle.putBoolean("addContentStaticImage" + (slideInstance + 1),
                    addContentStaticImage[slideInstance]);
            slide6Bundle.putBoolean("slide6ActiveAudio" + (slideInstance + 1), slide6ActiveAudio[slideInstance]);
            if (slide6ActiveAudio[slideInstance]) {
                slide6Bundle.putStringArray("slide6Audio" + (slideInstance+1), slide6Audio[slideInstance]);
            }
        }
        // Add instance info to pass into the slides
        slide6Bundle.putIntArray("slide6InstanceArray", slide6InstanceArray);
        masterBundle.putBundle("slide6Bundle", slide6Bundle);
    }

    private void packSlide6AddContent(int totalAddContInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide6Heading = new String[]{"Slide 6 Add 1", "Slide 6 Add 2"};
        String[] slide6BodyText = new String[]{"Medium Text Body for Description 1",
                "Medium Text Body for Description 2"};

        // Image info to be passed into activity
        String[] slide6StaticImage = new String[]{"ic_adventure", "ic_football"};

        // Image Names for Vertical Scrolling Image Panel
        String[][] slide6ScrollingImages = new String[2][];
        slide6ScrollingImages[0] = new String[5];
        slide6ScrollingImages[0][0] = "ic_chemistry";
        slide6ScrollingImages[0][1] = "ic_football";
        slide6ScrollingImages[0][2] = "ic_happypaint";
        slide6ScrollingImages[0][3] = "ic_mountain";
        slide6ScrollingImages[0][4] = "ic_quidditch";

        slide6ScrollingImages[1] = new String[4];
        slide6ScrollingImages[1][0] = "ic_quidditch";
        slide6ScrollingImages[1][1] = "ic_happypaint";
        slide6ScrollingImages[1][2] = "socbox_logo";
        slide6ScrollingImages[1][3] = "ic_mountain";

        // Audio info to be passed into activity
        Boolean[] slide6ActiveAudio = new Boolean[]{true, false};
        String[][] slide6Audio = new String[][]{
                {"http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3",
                        "https://www.elec.york.ac.uk/internal_web/meng/yr3/modules/Filters/Digital/sounds/breakfast.wav"},
                {null, null}
        };
        //******************************************************************************************

        // Bundle all media information, ready to be sent via Intent
        Bundle slide6AddBundle = new Bundle();

        for (int addContInstance = 0; addContInstance < totalAddContInstances; addContInstance++) {
            slide6AddBundle.putString("slide6Heading" + (addContInstance + 1), slide6Heading[addContInstance]);
            slide6AddBundle.putString("slide6BodyText" + (addContInstance + 1), slide6BodyText[addContInstance]);
            slide6AddBundle.putString("slide6StaticImage" + (addContInstance + 1), slide6StaticImage[addContInstance]);
            slide6AddBundle.putStringArray("slide6ScrollingImages" + (addContInstance + 1), slide6ScrollingImages[addContInstance]);
            slide6AddBundle.putBoolean("slide6ActiveAudio" + (addContInstance + 1), slide6ActiveAudio[addContInstance]);
            if (slide6ActiveAudio[addContInstance]) {
                slide6AddBundle.putStringArray("slide6Audio" + (addContInstance + 1), slide6Audio[addContInstance]);
            }
            // Add instance info to pass into the slides
            slide6AddBundle.putIntArray("addContInstanceArray", extractAddContentInstanceArray(6));
            masterBundle.putBundle("slide6AddBundle", slide6AddBundle);
        }
    }

    private void packSlide7(int numberOfInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide7Heading = new String[] {"Slide 7 Heading 1", "Slide 7 Heading 2"};
        String[][] slide7ImageHeadings = new String[2][6];
        for (int slideInstance = 0; slideInstance < numberOfInstances; slideInstance++) {
            for (int i = 0; i < 6; i++) {
                slide7ImageHeadings[slideInstance][i] = "Inst. " + slideInstance +
                        ", Image " + (i + 1);
            }
        }

        // Audio info to be passed into activity
        Boolean[] slide7ActiveAudio = new Boolean[] {true, false};
        String[][] slide7Audio = new String[][] {
                {"http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3",
                        "https://www.elec.york.ac.uk/internal_web/meng/yr3/modules/Filters/Digital/sounds/breakfast.wav"},
                {null, null}
        };

        // Image info to be passed into activity
        // Static image names for image grid
        String[][] slide7Images = new String[2][6];
        slide7Images[0][0] = "ic_adventure";  // Top left
        slide7Images[0][1] = "ic_chemistry";  // Top right
        slide7Images[0][2] = "ic_football";   // Mid left
        slide7Images[0][3] = "ic_happypaint"; // Mid right
        slide7Images[0][4] = "ic_mountain";   // Bottom left
        slide7Images[0][5] = "ic_quidditch";  // Bottom right

        slide7Images[1][0] = "ic_quidditch";  // Top left
        slide7Images[1][1] = "ic_chemistry";  // Top right
        slide7Images[1][2] = "ic_mountain";   // Mid left
        slide7Images[1][3] = "ic_happypaint"; // Mid right
        slide7Images[1][4] = "ic_football";   // Bottom left
        slide7Images[1][5] = "ic_adventure";  // Bottom right

        int[] addContentStaticImages = {1, 6};

        //******************************************************************************************

        int totAddContentInstances = extractAddContentInfo(7);
        if (totAddContentInstances > 0) packSlide7AddContent(totAddContentInstances);

        int[] slide7InstanceArray = extractSlideArray(7);

        // Bundle all media information, ready to be sent via Intent
        Bundle slide7Bundle = new Bundle();
        for (int slideInstance = 0; slideInstance < numberOfInstances; slideInstance++) {
            slide7Bundle.putString("slide7Heading" + (slideInstance + 1), slide7Heading[slideInstance]);
            slide7Bundle.putStringArray("slide7ImageHeadings" + (slideInstance + 1), slide7ImageHeadings[slideInstance]);
            slide7Bundle.putStringArray("slide7Images" + (slideInstance + 1), slide7Images[slideInstance]);
            slide7Bundle.putInt("addContentStaticImages" + (slideInstance + 1),
                    addContentStaticImages[slideInstance]);
            slide7Bundle.putBoolean("slide7ActiveAudio" + (slideInstance + 1), slide7ActiveAudio[slideInstance]);
            if (slide7ActiveAudio[slideInstance]) {
                slide7Bundle.putStringArray("slide7Audio" + (slideInstance+1), slide7Audio[slideInstance]);
            }
        }
        // Add instance info to pass into the slides
        slide7Bundle.putIntArray("slide7InstanceArray", slide7InstanceArray);
        masterBundle.putBundle("slide7Bundle", slide7Bundle);
    }

    private void packSlide7AddContent(int totalAddContInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide7Heading = new String[] {"Slide 7 Add 1", "Slide 7 Add 2"};
        String[][] slide7ImageHeadings = new String[2][6];
        for (int slideInstance = 0; slideInstance < totalAddContInstances; slideInstance++) {
            for (int i = 0; i < 6; i++) {
                slide7ImageHeadings[slideInstance][i] = "Inst. " + slideInstance +
                        ", Image " + (i + 1);
            }
        }

        // Audio info to be passed into activity
        Boolean[] slide7ActiveAudio = new Boolean[] {true, false};
        String[][] slide7Audio = new String[][] {
                {"http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3",
                        "https://www.elec.york.ac.uk/internal_web/meng/yr3/modules/Filters/Digital/sounds/breakfast.wav"},
                {null, null}
        };

        // Image info to be passed into activity
        // Static image names for image grid
        String[][] slide7Images = new String[2][6];
        slide7Images[0][0] = "ic_adventure";  // Top left
        slide7Images[0][1] = "ic_chemistry";  // Top right
        slide7Images[0][2] = "ic_football";   // Mid left
        slide7Images[0][3] = "ic_happypaint"; // Mid right
        slide7Images[0][4] = "ic_mountain";   // Bottom left
        slide7Images[0][5] = "ic_quidditch";  // Bottom right

        slide7Images[1][0] = "ic_quidditch";  // Top left
        slide7Images[1][1] = "ic_chemistry";  // Top right
        slide7Images[1][2] = "ic_mountain";   // Mid left
        slide7Images[1][3] = "ic_happypaint"; // Mid right
        slide7Images[1][4] = "ic_football";   // Bottom left
        slide7Images[1][5] = "ic_adventure";  // Bottom right

        //******************************************************************************************

        // Bundle all media information, ready to be sent via Intent
        Bundle slide7AddBundle = new Bundle();

        for (int addContInstance = 0; addContInstance < totalAddContInstances; addContInstance++) {
            slide7AddBundle.putString("slide7Heading" + (addContInstance + 1), slide7Heading[addContInstance]);
            slide7AddBundle.putStringArray("slide7ImageHeadings" + (addContInstance + 1), slide7ImageHeadings[addContInstance]);
            slide7AddBundle.putStringArray("slide7Images" + (addContInstance + 1), slide7Images[addContInstance]);
            slide7AddBundle.putBoolean("slide7ActiveAudio" + (addContInstance + 1), slide7ActiveAudio[addContInstance]);
            if (slide7ActiveAudio[addContInstance]) {
                slide7AddBundle.putStringArray("slide7Audio" + (addContInstance+1), slide7Audio[addContInstance]);
            }
            // Add instance info to pass into the slides
            slide7AddBundle.putIntArray("addContentInstanceArray", extractAddContentInstanceArray(7));
            masterBundle.putBundle("slide7AddBundle", slide7AddBundle);
        }
    }

    private void packSlide8(int numberOfInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Image Names for Scrolling Image Panel
        String[][] slide8ScrollingImages = new String[2][];
        slide8ScrollingImages[0] = new String[6];
        slide8ScrollingImages[0][0] = "ic_adventure";
        slide8ScrollingImages[0][1] = "ic_chemistry";
        slide8ScrollingImages[0][2] = "ic_football";
        slide8ScrollingImages[0][3] = "ic_happypaint";
        slide8ScrollingImages[0][4] = "ic_mountain";
        slide8ScrollingImages[0][5] = "ic_quidditch";

        slide8ScrollingImages[1] = new String[4];
        slide8ScrollingImages[1][0] = "ic_quidditch";
        slide8ScrollingImages[1][1] = "ic_happypaint";
        slide8ScrollingImages[1][2] = "ic_football";
        slide8ScrollingImages[1][3] = "ic_chemistry";

        // Static Image Names
        String[][] slide8StaticImages = new String[2][2];
        slide8StaticImages[0][0] = "ic_adventure";  // Left
        slide8StaticImages[0][1] = "ic_chemistry";  // Right

        slide8StaticImages[1][0] = "ic_football";   // Left
        slide8StaticImages[1][1] = "ic_happypaint"; // Right

        // Video info to be passed into activity
        String[] slide8VideoURL = {"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"};

        int[] addContentStaticImages = {0, 2};
        int[] addContentScrollingImage = {5, 0};

        //******************************************************************************************

        int totAddContentInstances = extractAddContentInfo(8);
        if (totAddContentInstances > 0) packSlide8AddContent(totAddContentInstances);

        int[] slide8InstanceArray = extractSlideArray(8);

        // Bundle all media information, ready to be sent via Intent
        Bundle slide8Bundle = new Bundle();
        for (int slideInstance = 0; slideInstance < numberOfInstances; slideInstance++) {
            slide8Bundle.putStringArray("slide8ScrollingImages" + (slideInstance+1),
                    slide8ScrollingImages[slideInstance]);
            slide8Bundle.putStringArray("slide8StaticImages" + (slideInstance+1),
                    slide8StaticImages[slideInstance]);
            slide8Bundle.putInt("addContentStaticImages" + (slideInstance + 1),
                    addContentStaticImages[slideInstance]);
            slide8Bundle.putInt("addContentScrollingImage" + (slideInstance + 1),
                    addContentScrollingImage[slideInstance]);
            slide8Bundle.putString("slide8VideoURL" + (slideInstance + 1),
                    slide8VideoURL[slideInstance]);
        }
        // Add instance info to pass into the slides
        slide8Bundle.putIntArray("slide8InstanceArray", slide8InstanceArray);
        masterBundle.putBundle("slide8Bundle", slide8Bundle);
    }

    private void packSlide8AddContent(int totalAddContInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Image Names for Scrolling Image Panel
        String[][] slide8ScrollingImages = new String[2][];
        slide8ScrollingImages[0] = new String[6];
        slide8ScrollingImages[0][0] = "ic_adventure";
        slide8ScrollingImages[0][1] = "ic_chemistry";
        slide8ScrollingImages[0][2] = "ic_football";
        slide8ScrollingImages[0][3] = "ic_happypaint";
        slide8ScrollingImages[0][4] = "ic_mountain";
        slide8ScrollingImages[0][5] = "ic_quidditch";

        slide8ScrollingImages[1] = new String[4];
        slide8ScrollingImages[1][0] = "ic_quidditch";
        slide8ScrollingImages[1][1] = "ic_happypaint";
        slide8ScrollingImages[1][2] = "ic_football";
        slide8ScrollingImages[1][3] = "ic_chemistry";

        // Static Image Names
        String[][] slide8StaticImages = new String[2][2];
        slide8StaticImages[0][0] = "ic_adventure";  // Left
        slide8StaticImages[0][1] = "ic_chemistry";  // Right

        slide8StaticImages[1][0] = "ic_football";   // Left
        slide8StaticImages[1][1] = "ic_happypaint"; // Right

        // Video info to be passed into activity
        String[] slide8VideoURL = {"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"};

        //******************************************************************************************

        // Bundle all media information, ready to be sent via Intent
        Bundle slide8AddBundle = new Bundle();

        for (int addContInstance = 0; addContInstance < totalAddContInstances; addContInstance++) {
            slide8AddBundle.putStringArray("slide8ScrollingImages" + (addContInstance+1),
                    slide8ScrollingImages[addContInstance]);
            slide8AddBundle.putStringArray("slide8StaticImages" + (addContInstance + 1),
                    slide8StaticImages[addContInstance]);
            slide8AddBundle.putString("slide8VideoURL" + (addContInstance + 1),
                    slide8VideoURL[addContInstance]);
            // Add instance info to pass into the slides
            slide8AddBundle.putIntArray("addContentInstanceArray", extractAddContentInstanceArray(8));
            masterBundle.putBundle("slide8AddBundle", slide8AddBundle);
        }
    }

    private void packSlide9(int numberOfInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide9Headings = {"Slide 9 Heading 1", "Slide 9 Heading 2"};
        String[][] slide9ScrollHeadings = new String[numberOfInstances][3];

        for (int slideInstance = 0; slideInstance < numberOfInstances; slideInstance++) {
            for (int i = 0; i < 3; i++) {
                slide9ScrollHeadings[slideInstance][i] = "Instance: " + slideInstance +
                        ", Scroller " + (i + 1) + " Heading ";
            }
        }

        // Image Names for the top scrolling image panel
        String[][] slide9TopScrollingImages = new String[2][];
        slide9TopScrollingImages[0] = new String[6];
        slide9TopScrollingImages[0][0] = "ic_adventure";
        slide9TopScrollingImages[0][1] = "ic_chemistry";
        slide9TopScrollingImages[0][2] = "ic_football";
        slide9TopScrollingImages[0][3] = "ic_happypaint";
        slide9TopScrollingImages[0][4] = "ic_quidditch";
        slide9TopScrollingImages[0][5] = "ic_mountain";

        slide9TopScrollingImages[1] = new String[4];
        slide9TopScrollingImages[1][0] = "ic_happypaint";
        slide9TopScrollingImages[1][1] = "ic_football";
        slide9TopScrollingImages[1][2] = "ic_quidditch";
        slide9TopScrollingImages[1][3] = "socbox_logo";

        // Image Names for the middle scrolling image panel
        String[][] slide9MidScrollingImages = new String[2][];
        slide9MidScrollingImages[0] = new String[4];
        slide9MidScrollingImages[0][0] = "ic_adventure";
        slide9MidScrollingImages[0][1] = "ic_mountain";
        slide9MidScrollingImages[0][2] = "ic_quidditch";
        slide9MidScrollingImages[0][3] = "ic_chemistry";

        slide9MidScrollingImages[1] = new String[6];
        slide9MidScrollingImages[1][0] = "ic_quidditch";
        slide9MidScrollingImages[1][1] = "ic_mountain";
        slide9MidScrollingImages[1][2] = "ic_football";
        slide9MidScrollingImages[1][3] = "ic_chemistry";
        slide9MidScrollingImages[1][4] = "socbox_logo";
        slide9MidScrollingImages[1][5] = "ic_happypaint";

        // Image Names for the bottom scrolling image panel
        String[][] slide9BottomScrollingImages = new String[2][];
        slide9BottomScrollingImages[0] = new String[4];
        slide9BottomScrollingImages[0][0] = "socbox_logo";
        slide9BottomScrollingImages[0][1] = "ic_quidditch";
        slide9BottomScrollingImages[0][2] = "ic_mountain";
        slide9BottomScrollingImages[0][3] = "ic_chemistry";

        slide9BottomScrollingImages[1] = new String[5];
        slide9BottomScrollingImages[1][0] = "ic_football";
        slide9BottomScrollingImages[1][1] = "ic_adventure";
        slide9BottomScrollingImages[1][2] = "ic_mountain";
        slide9BottomScrollingImages[1][3] = "ic_chemistry";
        slide9BottomScrollingImages[1][4] = "ic_happypaint";

        // Audio info to be passed into activity
        Boolean[] slide9ActiveAudio = new Boolean[] {true, false};
        String[][] slide9Audio = new String[][] {
                {"http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3",
                        "https://www.elec.york.ac.uk/internal_web/meng/yr3/modules/Filters/Digital/sounds/breakfast.wav"},
                {null, null}
        };

        int[] addContentImageBottom = {0, 1};
        int[] addContentImageMid = {0, 0};
        int[] addContentImageTop = {6, 0};
        //******************************************************************************************

        int totAddContentInstances = extractAddContentInfo(9);
        if (totAddContentInstances > 0) packSlide9AddContent(totAddContentInstances);

        int[] slide9InstanceArray = extractSlideArray(9);

        // Bundle all media information, ready to be sent via Intent
        Bundle slide9Bundle = new Bundle();
        for (int slideInstance = 0; slideInstance < numberOfInstances; slideInstance++) {
            slide9Bundle.putString("slide9Heading" + (slideInstance+1), slide9Headings[slideInstance]);
            slide9Bundle.putStringArray("slide9ScrollHeadings" + (slideInstance+1),
                    slide9ScrollHeadings[slideInstance]);
            slide9Bundle.putStringArray("slide9TopScrollingImages" + (slideInstance + 1),
                    slide9TopScrollingImages[slideInstance]);
            slide9Bundle.putStringArray("slide9MidScrollingImages" + (slideInstance+1),
                    slide9MidScrollingImages[slideInstance]);
            slide9Bundle.putStringArray("slide9BottomScrollingImages" + (slideInstance+1),
                    slide9BottomScrollingImages[slideInstance]);
            slide9Bundle.putBoolean("slide9ActiveAudio" + (slideInstance + 1), slide9ActiveAudio[slideInstance]);
            if (slide9ActiveAudio[slideInstance]) {
                slide9Bundle.putStringArray("slide9Audio" + (slideInstance+1), slide9Audio[slideInstance]);
            }

            slide9Bundle.putInt("addContentImageBottom" + (slideInstance + 1),
                    addContentImageBottom[slideInstance]);
            slide9Bundle.putInt("addContentImageMid" + (slideInstance + 1),
                    addContentImageMid[slideInstance]);
            slide9Bundle.putInt("addContentImageTop" + (slideInstance + 1),
                    addContentImageTop[slideInstance]);
        }
        // Add instance info to pass into the slides
        slide9Bundle.putIntArray("slide9InstanceArray", slide9InstanceArray);
        masterBundle.putBundle("slide9Bundle", slide9Bundle);
    }

    private void packSlide9AddContent(int totalAddContInstances) {
        //***********---These are currently being hard-coded for testing purposes only---***********
        //**********---This will be changed with understanding of the server and parser---**********
        // Text info to be passed into activity
        String[] slide9Headings = {"Slide 9 Add 1", "Slide 9 Add 2"};
        String[][] slide9ScrollHeadings = new String[totalAddContInstances][3];

        for (int addContentInstance = 0; addContentInstance < totalAddContInstances; addContentInstance++) {
            for (int i = 0; i < 3; i++) {
                slide9ScrollHeadings[addContentInstance][i] = "Instance: " + addContentInstance +
                        ", Scroller " + (i + 1) + " Heading ";
            }
        }

        // Image Names for the top scrolling image panel
        String[][] slide9TopScrollingImages = new String[2][];
        slide9TopScrollingImages[0] = new String[6];
        slide9TopScrollingImages[0][0] = "ic_adventure";
        slide9TopScrollingImages[0][1] = "ic_chemistry";
        slide9TopScrollingImages[0][2] = "ic_football";
        slide9TopScrollingImages[0][3] = "ic_happypaint";
        slide9TopScrollingImages[0][4] = "ic_quidditch";
        slide9TopScrollingImages[0][5] = "ic_mountain";

        slide9TopScrollingImages[1] = new String[4];
        slide9TopScrollingImages[1][0] = "ic_happypaint";
        slide9TopScrollingImages[1][1] = "ic_football";
        slide9TopScrollingImages[1][2] = "ic_quidditch";
        slide9TopScrollingImages[1][3] = "socbox_logo";

        // Image Names for the middle scrolling image panel
        String[][] slide9MidScrollingImages = new String[2][];
        slide9MidScrollingImages[0] = new String[4];
        slide9MidScrollingImages[0][0] = "ic_adventure";
        slide9MidScrollingImages[0][1] = "ic_mountain";
        slide9MidScrollingImages[0][2] = "ic_quidditch";
        slide9MidScrollingImages[0][3] = "ic_chemistry";

        slide9MidScrollingImages[1] = new String[6];
        slide9MidScrollingImages[1][0] = "ic_quidditch";
        slide9MidScrollingImages[1][1] = "ic_mountain";
        slide9MidScrollingImages[1][2] = "ic_football";
        slide9MidScrollingImages[1][3] = "ic_chemistry";
        slide9MidScrollingImages[1][4] = "socbox_logo";
        slide9MidScrollingImages[1][5] = "ic_happypaint";

        // Image Names for the bottom scrolling image panel
        String[][] slide9BottomScrollingImages = new String[2][];
        slide9BottomScrollingImages[0] = new String[4];
        slide9BottomScrollingImages[0][0] = "socbox_logo";
        slide9BottomScrollingImages[0][1] = "ic_quidditch";
        slide9BottomScrollingImages[0][2] = "ic_mountain";
        slide9BottomScrollingImages[0][3] = "ic_chemistry";

        slide9BottomScrollingImages[1] = new String[5];
        slide9BottomScrollingImages[1][0] = "ic_football";
        slide9BottomScrollingImages[1][1] = "ic_adventure";
        slide9BottomScrollingImages[1][2] = "ic_mountain";
        slide9BottomScrollingImages[1][3] = "ic_chemistry";
        slide9BottomScrollingImages[1][4] = "ic_happypaint";

        // Audio info to be passed into activity
        Boolean[] slide9ActiveAudio = new Boolean[] {true, false};
        String[][] slide9Audio = new String[][] {
                {"http://android.programmerguru.com/wp-content/uploads/2013/04/hosannatelugu.mp3",
                        "https://www.elec.york.ac.uk/internal_web/meng/yr3/modules/Filters/Digital/sounds/breakfast.wav"},
                {null, null}
        };
        //******************************************************************************************

        // Bundle all media information, ready to be sent via Intent
        Bundle slide9AddBundle = new Bundle();

        for (int addContInstance = 0; addContInstance < totalAddContInstances; addContInstance++) {
            slide9AddBundle.putString("slide9Heading" + (addContInstance+1), slide9Headings[addContInstance]);
            slide9AddBundle.putStringArray("slide9ScrollHeadings" + (addContInstance+1),
                    slide9ScrollHeadings[addContInstance]);
            slide9AddBundle.putStringArray("slide9TopScrollingImages" + (addContInstance+1),
                    slide9TopScrollingImages[addContInstance]);
            slide9AddBundle.putStringArray("slide9MidScrollingImages" + (addContInstance+1),
                    slide9MidScrollingImages[addContInstance]);
            slide9AddBundle.putStringArray("slide9BottomScrollingImages" + (addContInstance+1),
                    slide9BottomScrollingImages[addContInstance]);
            slide9AddBundle.putBoolean("slide9ActiveAudio" + (addContInstance + 1), slide9ActiveAudio[addContInstance]);
            if (slide9ActiveAudio[addContInstance]) {
                slide9AddBundle.putStringArray("slide9Audio" + (addContInstance+1), slide9Audio[addContInstance]);
            }
        }
        // Add instance info to pass into the slides
        slide9AddBundle.putIntArray("addContentInstanceArray", extractAddContentInstanceArray(9));
        masterBundle.putBundle("slide9AddBundle", slide9AddBundle);
    }

    //--------------------------------------------------------------------------------------------//
    //----------------------------------------  onCreate  ----------------------------------------//
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation_slides);

        // This button sets up the presentation example for debugging and forms the basis of the
        //   final functionality of the the society presentations
        Button autoProgressButton = (Button)findViewById(R.id.auto_progress_button);
        autoProgressButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                examplePresentation();
            }
        });

        // These buttons are for testing and debugging purposes only, they display each slide
        //   individually
        Button slideOneButton = (Button)findViewById(R.id.slide_one_button);
        Button slideTwoButton = (Button)findViewById(R.id.slide_two_button);
        Button slideThreeButton = (Button)findViewById(R.id.slide_three_button);
        Button slideFourButton = (Button)findViewById(R.id.slide_four_button);
        Button slideFiveButton = (Button)findViewById(R.id.slide_five_button);
        Button slideSixButton = (Button)findViewById(R.id.slide_six_button);
        Button slideSevenButton = (Button)findViewById(R.id.slide_seven_button);
        Button slideEightButton = (Button)findViewById(R.id.slide_eight_button);
        Button slideNineButton = (Button)findViewById(R.id.slide_nine_button);

        slideOneButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                slide1ButtonPressed();
            }
        });

        slideTwoButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                slide2ButtonPressed();
            }
        });

        slideThreeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                slide3ButtonPressed();
            }
        });

        slideFourButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                slide4ButtonPressed();
            }
        });

        slideFiveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                slide5ButtonPressed();
            }
        });

        slideSixButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                slide6ButtonPressed();
            }
        });

        slideSevenButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                slide7ButtonPressed();

            }
        });

        slideEightButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                slide8ButtonPressed();
            }
        });

        slideNineButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                slide9ButtonPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_presentation_slides, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_home_menu:
                backHome();
                return true;
            case R.id.action_settings:
                //action for setting
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void backHome() {
        Intent backHome = new Intent(PresentationSlides.this, UserHome.class);
        startActivity(backHome);
    }
}