package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControllerPopUp {


    @FXML
    private TextField server;


    @FXML
    private TextField port;
    @FXML
    private Button poczatek;


    public TextField getServer() {
        return server;
    }

    public void setServer(TextField server) {
        this.server = server;
    }

    public TextField getPort() {
        return port;
    }

    public void setPort(TextField port) {
        this.port = port;
    }

    public Button getPoczatek() {
        return poczatek;
    }

    public void setPoczatek(Button poczatek) {
        this.poczatek = poczatek;
    }
}
