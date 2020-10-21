package Router;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

public class channelListener implements Runnable {
    private final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    private final Selector selector = Selector.open();
    private channelSelector channelSelector;
    private ExecutorService executorService;

    public channelListener(int port, String serverThreadName, channelSelector channelSelector, ExecutorService executorService) throws IOException {
        // init non blocking buffer
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // setup server listening port
        serverSocketChannel.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port));

        // assigning routing table and packet table
        this.channelSelector = channelSelector;

        // pass the executor
        this.executorService = executorService;
    }

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

        // start new channel thread
        executorService.submit(new channelThread(newChannel, channelSelector));
    }
}
