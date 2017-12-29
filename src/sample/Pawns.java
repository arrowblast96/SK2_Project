package sample;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Pawns {
    private GridPane playground;
    private ObservableList<Node> allChildren;
    private ArrayList<ArrayList<Integer>> pawnsIndexes;
    private ArrayList<ImageView> pawnsList;
    public Pawns(GridPane playground, ObservableList<Node> allChildren) {
        this.playground = playground;
        this.allChildren = allChildren;
    }
    public void generatePawns()
    {
        
    }


}
