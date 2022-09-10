import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Eyes {
    Robot robot;
    Screen screen;
    int x = 0, y = 0;
    public Pair X_BOUNDS, Y_BOUNDS;
    Pair resolution;
    int DEFAULT_WAIT = 10;
    ArrayList<Image> record = new ArrayList<Image>();
    Eyes(){}
    Eyes(Robot _robot, Screen _screen) throws AWTException {
        robot = _robot;
        screen = _screen;
        init();
    }
    void init() throws AWTException {
        resolution = screen.resolution();
        X_BOUNDS = new Pair(0, resolution.x);
        Y_BOUNDS = new Pair(0, resolution.y);
        x = resolution.x/2;
        y = resolution.y/2;
        moveToCurrentPosition();
    }
    public void centerBounding() {
        X_BOUNDS =  new Pair(X_BOUNDS.y/3, X_BOUNDS.y - X_BOUNDS.y/3);
        Y_BOUNDS =  new Pair(Y_BOUNDS.y/4, Y_BOUNDS.y - Y_BOUNDS.y/4);
    }
    public Image Snap(int xmin, int xmax, int ymin, int ymax) {
        Image snap = new Image(robot, new Pair(xmax, ymax), new Pair(xmin, ymin));
        record.add(snap);
        return snap;
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
}
