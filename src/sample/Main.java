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

import java.net.Socket;
import java.util.ArrayList;

public class Main extends Application {

    private GridPane playground;
    private ObservableList<Node> allChildren;
    private ImageView kolejka;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (Parent) loader.load();
        Controller controller = (Controller) loader.getController();


        Scene scene = new Scene(root, 800, 800);
        playground = (GridPane) loader.getNamespace().get("playground");
        System.out.println(playground);
        allChildren = playground.getChildren();
        kolejka=(ImageView) loader.getNamespace().get("kolejka");

        Path path = new Path(playground, allChildren);

        path.generatePath();
        path.generateFreeFields();
        Pawns pawns = new Pawns(playground, allChildren);
        pawns.generatePawns();

        Player player = new Player(pawns, path,PawnType.RED,new Socket("localhost",1234));
        player.adjustPawnsandPath();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);



        TCP_Client client=new TCP_Client(player,kolejka);




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

                            primaryStage.show();

                        }
                    });
                }
            }
        };

        renderer.setDaemon(true);
        renderer.start();
        Thread game=new Thread(client);
        game.start();









    }
    public static void main(String[] args) {
        launch(args);
    }
}
