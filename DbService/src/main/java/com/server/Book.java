package com.server;

public class Book {
    private String title;
    private String author;

    public Book(String _title, String _author) {
        title = _title;
        author = _author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}