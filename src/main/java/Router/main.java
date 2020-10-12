package Router;

import Commons.Packet.packetTable;
import Router.Routing.routingTable;
import Router.Server.socketSender;
import Router.Server.socketServer;

import java.io.IOException;

public class main {
    public static void main( String[] args )
    {
        try {
            // init tables
            packetTable packetTable = new packetTable();
            routingTable routingTable = new routingTable();

            // init servers
            socketServer brokerServer = new socketServer(5000,"BrokerServer", routingTable, packetTable);
            socketServer marketServer = new socketServer(5001,"MarketServer", routingTable, packetTable);
            socketSender socketSender = new socketSender(routingTable, packetTable);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
