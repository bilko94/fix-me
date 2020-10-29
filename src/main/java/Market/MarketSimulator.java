package Market;

import Commons.ClientSocketService.SocketService;
import Commons.Packet.Packet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MarketSimulator implements Runnable{

    private SocketService connection = null;
    private final int marketID;
    private Market stockMarket = new Market();

    public MarketSimulator() throws IOException, InterruptedException {
        this.connection = new SocketService(5001);
        marketID = connection.getId();
    }

    public void printMarket(){
        stockMarket.printMarket();
    }

    public int getMarketID() {
        return marketID;
    }

    @Override
    public void run() {

        try {
            String[] info;
            String answer = "";

            System.out.println("market id = " + marketID );
            Packet msg;
            while (true){
                msg = connection.getResponseMessage();
                if (!(msg == null)) {
                    info = msg.message.split(" ");
                    if (info[0].equals("buy")) {
                        answer = stockMarket.buy(info[1], Integer.parseInt(info[2]));
                        System.out.println(msg.sender + " order : '" + msg.message + "' was " + answer);
                        connection.sendMessage(answer, msg.sender);
                    }
                    else if (info[0].equals("sell")) {
                        answer = stockMarket.sell(info[1], Integer.parseInt(info[2]));
                        connection.sendMessage(answer, msg.sender);
                        System.out.println(msg.sender + " sale  : '" + msg.message + "' was " + answer);
                    }
                }
                TimeUnit.MILLISECONDS.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
