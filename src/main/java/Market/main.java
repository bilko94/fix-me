package Market;

import Router.Client.socketClient;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World! market" );
        System.out.println("testing mongo server");
        MongoDBAtlasClient.connection();
        socketClient client = null;
        try {
            client = new socketClient(5000);
            while (true){
                client.sendMessage("hi");
                System.out.println(client.getResponseMessage());
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
