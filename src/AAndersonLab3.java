import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AAndersonLab3 extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Lab 2");
        BorderPane root = new BorderPane();
        SevenSegment topsegment = new SevenSegment(0);
        SevenSegment bottomSegment1 = new SevenSegment(1);
        SevenSegment bottomSegment2 = new SevenSegment(2);
        SevenSegment bottomSegment3 = new SevenSegment(3);
        SevenSegment bottomSegment4 = new SevenSegment(4);

        root.setCenter(topsegment);
        topsegment.draw();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Aryk L Anderson, CSCD 370 Lab 3, Spring 2016");
        alert.showAndWait();
    }

    private MenuBar buildMenus() {

        MenuBar menuBar = new MenuBar();
//
//        Menu fileMenu = new Menu("_File");
//
//        MenuItem quitMenuItem = new MenuItem("_Exit");
//        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
//        quitMenuItem.setOnAction(actionEvent -> Platform.exit());
//
//        MenuItem newMenuItem = new MenuItem("_New");
//        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
//        newMenuItem.setOnAction(actionEvent -> onNew());
//
//        MenuItem openMenuItem = new MenuItem("_Open");
//        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
//        openMenuItem.setOnAction(actionEvent -> onOpen());
//
//        MenuItem saveMenuItem = new MenuItem("_Save");
//        saveMenuItem.setOnAction(actionEvent -> onSave());
//        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
//
//        MenuItem saveAsMenuItem = new MenuItem("_Save As");
//        saveAsMenuItem.setOnAction(actionEvent -> onSaveAs());
//        saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
//
//        fileMenu.getItems().addAll(newMenuItem,
//                                   openMenuItem,
//                                   saveMenuItem,
//                                   saveAsMenuItem,
//                                   new SeparatorMenuItem(),
//                                   quitMenuItem);
//
//        String[] widthItems = {"_1 Pixel", "_4 Pixels", "_8 Pixels"};
//        String[] colorItems = {"_Black", "_Red", "_Green", "_Blue"};
//        Menu width = new Menu("_Width");
//        Menu color = new Menu("_Color");
//
//        for (String wString : widthItems) {
//            RadioMenuItem item = new RadioMenuItem(wString);
//            item.setOnAction(actionEvent -> onWidth(wString));
//            width.getItems().add(item);
//        }
//
//        for (String wString : colorItems) {
//            RadioMenuItem item = new RadioMenuItem(wString);
//            item.setOnAction(actionEvent -> onColor(wString));
//            color.getItems().add(item);
//        }
//
//        Menu helpMenu = new Menu("_Help");
//        MenuItem aboutMenuItem = new MenuItem("_About");
//        aboutMenuItem.setOnAction(actionEvent -> onAbout());
//        helpMenu.getItems().add(aboutMenuItem);
//
//        menuBar.getMenus().addAll(fileMenu, width, color, helpMenu);
        return menuBar;
    }
}
