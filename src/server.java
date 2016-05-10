import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Sylwia on 2016-03-13.
 */
public class server {

    private ServerSocket serverSocket = null;
    private HashMap<clientThread,String > p12 = new HashMap<>();
    public Database database = new Database();
    public server() {
        try {

            serverSocket = new ServerSocket(56565);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public synchronized boolean registerUser(String user,clientThread thread)
    {
        boolean a ;
        try {

             a = database.addUser(user);
            if(a == false){return false;}
            else {
                p12.put(thread, user);
                return true;
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());}
        return true;

    }
    public synchronized boolean loginUser(String user,clientThread thread)
    {
        boolean a ;
        try {

            a = database.loginUser(user);
            if(a == false)
            {
                p12.put(thread, user);
                return false;
            }
            else {

                return true;
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());}
        return true;

    }
    public synchronized int [] statystyki(String user)throws Exception
    {
        int []result;


            result = database.getStats(user);

        return result;

    }
    public synchronized void removeUser(String user, clientThread thread)
    {
        try {
            database.deleteUser(user);
        }catch(Exception e) {
            System.out.println(e.getMessage());}
        p12.remove(thread);
    }

    public synchronized clientThread findEnemyUser(clientThread user)
    {
        for (clientThread key : p12.keySet()) {
            if(key!=user)
            {
                p12.remove(key);
                //p12.remove(user);
                return key;
            }
        }
        return null;
    }
    public synchronized void addWinn(String nickk)
    {
        try {database.addWin(nickk);
        }catch(Exception e) {
            System.out.println(e.getMessage());}
    }
    public synchronized void addLoser(String nickk)
    {
        try {database.addLose(nickk);
        }catch(Exception e) {
            System.out.println(e.getMessage());}
    }
    public void start() {
        try {
            while (true) {
                Socket client = serverSocket.accept();
                clientThread user = new clientThread(client,this);
                user.start();
                //usunięcie wątków

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {database.droptable();
        }catch(Exception e) {
            System.out.println(e.getMessage());}
    }
}