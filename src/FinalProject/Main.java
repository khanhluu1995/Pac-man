package FinalProject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application implements EventHandler<ActionEvent> {




    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane root = new BorderPane();
        Group group = new Group();

        GridPane maze = new GridPane();
        for (int i = 0; i < 20;i++) {
            for (int j = 0; j < 20; j++) {
                MazeGrid mazeGrid = new MazeGrid();
                mazeGrid.setFill(Color.CYAN);
                maze.add(mazeGrid, i, j, 1, 1);
            }
        }

        Circle circle = new Circle(4);
        circle.setFill(Color.WHITE);

        maze.getChildren().add(4, circle);

        maze.getChildren().remove(21);
        maze.getChildren().remove(2);






        //START TO BUILD THE CUSTOMIZE MAZE MANUALLY

        maze.setAlignment(Pos.CENTER);



        VBox vBox = new VBox();

        vBox.getChildren().add(maze);



        root.setCenter(vBox);



        Label mStatus = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(mStatus);
        root.setBottom(toolBar);
        primaryStage.setTitle("AshMan");
        root.setTop(buildMenuBar());
        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.show();
    }

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
