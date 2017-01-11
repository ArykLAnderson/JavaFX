/**
 * Created by Aryk on 1/10/2017.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AAndersonLab0 extends Application {

    private Label status;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> setStatus("Hello world!"));

        BorderPane root = new BorderPane();
        root.setCenter(btn);
        btn.prefWidthProperty().bind(primaryStage.widthProperty().divide(2));
        root.setTop(buildMenus());

        this.status = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(this.status);
        root.setBottom(toolBar);

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Aryk L Anderson, CSCD 370 Lab0, Spring 2016");
        alert.showAndWait();
        //Every assignment requires an about box like this
    }

    private MenuBar buildMenus() {

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("_File");
        MenuItem quitMenuItem = new MenuItem("_Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().add(quitMenuItem);

        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }

    private void setStatus(String status) {
        if (this.status == null)
            this.status = new Label(status);
        else
            this.status.setText(status);
    }
}
