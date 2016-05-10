import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.AttributedCharacterIterator;

/**
 * Created by Sylwia on 2016-04-04.
 */
public class StartPage extends JPanel implements ActionListener {

    static Socket socket;
    static String str;
    static String pass;
    private static PrintWriter out;
    private static BufferedReader in;
    public static JFrame frame1;
    public JTextField userText;
    public JPasswordField userText1;
    public JLabel message;

    public void createGui(JFrame frame) {
        frame1 = frame;
        JPanel main = new JPanel(new FlowLayout());


        try {
            socket = new Socket("localhost", 56565);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JLabel b = new JLabel("Battleship", SwingConstants.CENTER);
        b.setFont(new Font("Serif", Font.PLAIN, 80));
        JLabel background1 = new JLabel(new ImageIcon("595.jpg"));


        JLabel namelabel = new JLabel("User name: ", JLabel.RIGHT);
        namelabel.setFont(new Font("Serif", Font.PLAIN, 28));
        userText = new JTextField();
        userText.setFont(new Font("Serif", Font.PLAIN, 24));
        userText.setPreferredSize(new Dimension(240, 40));
        userText.addActionListener(this);


        ImageIcon img = new ImageIcon("zaloguj.jpg");
        JButton button = new JButton("login");
        button.setPreferredSize(new Dimension(280, 110));
        button.setFont(new Font("Serif", Font.PLAIN, 28));
        button.addActionListener(this);
        button.setIcon(img);

        JLabel  passlabel= new JLabel("Password: ", JLabel.RIGHT);
        passlabel.setFont(new Font("Serif", Font.PLAIN, 28));
        userText1 = new JPasswordField();
        userText1.setEchoChar('*');
        userText1.setFont(new Font("Serif", Font.PLAIN, 28));
        userText1.setPreferredSize(new Dimension(240,40));
        userText1.addActionListener(this);


        ImageIcon imgg = new ImageIcon("zarejestruj.jpg");
        JButton button1 = new JButton("register");
        button1.setPreferredSize(new Dimension(280, 110));
        button1.setFont(new Font("Serif", Font.PLAIN, 28));
        button1.addActionListener(this);
        button1.setIcon(imgg);

        message = new JLabel(" ");
        message.setFont(new Font("Serif", Font.PLAIN, 20));

        main.add(background1);
        main.add(namelabel);
        main.add(userText);
         main.add(passlabel);
         main.add(userText1);
        main.add(button);
        main.add(button1);
        main.add(message);

        try {

            Runnable r2 = ()-> {

                try {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        if (parset(inputLine) == true)
                        {
                            break;}
                    }


                } catch (Exception ed) {
                    System.out.println(ed.getMessage());
                }
            };
            new Thread(r2).start();


        } catch (Exception ed) {
            System.out.println(ed.getMessage());}

        frame.setContentPane(main);
        frame.pack();
        frame.setSize(800, 800);
        frame.setVisible(true);
    }


    @Override
    public void paintComponents(Graphics g) {
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File("strawberry.jpg"));
            super.paintComponent(g);
            g.drawImage(icon, 0, 0, 1000, 600, null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String classname = getClassName(e.getSource());
        JComponent component = (JComponent) (e.getSource());

        if (classname.equals("JMenuItem")) {
            JMenuItem menusource = (JMenuItem) (e.getSource());
            String menutext = menusource.getText();


            if (menutext.equals("Load Game")) {

                // LoadGame();
            } else if (menutext.equals("Save Game")) {

                // SaveGame();
            }

        } else if (classname.equals("JButton")) {
            JButton button = (JButton) (e.getSource());
            String menutext = button.getText();
            if (menutext.equals("register")) {
                //registerInServer();
                register(true);


            } else if (menutext.equals("login")) {
               // registerInServer();
                register(false);

            }


        }
    }


    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1);
    }

    public boolean parset(String msg) {
        String[] foo = msg.split(" ");
        switch (foo[0]) {
            case "nick":
                if (foo[1].equals("wolny")){
                    Ships ships = new Ships();
                    ships.createGui(frame1, socket, in, out, str);
                    return true;
                }
                else return false;

            case "zalogowano":
                if (foo[1].equals("tak")){
                    Ships ships = new Ships();
                    ships.createGui(frame1, socket, in, out, str);
                    return true;
                }
                else return false;

        }
        return false;
    }

    public void registerInServer() {

    }
    public boolean register(boolean x){
        str = userText.getText();
        char []a = userText1.getPassword();
        pass = String.valueOf(a);

        if (x == true)
            out.println("register " + str+":"+pass);
        else out.println("login " + str+":"+pass);
        out.flush();



        return false;
    }
}

