package FinalProject;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MazeGrid extends Rectangle {
    Circle dot;
    Boolean isWall;

    public MazeGrid() {
        super();
        super.setHeight(20);
        super.setWidth(20);
        this.isWall = false;
        dot = new Circle(10);
        dot.setFill(Color.WHITE);
    }


}
