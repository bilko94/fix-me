package Market;

import Router.Client.socketClient;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main( String[] args ) throws InterruptedException, IOException {
//        System.out.println( "Hello World! market" );
//        System.out.println("testing mongo server");
//        MongoDBAtlasClient.connection();

        socketClient client = new socketClient(5000);
        String message;
        while (true){
            message = client.getResponseMessage();
            if (!message.equals(""))
                System.out.println(message);
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
