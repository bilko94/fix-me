package Market;

import java.util.ArrayList;
import java.util.List;

public class market {

    private final List<marketList> stockList = new ArrayList<>();

    public market() {
        int  random;
        int     i = 20;

        // Market list commodities (this is accessed when generating markets)
        String[] genericList = {
                "Corn",
                "Oats",
                "Rough_Rice",
                "Soybeans",
                "Rapeseed",
                "SoyMeal",
                "Oil",
                "Wheat",
                "Milk",
                "Cocoa",
                "Coffee",
                "Orange_Juice",
                "Adzuki_bean",
                "Crude_Oil",
                "Propane",
                "Gasoline",
                "Natural_gas",
                "Ethanol",
                "Bananas",
                "Premium_Waifu's",
                "Terephthalic",
                "Hardwood_Pulp",
                "Copper",
                "Gold",
                "Platinum",
                "Palladium",
                "Palm_Oil",
                "Rubber",
                "Amber",
                "Live_Cattle",
                "Bitcoin",
                "Oil",
                "Gold",
                "Sugar",
                "Zinc",
                "Tin",
                "Steel",
                "Butter",
                "Eggs",
                "Pork_Bellies"
        };

        while (i > 0){
            random = (int) Math.floor(40 * Math.random());
            marketList newItem;
            boolean bool = false;

            for (marketList item: stockList){
                if (item.getInstrument().equals(genericList[random])) {
                    bool = true;
                    break;
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
        int prevQty = 0;

        for (marketList listItem: stockList){
            if (listItem.getInstrument().equals(instrument)){
                prevQty = listItem.getStock();
                if (prevQty > qty){
                    listItem.setStock(prevQty - qty);
                    return ("Executed");
                }
                else {
                    return ("Rejected");
                }
            }
        }
        return ("Rejected");
    }

    public String sell(String instrument, int qty){
        int i = 0;
        int prevQty = 0;

        for (marketList listItem: stockList){
            if (listItem.getInstrument().equals(instrument)){
                prevQty = listItem.getStock();
                listItem.setStock(prevQty + qty);
                return ("Executed");
            }
        }
        return ("Rejected");
    }

    public List<marketList> getStockList() {
        return stockList;
    }

    public void printMarket(){
        //simple printer to visualize the market
        for (marketList item: stockList){
            System.out.println(item.getInstrument()+" || Price: "+item.getPrice()+" || Stock: "+item.getStock());
        }
    }
}
