package Router;

import java.io.IOException;

public class main {
    public static void main( String[] args )
    {
        try {
            socketServer brokerServer = new socketServer(5000,"Broker");
            socketServer MarketServer = new socketServer(5001,"Market");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
