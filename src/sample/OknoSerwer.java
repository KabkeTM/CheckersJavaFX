package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class OknoSerwer extends Application  {
    private static final String szczurekURL = "file:D:\\Programy\\Java\\projekt_warcaby_v2\\src\\sample\\background.jpg";
    Group layout = new Group();
    PionekClass[][] PionekClass = new PionekClass[8][8];
    Board[][] board = new Board[8][8];
    ArrayList<Pozycje> lista = new ArrayList<>();
    Server serwer = new Server(5000);
    public static BufferedReader reader1;

    int type2;
    int kolor_gracza = 1;


    public enum STATE{
        MENU,
        SINGLE,
        MULTI
    }

    public enum GRACZ{
        ORANGE,
        PINK
    }

    final int
            EMPTY = 0,
            ORANGE = 1,
            PINK = 2;

    public static STATE state = STATE.MENU;

    public static GRACZ gracz = GRACZ.ORANGE;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("warcaby serwer");

        Group MenuLayout = new Group();

        int start;
        for (int y = 0; y < 8; y++){
            if (y % 2 == 0){
                start = 0;
            }
            else
                start = 1;

            for (int x = start; x < 8; x += 2){
                Board tile = new Board(true, x * 100, y * 100);
                board[x][y] = tile;
                layout.getChildren().add(tile);
            }
        }

        for (int y = 0; y < 8; y++){
            if (y % 2 == 0){
                start = 1;
            }
            else
                start = 0;

            for (int x = start; x < 8; x += 2){
                Board tile = new Board(false, x * 100, y * 100);
                board[x][y] = tile;
                layout.getChildren().add(tile);
            }
        }

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (y <= 2 && (x + y) % 2 != 0) {
                    PionekClass pioneczek = new PionekClass(1, x * 100, y * 100);

                    PionekClass[x][y] = pioneczek;
                    PionekClass[x][y].co = ORANGE;

                    layout.getChildren().add(PionekClass[x][y]);
                }
                else if (y >= 5 && (x + y) % 2 != 0) {
                    PionekClass pioneczek = new PionekClass(2, x * 100, y * 100);

                    PionekClass[x][y] = pioneczek;
                    PionekClass[x][y].co = PINK;

                    layout.getChildren().add(PionekClass[x][y]);
                }
                else {
                    PionekClass pioneczek = new PionekClass();

                    PionekClass[x][y] = pioneczek;
                    PionekClass[x][y].co = EMPTY;
                }
            }
        }

        layout.setOnMouseClicked(event -> {
            int col = (int) ((event.getX() - 2) / 100);
            int row = (int) ((event.getY() - 2) / 100);

            if (col >= 0 && col < 8 && row >= 0 && row < 8 && OknoSerwer.state == STATE.MULTI) {
                System.out.println("kliknieto na ta kol " + col + "wiersz " + row);
                Pozycje poz = new Pozycje(col, row);
                try {
                    ilosc_klikow(poz);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                System.out.println("kliknieto na ta kol " + col + "wiersz " + row);
                Pozycje poz = new Pozycje(col, row);
                try {
                    ilosc_klikow(poz);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Menu vbox = new Menu(
                new MenuItem("Singleplayer"),
                new MenuItem("Multiplayer"),
                new MenuItem("Exit")
        );

        vbox.setTranslateX(300);
        vbox.setTranslateY(350);
        MenuLayout.getChildren().add(vbox);

        MenuLayout.setOnMouseClicked(event -> {
            double mx = event.getX();
            double my = event.getY();
            System.out.println(mx +""+ my);
            if(mx >= 300 && mx <= 500){
                if(my >= 350 && my <= 380){
                    OknoSerwer.state = STATE.SINGLE;
                    Scene scene = new Scene(layout, 800, 800);
                    primaryStage.setScene(scene);

                }

                if(my >= 381 && my <= 410){
                    OknoSerwer.state = STATE.MULTI;
                    Scene scene = new Scene(layout, 800, 800);
                    primaryStage.setScene(scene);

                }

                if(my >= 411 && my <= 440){
                    Platform.exit();
                }
            }
        });

        Image szczurek = new Image(szczurekURL);
        ImagePattern pattern = new ImagePattern(szczurek,0,0,1,1,true);

        Scene MenuScene = new Scene(MenuLayout, 800, 800);
        primaryStage.setScene(MenuScene);
        MenuScene.setFill(pattern);

        primaryStage.show();
    }


    void ilosc_klikow(Pozycje p) throws IOException {
        int fromcol;
        int fromrow;
        int tocol;
        int torow;
        lista.add(p);

        if (lista.size() == 1) {
            fromcol = lista.get(0).x;
            fromrow = lista.get(0).y;
            if (PionekClass[fromcol][fromrow].co == EMPTY) {
                System.out.println("nie mozesz nic stad przeniesc poniewaz pole jest puste");
                lista.clear();
            } else if (PionekClass[fromcol][fromrow].co != ORANGE && OknoSerwer.gracz == GRACZ.ORANGE) {
                System.out.println("nie tan Pionek");
                lista.clear();
            } else if (PionekClass[fromcol][fromrow].co != PINK && OknoSerwer.gracz == GRACZ.PINK) {
                System.out.println("nie tan Pionek");
                lista.clear();
            }
        }
        if (lista.size() == 2) {
            System.out.println("wsp.0 \n" + lista.get(0).x + "\n" + lista.get(0).y + "\n");
            System.out.println("wsp.1 \n " + lista.get(1).x + "\n" + lista.get(1).y + "\n");
            fromcol = lista.get(0).x;
            fromrow = lista.get(0).y;
            tocol = lista.get(1).x;
            torow = lista.get(1).y;
            lista.clear();
            sprawdzenie(fromcol, fromrow, tocol, torow);

        }
    }

    void zamiana(int fromcol, int fromrow, int tocol, int torow){
        if(PionekClass[tocol][torow].co == EMPTY) {
            PionekClass pioneczek = PionekClass[fromcol][fromrow];
            type2 = pioneczek.type;

            PionekClass pioneczek2 = new PionekClass(type2, tocol * 100, torow * 100);
            PionekClass[tocol][torow] = pioneczek2;

            PionekClass[tocol][torow].co = type2;
            layout.getChildren().add(PionekClass[tocol][torow]);
            layout.getChildren().remove(PionekClass[fromcol][fromrow]);

            PionekClass pioneczek3 = new PionekClass();
            PionekClass[fromcol][fromrow] = pioneczek3;
            PionekClass[fromcol][fromrow].co = EMPTY;
        }
        else
            System.out.println("tam jakis dzidek juz jest");

    }

    void zbicie(int fromcol, int fromrow, int tocol, int torow, int midcol, int midrow){
        PionekClass kolor1 = PionekClass[fromcol][fromrow];
        type2 = kolor1.type;

        PionekClass kolor2 = PionekClass[midcol][midrow];
        int type3;

        type3 = kolor2.type;

        if(type2 != type3) {
            //jeżeli jest pusto to dokonamy zbicia
            if (PionekClass[tocol][torow].co == EMPTY) {
                PionekClass pioneczek = PionekClass[fromcol][fromrow];
                type2 = pioneczek.type;

                PionekClass pioneczek2 = new PionekClass(type2, tocol * 100, torow * 100);
                PionekClass[tocol][torow] = pioneczek2;

                PionekClass[tocol][torow].co = type2;
                layout.getChildren().add(PionekClass[tocol][torow]);
                layout.getChildren().remove(PionekClass[fromcol][fromrow]);
                layout.getChildren().remove(PionekClass[midcol][midrow]);

                PionekClass pioneczek3 = new PionekClass();
                PionekClass[fromcol][fromrow] = pioneczek3;
                PionekClass[fromcol][fromrow].co = EMPTY;

                PionekClass pioneczek4 = new PionekClass();
                PionekClass[midcol][midrow] = pioneczek4;
                PionekClass[midcol][midrow].co = EMPTY;

            }
            else
                System.out.println("tam jakis dzidek juz jest");
        }
        else
            System.out.println("halo halo nie mozesz zbic swojego pionka");
    }

    void clientSendAndRead(int fromcol, int fromrow, int tocol, int torow) throws IOException {
        reader1 = new BufferedReader(new InputStreamReader(serwer.socket.getInputStream()));
        String wspolrzedne;

        PrintWriter pr1 = new PrintWriter(serwer.socket.getOutputStream());

        pr1.println(fromcol + "" + fromrow + "" + tocol + "" + torow);
        pr1.flush();


        wspolrzedne = reader1.readLine();
        fromcol = ((int)wspolrzedne.charAt(0))-48;
        fromrow = ((int)wspolrzedne.charAt(1))-48;
        tocol = ((int)wspolrzedne.charAt(2))-48;
        torow = ((int)wspolrzedne.charAt(3))-48;

        System.out.println(fromcol + "" + fromrow + "" + tocol + "" + torow);

        sprawdzenie_multi(fromcol, fromrow, tocol, torow);
    }

    void sprawdzenie(int fromcol, int fromrow, int tocol, int torow) throws IOException {

        int midcol;
        int midrow;
        boolean poprawnyRuch = false;

        if(OknoSerwer.gracz == GRACZ.PINK && tocol == fromcol + 1 && torow == fromrow + 1){
            System.out.println("tak nie mozna");
        }

        else if(OknoSerwer.gracz == GRACZ.ORANGE && tocol == fromcol - 1 && torow == fromrow - 1){
            System.out.println("tak nie mozna");
        }

        else if(tocol == fromcol + 1 && torow == fromrow + 1){
            zamiana(fromcol, fromrow, tocol, torow);
            poprawnyRuch = true;
        }

        else if(tocol == fromcol - 1 && torow == fromrow + 1){
            zamiana(fromcol, fromrow, tocol, torow);
            poprawnyRuch = true;
        }

        else if(tocol == fromcol - 1 && torow == fromrow - 1){
            zamiana(fromcol, fromrow, tocol, torow);
            poprawnyRuch = true;
        }

        else if(tocol == fromcol + 1 && torow == fromrow - 1){
            zamiana(fromcol, fromrow, tocol, torow);
            poprawnyRuch = true;
        }

        else if(tocol == fromcol + 2 && torow == fromrow + 2){
            midcol = fromcol + 1;
            midrow = fromrow + 1;
            zbicie(fromcol, fromrow, tocol, torow, midcol, midrow);
            poprawnyRuch = true;
        }

        else if(tocol == fromcol - 2 && torow == fromrow + 2){
            midcol = fromcol - 1;
            midrow = fromrow + 1;
            zbicie(fromcol, fromrow, tocol, torow, midcol, midrow);
            poprawnyRuch = true;
        }

        else if(tocol == fromcol + 2 && torow == fromrow - 2){
            midcol = fromcol + 1;
            midrow = fromrow - 1;
            zbicie(fromcol, fromrow, tocol, torow, midcol, midrow);
            poprawnyRuch = true;
        }

        else if(tocol == fromcol - 2 && torow == fromrow - 2){
            midcol = fromcol - 1;
            midrow = fromrow - 1;
            zbicie(fromcol, fromrow, tocol, torow, midcol, midrow);
            poprawnyRuch = true;
        }

        else {
            System.out.println("tak nie mozna");
        }

        if(OknoSerwer.state == OknoSerwer.STATE.SINGLE && OknoSerwer.gracz == GRACZ.PINK && poprawnyRuch){
            OknoSerwer.gracz = GRACZ.ORANGE;
            kolor_gracza = kolor_gracza + 1;
        }
        else if (OknoSerwer.state == OknoSerwer.STATE.SINGLE && OknoSerwer.gracz == GRACZ.ORANGE && poprawnyRuch){
            OknoSerwer.gracz = GRACZ.PINK;
            kolor_gracza = kolor_gracza + 1;
        }

        if(OknoSerwer.state == OknoSerwer.STATE.MULTI && poprawnyRuch){
            clientSendAndRead(fromcol, fromrow, tocol, torow);
        }


    }

    void sprawdzenie_multi(int fromcol, int fromrow, int tocol, int torow) throws IOException {

        int midcol;
        int midrow;

        if(tocol == fromcol + 1 && torow == fromrow + 1){
            zamiana(fromcol, fromrow, tocol, torow);
        }

        else if(tocol == fromcol - 1 && torow == fromrow + 1){
            zamiana(fromcol, fromrow, tocol, torow);
        }

        else if(tocol == fromcol - 1 && torow == fromrow - 1){
            zamiana(fromcol, fromrow, tocol, torow);
        }

        else if(tocol == fromcol + 1 && torow == fromrow - 1){
            zamiana(fromcol, fromrow, tocol, torow);
        }

        else if(tocol == fromcol + 2 && torow == fromrow + 2){
            midcol = fromcol + 1;
            midrow = fromrow + 1;
            zbicie(fromcol, fromrow, tocol, torow, midcol, midrow);
        }

        else if(tocol == fromcol - 2 && torow == fromrow + 2){
            midcol = fromcol - 1;
            midrow = fromrow + 1;
            zbicie(fromcol, fromrow, tocol, torow, midcol, midrow);
        }

        else if(tocol == fromcol + 2 && torow == fromrow - 2){
            midcol = fromcol + 1;
            midrow = fromrow - 1;
            zbicie(fromcol, fromrow, tocol, torow, midcol, midrow);
        }

        else if(tocol == fromcol - 2 && torow == fromrow - 2){
            midcol = fromcol - 1;
            midrow = fromrow - 1;
            zbicie(fromcol, fromrow, tocol, torow, midcol, midrow);
        }

        else {
            System.out.println("tak nie mozna");
        }

    }

    public static void main(String[] args){
        launch(args);
    }
}