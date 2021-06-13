package ru.cft.focusstart.task6.client;

import common.ChatConnection;
import common.Message;
import common.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

import static ru.cft.focusstart.task6.client.ClientConstants.DEFAULT_HOST;
import static ru.cft.focusstart.task6.client.ClientConstants.DEFAULT_PORT;

public class Client {
    private Socket socket;
    private ChatConnection chatConnection;
    private ClientModel model;
    private ClientGui gui;
    private volatile boolean isConnect = false;
    private static final Logger logger = LoggerFactory.getLogger(Client.class.getName());

    public void setModel(ClientModel model) {
        this.model = model;
    }

    public void setGui(ClientGui gui) {
        this.gui = gui;
        gui.initFrameClient();
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }


    public void connect(boolean reconnecting) {
        logger.info("Trying to connect to server");
        if (!isConnect) {
            try {
                socket = reconnecting ?
                        new Socket(gui.getServerAddressFromOptionPane(), gui.getPortServerFromOptionPane()) :
                        new Socket(DEFAULT_HOST, DEFAULT_PORT);
                chatConnection = new ChatConnection(socket);
                isConnect = true;
                logger.info("Successfully connected, {}", socket.getRemoteSocketAddress());
                gui.addMessage(ClientConstants.CONNECTION_SUCCESS);
            } catch (Exception e) {
                logger.error("Сonnection failed, {}", socket.getRemoteSocketAddress());
                gui.errorDialogWindow(ClientConstants.CONNECTION_FAIL);
            }
        } else {
            gui.errorDialogWindow(ClientConstants.CONNECTED);
        }
    }

    public void nameUserRegistration() {
        while (isConnect) {
            try {
                Message message = chatConnection.receive();
                logger.info("Authorization. Message received. Type {}, contents ({})", message.getTypeMessage(), message.getTextMessage());
                switch (message.getTypeMessage()) {
                    case AUTHORIZATION_REQUEST -> chatConnection.send(new Message(MessageType.USER_NAME, gui.getUserName()));
                    case AUTHORIZATION_FAILURE -> gui.errorDialogWindow(message.getTextMessage());
                    case AUTHORIZATION_SUCCESS -> {
                        model.setUsers(message.getListUsers());
                        gui.refreshListUsers(model.getUsers());
                    }
                    case USER_JOINED -> {
                        gui.addMessage(ClientConstants.AUTHORIZATION_SUCCESS);
                        return;
                    }
                    default -> throw new IllegalStateException("Unexpected value for authorization: " + message.getTypeMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                gui.errorDialogWindow(ClientConstants.AUTHORIZATION_ERROR);
                break;
            }
        }
    }

    public void sendMessageOnServer(String text) {
        try {
            chatConnection.send(new Message(MessageType.MESSAGE, text));
            logger.info("Message with ({}) contents sent to server", text);
        } catch (Exception e) {
            gui.errorDialogWindow(ClientConstants.SENDING_ERROR);
        }
    }

    public void exchangingMessages() {
        while (isConnect) {
            try {
                Message message = chatConnection.receive();
                logger.info("Chatting. Message with {} type and ({}) contents received", message.getTypeMessage(), message.getTextMessage());
                if (message.getTypeMessage() == MessageType.MESSAGE) {
                    gui.addMessage(message.getTextMessage());
                }
                if (message.getTypeMessage() == MessageType.USER_JOINED) {
                    model.addUser(message.getTextMessage());
                    gui.refreshListUsers(model.getUsers());
                    gui.addMessage(String.format(ClientConstants.USER_JOIN, message.getTextMessage()));
                }
                if (message.getTypeMessage() == MessageType.USER_LEFT) {
                    model.removeUser(message.getTextMessage());
                    gui.refreshListUsers(model.getUsers());
                    gui.addMessage(String.format(ClientConstants.USER_LEFT, message.getTextMessage()));
                }
            } catch (Exception e) {
                gui.errorDialogWindow(ClientConstants.RECEIVING_ERROR);
                setConnect(false);
                gui.refreshListUsers(model.getUsers());
                break;
            }
        }
    }

    public void disconnect() {
        try {
            if (isConnect) {
                chatConnection.send(new Message(MessageType.USER_DISCONNECT));
                model.getUsers().clear();
                isConnect = false;
                gui.refreshListUsers(model.getUsers());
                logger.info("You are disconnected from server");
                return;
            }
            gui.errorDialogWindow(ClientConstants.ALREADY_DISCONNECTED);
        } catch (Exception e) {
            gui.errorDialogWindow(ClientConstants.DISCONNECT_ERROR);
        }
    }

    public void clearResources() {
        if (socket != null) {
            chatConnection.close();
        }
    }
}
