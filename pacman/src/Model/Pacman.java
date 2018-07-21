package Model;

import Model.Creator.Labyrinth;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Pacman extends Character{
    
    private boolean superPacman = false;
    private GameElements GE;
    private Timeline timeline;
    private final int LIFES_START = 5;
    private final int SECONDS_SUPER = 5;
    
    private static CareTaker CT;

    public Pacman(int xCoord, int yCoord) {
        super(xCoord, yCoord);
        this.GE = GameElements.PACMAN;
    }
    
    public Pacman(Pacman origin){
        super(origin);
        this.GE = origin.GE;
        this.superPacman = origin.superPacman;
        if(this.superPacman){
            double t = SECONDS_SUPER - origin.timeline.getCurrentTime().toSeconds();
            this.timeline = new Timeline(new KeyFrame(Duration.seconds(t)
                    ,  ae -> this.changeNormalPacman()));
        }
    }
    
    public void restartTimeline(){
        if(this.isSuperPacman()){
            timeline.setCycleCount(1);
            timeline.play();
        } 
    }
    
    public boolean isSuperPacman(){
        return this.superPacman;
    }
    
    public void changeSuperPacman(boolean t){
        if(t == true){
            double timeSuperLeft = 0.0;
            //si pacman "mange" un champignon alors qu'il est déjà en superPacman => on ajoute son temps restant pour l'aditionner à SECONDS_SUPER
            if(this.superPacman){
                timeSuperLeft = SECONDS_SUPER - this.timeline.getCurrentTime().toSeconds();
                timeline.stop();
            }
            else{
                this.superPacman = true;
                this.GE = GameElements.SUPER_PACMAN;
            }
            timeline = new Timeline(new KeyFrame(Duration.seconds(SECONDS_SUPER + timeSuperLeft), ae -> this.changeNormalPacman()));
            timeline.setCycleCount(1);
            timeline.play();
        }
    } 
    
    private void changeNormalPacman(){
        this.superPacman = false;
        this.GE = GameElements.PACMAN;
    } 

    @Override
    public GameElements getGameElement() {
        return this.GE;
    }

    public void clash(Labyrinth lab, GhostComponent gc) {
        if(this.isSuperPacman()){
            lab.incrementScore(gc.getScore());
            gc.setX(gc.getXorigin());
            gc.setY(gc.getYorigin());
        }
        else{
            if(CT.size() < gc.getPower()){//inutile de lancer la récupération d'un Memento si la partie est perdue...
                lab.setLife(0);
            }
            else{
                //on utilise la puissance du GhostComponent rencontré comme index de recherche de Memento
                lab.restoreOldState(CT.getMemento(gc.getPower()));
                lab.setLife(CT.size());
            }
        }
    }
    
    //Pacman mange un élément Eatable. Ajustement des effets sur la partie.
    public void eat(Eatable e, Labyrinth lab){
        if(e != null){
            lab.incrementScore(e.getPoints());
            lab.decrementPacgommes(e.getPoints());
            this.changeSuperPacman(e.transform());
            if(e.save()){
                CT.addMemento(lab.saveState());
            }
        }
    }
    
    //initialiser le CareTaker avec un nombre de Memento correspondant au nombre de vies de Pacman en début de partie.
    public void initCareTaker(Labyrinth lab){
        CT = new CareTaker();
        for(int i = 0; i < LIFES_START; ++i){
            CT.addMemento(lab.saveState());
        }
    }
    
    @Override
    public String toString(){
       return "Pacman";
    }
}
