import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.awt.geom.Point2D;


public class AAndersonLab2 extends Application {

    private Label status;
//    private ImageView view;

    private Canvas _tempCanvas;
    private Canvas _drawCanvas;
    private Point2D.Double _from, _to;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
//        this.view = new ImageView(new Image("images/logo.png"));

        BorderPane root = new BorderPane();
        ScrollPane scroll = new ScrollPane();
        _tempCanvas = new Canvas(400, 400);
        _tempCanvas.setOnMousePressed(this::mousePressed);
        _tempCanvas.setOnMouseReleased(this::mouseReleased);
        _tempCanvas.setOnMouseDragged(this::mouseDragged);

        blankCanvas(_tempCanvas);
        scroll.setContent(_tempCanvas);
        root.setCenter(scroll);
//        root.setCenter(this.view);
//        root.setTop(buildMenus());

        this.status = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(this.status);
        root.setBottom(toolBar);

//        ListView<String> lists = new ListView<>();
//        String[] tList = {"First Album", "Cindy", "Fred", "Kate", "Keith", "Matt", "Rickey"};
//        lists.setItems(FXCollections.observableArrayList(tList));
//        lists.getSelectionModel().selectedItemProperty().addListener(this);
//        lists.setPrefWidth(this.computeStringWidth("First Album"));
//        root.setLeft(lists);

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
        //Every assignment requires an about box like this
    }

    private void mousePressed(MouseEvent event) {
        _from = new Point2D.Double(event.getX(), event.getY());
    }

    private void mouseDragged(MouseEvent event) {
        _to = new Point2D.Double(event.getX(), event.getY());
        blankCanvas(_tempCanvas);
        drawLine(_tempCanvas, Color.BLACK);
    }

    private void mouseReleased(MouseEvent event) {
        _to = new Point2D.Double(event.getX(), event.getY());
    }

    private void drawLine(Canvas canvas, Paint paint) {

        Line line = new Line(_from.getX(), _from.getY(), _to.getX(), _to.getY());
        line.setStrokeWidth(1.0);
        line.setStroke(paint);
//        GraphicsContext context = canvas.getGraphicsContext2D();
//        context.moveTo(_from.getX(), _from.getY());
//        context.lineTo(_to.getX(), _to.getY());
//        context.stroke();
    }



//    private MenuBar buildMenus() {
//
//        MenuBar menuBar = new MenuBar();
//
//        Menu fileMenu = new Menu("_File");
//        MenuItem quitMenuItem = new MenuItem("_Quit");
//        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
//        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
//        fileMenu.getItems().add(quitMenuItem);
//
//        Menu helpMenu = new Menu("_Help");
//        MenuItem aboutMenuItem = new MenuItem("_About");
//        aboutMenuItem.setOnAction(actionEvent -> onAbout());
//        helpMenu.getItems().add(aboutMenuItem);
//
//        menuBar.getMenus().addAll(fileMenu, helpMenu);
//        return menuBar;
//    }


}
