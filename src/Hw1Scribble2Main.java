/*
 * Scribble Example with Basic Menus
 * Paul Schimpf, Sept 2016
 */
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.undo.UndoManager;

public class Hw1Scribble2Main extends Application
{
    private static final double DIMX=400, DIMY=400 ;  // Canvas dimensions
    private Canvas mCanvas ;                          // Canvas to draw on
    private double mLastX, mLastY;                    // last location of mouse
    private Color  mColor = Color.BLACK;              // initial color

    private UndoManager uManager;
    private MenuItem undoItem;
    private MenuItem redoItem;
    
    @Override
    public void start(Stage primaryStage) {

        mCanvas = new Canvas(DIMX, DIMY);
        this.uManager = new UndoManager();
        MenuBar menuBar = buildMenus();
        initCanvas(false);
        
        // showing two different ways to attach mouse event listeners
        mCanvas.setOnMousePressed(this::onMousePressed);
        mCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);

        /* root container */
        BorderPane root = new BorderPane();
        // setting the background of a container with CSS
        root.setStyle("-fx-background-color: lightgray;");
        root.setCenter(mCanvas);
        root.setTop(menuBar);

        // put a scene on the stage
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Scribble 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar buildMenus() {
        MenuBar menuBar = new MenuBar();

        // File menu with just new and exit for now
        Menu fileMenu = new Menu("_File");

        Menu editMenu = new Menu("_Edit");
        MenuItem undo = new MenuItem("_Undo");
        MenuItem redo = new MenuItem("_Redo");

        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undo.setOnAction(actionEvent -> this.undo());
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        redo.setOnAction(actionEvent -> this.redo());
        undo.setDisable(true);
        redo.setDisable(true);

        editMenu.getItems().add(undo);
        editMenu.getItems().add(redo);
        this.undoItem = undo;
        this.redoItem = redo;


        MenuItem item = new MenuItem("_New");
        item.setAccelerator(new KeyCodeCombination(KeyCode.N,
                KeyCombination.CONTROL_DOWN));
        item.setOnAction(actionEvent -> initCanvas(true));
        fileMenu.getItems().add(item);
        item = new MenuItem("_Quit");
        item.setAccelerator(new KeyCodeCombination(KeyCode.Q,
                KeyCombination.CONTROL_DOWN));
        item.setOnAction(actionEvent -> Platform.exit());
        fileMenu.getItems().add(item);
        
        // Color menu
        // These are more appropriately done with RadioMenuItem and
        // ToggleGroup, which will be added later
        String[] colorItems = new String[]{"_Red", "_Green", "_Blue", "Blac_k"};
        Menu colorMenu = new Menu("_Color");
        for (String colorItem : colorItems) {
            item = new MenuItem(colorItem);
            item.setOnAction(actionEvent -> onColor(colorItem));
            colorMenu.getItems().add(item);
        }

        // Help menu with an about item
        Menu helpMenu = new Menu("_Help");
        item = new MenuItem("_About");
        item.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(item);

        menuBar.getMenus().addAll(fileMenu, editMenu, colorMenu, helpMenu);

        return menuBar;
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Scribble 2");
        alert.setHeaderText("Aryk Anderson, Homework 1, January 2017");
        Optional<ButtonType> result = alert.showAndWait();
    }

    // Color change handler
    private void onColor(String colorItem) {
        switch (colorItem) {
            case "_Red":
                mColor = Color.RED;
                break;
            case "_Green":
                mColor = Color.GREEN;
                break;
            case "_Blue":
                mColor = Color.BLUE;
                break;
            case "Blac_k":
                mColor = Color.BLACK;
                break;
        }
    }

    // initialize the canvas
    private void initCanvas(boolean allowUndo) {

        if (allowUndo) {
            UndoableNew undoable = new UndoableNew(this.mCanvas);
            this.uManager.addEdit(undoable);
        }

        refreshUndoRedo();

        GraphicsContext g = mCanvas.getGraphicsContext2D();
        // defaults to transparent, showing the pane color
        // fill it so we can distinguish the boundaries
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, mCanvas.getWidth(), mCanvas.getHeight());
        g.setLineWidth(3);
    }

    private void refreshUndoRedo() {

        if (this.uManager.canUndo()) {
            this.undoItem.setText(this.uManager.getUndoPresentationName());
            this.undoItem.setDisable(false);
        } else {
            this.undoItem.setDisable(true);
        }

        if (this.uManager.canRedo()) {
            this.redoItem.setText(this.uManager.getRedoPresentationName());
            this.redoItem.setDisable(false);
        } else {
            this.redoItem.setDisable(true);
        }
    }

    // when the mouse is pressed, save the coordinates
    private void onMousePressed(MouseEvent e) {

        UndoableScribble undoable = new UndoableScribble(this.mCanvas);
        this.uManager.addEdit(undoable);

        mLastX = e.getX();
        mLastY = e.getY();

        this.refreshUndoRedo();
    }
    
    // when the mouse is dragged, draw a line
    private void onMouseDragged(MouseEvent e) {

        GraphicsContext g = mCanvas.getGraphicsContext2D();
        g.setStroke(mColor);
        g.strokeLine(mLastX, mLastY, e.getX(), e.getY());
        mLastX = e.getX();
        mLastY = e.getY();
    }

    private void undo() {
        this.uManager.undo();
        this.refreshUndoRedo();
    }

    private void redo() {
        this.uManager.redo();
        this.refreshUndoRedo();
    }

    static Canvas cloneCanvas(Canvas canvas) {

        WritableImage image = canvas.snapshot(null, null);
        Canvas clonedCanvas = new Canvas(canvas.getWidth(), canvas.getHeight());
        clonedCanvas.getGraphicsContext2D().drawImage(image, 0, 0);

        return clonedCanvas;
    }

    static void overrideCanvas(Canvas oldCanvas, Canvas newCanvas) {
        WritableImage image = newCanvas.snapshot(null, null);
        oldCanvas.setHeight(newCanvas.getHeight());
        oldCanvas.setWidth(newCanvas.getWidth());
        oldCanvas.getGraphicsContext2D().drawImage(image, 0, 0);
    }

    // this is where we start execution
    public static void main(String[] args) {
        launch(args);
    }

}
