package Router.Server;

import Router.Packet.packet;
import Router.Packet.packetTable;
import Router.Routing.routingTable;
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
            List<packet> grabbedPackets = packetTable.getPackets();
            for (packet scheduledPacket: grabbedPackets){
                System.out.println(scheduledPacket.message);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
