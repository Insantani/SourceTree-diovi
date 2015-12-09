package com.williamhenry.insantani;

import android.graphics.Bitmap;

public class SearchItem {

    private String title;
    private String content;
    private Bitmap picture;

    public SearchItem() {
    }

    public SearchItem(String title, String content, Bitmap picture) {
        this.title = title;
        this.content = content;
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getPicture(){
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

}
