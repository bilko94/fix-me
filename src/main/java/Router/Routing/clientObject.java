package Router.Routing;

import java.nio.channels.SocketChannel;

public class clientObject {
    public SocketChannel channel;
    public int port;
    public boolean verified;
    public int id;

    clientObject(int clientId, SocketChannel clientChannel, int serverPort){
        channel = clientChannel;
        id = clientId;
        port = serverPort;
        verified = false;
    }
}
