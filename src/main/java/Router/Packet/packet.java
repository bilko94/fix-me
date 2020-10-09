package Router.Packet;

public class packet {
    public int sender;
    public int recipient;
    public boolean valid;
    public String message;

    public packet(String message) {
        this.message = message;
        messageParser();
    }

    void messageParser(){
        // check up
        sender = 1001;
        recipient = 1000;
        valid = true;
    }
}
