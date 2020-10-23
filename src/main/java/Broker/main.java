package Broker;

import Commons.ClientSocketService.socketService;
import Commons.Packet.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] args) throws IOException, InterruptedException {
        socketService connection = new socketService(5000);
        String requestMsg;

        try {
            while (true){
                String[] msg;
                int marketID;

                msg = make().split(" ");
                marketID = Integer.parseInt(msg[3]);
                requestMsg = msg[0] + msg[1] + msg[2];

                System.out.println(requestMsg);
                connection.sendMessage(requestMsg, marketID);
                System.out.println(connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String make() throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String brokerMsg;
        System.out.println();
        System.out.println();
        System.out.println("Place your order: \nFormat: <buy/sell> <instrument> <amount> <marketID>");
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
        if (msgArray.length == 4) {
            if ((msgArray[0].equalsIgnoreCase("buy") || msgArray[0].equalsIgnoreCase("sell"))
                    && msgArray[1].length() > 0 && msgArray[2].length() > 0 && msgArray[3].length() == 6) {
                try {
                    Integer.parseInt(msgArray[2]);
                    Integer.parseInt(msgArray[3]);
                    return true;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter your message in the correct format \n Expected Format: <buy/sell> <instrument> <amount>");
                }
            }
        }
        return false;
    }
}
