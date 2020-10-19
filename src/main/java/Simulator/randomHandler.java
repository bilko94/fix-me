package Simulator;

import Market.marketSimulator;

public class randomHandler {

    marketSimulator m1;
    marketSimulator m2;
    marketSimulator m3;
    marketSimulator m4;
    marketSimulator m5;

    public randomHandler(marketSimulator market1, marketSimulator market2, marketSimulator market3, marketSimulator market4, marketSimulator market5) {
        m1 = market1;
        m2 = market2;
        m3 = market3;
        m4 = market4;
        m5 = market5;
    }

    public marketSimulator shuffle(){
        int choice = (int) Math.floor(Math.random() * 5);

        switch (choice) {
            case 1:
                return m1;
            case 2:
                return m2;
            case 3:
                return m3;
            case 4:
                return m4;
            case 5:
                return m5;
        }
        return m1;
    }
}
