package Socket;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;


public class host {
    public Selector                selector            = null;
    public SelectionKey            key                 = null;
    public ServerSocketChannel     serverSocketChannel = null;

    public host(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port));

        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }
}
