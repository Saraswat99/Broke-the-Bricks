package game;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ENTER;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePlay extends JPanel implements ActionListener,KeyListener{    
    private boolean play=false;
    private int score=0;
    private int totalBricks=21;
    private Timer timer;
    private int delay=8;
    private int playerX=310;
    private int ballposX=120;
    private int ballposY=350;
    private int ballXdir=-3 ;
    private int ballYdir=-3;
    private MapGenerator map1;
    
    
    GamePlay()
    {
        map1=new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }
    
    public void paint(Graphics g)
    {
        g.setColor(Color.gray);
        g.fillRect(1, 1, 693, 592);
        
        map1.draw((Graphics2D)g);
        
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(681, 0, 3, 592);
        
        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 8);
        
        
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,580,30);
        
        if(ballposY>590||totalBricks==0)
        {
            play=false;
            g.setColor(Color.white);
            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString("Game Over , Total score :"+score,250,300);
            g.drawString("Press Enter to Restart", 250, 350);
        }
        g.setColor(Color.red);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        g.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent arg0){
        timer.start();
        if(play){
        if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))
        {
            ballYdir=-ballYdir;         // direction of ball changed to another dir. in Y coordinates on hitting the bar
        }
        
        A:for(int i=0;i<map1.map.length;i++)
        {
            for(int j=0;j<map1.map[0].length;j++)
            {
                if(map1.map[i][j]>0){
               int brickX=j*map1.brickwidth+80;
               int brickY=i*map1.brickheight+50;
               int brickwidth=map1.brickwidth;
               int brickheight=map1.brickheight;
               
               Rectangle brickrect=new Rectangle(brickX,brickY,brickwidth,brickheight);
               Rectangle ballrect=new Rectangle(ballposX,ballposY,20,20);
            
               
               if(ballrect.intersects(brickrect))
               {
                   map1.setBrickValue(0, i, j);
                   totalBricks--;
                   score+=5;
                   
                   if(ballposX+19<=brickrect.x||ballposX+1>=brickrect.x+brickrect.width)
                       ballXdir=-ballXdir;
                   else
                       ballYdir=-ballYdir;
                 break ;  
                }
                }
            }
        }
            ballposX+=ballXdir;              //just ranomised the directions of ball on hitting with wall or border of frame.     
            ballposY+=ballYdir;
            if(ballposX<0)
                ballXdir=-ballXdir;
            if(ballposY<0)
                ballYdir=-ballYdir;
            if(ballposX>665)
                ballXdir=-ballXdir; 
    }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    @Override
    public void keyPressed(KeyEvent e){ 
        if(e.getKeyCode() ==KeyEvent.VK_RIGHT)
        {
            if(playerX>560)
                playerX=560;
            else
            {
               play=true;
               playerX+=20;
            }
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT)
        {
            if(playerX<23)
                playerX=23;
            else
            {
                play=true;
                playerX-=20;
            }
        }
        else if(e.getKeyCode()==VK_ENTER)
        {
          if(!play)
          {
            playerX=310;
            ballposX=120;
            ballposY=350;
            ballXdir=-3;
            ballYdir=-3;
            score=0;
            map1=new MapGenerator(3,7);
          }
        }
    }

   
    


}
