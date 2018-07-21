package Model;

import Model.Creator.Memento;
import java.util.Stack;

public class CareTaker {
    
    private final Stack<Memento> mementoList = new Stack<>();
    
    public Memento getMemento(int index){
        for(int i = 0; i < index - 1; ++i){
            //index correspond à la puissance du GhostComponent recontré par Pacman => en plus de supprimer
            //et retourner l'état qui va être restauré, on supprime les Memento qui précèdent en haut de la pile
            this.mementoList.pop();
        }
        return this.mementoList.pop();
    }
    
    public void addMemento(Memento m){
        this.mementoList.push(m);
    }
    
    public int size(){
        return this.mementoList.size();
    }
}
