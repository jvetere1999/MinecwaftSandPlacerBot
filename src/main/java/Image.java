import java.awt.*;

public class Image {
    Robot robot;
    int[][] image;
    public Image(Robot robot, Pair MAX, Pair MIN) {
        this.robot = robot;
        image = new int[MAX.x - MIN.x][MAX.y - MIN.y];

        for (int x = MIN.x; x <= MAX.x; x++) {
            for (int y = MIN.y; y <= MAX.y; y++) {
                robot.mouseMove(x, y);
                image[x - MIN.x][y - MIN.y] = robot.getPixelColor(x, y).getRGB();
            }
        }

    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] ints : image) {
            for (int y = 0; y < image[0].length; y++) {
                sb.append(ints[y]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
