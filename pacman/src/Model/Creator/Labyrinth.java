package Model.Creator;

import Model.AbstractCase;
import Model.Blinky;
import Model.PlayableCase;
import Model.Character;
import Model.Clyde;
import Model.Eatable;
import Model.Fruit;
import Model.GameElements;
import Model.GameStatistics;
import Model.Ghost;
import Model.GhostComponent;
import Model.GhostGroup;
import Model.Level;
import Model.Mushroom;
import Model.PacGomme;
import Model.Pacman;
import Model.Wall;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * 
 * @author Laurent
 * 
 * Gestion de la grille de jeu pour chaque niveau
 */

public class Labyrinth extends Observable {

    private AbstractCase[][] CASEMAP;
    private Pacman pacman;
    private Blinky blinky;
    private Clyde clyde;
    private Ghost pinky, inky;
    private List<GhostComponent> ghostsList = new ArrayList<>();
    //private List<AbstractCase> ghostRandomSpawn = new ArrayList<>();// enregistrer les cases éligibles en vue du random de placement des 4 Ghosts
    private final List<AbstractCase> GHOSTSPAWN = new ArrayList<>();// enregistrer 4 cases de la zone centrale pour placer les Ghost mangés
    private GameStatistics partyStats;// enregistrer les statistiques de la partie
    

    public Labyrinth() {
        partyStats = new GameStatistics();
        this.CASEMAP = initializeLab();
        putGhosts();
        initCareTaker();
    }
    
    public Labyrinth(Labyrinth origin){
        this.CASEMAP = copyCaseMap(origin.getMapCases());
        this.pacman = new Pacman(origin.pacman);
        this.ghostsList = copyGhostComponentList(origin.ghostsList);
        this.partyStats = new GameStatistics(origin.partyStats);
    }
    
    private void initCareTaker(){
        pacman.initCareTaker(this);
    }
    
    //copie profonde de la ghostsList pour la sauvegarde de l'état du Labyrinth dans un Memento
    private List<GhostComponent> copyGhostComponentList(List<GhostComponent> origin){
        List<GhostComponent> copyList = new ArrayList<>();
        for(GhostComponent gh : origin){
            copyList.add(gh.copy());
        }
        return copyList;
    }

    //initialisation du labyrinth sous la forme d'un tableau à 2 dimensions de PlayableCase
    private AbstractCase[][] initializeLab() {
        Level level = new Level();
        AbstractCase[][] result = new AbstractCase[level.getLevel().length][];

        final int NOTHING = 0;
        final int WALL = 1;
        final int PACMAN = 2;
        final int MUSHROOM = 3;
        final int PACGOMME = 4;
        final int FRUIT = 5;

        for (int i = 0; i < level.getLevel().length; ++i) {
            result[i] = new AbstractCase[level.getLevel()[0].length];

            for (int j = 0; j < level.getLevel()[0].length; ++j) {
                AbstractCase current;

                switch (level.getLevel()[i][j]) {
                    case NOTHING:
                        current = new PlayableCase(i, j);
                        GHOSTSPAWN.add(current);
                        break;
                    case WALL:
                        current = new Wall(i, j);
                        break;
                    case PACMAN:
                        current = new PlayableCase(i, j);
                        pacman = new Pacman(i, j);
                        break;
                    case MUSHROOM:
                        current = new PlayableCase(i, j, new Mushroom());
                        break;
                    case PACGOMME:
                        current = new PlayableCase(i, j, new PacGomme());
                        //ghostRandomSpawn.add(current);
                        partyStats.incrementPacgommes();
                        break;
                    case FRUIT:
                        current = new PlayableCase(i, j, new Fruit());
                        break;
                    default: //si on utilise un autre Stage et que l'int rencontré ne correspond pas à ceux utilisés
                        throw new RuntimeException("None recognize game element : " + level.getLevel()[i][j]);
                }
                result[i][j] = current;
            }
        }
        
        return result;
    }
    
