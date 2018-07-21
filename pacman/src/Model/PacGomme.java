package Model;


public class PacGomme implements Eatable{
    
    private final GameElements GE;
    private final int points = 1;
    private final boolean strong = false;
    private final boolean save = false;
    
    public PacGomme(){
        this.GE = GameElements.PACGOMME;
    }
    
    public PacGomme(PacGomme origin){
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
