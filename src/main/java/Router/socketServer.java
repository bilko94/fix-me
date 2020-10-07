package Router;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class socketServer implements Runnable {

    // userBuffer for later user
    List<userBuffer> readableUserBuffer = new ArrayList<userBuffer>();
    List<userBuffer> threadUserBuffer = new ArrayList<userBuffer>();

    // server vars
    public  String serverThreadName;
    private Thread serverThread;

    public ServerSocketChannel      serverSocketChannel  = null;
    public Selector                 selector             = null;
    public SelectionKey             key                  = null;


    public socketServer(int port, String serverName) throws IOException {
        // server config
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // setup server listening port
        serverSocketChannel.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port));

        // init non blocking buffer
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // assigning server thread name
        serverThreadName = serverName;

        // starting thread
        serverThread = new Thread (this, serverThreadName);
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

                // iterates through all keys in selector
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    // gets next key
                    key = (SelectionKey) iterator.next();

                    // checks for incoming connections
                    if (key.isAcceptable()) keyAcceptable();

                    // checks for incoming data
                    if (key.isReadable()) keyReadable();
                }
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // <<< under construction >>>
    // assigns keys to connected streams
    private void keyAcceptable() throws IOException {
        SocketChannel sc = serverSocketChannel.accept();
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
        System.out.println("Connection Accepted: " + sc.getLocalAddress() + "\n");
    }

    // reads incoming messages using unique keys
    private void keyReadable() throws IOException {
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
}
