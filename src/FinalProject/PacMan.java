package FinalProject;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

import java.io.Serializable;

public class PacMan extends MazeMovableObjects implements Serializable {
    int life;
    Scene pListenToScene;
    static String moveTo = "";
    boolean isMoving = false;
    int points = 0;
    Timeline timeline;
    AnimationTimer at;
    long previousTime = 0;

    public PacMan(Canvas mCanvas, Maze maze, Scene mScene) {
        super(mCanvas,maze);
        this.life = 2;
        addlistener(mScene);
    }



    @Override
    protected void drawObject() {
        graphicsContext.save();
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillOval(jPos*20,iPos*20,maze.actualMaze.length,maze.actualMaze.length);
        System.out.println("pacman position: " + iPos + " " + jPos);
        graphicsContext.restore();
    }

    private void move(){

            switch (moveTo) {
                case "up":
                    if (iPos - 1 >= 0 && !maze.actualMaze[iPos - 1][jPos].getWall()) {

                        at = new AnimationTimer() {
                            @Override
                            public void handle(long now) {
                                onTimer(now);
                            }
                        };
                        at.start();
                        updatePos(iPos - 1, jPos);
                    } else {
                        isMoving = false;
                        System.out.println("stuck");
                    }
                    break;

                case "down":
                    if (iPos + 1 <= 19 && !maze.actualMaze[iPos + 1][jPos].getWall()) {
                        updatePos(iPos + 1, jPos);
                    } else {
                        isMoving = false;
                        System.out.println("stuck");
                    }
                    break;

                case "right":
                    if (jPos + 1 <= 19 && !maze.actualMaze[iPos][jPos + 1].getWall()) {
                        updatePos(iPos, jPos + 1);
                    } else {
                        isMoving = false;
                        System.out.println("stuck");
                    }
                    break;

                case "left":
                    if (jPos - 1 >= 0 && !maze.actualMaze[iPos][jPos - 1].getWall()) {
                        updatePos(iPos, jPos - 1);
                    } else {
                        isMoving = false;
                        System.out.println("stuck");
                    }
                    break;
            }
        }

    private void onTimer(long now) {


    }


    private void addlistener(Scene scene){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                isMoving = true;
                switch (event.getCode()){
                    case UP:
                        moveTo = "up";
                        move();
                        break;
                    case DOWN:
                        moveTo = "down";
                        move();
                        break;
                    case LEFT:
                        moveTo = "left";
                        move();
                        break;
                    case RIGHT:
                        moveTo = "right";
                        move();
                        break;

                        default:
                            System.out.println("Invalid Key Press!");
                            break;
                }
            }
        });
    }
    protected void removeObject(int x, int y){
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(x,y,maze.actualMaze.length,maze.actualMaze.length);
    }

    private void updatePos(int update_i, int update_j){
        removeObject(jPos * 20, iPos * 20);
        this.iPos = update_i;
        this.jPos = update_j;
        setX();
        setY();
        drawObject();

    }

    private void movingMouth(){
//        graphicsContext.setFill(Color.YELLOW);
//        graphicsContext.fillArc(0,0,150,150,30,300, );

    }
//    private boolean validDirection(String s){
//        switch (s){
//            case "up":
//        }
//    }


    public void setLife(int life) {
        this.life = life;
    }


    public void setMoving(boolean moving) {
        isMoving = moving;
    }
}
