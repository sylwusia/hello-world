
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sylwia on 2016-04-23.
 */
public class LoserPage implements ActionListener{

    static JFrame frame;
    public void createGui(JFrame fr, String userName)
    {
        frame = fr;
        JPanel mainPanel = new JPanel(new FlowLayout());

        JLabel label = new JLabel("the loser is\n "+userName+"          ",SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 60));
        JButton button = new JButton("Play Again");
        button.setPreferredSize(new Dimension(300, 100));
        button.setFont(new Font("Serif", Font.PLAIN, 30));
        button.addActionListener(this);

        JLabel ship = new JLabel();
        ImageIcon img = new ImageIcon("loser.jpg");
        ship.setIcon(img);
        mainPanel.add(label);
        mainPanel.add(button);
        mainPanel.setBackground(new Color(39,119,187));
        mainPanel.add(ship);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StartPage page = new StartPage();
        page.createGui(frame);
    }
}
