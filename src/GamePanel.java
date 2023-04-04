import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, MouseListener {
    //screen width
    static final int GP_WIDTH = 600;
    //screen height
    static final int GP_HEIGHT = 500;
    //size of single cell of the snake and food
    static final int UNIT_SIZE = 20;
    //the object (snake) amount which can be shown on screen
    static final int GAME_UNITS = (GP_WIDTH * GP_HEIGHT)/UNIT_SIZE;
    //x coordinate of snake
    final int x[] = new int[GAME_UNITS];
    //y coordinate of snake
    final int y[] = new int[GAME_UNITS];
    //how fast snake is moving
    int delay = 125;
    //how big is snake
    int snakeSize = 6;
    //how much food snake has eaten
    int foodEaten;
    //x position of where food is located, generated  randomly
    int foodX;
    //y position of where food is located, generated randomly
    int foodY;
    //snake direction at the moment of starting the game
    char direction = 'R';
    //is snake running at the beginning of the game
    boolean running = false;
    boolean pause = false;
    //level
    int level = 1;
    Timer timer;
    Random random;
    SendResultsCallback sendResultsCallback;
    GamePanel(SendResultsCallback sendResultsCallback){
        this.sendResultsCallback = sendResultsCallback;
        addMouseListener(this);
        //instance of Random class
        random = new Random();
        //preferred size of Panel
        this.setSize(new Dimension(GP_WIDTH, GP_HEIGHT));
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
    //draw elements on screen
    public void draw(Graphics g){
        if(running){
            if (!pause){
                //drawing color of food
                g.setColor(Color.BLUE);
                g.fillRect(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
                //drawing snake
                for (int i = 0; i < snakeSize; i++) {
                    g.setColor(Color.PINK);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            } else {
                running = false;
                g.setColor(Color.WHITE);
                g.setFont(new Font("Ink Free", Font.BOLD, 60));
                FontMetrics metrics2 = getFontMetrics(g.getFont());
                g.drawString("PAUSE", (GP_WIDTH - metrics2.stringWidth("PAUSE"))/2, GP_HEIGHT/2);
            }
            running = true;
        } else {
            gameOver(g);
        }
    }

    public void newFood(){
        foodX = random.nextInt((int)((GP_WIDTH-(2*BlankPanel.MIN_DIMENSION))/UNIT_SIZE))*UNIT_SIZE;
        System.out.println("Współrzędna x: " + foodX);
        foodY = random.nextInt((int)(GP_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        System.out.println("Współrzędna y: " + foodY);
    }

    public void checkFood(){
        //checks if snake touches food
        if ((x[0] == foodX) && (y[0] == foodY)){
            //increments snake size
            snakeSize++;
            //increments game score
            foodEaten++;
            //increments level and decrements timer
            if ((foodEaten %10 == 0) && (running)){
                level++;
                MakeSound.playNewLevel();
                timer.stop();
                delay -= 5;
                timer = new Timer(delay, this);
                timer.start();
            }
            sendResultsCallback.sendResults(foodEaten, level);
            //makes sound
            MakeSound.playScore();
            //generates new food
            newFood();
        }
    }

    public void checkCollisions(){
        //checks if snake touches itself
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
        if (x[0] >= GP_WIDTH){
            running = false;
        }
        //checks if snake touches top border
        if(y[0] < 0){
            running = false;
        }
        //checks if snake touches bottom border
        if (y[0] >= GP_HEIGHT){
            running = false;
        }
        if (!running){
            timer.stop();
            MakeSound.playGameOver();
        }
    }

    public void gameOver(Graphics g){
        //display score
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Your score: " + foodEaten, (GP_WIDTH - metrics1.stringWidth("Your score: " + foodEaten))/2,
                GP_HEIGHT/2);
        //display game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 60));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (GP_WIDTH - metrics2.stringWidth("GAME OVER"))/2, GP_HEIGHT/3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !pause){
            new SnakeController(snakeSize, UNIT_SIZE, x, y, direction).moveSnake();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!running) {
            running = true;
        } else
            pause =! pause;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public class MyKeyAdapter extends KeyAdapter{
        //coding keys functionality
        @Override
        public void keyPressed(KeyEvent e) {
            direction = PanelController.controlPanel(e, direction);
            System.out.println("direction: " + direction);
        }
    }
}
