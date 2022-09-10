import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class BroBot {

    Robot robot;
    Screen screen;
    Eyes eyes;
    int x = 0, y = 0;
    int DEFAULT_WAIT = 10;
    HashMap<Integer, Boolean> isPressed = new HashMap<Integer, Boolean>();
    void init() throws AWTException {
        screen = new Screen();
        robot = new Robot(screen.GetDevice());
        eyes = new Eyes(robot, screen);
    }
    public BroBot() throws AWTException {
        init();
    }
    public BroBot(int _defaultWait) throws AWTException {
        init();
        DEFAULT_WAIT = _defaultWait;
    }
    void center() throws AWTException {
        eyes.center();
    }
    public Image takeImage() {
        int xmin = eyes.X_BOUNDS.x;
        xmin = xmin + xmin/2;
        int ymin = eyes.Y_BOUNDS.x;
        ymin = ymin + ymin/2;
        int xmax = eyes.X_BOUNDS.y;
        xmax = xmax - xmax/2;
        int ymax =eyes. Y_BOUNDS.y;
        ymax = ymax - ymax/2;
        return eyes.Snap(xmin, xmax, ymin, ymax);
    }

    public void printCurrentPosition() {
        System.out.println(x + " " + y);
    }

    public void space() {
        if (!isPressed.containsKey(KeyEvent.VK_SPACE)) {
            isPressed.put(KeyEvent.VK_SPACE, false);
        }
        if (isPressed.get(KeyEvent.VK_SPACE))
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
        if (isPressed.get(KeyEvent.VK_W))
            robot.keyRelease(KeyEvent.VK_W);
        else
            robot.keyPress(KeyEvent.VK_W);
        isPressed.put(KeyEvent.VK_W, !isPressed.get(KeyEvent.VK_W));
    }
    public void BACK() {
        if (!isPressed.containsKey(KeyEvent.VK_S)) {
            isPressed.put(KeyEvent.VK_S, false);
        }
        if (isPressed.get(KeyEvent.VK_S))
            robot.keyRelease(KeyEvent.VK_S);
        else
            robot.keyPress(KeyEvent.VK_S);
        isPressed.put(KeyEvent.VK_S, !isPressed.get(KeyEvent.VK_S));
    }
    public void LEFT() {
        if (!isPressed.containsKey(KeyEvent.VK_A)) {
            isPressed.put(KeyEvent.VK_A, false);
        }
        if (isPressed.get(KeyEvent.VK_A))
            robot.keyRelease(KeyEvent.VK_A);
        else
            robot.keyPress(KeyEvent.VK_A);
        isPressed.put(KeyEvent.VK_A, !isPressed.get(KeyEvent.VK_A));
    }
    public void RIGHT() {
        if (!isPressed.containsKey(KeyEvent.VK_D)) {
            isPressed.put(KeyEvent.VK_D, false);
        }
        if (isPressed.get(KeyEvent.VK_D))
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
        eyes.increment(5, x+90, y);
    }

    void moveAroundEdge() throws AWTException {
        eyes.moveAroundEdge();
    }

}
