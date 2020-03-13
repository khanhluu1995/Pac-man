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
    String moveTo = "";
    boolean isMoving = false;
    int points = 0;
    int destination = 0;


    public PacMan(Canvas mCanvas, Maze maze) {
        super(mCanvas,maze);
        this.life = 2;
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






    protected void pacManMovement(double x, double y){
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

    protected void updatePos(int update_i, int update_j){
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
