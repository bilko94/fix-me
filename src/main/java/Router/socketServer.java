package Router;

import Router.Routing.routingTable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class socketServer implements Runnable {

    // server vars
    private final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    private final Selector selector = Selector.open();
    private Router.Routing.routingTable routingTable;
    private int port;

    public socketServer(int port, String serverThreadName, routingTable routingTable) throws IOException {
        // init non blocking buffer
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // setup server listening port
        this.port = port;
        serverSocketChannel.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port));

        // assigning routing table
        this.routingTable = routingTable;

        // starting thread
        Thread serverThread = new Thread(this, serverThreadName);
        serverThread.start();
    }

    // server thread (receive)
    @Override
    public void run() {
        try {
            while (true) {
                // checks if any incoming keys
                if (selector.select() <= 0)
                    continue;

                // iterates through keys
                for (SelectionKey key : selector.selectedKeys()){
                    // checks for incoming connections
                    if (key.isAcceptable()) keyAcceptable(key);

                    // checks for incoming data
                    if (key.isReadable()) keyReadable(key);

                    // removes processed key
                    selector.selectedKeys().remove(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // <<< under construction >>>

    //TODO figure out how to manage users <<< busy with this >>>
    //TODO grind aim in CSGO
    //TODO make cheese sandwich
    //TODO make non blocking connection client
    //TODO make connection client multiThreaded
    //gg

    // assigns keys to connected streams
    private void keyAcceptable(SelectionKey key) throws IOException {
        SocketChannel sc = serverSocketChannel.accept();
        sc.configureBlocking(false);
        sc.register(selector, key.OP_READ);
        routingTable.add(sc, port);
    }

    // reads incoming messages using unique keys
    private void keyReadable(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer bb = ByteBuffer.allocate(1024);

        try {
            sc.read(bb);
        } catch (IOException e) {
            sc.close();
            return;
        }

        String result = new String(bb.array()).trim();
        if (result.length() <= 0) {
            sc.close();
            System.out.println("Connection closed...");
        } else {
            // no

        }
    }
}
