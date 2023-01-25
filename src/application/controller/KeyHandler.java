package application.controller;

import application.model.GameStatus;
import application.model.ThreesBlock;
import application.util.Direction;
import application.view.GameBoardPanel;
import application.view.NextBlockPanel;
import application.view.ThreesFrame;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class KeyHandler {

    private ThreesFrame mainFrame;
    private ThreesBlock[][] tempBlocks;
    private int direction = Direction.NONE;

    private static KeyHandler instance = null;

    public static KeyHandler getInstance() {
        if(instance == null) instance = new KeyHandler();
        return instance;
    }

    public void setMainFrame(ThreesFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    private KeyHandler() {
        this.tempBlocks = new ThreesBlock[4][4];
    }

//    public KeyHandler(ThreesFrame mainFrame) {
//        this.mainFrame = mainFrame;
//        this.tempBlocks = new ThreesBlock[4][4];
//    }

    private GameBoardPanel getGameBoard(){
        return mainFrame.getGameBoardPanel();
    }

    private JLabel getScorePanel() {
        return mainFrame.getScorePanel();
    }
    private NextBlockPanel getNextBlockPanel(){
        return mainFrame.getNextBlockPanel();
    }

    public void moveBoard(int d) {
        if(getGameBoard().isGameStarted() && !GameStatus.getInstance().isGameOver()) {
//            System.out.println(newDirection);

            if(d == Direction.NONE){
                resetAllBlocksMovable();
                resetAllTempBlocks();
            }
            else {
                boolean isMovable = false;
                if(direction == Direction.NONE) {
                    copyToTempBlocks();
                    if(isBlocksMovable(d)) {
                        direction = d;
                        computeTempBlocks();
                        isMovable = true;
                    } else if(direction == d) isMovable = true;
                }
                if(isMovable) moveBlocks(direction);
            }
            getGameBoard().setPaintDirection(d);

            //////////////////////////////

            if(direction != Direction.NONE) {
                GameStatus.getInstance().setBlocks(tempBlocks);
                resetAllTempBlocks();

                GameStatus.getInstance().nextBlockEnter(direction);

                //resetAllBlocksPosition();
                resetAllBlocksMovable();

                direction = Direction.NONE;
            } else {
                //resetAllBlocksPosition();
                resetAllBlocksMovable();
            }

            if(isOver()) {
                GameStatus.getInstance().setGameOver(true);
                int score = GameStatus.getInstance().computeAllScores();
                getScorePanel().setText(""+score);
            }

            getGameBoard().repaint();
            getNextBlockPanel().repaint();
            getGameBoard().setPaintDirection(Direction.NONE);

            GameStatus.getInstance().printBlocks();
            System.out.println();
//            getGameBoard().getStatus().printCoordinates();
//            System.out.println();
        }
    }

    public void keyPressed(KeyEvent e) {
        if(getGameBoard().isGameStarted() && !GameStatus.getInstance().isGameOver()) {

            int newDirection = judgeDirection(e);
//            System.out.println(newDirection);

            if(newDirection == Direction.NONE){
                resetAllBlocksMovable();
                resetAllTempBlocks();
            }
            else {
                boolean isMovable = false;
                if(direction == Direction.NONE) {
                    copyToTempBlocks();
                    if(isBlocksMovable(newDirection)) {
                        direction = newDirection;
                        computeTempBlocks();
                        isMovable = true;
                    } else if(direction == newDirection) isMovable = true;
                }
                if(isMovable) moveBlocks(direction);
            }
            getGameBoard().setPaintDirection(newDirection);
        }
    }

    public void keyReleased(KeyEvent e) {
        if(getGameBoard().isGameStarted() && !GameStatus.getInstance().isGameOver()) {
            if(direction != Direction.NONE) {
                GameStatus.getInstance().setBlocks(tempBlocks);
                resetAllTempBlocks();

                GameStatus.getInstance().nextBlockEnter(direction);

                //resetAllBlocksPosition();
                resetAllBlocksMovable();

                direction = Direction.NONE;
            } else {
                //resetAllBlocksPosition();
                resetAllBlocksMovable();
            }

            if(isOver()) {
                GameStatus.getInstance().setGameOver(true);
                int score = GameStatus.getInstance().computeAllScores();
                getScorePanel().setText(""+score);
            }

            getGameBoard().repaint();
            getNextBlockPanel().repaint();
            getGameBoard().setPaintDirection(Direction.NONE);

            GameStatus.getInstance().printBlocks();
            System.out.println();
//            getGameBoard().getStatus().printCoordinates();
//            System.out.println();
        }
    }

    private boolean isOver(){
        copyToTempBlocks();
        if(isBlocksMovable(Direction.NORTH)){
            resetAllTempBlocks();
            return false;
        }else if(isBlocksMovable(Direction.SOUTH)){
            resetAllTempBlocks();
            return false;
        }else if(isBlocksMovable(Direction.WEST)){
            resetAllTempBlocks();
            return false;
        }else if(isBlocksMovable(Direction.EAST)){
            resetAllTempBlocks();
            return false;
        }
        resetAllTempBlocks();
        return true;
    }

    private void resetAllTempBlocks(){
        this.tempBlocks = new ThreesBlock[4][4];
    }

    private void resetAllBlocksMovable(){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                ThreesBlock block = GameStatus.getInstance().getBlock(i, j);
                block.setMovable(false);
            }
        }
    }

    private void copyToTempBlocks(){
        for(int i = 0 ; i < 4 ; i++){
            for(int j = 0 ; j < 4 ; j++){
                ThreesBlock oldBlock = GameStatus.getInstance().getBlock(i, j);
                this.tempBlocks[i][j] = new ThreesBlock(oldBlock.getNumber());
                this.tempBlocks[i][j].setX(oldBlock.getX());
                this.tempBlocks[i][j].setY(oldBlock.getY());
            }
        }
    }

    private int judgeDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {return Direction.WEST;}
            case KeyEvent.VK_RIGHT -> {return Direction.EAST;}
            case KeyEvent.VK_UP -> {return Direction.NORTH;}
            case KeyEvent.VK_DOWN -> {return Direction.SOUTH;}
            default -> {}
        }
        return Direction.NONE;
    }

    private boolean isBlocksMovable(int direction){
        boolean isMovable = true;
        switch (direction) {
            case Direction.NORTH -> isMovable = isBlocksMovableNorth();
            case Direction.SOUTH -> isMovable = isBlocksMovableSouth();
            case Direction.WEST -> isMovable = isBlocksMovableWest();
            case Direction.EAST -> isMovable = isBlocksMovableEast();
            default -> {}
        }
        return isMovable;
    }

    private boolean isBlocksMovableEast() {
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j > -1; j--) {
                ThreesBlock currentBlock = tempBlocks[i][j];
                if(currentBlock.getNumber() != 0) {
                    if(j < 3) {
                        ThreesBlock collisionBlock = tempBlocks[i][j+1];
                        if(collisionBlock.getNumber() == 0) return true;
                        else if( (currentBlock.getNumber()==1 && collisionBlock.getNumber()==2) ||
                                (currentBlock.getNumber()==2 && collisionBlock.getNumber()==1) )
                            return true;
                        else if(currentBlock.getNumber() == collisionBlock.getNumber() &&
                                currentBlock.getNumber()!=1 && currentBlock.getNumber()!=2)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isBlocksMovableWest() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ThreesBlock currentBlock = tempBlocks[i][j];
                if(currentBlock.getNumber() != 0) {
                    if(j > 0) {
                        ThreesBlock collisionBlock = tempBlocks[i][j-1];
                        if(collisionBlock.getNumber() == 0) return true;
                        else if( (currentBlock.getNumber()==1 && collisionBlock.getNumber()==2) ||
                                (currentBlock.getNumber()==2 && collisionBlock.getNumber()==1) )
                            return true;
                        else if(currentBlock.getNumber() == collisionBlock.getNumber() &&
                                currentBlock.getNumber()!=1 && currentBlock.getNumber()!=2)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isBlocksMovableSouth() {
        for (int i = 3; i > -1; i--) {
            for (int j = 0; j < 4; j++) {
                ThreesBlock currentBlock = tempBlocks[i][j];
                if(currentBlock.getNumber() != 0) {
                    if(i < 3) {
                        ThreesBlock collisionBlock = tempBlocks[i+1][j];
                        if(collisionBlock.getNumber() == 0) return true;
                        else if( (currentBlock.getNumber()==1 && collisionBlock.getNumber()==2) ||
                                (currentBlock.getNumber()==2 && collisionBlock.getNumber()==1) )
                            return true;
                        else if(currentBlock.getNumber() == collisionBlock.getNumber() &&
                                currentBlock.getNumber()!=1 && currentBlock.getNumber()!=2)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isBlocksMovableNorth() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ThreesBlock currentBlock = tempBlocks[i][j];
                if(currentBlock.getNumber() != 0) {
                    if(i > 0) {
                        ThreesBlock collisionBlock = tempBlocks[i - 1][j];
                        if(collisionBlock.getNumber() == 0) return true;
                        else if( (currentBlock.getNumber()==1 && collisionBlock.getNumber()==2) ||
                                (currentBlock.getNumber()==2 && collisionBlock.getNumber()==1) )
                            return true;
                        else if(currentBlock.getNumber() == collisionBlock.getNumber() &&
                                currentBlock.getNumber()!=1 && currentBlock.getNumber()!=2)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    private void computeTempBlocks() {
        switch (direction) {
            case Direction.NORTH -> computeTempBlocksNorth();
            case Direction.SOUTH -> computeTempBlocksSouth();
            case Direction.WEST -> computeTempBlocksWest();
            case Direction.EAST -> computeTempBlocksEast();
            default -> {}
        }
    }

    private void computeTempBlocksEast() {
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j > -1; j--) {
                ThreesBlock currentBlock = tempBlocks[i][j];
                if(currentBlock.getNumber() != 0) {
                    if(j < 3) {
                        ThreesBlock collisionBlock = tempBlocks[i][j+1];
                        ThreesBlock originalBlock = GameStatus.getInstance().getBlock(i,j);

                        if(collisionBlock.getNumber()==0) {
                            collisionBlock.setNumber(currentBlock.getNumber());
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                        else if( (currentBlock.getNumber()==1 && collisionBlock.getNumber()==2) ||
                                (currentBlock.getNumber()==2 && collisionBlock.getNumber()==1) ) {
                            collisionBlock.setNumber(3);
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                        else if(currentBlock.getNumber()==collisionBlock.getNumber() &&
                                currentBlock.getNumber()!=1 && currentBlock.getNumber()!=2) {
                            collisionBlock.setNumber(currentBlock.getNumber()*2);
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                    }
                }
            }
        }
    }

    private void computeTempBlocksWest() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ThreesBlock currentBlock = tempBlocks[i][j];
                if(currentBlock.getNumber() != 0) {
                    if(j > 0) {
                        ThreesBlock collisionBlock = tempBlocks[i][j-1];
                        ThreesBlock originalBlock = GameStatus.getInstance().getBlock(i,j);

                        if(collisionBlock.getNumber()==0) {
                            collisionBlock.setNumber(currentBlock.getNumber());
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                        else if( (currentBlock.getNumber()==1 && collisionBlock.getNumber()==2) ||
                                (currentBlock.getNumber()==2 && collisionBlock.getNumber()==1) ) {
                            collisionBlock.setNumber(3);
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                        else if(currentBlock.getNumber()==collisionBlock.getNumber() &&
                                currentBlock.getNumber()!=1 && currentBlock.getNumber()!=2) {
                            collisionBlock.setNumber(currentBlock.getNumber()*2);
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                    }
                }
            }
        }
    }

    private void computeTempBlocksSouth() {
        for (int i = 3; i > -1; i--) {
            for (int j = 0; j < 4; j++) {
                ThreesBlock currentBlock = tempBlocks[i][j];
                if(currentBlock.getNumber() != 0) {
                    if(i < 3) {
                        ThreesBlock collisionBlock = tempBlocks[i+1][j];
                        ThreesBlock originalBlock = GameStatus.getInstance().getBlock(i,j);

                        if(collisionBlock.getNumber()==0) {
                            collisionBlock.setNumber(currentBlock.getNumber());
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                        else if( (currentBlock.getNumber()==1 && collisionBlock.getNumber()==2) ||
                                (currentBlock.getNumber()==2 && collisionBlock.getNumber()==1) ) {
                            collisionBlock.setNumber(3);
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                        else if(currentBlock.getNumber()==collisionBlock.getNumber() &&
                                currentBlock.getNumber()!=1 && currentBlock.getNumber()!=2) {
                            collisionBlock.setNumber(currentBlock.getNumber()*2);
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                    }
                }
            }
        }
    }

    private void computeTempBlocksNorth() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ThreesBlock currentBlock = tempBlocks[i][j];
                if(currentBlock.getNumber() != 0) {
                    if(i > 0) {
                        ThreesBlock collisionBlock = tempBlocks[i-1][j];
                        ThreesBlock originalBlock = GameStatus.getInstance().getBlock(i,j);

                        if(collisionBlock.getNumber()==0) {
                            collisionBlock.setNumber(currentBlock.getNumber());
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                        else if( (currentBlock.getNumber()==1 && collisionBlock.getNumber()==2) ||
                                 (currentBlock.getNumber()==2 && collisionBlock.getNumber()==1) ) {
                            collisionBlock.setNumber(3);
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                        else if(currentBlock.getNumber()==collisionBlock.getNumber() &&
                                currentBlock.getNumber()!=1 && currentBlock.getNumber()!=2) {
                            collisionBlock.setNumber(currentBlock.getNumber()*2);
                            currentBlock.setNumber(0);
                            originalBlock.setMovable(true);
                        }
                    }
                }
            }
        }
    }

    private void moveBlocks(int direction) {
        if(direction!=Direction.NONE){
            switch (direction) {
                case Direction.NORTH -> moveNorth();
                case Direction.SOUTH -> moveSouth();
                case Direction.WEST -> moveWest();
                case Direction.EAST -> moveEast();
                default -> {}
            }
        }
    }

    private void moveOneBlock(ThreesBlock block, int newX, int newY) {
        block.setX(newX); block.setY(newY);
    }

    private void moveEast() {
        int blockWidth = GameBoardPanel.MAX_WIDTH/4;
        for (int i = 0; i < 4; i++) {
            for (int j = 3; j > -1; j--) {
                ThreesBlock block = GameStatus.getInstance().getBlock(i,j);
                if(block.isMovable()){
                    int newX = block.getX()+blockWidth;
                    int newY = block.getY();
                    moveOneBlock(block,newX,newY);
                }
            }
        }
    }

    private void moveWest() {
        int blockWidth = GameBoardPanel.MAX_WIDTH/4;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ThreesBlock block = GameStatus.getInstance().getBlock(i,j);
                if(block.isMovable()){
                    int newX = block.getX()-blockWidth;
                    int newY = block.getY();
                    moveOneBlock(block,newX,newY);
                }
            }
        }
    }

    private void moveSouth() {
        int blockHeight = GameBoardPanel.MAX_HEIGHT/4;
        for (int i = 3; i > -1; i--) {
            for (int j = 0; j < 4; j++) {
                ThreesBlock block = GameStatus.getInstance().getBlock(i,j);
                if(block.isMovable()){
                    int newX = block.getX();
                    int newY = block.getY()+blockHeight;
                    moveOneBlock(block,newX,newY);
                }
            }
        }
    }

    private void moveNorth() {
        int blockHeight = GameBoardPanel.MAX_HEIGHT/4;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ThreesBlock block = GameStatus.getInstance().getBlock(i,j);
                if(block.isMovable()){
                    int newX = block.getX();
                    int newY = block.getY()-blockHeight;
                    moveOneBlock(block,newX,newY);
                }
            }
        }
    }

}
