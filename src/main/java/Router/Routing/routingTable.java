package Router.Routing;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class routingTable {
    List<client> connectedUsers = new ArrayList<>();

    public void add(SocketChannel socketChannel, int port){
        connectedUsers.add(new client(1,socketChannel, port));
    }
    public void remove(){}
    public void getChannel(){}

}
