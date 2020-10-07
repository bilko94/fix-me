package Market;
import socket.connect;

public class main {
    public static void main( String[] args )
    {
        System.out.println( "Hello World! market" );
        connect connection = new connect("localHost",5000);
    }
}
