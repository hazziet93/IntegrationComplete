package com.iteration2.mark.iteration3loginslides;

/*
Claritas: ‘Clarity through innovation’
Project: SocBox
Module: Presentation Slides
Code File Name: slide_d.java
Description: This is the fourth slide template for the presentation slides. It is comprised of a
             Heading or small body of text, two scrolling image panels and an audio player which
             the societies can chose to populate. This activity receives all media content from
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
Tag: U/PS/03/01 (partial)
Requirement: The user will be able to view panels with auto-scroll capabilities.
Tag: U/PS/04/01 (partial)
Requirement: The user can choose to progress through the slideshow manually or automatically.
Tag: U/PS/05/01 (partial)
Requirement: The user can access additional content via linked text and images.
Tag: U/PS/07/01 (partial)
Requirement: The user will progress to the relevant society’s home page upon completion of the
             slideshow.
Tag: U/PS/08/01 (partial)
Requirement: The user can restart or exit the slideshow at any point.
Other Information:
Note: This file is still work in progress.
 Todo: See PresentationSlides.java for general todos
*/

        import android.content.Context;
        import android.os.Bundle;
        import android.os.Handler;
        import android.util.Log;
        import android.view.GestureDetector;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.HorizontalScrollView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import java.util.Timer;
        import java.util.TimerTask;

public class SlideD extends ComPresSlideMethods {

    private String TAG = "SlideFourDualImageScrollTAG";

    //--------------------------------------------------------------------------------------------//
    //----------------------------------  Slide Functionality  -----------------------------------//
    //--------------------------------------------------------------------------------------------//
    private Bundle masterBundle;
    private Bundle slideFuncBundle;

    private int slideNumber = 0;
    private Timer slideProgTimer;
    private Context thisSlideContext = SlideD.this;

    // Additional Content Variables
    private boolean addContentSlide;

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
    public void addImages(String[] bottomScrollingImageTitles, String[] topScrollingImageTitles,
                          int addContentImageBottom, int addContentImageTop) {
        // Define the LinearLayouts and HorizontalScrollViews in which to embed each of the
        //   ImageViews and to add the auto-scroll functionality.
        final LinearLayout slide4TopScrollLayout = (LinearLayout)
                findViewById(R.id.slide_4_top_scroll_layout);
        final LinearLayout slide4BottomScrollLayout = (LinearLayout)
                findViewById(R.id.slide_4_bottom_scroll_layout);

        final HorizontalScrollView slide4TopScrollView = (HorizontalScrollView)
                findViewById(R.id.slide_4_top_scroll_view);
        final HorizontalScrollView slide4BottomScrollView = (HorizontalScrollView)
                findViewById(R.id.slide_4_bottom_scroll_view);

        // Populate the scrolling image panel frames with the selected images.
        embedScrollingImages(bottomScrollingImageTitles, slide4BottomScrollLayout, addContentSlide,
                slideNumber, addContentImageBottom, thisSlideContext, slideFuncBundle, masterBundle);
        embedScrollingImages(topScrollingImageTitles, slide4TopScrollLayout, addContentSlide,
                slideNumber, addContentImageTop, thisSlideContext, slideFuncBundle, masterBundle);

        // Add Auto-Scroll Functionality to scrolling image panels.
        autoScroll(slide4TopScrollLayout, slide4BottomScrollLayout, slide4TopScrollView,
                slide4BottomScrollView);
    }


    // Adds Auto-Scroll functionality to the scrolling image panels.
    public void autoScroll(final LinearLayout scrollLayout1, final LinearLayout scrollLayout2,
                           final HorizontalScrollView scrollView1,
                           final HorizontalScrollView scrollView2)
    {
        // Use a new Handler to run the timer and give the necessary delay for the scrolling panel
        //   image views to settle.
        handler = new Handler();
        r = new Runnable() {
            public void run() {

                // Define the layouts by the total widths of the scrolling panels in relation to
                //   each other, allowing auto scroll to work for any size of panel.
                final LinearLayout scrollLayoutLarge = (scrollLayout1.getRight() >
                        scrollLayout2.getRight()) ? scrollLayout1 : scrollLayout2;
                final LinearLayout scrollLayoutSmall = (scrollLayout1.getRight() >
                        scrollLayout2.getRight()) ? scrollLayout2 : scrollLayout1;
                final HorizontalScrollView scrollViewLarge = (scrollLayout1.getRight() >
                        scrollLayout2.getRight()) ? scrollView1 : scrollView2;
                final HorizontalScrollView scrollViewSmall = (scrollLayout1.getRight() >
                        scrollLayout2.getRight()) ? scrollView2 : scrollView1;

                // Initialise timer for the initial scroll to the right ->
                forwardScrollTimer = new Timer();

                // Timer task to be run when both image panels have reached the end of their initial
                //   scroll. Canceling the timer and running the method for the following reverse
                //   scroll.
                TimerTask endTimer = new TimerTask() {
                    @Override
                    public void run() {
                        forwardScrollTimer.cancel();
                        reverseScroll(scrollLayoutLarge, scrollLayoutSmall, scrollViewLarge,
                                scrollViewSmall);
                    }
                };

                // Timer task to be run every 25ms, allowing for a smooth scroll.
                //   It changes the scrollTo(x) value incrementally for each panel.
                TimerTask scrollRight = new TimerTask() {
                    int forwardScrollTimerValue;
                    @Override
                    public void run() {
                        forwardScrollTimerValue = (forwardScrollTimerValue + 25);
                        scrollViewSmall.scrollTo((forwardScrollTimerValue / 3), 0);
                        scrollViewLarge.scrollTo((forwardScrollTimerValue / 3), 0);
                    }
                };

                // Top scrolling image panel touch listener, to cancel auto scroll when selected
                //   by the user.
                scrollLayoutLarge.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        forwardScrollTimer.cancel();
                        if (auto) {
                            runTimer(8000);
                        }
                        return true;
                    }
                });

