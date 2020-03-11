package FinalProject;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class PacMan extends MazeMovableObjects implements Serializable {
    GraphicsContext graphicsContext;
    int life;
    Maze pacManOnMaze;
    Scene pListenToScene;

    public PacMan(Canvas mCanvas, Maze maze, Scene mScene) {
        this.graphicsContext = mCanvas.getGraphicsContext2D();
        pacManOnMaze = maze;
        this.life = 2;
        drawPacMan();
        addlistener(mScene);
    }

    private void drawPacMan(){
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillOval(0,0,20,20);
    }

    private void addlistener(Scene scene){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case UP:
                        System.out.println("Up");
                        break;
                    case DOWN:
                        System.out.println("Down");
                        break;
                    case LEFT:
                        System.out.println("Left");
                        break;
                    case RIGHT:
                        System.out.println("Right");
                        break;

                        default:
                            System.out.println("Invalid Key Press!");
                            break;
                }
            }
        });
    }

}
