package Commons.Packet;

public class packet {
    public int sender;
    public int recipient;
    public String message;

    public packet(String message) {
        this.message = message;
    }

    public packet(String message, int sender, int recipient){
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }

    public boolean isValid(){
        return true;
    }

    public String packetToString(){
        return "";
    }

}
