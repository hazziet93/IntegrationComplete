package com.iteration2.mark.iteration3loginslides;

/*
Claritas: ‘Clarity through innovation’
Project: SocBox
Module: Presentation Slides
Code File Name: slide_i.java
Description: This is the ninth slide template for the presentation slides. It is comprised of a
             Heading, three scrolling image panels with headings and an audio player which the
             societies can chose to populate. This activity receives all media content from
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

public class SlideI extends ComPresSlideMethods {

    private String TAG = "SlideNineTriScrollerTAG";

    //--------------------------------------------------------------------------------------------//
    //----------------------------------  Slide Functionality  -----------------------------------//
    //--------------------------------------------------------------------------------------------//
    private boolean auto;
    private Timer slideProgTimer;
    private int slideNumber = 0;

    private Bundle masterBundle;
    private Bundle slideFuncBundle;

    protected Handler handler;
    protected Runnable r;
    protected Timer forwardScrollTimer;
    protected Timer reverseScrollTimer;

    private Context thisSlideContext = SlideI.this;

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
    //-------------------------------------  Text Handling  --------------------------------------//
    //--------------------------------------------------------------------------------------------//
    // Populates the slide with the required Text
    private void addTextElements(String headingText, String[] scrollerHeadings, int headingSize,
                                 int scrollHeadingSize) {
        // Set TextView and embed the heading text received from the parent.
        TextView slide9Heading = (TextView) findViewById(R.id.slide_9_heading);
        addText(slide9Heading, headingText, headingSize);

        // Set TextViews in which to embed scrolling image panels headings.
        TextView[] slide9ScrollHeadings = new TextView[3];
        slide9ScrollHeadings[0] = (TextView) findViewById(R.id.slide_9_scroll_1_heading);
        slide9ScrollHeadings[1] = (TextView) findViewById(R.id.slide_9_scroll_2_heading);
        slide9ScrollHeadings[2] = (TextView) findViewById(R.id.slide_9_scroll_3_heading);

        // Embed each of the image headings received from the parent into the defined ImageViews.
        for (int i = 0; i < 3; i++) {
            addText(slide9ScrollHeadings[i], scrollerHeadings[i], scrollHeadingSize);
        }
    }

    //--------------------------------------------------------------------------------------------//
    //-------------------------------------  Image Handling  -------------------------------------//
    //--------------------------------------------------------------------------------------------//
    // Populates the slide with the required images.
    public void addImages(String[] bottomScrollingImageTitles,String[] midScrollingImageTitles,
                          String[] topScrollingImageTitles, int addContentImageBottom,
                          int addContentImageMid, int addContentImageTop) {
        // Define the LinearLayouts in which to embed each ImageView for the scrolling image panels.
        final LinearLayout slide9ImageScroller1 = (LinearLayout)
                findViewById(R.id.slide_9_image_scroller_1);
        final LinearLayout slide9ImageScroller2 = (LinearLayout)
                findViewById(R.id.slide_9_image_scroller_2);
        final LinearLayout slide9ImageScroller3 = (LinearLayout)
                findViewById(R.id.slide_9_image_scroller_3);

        // Embed Images into the scrolling image panels.
        embedScrollingImages(bottomScrollingImageTitles, slide9ImageScroller3, addContentSlide,
                slideNumber, addContentImageBottom, thisSlideContext, slideFuncBundle, masterBundle);
        embedScrollingImages(midScrollingImageTitles, slide9ImageScroller2, addContentSlide,
                slideNumber, addContentImageMid, thisSlideContext, slideFuncBundle, masterBundle);
        embedScrollingImages(topScrollingImageTitles, slide9ImageScroller1, addContentSlide,
                slideNumber, addContentImageTop, thisSlideContext, slideFuncBundle, masterBundle);

        // Defines layouts and views used for the auto-scroll functionality and calls the method
        //   that adds the auto-scroll functionality to scrolling image panels.
        defineAutoScrollLayouts(slide9ImageScroller1, slide9ImageScroller2, slide9ImageScroller3);
    }

    // Defines layouts and views used for the auto-scroll functionality and calls the method
    //   that adds the auto-scroll functionality to scrolling image panels.
    public void defineAutoScrollLayouts(final LinearLayout slide9ImageScroller1,
                                        final LinearLayout slide9ImageScroller2,
                                        final LinearLayout slide9ImageScroller3) {

        // Define the HorizontalScrollViews in which to embed each ImageView for the scrolling image
        //   panels
        final HorizontalScrollView slide9TopScrollView = (HorizontalScrollView)
                findViewById(R.id.slide_9_top_scroll_view);
        final HorizontalScrollView slide9MidScrollView = (HorizontalScrollView)
                findViewById(R.id.slide_9_mid_scroll_view);
        final HorizontalScrollView slide9BottomScrollView = (HorizontalScrollView)
                findViewById(R.id.slide_9_bottom_scroll_view);

        // Use a new Handler to run the timer and give the necessary delay for the scrolling panel
        //   image views to settle.
        handler = new Handler();
        r = new Runnable() {
            public void run() {
                LinearLayout scrollLayoutLarge;
                LinearLayout scrollLayoutSmall1;
                HorizontalScrollView scrollViewLarge;
                HorizontalScrollView scrollViewSmall1;
                LinearLayout scrollLayoutSmall2;
                HorizontalScrollView scrollViewSmall2;

                if ((slide9ImageScroller1.getRight() > slide9ImageScroller2.getRight()) &&
                        (slide9ImageScroller1.getRight() > slide9ImageScroller3.getRight())) {
                    scrollLayoutLarge = slide9ImageScroller1;
                    scrollLayoutSmall1 = slide9ImageScroller2;
                    scrollLayoutSmall2 = slide9ImageScroller3;
                    scrollViewLarge = slide9TopScrollView;
                    scrollViewSmall1 = slide9MidScrollView;
                    scrollViewSmall2 = slide9BottomScrollView;
                }
                else if ((slide9ImageScroller2.getRight() > slide9ImageScroller1.getRight()) &&
                        (slide9ImageScroller2.getRight() > slide9ImageScroller3.getRight())) {
                    scrollLayoutLarge = slide9ImageScroller2;
                    scrollLayoutSmall1 = slide9ImageScroller1;
                    scrollLayoutSmall2 = slide9ImageScroller3;
                    scrollViewLarge = slide9MidScrollView;
                    scrollViewSmall1 = slide9TopScrollView;
                    scrollViewSmall2 = slide9BottomScrollView;
                }
                else {
                    scrollLayoutLarge = slide9ImageScroller3;
                    scrollLayoutSmall1 = slide9ImageScroller1;
                    scrollLayoutSmall2 = slide9ImageScroller2;
                    scrollViewLarge = slide9BottomScrollView;
                    scrollViewSmall1 = slide9TopScrollView;
                    scrollViewSmall2 = slide9MidScrollView;
                }

                autoScroll(scrollLayoutLarge, scrollLayoutSmall1, scrollLayoutSmall2,
                        scrollViewLarge, scrollViewSmall1, scrollViewSmall2);
            }
        };
        handler.postDelayed(r, 400); // Initial delay to allow the imageViews in the panel to settle
    }


    // Adds Auto-Scroll functionality to the scrolling image panels.
    public void autoScroll(final LinearLayout scrollLayoutLarge,
                           final LinearLayout scrollLayoutSmall1,
                           final LinearLayout scrollLayoutSmall2,
                           final HorizontalScrollView scrollViewLarge,
                           final HorizontalScrollView scrollViewSmall1,
                           final HorizontalScrollView scrollViewSmall2) {
        // Initialise timer for the initial scroll to the right ->
        forwardScrollTimer = new Timer();

        // Timer task to be run when both image panels have reached the end of their initial
        //   scroll. Canceling the timer and running the method for the following reverse
        //   scroll.
        TimerTask endTimer = new TimerTask() {
            @Override
            public void run() {
                forwardScrollTimer.cancel();
                reverseScroll(scrollLayoutLarge, scrollLayoutSmall1, scrollLayoutSmall2,
                        scrollViewLarge, scrollViewSmall1, scrollViewSmall2);
            }
        };

        // Timer task to be run every 25ms, allowing for a smooth scroll.
        //   It changes the scrollTo(x) value incrementally for each panel.
        TimerTask scrollRight = new TimerTask() {
            int forwardScrollTimerValue;
            @Override
            public void run() {
                forwardScrollTimerValue = (forwardScrollTimerValue + 25);
                scrollViewSmall1.scrollTo((forwardScrollTimerValue / 3), 0);
                scrollViewSmall2.scrollTo((forwardScrollTimerValue / 3), 0);
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

        // Middle scrolling image panel touch listener, to cancel auto scroll when selected
        //   by the user.
        scrollLayoutSmall1.setOnTouchListener(new View.OnTouchListener() {
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
        scrollLayoutSmall2.setOnTouchListener(new View.OnTouchListener() {
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

    // Adds the reverse Auto-Scroll functionality to the scrolling image panels.
    public void reverseScroll(final LinearLayout scrollLayoutLarge,
                              final LinearLayout scrollLayoutSmall1,
                              final LinearLayout scrollLayoutSmall2,
                              final HorizontalScrollView scrollViewLarge,
                              final HorizontalScrollView scrollViewSmall1,
                              final HorizontalScrollView scrollViewSmall2) {
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
                scrollViewSmall1.scrollTo(scrollLayoutSmall1.getRight() +
                        (autoScrollTimerValue / 3), 0);
                scrollViewSmall2.scrollTo(scrollLayoutSmall2.getRight() +
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

        // Middle scrolling image panel touch listener, to cancel auto scroll when selected
        //   by the user.
        scrollLayoutSmall1.setOnTouchListener(new View.OnTouchListener() {
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
        scrollLayoutSmall2.setOnTouchListener(new View.OnTouchListener() {
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
        setContentView(R.layout.slide_i);

        // Text variables
        String headingText = null;
        String[] scrollerHeadings = new String[3];
        int headingSize = 30;
        int scrollHeadingSize = 20;

        // Image variables
        String[] topScrollingImageTitles = null;
        String[] midScrollingImageTitles = null;
        String[] bottomScrollingImageTitles = null;
        int addContentImageBottom = 0;
        int addContentImageMid = 0;
        int addContentImageTop = 0;

        // Audio variables
        boolean audioActive = false;
        String[] audioAddresses = null;

        // Extract data from parent activity
        Bundle extras = getIntent().getExtras();
        Bundle slide9Bundle;
        Bundle slide9AddBundle;

        if (extras != null) {
            masterBundle = extras.getBundle("masterBundle");

            // Extract presentation data
            slideFuncBundle = masterBundle.getBundle("slideFuncBundle");

            auto = slideFuncBundle.getBoolean("autoProgress");
            slideNumber = slideFuncBundle.getInt("slideNumber");
            addContentSlide = slideFuncBundle.getBoolean("addContentSlide");

            if (addContentSlide) { // Additional content slide
                slide9AddBundle = masterBundle.getBundle("slide9AddBundle");

                int[] addContentInstanceArray = slide9AddBundle.getIntArray("addContentInstanceArray");
                int addContentInstance = addContentInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide9AddBundle.getString("slide9Heading" + addContentInstance);
                scrollerHeadings = slide9AddBundle.getStringArray("slide9ScrollHeadings" +
                        addContentInstance);

                // Obtain Image info from parent
                topScrollingImageTitles = slide9AddBundle.getStringArray("slide9TopScrollingImages" +
                        addContentInstance);
                midScrollingImageTitles = slide9AddBundle.getStringArray("slide9MidScrollingImages" +
                        addContentInstance);
                bottomScrollingImageTitles = slide9AddBundle.getStringArray(
                        "slide9BottomScrollingImages" + addContentInstance);

                // Obtain Audio info from parent
                audioActive = slide9AddBundle.getBoolean("slide9ActiveAudio" + addContentInstance);
                if (audioActive)
                    audioAddresses = slide9AddBundle.getStringArray("slide9Audio" + addContentInstance);

            }
            else {
                // Extract Slide 9 media
                slide9Bundle = masterBundle.getBundle("slide9Bundle");

                // Slide 9 Instance info
                int[] slideInstanceArray = slide9Bundle.getIntArray("slide9InstanceArray");
                int slideInstance = slideInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide9Bundle.getString("slide9Heading" + slideInstance);
                scrollerHeadings = slide9Bundle.getStringArray("slide9ScrollHeadings" +
                        slideInstance);

                // Obtain Image info from parent
                topScrollingImageTitles = slide9Bundle.getStringArray("slide9TopScrollingImages" +
                        slideInstance);
                midScrollingImageTitles = slide9Bundle.getStringArray("slide9MidScrollingImages" +
                        slideInstance);
                bottomScrollingImageTitles = slide9Bundle.getStringArray(
                        "slide9BottomScrollingImages" + slideInstance);

                // Obtain Audio info from parent
                audioActive = slide9Bundle.getBoolean("slide9ActiveAudio" + slideInstance);
                if (audioActive)
                    audioAddresses = slide9Bundle.getStringArray("slide9Audio" + slideInstance);

                addContentImageBottom = slide9Bundle.getInt("addContentImageBottom" + slideInstance);
                addContentImageMid = slide9Bundle.getInt("addContentImageMid" + slideInstance);
                addContentImageTop = slide9Bundle.getInt("addContentImageTop" + slideInstance);
            }
        }
        else {
            Log.v(TAG, "ERROR: Slide not populated");
        }

        // Setup swipe gesture detector for manually changing slides
        gestureDetector = new GestureDetector(new SwipeGestureDetector());

        // Populates the slide with the required text
        addTextElements(headingText, scrollerHeadings, headingSize, scrollHeadingSize);

        // Populates the slide with the required images
        addImages(bottomScrollingImageTitles, midScrollingImageTitles, topScrollingImageTitles,
                addContentImageBottom, addContentImageMid, addContentImageTop);

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