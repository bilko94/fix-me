package Broker;

import Commons.ClientSocketService.socketService;
import Commons.Packet.packet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class simulator implements Runnable {

    private int recipient;
    public simulator(int ID) {
        recipient = ID;
    }

    @Override
    public void run() {
        try {
            socketService connection = new socketService(5000);
            String  requestMsg;
            packet  msg;

            boolean bool = true;
            while (true){
//                requestMsg = brokerMessage.make();
                if (bool) {
                    requestMsg = "Buy Oil 7";
                    bool = false;
                } else {
                    requestMsg = "Sell Oil 7";
                    bool = true;
                }
                System.out.println(requestMsg);
                connection.sendMessage(requestMsg,recipient);
                msg = connection.getResponseMessage();
                if (!(msg == null))
                    System.out.println(msg.message);
                TimeUnit.MILLISECONDS.sleep(3000);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
