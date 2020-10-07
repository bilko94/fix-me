package Router;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class socketServer implements Runnable {

    // userBuffer for valid userPackets (avoid invalid/lost packets)
    public List<userBuffer> readableUserBuffer = new ArrayList<userBuffer>();

    // threadPackets to avoid invalid/incomplete packets
    private List<userBuffer> threadUserBuffer = new ArrayList<userBuffer>();

    // server vars
    private ServerSocketChannel serverSocketChannel = null;
    private Selector selector;
    private Thread serverThread;
    private String serverThreadName;

    // selection key for thread
    private SelectionKey key = null;


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

                //   iterates through all keys in selector
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    // gets next key
                    key = (SelectionKey) iterator.next();
                    iterator.remove();
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
        SocketChannel sc = (SocketChannel) this.key.channel();
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
