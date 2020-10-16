package Router;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class main {
    public static void main( String[] args )
    {
        try {
            // init channel selector
            channelSelector channelSelector = new channelSelector();
            ExecutorService executorService = Executors.newCachedThreadPool();

            // init socket listeners
            // for exec
            executorService.submit(new channelListener(5000,"brokerListener", channelSelector, executorService));
            // for exec
            executorService.submit(new channelListener(5001,"marketListener", channelSelector, executorService));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
