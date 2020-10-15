package Router;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class channelSelector {
    // TODO hash this shit
    List<channel> channels = new ArrayList<>();

    public int register(SocketChannel channel){
        int id = getId();
        channels.add(new channel(id, channel));
        return id;
    }

    public channel getChannel(int id){
        for (channel channel : this.channels){
            if (channel.id == id){
                return channel;
            }
        }
        return null;
    }

    public void remove(SocketChannel channel){
//        for (channel connectedchannel : channels){
//            if (connectedchannel.equals())
//        }
    }

    public int getId(){
        int newId = 100000;
        while (newId < 1000000){
            if (idAvailable(newId))
                return newId;
            newId++;
        }
        return -1;
    }

    public boolean idAvailable(int id){
        for (channel channel : channels){
            if (channel.id == id){
                return false;
            }
        }
        return true;
    }
}
