package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


import javax.imageio.ImageIO;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;

import static sample.PawnType.BLUE;

public class TCP_Client implements Runnable{



    private Player player;
    private BufferedReader reader;
    private PrintWriter writer;
    private String message;
    private ImageView kolejka;
    private ImageView dice;
    private ImageView pionki;
    private ArrayList<ImageView> onDice;
    public TCP_Client(Player player,ImageView kolejka, ImageView dice,ImageView kolor) throws Exception{
        this.player = player;
        this.kolejka = kolejka;
        this.dice=dice;
        this.pionki=kolor;
        reader=new BufferedReader(new InputStreamReader(player.getSocket().getInputStream()));
        writer = new PrintWriter(player.getSocket().getOutputStream(), true);
        onDice=new ArrayList<>();
        onDice.add(new ImageView("file:/home/osboxes/Desktop/SK2_Project/Pics/one.jpg"));
        onDice.add(new ImageView("file:/home/osboxes/Desktop/SK2_Project/Pics/two.jpg"));
        onDice.add(new ImageView("file:/home/osboxes/Desktop/SK2_Project/Pics/three.jpg"));
        onDice.add(new ImageView("file:/home/osboxes/Desktop/SK2_Project/Pics/four.jpg"));
        onDice.add(new ImageView("file:/home/osboxes/Desktop/SK2_Project/Pics/five.jpg"));
        onDice.add(new ImageView("file:/home/osboxes/Desktop/SK2_Project/Pics/six.jpg"));

    }
    public void close() throws IOException {
        player.getSocket().close();
    }
    public void numberOnDice()
    {
        dice.setImage(onDice.get(player.getNumber()-1).getImage());
        System.out.println(dice.getImage().getUrl());

    }
    public ArrayList<ArrayList<Integer>> swipePath(Integer n, ArrayList<ArrayList<Integer>> tab)
    {
        ArrayList<ArrayList<Integer>> newPathIndex=new ArrayList<>();

        for(int i=0;i<40;i++)
        {
            newPathIndex.add(tab.get((i+n)%40));
            System.out.println(newPathIndex.get(i));

        }
        return newPathIndex;

    }
    public void whichDice()
    {
        switch (player.getColor())
        {
            case RED:
            {
                GridPane.setColumnIndex(dice,8);
                GridPane.setRowIndex(dice,2);
                break;
            }
            case BLUE:
            {
                GridPane.setColumnIndex(dice,8);
                GridPane.setRowIndex(dice,8);
                break;
            }
            case GREEN:
            {
                GridPane.setColumnIndex(dice,2);
                GridPane.setRowIndex(dice,8);
                break;
            }
            case YELLOW:
            {
                GridPane.setColumnIndex(dice,2);
                GridPane.setRowIndex(dice,2);
                break;
            }
        }
    }
    public String parsujDane()
    {
        String dane_do_przesłania=new String();
        dane_do_przesłania+="[";
        for(ArrayList<Integer> indexes: player.getPlayerPath().getPathIndexes())
        {
            dane_do_przesłania+="(";
            for (Integer i: indexes) {
                dane_do_przesłania+=i;
            }
            dane_do_przesłania+=")";
        }
        dane_do_przesłania+="]:[";
        for(ArrayList<Integer> indexes: player.getPlayerPath().getFreeFieldIndexes())
        {
            dane_do_przesłania+="(";
            for (Integer i: indexes) {
                dane_do_przesłania+=i;
            }
            dane_do_przesłania+=")";
        }
        dane_do_przesłania+="]:[";
        for(ArrayList<ArrayList<Integer>> indexes: player.getPlayerPawns().getAllPawnsIndexes())
        {
            dane_do_przesłania+="(";
            for (ArrayList<Integer> index: indexes) {
                dane_do_przesłania+="{";
                for (Integer i: index) {
                    dane_do_przesłania+=i;
                }
                dane_do_przesłania+="}";
            }
            dane_do_przesłania+=")";
        }
        dane_do_przesłania+="]";

        return dane_do_przesłania;
    }
    public void tlumaczDane(String message,int swipe)
    {
        String[] dane=message.split(":");
        ArrayList<ArrayList<Integer>> newPathIndexes=new ArrayList<>();
        ArrayList<ArrayList<Integer>> newFreeFieldIndexes=new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Integer>>> newAllPawnsIndexes=new ArrayList<>();
        char[] dane_sciezka=dane[0].toCharArray();
        for(int i = 1; i < dane_sciezka.length-1; i++)
        {

            if(dane_sciezka[i]==new String("(").charAt(0))
            {
                newPathIndexes.add(new ArrayList<>());

            }
            else if(((int) dane_sciezka[i])-48==0)
            {
                if((((int) dane_sciezka[i-1])-48<0 || ((int) dane_sciezka[i+1])-48<0) && ((int) dane_sciezka[i-1])-48!=1)
                {
                    int index = newPathIndexes.size()-1;
                    newPathIndexes.get(index).add(((int) dane_sciezka[i])-48);
                }
                else if(((int) dane_sciezka[i-1])-48==1 && ((int) dane_sciezka[i+1])-48<0 && newPathIndexes.get(newPathIndexes.size()-1).size()==1)
                {
                    int index = newPathIndexes.size()-1;
                    newPathIndexes.get(index).add(((int) dane_sciezka[i])-48);
                }
                else{
                    int index = newPathIndexes.size()-1;
                    int index_1 = newPathIndexes.get(index).size()-1;
                    newPathIndexes.get(index).set(index_1, newPathIndexes.get(index).get(index_1) * 10);
                }
            }
          else if(((int) dane_sciezka[i])-48>0 && ((int) dane_sciezka[i])-48<=9) {
                int index = newPathIndexes.size()-1;

                newPathIndexes.get(index).add(((int) dane_sciezka[i])-48);
            }
        }
        char[] dane_pola_wygrane=dane[1].toCharArray();
        for(int i = 1; i < dane_pola_wygrane.length-1; i++)
        {

            if(dane_pola_wygrane[i]==new String("(").charAt(0))
            {
                newFreeFieldIndexes.add(new ArrayList<>());


            }
            else if(((int) dane_pola_wygrane[i])-48==0)
            {
                if((((int) dane_pola_wygrane[i-1])-48<0 || ((int) dane_pola_wygrane[i+1])-48<0) && ((int) dane_pola_wygrane[i-1])-48!=1)
                {
                    int index = newFreeFieldIndexes.size()-1;
                    newFreeFieldIndexes.get(index).add(((int) dane_pola_wygrane[i])-48);
                }
                else if(((int) dane_pola_wygrane[i-1])-48==1 && ((int) dane_pola_wygrane[i+1])-48<0)
                {
                    int index = newFreeFieldIndexes.size()-1;
                    newFreeFieldIndexes.get(index).add(((int) dane_pola_wygrane[i])-48);
                }
                else{
                    int index = newFreeFieldIndexes.size()-1;
                    int index_1 = newFreeFieldIndexes.get(index).size()-1;
                    newFreeFieldIndexes.get(index).set(index_1, newFreeFieldIndexes.get(index).get(index_1) * 10);
                }
            }
            else if(((int) dane_pola_wygrane[i])-48>0 && ((int) dane_pola_wygrane[i])-48<=9) {
                int index = newFreeFieldIndexes.size()-1;

                newFreeFieldIndexes.get(index).add(((int) dane_pola_wygrane[i])-48);
            }
        }
        char[] dane_pionki=dane[2].toCharArray();

        for(int i = 1; i < dane_pionki.length-1; i++)
        {

            if(dane_pionki[i]==new String("(").charAt(0))
            {
                newAllPawnsIndexes.add(new ArrayList<>());


            }
            else if(dane_pionki[i]==new String("{").charAt(0))
            {
                int index = newAllPawnsIndexes.size()-1;
                newAllPawnsIndexes.get(index).add(new ArrayList<>());


            }
            else if(((int) dane_pionki[i])-48==0)
            {
                int index = newAllPawnsIndexes.size()-1;
                int index_1 = newAllPawnsIndexes.get(index).size()-1;
                if((((int) dane_pionki[i-1])-48<0 || ((int) dane_pionki[i+1])-48<0 || ((int) dane_pionki[i-1])==123 || ((int) dane_pionki[i+1])==125) && ((int) dane_pionki[i-1])-48!=1)
                {

                    newAllPawnsIndexes.get(index).get(index_1).add(((int) dane_pionki[i])-48);
                }
                else if(newAllPawnsIndexes.get(index).get(index_1).size()==1 && ((int) dane_pionki[i+1])==125)
                {
                    newAllPawnsIndexes.get(index).get(index_1).add(((int) dane_pionki[i])-48);
                }
                else{

                    int index_2 = newAllPawnsIndexes.get(index).get(index_1).size()-1;
                    newAllPawnsIndexes.get(index).get(index_1).set(index_2, newAllPawnsIndexes.get(index).get(index_1).get(index_2) * 10);
                }
            }
            else if(((int) dane_pionki[i])-48>0 && ((int) dane_pionki[i])-48<=9) {
                int index = newAllPawnsIndexes.size()-1;
                int index_1 = newAllPawnsIndexes.get(index).size()-1;
                newAllPawnsIndexes.get(index).get(index_1).add(((int) dane_pionki[i])-48);
            }
        }

        switch(player.getColor())
        {
            case RED:
            {
                switch (swipe)
                {
                    case 0:
                    {
                        break;
                    }
                    case 1:
                    {
                        newPathIndexes=swipePath(30,newPathIndexes);
                        break;
                    }
                    case 2:
                    {
                        newPathIndexes=swipePath(20,newPathIndexes);
                        break;
                    }
                    case 3:
                    {
                        newPathIndexes=swipePath(10,newPathIndexes);
                        break;
                    }
                }



                break;
            }


            case BLUE:
            {

                switch (swipe)
                {
                    case 0:
                    {
                        newPathIndexes=swipePath(10,newPathIndexes);
                        break;
                    }
                    case 1:
                    {

                        break;
                    }
                    case 2:
                    {
                        newPathIndexes=swipePath(30,newPathIndexes);
                        break;
                    }
                    case 3:
                    {
                        newPathIndexes=swipePath(20,newPathIndexes);
                        break;
                    }
                }



                break;



            }
            case GREEN:
            {

                switch (swipe)
                {
                    case 0:
                    {
                        newPathIndexes=swipePath(20,newPathIndexes);
                        break;
                    }
                    case 1:
                    {
                        newPathIndexes=swipePath(10,newPathIndexes);
                        break;
                    }
                    case 2:
                    {

                        break;
                    }
                    case 3:
                    {
                        newPathIndexes=swipePath(30,newPathIndexes);
                        break;
                    }
                }



                break;



            }
            case YELLOW:
            {

                switch (swipe)
                {
                    case 0:
                    {
                        newPathIndexes=swipePath(30,newPathIndexes);
                        break;
                    }
                    case 1:
                    {
                        newPathIndexes=swipePath(20,newPathIndexes);
                        break;
                    }
                    case 2:
                    {
                        newPathIndexes=swipePath(10,newPathIndexes);
                        break;
                    }
                    case 3:
                    {

                        break;
                    }
                }
                break;



            }
        }

        for(int i=0;i<newPathIndexes.size();i++)
        {
            if(newPathIndexes.get(i)!=player.getPlayerPath().getPathIndexes().get(i))
            {
                Integer lostIndex;
                if((lostIndex=player.getPlayerPath().getPathIndexes().indexOf(newPathIndexes.get(i)))!=-1) {
                    ImageView from = player.getPlayerPath().getPath().get(lostIndex);
                    ImageView pawn = player.getPlayerPath().getPath().get(i);
                    player.getPlayerPath().getPath().set(i,from);
                    player.getPlayerPath().getPath().set(lostIndex,pawn);
                }
                else if((lostIndex=player.getPlayerPath().getFreeFieldIndexes().indexOf(newPathIndexes.get(i)))!=-1) {
                    ImageView from = player.getPlayerPath().getFreeField().get(lostIndex);
                    ImageView pawn = player.getPlayerPath().getPath().get(i);
                    player.getPlayerPath().getPath().set(i,from);
                    player.getPlayerPath().getFreeField().set(lostIndex,pawn);
                }
                else if((lostIndex=player.getPlayerPawns().getAllPawnsIndexes().get(player.getPlayerPawns().getColor()).indexOf(newPathIndexes.get(i)))!=-1) {
                    ImageView from = player.getPlayerPawns().getPawnsList().get(lostIndex);
                    ImageView pawn = player.getPlayerPath().getPath().get(i);
                    player.getPlayerPath().getPath().set(i,from);
                    player.getPlayerPawns().getPawnsList().set(lostIndex,pawn);
                }
            }


        }
        player.getPlayerPath().setPathIndexes(newPathIndexes);
        for(ImageView image : player.getPlayerPath().getPath())
        {
            int index=player.getPlayerPath().getPath().indexOf(image);

            GridPane.setRowIndex(image,player.getPlayerPath().getPathIndexes().get(index).get(0));
            GridPane.setColumnIndex(image,player.getPlayerPath().getPathIndexes().get(index).get(1));
        }

        /*for(int i=0;i<newFreeFieldIndexes.size();i++)
        {
            if(newFreeFieldIndexes.get(i)!=player.getPlayerPath().getPathIndexes().get(i))
            {
                Integer lostIndex;
                if((lostIndex=player.getPlayerPath().getPathIndexes().indexOf(newFreeFieldIndexes.get(i)))!=-1) {
                    ImageView from = player.getPlayerPath().getPath().get(lostIndex);
                    ImageView pawn = player.getPlayerPath().getFreeField().get(i);
                    player.getPlayerPath().getFreeField().set(i,from);
                    player.getPlayerPath().getPath().set(lostIndex,pawn);
                }
                else if((lostIndex=player.getPlayerPath().getFreeFieldIndexes().indexOf(newFreeFieldIndexes.get(i)))!=-1) {
                    ImageView from = player.getPlayerPath().getFreeField().get(lostIndex);
                    ImageView pawn = player.getPlayerPath().getFreeField().get(i);
                    player.getPlayerPath().getFreeField().set(i,from);
                    player.getPlayerPath().getFreeField().set(lostIndex,pawn);
                }
                else if((lostIndex=player.getPlayerPawns().getAllPawnsIndexes().get(player.getPlayerPawns().getColor()).indexOf(newFreeFieldIndexes.get(i)))!=-1) {
                    ImageView from = player.getPlayerPawns().getPawnsList().get(lostIndex);
                    ImageView pawn = player.getPlayerPath().getFreeField().get(i);
                    player.getPlayerPath().getFreeField().set(i,from);
                    player.getPlayerPawns().getPawnsList().set(lostIndex,pawn);
                }
            }


        }*/
       for(int i=0;i<newAllPawnsIndexes.size();i++)
        {
            for(int j=0;j<newAllPawnsIndexes.get(i).size();j++)
                if(player.getPlayerPath().getPathIndexes().contains(newAllPawnsIndexes.get(i).get(j)))
                {
                    Integer lostIndex;
                    if((lostIndex=player.getPlayerPawns().getAllPawnsIndexes().get(player.getPlayerPawns().getColor()).indexOf(newAllPawnsIndexes.get(i).get(j)))!=-1) {
                    ImageView from = player.getPlayerPawns().getPawnsList().get(lostIndex);
                    ImageView pawn = player.getPlayerPawns().getPawnsList().get(i);
                    player.getPlayerPawns().getPawnsList().set(i,from);
                    player.getPlayerPawns().getPawnsList().set(lostIndex,pawn);

                }
                    else if((lostIndex=player.getPlayerPath().getPathIndexes().indexOf(newAllPawnsIndexes.get(i).get(j)))!=-1) {
                        ImageView from = player.getPlayerPath().getPath().get(lostIndex);
                        ImageView pawn = player.getPlayerPawns().getPawnsList().get(i);
                        player.getPlayerPawns().getPawnsList().set(i,from);
                        player.getPlayerPath().getPath().set(lostIndex,pawn);

                    }
                     else if((lostIndex=player.getPlayerPath().getFreeFieldIndexes().indexOf(newAllPawnsIndexes.get(i).get(j)))!=-1) {
                        ImageView from = player.getPlayerPath().getFreeField().get(lostIndex);
                        ImageView pawn = player.getPlayerPawns().getPawnsList().get(i);
                        player.getPlayerPawns().getPawnsList().set(i,from);
                        player.getPlayerPath().getFreeField().set(lostIndex,pawn);

                    }

                }


        }




        //player.getPlayerPath().setFreeFieldIndexes(newFreeFieldIndexes);
        player.getPlayerPawns().setAllPawnsIndexes(newAllPawnsIndexes);



        /*for(ImageView image : player.getPlayerPath().getFreeField())
        {
            int index=player.getPlayerPath().getFreeField().indexOf(image);
            GridPane.setRowIndex(image,player.getPlayerPath().getFreeFieldIndexes().get(index).get(0));
            GridPane.setColumnIndex(image,player.getPlayerPath().getFreeFieldIndexes().get(index).get(1));
        }*/
        int i=0;


        for(ImageView image : player.getPlayerPawns().getAllPawnsList())
        {
            if(i==4)
            {
                i=0;
            }
            System.out.println("Pionki");
            int index=player.getPlayerPawns().getAllPawnsList().indexOf(image);
            System.out.println(newAllPawnsIndexes.get(index/4).get(i));

            GridPane.setRowIndex(image,player.getPlayerPawns().getAllPawnsIndexes().get(index/4).get(i).get(0));
            GridPane.setColumnIndex(image,player.getPlayerPawns().getAllPawnsIndexes().get(index/4).get(i).get(1));
            i++;
        }


    }

