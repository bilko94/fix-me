package Broker;

import java.util.ArrayList;
import java.util.List;

public class stock {
    List<String> instruments = new ArrayList<>();

    public void add(String instrument){
        instruments.add(instrument);
    }

    public void remove(String instrument){
        int pos = instrument.indexOf(instrument);

        if (pos >= 0){
            instruments.remove(pos);
        }
    }

    public int amount(String instrument){
        int amm = 0;
        for (String storedInstrument : instruments){
            if (storedInstrument.equals(instrument))
                amm++;
        }
        return amm;
    }

}
