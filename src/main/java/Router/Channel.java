package Router;

import java.nio.channels.SocketChannel;

public class Channel {
    public int id;
    public SocketChannel channel;

    public Channel(int id, SocketChannel channel){
        this.id = id;
        this.channel = channel;
    }
}
