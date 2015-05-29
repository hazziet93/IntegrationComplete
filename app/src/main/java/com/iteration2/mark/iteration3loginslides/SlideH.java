package com.iteration2.mark.iteration3loginslides;

/*
Claritas: ‘Clarity through innovation’
Project: SocBox
Module: Presentation Slides
Code File Name: slide_h.java
Description: This is the eighth slide template for the presentation slides. It is comprised of two
             images, a video and a scrolling image panel which the societies can chose to populate.
             This activity receives all media content from PresentationSlides.java and populates
             itself with the information.
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
Tag: U/PS/03/01 (partial)
Requirement: The user will be able to view panels with auto-scroll capabilities.
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
        import android.os.Handler;
        import android.util.Log;
        import android.view.GestureDetector;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.HorizontalScrollView;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.SeekBar;
        import android.widget.Toast;

        import java.util.Timer;
        import java.util.TimerTask;

public class SlideH extends ComPresSlideMethods implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {

    private String TAG = "SlideEightVideoScrollerImagesTAG";

    //--------------------------------------------------------------------------------------------//
    //----------------------------------  Slide Functionality  -----------------------------------//
    //--------------------------------------------------------------------------------------------//

    private int slideNumber = 0;
    private Timer slideProgTimer;
    private boolean addContentSlide;
    private Context thisSlideContext = SlideH.this;

    private Bundle slideFuncBundle;
    private Bundle masterBundle;

    protected boolean auto;
    protected Handler handler;
    protected Runnable r;
    protected Timer forwardScrollTimer;
    protected Timer reverseScrollTimer;

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
        if (forwardScrollTimer != null) forwardScrollTimer.cancel();
        if (reverseScrollTimer != null) reverseScrollTimer.cancel();
        if (handler != null) handler.removeCallbacks(r);
    }

    //--------------------------------------------------------------------------------------------//
    //-------------------------------------  Image Handling  -------------------------------------//
    //--------------------------------------------------------------------------------------------//
    // Populates the slide with the required images.
    public void addImages(String[] staticImageTitles, String[] scrollingImageTitles,
                          int addContentStaticImages, int addContentScrollingImage) {
        // Set the beginning of the imageViewName, allowing the images to be embedded in a loop.
        String imageViewName = "slide_8_image_";
        // Define the LinearLayouts in which to embed each ImageViews
        final LinearLayout slide8scrollLayout = (LinearLayout)
                findViewById(R.id.slide_8_scroll_layout);
        final HorizontalScrollView slide8scrollView = (HorizontalScrollView)
                findViewById(R.id.slide_8_scroll_view);

        embedMultipleImages(staticImageTitles, imageViewName, addContentStaticImages,
                addContentSlide, slideNumber, thisSlideContext, slideFuncBundle, masterBundle);
        embedScrollingImages(scrollingImageTitles, slide8scrollLayout, addContentSlide,
                slideNumber, addContentScrollingImage, thisSlideContext, slideFuncBundle,
                masterBundle);

        // Add Auto-Scroll Functionality to scrolling image panels.
        autoScroll(slide8scrollLayout, slide8scrollView);
    }

    // Adds Auto-Scroll functionality to the scrolling image panels.
    public void autoScroll(final LinearLayout scrollLayout, final HorizontalScrollView scrollView)
    {
        // Use a new Handler to run the timer and give the necessary delay for the scrolling panel
        //   image views to settle.
        handler = new Handler();
        r = new Runnable() {
            public void run() {
                // Initialise timer for the initial scroll to the right ->
                forwardScrollTimer = new Timer();

                // Timer task to be run when both image panels have reached the end of their initial
                //   scroll. Cancels the timer and running the method for the following reverse
                //   scroll.
                TimerTask endTimer = new TimerTask() {
                    @Override
                    public void run() {
                        forwardScrollTimer.cancel();
                        reverseScroll(scrollLayout, scrollView);
                    }
                };

                // Timer task to be run every 25ms, allowing for a smooth scroll.
                //   It changes the scrollTo(x) value incrementally for each panel.
                TimerTask scrollRight = new TimerTask() {
                    int forwardScrollTimerValue;
                    @Override
                    public void run() {
                        forwardScrollTimerValue = (forwardScrollTimerValue + 25);
                        scrollView.scrollTo((forwardScrollTimerValue / 3), 0);
                    }
                };

                // Scrolling image panel touch listener, to cancel auto scroll when selected
                //   by the user.
                scrollLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        forwardScrollTimer.cancel();
                        if (auto) {
                            runTimer(8000);
                        }
                        return true;
                    }
                });

                // Calling each of the above timer tasks.
                forwardScrollTimer.schedule(endTimer, (scrollLayout.getRight() * 3));
                forwardScrollTimer.schedule(scrollRight, 25, 25);
            }
        };
        handler.postDelayed(r, 400); // Initial delay to allow the imageViews in the panel to settle
    }

    // Adds the reverse Auto-Scroll functionality to the scrolling image panels.
    public void reverseScroll(final LinearLayout scrollLayout,
                              final HorizontalScrollView scrollView) {
        // Initialise timer for the reverse scroll to the left <-
        reverseScrollTimer = new Timer();

        // Timer task to be run when both image panels have reached the end of their second scroll.
        //   Canceling the timer and allowing the slide transition timer to start.
        TimerTask endTimer = new TimerTask() {
            @Override
            public void run() {
                reverseScrollTimer.cancel();
                if (auto) {
                    runTimer(0);
                }
            }
        };

        // Timer task to be run every 25ms, allowing for a smooth scroll.
        //   It decrements the scrollTo(x) value for each panel.
        TimerTask scrollLeft = new TimerTask() {
            int autoScrollTimerValue;
            @Override
            public void run() {
                autoScrollTimerValue = (autoScrollTimerValue - 25);
                scrollView.scrollTo(scrollLayout.getRight() +
                        (autoScrollTimerValue / 3), 0);
            }
        };

        // Scrolling image panel touch listener, to cancel auto scroll when selected
        //   by by the user.
        scrollLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                reverseScrollTimer.cancel();
                if (auto) {
                    runTimer(5000);
                }
                return true;
            }
        });

        // Calling each of the above timer tasks.
        reverseScrollTimer.schedule(endTimer, (scrollLayout.getRight() * 3));
        reverseScrollTimer.schedule(scrollLeft, 25, 25);
    }

    //--------------------------------------------------------------------------------------------//
    //-------------------------------------  Video Handling  -------------------------------------//
    //--------------------------------------------------------------------------------------------//
    public String videoURL;
    public VideoHandler videoHandler;
    boolean isChecked = true;

    // Methods for Video Handler
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

        LinearLayout slide8ImageLayout = (LinearLayout) findViewById(R.id.slide_8_image_layout);
        ImageView slide8Image1 = (ImageView) findViewById(R.id.slide_8_image_1);
        ImageView slide8Image2 = (ImageView) findViewById(R.id.slide_8_image_2);
        HorizontalScrollView slide8ScrollView = (HorizontalScrollView) findViewById(R.id.slide_8_scroll_view);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            slide8ImageLayout.setVisibility(View.GONE);
            slide8Image1.setVisibility(View.GONE);
            slide8Image2.setVisibility(View.GONE);
            slide8ScrollView.setVisibility(View.GONE);
            actionBar.hide();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            slide8ImageLayout.setVisibility(View.VISIBLE);
            slide8Image1.setVisibility(View.VISIBLE);
            slide8Image2.setVisibility(View.VISIBLE);
            slide8ScrollView.setVisibility(View.VISIBLE);
            actionBar.show();

        }
    }

    //--------------------------------------------------------------------------------------------//
    //----------------------------------------  onCreate  ----------------------------------------//
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_h);

        // Image variables
        String[] staticImageTitles = null;
        String[] scrollingImageTitles = null;
        int addContentStaticImages = 0;
        int addContentScrollingImage = 0;

        // Extract data from parent activity
        Bundle extras = getIntent().getExtras();
        Bundle slide8Bundle;
        Bundle slide8AddBundle;

        if (extras != null) {
            masterBundle = extras.getBundle("masterBundle");

            // Extract presentation data
            slideFuncBundle = masterBundle.getBundle("slideFuncBundle");
            auto = slideFuncBundle.getBoolean("autoProgress");
            slideNumber = slideFuncBundle.getInt("slideNumber");
            addContentSlide = slideFuncBundle.getBoolean("addContentSlide");

            if (addContentSlide) { // Additional content slide
                slide8AddBundle = masterBundle.getBundle("slide8AddBundle");

                int[] addContentInstanceArray = slide8AddBundle.getIntArray("addContentInstanceArray");
                int addContentInstance = addContentInstanceArray[slideNumber];

                // Obtain Image info from parent
                staticImageTitles = slide8AddBundle.getStringArray("slide8StaticImages" +
                        addContentInstance);
                scrollingImageTitles = slide8AddBundle.getStringArray("slide8ScrollingImages" +
                        addContentInstance);

                // Obtain Video info from parent
                videoURL = slide8AddBundle.getString("slide8VideoURL" + addContentInstance);
            }
            else {
                // Extract Slide 8 media
                slide8Bundle = masterBundle.getBundle("slide8Bundle");

                // Slide 8 Instance info
                int[] slideInstanceArray = slide8Bundle.getIntArray("slide8InstanceArray");
                int slideInstance = slideInstanceArray[slideNumber];

                // Obtain Image info from parent
                staticImageTitles = slide8Bundle.getStringArray("slide8StaticImages" +
                        slideInstance);
                scrollingImageTitles = slide8Bundle.getStringArray("slide8ScrollingImages" +
                        slideInstance);

                addContentStaticImages = slide8Bundle.getInt("addContentStaticImages" +
                        slideInstance);
                addContentScrollingImage = slide8Bundle.getInt("addContentScrollingImage" +
                        slideInstance);

                // Obtain Video info from parent
                videoURL = slide8Bundle.getString("slide8VideoURL" + slideInstance);
            }
        }
        else {
            Log.v(TAG, "ERROR: Slide not populated");
        }

        // Setup swipe gesture detector for manually changing slides
        gestureDetector = new GestureDetector(new SwipeGestureDetector());

        // Populates the slide with the required images
        addImages(staticImageTitles, scrollingImageTitles, addContentStaticImages,
                addContentScrollingImage);

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