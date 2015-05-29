package com.iteration2.mark.iteration3loginslides;

/*   Claritas: Clarity through innovation
     Project: Socbox
     Module: Image Media Module
     Code file name: ImageHandler.java
     Description: This class is built to contain and test the Image handler. It therefore
     embeds images within frames using the reference IDs of the images and frames to locate them.
     While it is probably complete. It has not been tested yet and may still need to be modified.
     It will be modified to allow for image streaming and some of this work is completed and commented out.
     It will also be modified to allow for images to be links when there is something to link to.
    Initial authors: Anton Zijlstra
    Change History:
    Version 0.1
    Author: Anton Zijlstra
    Change: Created original image handler and partially completed image streaming
    Date: 01/03/15
    Traceability
    Tag: U/IM/01/01
    Requirement: Images can be displayed
    Tag: U/IM/02/01
    Requirement: Images are automatically resized
    Tag: U/IM/03/01
    Requirement: There can be multiple scrolling images placed within a frame
    Tag: U/IM/04/01
    Requirement: These can scroll vertically or horizontally
    Tag: U/IM/05/01
    Requirement: Some images act as links
    Other information:
    Note: This file has not yet been tested and may be incomplete. It is also awaiting Image streaming
    completion.
    To do: Test to see if the Image handler works as intended
           Finish Image streaming when possible
    */



//Variable for image streaming, please ignore
//private ImageView imageView;

        import android.app.Activity;
        import android.widget.ImageView;

//imports for image streaming
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import android.graphics.drawable.Drawable;

//In order to use the image handler you need to do the following:
//Step 1: Define an Image handler method within your activity
//Step 2: Define the Strings for the Image file and imageView frame names
//Step 3: Use getResources to obtain the ID's of the Image and frame
//Step 4: Call the embed_image module with those arguments
/*E.g.
*  Step 1:
*   public ImageHandler imageHandler;
*   imageHandler = new ImageHandler();
*
*  Step 2:
*   String imageToEmbed = "test1";
*   String frameToEmbedImage = "imageView1";
*
*  Step 3:
*   int imageID = getResources().getIdentifier(imageToEmbed, "drawable", "com.claritas.anton.socbox");
    int frameID = getResources().getIdentifier(frameToEmbedImage, "id", "com.claritas.anton.socbox");
*
*  Step 4:
*   embed_image(this, imageID, frameID);
* */
//To see an example of this please see the ExampleActivity file

public class ImageHandler /*extends ActionBarActivity */{

    //This class embeds a given image within a given Image view in the main layout. It is fully modular.
    //The only requirement for use is that the Image and Frame ID must be defined beforehand (see above)
    //and that there must be an Image in storage and an ImageViewer defined in the layout xml file
    /*Inputs: The image and file to be displayed
    * Outputs: Nothing*/
    public void embed_image(Activity activity, int imageID, int frameID)
    {
        //ImageView imageView = new ImageView(this);

        //This code will be for streaming an image
        /*try {
            URL url = new URL("@string/address");
            InputStream content = (InputStream) url.getContent();
            Drawable image = Drawable.createFromStream(content , "src");
            //imageView.setImageDrawable(image);
            ImageView embedded_image = (ImageView) findViewById(frameID);
            embedded_image.setImageDrawable(image);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //Find the correct ImageView frame
        ImageView embedded_image = (ImageView) activity.findViewById(frameID);
        //Embed the given image within it
        embedded_image.setImageResource(imageID);
    }

}