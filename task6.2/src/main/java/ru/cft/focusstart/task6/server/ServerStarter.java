package ru.cft.focusstart.task6.server;

public class ServerStarter {

    public static void main(String[] args) {
        Server server = new Server(new ServerModel());
        server.startServer();
        server.startListening();
        server.stopServer();

    }
}

