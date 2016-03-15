import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Sylwia on 2016-03-13.
 */
public class server {

    private ServerSocket serverSocket = null;
    private HashMap<clientThread,String > p12 = new HashMap<>();

    public server() {
        try {
            serverSocket = new ServerSocket(56565);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public void registerUser(String user,clientThread thread)
    {
        p12.put(thread,user);


    }

    public clientThread findEnemyUser(clientThread user)
    {
        for (clientThread key : p12.keySet()) {
            if(key!=user)
            {
                p12.remove(key);
                return key;
            }
        }
        return null;
    }

    public void start() {
        try {
            while (true) {
                Socket client = serverSocket.accept();
                clientThread user = new clientThread(client,this);
                user.run();


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}