package Model;

public interface Eatable{// Elements mangeables du jeu

    public abstract GameElements getGameElement();
    public abstract int getPoints();//renvoie la valeur en points de l'élément mangeable
    public abstract boolean transform();//indique si l'élément mangeable transforme Pacman en SuperPacman
    public abstract boolean save();
}
