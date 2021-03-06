/**
 * Created by Sylwia on 2016-03-26.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
//import javax.swing.border.*;
//import java.io.*;

public class BattleGui implements ActionListener
{
    private final static String DEFAULT_FILENAME = "battlegui.txt";
    private int GRID_SIZE = 10;
    private static JButton [] buttonArray;
    private static JLabel []labels;
    private static int []arrayOfShip;
    static int [] array = new  int[100];
    static Socket socket;
    static int turn=1;
    static int counter = 1;
    static Timer timer= new Timer(500, new MyTimerActionListener());
    static int winner;
    private static String userName;
    static BufferedReader in;
    static  private PrintWriter out;
    static JButton     sendMessage;
    static JTextField  messageBox;
    static JTextArea   chatBox;
    static  JFrame frame;
    private static String []res ;
   /* public JMenuBar createMenu()
    {
        JMenuBar menuBar  = new JMenuBar();;
        JMenu menu = new JMenu("Battle Menu");
        JMenuItem menuItem;

        menuBar.add(menu);

        menuItem = new JMenuItem("New Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Load Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save Game");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        // submenu
        menu.addSeparator();
        return menuBar;
    }*/

    public  Container createContentPaneCPU()
    {
        int numButtons = GRID_SIZE * GRID_SIZE;
        JPanel grid = new JPanel(new GridLayout(GRID_SIZE,GRID_SIZE));
        buttonArray = new JButton[numButtons];


        for (int i=0; i<numButtons; i++)
        {
            buttonArray[i] = new JButton(" ");

            // This  button was clicked in actions listener
            buttonArray[i].setActionCommand("" + i); // String "0", "1" itd.
            buttonArray[i].addActionListener(this);
            grid.add(buttonArray[i]);

        }
        return grid;
    }

    public  Container createContentPane(int []arrayOfShips)
    {
        int numButtons = GRID_SIZE * GRID_SIZE;
        JPanel grid = new JPanel(new GridLayout(GRID_SIZE,GRID_SIZE));
        labels = new JLabel[(10 * 10)];
        ImageIcon grassIcon = new ImageIcon("blank.jpg");
        ImageIcon img = new ImageIcon("cross.jpg");

        for (int i = 0; i < numButtons; i++) {

            if(arrayOfShips[i]==1)
                labels[i] = new JLabel(img);
            else{
                labels[i] = new JLabel(grassIcon);}
            //labels[i].setSize(5,5);
            labels[i].setBackground(Color.gray);
            labels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            grid.add(labels[i]);
        }
        return grid;
    }

    /**
     * iwent z Menu  board.
     *
     */
   /* class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
                // do nothing
            } else if (messageBox.getText().equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                chatBox.append("<" + userName + ">:  " + messageBox.getText()
                        + "\n");

                out.println("message "+userName+":"+messageBox.getText());
                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();
        }
    }*/

    private  void setWinner()
    {
        int sum=0;
        for(int i=0;i<100;i++)
        {
            if(arrayOfShip[i]==1)sum++;
        }
        winner=sum;
    }
    public void actionPerformed(ActionEvent e)
    {
        String classname = getClassName(e.getSource());
        JComponent component = (JComponent)(e.getSource());

        if (classname.equals("JMenuItem"))
        {
            JMenuItem menusource = (JMenuItem)(e.getSource());
            String menutext  = menusource.getText();


            if (menutext.equals("Load Game"))
            {
                /* BATTLEGUI    Add your code here to handle Load Game **********/
                LoadGame();
            }
            else if (menutext.equals("Save Game"))
            {
                /* BATTLEGUI    Add your code here to handle Save Game **********/
                SaveGame();
            }
            else if (menutext.equals("New Game"))
            {
                /* BATTLEGUI    Add your code here to handle Save Game **********/
                NewGame();
            }
        }

        else if (classname.equals("JButton"))
        {
            JButton button = (JButton)(e.getSource());
            String text  = button.getText();
            if(text.equals("Send Message"))
            {
                if (messageBox.getText().length() < 1) {
                    // do nothing
                } else if (messageBox.getText().equals(".clear")) {
                    chatBox.setText("Cleared all messages\n");
                    messageBox.setText("");
                } else {
                    chatBox.append("<" + userName + ">:  " + messageBox.getText()
                            + "\n");

                    out.println("message "+userName+":"+messageBox.getText());
                    messageBox.setText("");
                }
                messageBox.requestFocusInWindow();
            }
            else
            {
                int bnum = Integer.parseInt(button.getActionCommand());
                int row = bnum / GRID_SIZE;
                int col = bnum % GRID_SIZE;
                //System.out.println(e.getSource());
                if(turn ==1){
                    out.println("fire "+bnum);

                }

                // button.setBackground(Color.GREEN);

                fireShot(row, col);
            }}
    }


    public static void parse(String msg) {
        String[] foo = msg.split(" ");
        switch (foo[0]) {
            case "wait":
                out.println("play test");
                break;
            case "bum":
                turn =1;
                ImageIcon img;
                int a = Integer.parseInt(foo[1]);
                if (arrayOfShip[a] == 1)
                {out.println("checked 1:"+foo[1]);
                    img = new ImageIcon("1.jpg");
                    array[a]=1;
                    timer.start();}
                else {
                    out.println("checked 0:" + foo[1]);
                    img = new ImageIcon("water1.jpg");
                }
                labels[a].setIcon(img);
                System.out.println("bum");

                break;
            case "summary":
                turn =0;
                System.out.println("summary");
                String []hh = foo[1].split(":");
                int b = Integer.parseInt(hh[0]);
                int z = Integer.parseInt(hh[1]);
                if (b == 1) {
                    System.out.println("jupii");
                    ImageIcon imgg  = new ImageIcon("fire.jpg");
                    buttonArray[z].setIcon(imgg);
                    //.setBackground(Color.red);
                    winner--;
                }
                else {
                    System.out.println("ehhhh");
                    ImageIcon im  = new ImageIcon("water.jpg");
                    buttonArray[z].setIcon(im);
                    //buttonArray[z].setBackground(Color.GRAY);
                }
                if(winner <= 0) {


                    // out.println("stat "+userName);
                    WinnerPage win = new WinnerPage();
                    win.createGui(frame,userName);
                    timer.stop();
                    out.println("winner "+userName);
                }

                break;
            case "you":


                LoserPage lose = new LoserPage();
                lose.createGui(frame,userName);
                timer.stop();
                out.println("loser "+userName);
                break;
            case "jupi":
                // out.println("fire 10");
                break;
            case "message":
                String []jj = foo[1].split(":");
                String ans=jj[1]+" ";
                for(int i=2;i<foo.length;++i)
                    ans+=(foo[i]+" ");
                //System.out.println(ans);
                chatBox.append("<" + jj[0] + ">:  " + ans
                        + "\n");
                break;
            case "stat":
                //res =new String[2];
                res[0] = foo[1];
                res[1] = foo[2];


                break;

        }


    }
    /**
     *  Returns the class name
     */
    protected String getClassName(Object o)
    {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }


    public void createAndShowGUI(JFrame fr, int[] arrayOfShipss,
                                 Socket st, BufferedReader h, PrintWriter y, String str)
    {

        int maxGap = 20;
        int ButtonWidth = 20;
        int ButtonHeight = 1;
        socket = st;
        in = h;
        out = y;
        frame = fr;
        arrayOfShip = arrayOfShipss;

        userName = str;
        setWinner();
        BattleGui battlegui = new BattleGui();
        // frame.setJMenuBar(battlegui.createMenu());
        JPanel gui = new JPanel(new GridLayout(2,3,20,5));
        //JPanel text = new JPanel((new FlowLayout()));
        JLabel ll = new JLabel("shoot all enemy ships to win",SwingConstants.CENTER);
        ll.setFont(new Font("Serif", Font.PLAIN, 28));
        //gui.setBorder(new EmptyBorder(5,5,5,5));
        //Set up components preferred size
        //Dimension buttonSize = b.getPreferredSize();
        JLabel b = new JLabel("You",SwingConstants.CENTER);
        b.setFont(new Font("Serif", Font.PLAIN, 60));
        JLabel a = new JLabel("Enemy",SwingConstants.CENTER);
        a.setFont(new Font("Serif", Font.PLAIN, 60));


        gui.add(b);
        gui.add(ll);
        gui.add(a);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(this);

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 12));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);


        // b.setPreferredSize(new Dimension(ButtonWidth, ButtonHeight));
        gui.add(battlegui.createContentPane(arrayOfShip));
        gui.add(mainPanel);
        gui.add(battlegui.createContentPaneCPU());
        frame.setContentPane(gui);

        /*
        BattleGui battlegui = new BattleGui();
        frame.setJMenuBar(battlegui.createMenu());
        frame.setContentPane(battlegui.createContentPane());
        */
        try {

            Runnable r1 = ()-> {
                try{

                    String inputLine;
                    while((inputLine=in.readLine())!=null)
                    {

                        parse(inputLine);
                    }



                }catch (Exception ed) {
                    System.out.println(ed.getMessage());}


            };
            new Thread(r1).start();


        } catch (Exception ed) {
            System.out.println(ed.getMessage());}
        // Display the window, setting the size
        frame.setSize(1200, 700);
        frame.setVisible(true);
    }





    public void NewGame()
    {
        System.out.println("New game selected");

    }


    /**
     * This method is called from the Menu event: Load Game.
     * BATTLEGUI
     */
    public void LoadGame()
    {
        System.out.println("Load game selected");
    }


    /**
     * This method is called from the Menu event: Save Game.
     * BATTLEGUI
     */
    public void SaveGame()
    {
        System.out.println("Save game selected");
    }

    /**
     * This method is called from the Mouse Click event.
     * BATTLEGUI
     */
    public void fireShot(int row, int col)
    {
        System.out.println("Fire shot selected: at (" + row + ", " + col + ")");
    }


    private static class MyTimerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ImageIcon img;
            if(counter==4)counter=0;
            counter++;
            for(int i=0;i<100;++i)
            {
                if(array[i]==1){
                    img = new ImageIcon(counter+".jpg");
                    labels[i].setIcon(img);}

            }

        }
    }
}
