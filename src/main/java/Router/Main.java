package Router;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main( String[] args )
    {
        try {
            // init channel selector
            ChannelSelector channelSelector = new ChannelSelector();
            ExecutorService executorService = Executors.newCachedThreadPool();

            // init socket listeners
            // for exec
            executorService.submit(new ChannelListener(5000, channelSelector, executorService));
            // for exec
            executorService.submit(new ChannelListener(5001, channelSelector, executorService));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
