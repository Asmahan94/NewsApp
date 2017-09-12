package com.example.asmahansalem.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Asmahan Salem on 9/10/2017.
 */

public class loaderNews extends AsyncTaskLoader<List<News>> {

    /**
     * Here is a Java code source for class loaderNews With full original notes
     * <p>
     * https://github.com/DariaKalashnik/News/blob/master/app/src/main/java/com/example/android/news/NewsLoader.java
     */

    private String mUrl;

    loaderNews(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    // background thread
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // perform the network request and extract the news.
        return Utils.fetchNews(mUrl);
    }
}
