package Model;

import Model.Motion.AStarPathFinding;
import Model.Motion.RandomMove;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import Model.Motion.AiMotion;


//Ghost chasseur
public class Blinky extends Ghost{

    public Blinky(int xCoord, int yCoord, AbstractCase[][] map, GameElements ge) {
        super(xCoord, yCoord, map, ge);
        this.motion = new AStarPathFinding(map);
    }
    
    public Blinky(Blinky origin){
        super(origin);
        this.motion = origin.motion;
    }
    
    @Override
    public Blinky copy(){
        return new Blinky(this);
    }
    
    /*
    * pendant 3 secondes le Ghost se déplace de façon "aléatoire".
    * cela permet de séparer efficacement les Group s'il y a 2 chasseurs dedans (s'ils cherchent tous les 2
    * le chemin le plus court, à la séparation du Group => se recomposent immédiatement car vont dans la même drection. 
    */
    @Override
    public void setTmpRandomMover(){
        AiMotion tmp = this.motion;
        this.motion = new RandomMove(tmp.getGrid());
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(3), ae-> this.motion = tmp));
        t.setCycleCount(1);
        t.play();
    }
    
}
