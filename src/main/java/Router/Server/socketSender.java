package Router.Server;

import Commons.Packet.packet;
import Commons.Packet.packetTable;
import Router.Routing.client;
import Router.Routing.routingTable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class socketSender implements Runnable{

    packetTable packetTable;
    routingTable routingTable;

    public socketSender(routingTable routingTable, packetTable packetTable) {
        this.routingTable = routingTable;
        this.packetTable = packetTable;
        Thread senderThread = new Thread(this, "socketSender");
        senderThread.start();
    }

    @Override
    public void run() {
        while (true){
            if (!packetTable.packetsAvailable()){
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            List<packet> grabbedPackets = packetTable.getPackets();
            for (packet scheduledPacket: grabbedPackets){
                System.out.println("found packet");
                if (scheduledPacket.isValid()){
                    System.out.println("packet Valid");
                    client sender = routingTable.getChannel(scheduledPacket.sender);
                    client recipient = routingTable.getChannel(scheduledPacket.recipient);
                    try {
                        if (recipient != null) {
                            System.out.println("found Recipient");
                            writeToSocketChannel(recipient.channel, scheduledPacket.packetToString());
                        } else {
                            System.out.println("cannot find recipient");
                            writeToSocketChannel(sender.channel, new packet("cannot find route " + scheduledPacket.recipient, 1, scheduledPacket.sender).packetToString());
                            System.out.println("cannot find recipient");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToSocketChannel(SocketChannel channel, String message) throws IOException {
        ByteBuffer bb = ByteBuffer.wrap(message.getBytes());

        try {
            channel.write(bb);
        } catch (IOException e) {
            closeChannel(channel);
            return;
        }
    }

    private void closeChannel(SocketChannel sc) throws IOException {
        routingTable.remove(sc);
        sc.close();
    }
}
