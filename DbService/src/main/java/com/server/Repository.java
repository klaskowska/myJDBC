package com.server;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map.Entry;

// For now it is possible to make changes in only one given table
public class Repository {
    private static final String EmptyRequestError = "Error: Empty request";
    private static final String UnknownRequestTypeError = "Error: Incorrect request type";
    private static final String IncorrectRequestBodyError = "Error: Incorrect request body";
    private static final Pattern selectByTitlePattern = Pattern.compile("^SELECT \\* from Books where title = '([a-z0-9]+)';$", Pattern.CASE_INSENSITIVE);
    private static final Pattern selectByAuthorPattern = Pattern.compile("^SELECT \\* from Books where author = '([a-z0-9]+)';$", Pattern.CASE_INSENSITIVE);
    private static final Pattern insertPattern = Pattern.compile("^INSERT INTO Books \\(title, author\\) VALUES \\('([a-z0-9]+)', '([a-z0-9]+)'\\);$", Pattern.CASE_INSENSITIVE);
    private static final String BodyError = "-1";
    private static final String AlreadyExistsError = "-2";

    private Map<String, Book> books = new HashMap<>();

    public Repository() {
        books.put("test", new Book("test", "test"));
    }

    public String executeRequest(String request) {
        if (request == null)
            return EmptyRequestError;

        Matcher selectByTitleMatcher = selectByTitlePattern.matcher(request);
        if (selectByTitleMatcher.find())
            return executeSelectByTitleRequest(selectByTitleMatcher.group(1));

        Matcher selectByAuthorMatcher = selectByAuthorPattern.matcher(request);
        if (selectByAuthorMatcher.find())
            return executeSelectByAuthorRequest(selectByAuthorMatcher.group(1));

        Matcher insertMatcher = insertPattern.matcher(request);
        if (insertMatcher.find())
            return executeInsertRequest(new Book(insertMatcher.group(1), insertMatcher.group(2)));

        return UnknownRequestTypeError;
    }

    private String executeSelectByTitleRequest(String title) {
        if (title == null)
            return IncorrectRequestBodyError;
        Book book = books.get(title);
        if (book == null)
            return null;
        return book.getTitle() + ", " + book.getAuthor() + "\n";
    }

    private String executeSelectByAuthorRequest(String author) {
        if (author == null)
            return IncorrectRequestBodyError;

        StringBuilder selectedBooks = new StringBuilder();
        for(Entry<String, Book> entry : books.entrySet()) {
            if (entry.getValue().getAuthor().equals(author)) {
                selectedBooks
                        .append(entry.getValue().getTitle())
                        .append(", ").append(entry.getValue().getAuthor())
                        .append("\n");
            }
        }
        if (selectedBooks.toString().equals(""))
            return null;
        return selectedBooks.toString();
    }

    private String executeInsertRequest(Book book) {
        if (book == null)
            return BodyError;
        if (books.containsKey(book.getTitle()))
            return AlreadyExistsError;
        books.put(book.getTitle(), book);
        return "1";
    }
}