package sample;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class Path {
    private GridPane playground;
    private ObservableList<Node> allChildren;

    private ArrayList<ArrayList<Integer>> pathIndexes;
    private ArrayList<ArrayList<Integer>> freeFieldIndexes;
    private ArrayList<ImageView> path;
    private ArrayList<ImageView> freeField;
    public Path(GridPane playground, ObservableList<Node> allChildren) {
        this.playground = playground;
        this.allChildren = allChildren;
        setPath(new ArrayList<ImageView>());
        setFreeField(new ArrayList<ImageView>());
    }

    public void generatePath()
    {
        setPathIndexes(new ArrayList<ArrayList<Integer>>());
        for(int i=0;i<40;i++){
            getPathIndexes().add(new ArrayList<Integer>());

        }
        //red
        for(int i=0;i<5;i++)
        {
            getPathIndexes().get(i).add(i);
            getPathIndexes().get(i).add(6);
        }
        for(int i=0;i<4;i++)
        {
            getPathIndexes().get(i+5).add(4);
            getPathIndexes().get(i+5).add(7+i);
        }
        getPathIndexes().get(9).add(5);
        getPathIndexes().get(9).add(10);
        //blue
        for(int i=0;i<5;i++)
        {
            getPathIndexes().get(i+10).add(6);
            getPathIndexes().get(i+10).add(10-i);
        }
        for(int i=0;i<4;i++)
        {
            getPathIndexes().get(i+15).add(7+i);
            getPathIndexes().get(i+15).add(6);
        }
        getPathIndexes().get(19).add(10);
        getPathIndexes().get(19).add(5);
        //green
        for(int i=0;i<5;i++)
        {
            getPathIndexes().get(i+20).add(10-i);
            getPathIndexes().get(i+20).add(4);
        }
        for(int i=0;i<4;i++)
        {
            getPathIndexes().get(i+25).add(6);
            getPathIndexes().get(i+25).add(3-i);
        }
        getPathIndexes().get(29).add(5);
        getPathIndexes().get(29).add(0);
        //yellow
        for(int i=0;i<5;i++)
        {
            getPathIndexes().get(i+30).add(4);
            getPathIndexes().get(i+30).add(i);
        }
        for(int i=0;i<4;i++)
        {
            getPathIndexes().get(i+35).add(3-i);
            getPathIndexes().get(i+35).add(4);
        }
        getPathIndexes().get(39).add(0);
        getPathIndexes().get(39).add(5);
        for (ArrayList<Integer> index: getPathIndexes()) {
          for(Node child: allChildren)
          {
                  if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                      getPath().add((ImageView)child);
                  }
          }
          }

    }
    public void generateFreeFields()
    {
        setFreeFieldIndexes(new ArrayList<ArrayList<Integer>>());
        for(int i=0;i<4;i++){
            getFreeFieldIndexes().add(new ArrayList<Integer>());
            getFreeFieldIndexes().get(i).add(i+1);
            getFreeFieldIndexes().get(i).add(5);

        }
        for (ArrayList<Integer> index: getFreeFieldIndexes()) {
            for(Node child: allChildren)
            {
                if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                    getFreeField().add((ImageView)child);
                }
            }
        }

    }


    public ArrayList<ArrayList<Integer>> getPathIndexes() {
        return pathIndexes;
    }

    public void setPathIndexes(ArrayList<ArrayList<Integer>> pathIndexes) {
        this.pathIndexes = pathIndexes;
    }

    public ArrayList<ArrayList<Integer>> getFreeFieldIndexes() {
        return freeFieldIndexes;
    }

    public void setFreeFieldIndexes(ArrayList<ArrayList<Integer>> freeFieldIndexes) {
        this.freeFieldIndexes = freeFieldIndexes;
    }

    public ArrayList<ImageView> getPath() {
        return path;
    }

    public void setPath(ArrayList<ImageView> path) {
        this.path = path;
    }

    public ArrayList<ImageView> getFreeField() {
        return freeField;
    }

    public void setFreeField(ArrayList<ImageView> freeField) {
        this.freeField = freeField;
    }
}
