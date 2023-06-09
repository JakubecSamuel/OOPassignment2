package sk.stuba.fei.uim.oop;

public enum Direction {
    HORIZONTAL(-1, 1),
    VERTICAL(0, 0),
    UPLEFT(-1, 1),
    DOWNLEFT(-1, -1),
    UPRIGHT(1, 1),
    DOWNRIGHT(1, -1),
    UP(0, 1),
    RIGHT(1, 0),
    DOWN(0, -1),
    LEFT(-1, 0);

    private int x;
    private int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getXvalue() {
        return x;
    }

    public int getYvalue() {
        return y;
    }
}