package ru.cft.focusstart.task6.server;

import common.ChatConnection;
import common.Message;
import common.MessageType;
import common.User;
import exceptions.EqualNickNamesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.cft.focusstart.task6.server.ServerConstants.DEFAULT_PORT;
import static ru.cft.focusstart.task6.server.ServerConstants.NICKNAME_DUPLICATE;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class.getName());
    private ServerSocket serverSocket;
    private final ServerModel model;
    private static final int PORT = DEFAULT_PORT;

    public Server(ServerModel model) {
        this.model = model;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            logger.error("Server could not start, {}", e.getMessage());
        }
    }

    public void stopServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.getClients().clear();
        logger.info("Server was stopped");
        for (Map.Entry<User, ChatConnection> clients : model.getClients().entrySet()) {
            User user = clients.getKey();
            ChatConnection connection = clients.getValue();
            try {
                connection.close();
                logger.info("Client {} connection closed", user.getName());
            } catch (Exception e) {
                logger.error("Could not close {} connection", user.getName());
            }
        }
    }


    public void startListening() {
        AtomicInteger countId = new AtomicInteger(0);
        while (true) {
            try {
                logger.info("Server is running on port {}", PORT);
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler((clientSocket), countId.incrementAndGet())).start();
            } catch (Exception e) {
                logger.warn("ChatConnection with server has been lost");
            }
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final Logger logger = LoggerFactory.getLogger(ClientHandler.class.getName());
        private final int id;
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        private User user;

        public ClientHandler(Socket clientSocket, int id) {
            this.clientSocket = clientSocket;
            this.id = id;
        }

        private void userAuthorization(ChatConnection chatConnection) throws IOException {
            while (clientSocket.isConnected()) {
                try {
                    chatConnection.send(new Message(MessageType.AUTHORIZATION_REQUEST));
                    Message message = chatConnection.receive();
                    logger.warn("Authorization. Message received. Type {}, contents ({})", message.getTypeMessage(), message.getTextMessage());
                    String userName = message.getTextMessage();
                    checkDuplicates(chatConnection, userName);
                    if (message.getTypeMessage() == MessageType.USER_NAME && userName != null && !userName.isEmpty()) {
                        newUser(chatConnection, userName);
                        logger.info(ServerConstants.AUTHORIZATION_SUCCESS, userName, id);
                        return;
                    } else {
                        chatConnection.send(new Message(MessageType.AUTHORIZATION_FAILURE, ServerConstants.AUTHORIZATION_FAIL));
                    }
                } catch (EqualNickNamesException e) {
                    logger.warn("Error while adding new user, {}", e.getMessage());
                } catch (IOException e) {
                    logger.warn("Error while adding new user, {}", e.getMessage());
                    model.removeUser(user);
                    clientSocket.close();
                    break;
                }
            }
        }

        private void checkDuplicates(ChatConnection chatConnection, String userName) throws EqualNickNamesException, IOException {
            if (model.checkNameDuplicates(userName)) {
                chatConnection.send(new Message(MessageType.AUTHORIZATION_FAILURE, NICKNAME_DUPLICATE));
                throw new EqualNickNamesException(NICKNAME_DUPLICATE);
            }
        }

        private void newUser(ChatConnection chatConnection, String userName) throws IOException {
            user = new User(id, userName);
            model.addUser(user, chatConnection);
            Set<String> listUsers = new HashSet<>();
            for (User users : model.getClients().keySet()) {
                listUsers.add(users.getName());
            }
            chatConnection.send(new Message(MessageType.AUTHORIZATION_SUCCESS, listUsers));
            sendMessageToAllUsers(new Message(MessageType.USER_JOINED, userName));
        }

        private void chatting(ChatConnection chatConnection) throws IOException {
            while (true) {
                Message message = chatConnection.receive();
                logger.info("Chatting. Message received. Type {}, contents ({})", message.getTypeMessage(), message.getTextMessage());
                if (message.getTypeMessage() == MessageType.MESSAGE) {
                    String time = LocalDateTime.now().format(formatter);
                    String textMessage = String.format("%s %s: %s\n", time, user.getName(), message.getTextMessage());
                    sendMessageToAllUsers(new Message(MessageType.MESSAGE, textMessage));
                    logger.info("Chatting. Message sent. Type {}, contents ({})", message.getTypeMessage(), textMessage);
                }
                if (message.getTypeMessage() == MessageType.USER_DISCONNECT) {
                    sendMessageToAllUsers(new Message(MessageType.USER_LEFT, user.getName()));
                    logger.warn("Chatting. Message received. Type {}, contents ({})", message.getTypeMessage(), message.getTextMessage());
                    model.removeUser(user);
                    clientSocket.close();
                    logger.info("User {} disconnected {} - current users on server {}", user.getName(), clientSocket.getRemoteSocketAddress(), model.getClients().size());
                    break;
                }
            }
        }

        private void sendMessageToAllUsers(Message message) {
            for (Map.Entry<User, ChatConnection> entry : model.getClients().entrySet()) {
                try {
                    entry.getValue().send(message);
                } catch (Exception e) {
                    logger.warn("Sending message to user with nickname {} and {} id failed. Possible cause {} ",
                            entry.getKey().getName(), entry.getKey().getId(), e.getMessage());
                    entry.getValue().close();
                }
            }
        }

        @Override
        public void run() {
            logger.info("New user has connected. {}", clientSocket.getRemoteSocketAddress());
            try (ChatConnection chatConnection = new ChatConnection(clientSocket)) {
                userAuthorization(chatConnection);
                chatting(chatConnection);
                clientSocket.close();
                logger.info("User disconnected. Name {}", user.getName());
            } catch (IOException e) {
                logger.error("Error while exchanging messages {}", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
