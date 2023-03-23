import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    //screen width
    static final int WIDTH = 500;
    //screen height
    static final int HEIGHT = 500;
    //size of the single cell of the snake and food
    static final int UNIT_SIZE = 20;
    //the object (snake) amount which can be shown on the screen
    static final int GAME_UNITS = (WIDTH*HEIGHT)/UNIT_SIZE;
    //x coordinate of the snake
    final int x[] = new int[GAME_UNITS];
    //y coordinate of the snake
    final int y[] = new int[GAME_UNITS];
    //how fast the snake is moving
    final int DELAY= 125;
    //how big is the snake
    int snakeSize = 6;
    //how much food the snake has eaten
    int foodEaten;
    //x position of where the food is located, generated  randomly
    int foodX;
    //y position of where the food is located, generated randomly
    int foodY;
    //snake direction at the moment of starting the game
    char direction = 'R';
    //is the snake running at the beginning of the game
    boolean running = false;
    Timer timer;
    Random random;
    Panel(){
        //instance of the Random class
        random = new Random();
        //preferred size of Panel
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //background color
        this.setBackground(Color.black);
        //set the focus
        this.setFocusable(true);
        //add event handling
        this.addKeyListener(new MyKeyAdapter());
        //starting game
        startGame();
    }

    public void startGame(){
        newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    //draw elements on the screen
    public void draw(Graphics g){
        if(running){
            //drawing the color of food
            g.setColor(Color.GREEN);
            g.fillRect(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
            //drawing the snake
            for (int i = 0; i < snakeSize; i++) {
                g.setColor(Color.PINK);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            //showing score on the screen
            g.setColor(Color.BLUE);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten))/2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }
    //moveSnake the snake
   /* public void moveSnake(){
        //shifting the body parts of the snake around
        for (int i = snakeSize; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        //changing the direction of where the snake is heading
        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }
    }*/
    //generate food
    public void newFood(){
        foodX = random.nextInt((int)(WIDTH/UNIT_SIZE))*UNIT_SIZE;
        foodY = random.nextInt((int)(HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void checkFood(){
        //checks if snake touches the food
        if ((x[0] == foodX) && (y[0] == foodY)){
            //increments snake size
            snakeSize++;
            //increments game score
            foodEaten++;
            //generates new food
            newFood();
        }
    }

    public void checkCollisions(){
        //checks if the snake touches itself
        for (int i = snakeSize; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //checks if the snake touches left border
        if (x[0] < 0){
            running = false;
        }
        //checks if the snake touches right border
        if (x[0] > WIDTH){
            running = false;
        }
        //checks if the snake touches top border
        if(y[0] < 0){
            running = false;
        }
        //checks if the snake touches bottom border
        if (y[0] > HEIGHT){
            running = false;
        }
        if (!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        //display score
        g.setColor(Color.yellow);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + foodEaten, (WIDTH - metrics1.stringWidth("Score: " + foodEaten))/2,
                /*g.getFont().getSize()*/ HEIGHT/3);
        //display game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 60));
        //FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", WIDTH/8 /*- metrics2.stringWidth("GAME OVER"))/2*/, HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            new MoveSnake(snakeSize, UNIT_SIZE, x, y, direction).moveSnake();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        //coding keys functionality
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }

        }
    }
}