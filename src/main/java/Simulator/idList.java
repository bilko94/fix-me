package Simulator;

import java.util.ArrayList;
import java.util.List;

public class idList {

    private List<Integer> marketid  = new ArrayList<>();

    public void add(int id){
        marketid.add(id);
    }

    public List<Integer> getMarketid() {
        return marketid;
    }
}
