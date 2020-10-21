package Router;

import Commons.Packet.packet;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

public class channelThread implements Runnable {
    SocketChannel channel;
    int id;
    channelSelector channelSelector;

    public channelThread(channel channel, channelSelector channelSelector){
        this.channel = channel.channel;
        this.id = channel.id;
        this.channelSelector = channelSelector;
//        Thread senderThread = new Thread(this, "hard line");
//        senderThread.start();
    }

    @Override
    public void run() {
        ByteBuffer bb;
        String result;
        try {
            // relays id before any messages can be sent
            sendId();

            // starts reading incoming messages
            while (true){
                bb = ByteBuffer.allocate(1024);
                channel.read(bb);
                result = new String(bb.array()).trim();
                if (result.length() > 0){
                    sendPacket(new packet(result));
                }
            }
        } catch (SocketException e) {
            channelSelector.remove(this.id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendId() throws IOException {
        packet idPacket = new packet("connected",1,this.id);
        ByteBuffer bb = ByteBuffer.wrap(idPacket.packetToString().getBytes());
        writeToChannel(channelSelector.getChannel(this.id),bb);
    }

    public void sendPacket(packet scheduledPacket) throws IOException {
        channel recipient = channelSelector.getChannel(scheduledPacket.recipient);
        ByteBuffer bb;

        if (!scheduledPacket.isValid()){
            String inValidPacketMsg = "invalid checksum (" + scheduledPacket.packetToString().replace(";","_") + ")";
            bb = ByteBuffer.wrap(new packet(inValidPacketMsg, 1, 1).packetToString().getBytes());
            writeToChannel(channelSelector.getChannel(this.id), bb);
        }
        else if (recipient == null){
            String noRecipientMsg = "cannot find " + scheduledPacket.recipient + ": (" + scheduledPacket.packetToString().replace(";","_") + ")";
            bb = ByteBuffer.wrap(new packet(noRecipientMsg, 1, 1).packetToString().getBytes());
            writeToChannel(channelSelector.getChannel(this.id), bb);
        }
        else {
            bb = ByteBuffer.wrap(scheduledPacket.packetToString().getBytes());
            writeToChannel(recipient, bb);
        }
    }

    public void writeToChannel(channel channelObj, ByteBuffer message) throws IOException {
        try {
            channel.write(message);
        } catch (ClosedChannelException e) {
            channelSelector.remove(channelObj.id);
        }
    }
}
