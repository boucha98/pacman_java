package Model;

import Model.Creator.Labyrinth;
import java.util.Observable;
import javafx.scene.input.KeyCode;

//point d'entrée du modèle : génération des nouveaux labyrinth pendant 5 manches maximum (avec vitesse de jeu croissante)
//le nombre de manches est fixé avec le nombre de valeurs du tableau speed.
public class Game extends Observable{
    
    private Labyrinth currentMaze;
    private static int level = 0;
    private static int totalScore = 0;
    private final int[] speed = {700,600,500,400,300}; //vitesse qui sera utilisée par la timeline du contrôleur. Le nombre d'éléments dans cette liste détermine le nombre de niveau dans la partie.
    
    public Game(){
        currentMaze = new Labyrinth();
    }
    
    public int getLevel(){
        return this.level + 1;
    }
    
    public int getTotalScore(){
        return this.totalScore;
    }
    
    public int getSpeed(){
        return this.speed[level];
    }
    
    public Labyrinth getLabyrinth(){
        return this.currentMaze;
    }
    
    //changement de niveau
    public void setNextLabyrinth(){
        ++level;
        totalScore += currentMaze.getStatistics().getScore();
        currentMaze = new Labyrinth();
    }
    //3 méthodes ci-dessous: vérifier l'état de la partie
    public boolean loseGame(){
        return currentMaze.loseGame();
    }
    
    public boolean winGame(){
        return currentMaze.winGame() && level >= speed.length - 1;
    }
    
    public boolean winLevel(){
        return currentMaze.winGame() && level < speed.length - 1;
    }
    
    public void pacmanMover(KeyCode kc){
        AbstractCase nextStep = new Wall(0,0);//initialisé nextStep avec une case non jouable
        switch(kc){
            case UP: nextStep = caseUp(currentMaze.getPacman());
                break;
            case DOWN: nextStep = caseDown(currentMaze.getPacman());
                break;
            case LEFT: nextStep = caseLeft(currentMaze.getPacman());
                break;
            case RIGHT: nextStep = caseRight(currentMaze.getPacman());
                break;
            default: break;
        }
        if(nextStep.getPlayable()){//si la case est un mur (!nextStep.getPlayable()) => on ne bouge pas
            currentMaze.pacmanToNextCase((PlayableCase)nextStep);
        }
        notifie();
    }
    
    public void ghostsMover(){
        currentMaze.moveGhosts();
        notifie();
    }
    
    //notifier la vue de tout changement dans l'état du modèle
    public void notifie(){
        setChanged();
        notifyObservers(currentMaze);
    }
    
    
    private AbstractCase caseUp(Character ch){
        AbstractCase temp = currentMaze.getCase(currentMaze.getCharacterCase(ch).getX() - 1,
                currentMaze.getCharacterCase(ch).getY());
        return temp;
    }    
    
    private AbstractCase caseDown(Character ch){
        AbstractCase temp = currentMaze.getCase(currentMaze.getCharacterCase(ch).getX() + 1,
                currentMaze.getCharacterCase(ch).getY());
        return temp;
    }
    
    private AbstractCase caseRight(Character ch){
        AbstractCase temp = currentMaze.getCase(currentMaze.getCharacterCase(ch).getX(),
                currentMaze.getCharacterCase(ch).getY() + 1);
        return temp;
    }
    
    private AbstractCase caseLeft(Character ch){
        AbstractCase temp = currentMaze.getCase(currentMaze.getCharacterCase(ch).getX(), 
                currentMaze.getCharacterCase(ch).getY() - 1);
        return temp;
    }
    
}
