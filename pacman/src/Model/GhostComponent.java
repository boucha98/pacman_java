package Model;

import Model.Motion.RandomMove;
import Controller.Direction;
import java.util.ArrayList;
import java.util.List;
import Model.Motion.AiMotion;

public abstract class GhostComponent extends Character{
    
    protected AiMotion motion;
    protected String st;
    
    public GhostComponent(int x, int y, AbstractCase[][] map){
        super(x,y);
        motion = new RandomMove(map);
    }
    
    public GhostComponent(GhostComponent origin){
        super(origin);
        this.motion = origin.motion;
    }
    
    
    //méthodes communes aux Ghosts et GhostGroup
    
    public abstract GhostComponent copy();//utile pour copie profonde de List<GhostComponent> (impossible de faire new GhostComponent(...)  car classe abstraite)
    
    public abstract int getPower(); 
 
    public abstract int getScore();
  
    public abstract Direction getDirection();
    
    public abstract void setDirection(Direction newDir);
    
    public void move(AbstractCase pacmanLocation){
        AbstractCase nextStep = motion.getNextStep(this, pacmanLocation);
        this.setX(nextStep.getX());
        this.setY(nextStep.getY());
    }
    
    //méthodes pour les GhostGroup
    public abstract List<GhostComponent> getList();
    
    public abstract void startTimeline (List <GhostComponent> ghList);
    
    public abstract void stopTimeline();
    
    public abstract void addDirections(ArrayList<Direction> playables);
    
    //méthode définit uniquement pour Blinky
    public void setTmpRandomMover(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void setStringName(String s){
        this.st = s;
    }
}
