package FinalProject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.management.snmp.jvminstr.JvmMemPoolEntryImpl;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    Setting setting;
    File mFile = null;

    @Override
    public void start(Stage primaryStage) throws Exception{

        root = new BorderPane();
        scene = new Scene(root, 400, 500);
        mCanvas = new Canvas(scene.getWidth(),scene.getHeight()-100);
//        mCanvas.widthProperty().addListener(event->resizable());
//        mCanvas.heightProperty().addListener(event->resizable());
//        mCanvas.widthProperty().bind(root.widthProperty());
//        mCanvas.heightProperty().bind(root.heightProperty());
        setting = new Setting();
        buildSettingObjects();
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
                alert.show();
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
        openMenuItem.setOnAction(actionEvent->onOpen(primaryStage));
        saveMenuItem = new MenuItem("_Save as");
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveMenuItem.setOnAction(actionEvent->onSave(primaryStage));

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

    private void onOpen(Stage mStage){
        FileChooser chooser = new FileChooser() ;
        chooser.setTitle("Open a png File");
        chooser.setInitialDirectory(new File("."));
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ash Files", "*.ash"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = chooser.showOpenDialog(mStage);
        if (selectedFile==null) return ;
        try {
// TODO: open a stream, read the stuff, close the stream
            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            maze = (Maze) objectInputStream.readObject();
            fileInputStream.close();

        }
        catch (IOException | ClassNotFoundException ex) {
// TODO: report the problem somehow
            Logger.getLogger(Main.class.getName())

                    .log(Level.SEVERE, "Open Exception", ex.getMessage());
            return;

        }

    }

    private void onSave(Stage mainStage){
        FileChooser chooser = new FileChooser() ;
        chooser.setTitle("Save Image File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("special game file", "*.ash")
        ) ;

        File file = chooser.showSaveDialog(mainStage) ;
        if (file !=null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(maze);
                System.out.println("go here");
                fileOutputStream.close();

            } catch (Exception e) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Save Exception", e.getMessage());
                e.printStackTrace();

            }


        }

    }

    private void onNew(MenuItem goMenuItem, MenuItem pauseMenuItem) throws IOException {
        for(int i= 0; i<maze.ghosts.size();i++){
            maze.ghosts.get(i).at.stop();
        }
        buildSettingObjects();
        maze.level.set(1);
        PacMan.points.set(0);
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
        setting.showAndWait();
        buildSettingObjects();
    }

    private void buildSettingObjects(){
        SettingStruct settingStruct = new SettingStruct();
        settingStruct.readPreferences(getClass());
        numGhosts = settingStruct.numGhosts;
        numGhostsIncPerLevel = settingStruct.numGhostsIncPerLevel;
    }

    //SUB CLASS FOR SETTING OBJECTS
    class Setting extends Dialog<Void> {

        @FXML
        private ChoiceBox<Integer> initial_num_choice;

        @FXML
        private ChoiceBox<Integer> additional_ghost_choice;

        SettingStruct settingStruct;


        public Setting() throws IOException {
            super();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
            fxmlLoader.setController(this);
            Parent parent = fxmlLoader.load();
            initialization();
            getDialogPane().setContent(parent);

            ButtonType OK = new ButtonType("OK");
            ButtonType Cancel = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);
            getDialogPane().getButtonTypes().add(OK);
            getDialogPane().getButtonTypes().add(Cancel);

            Button buttonOK = (Button) getDialogPane().lookupButton(OK);
            buttonOK.addEventFilter(ActionEvent.ACTION, actionEvent -> onButtonOK(actionEvent));

            Button buttonCancel = (Button)getDialogPane().lookupButton(Cancel);
            buttonCancel.addEventFilter(ActionEvent.ACTION, actionEvent -> onButtonCancel(actionEvent));
        }

        private void initialization(){
            settingStruct = new SettingStruct();
            settingStruct.readPreferences(getClass());

            initial_num_choice.getItems().addAll(1,2,3,4);
            additional_ghost_choice.getItems().addAll(1,2,3,4);


            initial_num_choice.setValue(settingStruct.numGhosts);
            additional_ghost_choice.setValue(settingStruct.numGhostsIncPerLevel);

        }

        private void onButtonOK(ActionEvent event){
            try {
                if(settingStruct.numGhosts != initial_num_choice.getValue()){
                    settingStruct.numGhosts = initial_num_choice.getValue();
                    settingStruct.storePreferences(getClass());
                    settingStruct.numGhostsIncPerLevel =  additional_ghost_choice.getValue();
                    settingStruct.storePreferences(getClass());
                    onNew(goMenuItem,pauseMenuItem);
                }
                else {
                    settingStruct.numGhosts = initial_num_choice.getValue();
                    settingStruct.storePreferences(getClass());
                    settingStruct.numGhostsIncPerLevel =  additional_ghost_choice.getValue();
                    settingStruct.storePreferences(getClass());
                }


            }catch (Exception e){
                Logger.getLogger(Main.class.getName())

                        .log(Level.SEVERE, "Setting Class Exception", e.getMessage());

                e.printStackTrace();
            }

        }

        private void onButtonCancel(ActionEvent event){

        }

    }


}
