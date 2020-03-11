package FinalProject;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PacMan {
    GraphicsContext graphicsContext;
    int life;
    Maze pacManOnMaze;

    public PacMan(Canvas mCanvas, Maze maze) {
        this.graphicsContext = mCanvas.getGraphicsContext2D();
        pacManOnMaze = maze;
        this.life = 2;
        drawPacMan();
    }

    private void drawPacMan(){
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillOval(0,0,20,20);
    }
}
