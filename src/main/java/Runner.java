import java.awt.*;

public class Runner {


    public static void main(String[] args) {
        BroBot sandBoy = null;
        try {
            sandBoy = new BroBot(100);

        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
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
    }
}
