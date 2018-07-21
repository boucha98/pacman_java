package Model;

public class Fruit implements Eatable{
    
    private final GameElements GE;
    private final int points = 0;
    private final boolean strong = true;
    private final boolean save = false;
    
    public Fruit(){
        this.GE = GameElements.FRUIT;
    }
    
    public Fruit(Fruit origin){
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
