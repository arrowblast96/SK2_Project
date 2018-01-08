package sample;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.min;

public class Player {
    private Pawns playerPawns;
    private ArrayList<Pawns> allPawns;
    private Path playerPath;
    private int number;
    private PawnType color;
    private Socket socket;

    public Player(Pawns playerPawns, Path playerPath, PawnType color, Socket socket) {
        this.playerPawns = playerPawns;
        this.playerPath = playerPath;

        this.color = color;
        this.socket = socket;
    }


    public void swipePath(Integer n)
    {
        ArrayList<ArrayList<Integer>> newPathIndex=new ArrayList<>();
        ArrayList<ImageView> newPath=new ArrayList<>();
        for(int i=0;i<40;i++)
        {
            newPathIndex.add(getPlayerPath().getPathIndexes().get((i+n)%40));
            newPath.add(getPlayerPath().getPath().get((i+n)%40));

        }
        getPlayerPath().setPathIndexes(newPathIndex);
        getPlayerPath().setPath(newPath);
    }
    public void adjustPawnsandPath()
    {
        switch (getColor())
        {
            case BLUE:
            {
                swipePath(10);
                getPlayerPawns().setColor(1);
                getPlayerPawns().setPawnsList(new ArrayList<>());
                getPlayerPawns().setClickList(new ArrayList<>());
                for (ArrayList<Integer> index: getPlayerPawns().getAllPawnsIndexes().get(1)) {
                    for(Node child: getPlayerPawns().getAllChildren())
                    {
                        if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                            getPlayerPawns().getPawnsList().add((ImageView)child);
                            getPlayerPawns().getClickList().add((ImageView)child);
                            break;
                        }
                    }
                }
                getPlayerPath().setFreeFieldIndexes(new ArrayList<ArrayList<Integer>>());
                for(int i=0;i<4;i++){
                    getPlayerPath().getFreeFieldIndexes().add(new ArrayList<Integer>());
                    getPlayerPath().getFreeFieldIndexes().get(i).add(5);
                    getPlayerPath().getFreeFieldIndexes().get(i).add(9-i);

                }
                getPlayerPath().setFreeField(new ArrayList<>());
                for (ArrayList<Integer> index: getPlayerPath().getFreeFieldIndexes()) {
                    for(Node child: getPlayerPath().getAllChildren())
                    {
                        if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                            getPlayerPath().getFreeField().add((ImageView)child);
                            getPlayerPawns().getClickList().add((ImageView)child);
                            break;
                        }
                    }
                }
                break;
            }
            case GREEN:
            {
                swipePath(20);
                getPlayerPawns().setColor(2);
                getPlayerPawns().setPawnsList(new ArrayList<>());
                getPlayerPawns().setClickList(new ArrayList<>());
                for (ArrayList<Integer> index: getPlayerPawns().getAllPawnsIndexes().get(2)) {
                    for(Node child: getPlayerPawns().getAllChildren())
                    {
                        if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                            getPlayerPawns().getPawnsList().add((ImageView)child);
                            getPlayerPawns().getClickList().add((ImageView)child);

                            break;
                        }
                    }
                }
                getPlayerPath().setFreeFieldIndexes(new ArrayList<ArrayList<Integer>>());
                for(int i=0;i<4;i++){
                    getPlayerPath().getFreeFieldIndexes().add(new ArrayList<Integer>());
                    getPlayerPath().getFreeFieldIndexes().get(i).add(9-i);
                    getPlayerPath().getFreeFieldIndexes().get(i).add(5);

                }
                getPlayerPath().setFreeField(new ArrayList<>());
                for (ArrayList<Integer> index: getPlayerPath().getFreeFieldIndexes()) {
                    for(Node child: getPlayerPath().getAllChildren())
                    {
                        if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                            getPlayerPath().getFreeField().add((ImageView)child);
                            getPlayerPawns().getClickList().add((ImageView)child);
                            break;
                        }
                    }
                }
                break;
            }
            case YELLOW:
            {
                swipePath(30);
                getPlayerPawns().setColor(3);
                getPlayerPawns().setPawnsList(new ArrayList<>());
                getPlayerPawns().setClickList(new ArrayList<>());
                for (ArrayList<Integer> index: getPlayerPawns().getAllPawnsIndexes().get(3)) {
                    for(Node child: getPlayerPawns().getAllChildren())
                    {
                        if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                            getPlayerPawns().getPawnsList().add((ImageView)child);
                            getPlayerPawns().getClickList().add((ImageView)child);

                            break;
                        }
                    }
                }
                getPlayerPath().setFreeFieldIndexes(new ArrayList<ArrayList<Integer>>());
                for(int i=0;i<4;i++){
                    getPlayerPath().getFreeFieldIndexes().add(new ArrayList<Integer>());
                    getPlayerPath().getFreeFieldIndexes().get(i).add(5);
                    getPlayerPath().getFreeFieldIndexes().get(i).add(1+i);

                }
                getPlayerPath().setFreeField(new ArrayList<>());
                for (ArrayList<Integer> index: getPlayerPath().getFreeFieldIndexes()) {
                    for(Node child: getPlayerPath().getAllChildren())
                    {
                        if(GridPane.getRowIndex(child)==index.get(0) && GridPane.getColumnIndex(child)==index.get(1)){
                            getPlayerPath().getFreeField().add((ImageView)child);
                            break;
                        }
                    }
                }
                break;
            }
        }

    }
    public boolean startPlay()
    {

        for (int i = 0; i < getPlayerPawns().getPawnsList().size(); i++) {

            if (getPlayerPawns().getChosen().get(i) == true) {


                System.out.println(getNumber());
                ImageView pawn = getPlayerPawns().getClickList().get(i);
                Boolean ok_path = false;

                ImageView to_where = null;


                if (getPlayerPath().getPath().contains(pawn)) {
                        System.out.println("On path");
                        to_where = getPlayerPath().getPath().get(min(getPlayerPath().getPath().indexOf(pawn) + getNumber(), getPlayerPath().getPath().size()-1));
                       if (!playerPawns.getClickList().contains(to_where)){
                       setNumber(getNumber() - (getPlayerPath().getPath().indexOf(to_where) - getPlayerPath().getPath().indexOf(pawn)));

                            ok_path = true;
                        }


                }

                System.out.println(ok_path);
                if (ok_path == false && getNumber()==6 && !getPlayerPath().getFreeField().contains(pawn)) {
                    System.out.println("On board");
                    ImageView from= getPlayerPath().getPath().get(0);

                    ArrayList<Integer> playerIndexes=getPlayerPawns().getAllPawnsIndexes().get(getPlayerPawns().getColor()).get(i);
                    ArrayList<Integer> fromIndexes=getPlayerPath().getPathIndexes().get(getPlayerPath().getPath().indexOf(from));
                    getPlayerPath().getPathIndexes().set(0,playerIndexes);
                    getPlayerPawns().getAllPawnsIndexes().get(getPlayerPawns().getColor()).set(i,fromIndexes);
                    getPlayerPawns().getPawnsList().set(i,from);

                    getPlayerPath().getPath().set(0,pawn);

                    move(pawn, from);
                }
                else if(ok_path==false && getPlayerPath().getFreeField().contains(pawn))
                {
                    System.out.println("On Free Field");
                    Integer pawnIndex= getPlayerPath().getFreeField().indexOf(pawn);
                    ArrayList<Integer> pawnIndexes=getPlayerPawns().getAllPawnsIndexes().get(playerPawns.getColor()).get(i);
                    if(getNumber() <=(getPlayerPath().getFreeField().size()-1)-pawnIndex)
                    {
                        ImageView to_what= getPlayerPath().getFreeField().get(pawnIndex+ getNumber());
                        ArrayList<Integer> to_whatIndexes=getPlayerPath().getFreeFieldIndexes().get(getPlayerPath().getFreeField().indexOf(to_what));
                        getPlayerPath().getFreeFieldIndexes().set(getPlayerPath().getFreeField().indexOf(to_what),pawnIndexes);
                        getPlayerPawns().getAllPawnsIndexes().get(getPlayerPawns().getColor()).set(i,to_whatIndexes);


                        getPlayerPath().getFreeField().set(pawnIndex+ getNumber(),pawn);
                        getPlayerPath().getFreeField().set(pawnIndex,to_what);
                        move(pawn,to_what);
                        setNumber(getNumber()-((getPlayerPath().getFreeField().size()-1)-pawnIndex));
                        System.out.println(getNumber());
                        if(getNumber() ==0)
                        {

                            System.out.println("Usuń pionek");
                            getPlayerPawns().getClickList().get(getPlayerPawns().getClickList().indexOf(pawn)).setOnMouseClicked(
                                    null
                            );
                            getPlayerPawns().getClickList().remove(pawn);
                            getPlayerPath().getFreeField().remove(getPlayerPath().getFreeField().size()-1);
                        }
                    }

                }
                else if(ok_path==true) {
                    System.out.println("Try");

                    Integer pawnIndex= getPlayerPath().getPath().indexOf(pawn);
                    Integer to_where_index= getPlayerPath().getPath().indexOf(to_where);
                    ArrayList<Integer> playerIndexes=getPlayerPawns().getAllPawnsIndexes().get(playerPawns.getColor()).get(i);
                    ArrayList<Integer> to_whereIndexes=getPlayerPath().getPathIndexes().get(to_where_index);
                    getPlayerPath().getPathIndexes().set(to_where_index,playerIndexes);
                    getPlayerPawns().getAllPawnsIndexes().get(playerPawns.getColor()).set(i,to_whereIndexes);

                    getPlayerPath().getPath().set(to_where_index,pawn);
                    getPlayerPath().getPath().set(pawnIndex,to_where);
                    move(pawn, to_where);
                    if(getNumber() >0 && to_where_index==getPlayerPath().getPath().size()-1)
                    {
                        ImageView from= getPlayerPath().getFreeField().get(getNumber() -1);
                         pawnIndex= getPlayerPath().getPath().indexOf(pawn);
                        Integer fromindex= getPlayerPath().getFreeField().indexOf(from);
                        playerIndexes=getPlayerPawns().getAllPawnsIndexes().get(playerPawns.getColor()).get(i);
                        ArrayList<Integer> fromIndexes=getPlayerPath().getFreeFieldIndexes().get(fromindex);

                        getPlayerPath().getFreeFieldIndexes().set(fromindex,playerIndexes);
                        getPlayerPawns().getAllPawnsIndexes().get(playerPawns.getColor()).set(i,fromIndexes);
                        getPlayerPath().getFreeField().set(fromindex,pawn);
                        getPlayerPath().getPath().set(pawnIndex,from);
                        move(pawn,from);
                        if(playerPath.getFreeField().indexOf(pawn)+1==playerPath.getFreeField().size())
                        {

                            pawn.setOnMouseClicked(
                                    null
                            );
                            getPlayerPawns().deletePawn(getPlayerPawns().getClickList().indexOf(pawn));
                            getPlayerPath().getFreeField().remove(getPlayerPath().getFreeField().size()-1);
                        }
                    }
                }
                System.out.println("Moved");
                getPlayerPawns().getChosen().set(i, false);
                return true;

            }

        }
        return false;

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

    public Pawns getPlayerPawns() {
        return playerPawns;
    }

    public void setPlayerPawns(Pawns playerPawns) {
        this.playerPawns = playerPawns;
    }

    public Path getPlayerPath() {
        return playerPath;
    }

    public void setPlayerPath(Path playerPath) {
        this.playerPath = playerPath;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public PawnType getColor() {
        return color;
    }

    public void setColor(PawnType color) {
        this.color = color;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
