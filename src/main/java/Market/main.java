package Market;

import Commons.ClientSocketService.socketService;
import Commons.*;
import Commons.Packet.packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main( String[] args ) throws InterruptedException, IOException {
//        List<market> marketArray = new ArrayList<>();
//        random = (int) Math.floor(7 * Math.random())+3;

        market stockMarket = new market();
        String[] info;

        System.out.println(stockMarket.getStockList().size());
        socketService connection = new socketService(5000);
        packet msg = connection.getResponseMessage();
        while (true){
            if (!msg.message.equals("")) {
//              System.out.println(msg);
                info = msg.message.split(" ");
                if (info[0].equals("buy")) {
                    stockMarket.buy(info[1], info[2]);
                }
                else if (info[0].equals("sell")) {

                }
            }

            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
