package Market;

import Commons.ClientSocketService.SocketService;
import Commons.Packet.Packet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MarketSimulator implements Runnable{

    private SocketService connection = null;
    private final int marketID;

    public MarketSimulator() throws IOException, InterruptedException {
        this.connection = new SocketService(5001);
        marketID = connection.getId();
    }


    public int getMarketID() {
        return marketID;
    }

    @Override
    public void run() {

        try {
            Market stockMarket = new Market();
            String[] info;
            String answer = "";

            stockMarket.printMarket();
            Packet msg;
            while (true){
                msg = connection.getResponseMessage();
                if (!(msg == null)) {
                    info = msg.message.split(" ");
                    if (info[0].equals("buy")) {
                        answer = stockMarket.buy(info[1], Integer.parseInt(info[2]));
                        connection.sendMessage(answer, msg.sender);
                        stockMarket.printMarket();
                    }
                    else if (info[0].equals("sell")) {
                        answer = stockMarket.sell(info[1], Integer.parseInt(info[2]));
                        connection.sendMessage(answer, msg.sender);
                        stockMarket.printMarket();
                    }
                }
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
