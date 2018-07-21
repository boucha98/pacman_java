package Model.Motion;

public class AStarNode  implements Comparable<AStarNode>{
    /*
    * le coutG (le cout pour aller du point de départ au noeud courant)
    * le coutH (Heuristique : le cout pour aller du noeud courant au noeud cible)
    * le coutF (somme des coûts précédents)
    * playable : le noeud est-il traversable?
    * le parent du noeud
    */
    private final int xCoord, yCoord;
    private int costG = 0, costH;
    private final boolean playable;
    private AStarNode parent;
    
    AStarNode(int x, int y, boolean playable){
        this.xCoord = x;
        this.yCoord = y;
        this.playable = playable;
    }
    
    public int getXCoord(){
        return this.xCoord;
    }
    
    public int getYCoord(){
        return this.yCoord;
    }
    
    public boolean getPlayable(){
        return this.playable;
    }
    
    public void setCostG(int costG){
        this.costG = costG;
    }
    
    public int getCostG(){
        return this.costG;
    }
    
    public void setCostH(AStarNode target){
        this.costH = Math.abs(this.getXCoord() - target.getXCoord()) + Math.abs(this.getYCoord() - target.getYCoord());
    }
    
    public int getCostH(){
        return this.costH;
    }
    
    public int getCostF(){
        return this.costG + this.costH;
    }
    
    public AStarNode getParent(){
        return this.parent;
    }
    
    public void setParent(AStarNode parent){
        this.parent = parent;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof AStarNode){
            AStarNode other = (AStarNode) o;
            if(other.xCoord == this.xCoord && other.yCoord == this.yCoord){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.xCoord;
        hash = 23 * hash + this.yCoord;
        return hash;
    }
    
    @Override
    public int compareTo(AStarNode other){
        return this.getCostF() - other.getCostF();
    }
    
    @Override
    public String toString(){
        return "[" + this.xCoord + " ; " + this.yCoord + "]";
    }
}
