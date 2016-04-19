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
    static Socket socket;
    static int turn=1;
    static int winner=20;
    private static String userName;
    static BufferedReader in;
    static  private PrintWriter out;

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
        ImageIcon grassIcon = new ImageIcon("C:\\Users\\Sylwia\\Desktop\\blank.jpg");
        ImageIcon img = new ImageIcon("C:\\Users\\Sylwia\\Desktop\\cross.jpg");

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
            int bnum = Integer.parseInt(button.getActionCommand());
            int row = bnum / GRID_SIZE;
            int col = bnum % GRID_SIZE;
            //System.out.println(e.getSource());
            if(turn ==1){
                out.println("fire "+bnum);

                }

           // button.setBackground(Color.GREEN);

            fireShot(row, col);
        }
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
                    img = new ImageIcon("C:\\Users\\Sylwia\\Desktop\\red.jpg");}
                else {
                    out.println("checked 0:" + foo[1]);
                    img = new ImageIcon("C:\\Users\\Sylwia\\Desktop\\ping.jpg");
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
                    buttonArray[z].setBackground(Color.red);
                    winner--;
                }
                else {
                    System.out.println("ehhhh");
                    buttonArray[z].setBackground(Color.GRAY);
                }
                if(winner == 0) System.out.println(userName);
                break;
            case "jupi":
                // out.println("fire 10");
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


    public static void createAndShowGUI(JFrame frame, int[]arrayOfShips, Socket st, BufferedReader h, PrintWriter y,String str)
    {

        int maxGap = 20;
        int ButtonWidth = 20;
        int ButtonHeight = 1;
        socket = st;
        in = h;
        out = y;
        arrayOfShip = arrayOfShips;
        userName = str;
        BattleGui battlegui = new BattleGui();
        // frame.setJMenuBar(battlegui.createMenu());
        JPanel gui = new JPanel(new GridLayout(2,3,20,5));

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
        gui.add(a);
        // b.setPreferredSize(new Dimension(ButtonWidth, ButtonHeight));
        gui.add(battlegui.createContentPane(arrayOfShips));
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
        frame.setSize(1000, 700);
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



}
