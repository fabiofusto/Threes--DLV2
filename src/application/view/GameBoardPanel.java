package application.view;

import application.controller.KeyListener;
import application.model.GameStatus;
import application.model.ThreesBlock;
import application.util.Direction;

import javax.swing.*;
import java.awt.*;

public class GameBoardPanel extends JPanel {
    public static final int MAX_WIDTH = 400;
    public static final int MAX_HEIGHT = 600;

//    private GameStatus status = null;

    private int paintDirection = Direction.NONE;

    public GameBoardPanel() {
        initComponents();
    }

    private void initComponents() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, MAX_WIDTH, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, MAX_HEIGHT, Short.MAX_VALUE)
        );
    }

//    public GameStatus getStatus() {
//        return status;
//    }

//    public void setStatus(GameStatus status) {
//        this.status = status;
//    }

    public int getPaintDirection() {
        return paintDirection;
    }

    public void setPaintDirection(int paintDirection) {
        this.paintDirection = paintDirection;
    }

    public boolean isGameStarted() {
        return GameStatus.getInstance() != null;
    }

    private void paintGameStatus(Graphics2D g2d) {
        Color bgColor = new Color(187,217,217);
        
        setBackground(bgColor);
        if(isGameStarted()) {
            paintMainBoard(g2d);
        }
    }

    private void paintMainBoard(Graphics2D g2d) {
        switch(paintDirection){
            case Direction.NORTH:
            case Direction.NONE:
                //status.printBlocks();
                for(int i=0;i<4;i++){
                    for(int j=0;j<4;j++){
                        ThreesBlock block = GameStatus.getInstance().getBlock(i, j);
                        paintBlock(g2d, block);
                    }
                }
                break;
            case Direction.SOUTH:
                //status.printBlocks();
                for(int i=3;i>-1;i--){
                    for(int j=0;j<4;j++){
                        ThreesBlock block = GameStatus.getInstance().getBlock(i, j);
                        paintBlock(g2d, block);
                    }
                }
                break;
            case Direction.WEST:
                //status.printBlocks();
                for(int j=0;j<4;j++){
                    for(int i=0;i<4;i++){
                        ThreesBlock block = GameStatus.getInstance().getBlock(i, j);
                        paintBlock(g2d, block);
                    }
                }
                break;
            case Direction.EAST:
                //status.printBlocks();
                for(int j=3;j>-1;j--){
                    for(int i=0;i<4;i++){
                        ThreesBlock block = GameStatus.getInstance().getBlock(i, j);
                        paintBlock(g2d, block);
                    }
                }
                break;
        }
    }

    public void drawString(Graphics g, String str, int xPos, int yPos) {
        int strWidth = g.getFontMetrics().stringWidth(str);
        g.drawString(str, xPos + strWidth / 2, yPos);
    }

    private void paintBlock(Graphics2D g2d, ThreesBlock block){
        int blockWidth = MAX_WIDTH/4;
        int blockHeight = MAX_HEIGHT/4;

        //draw background
        if(block.getNumber()==0){
            //System.out.println("Block 0");
            g2d.setColor(new Color(255,255,255));
        }else if(block.getNumber()==1){
            //System.out.println("Block 1:  x="+block.getX()+" y="+block.getY());
            g2d.setColor(new Color(95,169,242));
            g2d.fillRoundRect(block.getX(), block.getY(), blockWidth-2, blockHeight-2, 20, 20);
            g2d.setColor(new Color(102,203,255));
            g2d.fillRoundRect(block.getX(), block.getY(), blockWidth-2, blockHeight-12, 20, 20);
            //g2d.drawImage(blueOneImage, 0, 0, 100, 150, null);
        }else if(block.getNumber()==2){
            //System.out.println("Block 2:  x="+block.getX()+" y="+block.getY());
            g2d.setColor(new Color(204,82,123));
            g2d.fillRoundRect(block.getX(), block.getY(), blockWidth-2, blockHeight-2, 20, 20);
            g2d.setColor(new Color(255,102,128));
            g2d.fillRoundRect(block.getX(), block.getY(), blockWidth-2, blockHeight-12, 20, 20);
            //g2d.drawImage(redTwoImage, 0, 0, null);
        }else{
            //System.out.println("Block 3+:  x="+block.getX()+" y="+block.getY());
            g2d.setColor(new Color(255,204,102));
            g2d.fillRoundRect(block.getX(), block.getY(), blockWidth-2, blockHeight-2, 20, 20);
            g2d.setColor(new Color(254,255,255));
            g2d.fillRoundRect(block.getX(), block.getY(), blockWidth-2, blockHeight-12, 20, 20);
        }

        //draw number
        if(block.getNumber()==0){
            g2d.setColor(new Color(255,255,255));
        }else if(block.getNumber()==1){
            g2d.setColor(new Color(255,255,255));
        }else if(block.getNumber()==2){
            g2d.setColor(new Color(255,255,255));
        }else{
            g2d.setColor(new Color(0,0,0));
        }
        Font font = new Font("Comic Sans MS",Font.BOLD,36);
        if(block.getNumber()!=0){
            g2d.setFont(font);
            String num = ""+block.getNumber();
            int numWidth = g2d.getFontMetrics().stringWidth(num);
            g2d.drawString(num, block.getX()+blockWidth/2-numWidth/2, block.getY()+blockHeight/2+5); // 居中对齐文本绘制公式
        }

        //game over, draw scores
        if(GameStatus.getInstance().isGameOver()){
            int score = block.computeScore();
            if(score > 0){
                g2d.setColor(new Color(255,102,128));
                font = new Font("Comic Sans MS",Font.BOLD,15);
                g2d.setFont(font);
                String blockScore = "+"+score;
                int scoreWidth = g2d.getFontMetrics().stringWidth(blockScore);
                g2d.drawString(blockScore, block.getX()+blockWidth/2-scoreWidth/2, block.getY()+blockHeight/2+25);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintGameStatus(g2d);
    }
}
