package FinalProject;

import com.sun.xml.internal.ws.api.client.WSPortInfo;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    boolean alive;
    String moveTo = "";
    SimpleIntegerProperty points = new SimpleIntegerProperty(0);
    int destination = 0;


    public PacMan(Canvas mCanvas, Maze maze) {
        super(mCanvas,maze);
        alive = true;
        initialPosition();
    }

    @Override
    protected void initialPosition() {
        super.initialPosition();
        maze.actualMaze[iPos][jPos].setCake(false);
        maze.cake.set(maze.cake.get()-1);
    }

    @Override
    protected void drawObject() {
        setY(iPos);
        setX(jPos);
        graphicsContext.save();
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillOval(x,y,maze.actualMaze.length,maze.actualMaze.length);
//        System.out.println("pacman position: " + iPos + ", " + jPos);
//        System.out.println("pacman position in pixels: " + x + ", " + y);
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

    protected void movingMouth(int x, int y, int countStage, String mouthDirection){

        switch (mouthDirection) {
            case "up":
                    graphicsContext.setFill(Color.LIGHTBLUE);
                    graphicsContext.fillPolygon(new double[]{x + maze.actualMaze.length/2,x+countStage,x+20-countStage}, new double[]{y+10,y,y},3);

                break;
            case "down":
                graphicsContext.setFill(Color.LIGHTBLUE);
                graphicsContext.fillPolygon(new double[]{x + maze.actualMaze.length/2,x+countStage,x+20-countStage},
                                            new double[]{y+10,y+maze.actualMaze.length,y+maze.actualMaze.length},3);

                break;
            case "left" :
                graphicsContext.setFill(Color.LIGHTBLUE);
                graphicsContext.fillPolygon(new double[]{x + maze.actualMaze.length/2,x,x},
                        new double[]{y+10,y+countStage,y+20-countStage},3);
                break;
            case "right" :
                graphicsContext.setFill(Color.LIGHTBLUE);
                graphicsContext.fillPolygon(new double[]{x + maze.actualMaze.length/2,x+maze.actualMaze.length,x+maze.actualMaze.length},
                        new double[]{y+10,y+countStage,y+20-countStage},3);

                break;
        }

    }

    protected boolean getPoints(){
        if(maze.actualMaze[iPos][jPos].getCake()){
            maze.actualMaze[iPos][jPos].setCake(false);
            points.set(points.get()+1);
            return true;
        }

        else {
            return false;
        }
    }


}
