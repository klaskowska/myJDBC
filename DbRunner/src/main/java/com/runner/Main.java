package com.runner;

import com.server.DbServer;

public class Main {
    private static final int port = 5433;

    public static void main(String[] args) {
        DbServer dbServer = new DbServer(port);

        dbServer.runServer();
    }
}
