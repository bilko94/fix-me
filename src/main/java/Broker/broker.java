package Broker;


// message format:  <buy/sell> <instrument> <amount> <id> <checksum>

import Socket.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class broker {

    private static String id;

    public static void main(String[] args) throws IOException {
        // socket connect kuk here

        // id = id return here
        connect connection = new connect("localHost",5000);
        PrintWriter output = new PrintWriter(System.out, true);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String brokerMsg;

        System.out.println("Place your order: \nFormat: <buy/sell> <instrument> <amount>");
        while (true){
            brokerMsg = stdIn.readLine();
            if (checkInput(brokerMsg)) {
                output.println(sendMessage(brokerMsg));
                //TODO add a message return from router here (requires sockets completed)
            } else {
                System.out.println("Incorrect format for your order please try again");
            }
        }

    }

    private static String sendMessage(String message) {
        //String parsedMsg = id;
        String parsedMsg = "";
        int checksum = 1;
        parsedMsg += "-"+message.replaceAll(" ", "-");
        for (int i = 0; i < parsedMsg.length(); i++){
            int temp = (int) (Math.floor(Math.log(parsedMsg.charAt(i)) / Math.log(2))) + 1;
            checksum += ((1 << temp) - 1) ^ parsedMsg.charAt(i);
        }
        parsedMsg += "-"+checksum;
        return parsedMsg;
    }

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
