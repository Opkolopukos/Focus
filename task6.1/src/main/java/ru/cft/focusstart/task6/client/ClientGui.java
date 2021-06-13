package ru.cft.focusstart.task6.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;
import java.util.regex.Pattern;

import static ru.cft.focusstart.task6.client.ClientConstants.DEFAULT_HOST;
import static ru.cft.focusstart.task6.client.ClientConstants.DEFAULT_PORT;

public class ClientGui {
    private final Client client;
    private final JFrame frame = new JFrame("Chat");
    private final JTextArea messages = new JTextArea(30, 25);
    private final JTextArea users = new JTextArea(30, 10);
    private final JPanel panel = new JPanel();
    private final JTextField textField = new JTextField(40);
    private final JButton buttonDisconnect = new JButton("Disconnect");
    private final JButton buttonConnect = new JButton("Reconnect");
    private final JButton buttonSendMessage = new JButton("Send");

    public ClientGui(Client client) {
        this.client = client;
    }

    protected void initFrameClient() {
        messages.setEditable(false);
        messages.setBackground(Color.WHITE);

        users.setEditable(false);
        users.setBackground(Color.CYAN);

        frame.add(new JScrollPane(messages), BorderLayout.CENTER);
        frame.add(new JScrollPane(users), BorderLayout.EAST);

        panel.add(textField);
        panel.add(buttonSendMessage);
        panel.add(buttonConnect);
        panel.add(buttonDisconnect);

        frame.add(panel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (client.isConnect()) {
                    client.disconnect();
                    JOptionPane.showMessageDialog(frame, "ChatConnection is closed",
                            "Exit", JOptionPane.INFORMATION_MESSAGE
                    );
                }
                client.clearResources();
                System.exit(0);

            }
        });

        buttonConnect.setBackground(Color.WHITE);
        buttonConnect.addActionListener(e -> client.connect(true));
        buttonDisconnect.setBackground(Color.WHITE);
        buttonDisconnect.addActionListener(e -> client.disconnect());
        buttonSendMessage.setBackground(Color.WHITE);
        buttonSendMessage.addActionListener(e -> {
            client.sendMessageOnServer(textField.getText());
            textField.setText("");
        });

        textField.addActionListener(e -> {
            client.sendMessageOnServer(textField.getText());
            textField.setText("");
        });
        frame.setVisible(true);
        frame.pack();
    }

    protected void addMessage(String text) {
        messages.append(text);
    }

    protected void refreshListUsers(Set<String> listUsers) {
        users.setText("");
        if (client.isConnect()) {
            StringBuilder text = new StringBuilder("Users online:\n");
            for (String user : listUsers) {
                text.append(user).append("\n");
            }
            users.append(text.toString());
        }
    }

    protected String getServerAddressFromOptionPane() {
        String ipv4Pattern = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
        Pattern pattern = Pattern.compile(ipv4Pattern);
        String addressServer = JOptionPane.showInputDialog(
                frame, "Enter server address or type local for localhost",
                "Server address input",
                JOptionPane.QUESTION_MESSAGE);
        if (addressServer.equalsIgnoreCase("local")) {
            return DEFAULT_HOST;
        }
        if (pattern.matcher(addressServer).matches()) {
            return addressServer.trim();
        }
        errorDialogWindow("incorrect server address. Connecting to localhost");
        return DEFAULT_HOST;
    }

    protected int getPortServerFromOptionPane() {
        String port = JOptionPane.showInputDialog(
                frame, "Enter server port or type default to connect " + DEFAULT_PORT,
                "Server port input",
                JOptionPane.QUESTION_MESSAGE);
        if (port.equalsIgnoreCase("default")) {
            return DEFAULT_PORT;
        }
        try {
            return Integer.parseInt(port.trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Incorrect port. Trying to connect to 8080",
                    "Unavailable port", JOptionPane.ERROR_MESSAGE
            );
        }
        return DEFAULT_PORT;
    }

    protected String getUserName() {
        return JOptionPane.showInputDialog(
                frame, "Enter your nickname", "Authorization", JOptionPane.QUESTION_MESSAGE);
    }

    protected void errorDialogWindow(String text) {
        JOptionPane.showMessageDialog(frame, text, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
