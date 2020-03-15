package FinalProject;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.Random;

public class Ghost extends MazeMovableObjects implements Serializable {

    int initMovement;
    Random random;
    String direction = "";
    AnimationTimer at;
    int destination = 0;
    int speed;


    public Ghost(Canvas mCanvas, Maze maze, PacMan pacMan) {
        super(mCanvas, maze);
        initialPosition();
        random = new Random();
        directionGenerator(initMovement);
        speed = maze.level.get();
        while(20%speed!=0){
            speed++;
        }
        if(speed>10){
            speed = 10;
        }
        int currentLevel = maze.level.get();

        at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(pacMan.alive && !maze.isPause && maze.cake.get() > 0 && currentLevel == maze.level.get()) {
                    ghostMove();
                    eatPacMan(pacMan);
                }
                else if(maze.isPause  ){
                    at.stop();
                }
                else {
                    at.stop();
                }
            }
        };
        at.start();

    }

    private void directionGenerator(int num){
        initMovement = random.nextInt(4);

        switch (num){
            case 1:
                direction = "left";
                break;

            case 2:
                direction = "right";
                break;

            case 3:
                direction ="up";
                break;

            case 0:
                direction = "down";
                break;

            default:
                System.out.println("sometime wrong in directionGenerator()");
        }
    }

    private void removeObject(double x, double y){
        graphicsContext.save();
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(x,y,maze.actualMaze.length,maze.actualMaze.length);
        graphicsContext.restore();
    }
    protected void ghostMovement(double x, double y){
        graphicsContext.save();
        graphicsContext.setFill(Color.ORANGERED);
        graphicsContext.fillOval(x,y,maze.actualMaze.length,maze.actualMaze.length);
        graphicsContext.restore();
    }

    private void checkCake(){
        if(maze.actualMaze[iPos][jPos].getCake()){
            graphicsContext.save();
            graphicsContext.setFill(Color.ORCHID);
            graphicsContext.fillOval(converter(jPos)+5,converter(iPos)+5,maze.obstacleSize/2,maze.obstacleSize/2);
            graphicsContext.restore();
        }
    }



    public boolean eatPacMan(PacMan pacMan){
        if (pacMan.iPos== this.iPos && pacMan.jPos == this.jPos){
            maze.mySounds.playClip(3);
            pacMan.alive=false;
            maze.mySounds.playClip(4);
            return true;
        }
        else return false;
    }

    protected void ghostMove(){
        at.start();

        switch (direction){
            case "up":
                if (iPos - 1 >= 0 && !maze.actualMaze[iPos - 1][jPos].getWall()) {
                    at.start();
                    destination = converter(iPos-1);
                    int distance = Math.abs(y - destination);
                    if(distance>0 ){
                        removeObject(x,y);
                        y -= speed;
                        checkCake();
                        ghostMovement(x,y);
                    }

                    else if(distance == 0){
                        checkCake();
                        updatePos(iPos - 1, jPos);
                        directionGenerator(initMovement);

                    }

                } else {
                    at.stop();
                    directionGenerator(initMovement);
                    at.start();
                }
                break;

            case "down":
                if (iPos + 1 <= 19 && !maze.actualMaze[iPos + 1][jPos].getWall()) {
                    at.start();
                    destination = converter(iPos+1);
                    int distance = Math.abs(y - destination);
                    if(distance>0){
                        removeObject(x,y);
                        y+=speed;
                        checkCake();

                        ghostMovement(x,y);
                    }

                    else if(distance == 0){
                        checkCake();
                        updatePos(iPos + 1, jPos);
                        directionGenerator(initMovement);
                    }

                } else {
                    at.stop();
                    directionGenerator(initMovement);
                    at.start();
                }

                break;

            case "left":
                if (jPos - 1 >= 0 && !maze.actualMaze[iPos][jPos-1].getWall()) {
                    at.start();
                    destination = converter(jPos-1);
                    int distance = Math.abs(x - destination);
                    if(distance>0){
                        removeObject(x,y);
                        x-=speed;
                        checkCake();

                        ghostMovement(x,y);
                    }

                    else if(distance == 0){
                        checkCake();
                        updatePos(iPos, jPos-1);
                        directionGenerator(initMovement);
                    }

                } else {
                    at.stop();
                    directionGenerator(initMovement);
                    at.start();
                }

                break;

            case "right":
                if (jPos + 1 <= 19 && !maze.actualMaze[iPos][jPos+1].getWall()) {
                    at.start();
                    destination = converter(jPos+1);
                    int distance = Math.abs(x - destination);
                    if(distance>0){
                        removeObject(x,y);
                        x+=speed;
                        checkCake();
                        ghostMovement(x,y);

                    }

                    else if(distance == 0){
                        checkCake();
                        updatePos(iPos, jPos+1);
                        directionGenerator(initMovement);
                    }

                } else {
                    at.stop();
                    directionGenerator(initMovement);
                    at.start();
                }

                break;
        }

    }
}