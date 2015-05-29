package com.iteration2.mark.iteration3loginslides;

/*
Claritas: ‘Clarity through innovation’
Project: SocBox
Module: Presentation Slides
Code File Name: slide_g.java
Description: This is the seventh slide template for the presentation slides. It is comprised of a
             Heading, six images with individual headings and an audio player which the societies
             can chose to populate. This activity receives all media content from
             PresentationSlides.java and populates itself with the information.
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
Tag: U/PS/01/01 (partial)
Requirement: The slides can have 9 possible layouts.
Tag: U/PS/02/01 (partial)
Requirement: The user can view video, images, and text embedded within the slides.
Tag: U/PS/04/01 (partial)
Requirement: The user can choose to progress through the slideshow manually or automatically.
Tag: U/PS/05/01 (partial)
Requirement: The user can access additional content via linked text and images.
Tag: U/PS/06/01 (partial)
Requirement: The user can control the audio and video on these slides.
Tag: U/PS/07/01 (partial)
Requirement: The user will progress to the relevant society’s home page upon completion of the
             slideshow.
Tag: U/PS/08/01 (partial)
Requirement: The user can restart or exit the slideshow at any point.
Tag: U/PS/09/01 (partial)
Requirement: By visiting an additional content slide the user will pause the media in the previous
             slide.
Other Information:
Note: This file is still work in progress.
 Todo: See PresentationSlides.java for general todos
*/


        import android.content.Context;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.GestureDetector;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.MotionEvent;
        import android.widget.TextView;

        import java.util.Timer;
        import java.util.TimerTask;

public class SlideG extends ComPresSlideMethods {

    private String TAG = "SlideSevenImageGridTAG";

    //--------------------------------------------------------------------------------------------//
    //----------------------------------  Slide Functionality  -----------------------------------//
    //--------------------------------------------------------------------------------------------//
    private int slideNumber = 0;
    private Timer slideProgTimer;

    private boolean addContentSlide;
    private Context thisSlideContext = SlideG.this;

    private Bundle slideFuncBundle;
    private Bundle masterBundle;

    // For manual slide transitions
    private GestureDetector gestureDetector;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    // Private class for gestures
    private class SwipeGestureDetector
            extends GestureDetector.SimpleOnGestureListener {
        // Swipe properties, you can change it to make the swipe
        // longer or shorter and speed
        private static final int SWIPE_MINIMUM_DISTANCE = 120;
        private static final int SWIPE_VARIANCE = 200;
        private static final int SWIPE_SPEED_LIMIT = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_VARIANCE)
                    return false;

