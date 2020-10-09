package Commons.messageHandler;

public class messageParser {

    public String parse(String message){
        String parsedMsg = "";
        int checksum = 1;
        parsedMsg += "-"+message.replaceAll(" ", "-");
        checksum = getCheckSum(parsedMsg);
        parsedMsg += "-"+checksum;
        return parsedMsg;
    }

    public int getCheckSum(String seed){
        int checksum = 1;
        for (int i = 0; i < seed.length(); i++){
            int temp = (int) (Math.floor(Math.log(seed.charAt(i)) / Math.log(2))) + 1;
            checksum += ((1 << temp) - 1) ^ seed.charAt(i);
        }
        return checksum;
    }

    public boolean validateMessageWithCheckSum(String message, int checksum){
        int msgChecksum = getCheckSum(message);
        if (msgChecksum == checksum)
            return true;
        return false;
    }

}
