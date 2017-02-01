import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.awt.geom.Point2D;


public class AAndersonLab4 extends Application {

    private enum ToolBarPos {LEFT, TOP, RIGHT}

    private Label _status;
    private Canvas _tempCanvas;
    private Canvas _drawCanvas;
    private Point2D.Double _from, _to;
    private int _pixelWidth;
    private Color _drawColor;
    private ToggleGroup _widthToggle;
    private ToggleGroup _colorToggle;
    private VBox _menuBox;
    private ToolBarPos _pos;
    private BorderPane _root;
    private ToolBar _toolBar;
    private MenuBar _topMenu;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Lab 4");
        _status = new Label();

        _root = new BorderPane();
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
        _root.setCenter(scroll);

        _menuBox = new VBox();
        _topMenu = buildMenus();
        _menuBox.getChildren().add(_topMenu);
        _root.setTop(_menuBox);

        ToolBar statusBar = new ToolBar(_status);
        _root.setBottom(statusBar);

        _pos = ToolBarPos.LEFT;
        _toolBar = createToolbar();
        _root.setLeft(_toolBar);

        primaryStage.setScene(new Scene(_root));
        primaryStage.show();
        _status.setText("Everything is copacetic");
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
        alert.setHeaderText("Aryk L Anderson, CSCD 370 Lab 4, Spring 2016");
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
        drawLine(_drawCanvas, _drawColor);
    }

    private void drawLine(Canvas canvas, Paint paint) {

        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setStroke(paint);
        context.setLineWidth(_pixelWidth);
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

        _widthToggle = new ToggleGroup();
        _colorToggle = new ToggleGroup();
        boolean first = true;

        for (String wString : widthItems) {
            RadioMenuItem item = new RadioMenuItem(wString);
            item.setOnAction(actionEvent -> onWidth(wString));
            item.setToggleGroup(_widthToggle);
            width.getItems().add(item);
            if (first) {
                first = false;
                item.setSelected(true);
                onWidth(wString);
            }
        }

        first = true;
        for (String wString : colorItems) {
            RadioMenuItem item = new RadioMenuItem(wString);
            item.setOnAction(actionEvent -> onColor(wString));
            item.setToggleGroup(_colorToggle);
            color.getItems().add(item);
            if (first) {
                first = false;
                item.setSelected(true);
                onColor(wString);
            }
        }

        Menu helpMenu = new Menu("_Help");
        MenuItem aboutMenuItem = new MenuItem("_About");
        aboutMenuItem.setOnAction(actionEvent -> onAbout());
        helpMenu.getItems().add(aboutMenuItem);

        width.showingProperty().addListener((boolObj, oldVal, newVal) -> onWidthShowing(newVal));
        color.showingProperty().addListener((boolObj, oldVal, newVal) -> onColorShowing(newVal));

        menuBar.getMenus().addAll(fileMenu, width, color, helpMenu);
        return menuBar;
    }

    private void onWidthShowing(boolean bool) {
        ObservableList<Toggle> widths = _widthToggle.getToggles();

        if (_pixelWidth == 1) {
            _widthToggle.selectToggle(widths.get(0));
        } else if (_pixelWidth == 4) {
            _widthToggle.selectToggle(widths.get(1));
        } else if (_pixelWidth == 8) {
            _widthToggle.selectToggle(widths.get(2));
        }
    }

    private void onColorShowing(boolean bool) {
        ObservableList<Toggle> colors = _colorToggle.getToggles();
        if (_drawColor == Color.BLACK) {
            _colorToggle.selectToggle(colors.get(0));
        } else if (_drawColor == Color.RED) {
            _colorToggle.selectToggle(colors.get(1));
        } else if (_drawColor == Color.GREEN) {
            _colorToggle.selectToggle(colors.get(2));
        } else if (_drawColor == Color.BLUE) {
            _colorToggle.selectToggle(colors.get(3));
        }
    }

    private ToolBar createToolbar() {

        ToolBar tools = new ToolBar();
        tools.setOrientation(Orientation.VERTICAL);

        Button button = new Button();
        button.setGraphic(new ImageView(new Image("assets/New.png")));
        button.setOnAction(actionEvent -> onNew());
        button.setTooltip(new Tooltip("New drawing"));
        tools.getItems().add(button);

        button = new Button();
        button.setGraphic(new ImageView(new Image("assets/Open.png")));
        button.setOnAction(actionEvent -> onOpen());
        button.setTooltip(new Tooltip("Open drawing"));
        tools.getItems().add(button);

        button = new Button();
        button.setGraphic(new ImageView(new Image("assets/Save.png")));
        button.setOnAction(actionEvent -> onSave());
        button.setTooltip(new Tooltip("Save drawing"));
        tools.getItems().add(button);

        Separator seperator = new Separator();
        seperator.setOrientation(Orientation.HORIZONTAL);
        tools.getItems().add(seperator);

        button = new Button();
        button.setGraphic(new ImageView(new Image("assets/Width.png")));
        button.setOnAction(actionEvent -> onCycleWidth());
        button.setTooltip(new Tooltip("Cycle width setting"));
        tools.getItems().add(button);

        seperator = new Separator();
        seperator.setOrientation(Orientation.HORIZONTAL);
        tools.getItems().add(seperator);

        button = new Button();
        button.setGraphic(new ImageView(new Image("assets/Color.png")));
        button.setOnAction(actionEvent -> onCycleColor());
        button.setTooltip(new Tooltip("Cycle color setting"));
        tools.getItems().add(button);

        seperator = new Separator();
        seperator.setOrientation(Orientation.HORIZONTAL);
        tools.getItems().add(seperator);

        button = new Button();
        button.setGraphic(new ImageView(new Image("assets/Move.png")));
        button.setOnAction(actionEvent -> onMove());
        button.setTooltip(new Tooltip("Cycle toolbar position"));
        tools.getItems().add(button);

        return tools;
    }

    private void onCycleWidth() {

        if (_pixelWidth == 1) {
            _pixelWidth = 4;
            _status.setText("Changed pixel width to 4");
        } else if (_pixelWidth == 4) {
            _pixelWidth = 8;
            _status.setText("Changed pixel width to 8");
        } else if (_pixelWidth == 8) {
            _pixelWidth = 1;
            _status.setText("Changed pixel width to 1");
        }
    }

    private void onCycleColor() {

        if (_drawColor == Color.BLACK) {
            _drawColor = Color.RED;
            _status.setText("Changed color to red");
        } else if (_drawColor == Color.RED) {
            _drawColor = Color.GREEN;
            _status.setText("Changed color to green");
        } else if (_drawColor == Color.GREEN) {
            _drawColor = Color.BLUE;
            _status.setText("Changed color to blue");
        } else if (_drawColor == Color.BLUE) {
            _drawColor = Color.BLACK;
            _status.setText("Changed color to black");
        }
    }

    private void onMove() {

        if (_pos == ToolBarPos.LEFT) {
            _root.setLeft(null);
           _toolBar.setOrientation(Orientation.HORIZONTAL);

           for (Node node : _toolBar.getItems()) {
               if (node instanceof Separator) {
                   ((Separator) node).setOrientation(Orientation.VERTICAL);
               }
            }

           _menuBox.getChildren().add(_toolBar);
           _pos = ToolBarPos.TOP;
        } else if (_pos == ToolBarPos.TOP) {
            _menuBox = new VBox(_topMenu);
            _root.setTop(_menuBox);
            _toolBar.setOrientation(Orientation.VERTICAL);
            for (Node node : _toolBar.getItems()) {
                if (node instanceof Separator) {
                    ((Separator) node).setOrientation(Orientation.HORIZONTAL);
                }
            }
            _root.setRight(_toolBar);
            _pos = ToolBarPos.RIGHT;
        } else if (_pos == ToolBarPos.RIGHT) {
            _root.setRight(null);
            _root.setLeft(_toolBar);
            _pos = ToolBarPos.LEFT;
        }
    }

    private void onColor(String color) {

        switch(color) {
            case "_Black":
                _status.setText("Changed color to black");
                _drawColor = Color.BLACK;
                break;
            case "_Red":
                _status.setText("Changed color to red");
                _drawColor = Color.RED;
                break;
            case "_Green":
                _status.setText("Changed color to green");
                _drawColor = Color.GREEN;
                break;
            case "_Blue":
                _status.setText("Changed color to blue");
                _drawColor = Color.BLUE;
                break;
        }
    }

    private void onWidth(String width) {

        switch(width) {

            case "_1 Pixel":
                _status.setText("Changed pixel width to 1");
                _pixelWidth = 1;
                break;
            case "_4 Pixels":
                _status.setText("Changed pixel width to 4");
                _pixelWidth = 4;
                break;
            case "_8 Pixels":
                _status.setText("Changed pixel width to 8");
                _pixelWidth = 8;
                break;
        }
    }

    private void onNew() {
        blankCanvas(_drawCanvas);
    }

    private void onOpen() {
        _status.setText("Open command");
    }

    private void onSave() {
        _status.setText("Save command");
    }

    private void onSaveAs() {
        _status.setText("Save As command");
    }
}
