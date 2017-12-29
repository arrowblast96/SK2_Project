package sample;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
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
        path=new ArrayList<ImageView>();
        freeField=new ArrayList<ImageView>();
    }

    public void generatePath()
    {
        pathIndexes=new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<40;i++){
            pathIndexes.add(new ArrayList<Integer>());

        }
        //red
        for(int i=0;i<5;i++)
        {
            pathIndexes.get(i).add(i);
            pathIndexes.get(i).add(6);
        }
        for(int i=0;i<4;i++)
        {
            pathIndexes.get(i+5).add(4);
            pathIndexes.get(i+5).add(7+i);
        }
        pathIndexes.get(9).add(5);
        pathIndexes.get(9).add(10);
        //blue
        for(int i=0;i<5;i++)
        {
            pathIndexes.get(i+10).add(6);
            pathIndexes.get(i+10).add(10-i);
        }
        for(int i=0;i<4;i++)
        {
            pathIndexes.get(i+15).add(7+i);
            pathIndexes.get(i+15).add(6);
        }
        pathIndexes.get(19).add(10);
        pathIndexes.get(19).add(5);
        //green
        for(int i=0;i<5;i++)
        {
            pathIndexes.get(i+20).add(10-i);
            pathIndexes.get(i+20).add(4);
        }
        for(int i=0;i<4;i++)
        {
            pathIndexes.get(i+25).add(6);
            pathIndexes.get(i+25).add(3-i);
        }
        pathIndexes.get(29).add(5);
        pathIndexes.get(29).add(0);
        //yellow
        for(int i=0;i<5;i++)
        {
            pathIndexes.get(i+30).add(4);
            pathIndexes.get(i+30).add(i);
        }
        for(int i=0;i<4;i++)
        {
            pathIndexes.get(i+35).add(3-i);
            pathIndexes.get(i+35).add(4);
        }
        pathIndexes.get(39).add(0);
        pathIndexes.get(39).add(5);
        for (ArrayList<Integer> index: pathIndexes) {
          for(Node child: allChildren)
          {
                  if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                      path.add((ImageView)child);
                  }
          }
          }
    }
    public void generateFreeFields()
    {
        freeFieldIndexes=new ArrayList<ArrayList<Integer>>();
        for(int i=0;i<4;i++){
            freeFieldIndexes.add(new ArrayList<Integer>());
            freeFieldIndexes.get(i).add(i+1);
            freeFieldIndexes.get(i).add(5)

        }
        for (ArrayList<Integer> index: freeFieldIndexes) {
            for(Node child: allChildren)
            {
                if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                    freeField.add((ImageView)child);
                }
            }
        }

    }


}
