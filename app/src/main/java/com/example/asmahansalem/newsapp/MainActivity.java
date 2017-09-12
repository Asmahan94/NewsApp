package com.example.asmahansalem.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Here is a Java code source that helped me in implement this how to write this API and Async Task With full original notes
 * <p>
 * https://github.com/DariaKalashnik/News/blob/master/app/src/main/java/com/example/android/news/MainActivity.java
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String REQUEST_URL = "http://content.guardianapis.com/search?";
    private static final String API_KEY = "13fd1877-8db2-441b-b2d0-d501808dcb86";
    // private static final String SHOW_FIELDS = "show-fields";
    private static final int LOADER_ID = 1;
    private NewsAdapter newsAdapter;
    private TextView emptyNodata;
    private RecyclerView recyclerView;
    ProgressBar mProgressBar;
    LoaderManager loaderManager;
    String newsNumber;
    String orderBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.list_news);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        emptyNodata = (TextView) findViewById(R.id.text_noData);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        final ConnectivityManager connectivityManager;
        NetworkInfo networkInfo;

        newsAdapter = new NewsAdapter(MainActivity.this, new ArrayList<News>(), new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(News book) {
                String url = book.getURL();
                Intent newIntent = new Intent(Intent.ACTION_VIEW);
                if (newIntent.resolveActivity(getPackageManager()) != null) {
                    newIntent.setData(Uri.parse(url));
                    startActivity(newIntent);
                }
            }
        });

        recyclerView.setAdapter(newsAdapter);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        // If there is a network connection, then fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            loaderManager = getLoaderManager();
            loaderManager.restartLoader(LOADER_ID, null, MainActivity.this);
        } else {
            mProgressBar.setVisibility(View.GONE);
            emptyNodata.setVisibility(View.VISIBLE);
            emptyNodata.setText(R.string.error_connection);
        }
    }

    // Create the Loader
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle args) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        newsNumber = sharedPreferences.getString(getString(R.string.news_number2),
                getString(R.string.news_number));
        orderBy = sharedPreferences.getString(getString(R.string
                .order_by_key), getString(R.string.order_by));

        Uri uri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = uri.buildUpon();

        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("page-size", newsNumber);
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("api-key", API_KEY);
        // uriBuilder.appendQueryParameter("thumbnail", SHOW_FIELDS);

        return new loaderNews(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        // Clear the adapter of previous news data
        newsAdapter.clear();
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
            // Hide loading indicator because the data has been loaded
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }

}
