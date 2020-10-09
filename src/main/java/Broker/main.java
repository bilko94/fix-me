package Broker;

import Router.Client.socketClient;

import java.util.concurrent.TimeUnit;

public class main {
    public static void main( String[] args ) throws Exception {
        socketClient client = new socketClient(5000);

        client.sendMessage("hi");
        String message = client.getResponseMessage();
        while (true){
            message = client.getResponseMessage();
            if (!message.equals(""))
                System.out.println(message);
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
