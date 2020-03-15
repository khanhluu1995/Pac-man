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
import sun.management.snmp.jvminstr.JvmMemPoolEntryImpl;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.prefs.Preferences;


public class Main extends Application implements EventHandler<ActionEvent> {



    MenuItem pauseMenuItem ;
    MenuItem goMenuItem;
    MenuItem openMenuItem ;
    MenuItem saveMenuItem ;

    javafx.scene.canvas.Canvas mCanvas;
    BorderPane root;
    Scene scene;
    Maze maze;
    static int numGhosts;
    static int numGhostsIncPerLevel;

    @Override
    public void start(Stage primaryStage) throws Exception{

        root = new BorderPane();
        scene = new Scene(root, 400, 500);
        mCanvas = new Canvas(scene.getWidth(),scene.getHeight()-100);
        numGhosts = 2;
        numGhostsIncPerLevel = 2;
//        mCanvas.widthProperty().addListener(event->resizable());
//        mCanvas.heightProperty().addListener(event->resizable());
//        mCanvas.widthProperty().bind(root.widthProperty());
//        mCanvas.heightProperty().bind(root.heightProperty());
        maze = new Maze(mCanvas,scene,numGhosts);





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

                level_tb.setText("Level: " + maze.level.get());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("you made it to level: " + maze.level.get());
                alert.setTitle("Congratulation!");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK){
                    System.out.println("got it");
                    onGo(goMenuItem,pauseMenuItem,openMenuItem,saveMenuItem);
                }

            }
        });

        maze.cake.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(maze.cake.get() == 0){
                    maze.level.set(maze.level.get()+1);
                    try {
                        numGhosts+=numGhostsIncPerLevel;
                        maze = new Maze(mCanvas,scene,numGhosts);
                        goMenuItem.setDisable(false);
                        pauseMenuItem.setDisable(true);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                }
                cake_tb.setText("Cakes: " + maze.cake.get());

            }
        });

        //END TOOLBAR SETUP


        primaryStage.setTitle("AshMan");
        root.setTop(buildMenuBar(primaryStage));
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

    private MenuBar buildMenuBar(Stage primaryStage){

        MenuBar menuBar = new MenuBar();
        Menu newMenu = new Menu("_File");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        openMenuItem = new MenuItem("_Open");
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        openMenuItem.setOnAction(actionEvent->onOpen());
        saveMenuItem = new MenuItem("_Save as");
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveMenuItem.setOnAction(actionEvent->onSave());

        MenuItem quitMenuItem = new MenuItem(("_Quit"));
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent-> Platform.exit());
        newMenu.getItems().addAll(openMenuItem,saveMenuItem,separator,quitMenuItem);

        Menu gameMenu = new Menu("_Game");
        SeparatorMenuItem gameSeparator1 = new SeparatorMenuItem();

        SeparatorMenuItem gameSeparator2 = new SeparatorMenuItem();

        MenuItem newMenuItem = new MenuItem("_New");
        pauseMenuItem = new MenuItem("_Pause");
        goMenuItem = new MenuItem("_Go");


        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newMenuItem.setOnAction(actionEvent-> {
            try {
                onNew(goMenuItem,pauseMenuItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        goMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));

        goMenuItem.setOnAction(actionEvent->onGo(goMenuItem,pauseMenuItem,openMenuItem,saveMenuItem));
        pauseMenuItem.setDisable(true);
        pauseMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
        pauseMenuItem.setOnAction(actionEvent->onPause(goMenuItem,pauseMenuItem,openMenuItem,saveMenuItem));
        MenuItem settingMenuItem = new MenuItem("_Setting");
        settingMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        settingMenuItem.setOnAction(actionEvent->onSetting());
        gameMenu.getItems().addAll(newMenuItem,gameSeparator1,goMenuItem,pauseMenuItem,gameSeparator2,settingMenuItem);




        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem(" About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);


        menuBar.getMenus().addAll(newMenu,gameMenu,helpMenu);

        return menuBar;
    }




    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {

    }

    private void onOpen(){

    }

    private void onSave(){

    }

    private void onNew(MenuItem goMenuItem, MenuItem pauseMenuItem) throws IOException {
        for(int i= 0; i<maze.ghosts.size();i++){
            maze.ghosts.get(i).at.stop();
        }
        maze = new Maze(mCanvas,scene,numGhosts);
        maze.cake.set(264);
        goMenuItem.setDisable(false);
        pauseMenuItem.setDisable(true);
    }

    private void onGo(MenuItem goMenuItem, MenuItem pauseMenuItem, MenuItem open, MenuItem save){
        maze.isPause = false;
        for (int i = 0; i < maze.ghosts.size();i++){
            maze.ghosts.get(i).at.start();
        }
        goMenuItem.setDisable(true);
        pauseMenuItem.setDisable(false);
        open.setDisable(true);
        save.setDisable(true);

    }

    private void onPause(MenuItem goMenuItem, MenuItem pauseMenuItem,MenuItem open, MenuItem save){
        maze.isPause = true;
        goMenuItem.setDisable(false);
        pauseMenuItem.setDisable(true);
        open.setDisable(false);
        save.setDisable(false);
    }

    private void onSetting(){

    }

    public static void storePreferences(Class c){
        Preferences pref = Preferences.userNodeForPackage(c);
        pref.putInt("numGhostsPerLevel",numGhostsIncPerLevel);
        pref.putInt("numGhosts",numGhosts);

    }

    public static void readPreferences(Class c){
        Preferences pref = Preferences.userNodeForPackage(c);
        numGhostsIncPerLevel = pref.getInt("numGhostsPerLevel",numGhostsIncPerLevel);
        numGhosts = pref.getInt("numGhosts",numGhosts);
    }
}
