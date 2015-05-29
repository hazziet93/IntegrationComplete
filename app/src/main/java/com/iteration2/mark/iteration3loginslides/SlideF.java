package com.iteration2.mark.iteration3loginslides;

/*
Claritas: ‘Clarity through innovation’
Project: SocBox
Module: Presentation Slides
Code File Name: slide_f.java
Description: This is the sixth slide template for the presentation slides. It is comprised of a
             Heading, body of text, image, scrolling image panel and audio player which the
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
        import android.widget.LinearLayout;
        import android.widget.ScrollView;
        import android.widget.TextView;

        import java.util.Timer;
        import java.util.TimerTask;

public class SlideF extends ComPresSlideMethods {

    private String TAG = "SlideSixVertImageTextTAG";

    //--------------------------------------------------------------------------------------------//
    //----------------------------------  Slide Functionality  -----------------------------------//
    //--------------------------------------------------------------------------------------------//
    private Timer slideProgTimer;
    private int slideNumber;
    private boolean addContentSlide;
    private Context thisSlideContext = SlideF.this;

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
    public void onPause()
    {
        super.onPause();
        if (slideProgTimer != null) slideProgTimer.cancel();
        if (forwardScrollTimer != null) forwardScrollTimer.cancel();
        if (reverseScrollTimer != null) reverseScrollTimer.cancel();
        if (handler != null) handler.removeCallbacks(r);
    }

    //--------------------------------------------------------------------------------------------//
    //-------------------------------------  Image Handling  -------------------------------------//
    //--------------------------------------------------------------------------------------------//
    // Populates the slide with the required images
    public void addImages(String slide6StaticImage, String[] vertScrollImageTitles,
                          boolean addContentStaticImage, int addContentScrollingImage) {
        // Set the ImageView name and call method to embed the image within it.
        String imageViewName = "slide_6_image_view";
        embedImage(slide6StaticImage, imageViewName, slideNumber, addContentSlide, slideFuncBundle,
                masterBundle, thisSlideContext, addContentStaticImage);

        // Define the LinearLayout and HorizontalScrollView in which to embed each ImageView for the
        //   scrolling image panel
        final LinearLayout slide6VertScrollLayout = (LinearLayout)
                findViewById(R.id.slide_6_vert_scroll_layout);
        final ScrollView slide6VertScrollView = (ScrollView)
                findViewById(R.id.slide_6_vert_scroll_view);

        // Embed Images into the scrolling image panels
        embedScrollingImages(vertScrollImageTitles, slide6VertScrollLayout, addContentSlide,
                slideNumber, addContentScrollingImage, thisSlideContext, slideFuncBundle,
                masterBundle);

        // Add auto-scroll functionality to the scrolling image panels.
        autoScroll(slide6VertScrollLayout, slide6VertScrollView);
    }

    // Adds Auto-Scroll functionality to the scrolling image panels.
    public void autoScroll(final LinearLayout scrollLayout, final ScrollView scrollView)
    {
        // Use a new Handler to run the timer and give the necessary delay for the scrolling panel
        //   image views to settle.
        handler = new Handler();
        //handler.post(myRunnable);
        r = new Runnable() {
            public void run() {

                // Initialise timer for the initial scroll to the bottom
                forwardScrollTimer = new Timer();

                // Timer task to be run when both image panels have reached the end of their initial
                //   scroll. Canceling the timer and running the method for the following reverse
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
                        scrollView.scrollTo(0, (forwardScrollTimerValue / 3));
                    }
                };

                // Vertical scrolling image panel touch listener, to cancel auto scroll when
                //   selected by the user.
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
                forwardScrollTimer.schedule(endTimer, (scrollLayout.getBottom() * 3));
                forwardScrollTimer.schedule(scrollRight, 25, 25);
            }
        };
        handler.postDelayed(r, 400); // Initial delay to allow the imageViews in the panel to settle
    }

    // Adds the reverse Auto-Scroll functionality to the scrolling image panels.
    public void reverseScroll(final LinearLayout scrollLayout,
                              final ScrollView scrollView) {
        // Initialise timer for the reverse scroll to the top
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
        //   It decrements the scrollTo(y) value for each panel.
        TimerTask scrollLeft = new TimerTask() {
            int autoScrollTimerValue;
            @Override
            public void run() {
                autoScrollTimerValue = (autoScrollTimerValue - 25);
                scrollView.scrollTo(0, scrollLayout.getBottom() + (autoScrollTimerValue / 3));
            }
        };

        // Scrolling image panel touch listener, to cancel auto scroll when selected
        //   by the user.
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
        reverseScrollTimer.schedule(endTimer, (scrollLayout.getBottom() * 3));
        reverseScrollTimer.schedule(scrollLeft, 25, 25);
    }

    //--------------------------------------------------------------------------------------------//
    //----------------------------------------  onCreate  ----------------------------------------//
    //--------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_f);

        // Text variables
        String headingText = null;
        String bodyText = null;
        int headingSize = 30;
        int bodySize = 20;

        // Image variables
        String[] vertScrollImageTitles = new String[0];
        String slide6StaticImage = null;
        boolean addContentStaticImage = false;
        int addContentScrollingImage = 0;

        // Audio variables
        boolean audioActive = false;
        String[] audioAddresses = null;

        // Extract data from parent activity
        Bundle extras = getIntent().getExtras();
        Bundle slide6Bundle;
        Bundle slide6AddBundle;

        if (extras != null) {
            masterBundle = extras.getBundle("masterBundle");

            // Extract presentation data
            slideFuncBundle = masterBundle.getBundle("slideFuncBundle");

            auto = slideFuncBundle.getBoolean("autoProgress");
            slideNumber = slideFuncBundle.getInt("slideNumber");
            addContentSlide = slideFuncBundle.getBoolean("addContentSlide");

            if (addContentSlide) { // Additional content slide
                // Extract Slide 6 media
                slide6AddBundle = masterBundle.getBundle("slide6AddBundle");

                // Slide 6 Instance info
                int[] addContInstanceArray = slide6AddBundle.getIntArray("addContInstanceArray");
                int addContInstance = addContInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide6AddBundle.getString("slide6Heading" + addContInstance);
                bodyText = slide6AddBundle.getString("slide6BodyText" + addContInstance);

                // Obtain Image info from parent
                slide6StaticImage = slide6AddBundle.getString("slide6StaticImage" + addContInstance);
                vertScrollImageTitles = slide6AddBundle.getStringArray("slide6ScrollingImages" + addContInstance);

                // Obtain Audio info from parent
                audioActive = slide6AddBundle.getBoolean("slide6ActiveAudio" + addContInstance);
                if (audioActive)
                    audioAddresses = slide6AddBundle.getStringArray("slide6Audio" + addContInstance);
            }
            else {
                // Extract Slide 6 media
                slide6Bundle = masterBundle.getBundle("slide6Bundle");

                // Slide 6 Instance info
                int[] slideInstanceArray = slide6Bundle.getIntArray("slide6InstanceArray");
                int slideInstance = slideInstanceArray[slideNumber];

                // Obtain Text info from Parent
                headingText = slide6Bundle.getString("slide6Heading" + slideInstance);
                bodyText = slide6Bundle.getString("slide6BodyText" + slideInstance);

                // Obtain Image info from parent
                slide6StaticImage = slide6Bundle.getString("slide6StaticImage" + slideInstance);
                vertScrollImageTitles = slide6Bundle.getStringArray("slide6ScrollingImages" + slideInstance);

                // Obtain Audio info from parent
                audioActive = slide6Bundle.getBoolean("slide6ActiveAudio" + slideInstance);
                if (audioActive)
                    audioAddresses = slide6Bundle.getStringArray("slide6Audio" + slideInstance);

                addContentStaticImage = slide6Bundle.getBoolean("addContentStaticImage" + slideInstance);
                addContentScrollingImage = slide6Bundle.getInt("addContentScrollingImage" + slideInstance);
            }
        }
        else {
            Log.v(TAG, "ERROR: Slide not populated");
        }

        // Setup swipe gesture detector for manually changing slides
        gestureDetector = new GestureDetector(new SwipeGestureDetector());

        // Set TextView and embed the heading text received from the parent
        TextView slide6TextHeading = (TextView)findViewById(R.id.slide_6_text_heading);
        addText(slide6TextHeading, headingText, headingSize);

        // Set TextView and embed the text body received from the parent
        TextView slide6TextBody = (TextView)findViewById(R.id.slide_6_text_body);
        addText(slide6TextBody, bodyText, bodySize);

        // Populates the slide with the required images
        addImages(slide6StaticImage, vertScrollImageTitles, addContentStaticImage,
                addContentScrollingImage);

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
