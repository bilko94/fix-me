package Broker;

import Commons.ClientSocketService.socketService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {
        socketService connection = new socketService(5000);
        String msg = "";
        while (true){
            connection.sendMessage("hi",100001);
            msg = connection.getResponseMessage();
            if (!msg.equals(""))
                System.out.println(msg);
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
