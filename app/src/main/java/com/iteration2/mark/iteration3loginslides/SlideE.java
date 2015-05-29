package com.iteration2.mark.iteration3loginslides;
/*
Claritas: ‘Clarity through innovation’
Project: SocBox
Module: Presentation Slides
Code File Name: slide_e.java
Description: This is the fifth slide template for the presentation slides. It is comprised of a
             Heading, video and four images which the societies can chose to populate. This activity
             receives all media content from PresentationSlides.java and populates itself with the
             information.
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
        import android.content.res.Configuration;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.GestureDetector;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.SeekBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.Timer;
        import java.util.TimerTask;

public class SlideE extends ComPresSlideMethods implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private String TAG = "SlideFiveQuadImageVideoTAG";

    //--------------------------------------------------------------------------------------------//
    //----------------------------------  Slide Functionality  -----------------------------------//
    //--------------------------------------------------------------------------------------------//
    private Timer slideProgTimer;
    private int slideNumber = 0;
    private boolean addContentSlide;

    private Context thisSlideContext = SlideE.this;

    private Bundle slideFuncBundle;
    private Bundle masterBundle; // For manual slide transitions
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
    //-------------------------------------  Video Handling  -------------------------------------//
    //--------------------------------------------------------------------------------------------//
    public String videoURL;
    public VideoHandler videoHandler;
    boolean isChecked = true;

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPlay:
//                playPause = (playPause) ? false : true;
                videoHandler.setPlayPause();
//                if (!playPause) {runTimer();}
//                else {timer.cancel();}
                break;
            case R.id.buttonStop:
                videoHandler.deletePlayer();
                videoHandler.loadVideoSource();
                videoHandler.position = 0;
//                videoHandler.resetPlayer();
                Log.v(TAG, "Player start");
                break;
            case R.id.buttonMute:
                videoHandler.setMute();
                break;
            case R.id.buttonFullScreen:
                videoHandler.toggleFullScreen();
                Log.v(TAG, "Player fullscreen");
                break;
            case R.id.seekBarMedia:
                Log.v(TAG, "Player seekBar");
                break;
            case R.id.surface:
                if (isChecked) {
                    /**
                     * Remove the controller from the screen.
                     */
                    videoHandler.container.setVisibility(View.GONE);
                    isChecked = false;
                } else {
                    /**
                     * Show the controller on screen.
                     */
                    videoHandler.container.setVisibility(View.VISIBLE);
                    isChecked = true;
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

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        TextView slide5TextView = (TextView) findViewById(R.id.slide_5_heading);
        LinearLayout slide5ImageLayout = (LinearLayout) findViewById(R.id.slide_5_image_layout);
        ImageView slide5Image1 = (ImageView) findViewById(R.id.slide_5_image_1);
        ImageView slide5Image2 = (ImageView) findViewById(R.id.slide_5_image_2);
        ImageView slide5Image3 = (ImageView) findViewById(R.id.slide_5_image_3);
        ImageView slide5Image4 = (ImageView) findViewById(R.id.slide_5_image_4);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            slide5TextView.setVisibility(View.GONE);
            slide5ImageLayout.setVisibility(View.GONE);
            slide5Image1.setVisibility(View.GONE);
            slide5Image2.setVisibility(View.GONE);
            slide5Image3.setVisibility(View.GONE);
            slide5Image4.setVisibility(View.GONE);
            actionBar.hide();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            slide5TextView.setVisibility(View.VISIBLE);
            slide5ImageLayout.setVisibility(View.VISIBLE);
            slide5Image1.setVisibility(View.VISIBLE);
            slide5Image2.setVisibility(View.VISIBLE);
            slide5Image3.setVisibility(View.VISIBLE);
            slide5Image4.setVisibility(View.VISIBLE);
            actionBar.show();

        }
    }

    //--------------------------------------------------------------------------------------------//
    //----------------------------------------  onCreate  ----------------------------------------//
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_e);

        // Functional variables
        boolean auto = false;

        // Text variables
        String headingText = null;
        int headingSize = 30;

        // Image variables
        String[] imageTitles = new String[4];
        int addContentStaticImages = 0;

        // Extract data from parent activity
        Bundle extras = getIntent().getExtras();
        Bundle slide5Bundle;
        Bundle slide5AddBundle;

        if (extras != null) {
            masterBundle = extras.getBundle("masterBundle");

            // Extract presentation data
            slideFuncBundle = masterBundle.getBundle("slideFuncBundle");
            auto = slideFuncBundle.getBoolean("autoProgress");
            slideNumber = slideFuncBundle.getInt("slideNumber");
            addContentSlide = slideFuncBundle.getBoolean("addContentSlide");

            if (addContentSlide) { // Additional content slide
                slide5AddBundle = masterBundle.getBundle("slide5AddBundle");

                int[] addContentInstanceArray = slide5AddBundle.getIntArray("addContentInstanceArray");
                int addContentInstance = addContentInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide5AddBundle.getString("slide5Heading" + addContentInstance);

                // Obtain Image info from parent
                imageTitles = slide5AddBundle.getStringArray("slide5Images" + addContentInstance);

                // Obtain Video info from parent
                videoURL = slide5AddBundle.getString("slide5VideoURL" + addContentInstance);
            }
            else {
                // Extract Slide 5 media
                slide5Bundle = masterBundle.getBundle("slide5Bundle");

                // Slide 5 Instance info
                int[] slideInstanceArray = slide5Bundle.getIntArray("slide5InstanceArray");
                int slideInstance = slideInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide5Bundle.getString("slide5Heading" + slideInstance);

                // Obtain Image info from parent
                imageTitles = slide5Bundle.getStringArray("slide5Images" + slideInstance);
                addContentStaticImages = slide5Bundle.getInt("addContentStaticImages" + slideInstance);

                // Obtain Video info from parent
                videoURL = slide5Bundle.getString("slide5VideoURL" + slideInstance);
            }
        } else {
            Log.v(TAG, "ERROR: Slide not populated");
        }

        // Setup swipe gesture detector for manually changing slides
        gestureDetector = new GestureDetector(new SwipeGestureDetector());

        // Run timer for when the slides are in Automatic Mode
        if (auto){
            runTimer(0);
        }

        // Set TextView and embed the heading text received from the parent
        TextView slide5TextHeading = (TextView) findViewById(R.id.slide_5_heading);
        addText(slide5TextHeading, headingText, headingSize);

        // Set the beginning of the imageViewName, allowing the images to be embedded in a loop.
        String imageViewName = "slide_5_image_";

        // Embed all of the images for the slide
        embedMultipleImages(imageTitles, imageViewName, addContentStaticImages,
                addContentSlide, slideNumber, thisSlideContext, slideFuncBundle, masterBundle);

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
