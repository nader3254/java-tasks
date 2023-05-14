import java.io.File;
import java.io.*;
import customUDP.myudp;
import java.util.*;

class UDPcmdLine extends Thread {
    myudp udp;
    String op;
    myudp udp2;
    List<String> musicList, videoList;

    public void executeCmd(String cmd) {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", cmd);
        builder.redirectErrorStream(true);
        try {
            builder.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public UDPcmdLine(myudp ud, String opr, List<String> m, List<String> v) throws IOException {
        udp = ud;
        op = opr;
        udp2 = new myudp(4567);
        musicList = m;
        videoList = v;

    }

    public void run() {
        while (true) {

            try {
                String rec;
                rec = udp.receive();

                if (rec != "") {
                    // System.out.println("herte" + rec);
                    if (rec.contains("list music")) {
                        String tmp = "";
                        for (String str : musicList) {
                            tmp += str + "   ";
                        }
                        System.out.println(tmp);
                        udp2.send(tmp);
                    }
                    if (rec.contains("list videos")) {
                        String tmp = "";
                        for (String str : videoList) {
                            tmp += str + "  ";
                        }
                        System.out.println(tmp);
                        udp2.send(tmp);
                    }
                    if (rec.contains("musicplay")) {
                        String tt= rec.replace("musicplay", "");
                        String ttt=tt.replace(" ", "");
                        System.out.println(ttt);
                        if (musicList.contains(ttt)) {
                            String rt = ".\\music\\" + ttt;
                            executeCmd(rt);
                            udp2.send("ok");
                        } else {
                            udp2.send("file not found");
                        }
                    }
                    if (rec.contains("videoplay")) {
                        String tt = rec.replace("videoplay", "");
                        String ttt = tt.replace(" ", "");
                        System.out.println(ttt);
                        if (videoList.contains(ttt)) {
                            String rt = ".\\video\\" + ttt;
                            executeCmd(rt);
                            udp2.send("ok");
                        } else {
                            udp2.send("file not found");
                        }
                    }
                }

            } catch (IOException e) {

            }

        }
    }

}

public class mediaPlayer {

    public static List<String> displayDirectory(final File folder) {

        List<String> arr = new ArrayList<String>();
        for (int i = 0; i < folder.listFiles().length; i++) {
            arr.add(folder.listFiles()[i].getName());
        }

        return arr;

    }

    public static void main(String[] args) throws IOException {

        System.out.println("#######################################################");
        System.out.println("#######################################################");
        System.out.println("################## Remote Media Player ################");
        System.out.println("#######################################################");
        System.out.println("#######################################################");
        final File folder = new File(".\\music");
        final File folder2 = new File(".\\video");
        List<String> musics = displayDirectory(folder);
        List<String> videos = displayDirectory(folder2);

        myudp udp = new myudp(1234);
        UDPcmdLine mycmd = new UDPcmdLine(udp, "null", musics, videos);
        mycmd.start();

    }
}

