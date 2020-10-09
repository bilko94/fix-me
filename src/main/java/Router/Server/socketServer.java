package Router.Server;

import Router.Packet.packetTable;
import Router.Routing.client;
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
    private routingTable routingTable;
    private packetTable packetTable;
    private int port;

    public socketServer(int port, String serverThreadName, routingTable routingTable, packetTable packetTable) throws IOException {
        // init non blocking buffer
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // setup server listening port
        this.port = port;
        serverSocketChannel.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port));

        // assigning routing table and packet table
        this.routingTable = routingTable;
        this.packetTable = packetTable;

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
                keyWritable();
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
            closeChannel(sc);
            return;
        }

        String result = new String(bb.array()).trim();
        if (result.length() <= 0) {
            closeChannel(sc);
        } else {
            System.out.println(result);
//            packetTable.addPacket(result);
        }
    }

    private void keyWritable() throws IOException {
        String msg = "bruh server";
        client clientObject = routingTable.getChannel();
        SocketChannel sc = clientObject.channel;
        ByteBuffer bb = ByteBuffer.wrap(msg.getBytes());
        sc.write(bb);
    }

    private void closeChannel(SocketChannel sc) throws IOException {
        routingTable.remove(sc);
        sc.close();
    }
}
