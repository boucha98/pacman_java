package Model.Motion;

import Model.AbstractCase;
import Model.GhostComponent;

public interface AiMotion {
    
    //interface pour regrouper les classes de recherches de chemin (pour le moment RandomMove et AStarPathFinding)
    
    //obtenir la prochaine case de dépacement du GhostComponent
    public AbstractCase getNextStep(GhostComponent ghost, AbstractCase pacmanCase);
    
    //obtenir la grille de jeu
    public AbstractCase[][] getGrid();
}
