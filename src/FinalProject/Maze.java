package FinalProject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.crypto.spec.PSource;
import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Maze{
    MazeObjects[][] actualMaze = new MazeObjects[20][20];
    GraphicsContext graphicsContext;
    Canvas mCanvas;
    double obstacleSize;
    SimpleIntegerProperty cake = new SimpleIntegerProperty(399);
    Scene mScene;
    PacMan pacMan;
    AnimationTimer at;
    double distance = 0;
    String nextKey = "";
    int numGhosts = 2;
    ArrayList<Ghost> ghosts = new ArrayList<>();
    SimpleIntegerProperty level = new SimpleIntegerProperty(1);




    public Maze(Canvas mCanvas, Scene scene) throws IOException {
        obstacleSize = mCanvas.getWidth()/actualMaze.length;
        graphicsContext = mCanvas.getGraphicsContext2D();
        GraphicsContext graphicsContext = mCanvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(0,0,mCanvas.getWidth(),mCanvas.getHeight());
        for (int i = 0; i < actualMaze.length; i++){
            for (int j = 0; j < actualMaze.length; j++){
                actualMaze[i][j] = new MazeObjects(false,true,true);
            }
        }

        mScene = scene;
        this.mCanvas =  mCanvas;
        addlistener(mScene);
        readMap();
        this.pacMan = new PacMan(mCanvas,this);
        for (int i = 0; i < numGhosts; i++){
            ghosts.add(new Ghost(mCanvas,this));
        }
        at =new AnimationTimer() {
            @Override
            public void handle(long now) {
                move();
            }
        };
    }

    private void drawObstacle(int y, int x){
        graphicsContext.save();
        graphicsContext.setFill(Color.PAPAYAWHIP);
        graphicsContext.fillRect(x*20,y*20,obstacleSize,obstacleSize);
        graphicsContext.restore();
        actualMaze[y][x].setWall(true);
        cake.set(cake.get()-1);
    }

    private void drawCake(int y, int x){
        graphicsContext.save();
        graphicsContext.setFill(Color.ORCHID);
        graphicsContext.fillOval(x*20+5,y*20+5,obstacleSize/2,obstacleSize/2);
        graphicsContext.restore();
    }

    private void readMap() throws IOException {
        File file = new File("maze");
        Scanner scanner = new Scanner(file);
        int i = 0;
        int j = 0;
        String s = "";
        while (scanner.hasNext()){
            s = scanner.next();
            if(j == 19){
                if(s.equals("O")){
                    drawObstacle(i,j);
                }else {
                    drawCake(i,j);
                }
                i++;
                j=0;
            }
            else {
                if(s.equals("O")){
                    drawObstacle(i,j);
                }
                else {
                    drawCake(i,j);
                }
                j++;
            }
        }
    }//END OF READ MAP

    public void removeCake(int x, int y){
        graphicsContext.save();
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(x,y,obstacleSize,obstacleSize);
        graphicsContext.restore();
        actualMaze[y/20][x/20].setCake(false);
    }


    private void addlistener(Scene scene){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case UP:
                        if(!pacMan.moveTo.equals("up") && distance == 0) {
                            at.start();
                            pacMan.moveTo = "up";
                            nextKey = pacMan.moveTo;
                        }
                        else if( distance != 0){
                            nextKey = "up";
                        }
                        break;
                    case DOWN:
                        if(!pacMan.moveTo.equals("down")&& distance == 0) {
                            at.start();
                            pacMan.moveTo = "down";
                            nextKey = pacMan.moveTo;

                        }
                        else if( distance != 0){
                            nextKey = "down";
                        }
                        break;
                    case LEFT:
                        if(!pacMan.moveTo.equals("left")&& distance == 0) {
                            at.start();
                            pacMan.moveTo = "left";
                            nextKey = pacMan.moveTo;

                        }
                        else if( distance != 0){
                            nextKey = "left";
                        }
                        break;
                    case RIGHT:
                        if(!pacMan.moveTo.equals("right")&& distance == 0) {
                            at.start();
                            pacMan.moveTo = "right";
                            nextKey = pacMan.moveTo;

                        }
                        else if( distance != 0){
                            nextKey = "right";
                        }
                        break;

                    default:
                        System.out.println("Invalid Key Press!");
                        break;
                }
            }
        });
    }

    private void move(){

                switch (pacMan.moveTo) {
                    case "up":
                        if (pacMan.iPos - 1 >= 0 && !actualMaze[pacMan.iPos - 1][pacMan.jPos].getWall()) {

                            pacMan.destination = pacMan.converter(pacMan.iPos-1);
                            distance = Math.abs(pacMan.y-pacMan.destination);
                            if(distance!=0){
                                pacMan.removeObject(pacMan.x,pacMan.y);
                                pacMan.y --;
                                pacMan.pacManMovement(pacMan.x,pacMan.y);
                                pacMan.movingMouth(pacMan.x,pacMan.y,(int)distance,pacMan.moveTo);
                            }

                            else if(distance == 0){

//                                System.out.println("pacman position move in pixels: " + pacMan.x + ", " + pacMan.y);
                                pacMan.moveTo= nextKey;
                                pacMan.updatePos(pacMan.iPos - 1, pacMan.jPos);
                            }

                        } else {
                            System.out.println("stuck");
                            at.stop();
                        }
                        break;

                    case "down":
                        if (pacMan.iPos + 1 <= 19 && !actualMaze[pacMan.iPos + 1][pacMan.jPos].getWall()) {
                            pacMan.destination = pacMan.converter(pacMan.iPos+1);
                            distance = Math.abs(pacMan.y-pacMan.destination);
                            if(distance != 0){
                                pacMan.removeObject(pacMan.x,pacMan.y);
                                pacMan.y++;
                                pacMan.pacManMovement(pacMan.x,pacMan.y);
                                pacMan.movingMouth(pacMan.x,pacMan.y,(int)distance,pacMan.moveTo);
                            }

                            else if(distance == 0){
                                pacMan.moveTo= nextKey;
                                pacMan.updatePos(pacMan.iPos + 1, pacMan.jPos);
                            }
                        } else {
                            System.out.println("stuck");
                            at.stop();
                        }
                        break;

                    case "right":
                        if (pacMan.jPos + 1 <= 19 && !actualMaze[pacMan.iPos][pacMan.jPos + 1].getWall()) {
                            pacMan.destination = pacMan.converter(pacMan.jPos+1);
                            distance = Math.abs(pacMan.x-pacMan.destination);
                            if(distance != 0){
                                pacMan.removeObject(pacMan.x,pacMan.y);
                                pacMan.x++;
                                pacMan.pacManMovement(pacMan.x,pacMan.y);
                                pacMan.movingMouth(pacMan.x,pacMan.y,(int)distance,pacMan.moveTo);

//                                System.out.println(distance);
                            }

                            else if(distance == 0){
                                pacMan.moveTo= nextKey;
                                pacMan.updatePos(pacMan.iPos, pacMan.jPos+ 1);
                            }
                        } else {
                            System.out.println("stuck");
                            at.stop();
                        }
                        break;

                    case "left":
                        if (pacMan.jPos - 1 >= 0 && !actualMaze[pacMan.iPos][pacMan.jPos - 1].getWall()) {
                            pacMan.destination = pacMan.converter(pacMan.jPos-1);
                            distance = Math.abs(pacMan.x-pacMan.destination);
                            if(distance != 0){
                                pacMan.removeObject(pacMan.x,pacMan.y);
                                pacMan.x--;
                                pacMan.pacManMovement(pacMan.x,pacMan.y);
                                pacMan.movingMouth(pacMan.x,pacMan.y,(int)distance,pacMan.moveTo);
                            }

                            else if(distance == 0){
                                pacMan.moveTo= nextKey;
                                pacMan.updatePos(pacMan.iPos, pacMan.jPos - 1);

                            }
                        } else {
                            System.out.println("stuck");
                            at.stop();
                        }
                        break;
                }
                if(pacMan.getPoints()){
                    cake.set(cake.get()-1);
                }
//        System.out.println("pacman is at: " + pacMan.iPos + ", " + pacMan.jPos);
    }
}
