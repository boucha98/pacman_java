package Model;

public abstract class AbstractCase {
    private final int x, y;
    protected boolean playable;
    
    public AbstractCase(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public AbstractCase(AbstractCase origin){
        this.x = origin.x;
        this.y = origin.y;
        this.playable = origin.playable;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public abstract boolean getPlayable();
}
