package Market;

import Commons.ClientSocket.socketHandler;
import Commons.Packet.packet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main( String[] args ) throws InterruptedException, IOException {
        market stockMarket = new market();
        stockMarket.printMarket();

        socketHandler connection = new socketHandler(5000);
        String msg = "";
        while (true){
            msg = connection.getResponseMessage();
            if (!msg.equals(""))
                System.out.println(msg);
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
