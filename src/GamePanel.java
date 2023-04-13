import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**klasa obsługująca wszystkie elementy i akcje w panelu gry */
public class GamePanel extends JPanel implements ActionListener {
    //screen width
    static final int GP_WIDTH = 600;
    //screen height
    static final int GP_HEIGHT = 500;
    //size of single cell of snake and food
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
    int score;
    //x position of where food is located, generated  randomly
    int foodX;
    //y position of where food is located, generated randomly
    int foodY;
    //snake direction at the moment of starting the game
    char direction = 'R';
    //is snake running at the beginning of the game
    boolean running = false;
    boolean pause = false;
    boolean gameOver = false;
    //level
    int level = 1;
    Timer timer;
    Random random;
    SendResultsCallback sendResultsCallback;
    GamePanel(SendResultsCallback sendResultsCallback){
        this.sendResultsCallback = sendResultsCallback;
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
        this.addMouseListener(new myMouseAdapter());
        //starting game
        startGame();
    }
    /**metoda uruchamia grę*/
    public void startGame(){
        newFood();
        timer = new Timer(delay, this);
        timer.start();
    }

    /**metoda umożliwia rysowanie na ekranie*/
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    /**metoda rysuje wszystkie informacje na panelu*/
    //draw elements on screen
    public void draw(Graphics g){
        if(running){
            if (!pause){
                //drawing color of food
                drawFood(g);
                //drawing snake
                drawSnake(g);
            } else {
                running = false;
                //drawing color of food
                drawFood(g);
                //drawing snake
                drawSnake(g);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Ink Free", Font.BOLD, 60));
                FontMetrics metrics2 = getFontMetrics(g.getFont());
                g.drawString("PAUSE", (GP_WIDTH - metrics2.stringWidth("PAUSE"))/2, GP_HEIGHT/2);
            }
            running = true;
        }
        if (!running && !gameOver){
            startingText(g);
        }
        if (!running && gameOver) {
            gameOver(g);
            gameOver = false;
        }
    }
    /**metoda rysuje snake'a na ekranie*/
    public void drawSnake(Graphics g){
        for (int i = 0; i < snakeSize; i++) {
            g.setColor(Color.blue);
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
    }

    /**metoda rysuje pokarm na ekranie*/
    public void drawFood(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
    }

    /**metoda wyświetla napis początkowy*/
    public void startingText(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Click to start", (GP_WIDTH - metrics3.stringWidth("Click to start"))/2, (GP_HEIGHT/4)+50);
    }
    /**metoda wyświetla ekran z informacjami o wyniku i poziomie po zakończeniu gry*/
    public void gameOver(Graphics g){
        //displays score
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Your score: " + score, (GP_WIDTH - metrics1.stringWidth("Your score: " + score))/2,
                GP_HEIGHT/2);
        //displays level
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Your level: " + level, (GP_WIDTH - metrics2.stringWidth("Your level: " + level))/2,
                (GP_HEIGHT/2)+50);
        //displays game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 60));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (GP_WIDTH - metrics3.stringWidth("GAME OVER"))/2, (GP_HEIGHT/4)+50);
    }
    /**metoda służy do zmieniania kierunku i przemieszczania się snake'a*/
    public void moveSnake(){
        //shifting body parts of snake around
        for (int i = snakeSize; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        //changing direction of where snake is heading
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
    }

    /**metoda generuje "pokarm"*/
    public void newFood(){
        foodX = random.nextInt((GP_WIDTH-(2*BlankPanel.MIN_DIMENSION))/UNIT_SIZE)*UNIT_SIZE;
        //System.out.println("Współrzędna x: " + foodX);
        foodY = random.nextInt((GP_HEIGHT-(2*BlankPanel.MIN_DIMENSION))/UNIT_SIZE)*UNIT_SIZE;
        //System.out.println("Współrzędna y: " + foodY);
    }
    /**metoda sprawdza czy snake dotknął "pokarmu" */
    public void checkFood(){
        //checks if snake touches food
        if ((x[0] == foodX) && (y[0] == foodY)){
            //increments snake size
            snakeSize++;
            //increments game score
            score++;
            //increments level and decrements timer
            if ((score %10 == 0) && (running)){
                level++;
                MakeSound.playNewLevel();
                timer.stop();
                delay -= 5;
                timer = new Timer(delay, this);
                timer.start();
            }
            sendResultsCallback.sendResults(score, level);
            //makes sound
            MakeSound.playScore();
            //generates new food
            newFood();
        }
    }
    /**metoda sprawdza czy snake nie dotyka siebie lub którejś z krawędzi panelu*/
    public void checkCollisions(){
        //checks if snake touches itself
        for (int i = snakeSize; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
                gameOver = true;
            }
        }
        //checks if snake touches left border
        if (x[0] < 0){
            running = false;
            gameOver = true;
        }
        //checks if snake touches right border
        if (x[0] >= GP_WIDTH){
            running = false;
            gameOver = true;
        }
        //checks if snake touches top border
        if(y[0] < 0){
            running = false;
            gameOver = true;
        }
        //checks if snake touches bottom border
        if (y[0] >= GP_HEIGHT){
            running = false;
            gameOver = true;
        }
        if (!running){
            timer.stop();
            MakeSound.playGameOver();
        }
    }

    /**metoda odpowiada za obsługę zdarzeń*/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !pause){
            moveSnake();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    /**klasa obsługująca zdarzenia z klawiatury*/
    public class MyKeyAdapter extends KeyAdapter{
        /**metoda obsługuje zdarzenia naciśnięcia przycisku na klawiaturze*/
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

    /**klasa obsługująca kliknięcie myszką*/
    public class myMouseAdapter extends MouseAdapter{
        /**metoda obsługuje zdarzenia kliknięcia myszką*/
        @Override
        public void mousePressed(MouseEvent e) {
            if (!running) {
                running = true;
            } else {
                pause = !pause;
            }
        }
    }
}
