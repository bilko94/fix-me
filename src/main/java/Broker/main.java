package Broker;

import fixDecoder.messageTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main(String[] args) throws IOException {
        new messageTest().checkSumTest();
    }
}
