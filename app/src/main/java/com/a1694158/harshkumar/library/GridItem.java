package com.a1694158.harshkumar.library;

/**
 * Created by Harsh on 7/3/2017.
 */

public class GridItem {

    private String cover;
    private String title;

    public GridItem() {
    }

    public GridItem(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String image) {
        this.cover = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
