package Market;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        MarketSimulator market = new MarketSimulator();
        market.printMarket();
        executorService.submit(market);
    }
}
