import java.awt.*;

public class Screen {
    GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public GraphicsDevice GetDevice() {
        return device;
    }
    public Pair resolution() {
        return new Pair(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight());
    }
}
