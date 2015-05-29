package com.iteration2.mark.iteration3loginslides;
/*
Claritas: ‘Clarity through innovation’
Project: SocBox
Module: Presentation Slides
Code File Name: slide_a.java
Description: This is the first slide template for the presentation slides. It is comprised of a
             Heading and Video which the societies can chose. This activity receives all media
             content from PresentationSlides.java and populates itself with the information.
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
        import android.content.res.Configuration;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.GestureDetector;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.SeekBar;
        import android.widget.Toast;

        import java.util.Timer;
        import java.util.TimerTask;

public class SlideA extends ComPresSlideMethods implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private String TAG = "SlideOneLargeVideoTAG";

    //--------------------------------------------------------------------------------------------//
    //----------------------------------  Slide Functionality  -----------------------------------//
    //--------------------------------------------------------------------------------------------//
    private Timer slideProgTimer;
    private int slideNumber = 0;
    private Context thisSlideContext = SlideA.this;
    private Bundle slideFuncBundle = null;
    private Bundle masterBundle;

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
    }

    //--------------------------------------------------------------------------------------------//
    //-------------------------------------  Video Handling  -------------------------------------//
    //--------------------------------------------------------------------------------------------//
    // Variables for video
    public String videoURL; // URL for the requested video
    public VideoHandler videoHandler;
    boolean isChecked = true;

    // onClick listener for the buttons on the video controller panel.
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.buttonPlay :
                videoHandler.setPlayPause();
                break;
            case R.id.buttonStop :
                videoHandler.deletePlayer();
                videoHandler.loadVideoSource();
                videoHandler.position = 0;
                Log.v(TAG, "Player start");
                break;
            case R.id.buttonMute :
                videoHandler.setMute();
                break;
            case R.id.buttonFullScreen :
                videoHandler.toggleFullScreen();
                Log.v(TAG, "Player fullscreen");
                break;
            case R.id.seekBarMedia :
                Log.v(TAG, "Player seekBar");
                break;
            case R.id.surface :
                if (isChecked){
                    //Remove the controller from the screen.
                    videoHandler.container.setVisibility(View.GONE);
                    isChecked = false;
                } else{
                    // Show the controller on screen.
                    videoHandler.container.setVisibility(View.VISIBLE);
                    isChecked=true;
                }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        videoHandler.onStartTrackingTouch();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        videoHandler.onProgressChanged(seekBar, progress, fromUser);
    }

    // When user stops moving the progress handler
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        videoHandler.onStopTrackingTouch();
    }

    // Implement full-screen on slide by removing the other elements on the slide
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

//        TextView slide1TextView = (TextView) findViewById(R.id.heading_1);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            // Hide the Heading and actionbar to allow complete full-screen mode
//            slide1TextView.setVisibility(View.GONE);
            actionBar.hide();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            // Bring back the Heading and actionbar when full-screen mode ends.
//            slide1TextView.setVisibility(View.VISIBLE);
            actionBar.show();
        }
    }

    //--------------------------------------------------------------------------------------------//
    //----------------------------------------  onCreate  ----------------------------------------//
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_a);

        // Functional variables
        boolean auto = false;

        // Text variables
        String headingText = null;
        int headingSize = 30;

        // Extract data from parent activity
        Bundle extras = getIntent().getExtras();
        Bundle slide1Bundle;
        Bundle slide1AddBundle;

        if (extras != null) {
            masterBundle = extras.getBundle("masterBundle");

            // Extract presentation data
            slideFuncBundle = masterBundle.getBundle("slideFuncBundle");
            auto = slideFuncBundle.getBoolean("autoProgress");
            slideNumber = slideFuncBundle.getInt("slideNumber");
            addContentSlide = slideFuncBundle.getBoolean("addContentSlide");

            if (addContentSlide) { // Additional content slide
                slide1AddBundle = masterBundle.getBundle("slide1AddBundle");

                int[] addContentInstanceArray = slide1AddBundle.getIntArray("addContentInstanceArray");
                int addContentInstance = addContentInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide1AddBundle.getString("slide1Heading" + addContentInstance);

                // Obtain Video info from parent
                videoURL = slide1AddBundle.getString("slide1VideoURL" + addContentInstance);
            }
            else {
                // Extract Slide 1 media
                slide1Bundle = masterBundle.getBundle("slide1Bundle");

                // Slide 2 Instance info
                int[] slideInstanceArray = slide1Bundle.getIntArray("slide1InstanceArray");
                int slideInstance = slideInstanceArray[slideNumber];
                Log.v(TAG, "slideInstance: " + slideInstance);

                // Obtain Text info from Parent
                headingText = slide1Bundle.getString("slide1Heading" + slideInstance);

                // Obtain Video info from parent
                videoURL = slide1Bundle.getString("slide1VideoURL" + slideInstance);
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

        // Set TextView and embed the text received from the parent
//        TextView slide1TextView = (TextView) findViewById(R.id.slide_1_heading);
//        addText(slide1TextView, headingText, headingSize);

        // Call Video Handler
        videoHandler = new VideoHandler(this, videoURL, auto);
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