package Model;

public class Mushroom implements Eatable{
    
    private final GameElements GE;
    private final int points = 0;
    private final boolean strong = false;
    private final boolean save = true;
    
    public Mushroom(){
        this.GE = GameElements.MUSHROOM;
    }
    
    public Mushroom(Mushroom origin){
        this.GE = origin.GE;
    }
    
    @Override
    public GameElements getGameElement() {
        return this.GE;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public boolean transform() {
        return this.strong;
    }
    
    @Override
    public boolean save(){
        return this.save;
    }
}
