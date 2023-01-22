package com.driver;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class MyDriver implements Driver {
    static {
        try {
            MyDriver driver = new MyDriver();
            DriverManager.registerDriver(driver);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection connect(String url, Properties info) {
        String[] urlParts = url.split(":");
        if (urlParts.length != 3 || !urlParts[0].equals("myjdbc"))
            return null;
        int port;
        try {
            port = Integer.parseInt(urlParts[2]);
        }
        catch (NumberFormatException ex) {
            return null;
        }

        try {
            Socket socket = new Socket(urlParts[1], port);
            return new MyConnection(socket);
        } catch (UnknownHostException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return true;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}