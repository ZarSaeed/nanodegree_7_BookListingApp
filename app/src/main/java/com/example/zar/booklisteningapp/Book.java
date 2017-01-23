package com.example.zar.booklisteningapp;

/**
 * Created by Zar on 12/25/2016.
 */

public class Book {
    private String title;
    private String[] authors;

    public  Book(String title,String[] authors)
    {
        this.title=title;
        this.authors=authors;
    }

    public String getTitle() {
        return title;
    }

    public String[] getAuthors() {
        return authors;
    }
}
