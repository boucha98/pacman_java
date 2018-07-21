package Model;

import Controller.Direction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GhostGroup extends GhostComponent{
    
    private final List<GhostComponent> list;//Les 2 GhostComponent qui composent ce GhostGroup
    private final GameElements GE;
    private Direction dir;
    private ArrayList<Direction> playablesDirections;
    private double timeRemaining = 0.0;// pour la copie de GhostGroup dans un Memento : sauvegarder le temps restant pour la Timeline
    
    private Timeline timeline;

    public GhostGroup (int x, int y, AbstractCase[][] map, List<GhostComponent> ghostsList, GhostComponent g1, GhostComponent g2){
        super (x,y, map);
        this.GE = GameElements.GHOST_POWER;
        list = new ArrayList<>();
        list.add (g1);
        list.add (g2);
        stopTimelineException(g1);
        stopTimelineException(g2);
        this.setMyDirection(g1.getDirection());
        this.start(ghostsList);
    }
    
    public GhostGroup(GhostGroup origin){
        super(origin);
        this.dir = origin.dir;
        this.GE = origin.GE;
        this.list = new ArrayList<>();
        this.list.add(origin.list.get(0).copy());
        this.list.add(origin.list.get(1).copy());
        this.timeRemaining = 5 - origin.timeline.getCurrentTime().toSeconds();
    }
    
    @Override
    public GhostGroup copy(){
        return new GhostGroup(this);
    }
    
    /*
    dans Labyrinth, créer le new GhostGroup, 
    puis appeler la méthode startTimeline () sur ce groupe.
    appeler d'abord stopTimeline sur les GhostComponent 
    qui constituent ce groupe.
    */
    
    @Override
    public List<GhostComponent> getList() {
        return list;
    }

    @Override
    public GameElements getGameElement() {
        return this.GE;
    }

    @Override
    public int getPower() {
        int res = 0;
        for(int i =0; i < list.size(); ++i){
           res += list.get(i).getPower();
        }
        return res;
    }

    @Override
    public int getScore() {
        int res = 0;
        for(int i =0; i < list.size(); ++i){
           res += list.get(i).getScore();
        }
        return res;
    }

    @Override
    public Direction getDirection() {
       return this.dir;
    }

    @Override
    public void setDirection(Direction newDir) {
        this.dir = newDir;
    }
   
    @Override
    public String toString(){
        return " Ghost group [" + list.get(0).toString() + "/" + list.get(1).toString() + "] " + "pos = " + this.getX() + ":" + this.getY();
    }
    
    
    private void setMyDirection(Direction d){
        this.setDirection(d);
    }
    
    private void start(List<GhostComponent> ghList){
        this.startTimeline(ghList);
    }
    
    @Override
    public void startTimeline(List<GhostComponent> ghList) {
        KeyFrame kf = new KeyFrame(Duration.seconds(timeRemaining == 0.0 ? 5 : timeRemaining),
	        ae->this.divideGroup(ghList));
        timeline = new Timeline(kf);
        timeline.setCycleCount(1);
        timeline.play();
    }
    
    //stopper la timeline d'un GhostGroup qui rejoint un autre GhostGroup
    // et la mettre a 0
    @Override
    public void stopTimeline() {
        timeline.stop();
    }
    
    private void divideGroup(List <GhostComponent> ghList){
        Iterator<GhostComponent> it = ghList.iterator();
        while(it.hasNext()){
            GhostComponent tmp = it.next();
            if(tmp.equals(this)){
                it.remove();
            }
        }
        //ghList.remove(this);
        initGhost(list.get(0), this.getDirection(), ghList);
        Direction other = anotherDirection();
        initGhost(list.get(1), other, ghList);
    }
    
    @Override
    public void addDirections(ArrayList<Direction> playables){
        this.playablesDirections = playables;
    }

    private Direction anotherDirection(){
        ArrayList<Direction> tmp = new ArrayList<>();
        tmp.addAll(this.playablesDirections);
        tmp.remove(this.getDirection());
        return tmp.isEmpty() ? this.getDirection() : Direction.randomPlayableDirection(tmp);
    }
    
    //préparer un GhostComponent contenu dans le GhostGroup courant, avant de le réinsérer dans la ghostList du Labyrinth
    private void initGhost (GhostComponent gc, Direction d, List<GhostComponent> ghList){
        gc.setDirection(d);
        gc.setX(this.getX());
        gc.setY(this.getY());
        try{
            gc.startTimeline(ghList);
        }catch(UnsupportedOperationException e){
            System.out.println(e.getMessage() + ": startTimeline");
        }
        try{
            gc.setTmpRandomMover();
        }catch(UnsupportedOperationException e){
            System.out.println(e.getMessage() + ": setTmpRandomMover");
        }
        ghList.add(gc);
    }
    
    private void stopTimelineException(GhostComponent gc){
        try{
            gc.stopTimeline();
        }catch(UnsupportedOperationException e){
            System.out.println(e.getMessage() + ": stopTimeline");
        }
    }
}
