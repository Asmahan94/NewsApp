package com.example.asmahansalem.newsapp;

import android.graphics.Bitmap;

/**
 * Created by Asmahan Salem on 9/10/2017.
 */
class News {

    final private String section;
    final private String title;
    final private String date;
    final private String author;
    final private String url;


    News(String section, String title, String date,  String author,String url) {
        this.section = section;
        this.title = title;
        this.date = date;
        this.author = author;
        this.url = url;
    }

    String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    String getDate() {
        return date;
    }

    String getAuthor() {
        return author;
    }

    String getURL() {
        return url;
    }

}
