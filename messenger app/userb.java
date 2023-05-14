import java.io.IOException;
import customUDP.myudp;
import java.util.*;

class serviceRunner extends Thread {
    myudp udp;
    String op;

    public serviceRunner(myudp ud, String opr) {
        udp = ud;
        op = opr;
    }

    public void run() {
        // System.out.println("thread is running...");
        while (true) {
            if (op == "reciever") {
                try {
                    udp.receive();
                } catch (IOException e) {

                }

            } else {
                try {
                    udp.send();
                } catch (IOException e) {

                }
            }
        }
    }

}

public class userb {
    // myudp udp = new myudp(1234);

    public static void main(String[] args) throws IOException {

        System.out.println("############################################### ");
        System.out.println("     This is a messenger Program For User B   ");
        System.out.println("############################################### ");
        myudp udp = new myudp(4567);
        myudp udp2 = new myudp(1234);
        serviceRunner reciever = new serviceRunner(udp, "reciever");
        serviceRunner sender = new serviceRunner(udp2, "sender");
        sender.start();
        reciever.start();

    }
}
