package Commons.messageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


// message format:  <buy/sell> <instrument> <amount> <id> <recipientID> <checksum>
// example buy-item-5-6985-5544 , 293819
//TODO discuss message format:
// problems:
//  validation(separate message from sender id ,
//  recipientId and checksum) ,
//  different types messages ,
//  where to put message parser.
//  A soluion(ithink) :
//  "message(can be any type of message);clientID;recipientID;checkSum(checksum must includeIds when created)"
//  message will be placed into a packet class , packet(String message, int sender, int recipient)
//  packet will have a methods:
//      packet(String receivedMessage) <- this will parse the coming message and validate using the checksum
//      packet(String newMessage, int sender, int recipient) <- this will make a new packet and generate a checksum
//      String preparePacket() <- this will generate a checksum and prepare the message for sending
//      boolean isValid() <- this will check if the message is valid using the checksum
//

public class messageTest {

    public void broker() throws IOException {
        // socket connect kuk here

        // id = id return here
        PrintWriter output = new PrintWriter(System.out, true);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String brokerMsg;

        System.out.println("Place your order: \nFormat: <buy/sell> <instrument> <amount>");
        while (true){
            brokerMsg = stdIn.readLine();
            if (checkInput(brokerMsg)) {

                //TODO add a message return from router here (requires sockets completed)
            } else {
                System.out.println("Incorrect format for your order please try again");
            }
        }

    }
//
//    private static String sendMessage(String message) {
//        //String parsedMsg = id;
//        String parsedMsg = "";
//        int checksum = 1;
//        parsedMsg += "-"+message.replaceAll(" ", "-");
//        for (int i = 0; i < parsedMsg.length(); i++){
//            int temp = (int) (Math.floor(Math.log(parsedMsg.charAt(i)) / Math.log(2))) + 1;
//            checksum += ((1 << temp) - 1) ^ parsedMsg.charAt(i);
//        }
//        parsedMsg += "-"+checksum;
//        System.out.println("message sent: "+parsedMsg);
//        return parsedMsg;
//    }

    private static boolean checkInput(String message) {
        String[] msgArray = message.split(" ");
        if (msgArray.length == 3) {
            if ((msgArray[0].equalsIgnoreCase("buy") || msgArray[0].equalsIgnoreCase("sell"))
                    && msgArray[1].length() > 0 && msgArray[2].length() > 0) {
                try {
                    Integer.parseInt(msgArray[2]);
                    return true;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter your message in the correct format \n Expected Format: <buy/sell> <instrument> <amount>");
                }
            }
        }
        return false;
    }
}
