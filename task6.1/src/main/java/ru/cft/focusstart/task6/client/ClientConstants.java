package ru.cft.focusstart.task6.client;

public class ClientConstants {
    private static final PropertyReader propertyReader = new PropertyReader();

    private ClientConstants() {
    }

    protected static final String CONNECTION_SUCCESS = "Server info: You've successfully connected.\n";
    protected static final String CONNECTION_FAIL = "ChatConnection error";
    protected static final String CONNECTED = "You're already connected";
    protected static final String AUTHORIZATION_SUCCESS = "Server info: Authorization completed!\n";
    protected static final String AUTHORIZATION_ERROR = "Authorization error. Please try to connect again";
    protected static final String SENDING_ERROR = "Error while sending message. Try reconnection to server";
    protected static final String USER_JOIN = "Server info: User %s has joined the chat.\n";
    protected static final String USER_LEFT = "Server info: User %s has left the chat.\n";
    protected static final String RECEIVING_ERROR = "Unable to receive message from server";
    protected static final String ALREADY_DISCONNECTED = "You are already disconnected";
    protected static final String DISCONNECT_ERROR = "Server info: error while disconnect";
    protected static final String DEFAULT_HOST = propertyReader.getHost();
    protected static final int DEFAULT_PORT = propertyReader.getPort();
}
