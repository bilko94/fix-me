package Router;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class ChannelSelector {
    // TODO hash this shit
    HashMap<Integer, Channel> channels = new HashMap<>();

    public Channel register(SocketChannel channel){
        int id = newId();
        Channel newChannel = new Channel(id, channel);
        channels.put(id , newChannel);
        return newChannel;
    }

    public Channel getChannel(int id){
        return channels.get(id);
    }

    public void remove(int id){
        channels.remove(id);
    }

    public int newId(){
        int newId = (int) Math.ceil(999999 * Math.random());
        while (channels.containsKey(newId) && newId > 99999){
            System.out.println(newId);
            newId = (int) Math.ceil(999999 * Math.random());
        }
        return newId;
    }
}
