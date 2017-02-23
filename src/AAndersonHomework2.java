import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AAndersonHomework2 extends Application {

    private Label _status;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Homework 2");
        createLabel();

        BorderPane root = new BorderPane();

        Clock clock = new Clock();

        root.setTop(buildMenus());
        root.setCenter(clock);
        root.setBottom(new ToolBar(_status));
        _status.textProperty().bind(clock.hoursProperty().asString("%02d").concat(":")
                .concat(clock.minutesProperty().asString("%02d")).concat(":")
                .concat(clock.secondsProperty().asString("%02d")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void createLabel() {
        _status = new Label();
        _status.setText("Everything is copacetic");
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Aryk L Anderson, CSCD 370 Homework 2, Winter 2017");
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