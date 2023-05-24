package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class StraightPipe extends Pipe {
    private static final long serialVersionUID = 1L;
    private Direction direction;
    private final int coordX;
    private final int coordY;
    private boolean visited;

    public StraightPipe(int i, int j) {
        super();
        this.coordX = i;
        this.coordY = j;
        this.visited = false;
        Random rnd = new Random();
        int rotateType = rnd.nextInt(2);
        if(rotateType == 0){
            direction = Direction.HORIZONTAL;
        }
        else{
            direction = Direction.VERTICAL;
        }
        setPreferredSize(new Dimension(50, 50));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        setBackground(Color.gray);
        setOpaque(true);
    }

    @Override
    public void setVisited(boolean visited){
        this.visited = visited;
    }

    @Override
    public boolean isVisited() {
        return visited;
    }

    @Override
    public int getCoordX(){
        return this.coordX;
    }

    @Override
    public int getCoordY(){
        return this.coordY;
    }

    public void rotate(){
        if (this.direction == Direction.VERTICAL) {
            this.direction = Direction.HORIZONTAL;
        }
        else if (this.direction == Direction.HORIZONTAL) {
            this.direction = Direction.VERTICAL;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(this.visited){
            g2d.setColor(Color.green);
        }
        else{
            g2d.setColor(Color.black);
        }
        g2d.setStroke(new BasicStroke(10));
        if (this.direction == Direction.VERTICAL) {
            g2d.rotate(Math.toRadians(0), getWidth() / 2, getHeight() / 2);
        }
        else if (this.direction == Direction.HORIZONTAL) {
            g2d.rotate(Math.toRadians(90), getWidth() / 2, getHeight() / 2);
        }
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
    }
    @Override
    public Direction getDirection() {
        return this.direction;
    }
}