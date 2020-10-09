package Router.Packet;

import java.util.ArrayList;
import java.util.List;

public class packetTable {
    List<packet> packetBuffer = new ArrayList<>();

    public void addPacket(String message){
        packet newPacket = new packet(message);
        // verifyPacket
        // if not verified an error package will be built for sender and placed in verified packet
        packetBuffer.add(newPacket);
    }

    public List<packet> getPackets(){
        List<packet> scheduledPackets = new ArrayList<>(packetBuffer);
        packetBuffer = new ArrayList<>();
        return scheduledPackets;
    }

    public boolean packetsAvailable(){
        if (packetBuffer.size() > 0)
            return true;
        return false;
    }

}
