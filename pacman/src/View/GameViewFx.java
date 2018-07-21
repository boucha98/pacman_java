package View;

import Model.AbstractCase;
import Model.PlayableCase;
import Model.Character;
import Model.Game;
import Model.GameElements;
import Model.GameStatistics;
import Model.GhostComponent;
import Model.Creator.Labyrinth;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 
 * @author Laurent
 * 
 * Classe pour la gestion de la vue. 
 * Basée sur la bibliothèque JavaFx.
 */

public class GameViewFx implements Observer{
    
    private static final GameViewFx VIEWFX = new GameViewFx();
    private static Stage stage = new Stage();
    private static final Canvas CANVAS = new Canvas();
    private static final BorderPane ROOT = new BorderPane();
    private static final Scene SCENE = new Scene(ROOT);
    
    private static final int SIZE = 25; //taille des cases
    private static double height;
    private static double width;
    
    private static final Image PACMAN_NORMAL = new Image("file:images/pacman_normal.png");
    private static final Image PACMAN_SUPER = new Image("file:images/pacman_invincible.png");
    private static final Image PACMAN_DEAD = new Image("file:images/pacman_dead.gif");
    private static final Image PACMAN_VICTORY = new Image("file:images/pacman_victory.jpg");
    private static final Image PAC_GOMMES = new Image("file:images/boule-de-gomme.png");
    private static final Image MUSHROOM = new Image("file:images/champignon.png");
    private static final Image BLINKY = new Image("file:images/blinky.png");
    private static final Image INKY = new Image("file:images/inky.png");
    private static final Image PINKY = new Image("file:images/pinky.png");
    private static final Image CLYDE = new Image("file:images/clyde.png");
    private static final Image GHOST_ZOMBIE = new Image("file:images/enemy_zombie.png");
    private static final Image FRUIT = new Image("file:images/fruit.png");
    private static final Image ANGRY_GHOST = new Image("file:images/evil_ghost.png");
    
    
    public static GameViewFx getInstance(){       
        return VIEWFX;
    }
    
    private GameViewFx(){}
    
    public static void paintStage(Stage primaryStage, Labyrinth labyrinth){
        stage = primaryStage;
        stage.setResizable(false);
        stage.setTitle("Pacman - World");
        prepareCanvas(labyrinth.getMapCases());
        stage.setScene(SCENE);
        drawWelcome();
        stage.show();
        
    }
    
    private static void prepareCanvas(AbstractCase[][] map){
        ROOT.setCenter(CANVAS);
        CANVAS.requestFocus();
        CANVAS.setFocusTraversable(true);
        CANVAS.setHeight((map.length + 1) * SIZE);
        CANVAS.setWidth(map[0].length * SIZE);
        height = CANVAS.getHeight();
        width = CANVAS.getWidth();
    }
    
