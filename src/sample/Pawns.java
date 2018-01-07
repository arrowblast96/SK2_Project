package sample;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Pawns {
    private GridPane playground;
    private ObservableList<Node> allChildren;




    private ArrayList<ArrayList<ArrayList<Integer>>> allPawnsIndexes;
    private ArrayList<ImageView> pawnsList;

    public ArrayList<ImageView> getClickList() {
        return clickList;
    }

    public void setClickList(ArrayList<ImageView> clickList) {
        this.clickList = clickList;
    }

    private ArrayList<ImageView> clickList;


    private ArrayList<ImageView> allPawnsList;
    private ArrayList<Boolean> chosen;



    private Integer color;
    public Pawns(GridPane playground, ObservableList<Node> allChildren, Integer color) {
        this.playground = playground;
        this.color = color;
        this.setAllChildren(allChildren);
        setChosen(new ArrayList<>());
        pawnsList=new ArrayList<>();
        clickList=new ArrayList<>();
        allPawnsList=new ArrayList<>();
        for(int i=0;i<4;i++){
            getChosen().add(false);

        }
    }
    public void generatePawns()
    {
        //default
        setAllPawnsIndexes(new ArrayList<>());
        getAllPawnsIndexes().add(new ArrayList<>());
        for(int i=0;i<4;i++){
            getAllPawnsIndexes().get(0).add(new ArrayList<Integer>());
            getAllPawnsIndexes().get(0).get(i).add((Integer)(i/2));
            getAllPawnsIndexes().get(0).get(i).add(9+i%2);

        }

        for (ArrayList<Integer> index: getAllPawnsIndexes().get(0)) {

            for(Node child: getAllChildren())
            {
                if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                    getPawnsList().add((ImageView)child);
                    getClickList().add((ImageView)child);
                    getAllPawnsList().add((ImageView)child);

                    break;
                }
            }
        }
        //blue
        getAllPawnsIndexes().add(new ArrayList<>());
        for(int i=0;i<4;i++){
            getAllPawnsIndexes().get(1).add(new ArrayList<Integer>());
            getAllPawnsIndexes().get(1).get(i).add((Integer)(9+(i/2)));
            getAllPawnsIndexes().get(1).get(i).add(9+i%2);

        }
        for (ArrayList<Integer> index: getAllPawnsIndexes().get(1)) {
            for(Node child: getAllChildren())
            {
                if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){

                    getAllPawnsList().add((ImageView)child);

                    break;
                }
            }
        }
        //green
        getAllPawnsIndexes().add(new ArrayList<>());
        for(int i=0;i<4;i++){
            getAllPawnsIndexes().get(2).add(new ArrayList<Integer>());
            getAllPawnsIndexes().get(2).get(i).add((Integer)(9+(i/2)));
            getAllPawnsIndexes().get(2).get(i).add(i%2);

        }
        for (ArrayList<Integer> index: getAllPawnsIndexes().get(2)) {
            for(Node child: getAllChildren())
            {
                if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){

                    getAllPawnsList().add((ImageView)child);

                    break;
                }
            }
        }
        //yellow
        getAllPawnsIndexes().add(new ArrayList<>());
        for(int i=0;i<4;i++){
            getAllPawnsIndexes().get(3).add(new ArrayList<Integer>());
            getAllPawnsIndexes().get(3).get(i).add((Integer)(i/2));
            getAllPawnsIndexes().get(3).get(i).add(i%2);

        }
        for (ArrayList<Integer> index: getAllPawnsIndexes().get(3)) {
            for(Node child: getAllChildren())
            {
                if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){

                    getAllPawnsList().add((ImageView)child);

                    break;
                }
            }
        }


    }

    public void deletePawn(Integer n)
    {

        pawnsList.remove(n);
        clickList.remove(n);
        chosen.remove(n);

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

    public ArrayList<ArrayList<ArrayList<Integer>>> getAllPawnsIndexes() {
        return allPawnsIndexes;
    }

    public void setAllPawnsIndexes(ArrayList<ArrayList<ArrayList<Integer>>> allPawnsIndexes) {
        this.allPawnsIndexes = allPawnsIndexes;
    }
    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
    public ArrayList<ImageView> getAllPawnsList() {
        return allPawnsList;
    }

    public void setAllPawnsList(ArrayList<ImageView> allPawnsList) {
        this.allPawnsList = allPawnsList;
    }
}
