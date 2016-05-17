import javax.swing.*;

        import static java.lang.Thread.sleep;

/**
 * Created by Sylwia on 2016-03-26.
 */
public class Run {

    public Run()
    {
        JFrame frame = new JFrame("Battleships: Sylwia Nawój");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        StartPage page = new StartPage();
        page.createGui(frame);
        //BattleGui game= new BattleGui();
       // Ships ships = new Ships();
       // ships.createGui(frame);

       // game.createAndShowGUI(frame,ships.arrayOfShips);

    }


}
