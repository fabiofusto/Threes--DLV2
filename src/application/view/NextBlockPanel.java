package application.view;

import application.model.GameStatus;

import javax.swing.*;
import java.awt.*;

public class NextBlockPanel extends JPanel {
    public static final int MAX_WIDTH = 60;
    public static final int MAX_HEIGHT = 75;

//    private GameStatus status = null;

    public NextBlockPanel() {
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

    public boolean isGameStarted() {
        return GameStatus.getInstance() != null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintNextBlock(g2d);
    }

    private void paintNextBlock(Graphics2D g2d) {
        int blockWidth = MAX_WIDTH;
        int blockHeight = MAX_HEIGHT;

        Color bgColor = new Color(249,249,249);
        setBackground(bgColor);
        Font font = new Font("Comic Sans MS",Font.BOLD,36);

        if(isGameStarted()){
            int nextNumber = GameStatus.getInstance().getNextBlock().getNumber();

            switch (nextNumber){
                case 1:
                    g2d.setColor(new Color(95,169,242));
                    g2d.fillRoundRect(0, 0, blockWidth-2, blockHeight-2, 15, 15);
                    g2d.setColor(new Color(102,203,255));
                    g2d.fillRoundRect(0, 0, blockWidth-2, blockHeight-12, 15, 15);
                    break;
                case 2:
                    g2d.setColor(new Color(204,82,123));
                    g2d.fillRoundRect(0, 0, blockWidth-2, blockHeight-2, 15, 15);
                    g2d.setColor(new Color(255,102,128));
                    g2d.fillRoundRect(0, 0, blockWidth-2, blockHeight-12, 15, 15);
                    break;
                default:
                    g2d.setColor(new Color(255,204,102));
                    g2d.fillRoundRect(0, 0, blockWidth-2, blockHeight-2, 15, 15);
                    g2d.setColor(new Color(254,255,255));
                    g2d.fillRoundRect(0, 0, blockWidth-2, blockHeight-12, 15, 15);
                    if(nextNumber > 3) {
                        g2d.setFont(font);
                        g2d.setColor(new Color(0,0,0));
                        g2d.drawString("+", blockWidth/2-12, blockHeight/2+5);
                    }
            }
        }
    }
}
