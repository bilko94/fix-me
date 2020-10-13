package Market;

import Commons.ClientSocketService.socketService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class main {
    public static void main( String[] args ) throws InterruptedException, IOException {

        int          random;
        List<market> marketArray = new ArrayList<>();

        random = (int) Math.floor(7 * Math.random())+3;

        while (random > 0){
            marketArray.add(new market());
            random--;
        }
        System.out.println(marketArray.size());
        socketService connection = new socketService(5000);
        String msg = "";
        while (true){
            msg = connection.getResponseMessage();
            if (!msg.equals(""))
                System.out.println(msg);
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
