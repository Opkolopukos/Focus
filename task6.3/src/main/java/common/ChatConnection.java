package common;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class ChatConnection implements AutoCloseable {
    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;
    private final ObjectMapper mapper = new ObjectMapper(
            new JsonFactory().configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false));
    private static final Logger logger = LoggerFactory.getLogger(ChatConnection.class.getName());


    public ChatConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        logger.info("ChatConnection created");
    }


    public void send(Message message) throws IOException {
        synchronized (this.out) {
            mapper.writeValue((DataOutput) out, message);
            logger.debug("Message has been mapped and sent though output stream");
        }
    }

    public Message receive() throws IOException {
        synchronized (this.in) {
            Message message = mapper.readValue((DataInput) in, Message.class);
            logger.debug("Message has been mapped and read from input stream");
            return message;
        }
    }

    @Override
    public void close() {
        try {
            out.close();
            logger.debug("out stream has been closed");
        } catch (IOException e) {
            logger.error("Closing output stream has failed", e.getMessage());
        }
        try {
            in.close();
            logger.debug("input stream has been closed");
        } catch (IOException e) {
            logger.error("Closing input stream has failed", e.getMessage());
        }
        try {
            socket.close();
            logger.debug("Socket has been closed");
        } catch (IOException e) {
            logger.error("Closing socket stream has failed", e.getMessage());
        }
    }
}
