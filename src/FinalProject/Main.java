package FinalProject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
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
        scene = new Scene(root, 400, 405);
        mCanvas = new Canvas(scene.getWidth(),scene.getHeight());
//        mCanvas.widthProperty().addListener(event->resizable());
//        mCanvas.heightProperty().addListener(event->resizable());
//        mCanvas.widthProperty().bind(root.widthProperty());
//        mCanvas.heightProperty().bind(root.heightProperty());
        Maze maze = new Maze(mCanvas);
        PacMan pacMan = new PacMan(mCanvas,maze,scene);
        maze.removeCake((int)pacMan.x,(int)pacMan.y);
        int numGhosts = 2;
        ArrayList<Ghost> ghosts = new ArrayList<>();

        for (int i = 0; i < numGhosts; i++){
            ghosts.add(new Ghost(mCanvas,maze));
        }




        root.setCenter(mCanvas);



        Label mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);
        primaryStage.setTitle("AshMan");
        root.setTop(buildMenuBar());
        primaryStage.setScene(scene);
        primaryStage.show();



        //STARTED GAME
//        AnimationTimer gameStarted = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                pacMan.processInput();
//            }
//        };
//        gameStarted.start();
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
