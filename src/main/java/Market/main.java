package Market;
import Socket.connect;

import java.util.concurrent.TimeUnit;

public class main {
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World! market" );
        connect connection = new connect("localHost",5001);
        while (true){
            connection.send("scale");
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
