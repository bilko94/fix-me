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
            Packet incomingResponse;

            // buy/sell loop for simulation.
            boolean bool = true;
            while (true){
                if (bool) {
                    requestMsg = "buy Oil 7";
                    bool = false;
                } else {
                    requestMsg = "sell Oil 7";
                    bool = true;
                }
                connection.sendMessage(requestMsg, recipient);
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
