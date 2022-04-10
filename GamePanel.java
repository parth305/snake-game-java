package SnakeGame2;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    final static int WIDTH=700;
    final static int HEIGHT=700;
    final static int UNITE=25;
    int applex;
    int appley;
    int[] x=new int[(WIDTH*HEIGHT)/(UNITE*UNITE)];
    int[] y=new int[(WIDTH*HEIGHT)/(UNITE*UNITE)];
    int bodypart=4;
    int score=0;
    Timer timer;
    int DELY=200;
    Border border=BorderFactory.createLineBorder(Color.GREEN,1);
    boolean running;
    Random random=new Random();
    char direction='R';
    MyKeyAdapter keys;

    GamePanel(){
    this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
    this.setBackground(Color.BLACK);
    this.setBorder(border);
    this.setFocusable(true);
    keys=new MyKeyAdapter();
    this.addKeyListener(keys);

    startgame();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
          //  System.out.println("Hey");
            move();
            cheackapple();
            collisne();
        }
        repaint();
    }

    public void collisne(){
        //colid with body
         for(int i=bodypart;i>0;i--){
             if((x[0]==x[i]) && (y[0]==y[i]) )
                 running=false;
         }

         //colid width right
        if(x[0] > WIDTH-UNITE) {
           // running = false;
            x[0]=0;
        }
        //colid width down
        else if(y[0] >HEIGHT-UNITE) {
            //running = false;
            y[0]=0;
        }
        //colid with up
        else if(y[0] <0) {
           // running = false;
            y[0]=HEIGHT-UNITE;
        }
        //colid with left
        else if( x[0]<0) {
          //  running = false;
            x[0]=WIDTH-UNITE;
        }

        if(!running)
            timer.stop();
    }

    public void newApple(){
        applex=(random.nextInt(WIDTH/UNITE))*UNITE;
        appley=(random.nextInt(HEIGHT/UNITE))*UNITE;
    }

    public void cheackapple(){
        if(x[0]==applex && y[0]==appley){
            bodypart++;
            score++;
            newApple();
        }
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running) {
            for (int i = 0; i < WIDTH / UNITE; i++) {
                g.drawLine(i * UNITE, 0, i * UNITE, HEIGHT);
                g.drawLine(0, i * UNITE, WIDTH, i * UNITE);
            }


            g.setColor(Color.red);
            g.fillOval(applex, appley, UNITE, UNITE);

            for (int i = 0; i < bodypart; i++) {
                //   System.out.println(x[i]+" value of x"+i+"  "+y[i]+" value of y"+i);
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNITE, UNITE);
                } else {
                    g.setColor(new Color(130, 210, 90));
                    g.fillRect(x[i], y[i], UNITE, UNITE);
                }
            }
        }
        else
            gameover(g);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.ITALIC,30));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score: "+score,(WIDTH-metrics1.stringWidth("Score: "+score))/2,g.getFont().getSize());

    }

    public void startgame(){
        newApple();
        running=true;
        timer=new Timer(DELY,this);
        timer.start();
    }

    public void move() {
        for (int i = bodypart; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'R':
                x[0] = x[0] + UNITE;
                break;
            case 'D':
                y[0] = y[0] + UNITE;
                break;
            case 'U':
                y[0] = y[0] - UNITE;
                break;
            case 'L':
                x[0] = x[0] - UNITE;
                break;
        }
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if(direction!='D') {
                            direction = 'U';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(direction!='U') {
                            direction = 'D';
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if(direction!='R') {
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(direction!='L') {
                            direction = 'R';
                        }
                        break;
            }
        }
    }

    public void gameover(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.ITALIC,30));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score: "+score,(WIDTH-metrics1.stringWidth("Score: "+score))/2,0);

        g.setColor(Color.red);
        g.setFont(new Font("Comics",Font.ITALIC,100));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Game Over",(WIDTH-metrics.stringWidth("Game Over"))/2,HEIGHT/2);

      //  timer.stop();
    }
}
