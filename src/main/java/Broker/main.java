package Broker;

import socket.connect;

public class main {
    public static void main( String[] args ) {
        System.out.println( "Hello World! broker" );
        connect connection = new connect("localHost",5000);
        connection.send("bruh1");
        connection.send("bruh2");
        connection.send("bruh3");
        connection.send("bruh4");
        connection.close();
    }
}
