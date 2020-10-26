package Router;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class ChannelListener implements Runnable {
    private final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    private final Selector selector = Selector.open();
    private final ChannelSelector channelSelector;
    private final ExecutorService executorService;

    public ChannelListener(int port, ChannelSelector channelSelector, ExecutorService executorService) throws IOException {
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
                selector.select();
                registerChannel();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerChannel() throws IOException {
        SocketChannel sc = serverSocketChannel.accept();
        sc.configureBlocking(false);

        Channel newChannel = channelSelector.register(sc);

        // start new channel thread
        executorService.submit(new ChannelThread(newChannel, channelSelector));
    }
}
