package Router;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class routingTable {
    List<client> connectedUsers = new ArrayList<>();

    public void add(SocketChannel socketChannel){
        connectedUsers.add(new client(1,socketChannel));
    }
    public void remove(){}
    public void getChannel(){}

}