    //créer une copie profonde d'un tableau à 2 dimensions d'AbstractCase pour la création d'un Memento
    private AbstractCase[][] copyCaseMap(AbstractCase[][] caseMap){
        
        AbstractCase[][] result = new AbstractCase[caseMap.length][];
        
        for(int i = 0; i < caseMap.length; ++i){
            result[i] = new AbstractCase[caseMap[0].length];
            
            for(int j = 0; j < caseMap[0].length; ++j){
                if(!caseMap[i][j].getPlayable())
                    result[i][j] = new Wall((Wall)caseMap[i][j]);
                else
                    result[i][j] = new PlayableCase((PlayableCase)caseMap[i][j]);
            }
        }
        return result;
    }
    

    // Placer 4 instances de Ghost sur le tableau de PlayableCase[][]
    private void putGhosts() {
        PlayableCase spawn = (PlayableCase) GHOSTSPAWN.remove(0);
        blinky = new Blinky(spawn.getX(), spawn.getY(), CASEMAP, GameElements.BLINKY);
        ghostsList.add(blinky);
        spawn = (PlayableCase) GHOSTSPAWN.remove(0);
        pinky = new Ghost(spawn.getX(), spawn.getY(), CASEMAP, GameElements.PINKY);
        ghostsList.add(pinky);
        spawn = (PlayableCase) GHOSTSPAWN.remove(0);
        inky = new Ghost(spawn.getX(), spawn.getY(), CASEMAP, GameElements.INKY);
        ghostsList.add(inky);
        spawn = (PlayableCase) GHOSTSPAWN.remove(0);
        clyde = new Clyde(spawn.getX(), spawn.getY(), CASEMAP, GameElements.CLYDE);
        ghostsList.add(clyde);
        //ci-dessous : pour tester regroupement des Ghosts
//        blinky.setDirection(Direction.RIGHT);
//        blinky.setStringName("BLINKY");
//        pinky.setDirection(Direction.RIGHT);
//        pinky.setStringName("PINKY");
//        inky.setDirection(Direction.RIGHT);
//        inky.setStringName("INKY");
//        clyde.setDirection(Direction.LEFT);
        
        partyStats.setGhosts(4);
    }

    // Placer aléatoirement 4 instances de Ghost sur le tableau de PlayableCase[][]
//    private void randomGhost(){
//        Random gen = new Random();
//        int max = ghostRandomSpawn.size();
//        for(int i = 0; i < 4; ++i){
//            int indice = gen.nextInt(max-i);
//            AbstractCase spawn=ghostRandomSpawn.remove(indice);
//            spawn.setElement(new Ghost(spawn.getX(), spawn.getY()));
//        }
//    }
    
    public AbstractCase[][] getMapCases() {
        return CASEMAP;
    }

    public GameStatistics getStatistics() {
        return this.partyStats;
    }

    public boolean winGame() {
        return this.partyStats.getGhosts() == 0 || this.partyStats.getPacgommes() == 0;
    }

    public boolean loseGame() {
        return this.partyStats.getLife() <= 0;
    }

    public AbstractCase getCase(int x, int y) {
        return this.CASEMAP[x][y];
    }

    public AbstractCase getCharacterCase(Character ch) {
        return this.getCase(ch.getX(), ch.getY());
    }

    public Pacman getPacman() {
        return this.pacman;
    }

    public List getGhostsList() {
        return this.ghostsList;
    }
    
    //gestion de la rencontre entre pacman et un ghostComponent
    private boolean enemies(GhostComponent current){
        if (current.getX() == pacman.getX() && current.getY() == pacman.getY()) {
            pacman.clash(this, current);
            return true;
        }
        return false;
    }

    //vérifier la position Pacman par rapport à celles de tous les GhostComponent actifs.
    private void encounter() {
        boolean result = false;
        for(int i = 0; i < ghostsList.size() && !result; ++i) {
            GhostComponent current = ghostsList.get(i);
            result = enemies(current);
        }
    }
    
    //Gestion l'élément Eatable présent ou non sur la PlayableCase où se trouve Pacman
    private void checkEatables(PlayableCase next){
        PlayableCase pacmanCurrent = (PlayableCase) getCharacterCase(pacman);
        Eatable element = pacmanCurrent.getEatable();
        if (element != null) {//gestion des éléments mangeables pour Pacman
            next.setEatable(null);//le mangeable est consommé => l'attribut passe à null dans la case
            pacman.eat(element, this);
        }
    }
    
    //déplacer pacman vers la case suivante puis gérer tous les évènements de rencontre
    public void pacmanToNextCase(PlayableCase next) {
        pacman.setX(next.getX());
        pacman.setY(next.getY());
        encounter();
        checkEatables(next);
        notifie();
    }
    
