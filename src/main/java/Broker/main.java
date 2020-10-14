package Broker;

import Commons.ClientSocketService.socketService;
import Commons.Packet.packet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {
        socketService connection = new socketService(5000);
        packet msg;
        while (true){
            connection.sendMessage("hi",100001);
            msg = connection.getResponseMessage();
//            if (!(msg == null))
//                System.out.println(msg.message);
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
