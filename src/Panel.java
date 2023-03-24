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
    int delay = 125;
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
    //level
    int level = 1;
    Timer timer;
    Random random;
    Panel(){
        //instance of the Random class
        random = new Random();
        //preferred size of Panel
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //background color
        this.setBackground(Color.gray);
        //set focus
        this.setFocusable(true);
        //add event handling
        this.addKeyListener(new MyKeyAdapter());
        //starting game
        startGame();
    }

    public void startGame(){
        newFood();
        running = true;
        timer = new Timer(delay, this);
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
            g.drawString("Score: " + foodEaten + "Poziom: " + level, (WIDTH - metrics.stringWidth("Score: " +
                            foodEaten + "Poziom: " + level))/2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

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
            //increments level and decrements timer
            if ((foodEaten %10 == 0) && (running)){
                level++;
                MakeSound.makeSound("/Users/mac/Desktop/java/mixkit-arcade-bonus-alert-767.wav");
                timer.stop();
                delay-=5;
                timer = new Timer(delay, this);
                timer.start();
            }
            //makes sound
            MakeSound.makeSound("/Users/mac/Desktop/java/mixkit-retro-game-notification-212.wav");
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
        //checks if snake touches left border
        if (x[0] < 0){
            running = false;
        }
        //checks if snake touches right border
        if (x[0] > WIDTH){
            running = false;
        }
        //checks if snake touches top border
        if(y[0] < 0){
            running = false;
        }
        //checks if the snake touches bottom border
        if (y[0] > HEIGHT){
            running = false;
        }
        if (!running){
            timer.stop();
            MakeSound.makeSound("/Users/mac/Desktop/java/mixkit-funny-game-over-2878.wav");
        }
    }

    public void gameOver(Graphics g){
        //display score
        g.setColor(Color.yellow);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + foodEaten, (WIDTH - metrics1.stringWidth("Score: " + foodEaten))/2,
                g.getFont().getSize());
        //display game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 60));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (WIDTH - metrics2.stringWidth("GAME OVER"))/2, HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            new SnakeController(snakeSize, UNIT_SIZE, x, y, direction).moveSnake();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        //coding keys functionality
        @Override
        public void keyPressed(KeyEvent e) {
            direction = PanelController.controlPanel(e, direction);
        }
    }
}
