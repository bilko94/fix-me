package Router.Routing;

import java.nio.channels.SocketChannel;

public class client {
    public SocketChannel channel;
    public int port;
    public boolean verified;
    public int id;

    client(int clientId, SocketChannel clientChannel, int serverPort){
        channel = clientChannel;
        id = clientId;
        port = serverPort;
        verified = false;
    }
}
