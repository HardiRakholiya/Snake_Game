import javax.swing.JFrame;


public class gameframe extends JFrame {
 
    gameframe()
    {
       this.add(new gamepanel());
       this.setTitle("snake");
       this.setResizable(false);
       this.pack();
       this.setVisible(true);
       this.setLocationRelativeTo(null);
       


    }
}
