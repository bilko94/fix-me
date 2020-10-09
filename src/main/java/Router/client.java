package Router;

import java.nio.channels.SocketChannel;

public class client {
    public SocketChannel channel;
    public int id;

    client(int clientId, SocketChannel clientChannel){
        channel = clientChannel;
        id = clientId;
    }
}
