package com.iteration2.mark.iteration3loginslides;

        import android.content.Context;
        import android.content.Intent;
        import android.content.res.Resources;
        import android.graphics.drawable.BitmapDrawable;
        import android.graphics.drawable.Drawable;
        import android.graphics.drawable.LayerDrawable;
        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import java.util.ArrayList;

public class ComPresSlideMethods extends PresentationSlides {

    public void onLeftSwipe(boolean addContentSlide, int slideNumber,
                            Bundle slideFuncBundle, Bundle masterBundle, Context currentSlide) {
        if (!addContentSlide)
            ChangeSlide(calcNextSlide(slideNumber, slideFuncBundle, masterBundle), currentSlide,
                    masterBundle);
    }

    public void onRightSwipe(boolean addContentSlide, int slideNumber,
                             Bundle slideFuncBundle, Bundle masterBundle, Context currentSlide) {
        int previousSlide;
        if (!addContentSlide) {
            previousSlide = calcPreviousSlide(slideNumber, slideFuncBundle, masterBundle);
        }
        else {
            //
            previousSlide = slideOrder[slideNumber];
        }
        slideFuncBundle.putBoolean("addContentSlide", false);
        masterBundle.putBundle("slideFuncBundle", slideFuncBundle);
        ChangeSlide(previousSlide, currentSlide, masterBundle);
    }

    public int calcNextSlide(int slideNumber, Bundle slideFuncBundle, Bundle masterBundle)
    {
        int nextSlide;
        // If the current slide is the final one in the presentation, return to the parent activity
        if (slideNumber == slideOrder.length - 1) {
            nextSlide = 0;
        }
        // Otherwise increment the slideNumber and set the next slide to progress to?
        else {
            slideNumber++;
            nextSlide = slideOrder[slideNumber];

            slideFuncBundle.putInt("slideNumber", slideNumber);
            masterBundle.putBundle("slideFuncBundle", slideFuncBundle);
        }
        return nextSlide;
    }

    public int calcPreviousSlide(int slideNumber, Bundle slideFuncBundle, Bundle masterBundle)
    {
        int previousSlide;
        // If the current slide is the final one in the presentation, return to the parent activity
        if (slideNumber == 0) {
            previousSlide = 0;
        }
        // Otherwise increment the slideNumber and set the next slide to progress to?
        else {
            slideNumber--;
            previousSlide = slideOrder[slideNumber];
            slideFuncBundle.putInt("slideNumber", slideNumber);
            masterBundle.putBundle("slideFuncBundle", slideFuncBundle);
        }
        return previousSlide;
    }

