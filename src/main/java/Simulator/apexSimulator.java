package Simulator;

import Market.marketSimulator;

import java.io.IOException;

public class apexSimulator{


    public static void main(String[] args) throws InterruptedException, IOException {

        marketSimulator market1 = new marketSimulator();
        marketSimulator market2 = new marketSimulator();
        marketSimulator market3 = new marketSimulator();
        marketSimulator market4 = new marketSimulator();
        marketSimulator market5 = new marketSimulator();

        randomHandler randomM = new randomHandler(market1, market2, market3, market4, market5);

        Broker.simulator broker1 = new Broker.simulator(randomM.shuffle().getMarketID());
        Broker.simulator broker2 = new Broker.simulator(randomM.shuffle().getMarketID());
        Broker.simulator broker3 = new Broker.simulator(randomM.shuffle().getMarketID());
        Broker.simulator broker4 = new Broker.simulator(randomM.shuffle().getMarketID());
        Broker.simulator broker5 = new Broker.simulator(randomM.shuffle().getMarketID());

        Thread thread6 = new Thread(market1, "simulation6");
        Thread thread7 = new Thread(market2, "simulation7");
        Thread thread8 = new Thread(market3, "simulation8");
        Thread thread9 = new Thread(market4, "simulation9");
        Thread thread10 = new Thread(market5, "simulation10");
        Thread thread1 = new Thread(broker1, "simulation1");
        Thread thread2 = new Thread(broker2, "simulation2");
        Thread thread3 = new Thread(broker3, "simulation3");
        Thread thread4 = new Thread(broker4, "simulation4");
        Thread thread5 = new Thread(broker5, "simulation5");

        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }

}
