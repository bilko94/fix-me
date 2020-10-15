package Router;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class channelListener implements Runnable {
    private final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    private final Selector selector = Selector.open();
    private channelSelector channelSelector;

    public channelListener(int port, String serverThreadName, channelSelector channelSelector) throws IOException {
        // init non blocking buffer
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // setup server listening port
        serverSocketChannel.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port));

        // assigning routing table and packet table
        this.channelSelector = channelSelector;

        // starting thread
        Thread serverThread = new Thread(this, serverThreadName);
        serverThread.start();
    }

    // server thread (receive)
    // TODO check if messages overwrite
    // TODO rename this
    @Override
    public void run() {
        try {
            while (true) {
                if (selector.select() <= 0)
                    continue;

                Iterator keys = selector.selectedKeys().iterator();
                SelectionKey key;
                while (keys.hasNext()){
                    key = (SelectionKey) keys.next();

                    if (key.isAcceptable())
                        registerChannel(key);

                    keys.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerChannel(SelectionKey key) throws IOException {
        SocketChannel sc = serverSocketChannel.accept();
        sc.configureBlocking(false);
        sc.register(selector, key.OP_READ);
        channel newChannel = channelSelector.register(sc);

        // for exec
        new channelThread(newChannel, channelSelector);
    }
}
