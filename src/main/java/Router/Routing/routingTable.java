package Router.Routing;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class routingTable {
    List<client> connectedUsers = new ArrayList<>();

    public void add(SocketChannel socketChannel, int port){
        connectedUsers.add(new client(getId(),socketChannel, port));
    }

    public int getId(){
        return 1000 + connectedUsers.size();
    }

    public void remove(SocketChannel sc){

    }

    public client getChannel(int clientiId){
        for (client connectedClient : connectedUsers){
            if (connectedClient.id == clientiId)
                return connectedClient;
        }
        return null;
    }

    public void printClients(){
        for (client users: connectedUsers){
            System.out.println(users.id);
        }
    }

}