                // Left swipe
                if (diff > SWIPE_MINIMUM_DISTANCE
                        && Math.abs(velocityX) > SWIPE_SPEED_LIMIT) {
                    onLeftSwipe(addContentSlide, slideNumber, slideFuncBundle,
                            masterBundle, thisSlideContext);

                    // Right swipe
                } else if (-diff > SWIPE_MINIMUM_DISTANCE
                        && Math.abs(velocityX) > SWIPE_SPEED_LIMIT) {
                    onRightSwipe(addContentSlide, slideNumber, slideFuncBundle,
                            masterBundle, thisSlideContext);
                }
            } catch (Exception e) {
                Log.e("YourActivity", "Error on gestures");
            }
            return false;
        }
    }

    // Run the Slide Progression Timer.
    public void runTimer(int addTime){
        if (slideProgTimer == null) {
            slideProgTimer = new Timer();

            // Execute TimerTask nextSlideProgTask when timer finishes.
            slideProgTimer.schedule(nextSlideProgTask, 7500 + addTime);
        }
    }

    // When the Slide Progression Timer finishes, progress to the next slide.
    private TimerTask nextSlideProgTask = new TimerTask() {
        @Override
        public void run() {
            int nextSlide;

            if (!addContentSlide) {
                nextSlide = calcNextSlide(slideNumber, slideFuncBundle,
                        masterBundle);
            }
            else {
                nextSlide = slideOrder[slideNumber];
            }
            slideFuncBundle.putBoolean("addContentSlide", false);
            masterBundle.putBundle("slideFuncBundle", slideFuncBundle);
            ChangeSlide(nextSlide, thisSlideContext, masterBundle);
        }};

    // Stop slide progression when the slide is manually changed while in auto mode
    @Override
    public void onStop()
    {
        super.onStop();
        if (slideProgTimer != null) slideProgTimer.cancel();
    }

    //--------------------------------------------------------------------------------------------//
    //-------------------------------------  Text Handling  --------------------------------------//
    //--------------------------------------------------------------------------------------------//
    // Populates the slide with the required Text
    public void addTextElements(String headingText, String[] imageHeadings, int headingSize,
                                int imageHeadingSize) {
        // Set TextView and embed the heading text received from the parent
        TextView slide7Heading = (TextView)findViewById(R.id.slide_7_heading);
        addText(slide7Heading, headingText, headingSize);

        // Set TextViews in which to embed image headings
        TextView[] slide7ImageHeadingViews = new TextView[6];
        slide7ImageHeadingViews[0] = (TextView)findViewById(R.id.slide_7_image_title_1);
        slide7ImageHeadingViews[1] = (TextView)findViewById(R.id.slide_7_image_title_2);
        slide7ImageHeadingViews[2] = (TextView)findViewById(R.id.slide_7_image_title_3);
        slide7ImageHeadingViews[3] = (TextView)findViewById(R.id.slide_7_image_title_4);
        slide7ImageHeadingViews[4] = (TextView)findViewById(R.id.slide_7_image_title_5);
        slide7ImageHeadingViews[5] = (TextView)findViewById(R.id.slide_7_image_title_6);

        // Embed each of the image headings received from the parent into the defined ImageViews
        for (int i=0; i< 6; i++) {
            addText(slide7ImageHeadingViews[i], imageHeadings[i], imageHeadingSize);
        }
    }

    //--------------------------------------------------------------------------------------------//
    //----------------------------------------  onCreate  ----------------------------------------//
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_g);

        boolean auto = false;

        // Text variables
        String headingText = null;
        String[] imageHeadings = new String[6];
        int headingSize = 30;
        int imageHeadingSize = 20;

        // Image variables
        String[] imageTitles = new String[6];
        int addContentStaticImages = 0;

        // Audio variables
        boolean audioActive = false;
        String[] audioAddresses = null;

        // Extract data from parent activity
        Bundle extras = getIntent().getExtras();
        Bundle slide7Bundle;
        Bundle slide7AddBundle;

        if (extras != null) {
            masterBundle = extras.getBundle("masterBundle");

            // Extract presentation data
            slideFuncBundle = masterBundle.getBundle("slideFuncBundle");
            auto = slideFuncBundle.getBoolean("autoProgress");
            slideNumber = slideFuncBundle.getInt("slideNumber");
            addContentSlide = slideFuncBundle.getBoolean("addContentSlide");

            if (addContentSlide) { // Additional content slide
                slide7AddBundle = masterBundle.getBundle("slide7AddBundle");

                int[] addContentInstanceArray = slide7AddBundle.getIntArray("addContentInstanceArray");
                int addContentInstance = addContentInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide7AddBundle.getString("slide7Heading" + addContentInstance);
                imageHeadings = slide7AddBundle.getStringArray("slide7ImageHeadings" + addContentInstance);

                // Obtain Image info from parent
                imageTitles = slide7AddBundle.getStringArray("slide7Images" + addContentInstance);

                // Obtain Audio info from parent
                audioActive = slide7AddBundle.getBoolean("slide7ActiveAudio" + addContentInstance);
                if (audioActive)
                    audioAddresses = slide7AddBundle.getStringArray("slide7Audio" + addContentInstance);
            }
            else {
                // Extract Slide 7 media
                slide7Bundle = masterBundle.getBundle("slide7Bundle");

                // Slide 7 Instance info
                int[] slideInstanceArray = slide7Bundle.getIntArray("slide7InstanceArray");
                int slideInstance = slideInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide7Bundle.getString("slide7Heading" + slideInstance);
                imageHeadings = slide7Bundle.getStringArray("slide7ImageHeadings" + slideInstance);

                // Obtain Image info from parent
                imageTitles = slide7Bundle.getStringArray("slide7Images" + slideInstance);

                // Obtain Audio info from parent
                audioActive = slide7Bundle.getBoolean("slide7ActiveAudio" + slideInstance);
                if (audioActive)
                    audioAddresses = slide7Bundle.getStringArray("slide7Audio" + slideInstance);

                addContentStaticImages = slide7Bundle.getInt("addContentStaticImages" + slideInstance);
            }
        }
        else {
            Log.v(TAG, "ERROR: Slide not populated");
        }

        // Run timer for when the slides are in Automatic Mode
        if (auto){
            runTimer(0);
        }

        // Setup swipe gesture detector for manually changing slides
        gestureDetector = new GestureDetector(new SwipeGestureDetector());

        // Populates the slide with the required Text
        addTextElements(headingText, imageHeadings, headingSize, imageHeadingSize);

        // Set the beginning of the imageViewName, allowing the images to be embedded in a loop.
        String imageViewName = "slide_7_image_";

        // Embed all of the images for the slide
        embedMultipleImages(imageTitles, imageViewName, addContentStaticImages,
                addContentSlide, slideNumber, thisSlideContext, slideFuncBundle, masterBundle);

        // Activate AudioHandler if required.
        activateAudioPlayer(audioActive, audioAddresses);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}