package FinalProject;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public abstract class MazeMovableObjects {
    GraphicsContext graphicsContext;
    Maze maze;
    int iPos =0; int jPos = 0;
    double x,y;

    public MazeMovableObjects(Canvas mCanvas, Maze maze) {
        this.graphicsContext = mCanvas.getGraphicsContext2D();
        this.maze = maze;
        initialPosition();
    }

    protected void initialPosition(){
        boolean isGoodPos = false;
        while(!isGoodPos) {
            Random random = new Random();
            iPos = random.nextInt(20);
            jPos = random.nextInt(20);
            if(!maze.actualMaze[iPos][jPos].getWall()){
                drawObject();
                isGoodPos = true;
            }
        }
    }

    protected void drawObject(){
        setX();
        setY();
        graphicsContext.save();
        graphicsContext.setFill(Color.ORANGERED);
        graphicsContext.fillOval(x,y,maze.actualMaze.length,maze.actualMaze.length);
        graphicsContext.restore();
    }



    public void setX(){
        x = jPos*20;
    }

    public void setY(){
        y = iPos*20;
    }
}
