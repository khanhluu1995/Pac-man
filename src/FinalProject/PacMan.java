package FinalProject;

import com.sun.xml.internal.ws.api.client.WSPortInfo;
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
import java.util.Random;

public class PacMan extends MazeMovableObjects implements Serializable {
    int life;
    static String moveTo = "";
    boolean isMoving = false;
    int points = 0;
    AnimationTimer at;
    int destination = 0;


    public PacMan(Canvas mCanvas, Maze maze, Scene mScene) {
        super(mCanvas,maze);
        this.life = 2;
        addlistener(mScene);
        initialPosition();
    }


    @Override
    protected void initialPosition() {
        boolean isGoodPos = false;
        while(!isGoodPos) {
            Random random = new Random();
            iPos = 6;
            jPos = 14;

            if(!maze.actualMaze[iPos][jPos].getWall()){
                drawObject();
                isGoodPos = true;
            }
        }
    }

    @Override
    protected void drawObject() {
        setY(iPos);
        setX(jPos);
        graphicsContext.save();
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillOval(x,y,maze.actualMaze.length,maze.actualMaze.length);
        System.out.println("pacman position: " + iPos + ", " + jPos);
        System.out.println("pacman position in pixels: " + x + ", " + y);
        graphicsContext.restore();
    }

    private void move(){

        isMoving = true;
        at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                switch (moveTo) {
                    case "up":
                        if (iPos - 1 >= 0 && !maze.actualMaze[iPos - 1][jPos].getWall()) {

                            destination = converter(iPos-1);
                            double distance = Math.abs(y-destination);
                            if(distance!=0){
                                removeObject(x,y);
                                y--;
                                pacManMovement(x,y);
//                              System.out.println(distance);
                            }

                            else if(distance == 0){

                                isMoving = false;
                                System.out.println("pacman position move in pixels: " + x + ", " + y);
                                updatePos(iPos - 1, jPos);
                            }

                        } else {
                            System.out.println("stuck");
                            at.stop();
                        }
                        break;

                    case "down":
                        if (iPos + 1 <= 19 && !maze.actualMaze[iPos + 1][jPos].getWall()) {
                            destination = converter(iPos+1);
                            double distance = Math.abs(y-destination);
                            if(distance != 0){
                                removeObject(x,y);
                                y++;
                                pacManMovement(x,y);
//                                System.out.println(distance);
                            }

                            else if(distance == 0){
                                isMoving = false;
                                updatePos(iPos + 1, jPos);
                            }
                        } else {
                            System.out.println("stuck");
                            at.stop();
                        }
                        break;

                    case "right":
                        if (jPos + 1 <= 19 && !maze.actualMaze[iPos][jPos + 1].getWall()) {
                            destination = converter(jPos+1);
                            double distance = Math.abs(x-destination);
                            if(distance != 0){
                                removeObject(x,y);
                                x++;
                                pacManMovement(x,y);
//                                System.out.println(distance);
                            }

                            else if(distance == 0){
                                isMoving = false;
                                updatePos(iPos, jPos+ 1);
                            }
                        } else {
                            System.out.println("stuck");
                            at.stop();
                        }
                        break;

                    case "left":
                        if (jPos - 1 >= 0 && !maze.actualMaze[iPos][jPos - 1].getWall()) {
                            destination = converter(jPos-1);
                            double distance = Math.abs(x-destination);
                            if(distance != 0){
                                removeObject(x,y);
                                x--;
                                pacManMovement(x,y);
//                                System.out.println(distance);
                            }

                            else if(distance == 0){
                                isMoving = false;
                                updatePos(iPos, jPos - 1);
                            }
                        } else {
                            System.out.println("stuck");
                            at.stop();
                        }
                        break;
                }
            }
        };
        at.start();
    }
    private void addlistener(Scene scene){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case UP:
                        if(!moveTo.equals("up") && !isMoving) {
                            moveTo = "up";
                            move();
                        }
                        else {
                            at.stop();
                            updatePos(iPos-1,jPos);
                            isMoving=false;
                        }
                        break;
                    case DOWN:
                        if(!moveTo.equals("down")&& !isMoving) {
                            moveTo = "down";
                            move();
                        }
                        else {
                            at.stop();
                            updatePos(iPos+1,jPos);
                            isMoving=false;

                        }
                        break;
                    case LEFT:
                        if(!moveTo.equals("left")&& !isMoving) {
                            moveTo = "left";
                            move();
                        }
                        else {
                            at.stop();
                            updatePos(iPos,jPos-1);
                            isMoving=false;
                        }
                        break;
                    case RIGHT:
                        if(!moveTo.equals("right")&& !isMoving) {
                            moveTo = "right";
                            move();
                        }
                        else {
                            at.stop();
                            updatePos(iPos,jPos+1);
                            isMoving=false;
                        }
                        break;

                    default:
                        System.out.println("Invalid Key Press!");
                        break;
                }
            }
        });
    }



    private void pacManMovement(double x, double y){
        graphicsContext.save();
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillOval(x,y,maze.actualMaze.length,maze.actualMaze.length);
        graphicsContext.restore();
    }



    protected void removeObject(double x, double y){
        graphicsContext.save();
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(x,y,maze.actualMaze.length,maze.actualMaze.length);
        graphicsContext.restore();
    }

    private void updatePos(int update_i, int update_j){
//        removeObject(jPos * 20, iPos * 20);
        this.iPos = update_i;
        this.jPos = update_j;
        setX(jPos);
        setY(iPos);
//        drawObject();

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
