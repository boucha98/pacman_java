package Model;

public class Wall extends AbstractCase{
   
    public Wall(int x, int y) {
        super(x, y);
        this.playable = false;
    }
    
    public Wall(Wall origin){
        super(origin);
    }
    
    @Override
    public boolean getPlayable() {
        return this.playable;
    }
}
