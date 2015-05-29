package com.iteration2.mark.iteration3loginslides;

/**
 * Created by Andy on 11/03/2015.
 */

        import android.app.Activity;
        import android.graphics.Paint;
        import android.graphics.Typeface;
        import android.util.Log;
        import android.widget.TextView;

public class TextHandler {

    int x[] = new int[2];

    Activity activity;
    TextView textPanel;

    public TextHandler(Activity calledFrom){
        this.activity = calledFrom;
    }


    public int[] setPosition(TextView panel){

        panel.getLocationOnScreen(x);
        Log.v("DUCK", "text position:" + x[0]);
        Log.v("DUCK", "text position:" + x[1]);
        return x;
    }



    public void setX(int xPos, TextView textPanel){textPanel.setX(xPos);}
    public void setY(int yPos, TextView textPanel){textPanel.setY(yPos);}
    public void setPos(int xPos,int yPos, TextView textPanel){textPanel.setX(xPos);textPanel.setY(yPos);}
    public void setTextSize(int textSize, TextView textPanel){textPanel.setTextSize(textSize);}
    public void setText(String inputText, TextView textPanel){textPanel.setText(inputText);}
    public void setColour(int Colour, TextView textPanel){textPanel.setTextColor(Colour);}
    public void setTypeFace(Typeface font, TextView textPanel){textPanel.setTypeface(font);}
    public void setFormat(boolean underline, boolean bold, boolean italics, TextView textPanel){
        if(underline)
            textPanel.setPaintFlags(textPanel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        else {
            //Something should happen here
        }

        if ((bold)&&(!italics)){
            textPanel.setTypeface(null,Typeface.BOLD);
        }
        else if ((italics)&&(!bold)){
            textPanel.setTypeface(null,Typeface.ITALIC);
        }
        else if ((italics) && (bold)){
            textPanel.setTypeface(null,Typeface.BOLD_ITALIC);
        }
        else{
            textPanel.setTypeface(null,Typeface.NORMAL);
        }



    }


}