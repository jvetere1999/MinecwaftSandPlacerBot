public class Pair {
    public int x;
    public int y;
    public Pair(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public boolean between(int i) {
        return (x < i && i < y);
    }
}
