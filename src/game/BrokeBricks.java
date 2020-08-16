package game;

import javax.swing.JFrame;


public class BrokeBricks {
    JFrame f;
    BrokeBricks()
    {
        f=new JFrame();
        GamePlay game=new GamePlay();
        f.setBounds(10,10,700,600);
        f.setVisible(true);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(game);
    }
    public static void main(String...args)
    {
        new BrokeBricks();
    }
}
