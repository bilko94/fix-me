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
        int newId = 100000;
        while (newId < 1000000){
            if (idAvailable(newId))
                return newId;
            newId++;
        }
        return -1;
    }

    public boolean idAvailable(int id){
        for (client connectedClient : connectedUsers){
            if (connectedClient.id == id){
                return false;
            }
        }
        return true;
    }

    public void remove(SocketChannel sc){
        for (client connectedClient : connectedUsers){
            if (connectedClient.channel.equals(sc)){
                connectedUsers.remove(connectedClient);
                return;
            }
        }
    }

    public client getChannel(int clientiId){
        for (client connectedClient : connectedUsers){
            if (connectedClient.id == clientiId)
                return connectedClient;
        }
        return null;
    }

    public List<client> getNonVerifiedClients(){
        List<client> noIDClients = new ArrayList<>();
        for (client user : connectedUsers){
            if (user.verified == false)
                noIDClients.add(user);
        }
        return noIDClients;
    }

    public void printClients(){
        for (client users: connectedUsers){
            System.out.println(users.id);
        }
    }

}
