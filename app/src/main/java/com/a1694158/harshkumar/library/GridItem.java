package com.a1694158.harshkumar.library;

/**
 * Created by Harsh on 7/3/2017.
 */

public class GridItem {

    private String cover;
    private String title,date,publisher;
    private long author_id;
    private int quantity;
    private boolean shipToCanada,shipToUSA;


    public GridItem(String cover, String title, String date, String publisher, long author_id, int quantity, boolean shipToCanada, boolean shipToUSA) {
        this.cover = cover;
        this.title = title;
        this.date = date;
        this.publisher = publisher;
        this.author_id = author_id;
        this.quantity = quantity;

        this.shipToCanada = shipToCanada;
        this.shipToUSA = shipToUSA;
    }

    public GridItem() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isShipToCanada() {
        return shipToCanada;
    }

    public void setShipToCanada(boolean shipToCanada) {
        this.shipToCanada = shipToCanada;
    }

    public boolean isShipToUSA() {
        return shipToUSA;
    }

    public void setShipToUSA(boolean shipToUSA) {
        this.shipToUSA = shipToUSA;
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
