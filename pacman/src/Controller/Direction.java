package Controller;

import java.util.ArrayList;
import java.util.Random;

public enum Direction {
    UP('Z'), DOWN('S'), LEFT('Q'), RIGHT('D'), QUIT('P');
    
    private final char dir;

    Direction(char c){
        this.dir = c;
    }
    
    public static boolean isDirection(char keyb){
        for(Direction d : Direction.values()){
            if(d.dir == keyb) return true;
        }
        return false;
    }
    
    public static Direction getByChar(char keyb){
        for(Direction d : Direction.values()){
            if(d.dir == keyb) return d;
        }
        return null;
    }
    
    private static final Random rand = new Random();
    
    //renvoyer une Direction aléatoire
    public static Direction randomDirection() {
        return Direction.values()[rand.nextInt(Direction.values().length - 1)];
    }
    
    //renvoyer une Direction aléatoire depuis un ArrayList de Direction
    public static Direction randomPlayableDirection(ArrayList<Direction> playables) {
        int max = playables.size();
        return playables.get(rand.nextInt(max));
    }
}


