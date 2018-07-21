package Model;

public class GameStatistics {
    private int score, life, pacgommes, ghosts;
    
    public GameStatistics(){
        this.life = 0;
        this.score = 0;
        this.pacgommes = 0;
    }
    
    public GameStatistics(GameStatistics origin){
        this.life = origin.life;
        this.score = origin.score;
        this.pacgommes = origin.pacgommes;
        this.ghosts = origin.ghosts;
    }
    
    public int getLife(){
        return this.life;
    }
    
    public void decrementLife(int nbLifes){
        this.life -= nbLifes;
    }
    
    public void incrementLife(){
        this.life++;
    }
    
    public void setLife(int count){
        this.life = count;
    }
    
    
    public int getScore(){
        return this.score;
    }
    
    public void incrementScore(int points){
        this.score += points;
    }
    
    public int getPacgommes(){
        return this.pacgommes;
    }
    
    public void incrementPacgommes(){
        this.pacgommes++;
    }
    
    public void decrementPacgommes(int nb){
        this.pacgommes -= nb;
    }
    
    public int getGhosts(){
        return this.ghosts;
    }
    
    public void setGhosts(int nbGhosts){
        this.ghosts = nbGhosts;
    }
    
    public void decrementGhosts(){
        this.ghosts--;
    }
}
