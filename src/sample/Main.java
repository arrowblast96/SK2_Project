package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Main extends Application {

    private GridPane playground;
    private ObservableList<Node> allChildren;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent) loader.load();
        Controller controller = (Controller) loader.getController();


        Scene scene = new Scene(root, 800, 800);
        playground = (GridPane) loader.getNamespace().get("playground");
        System.out.println(playground);
        allChildren = playground.getChildren();


        Path path = new Path(playground, allChildren);

        path.generatePath();
        path.generateFreeFields();
        Pawns pawns = new Pawns(playground, allChildren);
        pawns.generatePawns();

        Player player = new Player(pawns, path,PawnType.GREEN);
        player.adjustPawnsandPath();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);

        primaryStage.show();
        Thread renderer = new Thread(){

            @Override
            public void run(){
                while (true) {
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException ex) {
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            player.startPlay();

                        }
                    });
                }
            }
        };

        renderer.setDaemon(true);
        renderer.start();



    }
    public static void main(String[] args) {
        launch(args);
    }
}
