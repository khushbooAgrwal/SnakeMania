import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int Screen_wid = 600;
    static final int Screen_height = 600;
    static final int Unit_size = 25;
    static final int Game_units  = (Screen_height*Screen_wid)/Unit_size;
    static final int delay = 100;
    final int x[] = new int[Game_units];
    final int y[] = new int[Game_units];
    int bodyParts = 6;
    int appleEaten ;
    int appleX;
    int appleY;
    char dir = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(Screen_wid,Screen_height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startgame();
    }
    public void startgame(){
        newApple();
        running = true;
        timer = new Timer(delay,this);
        timer.start();
    }
    public  void  paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    } 
    public void draw(Graphics g){
        if(running) {
            for (int i = 0; i < Screen_height / Unit_size; i++) {
                g.drawLine(i * Unit_size, 0, i * Unit_size, Screen_height);
                g.drawLine(0, i * Unit_size, Screen_wid, i * Unit_size);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, Unit_size, Unit_size);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], Unit_size, Unit_size);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], Unit_size, Unit_size);
                }

            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+appleEaten,(Screen_wid - metrics.stringWidth("Score: "+appleEaten))/2,g.getFont().getSize());
        }

        else{
            gameOver(g);
        }

    }
    public  void newApple(){
         appleX = random.nextInt((int)(Screen_wid/Unit_size))*Unit_size;
        appleY = random.nextInt((int)(Screen_wid/Unit_size))*Unit_size;
    }
    public void move(){
        for (int i = bodyParts; i >0 ; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
            switch (dir){
                case 'U':
                    y[0] =y[0] - Unit_size;
                    break;
                case 'D':
                    y[0] =y[0] + Unit_size;
                    break;
                case 'R':
                    x[0] =x[0] + Unit_size;
                    break;
                case 'L':
                    x[0] =x[0] - Unit_size;
                    break;

            }

    }
    public void checkApples(){
           if((x[0] == appleX) && (y[0] == appleY)){
               bodyParts++;
               appleEaten++;
               newApple();
           }
    }
    public void checkCollisions() {
        for (int i = bodyParts; i >0 ; i--) {
            if((x[0]==x[i])&& (y[0]==y[i])){
                running = false;
            }
        }
        if(x[0]<0){
            running = false;
        }

        if(y[0]>Screen_height){
            running = false;
        }
        if(y[0]<0){
            running = false;
        }
        if(x[0]>Screen_wid){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public  void  gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+appleEaten,(Screen_wid - metrics1.stringWidth("Score: "+appleEaten))/2,g.getFont().getSize());
          g.setColor(Color.red);
          g.setFont(new Font("Ink Free",Font.BOLD,75));
          FontMetrics metrics2 = getFontMetrics(g.getFont());
          g.drawString("Game Over",(Screen_wid - metrics2.stringWidth("Game Over"))/2,Screen_height/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
         if(running){
             move();
             checkApples();
             checkCollisions();
         }
         repaint();
    }
    public  class MyKeyAdapter extends KeyAdapter{
        @Override
        public  void keyPressed(KeyEvent e){
           switch (e.getKeyCode()){
               case KeyEvent.VK_LEFT:
                   if(dir != 'R'){
                       dir = 'L';
                   }
                   break;
               case KeyEvent.VK_RIGHT:
                   if(dir != 'L'){
                       dir = 'R';
                   }
                   break;
                case KeyEvent.VK_UP:
                   if(dir != 'D'){
                       dir = 'U';
                   }
                   break;
               case KeyEvent.VK_DOWN:
                   if(dir != 'U'){
                       dir = 'D';
                   }
                   break;
           }
        }
    }
}
