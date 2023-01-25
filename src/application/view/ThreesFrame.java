package application.view;

import application.IA.DLV;
import application.controller.KeyListener;
import application.model.GameStatus;
import application.resources.ImagesPath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreesFrame extends JFrame {

    private KeyListener keyListener;

    private JPanel gameBoardFrame;
    private JPanel jPanel1;
//    private JButton newGameButton;
    private JPanel nextBlockFrame;
    private JLabel nextBlockLabel;
    private JLabel scoreDisplayLabel;
    private JLabel scoreLabel;

    private GameBoardPanel gameBoard;
//    private GameStatus status;
    private NextBlockPanel nextBlockPanel;

    public ThreesFrame() {
        initComponents();
        initMyComponents();

        this.setIconImage(new ImageIcon(getClass().getResource(ImagesPath.appIconPath)).getImage());
        keyListener = new KeyListener(this);
        this.addKeyListener(keyListener);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setVisible(true);
        startGame();
    }

    private void initMyComponents() {
        gameBoard = new GameBoardPanel();
        gameBoard.setVisible(true);
        gameBoardFrame.setLayout(new BorderLayout());
        gameBoardFrame.add(gameBoard,BorderLayout.CENTER);

        nextBlockPanel = new NextBlockPanel();
        nextBlockPanel.setVisible(true);
        nextBlockFrame.setLayout(new BorderLayout());
        nextBlockFrame.add(nextBlockPanel,BorderLayout.CENTER);

//        newGameButton.setMargin(new Insets(0,0,0,0));
//        newGameButton.setIconTextGap(0);
//        newGameButton.setBorderPainted(false);
//        newGameButton.setBorder(null);
//        newGameButton.setText(null);
//        newGameButton.setFocusPainted(false);
//        newGameButton.setContentAreaFilled(false);

        pack();
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        nextBlockLabel = new JLabel();
        scoreLabel = new JLabel();
        gameBoardFrame = new JPanel();
        scoreDisplayLabel = new JLabel();
//        newGameButton = new JButton();
        nextBlockFrame = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Threes!");
        setResizable(false);
        //setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        jPanel1.setBackground(new Color(249, 249, 249));

        nextBlockLabel.setFont(new Font("Comic Sans MS", 0, 12));
        nextBlockLabel.setForeground(new Color(119, 126, 140));
        nextBlockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nextBlockLabel.setText("Next block");

        scoreLabel.setFont(new Font("Comic Sans MS", 0, 12));
        scoreLabel.setForeground(new Color(119, 126, 140));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setText("Score");

        gameBoardFrame.setBackground(new Color(187, 217, 217));
        GroupLayout gameBoardFrameLayout = new GroupLayout(gameBoardFrame);
        gameBoardFrame.setLayout(gameBoardFrameLayout);
        gameBoardFrameLayout.setHorizontalGroup(
                gameBoardFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        gameBoardFrameLayout.setVerticalGroup(
                gameBoardFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 600, Short.MAX_VALUE)
        );

        scoreDisplayLabel.setFont(new Font("Comic Sans MS", 0, 32));
        scoreDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);

//        newGameButton.setFont(new Font("Microsoft JhengHei", 0, 12));
//        newGameButton.setIcon(new ImageIcon(getClass().getResource(ImagesPath.newGameButton)));
//        newGameButton.setPressedIcon(new ImageIcon(getClass().getResource(ImagesPath.newGamePressButton)));
//        newGameButton.setRolloverIcon(new ImageIcon(getClass().getResource(ImagesPath.newGamePressButton)));
//        // bordo del bottone
//        newGameButton.addActionListener(new ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                newGameButtonActionPerformed(evt);
//            }
//        });

        nextBlockFrame.setBackground(new Color(249, 249, 249));
        GroupLayout nextBlockFrameLayout = new GroupLayout(nextBlockFrame);
        nextBlockFrame.setLayout(nextBlockFrameLayout);
        nextBlockFrameLayout.setHorizontalGroup(
                nextBlockFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 66, Short.MAX_VALUE)
        );
        nextBlockFrameLayout.setVerticalGroup(
                nextBlockFrameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 67, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(gameBoardFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(28, 28, 28)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                                                        .addComponent(newGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(scoreDisplayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(47, 47, 47)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(nextBlockLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(nextBlockFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
//                                                .addComponent(newGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(nextBlockFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(nextBlockLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(scoreLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(scoreDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18))
                                        .addComponent(gameBoardFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(19, 19, 19))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

//    private void newGameButtonActionPerformed(ActionEvent e) {startGame();}

    private void startGame() {
        GameStatus.getInstance().resetStatus();
        scoreDisplayLabel.setText("");

        nextBlockPanel.repaint();
        gameBoard.repaint();

        this.requestFocusInWindow();

        GameStatus.getInstance().printBlocks();
        System.out.println();
        new DLV();
    }

    public JLabel getNextBlockLabel(){
        return nextBlockLabel;
    }

    public GameBoardPanel getGameBoardPanel(){
        return gameBoard;
    }

    public JLabel getScorePanel(){
        return scoreDisplayLabel;
    }

    public NextBlockPanel getNextBlockPanel() {
        return nextBlockPanel;
    }

    public static void main(String[] args) {
//        System.setProperty("awt.useSystemAAFontSettings", "on");
//        System.setProperty("swing.aatext", "true");
//
//        EventQueue.invokeLater(() -> {
            new ThreesFrame();
//        });
    }


}
