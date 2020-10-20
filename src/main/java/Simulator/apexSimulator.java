package Simulator;

import Market.marketSimulator;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class apexSimulator{


    public static void main(String[] args) throws InterruptedException, IOException {
        //executor
        ExecutorService executorService = Executors.newCachedThreadPool();

        //randomiser
        Random random = new Random();

        //amount of markets and brokers to simulate
        int marketAmount = 5;
        int brokerAmount = 5;

        //market and broker array
        marketSimulator[] markets = new marketSimulator[marketAmount];
        Broker.simulator[] brokers = new Broker.simulator[brokerAmount];

        for (int i = 0; i < marketAmount; i++) {
            markets[i] = new marketSimulator();
            executorService.submit(markets[i]);
        }

        for (int i = 0; i < brokerAmount; i++) {
            brokers[i] = new Broker.simulator(markets[random.nextInt(brokerAmount)].getMarketID());
            executorService.submit(brokers[i]);
        }
    }

}
