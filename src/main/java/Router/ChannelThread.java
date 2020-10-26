package Router;

import Commons.Packet.Packet;

import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

public class ChannelThread implements Runnable {
    SocketChannel channel;
    int id;
    ChannelSelector channelSelector;

    public ChannelThread(Channel channel, ChannelSelector channelSelector){
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
                    sendPacket(new Packet(result));
                }
            }
        } catch (SocketException e) {
            channelSelector.remove(this.id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendId() throws IOException {
        Packet idPacket = new Packet("connected",1,this.id);
        writeToChannel(channelSelector.getChannel(this.id), idPacket.packetToString());
    }

    public void sendPacket(Packet scheduledPacket) throws IOException {
        Channel recipient = channelSelector.getChannel(scheduledPacket.recipient);

        //TODO split up
        if (!scheduledPacket.isValid()){
            String inValidPacketMsg = "invalid checksum (" + scheduledPacket.packetToString().replace(";","_") + ")";
            writeToChannel(channelSelector.getChannel(this.id), new Packet(inValidPacketMsg, 1, 1).packetToString());
        }
        else if (recipient == null){
            String noRecipientMsg = "cannot find " + scheduledPacket.recipient + ": (" + scheduledPacket.packetToString().replace(";","_") + ")";
            writeToChannel(channelSelector.getChannel(this.id), new Packet(noRecipientMsg, 1, 1).packetToString());
        }
        else {
            writeToChannel(recipient, scheduledPacket.packetToString());
        }
    }

    public void writeToChannel(Channel channelObj, String message) throws IOException {
        ByteBuffer bb;
        try {
            bb = ByteBuffer.wrap(message.getBytes());
            channelObj.channel.write(bb);
        } catch (ClosedChannelException e) {
            channelSelector.remove(channelObj.id);
        }
    }
}
