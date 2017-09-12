package com.example.asmahansalem.newsapp;

import android.graphics.Bitmap;

/**
 * Created by Asmahan Salem on 9/10/2017.
 */
class News {

    final private String section;
    final private String title;
    final private String date;
    //final private Bitmap mBitmap;
    final private String url;


    News(String section, String title, String date,  /**Bitmap bitmap,**/String url) {
        this.section = section;
        this.title = title;
        this.date = date;
        //this.mBitmap = bitmap;
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

    String getURL() {
        return url;
    }
    /**  public Bitmap getBitmap() {
     return mBitmap;
     }*/

}
