package com.example.celebrityapp;

public class ParseItem {
    private String imgUrl;
    private String title;
    private String detailedUrl;

    public ParseItem(String imgUrl, String title, String detailedUrl) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.detailedUrl = detailedUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDetailedUrl() {
        return detailedUrl;
    }
}
