package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.Socket;

public class Main extends Application {

    private GridPane playground;
    private ObservableList<Node> allChildren;
    private ImageView kolejka;
    private ImageView dice;

    private TextField server;



    private TextField port;

    private Button poczatek;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("popup.fxml"));
        Parent root = (Parent) loader.load();
        ControllerPopUp controllerpop = (ControllerPopUp) loader.getController();
        Scene popup = new Scene(root, 400, 200);
        server=(TextField) loader.getNamespace().get("server");
        port=(TextField) loader.getNamespace().get("port");
        poczatek=(Button) loader.getNamespace().get("poczatek");




       loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        root = (Parent) loader.load();
        Controller controller = (Controller) loader.getController();


        Scene scene = new Scene(root, 800, 800);
        playground = (GridPane) loader.getNamespace().get("playground");
        System.out.println(playground);
        allChildren = playground.getChildren();
        kolejka=(ImageView) loader.getNamespace().get("kolejka");
        dice=(ImageView) loader.getNamespace().get("dice");
        primaryStage.setScene(popup);
        poczatek.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Path path = new Path(playground, allChildren);

                path.generatePath();
                path.generateFreeFields();
                Pawns pawns = new Pawns(playground, allChildren, 0);
                pawns.generatePawns();

                Player player = null;
                try {
                    player = new Player(pawns, path, PawnType.RED,new Socket(server.getText(),Integer.parseInt(port.getText())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.adjustPawnsandPath();

                primaryStage.setTitle("Chinczyk");
                primaryStage.setScene(scene);


                TCP_Client client= null;
                try {
                    client = new TCP_Client(player,kolejka,dice);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Thread game=new Thread(client);
                game.start();
                event.consume();
            }
        });


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












    }
    public static void main(String[] args) {
        launch(args);
    }
}
