package com.rocdev.android.piet.newsreaderv2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Piet on 7-3-2015.
 */
public class RSSFeedHandler extends DefaultHandler {
    private RSSFeed feed;
    private RSSItem item;

    private boolean feedTitleHasBeenRead = false;
    private boolean feedPubDateHasBeenRead = false;
    private boolean isTitle = false;
    private boolean isFeedTitle1 = false;
    private boolean isFeedTitle2 = false;
    private boolean isItemDescription = false;
    private boolean isFeedDescription = false;
    private boolean isLink = false;
    private boolean isPubDate = false;
    private String pubDate;

    public RSSFeed getFeed() {
        return feed;
    }

    @Override
    public void startDocument() throws SAXException {
        feed = new RSSFeed();
        item = new RSSItem();
        pubDate = "";
    }

    @Override
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts) throws SAXException {

        switch (qName) {
            case "item":
                item = new RSSItem();
                break;
            case "title":
                isTitle = true;
                if (!isFeedTitle1) {
                    isFeedTitle1 = true;
                } else if (!isFeedTitle2) {
                    isFeedTitle2 = true;
                }
                break;
            case "description":
                if (!isFeedDescription) {
                    isFeedDescription = true;
                } else {
                    isItemDescription = true;
                }
                break;
            case "guid":
                isLink = true;
                return;
            case "pubDate":
                isPubDate = true;
                break;

        }
    }

    @Override
    public void endElement(String namespaceURI, String localName,
                           String qName) throws SAXException
    {
        if (qName.equals("item")) {
            feed.addItem(item);
            return;
        }
    }

    @Override
    public void characters(char ch[], int start, int length)
    {
        String s = new String(ch, start, length);
        if (isTitle) {
            if (isFeedTitle1 && isFeedTitle2) {
                if (!feedTitleHasBeenRead) {
                    feed.setTitle(s);
                    feedTitleHasBeenRead = true;
                }  else {
                    item.setTitle(s);
                }
            }
            isTitle = false;
        }
        else if (isLink) {
            item.setLink(s);
            isLink = false;
        }
        else if (isItemDescription) {
            item.setDescription(s);
            isItemDescription = false;
        }
        else if (isPubDate) {
            if (!feedPubDateHasBeenRead) {
                feed.setPubDate(s);
                feedPubDateHasBeenRead = true;
            }
            item.setPubDate(s);
            isPubDate = false;
        }
    }
}
