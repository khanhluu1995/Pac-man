package FinalProject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;


public class Main extends Application implements EventHandler<ActionEvent> {



    javafx.scene.canvas.Canvas mCanvas;
    BorderPane root;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{

        root = new BorderPane();
        scene = new Scene(root, 400, 500);
        mCanvas = new Canvas(scene.getWidth(),scene.getHeight()-100);
//        mCanvas.widthProperty().addListener(event->resizable());
//        mCanvas.heightProperty().addListener(event->resizable());
//        mCanvas.widthProperty().bind(root.widthProperty());
//        mCanvas.heightProperty().bind(root.heightProperty());
        Maze maze = new Maze(mCanvas,scene);





        root.setCenter(mCanvas);



        //TOOLBAR SETUP
        Label points_tb = new Label("Scores: " + maze.pacMan.points.get());
        Label level_tb = new Label("Level: " +maze.level.get());
        Label cake_tb = new Label("Cakes: " + maze.cake.get());
        Font font = new Font("Arial", 15);
        points_tb.setFont(font);
        level_tb.setFont(font);
        cake_tb.setFont(font);
        ToolBar toolBar = new ToolBar();
        toolBar.getItems().addAll(points_tb,level_tb,cake_tb);

        maze.pacMan.points.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                points_tb.setText("Scores: " + maze.pacMan.points.get());
            }
        });

        maze.level.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                level_tb.setText("Scores: " + maze.level.get());
            }
        });

        maze.cake.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                cake_tb.setText("Cakes: " + maze.cake.get());
            }
        });

        //END TOOLBAR SETUP

        primaryStage.setTitle("AshMan");
        root.setTop(buildMenuBar());
        primaryStage.setScene(scene);
        root.setBottom(toolBar);

        primaryStage.show();
    }

//    private void resizable(){
//        double width = root.getWidth();
//        double height = root.getHeight();
//
//        GraphicsContext gc = mCanvas.getGraphicsContext2D();
//
//    }

    private void onAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION) ;
        alert.setTitle("About") ;
        alert.setHeaderText("Khanh Luu, CSCD 370 Final Project, Wtr 2020") ;
        alert.showAndWait();
    }

    private MenuBar buildMenuBar(){

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("_File");

        MenuItem quitMenuItem = new MenuItem(("_Quit"));
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent-> Platform.exit());

        menu.getItems().add(quitMenuItem);

        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem(" About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);


        menuBar.getMenus().addAll(menu,helpMenu);

        return menuBar;
    }




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {

    }
}
