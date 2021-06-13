package ru.cft.focusstart.task6.client;

public class ClientStarter {
    public static void main(String[] args) {
        Client client = new Client();
        client.setModel(new ClientModel());
        client.setGui(new ClientGui(client));
        client.connect(false);
        while (true) {
            if (client.isConnect()) {
                client.nameUserRegistration();
                client.exchangingMessages();
                client.setConnect(false);
            }
        }
    }
}
