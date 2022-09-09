import java.awt.*;
import java.awt.event.KeyEvent;

import static java.lang.Thread.sleep;

public class BroBot {

    Robot robot;
    Screen screen;
    int x = 0, y = 0;
    Pair X_BOUNDS, Y_BOUNDS;
    Pair resolution;
    int DEFAULT_WAIT = 10;
    boolean space = false;
    void init() throws AWTException {
        screen = new Screen();
        robot = new Robot(screen.GetDevice());
        resolution = screen.resolution();
        X_BOUNDS = new Pair(0, resolution.x);
        Y_BOUNDS = new Pair(0, resolution.y);
        x = resolution.x/2;
        y = resolution.y/2;
        moveToCurrentPosition();
    }
    public BroBot() throws AWTException {
        init();
    }
    public BroBot(int defaultWait) throws AWTException {
        init();
        centerBounding();
        DEFAULT_WAIT = defaultWait;
    }
    public void centerBounding() {
        X_BOUNDS =  new Pair(X_BOUNDS.y/3, X_BOUNDS.y - X_BOUNDS.y/3);
        Y_BOUNDS =  new Pair(Y_BOUNDS.y/4, Y_BOUNDS.y - Y_BOUNDS.y/4);
    }


    /**
     * Moves the robot
     * @param _x
     * @param _y
     */
    public void move(int _x, int _y) {
        moveToWait(_x, _y, DEFAULT_WAIT);
    }

    public void moveTo(int _x, int _y) {
        this.x = _x;
        this.y = _y;
        moveToCurrentPosition();
    }
    public void moveToWait(int _x, int _y, int waitLength) {
        moveTo(_x, _y);
        try {
            sleep(waitLength);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void center() {
        move( resolution.x/2, resolution.y/2);
    }
    public void increment(int increment, int newX, int newY)  {
        if (newX > x || newY > y) {
            incrementUp(increment, newX, newY);
        }
        if (newX < x || newY < y) {
            incrementDown(increment, newX, newY);
        }
    }
    public void incrementUp(int increment, int newX, int newY)  {
        while (x < newX || y < newY) {
            int currX = (x >= newX) ? newX : x + increment;
            int currY = (y >= newY) ? newY : y + increment;
            move(currX, currY);
        }

        moveToCurrentPosition();
    }
    public void incrementDown(int increment, int newX, int newY)  {
        while (x > newX || y > newY) {
            int currX = (x <= newX) ? newX : x - increment;
            int currY = (y <= newY) ? newY : y - increment;
            move(currX, currY);
        }

        moveToCurrentPosition();
    }
    private boolean inBounds() {
        return X_BOUNDS.between(x) && Y_BOUNDS.between(y);
    }
    public void moveToCurrentPosition() {
        robot.mouseMove(x, y);
    }
    public void moveAroundEdge() {
        moveTo(X_BOUNDS.x, Y_BOUNDS.x);
        printCurrentPosition();
        increment(10, X_BOUNDS.y, y);
        printCurrentPosition();
        increment(10, x, Y_BOUNDS.y);
        printCurrentPosition();
        increment(10, X_BOUNDS.x, y);
        printCurrentPosition();
        increment(10, x, Y_BOUNDS.x);
        printCurrentPosition();
    }
    public void printCurrentPosition() {
        System.out.println(x + " " + y);
    }

    public void space(int lengthOfPress) {
        if (space)
            robot.keyRelease(KeyEvent.VK_SPACE);
        else
            robot.keyPress(KeyEvent.VK_SPACE);
    }
}
