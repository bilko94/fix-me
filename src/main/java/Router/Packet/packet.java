package Router.Packet;

public class packet {
    int sender;
    int recipient;
    int valid;
    public String message;

    public packet(String message) {
        this.message = message;
        messageParser();
    }

    void messageParser(){
        // check up
        sender = 1000;
        recipient = 1000;
        valid = 1;
    }
}
