package Commons.Packet;

public class Packet {
    public int sender;
    public int recipient;
    public int checkSum;
    public String message;

    public Packet(String message) {
        String[] msgArray;

        msgArray = message.split(";");
        if (msgArray.length >= 4){
            this.sender = Integer.parseInt(msgArray[0]);
            this.message = msgArray[1];
            this.recipient = Integer.parseInt(msgArray[2]);
            this.checkSum = Integer.parseInt(msgArray[3]);
        }
        else {
            System.out.println("There was an error with the incoming msg.");
        }

    }

    public Packet(String message, int sender, int recipient){
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
        this.checkSum = genCheckSum();
    }

    public int genCheckSum(){
        int genCheckSum = 1;
        for (int i = 0; i < message.length(); i++){
            int temp = (int) (Math.floor(Math.log(message.charAt(i)) / Math.log(2))) + 1;
            genCheckSum += ((1 << temp) - 1) ^ message.charAt(i);
        }
        return genCheckSum;
    }

    public boolean isValid(){
        if (genCheckSum() == this.checkSum){
            return true;
        }
        return false;
    }

    public String packetToString(){
        String parsedMsg;

        parsedMsg = sender+";"+message+";"+recipient+";"+checkSum;

        return parsedMsg;
    }

}
