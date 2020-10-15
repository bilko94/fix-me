package Router;

import java.nio.channels.SocketChannel;

public class channel {
    public int id;
    public SocketChannel channel;

    public channel(int id, SocketChannel channel){
        this.id = id;
        this.channel = channel;
    }
}
