package Model.Creator;

import Model.AbstractCase;
import Model.GameStatistics;
import Model.GhostComponent;
import Model.Pacman;
import java.util.List;

/**
 * 
 * @author Laurent
 * 
 * Sauvegarde d'un état de la partie lorsque qu'un Mushroom est mangé par Pacman
 * Toutes les sont des getters pour restaurer cette état si besoin
 */
public class Memento {
    private final Labyrinth state;
    
    Memento(Labyrinth origin){
        this.state = origin;
    }
    
    AbstractCase[][] getOriginMapState(){
        return state.getMapCases();
    }
    
    GameStatistics getOriginStatistics(){
        return state.getStatistics();
    }
    
    Pacman getOriginPacman(){
        return state.getPacman();
    }
    
    List<GhostComponent> getOriginGhosts(){
        return state.getGhostsList();
    }
}
