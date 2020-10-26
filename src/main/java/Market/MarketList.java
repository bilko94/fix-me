package Market;

public class MarketList {

    private String  instrument;
    private int     price;
    private int     stock;

    public MarketList(String item){
        instrument = item;
        price = (int) Math.floor(5000*Math.random());
        stock = (int) Math.floor(100*Math.random());
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setListing(int qty, int price){
        this.price = price;
        this.stock = qty;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getInstrument() {
        return instrument;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}
