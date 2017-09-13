package com.example.asmahansalem.newsapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Asmahan Salem on 9/10/2017.
 */

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    /**
     * Here is a Java code source that helped me in implement this Adapter
     * <p>
     * https://github.com/DariaKalashnik/News/blob/master/app/src/main/java/com/example/android/news/NewsAdapter.java
     */
    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();
    private static OnItemClickListener mListener;
    private ArrayList<News> mNews;
    private MainActivity mContext;

    NewsAdapter(MainActivity mContext, ArrayList<News> mNews, OnItemClickListener mListener) {
        this.mContext = mContext;
        this.mNews = mNews;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder viewHolder, int position) {

        News news = mNews.get(position);

        viewHolder.title.setText(news.getTitle());
        viewHolder.section.setText(news.getSection());
        viewHolder.date.setText(makeDateFormat(news.getDate()));
        viewHolder.onListClick(mNews.get(position), mListener);
    }

    // make new date format
    private String makeDateFormat(String date) {
        date = date.substring(0, date.length() - 1);
        String defaultDate = "yyyy-MM-dd'T'HH:mm:ss";
        String newDate = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(defaultDate);
        SimpleDateFormat outputFormat = new SimpleDateFormat(newDate);
        Date mDate;
        String output = "";
        try {
            mDate = inputFormat.parse(date);
            output = outputFormat.format(mDate);
        } catch (ParseException e) {
            Log.e(LOG_TAG, String.valueOf(R.string.dateParsing), e);
        }
        return output;
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    void clear() {
        mNews.clear();
        notifyDataSetChanged();
    }

    void addAll(List<News> book) {
        mNews.addAll(book);
        notifyDataSetChanged();
    }

    interface OnItemClickListener {
        void onItemClick(News book);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView section;
        TextView date;
        ImageView imageView;
        
        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title_news);
            section = itemView.findViewById(R.id.section_news);
            date = itemView.findViewById(R.id.news_date);
            imageView = itemView.findViewById(R.id.news_imageView);
        }

        void onListClick(final News book, final OnItemClickListener mListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(book);
                }
            });
        }
    }
}
