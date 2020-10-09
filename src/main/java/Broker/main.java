package Broker;

import Commons.ClientSocket.socketHandler;
import Commons.messageHandler.messageTest;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        socketHandler connection = new socketHandler(5000);
        new messageTest().checkSumTest();
    }
}
