package Model;

import Model.Motion.AStarPathFinding;
import Model.Motion.RandomMove;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

//Ghost lunatique : change toutes les 10 secondes de comportement (chasseur puis aléatoire)
public class Clyde extends Ghost{
    
    private int choiceAi = 0;
    private final Timeline t;
    
    public Clyde(int xCoord, int yCoord, AbstractCase[][] map, GameElements ge) {
        super(xCoord, yCoord, map, ge);
        t = new Timeline(new KeyFrame(Duration.seconds(10), ae->changeAiMover()));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
    }
    
    public Clyde(Clyde origin){
        super(origin);
        this.choiceAi = origin.choiceAi;
        double currentT = 10 - origin.t.getCurrentTime().toSeconds();
        this.t = new Timeline(new KeyFrame(Duration.seconds(currentT)
                    , ae -> this.changeAiMover()));
    }
    
    @Override
    public Clyde copy(){
        return new Clyde(this);
    }
    
    
    public void restartTimeline(){
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
    }
    
    //méthode qui permet de changer de classe de déplacement
    private void changeAiMover(){
        if(this.choiceAi == 0){
            this.motion = new AStarPathFinding(this.motion.getGrid());
            this.choiceAi = 1;
        }
        else{
            this.motion = new RandomMove(this.motion.getGrid());
            this.choiceAi = 0;
        }
    }
    @Override
    public String toString(){
        return "Clyde " + "pos = " + this.getX() + ":" + this.getY(); 
    }
    
}
