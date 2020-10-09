package Router.Routing;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class routingTable {
    List<client> connectedUsers = new ArrayList<>();

    public void add(SocketChannel socketChannel, int port){
        connectedUsers.add(new client(1,socketChannel, port));
    }
    public void remove(SocketChannel sc){}
    public client getChannel(){
        if ( connectedUsers.size() > 0)
            return connectedUsers.get(0);
        return null;
    }

}
