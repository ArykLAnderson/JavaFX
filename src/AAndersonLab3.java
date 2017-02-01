import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class AAndersonLab3 extends Application {

    private Label _status;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Lab 3");
        createLabel();

        BorderPane root = new BorderPane();
        SevenSegment topSegment = new SevenSegment(0);
        VBox widgets = new VBox();
        HBox segments = new HBox();
        widgets.alignmentProperty().setValue(Pos.CENTER);
        segments.alignmentProperty().setValue(Pos.CENTER);
        widgets.setSpacing(5.0);
        SevenSegment bottomSegment1 = new SevenSegment(1);
        SevenSegment bottomSegment2 = new SevenSegment(2);
        SevenSegment bottomSegment3 = new SevenSegment(3);
        SevenSegment bottomSegment4 = new SevenSegment(4);
        segments.getChildren().addAll(bottomSegment1, bottomSegment2, bottomSegment3, bottomSegment4);
        Button button = new Button("Increment All");
        button.setOnAction(actionEvent -> increment(topSegment,
                                                    bottomSegment1,
                                                    bottomSegment2,
                                                    bottomSegment3,
                                                    bottomSegment4));

        widgets.getChildren().addAll(topSegment, button, segments);
        root.setTop(buildMenus());
        root.setCenter(widgets);
        root.setBottom(_status);
        topSegment.draw();
        root.setPrefSize(400, 500);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void increment(SevenSegment a, SevenSegment b, SevenSegment c, SevenSegment d, SevenSegment e) {

        a.increment();
        b.increment();
        c.increment();
        d.increment();
        e.increment();
    }

    private void createLabel() {
        _status = new Label();
        _status.setText("Everything is copacetic");
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Aryk L Anderson, CSCD 370 Lab 3, Spring 2016");
        alert.showAndWait();
    }

    private MenuBar buildMenus() {

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("_File");
        MenuItem quitMenuItem = new MenuItem("_Exit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());

        Menu aboutMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        aboutMenu.getItems().add(aboutMenuItem);

        fileMenu.getItems().add(quitMenuItem);
        menuBar.getMenus().addAll(fileMenu, aboutMenu);

        return menuBar;
    }
}