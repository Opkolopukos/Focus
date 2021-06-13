package ru.cft.focusstart.task6.server;

public class ServerConstants {

    private static final PropertyReader SERVER_CONFIG_READER = new PropertyReader();

    private ServerConstants() {
    }

    protected static final String NICKNAME_DUPLICATE = "Attempt to authorize with nickname that is already taken";
    protected static final String AUTHORIZATION_FAIL = "Authorization failed";
    protected static final String AUTHORIZATION_SUCCESS = "Authorization complete. New User {}. ID - {}";
    protected static final int DEFAULT_PORT = SERVER_CONFIG_READER.getPort();
}
