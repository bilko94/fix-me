package Broker;

import Commons.ClientSocketService.SocketService;
import Commons.Packet.Packet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BrokerSimulator implements Runnable {

    private final int recipient;
    public BrokerSimulator(int ID) {
        recipient = ID;
    }

    @Override
    public void run() {
        try {
            //Service connection
            SocketService connection = new SocketService(5000);
            String  requestMsg;
            Packet msg;

            // buy/sell loop for simulation.
            boolean bool = true;
            while (true){
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
