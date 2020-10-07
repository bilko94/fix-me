package Router;

import Socket.host;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main( String[] args )
    {
        try {
            socketServer brokerServer = new socketServer("Broker");
            socketServer MarketServer = new socketServer("Market");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
