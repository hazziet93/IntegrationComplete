package com.iteration2.mark.iteration3loginslides;

/*
Claritas: ‘Clarity through innovation’
Project: SocBox
Module: Presentation Slides
Code File Name: slide_b.java
Description: This is the second slide template for the presentation slides. It is comprised of a
             Heading, image, body of text and audio player which the societies can chose to
             populate. This activity receives all media content from PresentationSlides.java and
             populates itself with the information.
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
Tag: U/PS/09/01
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

public class SlideB extends ComPresSlideMethods {

    private String TAG = "SlideTwoImageTextTAG";

    //--------------------------------------------------------------------------------------------//
    //----------------------------------  Slide Functionality  -----------------------------------//
    //--------------------------------------------------------------------------------------------//
    private Timer slideProgTimer;

    private int slideNumber = 0;
    private Context thisSlideContext = SlideB.this;
    private Bundle slideFuncBundle = null;
    private Bundle masterBundle = null;

    // Additional Content Variables
    private boolean addContentSlide;

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
    private void runTimer(int addTime){
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

//        if (audioHandler.mediaPlayer.isPlaying()) {
//            audioHandler.mediaPlayer.stop();
//            audioHandler.mediaPlayer.release();
//            audioHandler.mediaPlayer = null;
//        }
    }

    //--------------------------------------------------------------------------------------------//
    //----------------------------------------  onCreate  ----------------------------------------//
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_b);

        // Functional variables
        boolean auto = false;

        // Text variables
        String headingText = null;
        String bodyText = null;

        // Image variables
        String imageTitle = null;

        // Audio variables
        boolean audioActive = false;
        String[] audioAddresses = null;

        // Extract data from parent activity
        Bundle extras = getIntent().getExtras();
        Bundle slide2Bundle;
        Bundle slide2AddBundle;

        if (extras != null) {
            masterBundle = extras.getBundle("masterBundle");

            // Extract presentation data
            slideFuncBundle = masterBundle.getBundle("slideFuncBundle");

            auto = slideFuncBundle.getBoolean("autoProgress");
            slideNumber = slideFuncBundle.getInt("slideNumber");
            addContentSlide = slideFuncBundle.getBoolean("addContentSlide");

            if (addContentSlide) { // Additional content slide
                slide2AddBundle = masterBundle.getBundle("slide2AddBundle");

                int[] addContentInstanceArray = slide2AddBundle.getIntArray("addContentInstanceArray");
                int addContentInstance = addContentInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide2AddBundle.getString("slide2Heading" + addContentInstance);
                bodyText = slide2AddBundle.getString("slide2TextBody" + addContentInstance);

                // Obtain Image info from parent
                imageTitle = slide2AddBundle.getString("slide2Image" + addContentInstance);

                // Obtain Audio info from parent
                audioActive = slide2AddBundle.getBoolean("slide2ActiveAudio" + addContentInstance);
                if (audioActive)
                    audioAddresses = slide2AddBundle.getStringArray("slide2Audio" + addContentInstance);
            }
            else { // Normal presentation slide
                // Extract Slide 2 media
                slide2Bundle = masterBundle.getBundle("slide2Bundle");

                // Slide 2 Instance info
                int[] slideInstanceArray = slide2Bundle.getIntArray("slide2InstanceArray");
                int slideInstance = slideInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide2Bundle.getString("slide2Heading" + slideInstance);
                bodyText = slide2Bundle.getString("slide2TextBody" + slideInstance);

                // Obtain Image info from parent
                imageTitle = slide2Bundle.getString("slide2Image" + slideInstance);

                // Obtain Audio info from parent
                audioActive = slide2Bundle.getBoolean("slide2ActiveAudio" + slideInstance);
                if (audioActive)
                    audioAddresses = slide2Bundle.getStringArray("slide2Audio" + slideInstance);
            }
        }
        else {
            Log.v(TAG, "ERROR: Slide not populated");
        }

        // Setup swipe gesture detector for manually changing slides
        gestureDetector = new GestureDetector(new SwipeGestureDetector());

        // Run timer for when the slides are in Automatic Mode
        if (auto){
            runTimer(0);
        }

        // Set TextView and embed the heading text received from the parent
        int headingSize = 25;
        TextView slide2TextHeading = (TextView)findViewById(R.id.slide_2_heading);
        addText(slide2TextHeading, headingText, headingSize);

        // Set TextView and embed the text body received from the parent
        int bodySize = 15;
        TextView slide2TextBody = (TextView)findViewById(R.id.slide_2_large_text_body);
        addText(slide2TextBody, bodyText, bodySize);

        // Set the ImageView name and call method to embed the image within it.
        String imageViewName = "slide_2_image_view";
        embedImage(imageTitle, imageViewName, slideNumber, addContentSlide, slideFuncBundle,
                masterBundle, thisSlideContext, true);

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
        switch (item.getItemId()){

        }

        return super.onOptionsItemSelected(item);
    }
}
