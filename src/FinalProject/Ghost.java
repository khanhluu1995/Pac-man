package FinalProject;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.Serializable;

public class Ghost extends MazeMovableObjects implements Serializable {


    public Ghost(Canvas mCanvas, Maze maze) {
        super(mCanvas, maze);
    }

    @Override
    protected void initialPosition() {
        super.initialPosition();
    }

    public boolean eatPacMan(PacMan pacMan){
        if (pacMan.iPos== this.iPos && pacMan.jPos == this.jPos){
            return true;
        }
        else return false;
    }
}