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
        String answer = "";

        System.out.println(stockMarket.getStockList().size());
        socketService connection = new socketService(5000);
        packet msg = connection.getResponseMessage();
        while (true){
            if (!msg.message.equals("")) {
                info = msg.message.split(" ");
                if (info[0].equals("buy")) {
                    answer = stockMarket.buy(info[1], Integer.parseInt(info[2]));
                    System.out.println(answer);
                    connection.sendMessage(answer, msg.sender);
                }
                else if (info[0].equals("sell")) {
                    answer = stockMarket.sell(info[1], Integer.parseInt(info[2]));
                    System.out.println(answer);
                    connection.sendMessage(answer, msg.sender);
                }
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
