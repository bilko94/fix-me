package Router;

import Commons.ClientSocketService.SocketService;
import Commons.Packet.Packet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main( String[] args ) throws IOException, InterruptedException {
        SocketService connection1 = new SocketService(5000);
        SocketService connection2 = new SocketService(5001);
        int id1 = connection1.getId();
        int id2 = connection2.getId();

        Packet incoming1;
        Packet incoming2;
        String msg;

        while (true){
            msg = "hi " + (Math.random() * 100);
            connection1.sendMessage(msg, id2);
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("sending message : '" + msg + "' to :" + id2);
            while (true){
                incoming2 = connection2.getResponseMessage();
                if (incoming2 != null){
                    System.out.println("recieved from msg : " + incoming2.message + " from : " + incoming2.sender);
                    TimeUnit.MILLISECONDS.sleep(2000);
                    break;
                }

                incoming1 = connection1.getResponseMessage();
                if (incoming1 != null){
                    System.out.println("recieved msg : " + incoming1.message + " from : " + incoming1.sender);
                    TimeUnit.MILLISECONDS.sleep(2000);
                    break;
                }
            }
        }
    }
}
