package Router;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class channel {
    private List<SocketChannel> channels = new ArrayList<>();

    public void remove(SocketChannel socketChannel) throws IOException {
        socketChannel.close();
        channels.remove(socketChannel);
    }

    public void add(SocketChannel socketChannel) {
        channels.add(socketChannel);
    }


}
