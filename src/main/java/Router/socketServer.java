package Router;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.List;

public class socketServer implements Runnable {

    // server vars
    private final ServerSocketChannel serverSocketChannel;
    private final channel ConnectedChannel = new channel();
    private final Selector selector;
    private SelectionKey key;

    public socketServer(int port, String serverThreadName) throws IOException {
        // server config
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // setup server listening port
        serverSocketChannel.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port));

        // init non blocking buffer
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        // starting thread
        Thread serverThread = new Thread(this, serverThreadName);
        serverThread.start();
    }

    // for later (for sending and receiving messages)
    private void writeBuffer(){}
    public void readBuffer(){}

    // server thread (send / receive)
    @Override
    public void run() {
        try {
            while (true) {
                // checks if any incoming keys
                if (selector.select() <= 0) continue;

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

    //TODO make times messages in connection client
    //TODO figure out how to manage users
    //TODO grind aim in CSGO
    //TODO make cheese sandwich
    //TODO make non blocking connection client
    //TODO make connection client mutlThreaded
    //gg

    // assigns keys to connected streams
    private void keyAcceptable(SelectionKey key) throws IOException {
        SocketChannel sc = serverSocketChannel.accept();
        sc.configureBlocking(false);
        sc.register(selector, key.OP_READ);
        ConnectedChannel.add(sc);
        System.out.println("Connection Accepted: " + sc.getLocalAddress() + "\n");
    }

    // reads incoming messages using unique keys
    private void keyReadable(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        sc.read(bb);
        String result = new String(bb.array()).trim();
        System.out.println("Message received: "
                + result
                + " Message length= " + result.length());
        if (result.length() <= 0) {
            sc.close();
            System.out.println("Connection closed...");
        }
    }

    private void keyWritable(SelectionKey key) throws IOException {

    }
}
