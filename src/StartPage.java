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
public class StartPage extends JPanel implements ActionListener{

    static Socket socket;
    static String str;
    private static PrintWriter out;
    private static BufferedReader in;
    public static JFrame frame1;
    public JTextField userText;
    public void createGui(JFrame frame)
    {
        frame1 = frame;
        JPanel main = new JPanel(new FlowLayout());

        JLabel b= new JLabel("Battleship",SwingConstants.CENTER);
        b.setFont(new Font("Serif", Font.PLAIN, 80));
        JLabel background1 = new JLabel(new ImageIcon("C:\\Users\\Sylwia\\Desktop\\595.jpg"));



        JLabel  namelabel= new JLabel("User name: ", JLabel.RIGHT);
        namelabel.setFont(new Font("Serif", Font.PLAIN, 28));
         userText = new JTextField();
        userText.setFont(new Font("Serif", Font.PLAIN, 24));
        userText.setPreferredSize(new Dimension(240,40));
        userText.addActionListener(this);


        ImageIcon img = new ImageIcon("C:\\Users\\Sylwia\\Desktop\\hyh.jpg");
        JButton button = new JButton("register");
        button.setPreferredSize(new Dimension(220, 80));
        button.setFont(new Font("Serif", Font.PLAIN, 28));
        button.addActionListener(this);
        button.setIcon(img);

        main.add(background1);
        main.add(namelabel);
        main.add(userText);
        main.add(button);


        frame.setContentPane(main);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);
    }





    @Override
    public void paintComponents(Graphics g) {
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File("strawberry.jpg"));
            super.paintComponent(g);
            g.drawImage(icon, 0, 0, 1000, 600, null);
        }catch(Exception e){System.out.println(e.getMessage());}
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        String classname = getClassName(e.getSource());
        JComponent component = (JComponent)(e.getSource());

        if (classname.equals("JMenuItem"))
        {
            JMenuItem menusource = (JMenuItem)(e.getSource());
            String menutext  = menusource.getText();


            if (menutext.equals("Load Game"))
            {

               // LoadGame();
            }
            else if (menutext.equals("Save Game"))
            {

               // SaveGame();
            }

        }

        else if (classname.equals("JButton"))
        {
            JButton button = (JButton)(e.getSource());
            String menutext  = button.getText();
            if(menutext.equals("register"))
            {
                registerInServer();
                Ships ships = new Ships();
                 ships.createGui(frame1,socket,in,out,str);

            }




        }
    }



    protected String getClassName(Object o)
    {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    public void registerInServer()
    {
        try {
            socket = new Socket("localhost", 56565);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        str = userText.getText();
        out.println("register "+str);
        out.flush();
    }
}
