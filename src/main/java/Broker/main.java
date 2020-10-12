package Broker;

import Commons.ClientSocket.socketHandler;
import Commons.Packet.packet;
import Commons.messageHandler.messageTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {
        socketHandler connection = new socketHandler(5000);
        packet message = new packet("hi",1000,1001);
        String msg = "";
        while (true){
            connection.sendMessage(message);
            msg = connection.getResponseMessage();
            if (!msg.equals(""))
                System.out.println(msg);
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
