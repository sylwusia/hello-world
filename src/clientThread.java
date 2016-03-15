import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Sylwia on 2016-03-13.
 */
public class clientThread extends Thread{


    private Socket socketOfThread;
    private PrintWriter out;
    private BufferedReader in;
    private server cos;

    public void run(){
        String inputLine;
        try{
            while ((inputLine = in.readLine()) != null) {
                Parse(inputLine);


             }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void Parse(String msg)
    {
        //register <nazwa uzytkownika>
        //register Sylwia
        String[] foo= msg.split(" ");
        switch (foo[0])
        {
            case "register":
                cos.registerUser(foo[1],this);
            break;
            default:
                out.println("coś ci nie pyklo , zastosuj sie do instrukcji");
        }


    }
    public clientThread(Socket socket,server s)
    {
        cos = s;
        socketOfThread=socket;
        try {
            out =
                    new PrintWriter(socketOfThread.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socketOfThread.getInputStream()));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
