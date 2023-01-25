package application.model;

import application.util.Direction;
import application.view.GameBoardPanel;

import java.util.LinkedList;
import java.util.Random;

public class GameStatus {
    private ThreesBlock[][] blocks;
    private ThreesBlock nextBlock;
    private boolean gameOver;
    private final Random random = new Random();

    private static GameStatus instance = null;
    public static GameStatus getInstance(){
        if(instance == null) instance = new GameStatus();
        return instance;
    }

    public void resetStatus() {instance = null;}

    public GameStatus(){
        this.gameOver = false;
        ThreesBlock[][] temp = createNewBoard();
        setBlocks(temp);
        generateNextBlock();
    }

    public void printBlocks() {
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++) {
                System.out.print(this.blocks[i][j].getNumber()+"\t");
            }
            System.out.println();
        }
        System.out.println("Next block: "+this.nextBlock.getNumber());
    }

    public void printCoordinates() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(this.blocks[i][j].getX()+"-"+this.blocks[i][j].getY()+" ");
            }
            System.out.println();
        }
    }
    private ThreesBlock[][] createNewBoard() {
        ThreesBlock[][] temp = new ThreesBlock[4][4];

        int blockWidth = GameBoardPanel.MAX_WIDTH/4;
        int blockHeight = GameBoardPanel.MAX_HEIGHT/4;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++) {
                temp[i][j] = new ThreesBlock(0);
                temp[i][j].setX((j)*blockWidth);
                temp[i][j].setY((i)*blockHeight);
            }
        }


        int count = 3;
        while(count > 0) {
            int i = random.nextInt(4);
            int j = random.nextInt(4);
            if(temp[i][j].getNumber() == 0) {
                temp[i][j].setNumber(1);
                count--;
            }
        }

        count = 3;
        while(count > 0) {
            int i = random.nextInt(4);
            int j = random.nextInt(4);
            if(temp[i][j].getNumber() == 0) {
                temp[i][j].setNumber(2);
                count--;
            }
        }

        count = 3;
        while(count > 0) {
            int i = random.nextInt(4);
            int j = random.nextInt(4);
            if(temp[i][j].getNumber() == 0) {
                temp[i][j].setNumber(3);
                count--;
            }
        }

        return temp;
    }

    public void generateNextBlock() {
        int redCount = 0;
        int blueCount = 0;
        int whiteCount = 0;
        int maxNumber = 0;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++) {
                ThreesBlock temp = getBlock(i,j);
                if(temp.getNumber() == 1) {
                        blueCount++;
                } else if (temp.getNumber() == 2) {
                    redCount++;
                } else if (temp.getNumber() != 0) {
                    if(maxNumber < temp.getNumber())
                        maxNumber = temp.getNumber();
                    whiteCount++;
                }
            }
        }

        int number = decideNextBlockColor(nextBlock,whiteCount,blueCount,redCount,maxNumber);
        nextBlock = new ThreesBlock(number);
        nextBlock.setX(0);
        nextBlock.setY(0);
    }

    private int decideNextBlockColor(ThreesBlock nowBlock,int whiteCount,int blueCount,int redCount,int maxNumber) {
        int nowNumber = -1;
        if(nowBlock != null){
            nowNumber = nowBlock.getNumber();
        }
        int[] weights = {5, 5, 5};    //weights for white, blue and red, default for equals.
        for(int i=0;i<3;i++){
            if(i!=nowNumber){
                weights[i] = weights[i] + 15;
            }
        }
        if(redCount==blueCount){
            // nu cazz
        }else if(blueCount > redCount){
            weights[2] = weights[2] + 15*(blueCount - redCount);
        }else{
            weights[1] = weights[1] + 15*(redCount - blueCount);
        }
//        System.out.println(weights[0]+" "+weights[1]+" "+weights[2]);

        int newNumber = 0;
        int random = this.random.nextInt(weights[0]+weights[1]+weights[2]);
        if(random < weights[0]){
            //white
            int bigNumber = this.random.nextInt(100);
            if(bigNumber<10){
                //generate a big number
                if(maxNumber/8 < 3){
                    newNumber = 3;
                }else{
                    newNumber = maxNumber/8;
                }
            }else{
                newNumber = 3;
            }
        }else if(random < weights[0]+weights[1]){
            //blue
            newNumber = 1;
        }else{
            //red
            newNumber = 2;
        }

        return newNumber;
    }

    public void nextBlockEnter(int direction){
        LinkedList<Integer> emptyBlocks = new LinkedList<>();
        int index = -1;
        switch(direction){
            case Direction.NORTH:
                for(int j=0;j<4;j++){
                    ThreesBlock block = getBlock(3, j);
                    if(block.getNumber()==0){
                        emptyBlocks.add(j);
                    }
                }
                if(emptyBlocks.isEmpty()) return;

                index = random.nextInt(emptyBlocks.size());
                blocks[3][emptyBlocks.get(index)].setNumber(nextBlock.getNumber());
                generateNextBlock();
                break;
            case Direction.SOUTH:
                for(int j=0;j<4;j++){
                    ThreesBlock block = getBlock(0, j);
                    if(block.getNumber()==0){
                        emptyBlocks.add(j);
                    }
                }
                if(emptyBlocks.isEmpty()) return;

                index = random.nextInt(emptyBlocks.size());
                blocks[0][emptyBlocks.get(index)].setNumber(nextBlock.getNumber());
                generateNextBlock();
                break;
            case Direction.WEST:
                for(int i=0;i<4;i++){
                    ThreesBlock block = getBlock(i, 3);
                    if(block.getNumber()==0){
                        emptyBlocks.add(i);
                    }
                }
                if(emptyBlocks.isEmpty()) return;

                index = random.nextInt(emptyBlocks.size());
                blocks[emptyBlocks.get(index)][3].setNumber(nextBlock.getNumber());
                generateNextBlock();
                break;
            case Direction.EAST:
                for(int i=0;i<4;i++){
                    ThreesBlock block = getBlock(i, 0);
                    if(block.getNumber()==0){
                        emptyBlocks.add(i);
                    }
                }
                if(emptyBlocks.isEmpty()) return;

                index = random.nextInt(emptyBlocks.size());
                blocks[emptyBlocks.get(index)][0].setNumber(nextBlock.getNumber());
                generateNextBlock();
                break;
            default:
        }
    }

    public int computeAllScores() {
        int count = 0;
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                count += blocks[i][j].computeScore();
        }
        return count;
    }

    public ThreesBlock[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(ThreesBlock[][] blocks) {
        this.blocks = blocks;
    }

    public ThreesBlock getNextBlock() {
        return nextBlock;
    }

    public void setNextBlock(ThreesBlock nextBlock) {
        this.nextBlock = nextBlock;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public ThreesBlock getBlock(int i, int j) {
        return this.blocks[i][j];
    }
}
