package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class StartFinishPipe extends Pipe {
    private static final long serialVersionUID = 1L;
    private Direction direction;
    private final int coordX;
    private final int coordY;
    private boolean visited;

    public StartFinishPipe(int i, int j) {
        super();
        this.coordX = i;
        this.coordY = j;
        this.visited = false;
        Random rnd = new Random();
        int rotateType = rnd.nextInt(4);
        if(rotateType == 0){
            direction = Direction.UP;
        }
        else if(rotateType == 1){
            direction = Direction.RIGHT;
        }else if(rotateType == 2){
            direction = Direction.DOWN;
        }else{
            direction = Direction.LEFT;
        }
        setPreferredSize(new Dimension(50, 50));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        setOpaque(true);
    }

    @Override
    public void setVisited(boolean visited){
        this.visited = visited;
    }

    @Override
    public boolean isVisited() {
        return this.visited;
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
        if (this.direction == Direction.UP) {
            this.direction = Direction.RIGHT;
        }
        else if(this.direction == Direction.RIGHT){
            this.direction = Direction.DOWN;
        }
        else if(this.direction == Direction.DOWN){
            this.direction = Direction.LEFT;
        }
        else if(this.direction == Direction.LEFT){
            this.direction = Direction.UP;
        }
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int circleSize = Math.min(getWidth(), getHeight()) / 2;
        if (this.direction == Direction.UP) {
            g2d.rotate(Math.toRadians(-90), getWidth() / 2, getHeight() / 2);
        }
        else if(this.direction == Direction.RIGHT){
            g2d.rotate(Math.toRadians(0), getWidth() / 2, getHeight() / 2);
        }
        else if(this.direction == Direction.DOWN){
            g2d.rotate(Math.toRadians(90), getWidth() / 2, getHeight() / 2);
        }
        else if(this.direction == Direction.LEFT){
            g2d.rotate(Math.toRadians(180), getWidth() / 2, getHeight() / 2);
        }
        g2d.fillOval((getWidth() - circleSize) / 2, (getHeight() - circleSize) / 2, circleSize, circleSize);
        g2d.setStroke(new BasicStroke(10));
        g2d.drawLine(getWidth() / 2, getHeight() / 2, getWidth(), getHeight() / 2);
    }
    @Override
    public Direction getDirection() {
        return this.direction;
    }
}