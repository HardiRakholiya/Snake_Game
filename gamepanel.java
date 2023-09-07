import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class gamepanel extends JPanel implements ActionListener{

    static final int screenwidth = 600;
    static final int screenheight = 600;
    static final int unitsize = 35;
    static final int gameunits = (screenwidth*screenheight)/unitsize;
    static final int delay = 200;

    final  int x[] = new int[gameunits];
    final  int y[] = new int[gameunits];
    int bodyparts_snake = 5;
    int balleseaten;
    int ballx;
    int bally;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    

   gamepanel()
   {
      random = new Random();
      this.setPreferredSize(new Dimension(screenwidth, screenheight));
      this.setBackground(Color.black);
      this.setFocusable(true);
      this.addKeyListener(new MykeyAdapter());
      startgame();

   }
   public void startgame()
   {
       newball();
       running = true;
       timer = new Timer(delay , this);
       timer.start();

   }
   public void paintComponent(Graphics g)
   {
        super.paintComponent(g);
        draw(g);
   }
   public  void draw(Graphics g)
   {
      if(running){
      for(int i=0 ;i < screenheight/unitsize ; i++)
      {
        g.drawLine(i*unitsize, 0, i*unitsize, screenheight);
         g.drawLine(0,i*unitsize, screenwidth,i*unitsize);
      }
      g.setColor(Color.red);
      g.fillOval(ballx, bally, unitsize, unitsize);

      for(int i = 0 ; i < bodyparts_snake ; i++)
      {
        if(i==0)
        {
            g.setColor(Color.blue);
            g.fillRect(x[i], y[i], unitsize, unitsize);
        }
        else{
            g.setColor(new Color(20,20 , 100));
            g.fillRect(x[i], y[i], unitsize, unitsize);
        }
      }
       g.setColor(Color.ORANGE);
      g.setFont(new Font(TOOL_TIP_TEXT_KEY, Font.BOLD, 40));
      FontMetrics metrics = getFontMetrics(g.getFont());
      g.drawString("Score " + balleseaten, (screenwidth - metrics.stringWidth("Score " + balleseaten))/2, g.getFont().getSize());

    }
    else{
        gameover(g);
    }
         
   }
   public void newball()
   {
      ballx = random.nextInt((int)(screenwidth/unitsize))*unitsize;
      bally = random.nextInt((int)(screenheight/unitsize))*unitsize;
   }
   public void move()
   {
        for(int i = bodyparts_snake ; i>0 ; i--)
        {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction)
        {
         case 'U':
            y[0] = y[0]-unitsize;
            break;
         case 'D':
            y[0] = y[0] + unitsize;
            break;
         case 'L':
            x[0] = x[0]-unitsize;
            break;
         case 'R':
            x[0] = x[0] + unitsize;
            break;
        }

   }

   public void checkball()
   {
         if((x[0] == ballx) && (y[0] == bally))
         {
            bodyparts_snake++;
            balleseaten++;
            newball();
         }
   }
   public void checkcollision()
   {
        // headcollision with body 
        for(int i=bodyparts_snake ; i> 0 ; i--)
        {
            if((x[0]) == x[i] && (y[0] == y[i]))
            {
                running = false;
            }

        }
        // headcollision with left border 
        if(x[0] < 0 )
        {
           running = false;
        }
         // headcollision with right border 
         if(x[0] > screenwidth)
         {
             running = false;
         }
         

          // headcollision with top border 
          if(y[0] < 0)
          {
             running = false;
          }

           // headcollision with bottom border 
           if(y[0] > screenheight)
           {
             running = false;
           }
           if(!running)
           {
            timer.stop();
           }
   }
   public void gameover(Graphics g)
   {
    //   score
     g.setColor(Color.ORANGE);
      g.setFont(new Font(TOOL_TIP_TEXT_KEY, Font.BOLD, 40));
      FontMetrics metrics1 = getFontMetrics(g.getFont());
      g.drawString("Score " + balleseaten, (screenwidth - metrics1.stringWidth("Score " + balleseaten))/2, g.getFont().getSize());

      // game over
      g.setColor(Color.ORANGE);
      g.setFont(new Font(TOOL_TIP_TEXT_KEY, Font.BOLD, unitsize));
      FontMetrics metrics2 = getFontMetrics(g.getFont());
      g.drawString("Game Over", (screenwidth - metrics2.stringWidth("Game Over"))/2, screenheight/2);
   }



@Override
public void actionPerformed(ActionEvent e) {

    // throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");

    if(running)
    {
        move();
        checkball();
        checkcollision();
    }

    repaint();
}


 public class MykeyAdapter extends KeyAdapter{
    @Override
    public void keyPressed(KeyEvent e)
    {
       switch(e.getKeyCode())
       {
          case KeyEvent.VK_LEFT:
           if(direction != 'R')
           {
             direction = 'L';
           }
           break;
         case KeyEvent.VK_RIGHT:
           if(direction != 'L')
           {
             direction = 'R';
           }
           break;
         case KeyEvent.VK_UP:
           if(direction != 'D')
           {
             direction = 'U';
           }
           break;
        case KeyEvent.VK_DOWN:
           if(direction != 'U')
           {
             direction = 'D';
           }
           break;
          
       } 
    }
 }
  
}
