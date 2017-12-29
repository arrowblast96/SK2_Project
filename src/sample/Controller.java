package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller{
    @FXML
    private GridPane playground;
    @FXML
    private VBox box;


    public GridPane getPlayground() {
        return playground;
    }

    public void setPlayground(GridPane playground) {
        this.playground = playground;
    }


    public VBox getBox() {
        return box;
    }

    public void setBox(VBox box) {
        this.box = box;
    }
}
