package Broker;

import Router.Client.socketClient;

import java.util.concurrent.TimeUnit;

public class main {
    public static void main( String[] args ) throws Exception {
        socketClient client = new socketClient(5000);

        while (true){
            client.sendMessage("hi");
            System.out.println(client.getResponseMessage());
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
