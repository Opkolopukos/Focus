package ru.cft.focusstart.task6.server;

import common.ChatConnection;
import common.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerModel {
    private final Map<User, ChatConnection> clients = new ConcurrentHashMap<>();

    protected Map<User, ChatConnection> getClients() {
        return clients;
    }

    protected void addUser(User user, ChatConnection chatConnection) {
        clients.put(user, chatConnection);
    }

    protected void removeUser(User user) {
        clients.remove(user);
    }

    protected boolean checkNameDuplicates(String name) {
        return clients.entrySet().stream().anyMatch(user -> user.getKey().getName().equals(name));
    }
}