    //déplacer tous les GhostComponent présents dans la ghostsList
    //vérifier s'il y a des créations de GhostGroup
    //vérifier si Pacman rencontre un des GhostComponent
    public void moveGhosts(){
        //copier la liste originale pour ne pas rencontrer un problème de concurrence (modifications pendant le parcours de la liste)
        List<GhostComponent> copyGhostList = new LinkedList<>(ghostsList);
        //System.out.println(ghostsList + ", size = " + ghostsList.size());
        while(!copyGhostList.isEmpty()){
            //chaque GhostComponent est retiré de la liste copie pour traitement, puis supprimé dans la liste originale si besoin
            GhostComponent current = copyGhostList.remove(0);            
            current.move(new PlayableCase(pacman.getX(), pacman.getY()));
            if(enemies(current) && !pacman.isSuperPacman()){//si on ne coupe pas cette boucle : 2 ghostsList vont coexister
                break;
            }
            GhostGroup toInsert = ghostFusion(copyGhostList, current);
            if(toInsert != null){//on insère le nouveau GhostGroup
                ghostsList.add(toInsert);
            }
        }
        notifie();
    }
 
    //lancer la création d'un GhostGroup (si deux GhostComponent ont la même position)
    private GhostGroup ghostFusion(List<GhostComponent> copyGhostList, GhostComponent current){
        GhostGroup toInsert = null;
        List<GhostComponent> allTmp = new ArrayList<>();
        allTmp.addAll(ghostsList);
        allTmp.addAll(copyGhostList);
        for(int i = 0; i < allTmp.size()  && toInsert == null; ++i){
            GhostComponent other = allTmp.get(i);
            if(!current.equals(other) && current.getX() == other.getX() && current.getY() == other.getY()){
                copyGhostList.remove(other);
                ghostsList.remove(current);
                ghostsList.remove(other);//au cas où il avait déjà été traité dans la liste dans moveGhosts()
                toInsert = new GhostGroup(current.getX(), current.getY(), CASEMAP, ghostsList, current, other);
            }  
        }
        return toInsert;
    }
    
    //dans un try...catch car stopTimeline() déclenche une UnsupportedOperationException() pour les Ghost simples
    private void stopTimelineException(GhostComponent gc){
        try{
            gc.stopTimeline();
        }catch(UnsupportedOperationException e){
            System.out.println(e.getMessage() + ": stopTimeline");
        }
    }
    
    //idem pour startTimeline()
    private void startTimelineException(GhostComponent gc){
        try{
            gc.startTimeline(ghostsList);
        }catch(UnsupportedOperationException e){
            System.out.println(e.getMessage() + ": startTimeline");
        }
    }

    private void notifie() {
        setChanged();
        notifyObservers(this);
    }
    
    public void incrementLife(){
        this.partyStats.incrementLife();
    }
    
    public void incrementScore(int points){
        this.partyStats.incrementScore(points);
    }
    
    public void decrementPacgommes(int nb){
        this.partyStats.decrementPacgommes(nb);
    }
    
    public void setLife(int nb){
        this.partyStats.setLife(nb);
    }
    
    public Memento saveState(){
        this.partyStats.incrementLife();
        return new Memento(new Labyrinth(this));
    }
    
    //restauration de la ghostsList à un état antérieur : 
    // 1 - on stoppe les Timeline de tous les GhostComponent avant modification
    // 2 - on restaure l'ancienne liste
    // 3 - on démarre toutes les Timeline des GhostComponent
    private void restoreOldGhostsList(Memento restore){
        for(GhostComponent g : ghostsList)
            this.stopTimelineException(g);
        this.ghostsList.clear();
        this.ghostsList.addAll(restore.getOriginGhosts());
        for(GhostComponent g : ghostsList)
            this.startTimelineException(g);
    }
    
    //utilisation d'un Memento pour restaurer un ancien état de la partie
    public void restoreOldState(Memento oldState){
        this.CASEMAP = oldState.getOriginMapState();
        this.partyStats = oldState.getOriginStatistics();
        this.pacman = oldState.getOriginPacman();
        this.pacman.restartTimeline();
        restoreOldGhostsList(oldState);   
    }
       
}
