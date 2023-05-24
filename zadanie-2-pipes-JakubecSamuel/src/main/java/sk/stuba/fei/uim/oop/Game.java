package sk.stuba.fei.uim.oop;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Game implements MouseListener, KeyListener, ChangeListener, MouseMotionListener {
    private Pipe[][] grid;
    private JPanel panel;
    private JFrame frame;
    private JSlider slider;
    private int gridSize;
    private int startRow;
    private int endRow;
    private boolean gameWon = false;
    private int level = 1;
    private JLabel levelText;

    public Game() {
        frame = new JFrame("Pipes Assignment 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(gridSize * 50, gridSize * 50);
        frame.getContentPane().setBackground(Color.ORANGE);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.addKeyListener(this);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        slider = new JSlider(JSlider.HORIZONTAL, 8, 12, 8);
        slider.addChangeListener(e -> restartGame());
        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(0);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        topPanel.add(slider);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        levelText = new JLabel("Level " + level);
        levelText.setFont(new Font("Arial", Font.BOLD, 24));
        levelText.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(levelText);
        JLabel textLabel = new JLabel("<html>ESC - Close App<br>ENTER - Check  Win<br>R - Restart Game" +
                "<br>When you find the right path<br>and click check path<br>you advance to the next level. </html>");
        leftPanel.add(textLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton restartButton = new JButton("Restart Game");
        restartButton.addActionListener(e -> {
            restartGame();
        });
        restartButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(restartButton);

        JButton checkWinButton = new JButton("Check Path");
        checkWinButton.addActionListener(event -> {
            this.checkWin(grid[startRow][0]);
            this.panel.repaint();
            Timer timer = new Timer(2000, e -> {
                this.setAllVisitedFalse();
                this.panel.repaint();
                if (gameWon) {
                    restartGame();
                }
            });
            timer.setRepeats(false);
            timer.start();
        });
        checkWinButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(checkWinButton);

        frame.getContentPane().add(leftPanel, BorderLayout.WEST);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        panel = new JPanel();
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        gridSize = 8;
        generateP();

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void setLevel(boolean reset, JLabel levelText){
        if(reset){
            level = 1;
        }else{
            level++;
        }
        levelText.setText("Level " + level);
    }

    private void generateP(){
        Random random = new Random();
        startRow = random.nextInt(gridSize);
        endRow = random.nextInt(gridSize);
        panel.setLayout(new GridLayout(gridSize, gridSize));
        grid = new Pipe[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if(i == startRow && j == 0){
                    StartFinishPipe start = new StartFinishPipe(startRow, 0);
                    grid[startRow][0] = start;
                    grid[startRow][0].setStart(true);
                    panel.add(start);
                }else if(i == endRow && j == gridSize-1){
                    StartFinishPipe end = new StartFinishPipe(endRow, gridSize-1);
                    grid[endRow][gridSize-1] = end;
                    grid[endRow][gridSize-1].setEnd(true);
                    panel.add(end);
                }else{
                    int pipeType = random.nextInt(2);
                    if (pipeType == 0) {
                        StraightPipe sp = new StraightPipe(i, j);
                        grid[i][j] = sp;
                        panel.add(sp);
                    } else {
                        CurvedPipe cp = new CurvedPipe(i, j);
                        grid[i][j] = cp;
                        panel.add(cp);
                    }
                }
            }
        }
    }

    private void restartGame() {
        if(gameWon){
            setLevel(false, levelText);
        }else{
            setLevel(true, levelText);
        }
        this.frame.getContentPane().remove(panel);
        panel = new JPanel();
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        gridSize = slider.getValue();
        this.gameWon = false;
        frame.setSize(gridSize * 50, gridSize * 50);
        generateP();

        // Add the new panel to the frame and redraw it
        frame.getContentPane().add(panel);
        frame.requestFocusInWindow();
        frame.pack();
        frame.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
        Component current = panel.getComponentAt(e.getX(), e.getY());
        if(current instanceof CurvedPipe){
            ((CurvedPipe) current).rotate();
        }
        else if(current instanceof StraightPipe){
            ((StraightPipe) current).rotate();
        }
        else if(current instanceof StartFinishPipe){
            ((StartFinishPipe) current).rotate();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            restartGame();
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            frame.dispose();
        }else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.checkWin(grid[startRow][0]);
            this.panel.repaint();
            Timer timer = new Timer(2000, event -> {
                this.setAllVisitedFalse();
                this.panel.repaint();
                if (gameWon) {
                    restartGame();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component current = panel.getComponentAt(e.getX(), e.getY());
        if (!(current instanceof Pipe)) {
            return;
        }
        for (Component c : panel.getComponents()) {
            if (c instanceof Pipe) {
                if (c == current) {
                    c.setBackground(Color.cyan);
                } else if(((Pipe) c).isStart()){
                    c.setBackground(Color.green);
                } else if(((Pipe) c).isEnd()){
                    c.setBackground(Color.red);
                } else{
                    c.setBackground(Color.gray);
                }
            }
        }
        panel.repaint();
    }

    private void checkWin(Pipe current) {
        current.setVisited(true);
        if(current instanceof StartFinishPipe){
            if (current.getDirection() == Direction.UP) {
                Pipe nb = getNeighbor(current);
                if (nb != null && !nb.isVisited() && nb.getDirection() != Direction.HORIZONTAL && nb.getDirection() != Direction.UPLEFT && nb.getDirection() != Direction.UPRIGHT) {
                    nb.setVisited(true);
                    if(!nb.isEnd() && !nb.isStart() && nb.getCoordX() >= 0 && nb.getCoordY() <= gridSize-1){
                        checkWin(nb);
                    }
                    else if(nb.isEnd()){
                        this.gameWon = true;
                    }
                }
            } else if (current.getDirection() == Direction.RIGHT) {
                Pipe nb = getNeighbor(current);
                if (nb != null && !nb.isVisited() && nb.getDirection() != Direction.VERTICAL && nb.getDirection() != Direction.UPRIGHT && nb.getDirection() != Direction.DOWNRIGHT) {
                    nb.setVisited(true);
                    if(!nb.isEnd() && !nb.isStart() && nb.getCoordX() >= 0 && nb.getCoordY() <= gridSize-1){
                        checkWin(nb);
                    }
                    else if(nb.isEnd()){
                        this.gameWon = true;
                    }
                }
            } else if (current.getDirection() == Direction.DOWN) {
                Pipe nb = getNeighbor(current);
                if (nb != null && !nb.isVisited() && nb.getDirection() != Direction.HORIZONTAL && nb.getDirection() != Direction.DOWNLEFT && nb.getDirection() != Direction.DOWNRIGHT) {
                    nb.setVisited(true);
                    if(!nb.isEnd() && !nb.isStart() && nb.getCoordX() >= 0 && nb.getCoordY() <= gridSize-1){
                        checkWin(nb);
                    }
                    else if(nb.isEnd()){
                        this.gameWon = true;
                    }
                }
            } else if (current.getDirection() == Direction.LEFT) {
                Pipe nb = getNeighbor(current);
                if (nb != null && !nb.isVisited() && nb.getDirection() != Direction.VERTICAL && nb.getDirection() != Direction.UPLEFT && nb.getDirection() != Direction.DOWNLEFT) {
                    nb.setVisited(true);
                    if(!nb.isEnd() && !nb.isStart() && nb.getCoordX() >= 0 && nb.getCoordY() <= gridSize-1){
                        checkWin(nb);
                    }
                    else if(nb.isEnd()){
                        this.gameWon = true;
                    }
                }
            }
        }
        else if(current instanceof CurvedPipe){
            if(current.getDirection() == Direction.UPRIGHT){
                Pipe nb = getNeighbor(current);
                if(nb != null && !nb.isVisited() && nb.getDirection() != Direction.UPRIGHT){
                    nb.setVisited(true);
                    if(!nb.isEnd() && !nb.isStart() && nb.getCoordX() >= 0 && nb.getCoordY() <= gridSize-1){
                        checkWin(nb);
                    }
                    else if(nb.isEnd()){
                        this.gameWon = true;
                    }
                }
            }
            else if(current.getDirection() == Direction.DOWNRIGHT){
                Pipe nb = getNeighbor(current);
                if(nb != null && !nb.isVisited() && nb.getDirection() != Direction.DOWNRIGHT){
                    nb.setVisited(true);
                    if(!nb.isEnd() && !nb.isStart() && nb.getCoordX() >= 0 && nb.getCoordY() <= gridSize-1){
                        checkWin(nb);
                    }
                    else if(nb.isEnd()){
                        this.gameWon = true;
                    }
                }
            }
            else if(current.getDirection() == Direction.DOWNLEFT){
                Pipe nb = getNeighbor(current);
                if(nb != null && !nb.isVisited() && nb.getDirection() != Direction.DOWNLEFT){
                    nb.setVisited(true);
                    if(!nb.isEnd() && !nb.isStart() && nb.getCoordX() >= 0 && nb.getCoordY() <= gridSize-1){
                        checkWin(nb);
                    }
                    else if(nb.isEnd()){
                        this.gameWon = true;
                    }
                }
            }
            else if(current.getDirection() == Direction.UPLEFT){
                Pipe nb = getNeighbor(current);
                if(nb != null && !nb.isVisited() && nb.getDirection() != Direction.UPLEFT){
                    nb.setVisited(true);
                    if(!nb.isEnd() && !nb.isStart() && nb.getCoordX() >= 0 && nb.getCoordY() <= gridSize-1){
                        checkWin(nb);
                    }
                    else if(nb.isEnd()){
                        this.gameWon = true;
                    }
                }
            }
        }
        else if(current instanceof StraightPipe){
            if(current.getDirection() == Direction.HORIZONTAL){
                Pipe nb = getNeighbor(current);
                if(nb != null && !nb.isVisited() && nb.getDirection() != Direction.VERTICAL){
                    nb.setVisited(true);
                    if(!nb.isEnd() && !nb.isStart() && nb.getCoordX() >= 0 && nb.getCoordY() <= gridSize-1){
                        checkWin(nb);
                    }
                    else if(nb.isEnd()){
                        this.gameWon = true;
                    }
                }
            }
            else if(current.getDirection() == Direction.VERTICAL){
                Pipe nb = getNeighbor(current);
                if(nb != null && !nb.isVisited() && nb.getDirection() != Direction.HORIZONTAL){
                    nb.setVisited(true);
                    if(!nb.isEnd() && !nb.isStart() && nb.getCoordX() >= 0 && nb.getCoordY() <= gridSize-1){
                        checkWin(nb);
                    }
                    else if(nb.isEnd()){
                        this.gameWon = true;
                    }
                }
            }
        }
    }

    private Pipe getNeighbor(Pipe current){
        if(current instanceof StraightPipe){
            if(current.getDirection() == Direction.VERTICAL){
                boolean inBounds = (current.getCoordX()-1 >= 0) && (current.getCoordX()-1 <= gridSize-1);
                boolean inBounds2 = (current.getCoordX()+1 >= 0) && (current.getCoordX()+1 <= gridSize-1);
                if(inBounds && !grid[current.getCoordX()-1][current.getCoordY()].isVisited() && grid[current.getCoordX()-1][current.getCoordY()].getDirection() != Direction.UPRIGHT
                        && grid[current.getCoordX()-1][current.getCoordY()].getDirection() != Direction.UPLEFT){
                    return grid[current.getCoordX()-1][current.getCoordY()];
                }
                else if(inBounds2 && grid[current.getCoordX()+1][current.getCoordY()].getDirection() != Direction.DOWNRIGHT
                        && grid[current.getCoordX()+1][current.getCoordY()].getDirection() != Direction.DOWNLEFT){
                    return grid[current.getCoordX()+1][current.getCoordY()];
                }
            }
            else if(current.getDirection() == Direction.HORIZONTAL){
                boolean inBounds = (current.getCoordY()+1 >= 0) && (current.getCoordY()+1 <= gridSize-1);
                boolean inBounds2 = (current.getCoordY()-1 >= 0) && (current.getCoordY()-1 <= gridSize-1);
                if(inBounds && !grid[current.getCoordX()][current.getCoordY()+1].isVisited() && grid[current.getCoordX()][current.getCoordY()+1].getDirection() != Direction.DOWNRIGHT
                && grid[current.getCoordX()][current.getCoordY()+1].getDirection() != Direction.UPRIGHT){
                    return grid[current.getCoordX()][current.getCoordY()+1];
                }
                else if(inBounds2 && grid[current.getCoordX()][current.getCoordY()-1].getDirection() != Direction.DOWNLEFT
                        && grid[current.getCoordX()][current.getCoordY()-1].getDirection() != Direction.UPLEFT){
                    return grid[current.getCoordX()][current.getCoordY()-1];
                }
            }
        }
        else if(current instanceof CurvedPipe){
            if(current.getDirection() == Direction.UPLEFT){
                boolean inBounds = (current.getCoordY()-1 >= 0) && (current.getCoordY()-1 <= gridSize-1);
                boolean inBounds2 = (current.getCoordX()-1 >= 0) && (current.getCoordX()-1 <= gridSize-1);
                if(inBounds && !grid[current.getCoordX()][current.getCoordY()-1].isVisited() && grid[current.getCoordX()][current.getCoordY()-1].getDirection() != Direction.VERTICAL
                        && grid[current.getCoordX()][current.getCoordY()-1].getDirection() != Direction.DOWNLEFT){
                    return grid[current.getCoordX()][current.getCoordY()-1];
                }
                else if(inBounds2 && grid[current.getCoordX()-1][current.getCoordY()].getDirection() != Direction.HORIZONTAL
                        && grid[current.getCoordX()-1][current.getCoordY()].getDirection() != Direction.UPRIGHT){
                    return grid[current.getCoordX()-1][current.getCoordY()];
                }
            }
            else if(current.getDirection() == Direction.UPRIGHT){
                boolean inBounds = (current.getCoordX()-1 >= 0) && (current.getCoordX()-1 <= gridSize-1);
                boolean inBounds2 = (current.getCoordY()+1 >= 0) && (current.getCoordY()+1 <= gridSize-1);
                if(inBounds && !grid[current.getCoordX()-1][current.getCoordY()].isVisited() && grid[current.getCoordX()-1][current.getCoordY()].getDirection() != Direction.HORIZONTAL
                        && grid[current.getCoordX()-1][current.getCoordY()].getDirection() != Direction.UPLEFT){
                    return grid[current.getCoordX()-1][current.getCoordY()];
                }
                else if(inBounds2 && grid[current.getCoordX()][current.getCoordY()+1].getDirection() != Direction.DOWNRIGHT
                        && grid[current.getCoordX()][current.getCoordY()+1].getDirection() != Direction.VERTICAL){
                    return grid[current.getCoordX()][current.getCoordY()+1];
                }
            }
            else if(current.getDirection() == Direction.DOWNLEFT){
                boolean inBounds = (current.getCoordY()-1 >= 0) && (current.getCoordY()-1 <= gridSize-1);
                boolean inBounds2 = (current.getCoordX()+1 >= 0) && (current.getCoordX()+1 <= gridSize-1);
                if(inBounds && !grid[current.getCoordX()][current.getCoordY()-1].isVisited() && grid[current.getCoordX()][current.getCoordY()-1].getDirection() != Direction.UPLEFT
                        && grid[current.getCoordX()][current.getCoordY()-1].getDirection() != Direction.VERTICAL){
                    return grid[current.getCoordX()][current.getCoordY()-1];
                }
                else if(inBounds2 && grid[current.getCoordX()+1][current.getCoordY()].getDirection() != Direction.DOWNRIGHT
                        && grid[current.getCoordX()+1][current.getCoordY()].getDirection() != Direction.HORIZONTAL){
                    return grid[current.getCoordX()+1][current.getCoordY()];
                }
            }
            else if(current.getDirection() == Direction.DOWNRIGHT){
                boolean inBounds = (current.getCoordY()+1 >= 0) && (current.getCoordY()+1 <= gridSize-1);
                boolean inBounds2 = (current.getCoordX()+1 >= 0) && (current.getCoordX()+1 <= gridSize-1);
                if(inBounds && !grid[current.getCoordX()][current.getCoordY()+1].isVisited() && grid[current.getCoordX()][current.getCoordY()+1].getDirection() != Direction.VERTICAL
                        && grid[current.getCoordX()][current.getCoordY()+1].getDirection() != Direction.UPRIGHT){
                    return grid[current.getCoordX()][current.getCoordY()+1];
                }
                else if(inBounds2 && grid[current.getCoordX()+1][current.getCoordY()].getDirection() != Direction.DOWNLEFT
                        && grid[current.getCoordX()+1][current.getCoordY()].getDirection() != Direction.HORIZONTAL){
                    return grid[current.getCoordX()+1][current.getCoordY()];
                }
            }
        }
        else if(current instanceof StartFinishPipe){
            if (current.getDirection() == Direction.UP) {
                boolean inBounds = (current.getCoordX()-1 >= 0) && (current.getCoordX()-1 <= gridSize-1);
                if(inBounds){
                    return grid[current.getCoordX() - 1][current.getCoordY()];
                }
            } else if (current.getDirection() == Direction.RIGHT) {
                boolean inBounds = (current.getCoordY()+1 >= 0) && (current.getCoordY()+1 <= gridSize-1);
                if(inBounds){
                    return grid[current.getCoordX()][current.getCoordY() + 1];
                }
            } else if (current.getDirection() == Direction.DOWN) {
                boolean inBounds = (current.getCoordX()+1 >= 0) && (current.getCoordX()+1 <= gridSize-1);
                if(inBounds){
                    return grid[current.getCoordX() + 1][current.getCoordY()];
                }
            } else if (current.getDirection() == Direction.LEFT) {
                boolean inBounds = (current.getCoordY()-1 >= 0) && (current.getCoordY()-1 <= gridSize-1);
                if(inBounds){
                    return grid[current.getCoordX()][current.getCoordY() - 1];
                }
            }
        }
        return null;
    }
    public void setAllVisitedFalse(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                    grid[i][j].setVisited(false);
            }
        }
    }
}