    //affichage de l'écran d'accueil
    public static void drawWelcome(){
        GraphicsContext gc = CANVAS.getGraphicsContext2D();
        gc.setFill(Color.TOMATO);
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 160, width, height/1.8);
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font("Impact", 30));
        gc.fillText("WELCOME TO PACMAN WORLD !!!", width/6, 60);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Impact", 20));
        gc.fillText("The stars of the game : ", width/3, 140);
        gc.setFill(Color.TOMATO);
        gc.setFont(Font.font("Impact", 15));
        gc.drawImage(PACMAN_NORMAL, width/3.2, 200);
        gc.fillText("Pacman, the gum eater.", width/2.4, 220);
        gc.drawImage(BLINKY, width/3.3, 260);
        gc.fillText("Blinky, the hunter.", width/2.4, 280);
        gc.drawImage(INKY, width/3.3, 320);
        gc.fillText("Inky, lost in the maze.", width/2.4, 340);
        gc.drawImage(PINKY, width/3.3, 380);
        gc.fillText("Pinky, another idiot...", width/2.4, 400);
        gc.drawImage(CLYDE, width/3.3, 440);
        gc.fillText("Clyde, the capricious one.", width/2.4, 460);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Impact", 20));
        gc.fillText("Press any key to begin or ESC to exit game", width/5.5, 580);
        gc.setFont(Font.font("Arial", 11));
        gc.fillText("© Laurent", width/1.1, 640);
    }
    
    //page intermédiaire de changement de niveau
    public static void drawLevelPage(int level){
        GraphicsContext gc = CANVAS.getGraphicsContext2D();
        gc.setFill(Color.YELLOW);
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Impact", 30));
        gc.fillText("LEVEL " + level + " ON 5 BEGIN !", 50, 350);
        if(level > 1){
            gc.fillText("SPEED INCREASE !!!", 50, 400);
        }
        gc.fillText("Press ENTER Key", 50, 500);
    }
    
    //renvoie l'image de l'éléments de jeu à dessiner
    private static Image drawEatable(PlayableCase c){
        Image result = null;
        GameElements toDrawEatable = c.getElementsEatable();
       
        if(toDrawEatable != null){
            switch (toDrawEatable) {
                case PACGOMME:
                    result = PAC_GOMMES;
                    break;
                case FRUIT:
                    result = FRUIT;
                    break;
                case MUSHROOM:
                    result = MUSHROOM;
                    break;
                default:
                    break;
            }
        }
        return result;
    }
    
    //renvoie l'image du personnage à dessiner
    private static Image drawCharacters(Character c){
        switch (c.getGameElement()) {
            case BLINKY:
                return BLINKY;
            case INKY:
                return INKY;
            case PINKY:
                return PINKY;
            case CLYDE:
                return CLYDE;
            case GHOST_POWER:
                return ANGRY_GHOST;
            case PACMAN:
                return PACMAN_NORMAL;
            default:
                return PACMAN_SUPER;
        }
    }
    
    //écran de victoire
    public static void drawPacmanVictorious(int score){
        GraphicsContext gc = CANVAS.getGraphicsContext2D();
        gc.setFill(Color.TOMATO);
        gc.fillRect(0, 0, width, height);
        gc.drawImage(PACMAN_VICTORY, width/3.5, 20);
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font("Impact", 30));
        gc.fillText("YOU WIN, CONGRATULATIONS !", width/5.4, height/2);
        gc.fillText("SCORE: " + score + ".", width/2.7, height/1.5);
        gc.fillText("Press any key to close.", width/4, height/1.2);
    }
    
    //écran de défaite
    public static void drawPacmanDead(){        
        GraphicsContext gc = CANVAS.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        gc.drawImage(PACMAN_DEAD, width/2.2, height/3.5, SIZE * 2, SIZE * 2);
        gc.setStroke(Color.YELLOW);
        gc.setFont(Font.font("Impact", 30));
        gc.strokeText("You lose, try again !", width/3.2, height/2);
        gc.strokeText("Press any key to close.", width/3.6, height/1.5);
    }
    
    private static void drawLabyrinth(GraphicsContext gc, AbstractCase[][] map, List<Character> chList){
        for(int i = 0; i < map.length; ++i){
            //dessiner les cases : mur ou chemin avec l'éléments de jeu dessus (PacGomme, Champignon,...)
            for(int j = 0; j < map[i].length; ++j){
                AbstractCase current = map[i][j];
                if(!current.getPlayable()){
                    gc.setFill(Color.MAROON);
                    gc.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
                    gc.setStroke(Color.BLACK);
                    gc.strokeRect(j * SIZE, i * SIZE, SIZE, SIZE);
                }     
                else{
                    gc.setFill(Color.WHITE);
                    gc.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
                    gc.drawImage(drawEatable((PlayableCase)current), j * SIZE, i * SIZE, SIZE - 1, SIZE - 1);
                }    
            }
            //dessiner les personnages
            for(Iterator<Character> it = chList.iterator(); it.hasNext();){
                Character c = it.next();
                gc.drawImage(drawCharacters(c), c.getY() * SIZE, c.getX() * SIZE, SIZE - 1, SIZE - 1);
                if(c.getGameElement().equals(GameElements.GHOST_POWER)){
                    GhostComponent tmp = (GhostComponent)c;
                    int power = tmp.getPower();
                    gc.setFill(Color.WHITE);
                    gc.setStroke(Color.BLACK);
                    gc.setFont(Font.font(17));
                    gc.strokeText("" + power, c.getY() * (SIZE + 1), c.getX() * (SIZE + 1.5));
                    gc.fillText("" + power, c.getY() * (SIZE + 1), c.getX() * (SIZE + 1.5));
                }
            }
        }
    }
    
    //barre des scores courants en bas de la vue
    private static void drawStatistics(GraphicsContext gc, GameStatistics partyStats, int level){
        String allLines = "Score: " + partyStats.getScore() + "\t - \t Lifes: " + partyStats.getLife() 
                + "\t - \t PacGommes: " + partyStats.getPacgommes() + "\t - \t LEVEL: " + level;
        
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0, CANVAS.getHeight() - SIZE, CANVAS.getHeight() - SIZE, SIZE);
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Impact", 15));
        gc.fillText(allLines, 100, CANVAS.getHeight() - 5);
    }
    
    //lance l'impression du Labyrinth et des scores
    public static void drawScene(Labyrinth lab, int level) {
        AbstractCase[][] map = lab.getMapCases();
        List<Character> characterList = new ArrayList<>();
        characterList.add(lab.getPacman());
        characterList.addAll(lab.getGhostsList());
        
        GraphicsContext gcLab = CANVAS.getGraphicsContext2D();
        
        drawLabyrinth(gcLab, map, characterList);
        
        GameStatistics partyStats = lab.getStatistics();
        drawStatistics(gcLab, partyStats, level);
    }
    
    //mise à jour de la vue en fonction des notifications du modèle
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Game){
            Game g = (Game) o;
            drawScene(g.getLabyrinth(), g.getLevel()); 
        } 
        else if(o instanceof Labyrinth){
            Labyrinth lab = (Labyrinth) o;
            drawScene(lab, 0); 
        } 
    } 
}
