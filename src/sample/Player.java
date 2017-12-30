package sample;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

import static java.lang.Math.min;

public class Player {
    private Pawns playerPawns;
    private Path playerPath;
    private int number=6;
    private PawnType color;

    public Player(Pawns playerPawns, Path playerPath ,PawnType color) {
        this.playerPawns = playerPawns;
        this.playerPath = playerPath;
        this.color=color;
    }
    public void swipePath(Integer n)
    {
        ArrayList<ArrayList<Integer>> newPathIndex=new ArrayList<>();
        ArrayList<ImageView> newPath=new ArrayList<>();
        for(int i=0;i<40;i++)
        {
            newPathIndex.add(playerPath.getPathIndexes().get((i+n)%40));
            newPath.add(playerPath.getPath().get((i+n)%40));

        }
        playerPath.setPathIndexes(newPathIndex);
        playerPath.setPath(newPath);
    }
    public void adjustPawnsandPath()
    {
        switch (color)
        {
            case BLUE:
            {
                swipePath(10);
                playerPawns.setPawnsIndexes(new ArrayList<ArrayList<Integer>>());
                for(int i=0;i<4;i++){
                    playerPawns.getPawnsIndexes().add(new ArrayList<Integer>());
                    playerPawns.getPawnsIndexes().get(i).add((Integer)(9+(i/2)));
                    playerPawns.getPawnsIndexes().get(i).add(9+i%2);

                }
                playerPawns.setPawnsList(new ArrayList<>());
                for (ArrayList<Integer> index: playerPawns.getPawnsIndexes()) {
                    for(Node child: playerPawns.getAllChildren())
                    {
                        if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                            playerPawns.getPawnsList().add((ImageView)child);

                            break;
                        }
                    }
                }
                break;
            }
            case GREEN:
            {
                swipePath(20);
                playerPawns.setPawnsIndexes(new ArrayList<ArrayList<Integer>>());
                for(int i=0;i<4;i++){
                    playerPawns.getPawnsIndexes().add(new ArrayList<Integer>());
                    playerPawns.getPawnsIndexes().get(i).add((Integer)(9+(i/2)));
                    playerPawns.getPawnsIndexes().get(i).add(i%2);

                }
                playerPawns.setPawnsList(new ArrayList<>());
                for (ArrayList<Integer> index: playerPawns.getPawnsIndexes()) {
                    for(Node child: playerPawns.getAllChildren())
                    {
                        if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                            playerPawns.getPawnsList().add((ImageView)child);

                            break;
                        }
                    }
                }
                break;
            }
            case YELLOW:
            {
                swipePath(30);
                playerPawns.setPawnsIndexes(new ArrayList<ArrayList<Integer>>());
                for(int i=0;i<4;i++){
                    playerPawns.getPawnsIndexes().add(new ArrayList<Integer>());
                    playerPawns.getPawnsIndexes().get(i).add((Integer)(i/2));
                    playerPawns.getPawnsIndexes().get(i).add(i%2);

                }
                playerPawns.setPawnsList(new ArrayList<>());
                for (ArrayList<Integer> index: playerPawns.getPawnsIndexes()) {
                    for(Node child: playerPawns.getAllChildren())
                    {
                        if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                            playerPawns.getPawnsList().add((ImageView)child);

                            break;
                        }
                    }
                }
                break;
            }
        }
        for (int i = 0; i < playerPawns.getPawnsList().size(); i++) {
            int finalI = i;
            playerPawns.getPawnsList().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    playerPawns.getChosen().set(finalI, true);
                    event.consume();
                }
            });
        }
    }
    public void startPlay()
    {

            for (int i = 0; i < playerPawns.getPawnsList().size(); i++) {

                if (playerPawns.getChosen().get(i) == true) {
                    System.out.println("In");
                    ImageView pawn = playerPawns.getPawnsList().get(i);
                    Boolean ok = false;
                    ImageView to_where = null;

                    for (int j = 0; j < playerPath.getPath().size(); j++) {
                        if (playerPath.getPath().get(j) == pawn) {
                            to_where = playerPath.getPath().get(min(j + number,playerPath.getPath().size()-1));

                            ok = true;
                            break;
                        }
                    }
                    if (ok == false) {
                        ImageView from=playerPath.getPath().get(0);
                        playerPath.getPath().set(0, pawn);
                        move(pawn, from);
                    } else {
                        Integer from = playerPath.getPath().indexOf(pawn);
                        playerPath.getPath().set(playerPath.getPath().indexOf(to_where), pawn);
                        playerPath.getPath().set(from, to_where);
                        move(pawn, to_where);
                    }
                    System.out.println("Moved");
                    playerPawns.getChosen().set(i, false);
                }
            }
        }

    private void move(ImageView pawn,ImageView place)
    {
        Integer pawn_x=GridPane.getRowIndex(pawn);
        Integer pawn_y=GridPane.getColumnIndex(pawn);
        Integer place_x=GridPane.getRowIndex(place);
        Integer place_y=GridPane.getColumnIndex(place);
        GridPane.setRowIndex(pawn,place_x);
        GridPane.setColumnIndex(pawn,place_y);
        GridPane.setRowIndex(place,pawn_x);
        GridPane.setColumnIndex(place,pawn_y);
    }
}
