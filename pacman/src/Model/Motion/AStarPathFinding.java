package Model.Motion;

import Model.AbstractCase;
import Model.PlayableCase;
import Model.GhostComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/*
* classe qui définit la recherche du "meilleur" chemin vers l'emplacement de Pacman,
* basée sur l'algorithme A*, variante de l'algorithme Dijkstra.
* Adaptation de l'algorithme original => ne prend pas en compte les déplacements en diagonal (règle du jeu Pacman)
* Utilisation dans les déplacements de Blinky et Clyde sur le tableau d'AbstractCase du Labyrinth.
*/
public class AStarPathFinding  implements AiMotion{
    
    private final int COST_ONE_STEP = 10;//coût d'un déplacement d'une case vers une voisine.
    private final AbstractCase[][] MAP;
    private final AStarNode[][] GRID;
    private PriorityQueue<AStarNode> openList;//utilisation de cette collection pour trier les éléments en fonction de leurs coûts
    private List<AStarNode> closeList;
    
    //grille de AStarNode pour sauvegarder leur coût de déplacement
    public AStarPathFinding(AbstractCase[][] tab){
        MAP = tab;
        GRID = new AStarNode[tab.length][];
        
        for(int i = 0; i < tab.length; ++i){
            GRID[i] = new AStarNode[tab[0].length];
            
            for(int j = 0; j < tab[0].length; ++j){
                GRID[i][j] = new AStarNode(i,j, tab[i][j].getPlayable());
            }
        }   
    }
    
    @Override
    public AbstractCase getNextStep(GhostComponent ghost, AbstractCase pacmanCase) {
        AStarNode tmp = this.getPath(new PlayableCase(ghost.getX(), ghost.getY()), pacmanCase).get(0);
        AbstractCase next = new PlayableCase(tmp.getXCoord(), tmp.getYCoord());
        return next;
    } 
    
    //obtenir le chemin le plus court (algorithme A*)
    private List<AStarNode> getPath(AbstractCase ghostCase, AbstractCase pacmanCase){
        openList = new PriorityQueue<>();
        closeList = new ArrayList<>();
        AStarNode start = new AStarNode(ghostCase.getX(), ghostCase.getY(), true);
        AStarNode target = new AStarNode(pacmanCase.getX(), pacmanCase.getY(), true);
        AStarNode current = start;
        boolean found = false;
        
        openList.add(current);
        
        while(!found){
            current = openList.poll();
            closeList.add(current);
            if(current.equals(target)){
                return orderPath(start, current);
            }
            
            for(AStarNode neighbour : validateAStarNodesNeighbours(current)){
                
                if(!closeList.contains(neighbour)){
                    if(!openList.contains(neighbour)){
                        neighbour.setParent(current);
                        neighbour.setCostH(target);
                        neighbour.setCostG(current.getCostG() + COST_ONE_STEP);
                        openList.add(neighbour);
                    }
                    else{
                        if(neighbour.getCostG() > current.getCostG() + COST_ONE_STEP){
                            neighbour.setParent(current);
                            neighbour.setCostG(current.getCostG() + COST_ONE_STEP);
                        }
                    }
                }
            }
            if(openList.isEmpty()){
                return new ArrayList<>();
            }
        }
        return null;
    }
    
    //retourne une liste ordonnée de AStarNode => chemin d'un point de départ vers un point d'arrivée
    private List<AStarNode> orderPath(AStarNode start, AStarNode target){
        List<AStarNode> path = new LinkedList<>();

        AStarNode current = target;
        do {
            path.add(current);
            current = current.getParent();
        } while (current != start);

        Collections.reverse(path);
        return path;
    }
   
    //vérifier que les AStarNode voisins d'un noeud sont des noeuds jouables et les renvoyer dans une List
    private List<AStarNode>validateAStarNodesNeighbours(AStarNode current){
        List<AStarNode> result = new ArrayList<>();
        AStarNode neighbourUp = (current.getXCoord() - 1 >= 0) ? GRID[current.getXCoord() - 1][current.getYCoord()] : null;
            if(validateAStarNode(neighbourUp)){
                result.add(neighbourUp);
            }
            AStarNode neighbourDown = (current.getXCoord() + 1 < GRID.length) ? GRID[current.getXCoord() + 1][current.getYCoord()] : null;
            if(validateAStarNode(neighbourDown)){
                result.add(neighbourDown);
            }
            AStarNode neighbourLeft = (current.getYCoord() - 1 >= 0) ?  GRID[current.getXCoord()][current.getYCoord() - 1] : null;
            if(validateAStarNode(neighbourLeft)){
                result.add(neighbourLeft);
            }
            AStarNode neighbourRight =  (current.getYCoord() + 1 < GRID[0].length) ? GRID[current.getXCoord()][current.getYCoord() + 1] : null;
            if(validateAStarNode(neighbourRight)){
                result.add(neighbourRight);
            }
        return result;
    }
    
    private boolean validateAStarNode(AStarNode search){
        return search != null && search.getPlayable() == true; 
    }
    
    @Override
    public AbstractCase[][] getGrid(){
        return this.MAP;
    }
}