    // Generic method used to change slides.
    public void ChangeSlide(int nextSlideInt, Context currentSlide, Bundle masterBundle) {
        // Convert the string for the next activity name into type Class.
        String nextSlideActivity = thisPackage + "." + activityTitles[nextSlideInt];
        Class<?> nextSlideClass = null;
        try {
            nextSlideClass = Class.forName(nextSlideActivity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Exit to the front page
        Intent nextSlideIntent = new Intent(currentSlide, nextSlideClass);
        nextSlideIntent.putExtra("masterBundle", masterBundle);
        startActivity(nextSlideIntent);
        this.overridePendingTransition(R.anim.pres_slides_next_enter, R.anim.pres_slides_next_leave);
    }

    //--------------------------------------------------------------------------------------------//
    //-------------------------------------  Text Handling  --------------------------------------//
    //--------------------------------------------------------------------------------------------//
    // Text Method, embedding
    public void addText(TextView textView, String text, int textSize) {
        TextHandler textHandler;
        // Call the text module
        textHandler = new TextHandler(this);

        // Setting the text size and content using methods within the text handler.
        textHandler.setText(text, textView);
        textHandler.setTextSize(textSize, textView);
    }

    //--------------------------------------------------------------------------------------------//
    //-------------------------------------  Image Handling  -------------------------------------//
    //--------------------------------------------------------------------------------------------//

    // Embeds a single image into a pre-defined ImageView
    public void embedImage(String imageTitle, String imageViewName, final int slideNumber,
                           boolean addContentSlide, final Bundle slideFuncBundle,
                           final Bundle masterBundle, final Context SlideContext,
                           boolean addContImage) {
        // Call ImageHandler
        ImageHandler imageHandler = new ImageHandler();


        // Obtain ID values for the image to be embedded and the Imageview
        int imageID = getResources().getIdentifier(imageTitle, "drawable", thisPackage);
        int imageViewID = getResources().getIdentifier(imageViewName, "id", thisPackage);

        // Add additional content linking and functionality if required.
        if ((addContentOrder[slideNumber] > 0) && (addContImage) && (!addContentSlide)) {

            addContentImageFunc(imageID, imageViewID, slideNumber, SlideContext, slideFuncBundle,
                    masterBundle);
        }
        else {
            //Embed a single image in a frame
            imageHandler.embed_image(this, imageID, imageViewID);
        }
    }

    // Embeds any number of static images into pre-defined ImageViews
    public void embedMultipleImages(String[] imageTitles, String imageViewName, int addContentImage,
                                    boolean addContentSlide, final int slideNumber,
                                    final Context SlideContext, final Bundle slideFuncBundle,
                                    final Bundle masterBundle){
        // Call ImageHandler
        ImageHandler imageHandler = new ImageHandler();

        // Integer arrays to hold the image and and frames ID numbers
        int[] imageIDs = new int[imageTitles.length];
        int[] imageViewIDs = new int[imageTitles.length];

        int imageTitlesLength = imageTitles.length;
        for (int i = 0; i < imageTitlesLength; i++){
            // Obtain ID values for the Imageview and the image to be embedded.
            imageIDs[i] = getResources().getIdentifier(imageTitles[i], "drawable", thisPackage);
            imageViewIDs[i] = getResources().getIdentifier(imageViewName + (i+1), "id", thisPackage);

            if ((addContentOrder[slideNumber] > 0) && (!addContentSlide) &&
                    (i == addContentImage - 1)) {
                addContentImageFunc(imageIDs[i], imageViewIDs[i], slideNumber, SlideContext,
                        slideFuncBundle, masterBundle);
            }
            else {
                //Embed a each image in its designated frame
                imageHandler.embed_image(this, imageIDs[i], imageViewIDs[i]);
            }
        }
    }

    // Dynamically create ImageViews, embed them in the vertical scrolling image panel and then
    //   embed the requested images within the ImageViews.
    public void embedScrollingImages(final String[] scrollingImageTitles,
                                     final LinearLayout scrollLayout, boolean addContentSlide,
                                     final int slideNumber, int addContentImage,
                                     final Context SlideContext, final Bundle slideFuncBundle,
                                     final Bundle masterBundle) {
        // Call ImageHandler for the embedding process.
        ImageHandler imageHandler = new ImageHandler();

        // Integer arrays to hold the image and and frames ID numbers.
        int[] scrollingImageIDs = new int[scrollingImageTitles.length];
        int[] scrollingFrameIDs = new int[scrollingImageTitles.length];

        // Define the layout parameters to be given to each ImageView.
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageViewParams.gravity = Gravity.CENTER;
        imageViewParams.setMargins(5, 5, 5, 5);

        int scrollingImageTitlesLength = scrollingImageTitles.length;
        for (int i = 0; i < scrollingImageTitlesLength; i++) {
            // Create ImageView
            final ImageView scrollingImageView = new ImageView(this);

            // Give each ImageView an ID, layout parameters and group images together.
            scrollingImageView.setId(i + 1);
            scrollingImageView.setLayoutParams(imageViewParams);
            scrollingImageView.setAdjustViewBounds(true);

            // Add ImageView to LinearLayout within scrolling panel.
            scrollLayout.addView(scrollingImageView);

            // Obtain ID values for the image to be embedded and the Imageview.
            scrollingImageIDs[i] = getResources().getIdentifier(scrollingImageTitles[i], "drawable",
                    thisPackage);
            scrollingFrameIDs[i] = scrollingImageView.getId();

            // Add additional content linking and functionality if required.
            if ((addContentOrder[slideNumber] > 0) && (!addContentSlide) &&
                    (i == addContentImage - 1)) {

                addContentImageFunc(scrollingImageIDs[i], scrollingFrameIDs[i], slideNumber,
                        SlideContext, slideFuncBundle, masterBundle);
            }
            else {
                // Embed each image into the ImageViews that have been embedded within the scrolling
                //   panel
                imageHandler.embed_image(this, scrollingImageIDs[i], scrollingFrameIDs[i]);
            }
        }
    }

    //
    public void addContentImageFunc(int imageTitleID, int imageViewID, final int slideNumber,
                                    final Context SlideContext, final Bundle slideFuncBundle, final
    Bundle masterBundle) {
        // Add watermark, indicating a link to additional content
        Drawable imageToEmbed = getResources().getDrawable(imageTitleID);
        Resources r = getResources();

        BitmapDrawable watermark = (BitmapDrawable) r.getDrawable(R.drawable.rasta);
        watermark.setGravity(Gravity.BOTTOM|Gravity.RIGHT);

        // Create two layers for ImageView to overlay the watermark on the requested image.
        Drawable[] layers = new Drawable[2];
        layers[0] = imageToEmbed;
        layers[1] = watermark;
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        ImageView addContent = ((ImageView) findViewById(imageViewID));

        // Setup Click listened to link to additional content slide
        addContent.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                // Decrement the slideInstance number to the presentation to return to this slide
                //   following the additional content.
                slideFuncBundle.putBoolean("addContentSlide", true);
                masterBundle.putBundle("slideFuncBundle", slideFuncBundle);

                // Progress to Additional Content Slide
                ChangeSlide(addContentOrder[slideNumber], SlideContext, masterBundle);
            }
        });
        addContent.setImageDrawable(layerDrawable);
    }

    //--------------------------------------------------------------------------------------------//
    //-------------------------------------  Audio Handling  -------------------------------------//
    //--------------------------------------------------------------------------------------------//
    public AudioHandler audioHandler;

    // Add audio player to the slide if it has been activated by the society.
    public void activateAudioPlayer(boolean audioActive, String[] audioAddresses) {
        if (audioActive) {
            // Add the slides audio URLs to an ArrayList to send to the AudioHandler.
            ArrayList<String> musicURLs = new ArrayList<>();
            for (int i = 0; i < audioAddresses.length; i++) {
                musicURLs.add(i, audioAddresses[i]);
            }
            // Call the AudioHandler with the list of audio files to be played.
            audioHandler = new AudioHandler(this, musicURLs);
        }
        else {
            // If there is no audio to be played on the slide, hide the media player button.
            ImageButton audioButton = (ImageButton) findViewById(R.id.popup);
            audioButton.setVisibility(View.GONE);
        }
    }

    // Audio Handler Methods
    public void displayPopUp(View view){audioHandler.displayPopUp(view);}
    public void dismissPopUp(View view){audioHandler.dismissPopUp();}
    public void backTrack(View view){audioHandler.backTrack();}
    public void forwardTrack(View view){audioHandler.forwardTrack();}
    public void playMusic(View view){audioHandler.playMusic();}
    public void mute(View view){audioHandler.mute();}

}
