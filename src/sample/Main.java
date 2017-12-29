package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;

public class Main extends Application {

    private GridPane playground;
    private ObservableList<Node> allChildren;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent)loader.load();
        Controller controller = (Controller)loader.getController();


        Scene scene = new Scene(root,800,800);
        playground=(GridPane)loader.getNamespace().get("playground");
        System.out.println(playground);
        allChildren=playground.getChildren();
        Path path=new Path(playground,allChildren);

        path.generatePath();
        path.generateFreeFields();


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);



        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
