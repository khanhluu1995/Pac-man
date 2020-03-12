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
import java.util.ArrayList;
import java.util.Scanner;


public class Maze{
    MazeObjects[][] actualMaze = new MazeObjects[20][20];
    GraphicsContext graphicsContext;
    Canvas mCanvas;
    double obstacleSize;
    int cake = 399;



    public Maze(Canvas mCanvas) throws IOException {
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


        readMap();
    }

    private void drawObstacle(int y, int x){
        graphicsContext.save();
        graphicsContext.setFill(Color.PAPAYAWHIP);
        graphicsContext.fillRect(x*20,y*20,obstacleSize,obstacleSize);
        graphicsContext.restore();
        actualMaze[y][x].setWall(true);
        cake--;
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





}
