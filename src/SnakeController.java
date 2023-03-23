public class SnakeController {
    int UNIT_SIZE;
    int snakeSize;
    int x[];
    int y[];
    char direction;
    public SnakeController(int snakeSize, int UNIT_SIZE, int x[], int y[], char direction){
        this.snakeSize = snakeSize;
        this.UNIT_SIZE = UNIT_SIZE;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    public void moveSnake(){
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
    }
}
