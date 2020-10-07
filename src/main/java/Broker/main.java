package Broker;

import socket.connect;

public class main {
    public static void main( String[] args ) {
        System.out.println( "Hello World! broker" );
        new connect("localHost",5000);
        new connect("localHost",5000);
    }
}
