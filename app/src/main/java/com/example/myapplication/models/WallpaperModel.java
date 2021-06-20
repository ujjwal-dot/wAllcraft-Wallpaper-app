package com.example.myapplication.models;

public class WallpaperModel {
    private  String imageUrl,tags;

    public WallpaperModel(String imageUrl, String tags) {
        this.imageUrl = imageUrl;
        this.tags = tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTags() {
        return tags;
    }


}
