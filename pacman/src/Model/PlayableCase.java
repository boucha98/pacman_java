package Model;


public class PlayableCase extends AbstractCase{
    
    private Eatable element;
    
    public PlayableCase(int x, int y){
        super(x, y);
        this.playable = true;
        this.element = null;
    }
    
    public PlayableCase(int x, int y, Eatable element){
        this(x, y);
        this.playable = true;
        this.element = element;
    }
    
    public PlayableCase(PlayableCase origin){
        super(origin);
        this.element = origin.element;
    }
    
    public Eatable getEatable(){
        return this.element;
    }
    
    public void setEatable(Eatable p){
        this.element = p;
    }

    @Override
    public boolean getPlayable() {
        return this.playable;
    }

    public GameElements getElementsEatable() {
        if(this.getEatable() != null)
            return this.getEatable().getGameElement();
        return null;
    }
}
