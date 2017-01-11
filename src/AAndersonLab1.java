import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AAndersonLab1 extends Application implements ChangeListener<String>{

    private Label status;
    private ImageView view;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        this.view = new ImageView(new Image("images/logo.png"));

        BorderPane root = new BorderPane();
        root.setCenter(this.view);
        root.setTop(buildMenus());

        this.status = new Label("Everything is Copacetic");
        ToolBar toolBar = new ToolBar(this.status);
        root.setBottom(toolBar);

        ListView<String> lists = new ListView<>();
        String[] tList = {"First Album", "Cindy", "Fred", "Kate", "Keith", "Matt", "Rickey"};
        lists.setItems(FXCollections.observableArrayList(tList));
        lists.getSelectionModel().selectedItemProperty().addListener(this);
        lists.setPrefWidth(this.computeStringWidth("First Album"));
        root.setLeft(lists);

        primaryStage.setScene(new Scene(root, 350, 300));
        primaryStage.show();
    }

    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Aryk L Anderson, CSCD 370 Lab 1, Spring 2016");
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

    private void setView(String imageUrl) {
        if (this.view == null)
            this.view = new ImageView(new Image(imageUrl));
        else
            this.view.setImage(new Image(imageUrl));
    }

    private double computeStringWidth(String s) {
        final Text text = new Text(s);
        Scene goo = new Scene(new Group(text));
        text.applyCss();
        return text.getLayoutBounds().getWidth() + 5;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

        switch(newValue) {
            //"images/cindy.png", "images/fred.png", "images/kate.png", "images/keith.png", "images/logo.png", "images/matt.png", "images/rickey.png"
            case "First Album":
                this.setStatus("First Album, 1979");
                this.setView("images/logo.png");
                break;
            case "Cindy":
                this.setStatus("Cindy Wildon (Percussion since 1976)");
                this.setView("images/cindy.png");
                break;
            case "Fred":
                this.setStatus("Fred Schneider(Vocals, Cowbell, since 1976)");
                this.setView("images/fred.png");
                break;
            case "Kate":
                this.setStatus("Kate Pierson (Organ since 1976)");
                this.setView("images/kate.png");
                break;
            case "Keith":
                this.setStatus("Keith Strickland (Drums, Guitar, since 1976)");
                this.setView("images/keith.png");
                break;
            case "Matt":
                this.setStatus("Matt Flynn (Touring, Drums, prior to 2004)");
                this.setView("images/matt.png");
                break;
            case "Rickey":
                this.setStatus("Ricky Wilson, deceased (Bass, 1976-1985)");
                this.setView("images/rickey.png");
                break;
        }
    }
}
