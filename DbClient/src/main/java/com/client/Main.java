package com.client;

import java.sql.*;

public class Main {
    private static final String DB_URL = "myjdbc:localhost:5433";

    private static void insertBook(String title, String author) {
        String insertQuery = "INSERT INTO Books (title, author) VALUES ('" + title + "', '" + author + "');";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            int rows = statement.executeUpdate(insertQuery);
            System.out.println("Inserted " + rows + " rows");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void selectBook(String selectQuery) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                String selectedTitle = resultSet.getString(1);
                String selectedAuthor = resultSet.getString(2);
                System.out.println("Title: " + selectedTitle);
                System.out.println("Author: " + selectedAuthor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void selectBookByTitle(String title) {
        selectBook("SELECT * from Books where title = '" + title + "';");
    }

    private static void selectBookByAuthor(String author) {
        selectBook("SELECT * from Books where author = '" + author + "';");
    }

    public static void main(String[] args) {
        insertBook("aaa", "bbb");
        insertBook("ccc", "bbb");
        insertBook("ddd", "eee");

        selectBookByTitle("aaa");
        selectBookByAuthor("bbb");
        selectBookByAuthor("eee");
    }
}