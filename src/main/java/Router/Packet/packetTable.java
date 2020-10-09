package Router.Packet;

import java.util.ArrayList;
import java.util.List;

public class packetTable {
    List<packet> verifiedPacketBuffer = new ArrayList<>();

    public void addPacket(String message){
        packet newPacket = new packet(message);
        // verifyPacket
        // if not verified an error package will be built for sender and placed in verified packet
        verifiedPacketBuffer.add(newPacket);
    }

    public List<packet> getPackets(){
        List<packet> scheduledPackets = new ArrayList<>(verifiedPacketBuffer);
        verifiedPacketBuffer = new ArrayList<>();
        return scheduledPackets;
    }

//    public List<packet> getVerifiedPackets(int destinationPort){
//        List<packet> gatheredPackets = new ArrayList<>();
//        for (packet verifiedPacket : verifiedPacketBuffer){
//            if (verifiedPacket.recipient == destinationPort){
//                gatheredPackets.add(verifiedPacket);
//                verifiedPacketBuffer.remove(verifiedPacket);
//            }
//        }
//        return gatheredPackets;
//    }
}
