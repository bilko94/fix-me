package Market;
import Socket.connect;

public class main {
    public static void main( String[] args )
    {
        System.out.println( "Hello World! market" );
        connect connection = new connect("localHost",5000);
        connection.send("scale1");
        connection.send("scale2");
        connection.send("scale3");
        connection.send("scale4");
        connection.close();
    }
}
