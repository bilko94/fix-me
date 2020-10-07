package Router;

import Socket.host;

import java.io.IOException;

public class main {
    public static void main( String[] args )
    {
        System.out.println( "Hello World! router" );
        try {
            socketServer server = new socketServer(5000,64);
            server.run();
            System.out.println("hi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
