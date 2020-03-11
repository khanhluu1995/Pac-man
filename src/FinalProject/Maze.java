package FinalProject;


import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Maze{
    Circle dot;
    MazeObjects[][] actualMaze = new MazeObjects[20][20];
    GraphicsContext graphicsContext;
    Canvas mCanvas;
    final int obstacleSize = 20;


    public Maze(Canvas mCanvas) throws IOException {
        graphicsContext = mCanvas.getGraphicsContext2D();
        GraphicsContext graphicsContext = mCanvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.LIGHTBLUE);
        graphicsContext.fillRect(0,0,mCanvas.getWidth(),mCanvas.getHeight());
        for (int i = 0; i < actualMaze.length; i++){
            for (int j = 0; j < actualMaze.length; j++){
                actualMaze[i][j] = new MazeObjects(false,true,true);
            }
        }

//        insertObs();
        readMap();

    }

    private void drawObstacle(int x, int y){
        graphicsContext.save();
        graphicsContext.setFill(Color.PAPAYAWHIP);
        graphicsContext.fillRect(x,y,obstacleSize,obstacleSize);
        graphicsContext.restore();
        actualMaze[y/obstacleSize][x/obstacleSize].setWall(true);
    }

    private void readMap() throws IOException {
        File file = new File("C:\\Users\\nessy\\IdeaProjects\\CSCD370Final\\maze");
        Scanner scanner = new Scanner(file);
        int i = 0;
        int j = 0;
        String s = "";
        while (scanner.hasNext()){
            s = scanner.next();
            if(j == 19){
                if(s.equals("O")){
                    drawObstacle(j*20,i*20);
                }
                i++;
                j=0;
            }
            else {
                if(s.equals("O")){
                    drawObstacle(j*20,i*20);
                }
                j++;
            }
        }
    }




}
