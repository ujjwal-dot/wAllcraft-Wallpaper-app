package com.ujjwal.wallcraft.models;

import java.io.Serializable;

public class WallpaperModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private  String imageUrl,tags,largeimageUrl;

    public WallpaperModel(String imageUrl, String tags,String largeimageUrl) {
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.largeimageUrl=largeimageUrl;
    }

    public String getLargeimageUrl() {
        return largeimageUrl;
    }

    public void setLargeimageUrl(String largeimageUrl) {
        this.largeimageUrl = largeimageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTags() {
        return tags;
    }


}