    @Override
    public void run() {

        char[] kolor=new char[1];
        try {
            reader.read(kolor,0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message=new String(kolor);
        int swipe=0;
        int kolej=0;
        switch (message)
        {
            case "0":
            {
                player.setColor(PawnType.RED);
                pionki.setImage(new Image("file:/home/osboxes/Desktop/SK2_Project/Pics/red.png"));
                player.adjustPawnsandPath();
                System.out.println(player.getColor());
                kolej=0;
                break;

            }
            case "1":
            {
                player.setColor(BLUE);
                pionki.setImage(new Image("file:/home/osboxes/Desktop/SK2_Project/Pics/blue.png"));
                player.adjustPawnsandPath();
                System.out.println(player.getColor());
                kolej=1;
                break;

            }
            case "2":
            {
                player.setColor(PawnType.GREEN);
                pionki.setImage(new Image("file:/home/osboxes/Desktop/SK2_Project/Pics/green.png"));
                player.adjustPawnsandPath();
                System.out.println(player.getColor());
                kolej=2;
                break;
            }
            case "3":
            {
                player.setColor(PawnType.YELLOW);
                pionki.setImage(new Image("file:/home/osboxes/Desktop/SK2_Project/Pics/yellow.png"));
                player.adjustPawnsandPath();
                System.out.println(player.getColor());
                kolej=3;
                break;
            }

        }


        while (true) {
            char[] dane=new char[300];
            char[] liczba=new char[1];

            System.out.println("Start");


            try {
                reader.read(dane,0,300);
            } catch (IOException e) {
                e.printStackTrace();
            }

            message =new String(dane);

            System.out.println(message);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(message.contains("Twoja tura")) {

                kolejka.setVisible(true);
                writer.print("Wysłano");
                System.out.println("Wysłano");
                writer.flush();

                try {
                    reader.read(liczba, 0, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                message = new String(liczba);
                System.out.println("Wylosowana liczba -> " + message);
                System.out.println();
                player.setNumber(Integer.parseInt(message));
                Integer ile= player.getNumber();
                for (int i = 0; i < player.getPlayerPawns().getClickList().size(); i++) {
                    int finalI = i;
                    System.out.println(i);
                    player.getPlayerPawns().getClickList().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            player.getPlayerPawns().getChosen().set(finalI, true);
                            event.consume();
                        }
                    });
                }
                numberOnDice();
                whichDice();
                while (player.startPlay() == false) ;
                for (int i = 0; i < player.getPlayerPawns().getClickList().size(); i++) {

                    player.getPlayerPawns().getClickList().get(i).setOnMouseClicked(null);
                }

                kolejka.setVisible(false);
                if(ile!=6) {
                    System.out.println(parsujDane());
                    char[] end = parsujDane().toCharArray();
                    writer.print(end);

                    writer.flush();
                }

            }
            else
            {


               writer.print("Koniec");
                writer.flush();
                try {
                    reader.read(liczba,0,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                swipe=Integer.parseInt(new String(liczba));
                System.out.println(swipe);
                tlumaczDane(message, swipe);







            }


        }


    }

}
