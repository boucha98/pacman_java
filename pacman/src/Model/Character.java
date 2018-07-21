package Model;

public abstract class Character{
    
    private final int XORIGIN, YORIGIN;//enregistrer les coordonnées de départ de la partie (pour respawn).
    private int xCoord, yCoord;
   

    public Character(int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
        this.XORIGIN = x;
        this.YORIGIN = y;
    }
    
    public Character(Character origin){
        this.xCoord = origin.xCoord;
        this.yCoord = origin.yCoord;
        this.XORIGIN = origin.XORIGIN;
        this.YORIGIN = origin.YORIGIN;
    }
    
    
    public int getX(){
        return this.xCoord;
    }
    
    public int getY(){
        return this.yCoord;
    }
    
    public int getXorigin(){
        return this.XORIGIN;
    }
    
    public int getYorigin(){
        return this.YORIGIN;
    }
    
    public void setX(int x){
        this.xCoord = x;
    }
    
    public void setY(int y){
        this.yCoord = y;
    }
    
    public abstract GameElements getGameElement();

}
