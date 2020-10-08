package Router;

import java.io.IOException;

public class main {
    public static void main( String[] args )
    {
        System.out.println( "Hello World! router" );
        System.out.println("testing mongo server");
        MongoDBAtlasClient.connection();
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
