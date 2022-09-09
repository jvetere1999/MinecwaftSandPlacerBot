import java.awt.*;

import static java.lang.Thread.sleep;

public class Runner {


    public static void main(String[] args) throws InterruptedException {
        BroBot sandBoy = null;

        sleep(1999);
        brobot2();
    }
    public static void brobot1() {
        BroBot sandBoy = null;
        try {
            sandBoy = new BroBot(100);
            sandBoy.center();
            sandBoy.moveAroundEdge();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }  public static void brobot2() {
        BroBot sandBoy = null;
        try {
            sandBoy = new BroBot(100);
            sandBoy.TIMED_FORWARD(1000);
            sandBoy.TURN_90();
            sandBoy.TIMED_FORWARD(1000);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
