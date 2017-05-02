package com.appexample.newsreader;


public class Main2Activity {
    private String author;
    private String title;
    private String description;
    private String url;
    private String imgurl;
    private String published;

    public Main2Activity(String author, String title, String description, String url, String imageURL, String published) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imgurl = imageURL;
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getPublished(){
        return published;
    }
}

