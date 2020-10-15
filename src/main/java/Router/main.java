package Router;

import java.io.IOException;

public class main {
    public static void main( String[] args )
    {
        try {
            // init channel selector
            channelSelector channelSelector = new channelSelector();

            // init socket listeners
            // for exec
            new channelListener(5000,"brokerListener", channelSelector);
            // for exec
            new channelListener(5001,"marketListener", channelSelector);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
