package com.rocdev.android.piet.newsreaderv2;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class RSSFeed {
    private String title = null;
    private String pubDate = null;
    private ArrayList<RSSItem> items;

    private SimpleDateFormat dateInFormat =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

    public RSSFeed() {
        items = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDate() {
        return pubDate;
    }

    public long getPubDateMillis() {
        try {
            Date date = dateInFormat.parse(pubDate.trim());
            return date.getTime();
        }
        catch (Exception e) {
//            throw new RuntimeException(e);
            return -1;
        }

    }

    public int addItem(RSSItem item) {
        items.add(item);
        return items.size();
    }

    public RSSItem getItem(int index) {
        return items.get(index);
    }

    public ArrayList<RSSItem> getAllItems() {
        return items;
    }
}