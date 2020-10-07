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

public class socketServer implements Runnable {
    List<userBuffer> readableUserBuffer1 = new ArrayList<userBuffer>();
    List<userBuffer> threadUserBuffer1 = new ArrayList<userBuffer>();
    host server = null;
    int serverTickRate = 64;


    socketServer(int port, int serverTickRate) throws IOException {
        this.server = new host(port);
        this.serverTickRate = serverTickRate;
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

    private void keyAcceptable() throws IOException {
        SocketChannel sc = this.server.serverSocketChannel.accept();
        sc.configureBlocking(false);
        sc.register(this.server.selector, SelectionKey.
                OP_READ);
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
