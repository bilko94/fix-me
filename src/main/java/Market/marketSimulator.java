package Market;

import Commons.ClientSocketService.socketService;
import Commons.Packet.packet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class marketSimulator implements Runnable{

    private int marketID;

    public int getMarketID() {
        return marketID;
    }

    @Override
    public void run() {

        try {
            market stockMarket = new market();
            String[] info;
            String answer = "";

            stockMarket.printMarket();
            socketService connection = null;
            connection = new socketService(5001);
            marketID = connection.getId();
            packet msg = connection.getResponseMessage();
            while (true){
                msg = connection.getResponseMessage();
                if (!(msg == null)) {
                    info = msg.message.split(" ");
                    if (info[0].equals("buy")) {
                        answer = stockMarket.buy(info[1], Integer.parseInt(info[2]));
                        System.out.println(answer);
                        connection.sendMessage(answer, msg.sender);
                        stockMarket.printMarket();
                    }
                    else if (info[0].equals("sell")) {
                        answer = stockMarket.sell(info[1], Integer.parseInt(info[2]));
                        System.out.println(answer);
                        connection.sendMessage(answer, msg.sender);
                        stockMarket.printMarket();
                    }
                }
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
