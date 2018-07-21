package Controller;

import Model.Game;
import View.GameViewFx;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author Laurent
 * 
 * Point d'entrée du jeu.
 * Création du modèle (classe Game) en tant qu'observable et de la vue (GameViewFx) en tant qu'observateur
 */

public class GameControllerFx extends Application{
    
    private final Game GAME = new Game();
    private Timeline ghostsTimeline;

    public GameControllerFx() {
        GAME.addObserver(GameViewFx.getInstance());
    }  

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameViewFx.paintStage(primaryStage, GAME.getLabyrinth());
        primaryStage.show();
        startGame(primaryStage);
    }
    
    private void startGame(Stage primaryStage){
        primaryStage.getScene().setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode().equals(KeyCode.ESCAPE)){
                Platform.exit();
            }
            else{
                nextLevel(primaryStage);
            }
        });
    }
    
    private void nextLevel(Stage primaryStage){
        //System.out.println("SPEED = " + GAME.getSpeed() + " - LEVEL = " + GAME.getLevel());
        GameViewFx.drawLevelPage(GAME.getLevel());
        primaryStage.getScene().setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                this.actions(primaryStage);
            }
        });
    }
    
    //dessine la scène JavaFx et lance les actions pour Pacman d'un côté et pour les fantômes de l'autre
    private void actions(Stage primaryStage){
        GameViewFx.drawScene(GAME.getLabyrinth(), GAME.getLevel());
        pacmanActions(primaryStage);
        ghostsActions(primaryStage);
    }
    
    //prise en charge des commandes de l'utilisateur (flèches directionnelles)
    private void pacmanActions(Stage primaryStage){
        primaryStage.getScene().setOnKeyPressed((KeyEvent event) -> {
            KeyCode kc = event.getCode();
            GAME.pacmanMover(kc);
            endGameOrNextLevel(primaryStage);
        });
    }
    
    //gestion des déplacements des 4 Ghosts via une TimeLine
    private void ghostsActions(Stage primaryStage){
        ghostsTimeline = new Timeline(new KeyFrame(
                Duration.millis(GAME.getSpeed()),
                ae -> periodicAction(primaryStage))
        );
        ghostsTimeline.setCycleCount(Animation.INDEFINITE);
        ghostsTimeline.play();
    }
   
    //actions pour chaque Ghost à chaque KeyFrame de la TimeLine de ghostsActions()
    private void periodicAction(Stage primaryStage) {
        GAME.ghostsMover();
        endGameOrNextLevel(primaryStage);
    }
    
    //test si fin de partie et affichage d'une nouvelle Scene pour quitter
    private void endGameOrNextLevel(Stage stage){
        if(GAME.loseGame()){
            ghostsTimeline.stop();
            GameViewFx.drawPacmanDead();
            showEnd(stage);
        }
        else if(GAME.winLevel()){
            ghostsTimeline.stop();
            GAME.setNextLabyrinth();
            this.nextLevel(stage);
        }
        else if(GAME.winGame()){
            ghostsTimeline.stop();
            GameViewFx.drawPacmanVictorious(GAME.getTotalScore());
            showEnd(stage); 
        }
    }
    
    private void showEnd(Stage stage){
        stage.show();
        stage.getScene().setOnKeyPressed((KeyEvent event2) -> {
            if(event2.getCode()!= null){//dès qu'on presse une touche => fermeture de l'application
                Platform.exit();
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
