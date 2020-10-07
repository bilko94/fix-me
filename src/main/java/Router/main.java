package Router;

import java.io.IOException;

public class main {
    public static void main( String[] args )
    {
        try {
            // init servers
            socketServer brokerServer = new socketServer(5000,"Broker");
            socketServer marketServer = new socketServer(5001,"Market");

            // router shit happens here
            brokerServer.readBuffer();
            marketServer.readBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
