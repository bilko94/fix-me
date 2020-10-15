package Router;

import Commons.Packet.packet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class channelThread implements Runnable {
    SocketChannel channel;
    channelSelector channelSelector;

    public channelThread(SocketChannel channel, channelSelector channelSelector){
        this.channel = channel;
        this.channelSelector = channelSelector;
        Thread senderThread = new Thread(this, "hardline");
        senderThread.start();
    }

    @Override
    public void run() {
        ByteBuffer bb;
        String result;
        packet scheduledPacket;

        try {
            while (true){
                bb = ByteBuffer.allocate(1024);
                channel.read(bb);
                result = new String(bb.array()).trim();
                if (result.length() > 0){
                    sendPacket(new packet(result));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(packet scheduledPacket) throws IOException {
        channel recipient = channelSelector.getChannel(scheduledPacket.recipient);
        ByteBuffer bb;

        if (!scheduledPacket.isValid()){
            String inValidPacketMsg = "invalid checksum (" + scheduledPacket.packetToString().replace(";","_") + ")";
            bb = ByteBuffer.wrap(new packet(inValidPacketMsg, 1, 1).packetToString().getBytes());
            this.channel.write(bb);
        }
        else if (recipient == null){
            String noRecipientMsg = "cannot find " + scheduledPacket.recipient + ": (" + scheduledPacket.packetToString().replace(";","_") + ")";
            bb = ByteBuffer.wrap(new packet(noRecipientMsg, 1, 1).packetToString().getBytes());
            this.channel.write(bb);
        }
        else {
            bb = ByteBuffer.wrap(scheduledPacket.packetToString().getBytes());
            recipient.channel.write(bb);
        }

    }
}
