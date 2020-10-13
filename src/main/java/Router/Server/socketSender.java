package Router.Server;

import Commons.Packet.packet;
import Commons.Packet.packetTable;
import Router.Routing.clientObject;
import Router.Routing.routingTable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
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
            validateUsers();
            sendPackets();
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("error");
                e.printStackTrace();
            }
        }
    }

    private void validateUsers(){
        List<clientObject> nonVerifiedClientObjects = routingTable.getNonVerifiedClients();
        packet connectionMessage;

        for (clientObject nonVerifiedClientObject : nonVerifiedClientObjects){
            connectionMessage = new packet("connected",1, nonVerifiedClientObject.id);
            nonVerifiedClientObject.verified = true;
            if (writeToSocketChannel(nonVerifiedClientObject.channel, connectionMessage.packetToString()) == true)
                System.out.println(nonVerifiedClientObject.id + " connection verified");
        }
    }

    private void sendPackets(){
        if (!packetTable.packetsAvailable())
            return;

        List<packet> packets = packetTable.getPackets();
        clientObject sender;
        clientObject recipient;

        for (packet scheduledPacket: packets){
            sender      = routingTable.getChannel(scheduledPacket.sender);
            recipient   = routingTable.getChannel(scheduledPacket.recipient);

            if (!scheduledPacket.isValid())
                writeToSocketChannel(sender.channel, new packet("invalid checksum (" + scheduledPacket.packetToString().replace(";","_") + ")",1,sender.id).packetToString());

            else if (recipient == null)
                writeToSocketChannel(sender.channel, new packet("packet loss : (" + scheduledPacket.packetToString().replace(";","_") + ")" ,1,sender.id).packetToString());

            else if (recipient.verified == false)
                writeToSocketChannel(sender.channel, new packet("packet loss : (" + scheduledPacket.packetToString().replace(";","_") +")" ,1,sender.id).packetToString());

            else
                writeToSocketChannel(recipient.channel, scheduledPacket.packetToString());
        }
    }

    private boolean writeToSocketChannel(SocketChannel channel, String message) {
        ByteBuffer bb = ByteBuffer.wrap(message.getBytes());
        try {
            if (channel == null)
                return false;
            channel.write(bb);
            return true;
        } catch (ClosedChannelException e) {
            closeChannel(channel);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void closeChannel(SocketChannel channel){
        try {
            routingTable.remove(channel);
            channel.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return;
    }

}
