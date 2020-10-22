package Router;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class channelSelector {
    // TODO hash this shit
    HashMap<Integer,channel> channels = new HashMap<>();

    public channel register(SocketChannel channel){
        int id = getId();
        channel newChannel = new channel(id, channel);
        channels.put(id , newChannel);
        return newChannel;
    }

    public channel getChannel(int id){
        return channels.get(id);
    }

    public void remove(int id){
        channels.remove(id);
    }

    public int getId(){
        int newId = (int) Math.ceil(999999 * Math.random());
        while (channels.containsKey(newId) && newId > 99999){
            System.out.println(newId);
            newId = (int) Math.ceil(999999 * Math.random());
        }
        return newId;
    }
}
