package com.example.asmahansalem.newsapp;

/**
 * Created by Asmahan Salem on 9/13/2017.
 */

public class LoaderNews extends AsyncTaskLoader<List<News>> {

    /**
     * Here is a Java code source for class loaderNews With full original notes
     * <p>
     * https://github.com/DariaKalashnik/News/blob/master/app/src/main/java/com/example/android/news/NewsLoader.java
     */

    private String mUrl;

    LoaderNews(Context context, String url) {
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
