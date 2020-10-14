package Market;

import java.util.ArrayList;
import java.util.List;

public class market {

    private List<marketList> stockList = new ArrayList<>();

    public market() {
        int  random;
        int     i = 20;

//        random = Math.floor(41 * Math.random());
        String[] genericList = {
                "Corn",
                "Oats",
                "Rough Rice",
                "Soybeans",
                "Rapeseed",
                "Soy Meal",
                "Soybean Oil",
                "Wheat",
                "Milk",
                "Cocoa",
                "Coffee",
                "Orange Juice",
                "Adzuki bean",
                "Crude Oil",
                "Propane",
                "Gasoline",
                "Natural gas",
                "Ethanol",
                "Bananas",
                "Premium Waifu's",
                "Terephthalic Acid",
                "Hardwood Pulp",
                "Copper",
                "Gold",
                "Platinum",
                "Palladium",
                "Palm Oil",
                "Rubber",
                "Amber",
                "Live Cattle",
                "Bitcoin",
                "Oil",
                "Gold",
                "Sugar",
                "Zinc",
                "Tin",
                "Steel",
                "Butter",
                "Eggs",
                "Pork Bellies"
        };

        while (i > 0){
            random = (int) Math.floor(40 * Math.random());
            marketList newItem;
            boolean bool = false;

            for (marketList item: stockList){
               if (item.getInstrument().equals(genericList[random])){
                   bool = true;
               }
            }
            if (!bool){
                newItem = new marketList(genericList[random]);
                stockList.add(newItem);
            }
            i--;
        }
    }

    public String buy(String instrument, int qty) {
        int i = 0;
        int prevQty = 0;

        if (stockList.contains(instrument)){
            i = stockList.indexOf(instrument);
            marketList item = stockList.get(i);
            prevQty = item.getStock();
            if (prevQty > qty){
                item.setStock(prevQty - qty);
                return ("Success");
            }
            else {
                return ("Not enough stock of "+instrument+" in marketplace");
            }
        } else {
            return ("No such commodity found in marketplace");
        }

    }

    public String sell(String instrument, int qty, int price){
        int i = 0;
        int prevQty = 0;

        if (stockList.contains(instrument)){
            i = stockList.indexOf(instrument);
            marketList item = stockList.get(i);
            prevQty = item.getStock();
            item.setStock(prevQty + qty);
            return ("Success");
        } else {
            marketList newItem = new marketList(instrument);
            newItem.setListing(qty, price);
            return ("success");
        }
    }

    public List<marketList> getStockList() {
        return stockList;
    }

    public void printMarket(){
        for (marketList item: stockList){
            System.out.println(item.getInstrument()+" : Price: "+item.getPrice()+" : Stock: "+item.getStock());
        }
    }
}
