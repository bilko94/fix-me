package Router;

import java.io.IOException;

public class main {
    public static void main( String[] args )
    {
        System.out.println( "Hello World! router" );
        System.out.println("testing mongo server");
        MongoDBAtlasClient.connection();
        try {
            // init routing table
            routingTable routingTable = new routingTable();

            // init servers
            socketServer brokerServer = new socketServer(5000,"Broker", routingTable);
            socketServer marketServer = new socketServer(5001,"Market", routingTable);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
