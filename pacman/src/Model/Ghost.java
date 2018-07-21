package Model;

import Controller.Direction;
import java.util.ArrayList;
import java.util.List;


public class Ghost extends GhostComponent{
    
    private final GameElements GE;
    private Direction dir;
    private final int SCORE = 20;
    private final int POWER = 1;
    private ArrayList<Direction> playablesDirections;

    public Ghost(int xCoord, int yCoord, AbstractCase[][] map,GameElements ge) {
        super(xCoord, yCoord, map);
        this.GE = ge;
        this.dir = Direction.UP;
    }
    
    public Ghost(Ghost origin){
        super(origin);
        this.GE = origin.GE;
        this.dir = origin.dir;
    }
    
    @Override
    public Ghost copy(){
        return new Ghost(this);
    }
    
    @Override
    public GameElements getGameElement() {
        return this.GE;
    }
    
    @Override
    public Direction getDirection(){
        return this.dir;
    }
    
    @Override
    public void setDirection(Direction newDir){
        this.dir = newDir;
    }

    @Override
    public int getPower() {
        return this.POWER;
    }

    @Override
    public int getScore() {
        return this.SCORE;
    }

    @Override
    public List<GhostComponent> getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString(){
        return this.st + " pos = " + this.getX() + ":" + this.getY();
    }

    @Override
    public void startTimeline(List<GhostComponent> ghList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stopTimeline() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addDirections(ArrayList<Direction> playables) {
        this.playablesDirections = playables;
    }
}
