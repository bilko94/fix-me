package Broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class brokerMessage {
    public static String make() throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String brokerMsg;
        System.out.println();
        System.out.println();
        System.out.println("Place your order: \nFormat: <buy/sell> <instrument> <amount>");
        while (true){
            brokerMsg = stdIn.readLine();
            if (checkInput(brokerMsg)) {
                return brokerMsg;
                //TODO add a message return from router here (requires sockets completed)
            } else {
                System.out.println("Incorrect format for your order please try again");
            }
        }
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
