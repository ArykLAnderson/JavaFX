import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.awt.geom.Point2D;


public class AAndersonLab2 extends Application {

    private Label _status;
    private Canvas _tempCanvas;
    private Canvas _drawCanvas;
    private Point2D.Double _from, _to;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Lab 2");

        BorderPane root = new BorderPane();
        ScrollPane scroll = new ScrollPane();
        StackPane content = new StackPane();

        _tempCanvas = new Canvas(400, 400);
        _drawCanvas = new Canvas(400, 400);
        content.setOnMousePressed(this::mousePressed);
        content.setOnMouseReleased(this::mouseReleased);
        content.setOnMouseDragged(this::mouseDragged);
        blankCanvas(_tempCanvas);

        content.getChildren().add(_tempCanvas);
        content.getChildren().add(_drawCanvas);

        scroll.setContent(content);
        root.setCenter(scroll);
        root.setTop(buildMenus());

        _status = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(_status);
        root.setBottom(toolBar);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void blankCanvas(Canvas canvas) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        context.setFill(Color.WHITE);
        context.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Aryk L Anderson, CSCD 370 Lab 2, Spring 2016");
        alert.showAndWait();
    }

    private void mousePressed(MouseEvent event) {
        _from = new Point2D.Double(event.getX(), event.getY());
    }

    private void mouseDragged(MouseEvent event) {

        if (_drawCanvas.contains(event.getX(), event.getY())) {
            _to = new Point2D.Double(event.getX(), event.getY());
            blankCanvas(_tempCanvas);
            drawLine(_tempCanvas, Color.BLACK);
        }
    }

    private void mouseReleased(MouseEvent event) {

        if (_drawCanvas.contains(event.getX(), event.getY()))
            _to = new Point2D.Double(event.getX(), event.getY());

        blankCanvas(_tempCanvas);
        drawLine(_drawCanvas, Color.RED);
    }

    private void drawLine(Canvas canvas, Paint paint) {

        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setStroke(paint);
        context.strokeLine(_from.getX(), _from.getY(), _to.getX(), _to.getY());
        _status.setText("From (" + _from.getX() + ", " + _from.getY() +") to (" + _to.getX() + ", " + _to.getY() + ")");
    }

    private MenuBar buildMenus() {

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("_File");

        MenuItem quitMenuItem = new MenuItem("_Exit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        quitMenuItem.setOnAction(actionEvent -> Platform.exit());

        MenuItem newMenuItem = new MenuItem("_New");
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newMenuItem.setOnAction(actionEvent -> onNew());

        MenuItem openMenuItem = new MenuItem("_Open");
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        openMenuItem.setOnAction(actionEvent -> onOpen());

        MenuItem saveMenuItem = new MenuItem("_Save");
        saveMenuItem.setOnAction(actionEvent -> onSave());
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        MenuItem saveAsMenuItem = new MenuItem("_Save As");
        saveAsMenuItem.setOnAction(actionEvent -> onSaveAs());
        saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));

        fileMenu.getItems().addAll(newMenuItem,
                                   openMenuItem,
                                   saveMenuItem,
                                   saveAsMenuItem,
                                   new SeparatorMenuItem(),
                                   quitMenuItem);

        String[] widthItems = {"_1 Pixel", "_4 Pixels", "_8 Pixels"};
        String[] colorItems = {"_Black", "_Red", "_Green", "_Blue"};
        Menu width = new Menu("_Width");
        Menu color = new Menu("_Color");

        for (String wString : widthItems) {
            RadioMenuItem item = new RadioMenuItem(wString);
            item.setOnAction(actionEvent -> onWidth(wString));
            width.getItems().add(item);
        }

        for (String wString : colorItems) {
            RadioMenuItem item = new RadioMenuItem(wString);
            item.setOnAction(actionEvent -> onColor(wString));
            color.getItems().add(item);
        }

        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);

        menuBar.getMenus().addAll(fileMenu, width, color, helpMenu);
        return menuBar;
    }

    public void onColor(String color) {

        switch(color) {
            case "_Black":
                _status.setText("Set color to black");
                break;
            case "_Red":
                _status.setText("Set color to red");
                break;
            case "_Green":
                _status.setText("Set color to green");
                break;
            case "_Blue":
                _status.setText("Set color to blue");
                break;
        }
    }

    public void onWidth(String width) {

        switch(width) {

            case "_1 Pixel":
                _status.setText("Set width to 1 pixel");
                break;
            case "_4 Pixels":
                _status.setText("Set width to 4 pixels");
                break;
            case "_8 Pixels":
                _status.setText("Set width to 8 pixels");
                break;
        }
    }

    public void onNew() {
        blankCanvas(_drawCanvas);
    }

    public void onOpen() {
        _status.setText("Open command");
    }

    public void onSave() {
        _status.setText("Save command");
    }

    public void onSaveAs() {
        _status.setText("Save As command");
    }
}
