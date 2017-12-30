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
    private ArrayList<Boolean> chosen;
    public Pawns(GridPane playground, ObservableList<Node> allChildren) {
        this.playground = playground;
        this.setAllChildren(allChildren);
        setChosen(new ArrayList<>());
        pawnsList=new ArrayList<>();
        for(int i=0;i<4;i++){
            getChosen().add(false);

        }
    }
    public void generatePawns()
    {
        setPawnsIndexes(new ArrayList<ArrayList<Integer>>());
        for(int i=0;i<4;i++){
            getPawnsIndexes().add(new ArrayList<Integer>());
            getPawnsIndexes().get(i).add((Integer)(i/2));
            getPawnsIndexes().get(i).add(9+i%2);

        }
        for (ArrayList<Integer> index: getPawnsIndexes()) {
            for(Node child: getAllChildren())
            {
                if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                    getPawnsList().add((ImageView)child);

                    break;
                }
            }
        }
    }


    public ArrayList<ArrayList<Integer>> getPawnsIndexes() {
        return pawnsIndexes;
    }

    public void setPawnsIndexes(ArrayList<ArrayList<Integer>> pawnsIndexes) {
        this.pawnsIndexes = pawnsIndexes;
    }

    public ArrayList<ImageView> getPawnsList() {
        return pawnsList;
    }

    public void setPawnsList(ArrayList<ImageView> pawnsList) {
        this.pawnsList = pawnsList;
    }

    public ArrayList<Boolean> getChosen() {
        return chosen;
    }

    public void setChosen(ArrayList<Boolean> chosen) {
        this.chosen = chosen;
    }

    public ObservableList<Node> getAllChildren() {
        return allChildren;
    }

    public void setAllChildren(ObservableList<Node> allChildren) {
        this.allChildren = allChildren;
    }
}
