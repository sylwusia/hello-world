

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Database {
    public static final String DB_URL = "jdbc:sqlite:data_db";
    public static final String DRIVER = "org.sqlite.JDBC";
    public static  Connection c = null;
    public Database()
    {

        try {
//Class.forName("org.sqlite.jdbc");
            c = DriverManager.getConnection("jdbc:sqlite:E:\\java\\data_db");

            Statement stmt = c.createStatement();
           /* String sql = "CREATE TABLE USERS " +
                    "(nick TEXT PRIMARY KEY NOT NULL  ," +
                    " password TEXT    NOT NULL, " +
                    " data TEXT    NOT NULL, " +
                    " WIN            INT, " +
                    " LOSE        INT, " +
                    " STATUS        INT)";
            stmt.executeUpdate(sql);*/


            stmt.close();


            //c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
    public synchronized void deleteUser( String name ) throws Exception{
        Statement smt = null;
        smt = c.createStatement();
        String sqlQuery = "DELETE FROM USERS WHERE nick='"+ name+"' ; ";
        smt.executeUpdate(sqlQuery);
       // c.commit();
    }
    public synchronized void droptable( ) throws Exception{
        Statement smt = null;
        smt = c.createStatement();
        String sqlQuery = "DROP TABLE USERS";
        smt.executeUpdate(sqlQuery);
       // c.commit();
    }
    public synchronized boolean addUser( String nickk ) throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String  timee = dateFormat.format(cal.getTime()); //2014/08/06 16:00:22

        String []dane= nickk.split(":");

        Statement stmt = null;
        stmt = c.createStatement();
        String sqlQuery = "SELECT * FROM USERS";
        ResultSet res  = stmt.executeQuery( sqlQuery );
        String dbname;
        while( res.next() ){
             dbname = res.getString( "nick" );
            System.out.println(dbname);
            if(dbname.equals(dane[0]) ) return false;

        }
        Statement smt = null;
        smt = c.createStatement();
        System.out.println(nickk + "/n");
        sqlQuery = "INSERT INTO USERS (nick,password,data,WIN,LOSE,STATUS)  VALUES ('"+dane[0]+"','"+dane[1]+"','"+timee+"',0,0,0);";
               // "( " + nick + ", " + dateFormat.format(cal.getTime()) + ", "
             //   + 0 + ", " + 0 + ", " + 0 + ");";

        smt.execute(sqlQuery);
        smt.close();
        //c.commit();
        return true;
    }
    public synchronized boolean loginUser( String nickk ) throws Exception{

        Statement stmt = null;
        stmt = c.createStatement();
        String sqlQuery = "SELECT * FROM USERS";
        ResultSet res  = stmt.executeQuery( sqlQuery );
        String dbname,passw;
        String []dane = nickk.split(":");
        while( res.next() ){
            dbname = res.getString( "nick" );
            System.out.println(dbname);
            if(dbname.equals(dane[0]) ){
                passw = res.getString("password");
                if(passw.equals(dane[1]))
                    return false;}
        }
        return true;
    }
    public synchronized void addWin(String nickk) throws Exception
    {
        Statement smt = null;
        smt = c.createStatement();

        String sqlQuery = "SELECT WIN FROM USERS WHERE nick='"+nickk+"'; ";


        ResultSet rest  = smt.executeQuery(sqlQuery);
        int val =  ((Number) rest.getObject(1)).intValue();
        ++val;
        smt.close();

        smt=null;
        smt = c.createStatement();

        sqlQuery ="UPDATE USERS SET WIN='" +val+ "' WHERE nick='"+nickk+"';";
        smt.executeQuery(sqlQuery);
        smt.close();
    }
    public synchronized void addLose(String nickk) throws Exception
    {
        Statement smt = null;
        smt = c.createStatement();

        String sqlQuery = "SELECT LOSE FROM USERS WHERE nick='"+ nickk+"' ; ";


        ResultSet rest  = smt.executeQuery(sqlQuery);
        int val =  ((Number) rest.getObject(1)).intValue();
        ++val;
        smt.close();

        smt=null;
        smt = c.createStatement();

        sqlQuery ="UPDATE USERS SET LOSE='" +val+ "' WHERE nick='"+nickk+"';";
        smt.executeQuery(sqlQuery);
        smt.close();
    }
    public synchronized int [] getStats(String nickk) throws Exception
    {
        Statement smt = null;
        smt = c.createStatement();

        String sqlQuery = "SELECT WIN FROM USERS WHERE nick='"+ nickk+"' ; ";
        ResultSet r = smt.executeQuery(sqlQuery);
        int []val= new int [2];
        val[0] =  ((Number) r.getObject(1)).intValue();

        smt= null;
        smt = c.createStatement();
        sqlQuery ="SELECT LOSE FROM USERS WHERE nick='"+ nickk+"' ; ";
        ResultSet re = smt.executeQuery(sqlQuery);
        val[1] =  ((Number) re.getObject(1)).intValue();
        return val;
    }
}