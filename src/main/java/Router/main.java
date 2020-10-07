package Router;

import socket.host;

import java.io.IOException;

public class main {
    public static void main( String[] args )
    {
        System.out.println( "Hello World! router" );
        try {
            host server = new host(5000);
            System.out.println("hi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
