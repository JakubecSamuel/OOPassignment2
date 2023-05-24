package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CurvedPipe extends Pipe {
    private static final long serialVersionUID = 1L;
    private Direction direction;
    private final int coordX;
    private final int coordY;
    private boolean visited;

    public CurvedPipe(int i, int j) {
        super();
        this.coordX = i;
        this.coordY = j;
        this.visited = false;
        Random rnd = new Random();
        int rotateType = rnd.nextInt(4);
        if(rotateType == 0){
            direction = Direction.UPLEFT;
        }
        else if(rotateType == 1){
            direction = Direction.UPRIGHT;
        }else if(rotateType == 2){
            direction = Direction.DOWNLEFT;
        }else{
            direction = Direction.DOWNRIGHT;
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
        if (this.direction == Direction.UPLEFT) {
            this.direction = Direction.UPRIGHT;
        }
        else if(this.direction == Direction.UPRIGHT){
            this.direction = Direction.DOWNRIGHT;
        }
        else if(this.direction == Direction.DOWNRIGHT){
            this.direction = Direction.DOWNLEFT;
        }
        else if(this.direction == Direction.DOWNLEFT){
            this.direction = Direction.UPLEFT;
        }
        this.repaint();
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
        if (this.direction == Direction.UPLEFT) {
            g2d.rotate(Math.toRadians(0), getWidth() / 2, getHeight() / 2);
        }
        else if(this.direction == Direction.UPRIGHT){
            g2d.rotate(Math.toRadians(90), getWidth() / 2, getHeight() / 2);
        }
        else if(this.direction == Direction.DOWNRIGHT){
            g2d.rotate(Math.toRadians(180), getWidth() / 2, getHeight() / 2);
        }
        else if(this.direction == Direction.DOWNLEFT){
            g2d.rotate(Math.toRadians(270), getWidth() / 2, getHeight() / 2);
        }
        g2d.drawLine(0, getHeight() / 2, getWidth() / 2, getHeight() / 2);
        g2d.drawLine(getHeight() / 2, getHeight() / 2, getHeight() / 2, 0);
    }
    @Override
    public Direction getDirection() {
        return this.direction;
    }
}