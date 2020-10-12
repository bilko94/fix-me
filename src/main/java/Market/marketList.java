package Market;

public class marketList {

    private String  instrument;
    private int     price;
    private int     stock;

    public marketList(String item){
        instrument = item;
        price = (int) Math.floor(5000*Math.random());
        stock = (int) Math.floor(100*Math.random());
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
