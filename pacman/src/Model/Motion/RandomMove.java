package Model.Motion;

import Controller.Direction;
import Model.AbstractCase;
import Model.GhostComponent;
import Model.Wall;
import java.util.ArrayList;

//classe qui définit les déplacements "aléatoires" utilisés par défaut pour tous les GhostComponent

public class RandomMove implements AiMotion{

    private final AbstractCase[][] GRID;

    public RandomMove(AbstractCase[][] map) {
        this.GRID = map;
    }
    
    // recherche du déplacement suivant, en fonction de la Direction du GhostComponent
    // => si recontre d'une AbstractCase non jouable, utilisation d'un Random sur une liste des Case voisines valides
    @Override
    public AbstractCase getNextStep(GhostComponent ghost, AbstractCase pacmanLocation) {
        AbstractCase result = new Wall(0, 0);
        Direction d = ghost.getDirection();
        while (result.getPlayable() == false) {
            switch (d) {
                case UP:
                    result = caseUp(ghost.getX(), ghost.getY());
                    break;
                case DOWN:
                    result = caseDown(ghost.getX(), ghost.getY());
                    break;
                case LEFT:
                    result = caseLeft(ghost.getX(), ghost.getY());
                    break;
                case RIGHT:
                    result = caseRight(ghost.getX(), ghost.getY());
                    break;
            }

            if(! result.getPlayable()){
                d = Direction.randomPlayableDirection(playableDirections(ghost.getX(), ghost.getY()));
            }
        }
        ghost.setDirection(d);
        ghost.addDirections(playableDirections(result.getX(), result.getY()));
        return result;
    }

    //pour les déplacements des Ghost : isoler les Direction jouables (non Wall) pour le random de Direction
    private ArrayList<Direction> playableDirections(int posX, int posY) {
        ArrayList<Direction> playables = new ArrayList<>();
        AbstractCase up = caseUp(posX, posY);
        if (up.getPlayable()) {
            playables.add(Direction.UP);
        }
        AbstractCase down = caseDown(posX, posY);
        if (down.getPlayable()) {
            playables.add(Direction.DOWN);
        }
        AbstractCase right = caseRight(posX, posY);
        if (right.getPlayable()) {
            playables.add(Direction.RIGHT);
        }
        AbstractCase left = caseLeft(posX, posY);
        if (left.getPlayable()) {
            playables.add(Direction.LEFT);
        }

        return playables;
    }

    private AbstractCase caseUp(int posX, int posY) {
        return GRID[posX - 1][posY];
    }

    private AbstractCase caseDown(int posX, int posY) {
        return GRID[posX + 1][posY];
    }

    private AbstractCase caseRight(int posX, int posY) {
        return GRID[posX][posY + 1];
    }

    private AbstractCase caseLeft(int posX, int posY) {
        return GRID[posX][posY - 1];
    }

    @Override
    public AbstractCase[][] getGrid() {
        return this.GRID;
    }

}
