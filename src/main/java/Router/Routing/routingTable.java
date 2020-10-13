package Router.Routing;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class routingTable {
    List<clientObject> connectedUsers = new ArrayList<>();

    public void add(SocketChannel socketChannel, int port){
        clientObject newClientObject = new clientObject(getId(),socketChannel, port);
        connectedUsers.add(newClientObject);
        System.out.println(newClientObject.id + " connected (notVerified)");
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
        for (clientObject connectedClientObject : connectedUsers){
            if (connectedClientObject.id == id){
                return false;
            }
        }
        return true;
    }

    public void remove(SocketChannel sc){
        for (clientObject connectedClientObject : connectedUsers){
            if (connectedClientObject.channel.equals(sc)){
                System.out.println(connectedClientObject.id + " disconnected");
                connectedUsers.remove(connectedClientObject);
                return;
            }
        }
    }

    public clientObject getChannel(int clientiId){
        for (clientObject connectedClientObject : connectedUsers){
            if (connectedClientObject.id == clientiId)
                return connectedClientObject;
        }
        return null;
    }

    public List<clientObject> getNonVerifiedClients(){
        List<clientObject> noIDClientObjects = new ArrayList<>();
        for (clientObject user : connectedUsers){
            if (user.verified == false)
                noIDClientObjects.add(user);
        }
        return noIDClientObjects;
    }

    public void printClients(){
        for (clientObject users: connectedUsers){
            System.out.println(users.id);
        }
    }

}
