import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.Socket;
import javax.swing.border.LineBorder;

/**
 * Created by Sylwia on 2016-03-26.
 */
public class Ships implements MouseListener {


    private JButton[] buttonArray;
    private static int shipsCounter =0;
    private static int []ship={4,3,3,2,2,2,1,1,1,1};
    public static int []arrayOfShips={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private static boolean vertical= false;
    static Socket socket;

    static BufferedReader in;
    static  private PrintWriter out;

    public static void createGui(JFrame frame,Socket sta,BufferedReader a, PrintWriter g,String str) {

        socket = sta;
        in  = a;
        out  = g;
        Ships ships = new Ships();
        // frame.setJMenuBar(battlegui.createMenu());
        JPanel gui = new JPanel(new GridLayout(2, 1, 20, 5));
        JPanel inputPanel = new JPanel((new FlowLayout()));


        //gui.setBorder(new EmptyBorder(5,5,5,5));
        //Set up components preferred size
        JLabel b= new JLabel(" create your own battleship grid  ",SwingConstants.CENTER);
        b.setFont(new Font("Serif", Font.PLAIN, 60));
        inputPanel.add(b);
        JLabel i= new JLabel("           "+str+"             ",SwingConstants.CENTER);
        i.setFont(new Font("Serif", Font.PLAIN, 40));
        inputPanel.add(i);
        JButton button = new JButton("PLAY");
        button.setPreferredSize(new Dimension(150, 100));
        button.setFont(new Font("Serif", Font.PLAIN, 30));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BattleGui game = new BattleGui();




                try {
                   // socket = new Socket("localhost", 56565);
                   // in =new BufferedReader( new InputStreamReader(socket.getInputStream()));
                   // Runnable r1 = ()-> {
                       // try{
                            out.println("play test");
                            String serverAnswer;
                            while (( serverAnswer = in.readLine())!=null)
                            {
                                System.out.println(serverAnswer);
                                if(serverAnswer.equals("jupi znalazlem")) break;
                                parse(serverAnswer);

                            }



                        }catch (Exception ed) {
                            System.out.println(ed.getMessage());}


                   // };
                   // new Thread(r1).start();


               // } catch (Exception ed) {
                  //  System.out.println(ed.getMessage());}

                game.createAndShowGUI(frame,ships.arrayOfShips,socket,in,out,str);
            }
        });
        inputPanel.add(button);
        gui.add(inputPanel);
        gui.add(ships.createContentPane());
        frame.setContentPane(gui);


        // Display the window, setting the size
        frame.setSize(800, 700);
        frame.setVisible(true);
    }
    public static void parse(String msg)
    {
        String [] foo = msg.split(" ");
        switch(foo[0]) {
            case "wait":
                out.println("play test");
                break;


        }
    }
    public static void foo(String serverAnswer)
    {
        String[] foo= serverAnswer.split(" ");
        int bum ;
        switch (foo[0])
        {
            case "fire":
                bum =   Integer.parseInt(foo[1]);
                if(arrayOfShips[bum]==1)
                    sendToServer("checked 1");
                else sendToServer("checked 0");


        }

    }
    public static void sendToServer(String a) {
        try {
        socket = new Socket("localhost", 56565);
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println(a);


            } catch (Exception ed) {
                System.out.println(ed.getMessage());


        }
    }
    public Container createContentPane() {
        int numButtons = 10 * 10;
        JPanel grid = new JPanel(new GridLayout(10, 10));
         buttonArray = new JButton[numButtons];


        for (int i = 0; i < numButtons; i++) {


            buttonArray[i] = new JButton(" ");
            buttonArray[i].setActionCommand("" + i); // String "0", "1"
            buttonArray[i].addMouseListener(this);

            grid.add(buttonArray[i]);

        }
        return grid;
    }

    //public void actionPerformedd(ActionEvent e) {

        /*String classname = getClassName(e.getSource());
        JComponent component = (JComponent) (e.getSource());
        if (classname.equals("JButton"))
        {

            JButton menusource = (JButton) (e.getSource());
            String menutext  = menusource.getText();


            if (menutext.equals("PLAY"))
            {

                System.out.println("huh");
            }


        }*/
   // }

    protected String getClassName(Object o)
    {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    private int tryIfEmpty(int row,int col)
    {
        int result = 0;

      //  if(col%9==0){
            //if(buttonArray[row*col+1].getBackground()==Color.WHITE){
              //  result = 1;}
       // && row%9!=0 && buttonArray[row*col-1].getText()=="huhu"){
       // result = 1;
   // }
        return result;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
      /*  if(SwingUtilities.isLeftMouseButton(e))
        {
        String classname = getClassName(e.getSource());
        JComponent component = (JComponent) (e.getSource());
        if (classname.equals("JButton")) {
            if (shipsCounter > 0) {
                JButton button = (JButton) (e.getSource());

                int bnum = Integer.parseInt(button.getActionCommand());
                if (arrayOfShips[bnum] == 0) {
                    int row = bnum / 10;
                    int col = bnum % 10;
                    System.out.println(row + " " + col);
                    System.out.println(e.getSource());

                    shipsCounter--;

                    ImageIcon img = new ImageIcon("C:\\Users\\Sylwia\\Desktop\\cross.jpg");
                    button.setIcon(img);
                    button.setBackground(Color.WHITE);
                    button.setText("huhu");
                    arrayOfShips[bnum] = 1;
                }
            }
        }
        }*/
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {
            vertical = false;
        }
        else if(SwingUtilities.isRightMouseButton(e)) {
            vertical = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        JButton button = (JButton) (e.getSource());
        int bnum = Integer.parseInt(button.getActionCommand());
        ImageIcon img = new ImageIcon("C:\\Users\\Sylwia\\Desktop\\cross.jpg");
        Border pane =  new LineBorder(Color.red,2);
        Border border = buttonArray[bnum].getBorder();
      //LineBorder bbb =
        boolean u = pane.equals(border);
       // LineBorder bb = buttonArray[bnum].getBorder();
        if(shipsCounter<10) {

                if (vertical == false && tryIfReservedHorizontal(bnum)!=true) {


                    for (int i = 0; i < ship[shipsCounter]; i++) {

                        buttonArray[bnum + i].setIcon(img);
                        arrayOfShips[bnum + i] = 1;
                        if ((bnum + 10 + i) < 100)
                            arrayOfShips[bnum + 10 + i] = -1;
                        if ((bnum - 10 + i) >= 0)
                            arrayOfShips[bnum - 10 + i] = -1;

                    }
                    if ((bnum + ship[shipsCounter] - 1) % 10 != 9) {
                        arrayOfShips[bnum + (ship[shipsCounter])] = -1;
                        if ((bnum + (ship[shipsCounter]) - 10) >= 0)
                            arrayOfShips[bnum + (ship[shipsCounter]) - 10] = -1;
                        if ((bnum + (ship[shipsCounter]) + 10) < 100)
                            arrayOfShips[bnum + (ship[shipsCounter]) + 10] = -1;
                    }
                    if (bnum % 10 != 0) {
                        arrayOfShips[bnum - 1] = -1;
                        if ((bnum - 11) >= 0)
                            arrayOfShips[bnum - 11] = -1;
                        if (bnum + 9 < 100)
                            arrayOfShips[bnum + 9] = -1;
                    }
                    shipsCounter++;
                }
                else if (vertical == true && tryIfReservedVertical(bnum)!=true) {
                    for (int i = 0; i < ship[shipsCounter]; i++) {

                        buttonArray[bnum - (i * 10)].setIcon(img);
                        arrayOfShips[bnum - (i * 10)] = 1;
                        if ((bnum - (i * 10) - 1) % 10 != 9 && (bnum - (i * 10) - 1) >= 0)
                            arrayOfShips[bnum - (i * 10) - 1] = -1;
                        if ((bnum - (i * 10) + 1) % 10 != 0)
                            arrayOfShips[bnum - (i * 10) + 1] = -1;

                    }
                    if ((bnum + 10) < 100) {
                        arrayOfShips[bnum + 10] = -1;
                        if((bnum + 11) % 10 != 0)
                        arrayOfShips[bnum + 11] = -1;
                        if ((bnum + 9) % 10 != 9)
                            arrayOfShips[bnum + 9] = -1;
                    }

                    if ((bnum - ((ship[shipsCounter] - 1) * 10) - 11) >= 0) {
                        arrayOfShips[bnum - ((ship[shipsCounter] - 1) * 10) - 11] = -1;
                        if ((bnum - ((ship[shipsCounter] - 1) * 10) - 9) % 10 != 0)
                            arrayOfShips[bnum - ((ship[shipsCounter] - 1) * 10) - 9] = -1;
                        arrayOfShips[bnum - ((ship[shipsCounter] - 1) * 10) - 10] = -1;
                    }
                    shipsCounter++;
                }



            }

         }





    @Override
    public void mouseEntered(MouseEvent e) {
        JButton button = (JButton) (e.getSource());
        int bnum = Integer.parseInt(button.getActionCommand());
        LineBorder pane =  new LineBorder(Color.blue,2);
        LineBorder paneEdge =  new LineBorder(Color.blue,2);
        LineBorder wrongPane = new LineBorder(Color.red,2);
        int col = bnum % 10;
        if(shipsCounter<10) {

            if(tryIfReservedHorizontal(bnum)==true)pane=wrongPane;
            if(tryIfReservedVertical(bnum)==true)paneEdge=wrongPane;

            for(int i=0;i<ship[shipsCounter];i++) {

                if((bnum + i)>=0 && (bnum + i)<100)
                     buttonArray[bnum + i].setBorder(pane);
                if((bnum - (i*10))>=0 && (bnum - (i*10))<100)
                     buttonArray[bnum - (i*10)].setBorder(paneEdge);

            }

        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton button = (JButton) (e.getSource());
        int bnum = Integer.parseInt(button.getActionCommand());


        if(shipsCounter<10) {
            for(int i=0;i<ship[shipsCounter];i++){
                if((bnum + i)>=0 && (bnum + i)<100)
                    buttonArray[bnum+i].setBorder(UIManager.getBorder("Button.border"));
                if((bnum - (i*10))>=0 && (bnum - (i*10))<100)
                    buttonArray[bnum - (i*10)].setBorder(UIManager.getBorder("Button.border"));

        }}

    }
    public boolean tryIfReservedHorizontal(int bnum)
    {
        boolean reserved = false;
        if(ship[shipsCounter]==4)
        {
            if((bnum+2)%10==9 || (bnum+1)%10==9 || bnum%10==9)
                reserved = true;
        }
        else if(ship[shipsCounter]==3)
        {
            if((bnum+1)%10==9 || bnum%10==9)
                reserved = true;
        }
        else if (ship[shipsCounter]==2)
        {
            if( bnum%10==9)
                reserved = true;
        }
        for(int i=0;i<ship[shipsCounter];i++) {
            if( (bnum+i)<0 || (bnum+i)>99)
                reserved = true;
            else if (arrayOfShips[bnum+i]==1 || arrayOfShips[bnum+i]==-1 )
                reserved = true;

        }
        return reserved;
    }
    public boolean tryIfReservedVertical(int bnum)
    {
        boolean reserved = false;
        for(int i=0;i<ship[shipsCounter];i++) {
            if( bnum-(i*10)<0 || bnum-(i*10)>99 )
                reserved = true;
            else if (arrayOfShips[bnum-(i*10)]==1 || arrayOfShips[bnum-(i*10)]==-1)
                reserved = true;

        }
        return reserved;
    }
}