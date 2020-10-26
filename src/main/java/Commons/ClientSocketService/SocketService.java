package Commons.ClientSocketService;

import Commons.Packet.Packet;
import sun.net.ConnectionResetException;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SocketService implements Runnable {

    private final Selector selector = Selector.open();
    List<Packet> transmissionBuffer = new ArrayList<>();
    List<Packet> receivedBuffer = new ArrayList<>();
    public int id = 100000;
    int port;

    public int getId() {
        return id;
    }

    public SocketService(int port) throws IOException, InterruptedException {
        this.port = port;

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(InetAddress.getByName("localhost"), port));
        socketChannel.register(
                selector,
                SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE
        );

        Thread clientThread = new Thread(this, "clientSocket");
        clientThread.start();
        awaitId();
    }

    public void awaitId() throws InterruptedException {
        while (true){
            if (receivedBuffer.size() > 0){
                if (receivedBuffer.get(0).sender == 1){
                    id = receivedBuffer.get(0).recipient;
                    receivedBuffer.clear();
                    return;
                }
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    public void sendMessage(String message, int recipient) {
        Packet newPacket = new Packet(message, id, recipient);
        transmissionBuffer.add(newPacket);
    }

    public Packet getResponseMessage() {
        if (receivedBuffer.size() == 0)
            return null;
        Packet response = receivedBuffer.get(0);
        receivedBuffer.remove(receivedBuffer.get(0));
        return response;
    }

    private String getTransmissionMessage() {
        if (transmissionBuffer.size() == 0)
            return "";
        String message = transmissionBuffer.get(0).packetToString();
        transmissionBuffer.remove(transmissionBuffer.get(0));
        return message;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // checks if any incoming keys
                if (selector.select() > 0) {
                    // iterates through keys
                    for (SelectionKey key : selector.selectedKeys()){
                        // attempts connection
                        if (key.isConnectable() && !keyConnectable(key)) break;

                        // checks for incoming data
                        if (key.isReadable()) keyReadable(key);

                        // checks if data can be sent
                        if (key.isWritable() && transmissionBuffer.size() > 0) keyWritable(key);

                        selector.selectedKeys().remove(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean keyConnectable(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        try {
            while (sc.isConnectionPending()) {
                sc.finishConnect();
            }
        } catch (IOException e) {
            key.cancel();
            System.out.println("cannot connect to server on port " + port);
            return false;
        }
        return true;
    }

    private void keyReadable(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        try {
            sc.read(bb);
        } catch (ConnectionResetException e) {
            e.printStackTrace();
            System.out.println("Server disconnected");
            return;
        }
        String result = new String(bb.array()).trim();
        Packet response = new Packet(result);
        if (response.isValid())
            receivedBuffer.add(new Packet(result));
    }

    private void keyWritable(SelectionKey key) {
        String msg = getTransmissionMessage();
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer bb = ByteBuffer.wrap(msg.getBytes());
        try {
            sc.write(bb);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
            System.out.println("Server disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
