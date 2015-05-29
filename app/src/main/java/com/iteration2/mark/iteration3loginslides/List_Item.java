package com.iteration2.mark.iteration3loginslides;

/**
 * Created by Andy on 25/05/2015.
 */
public class List_Item {
    private int videoId;
    private String videoTitle;
    private String descriptionTitle;

    public List_Item(int videoId, String videoTitle, String descriptionTitle) {
        super();
        this.videoId            = videoId;
        this.videoTitle         = videoTitle;
        this.descriptionTitle   = descriptionTitle;
    }

    public int getVideoId(){
        return videoId;
    }

    public String getVideoTitle(){
        return videoTitle;
    }

    public String getDescriptionTitle(){
        return descriptionTitle;
    }
}