                // Bottom scrolling image panel touch listener, to cancel auto scroll when selected
                //   by the user.
                scrollLayoutSmall.setOnTouchListener(new View.OnTouchListener() {
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
                forwardScrollTimer.schedule(endTimer, (scrollLayoutLarge.getRight() * 3));
                forwardScrollTimer.schedule(scrollRight, 25, 25);
            }
        };
        handler.postDelayed(r, 400); // Initial delay to allow the imageViews in the panel to settle
    }

    // Adds the reverse Auto-Scroll functionality to the scrolling image panels.
    public void reverseScroll(final LinearLayout scrollLayoutLarge,
                              final LinearLayout scrollLayoutSmall,
                              final HorizontalScrollView scrollViewLarge,
                              final HorizontalScrollView scrollViewSmall) {
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
                scrollViewSmall.scrollTo(scrollLayoutSmall.getRight() +
                        (autoScrollTimerValue / 3), 0);
                scrollViewLarge.scrollTo(scrollLayoutLarge.getRight() +
                        (autoScrollTimerValue / 3), 0);
            }
        };

        // Top scrolling image panel touch listener, to cancel auto scroll when selected
        //   by the user.
        scrollLayoutLarge.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                reverseScrollTimer.cancel();
                if (auto) {
                    runTimer(5000);
                }
                return true;
            }
        });

        // Bottom scrolling image panel touch listener, to cancel auto scroll when selected
        //   by the user.
        scrollLayoutSmall.setOnTouchListener(new View.OnTouchListener() {
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
        reverseScrollTimer.schedule(endTimer, (scrollLayoutLarge.getRight() * 3));
        reverseScrollTimer.schedule(scrollLeft, 25, 25);
    }

    //--------------------------------------------------------------------------------------------//
    //----------------------------------------  onCreate  ----------------------------------------//
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_d);

        // Text variables
        String headingText = null;
        int headingSize = 25;

        // Image variables
        String[] topScrollingImageTitles = null;
        String[] bottomScrollingImageTitles = null;
        int addContentImageTop = 0;
        int addContentImageBottom = 0;

        // Extract data from parent activity
        Bundle extras = getIntent().getExtras();
        Bundle slide4Bundle;
        Bundle slide4AddBundle;

        if (extras != null) {
            masterBundle = extras.getBundle("masterBundle");

            // Extract presentation data
            slideFuncBundle = masterBundle.getBundle("slideFuncBundle");

            auto = slideFuncBundle.getBoolean("autoProgress");
            slideNumber = slideFuncBundle.getInt("slideNumber");
            addContentSlide = slideFuncBundle.getBoolean("addContentSlide");

            if (addContentSlide) { // Additional content slide
                slide4AddBundle = masterBundle.getBundle("slide4AddBundle");

                int[] addContentInstanceArray = slide4AddBundle.getIntArray("addContentInstanceArray");
                int addContentInstance = addContentInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide4AddBundle.getString("slide4Heading" + addContentInstance);

                // Obtain Image info from parent
                topScrollingImageTitles = slide4AddBundle.getStringArray("slide4TopScrollImages"
                        + addContentInstance);
                bottomScrollingImageTitles = slide4AddBundle.getStringArray("slide4BottomScrollImages"
                        + addContentInstance);
            }
            else {
                // Extract Slide 4 media
                slide4Bundle = masterBundle.getBundle("slide4Bundle");

                // Slide 4 Instance info
                int[] slideInstanceArray = slide4Bundle.getIntArray("slide4InstanceArray");
                int slideInstance = slideInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide4Bundle.getString("slide4Heading" + slideInstance);

                // Obtain Image info from parent
                topScrollingImageTitles = slide4Bundle.getStringArray("slide4TopScrollImages"
                        + slideInstance);
                bottomScrollingImageTitles = slide4Bundle.getStringArray("slide4BottomScrollImages"
                        + slideInstance);

                addContentImageBottom = slide4Bundle.getInt("addContentImageBottom" + slideInstance);
                addContentImageTop = slide4Bundle.getInt("addContentImageTop" + slideInstance);
            }
        } else {
            Log.v(TAG, "ERROR: Slide not populated");
        }

        // Setup swipe gesture detector for manually changing slides
        gestureDetector = new GestureDetector(new SwipeGestureDetector());

        // Set TextView and embed the heading text received from the parent.
        TextView slide4TextView = (TextView) findViewById(R.id.slide_4_middle_text);
        addText(slide4TextView, headingText, headingSize);

        // Populates the slide with the required images.
        addImages(bottomScrollingImageTitles, topScrollingImageTitles, addContentImageBottom,
                addContentImageTop);
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