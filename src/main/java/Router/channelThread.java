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
        writeToChannel(channelSelector.getChannel(this.id), idPacket.packetToString());
    }

    public void sendPacket(packet scheduledPacket) throws IOException {
        channel recipient = channelSelector.getChannel(scheduledPacket.recipient);

        //TODO split up
        if (!scheduledPacket.isValid()){
            String inValidPacketMsg = "invalid checksum (" + scheduledPacket.packetToString().replace(";","_") + ")";
            writeToChannel(channelSelector.getChannel(this.id), new packet(inValidPacketMsg, 1, 1).packetToString());
        }
        else if (recipient == null){
            String noRecipientMsg = "cannot find " + scheduledPacket.recipient + ": (" + scheduledPacket.packetToString().replace(";","_") + ")";
            writeToChannel(channelSelector.getChannel(this.id), new packet(noRecipientMsg, 1, 1).packetToString());
        }
        else {
            writeToChannel(recipient, scheduledPacket.packetToString());
        }
    }

    public void writeToChannel(channel channelObj, String message) throws IOException {
        ByteBuffer bb;
        try {
            bb = ByteBuffer.wrap(message.getBytes());
            channel.write(bb);
        } catch (ClosedChannelException e) {
            channelSelector.remove(channelObj.id);
        }
    }
}
