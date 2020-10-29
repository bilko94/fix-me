package Simulator;

import Broker.BrokerSimulator;
import Market.MarketSimulator;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApexSimulator {

    public static void main(String[] args) throws InterruptedException, IOException {
        //executor
        ExecutorService executorService = Executors.newCachedThreadPool();

        //randomizer
        Random random = new Random();

        //amount of markets and brokers to simulate
        int marketAmount = 5;
        int brokerAmount = 5;

        //market and broker array
        MarketSimulator[] markets = new MarketSimulator[marketAmount];
        BrokerSimulator[] brokers = new BrokerSimulator[brokerAmount];

        for (int i = 0; i < marketAmount; i++) {
            markets[i] = new MarketSimulator();
            executorService.submit(markets[i]);
        }

        for (int i = 0; i < brokerAmount; i++) {
            brokers[i] = new BrokerSimulator(markets[random.nextInt(brokerAmount)].getMarketID());
            executorService.submit(brokers[i]);
        }
    }

}
