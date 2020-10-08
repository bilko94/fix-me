package Broker;

import Socket.connect;

import java.util.concurrent.TimeUnit;

public class main {
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World! broker" );
        connect connection = new connect("localHost",5000);
        while (true){
            connection.send("bruh" + (System.currentTimeMillis() % 1000000));
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
