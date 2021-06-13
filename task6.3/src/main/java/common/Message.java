package common;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class Message implements Serializable {
    private MessageType typeMessage;
    private String textMessage;
    private Set<String> listUsers;

    /*
    To avoid com.fasterxml.jackson.databind.exc.InvalidDefinitionException
     */
    private Message() {

    }

    private Message(MessageType typeMessage, String textMessage, Set<String> listUsers) {
        this.textMessage = textMessage;
        this.typeMessage = typeMessage;
        this.listUsers = listUsers;
    }

    public Message(MessageType typeMessage, String textMessage) {
        this(typeMessage, textMessage, Collections.emptySet());
    }

    public Message(MessageType typeMessage, Set<String> listUsers) {
        this(typeMessage, "", listUsers);
    }

    public Message(MessageType typeMessage) {
        this(typeMessage, "", Collections.emptySet());
    }

    public MessageType getTypeMessage() {
        return typeMessage;
    }

    public Set<String> getListUsers() {
        return listUsers;
    }

    public String getTextMessage() {
        return textMessage;
    }
}
