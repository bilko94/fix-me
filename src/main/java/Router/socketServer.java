package Router;

import Socket.host;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class socketServer implements Runnable {
    List<userBuffer> readableUserBuffer1 = new ArrayList<userBuffer>();
    List<userBuffer> threadUserBuffer1 = new ArrayList<userBuffer>();
    public String serverThreadName;
    private Thread serverThread;
    host server = null;


    socketServer(String serverName) throws IOException {
        if (serverName.equals("Broker")){
            this.server = new host(5000);
        }
        else if (serverName.equals("Market")) {
            this.server = new host(5001);
        }
        serverThreadName = serverName;
        start();
    }

    private void writeBuffer(){}
    public void readBuffer(){}

    @Override
    public void run() {
        try {
            while (true) {
                if (this.server.selector.select() <= 0){
                    continue;
                }
                Set<SelectionKey> selectedKeys = this.server.selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    System.out.println("scanning");
                    this.server.key = (SelectionKey) iterator.next();
                    iterator.remove();
                    if (this.server.key.isAcceptable()) {
                        keyAcceptable();
                    }
                    if (this.server.key.isReadable()) {
                        keyReadable();
                    }
                }
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println(serverThreadName + " Server");
        if (serverThread == null) {
            serverThread = new Thread (this, serverThreadName);
            serverThread.start();
        }
    }

    private void keyAcceptable() throws IOException {
        SocketChannel sc = this.server.serverSocketChannel.accept();
        sc.configureBlocking(false);
        sc.register(this.server.selector, SelectionKey.OP_READ);
        System.out.println("Connection Accepted: " + sc.getLocalAddress() + "\n");
    }

    private void keyReadable() throws IOException {
        SocketChannel sc = (SocketChannel) this.server.key.channel();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        sc.read(bb);
        String result = new String(bb.array()).trim();
        System.out.println("Message received: "
                + result
                + " Message length= " + result.length());
        if (result.length() <= 0) {
            sc.close();
            System.out.println("Connection closed...");
            System.out.println(
                    "Server will keep running. " +
                            "Try running another client to " +
                            "re-establish connection");
        }
    }
}
