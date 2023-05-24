package sk.stuba.fei.uim.oop;

import java.awt.*;
import javax.swing.*;

public abstract class Pipe extends JLabel{
    private boolean start;
    private boolean end;
    private Direction direction;

    public Pipe() {
        super();
        start = false;
        end = false;
    }


    public void setStart(boolean start) {
        this.start = start;
        if (start) {
            setBackground(Color.GREEN);
        }
    }

    public void setEnd(boolean end) {
        this.end = end;
        if (end) {
            setBackground(Color.RED);
        }
    }

    public boolean isStart() {
        return start;
    }

    public boolean isEnd() {
        return end;
    }
    public Direction getDirection() {
        return this.direction;
    }

    public abstract void setVisited(boolean visited);

    public abstract boolean isVisited();

    public int getCoordX() {
        return 0;
    }

    public int getCoordY() {
        return 0;
    }
}