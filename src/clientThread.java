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
    private clientThread enemy;

   // private PrintWriter enemyout;
   // private BufferedReader enemyin;

    @Override
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

        String[] foo= msg.split(" ");
        switch (foo[0])
        {
            case "register":
                cos.registerUser(foo[1],this);
            break;
            case "play":

                enemy = cos.findEnemyUser(this);
                if(enemy!= null) {
                    String a = enemy.socketOfThread.getRemoteSocketAddress().toString();
                    System.out.println(a);
                   /* try {
                        enemyout =
                                new PrintWriter(enemy.socketOfThread.getOutputStream(), true);
                        enemyin = new BufferedReader(
                                new InputStreamReader(enemy.socketOfThread.getInputStream()));
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }*/
                     //out.println(a);
                    //enemy.out.println(this.socketOfThread.getRemoteSocketAddress().toString());
                    this.out.println("jupi znalazlem");
                    //enemy.enemyout.println("jupi znalazlem");
                }
                else out.println("wait moment");
                break;
            case "checked":

                enemy.out.println("summary "+foo[1]);
                break;

            case "fire":

                enemy.out.println("bum "+foo[1]);
                break;

            case "quit":
                try{
                    socketOfThread.close();
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
               cos.removeUser(foo[1],this);
                break;
            case "message":
                enemy.out.println("message "+foo[1]);
                break;
            default:
                out.println("użyj instrukcji");
        }


    }
    public clientThread(Socket socket,server s)
    {
        cos = s;
        socketOfThread = socket;
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
