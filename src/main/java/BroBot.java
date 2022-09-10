import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import static java.lang.Thread.sleep;
import static jdk.internal.org.objectweb.asm.Edge.JUMP;

public class BroBot {

    Robot robot;
    Screen screen;
    int x = 0, y = 0;
    Pair X_BOUNDS, Y_BOUNDS;
    Pair resolution;
    int DEFAULT_WAIT = 10;
    boolean space = false;
    HashMap<Integer, Boolean> isPressed = new HashMap<Integer, Boolean>();
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

    public void space() {
        if (!isPressed.containsKey(KeyEvent.VK_SPACE)) {
            isPressed.put(KeyEvent.VK_SPACE, false);
        }
        if (space)
            robot.keyRelease(KeyEvent.VK_SPACE);
        else
            robot.keyPress(KeyEvent.VK_SPACE);
        isPressed.put(KeyEvent.VK_SPACE, !isPressed.get(KeyEvent.VK_SPACE));
    }
    public void JUMP() {
        space();
        space();
    }
    public void FORWARD() {
        if (!isPressed.containsKey(KeyEvent.VK_W)) {
            isPressed.put(KeyEvent.VK_W, false);
        }
        if (space)
            robot.keyRelease(KeyEvent.VK_W);
        else
            robot.keyPress(KeyEvent.VK_W);
        isPressed.put(KeyEvent.VK_W, !isPressed.get(KeyEvent.VK_W));
    }
    public void BACK() {
        if (!isPressed.containsKey(KeyEvent.VK_S)) {
            isPressed.put(KeyEvent.VK_S, false);
        }
        if (space)
            robot.keyRelease(KeyEvent.VK_S);
        else
            robot.keyPress(KeyEvent.VK_S);
        isPressed.put(KeyEvent.VK_S, !isPressed.get(KeyEvent.VK_S));
    }
    public void LEFT() {
        if (!isPressed.containsKey(KeyEvent.VK_A)) {
            isPressed.put(KeyEvent.VK_A, false);
        }
        if (space)
            robot.keyRelease(KeyEvent.VK_A);
        else
            robot.keyPress(KeyEvent.VK_A);
        isPressed.put(KeyEvent.VK_A, !isPressed.get(KeyEvent.VK_A));
    }
    public void RIGHT() {
        if (!isPressed.containsKey(KeyEvent.VK_D)) {
            isPressed.put(KeyEvent.VK_D, false);
        }
        if (space)
            robot.keyRelease(KeyEvent.VK_D);
        else
            robot.keyPress(KeyEvent.VK_D);
        isPressed.put(KeyEvent.VK_D, !isPressed.get(KeyEvent.VK_D));
    }
    public void TIMED_FORWARD(int time) throws InterruptedException {
        FORWARD();
        int count = 0;
        int previousSum = 0;
        for (int i = 0; i < time; i++) {
            Color current = robot.getPixelColor(x, y);
            int sum = current.getAlpha() + current.getRed() + current.getGreen() + current.getBlue();
            if (previousSum > sum)
                count++;
            previousSum = sum;
            if (count == 5) {
                JUMP();
                count = 0;
            }
        }
        FORWARD();
    }
    public void TIMED_BACKWARD(int time) throws InterruptedException {
        BACK();
        sleep(time);
        BACK();
    }
    public void TIMED_LEFT(int time) throws InterruptedException {
        LEFT();
        sleep(time);
        LEFT();
    }
    public void TIMED_RIGHT(int time) throws InterruptedException {
        RIGHT();
        sleep(time);
        RIGHT();
    }

    public void TURN_90() {
        increment(5, x+90, y);
    }

}
