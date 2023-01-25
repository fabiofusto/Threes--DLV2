package application.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cell")
public class ThreesBlock {
    @Param(0)
    private int x;
    @Param(1)
    private int y;
    @Param(2)
    private int number;
    private boolean movable = false;

    public ThreesBlock() {}

    public ThreesBlock(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public int computeScore(){
        if(this.number < 3) return 0;
        else {
            int count = 1;
            int temp = this.number;
            while(temp != 3) {
                temp = temp/2;
                count++;
            }
            return (int) Math.pow(3,count);
        }
    }
}
