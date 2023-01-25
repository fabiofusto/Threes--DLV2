package application.controller;

import application.view.ThreesFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
    private ThreesFrame mainFrame;
//    private KeyHandler handler;

    public KeyListener(ThreesFrame frame) {
        this.mainFrame = frame;
//        this.handler = new KeyHandler(mainFrame);
        KeyHandler.getInstance().setMainFrame(mainFrame);
//        this.mainFrame.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        KeyHandler.getInstance().keyPressed(e);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        KeyHandler.getInstance().keyReleased(e);
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
