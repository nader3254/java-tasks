package customUDP;

// Java program to illustrate Server side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.*;


public class myudp 
{
    DatagramSocket ds ;
    byte[] receive;
    byte[] send;
    DatagramPacket DpReceive;
    Scanner sc;
    int _port;
    public myudp(int port) throws IOException
    {
        _port = port;
        System.out.println("starting udp service on "+port);
        ds = new DatagramSocket();
        receive = new byte[65535];
        send = null;
        DpReceive = null;
        sc = new Scanner(System.in);
    }

    public String receive() throws IOException
    {
        String str = "";
        try 
        {

            // ds.connect(InetAddress.getLocalHost(), _port);
            ds = new DatagramSocket(_port, InetAddress.getLocalHost());
            byte[] receive = new byte[65535];
            DpReceive = new DatagramPacket(receive, receive.length);
            ds.receive(DpReceive);
            str = data(receive).toString();
            System.out.println("requested :-" + data(receive));
            ds = new DatagramSocket();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return str;
    
    }
    public void send(String inp) throws IOException
    {
        try 
        {
            System.out.println("enter your message: ");
            //String inp = sc.nextLine();
            send = inp.getBytes();
            DatagramPacket DpSend =
                  new DatagramPacket(send, send.length, InetAddress.getLocalHost(),_port);
            ds.send(DpSend);
            ds = new DatagramSocket();
        }catch (Exception e) {
            // TODO: handle exception
        }
    }
    // A utility method to convert the byte array
    // data into a string representation.
    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}
