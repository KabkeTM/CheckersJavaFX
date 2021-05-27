package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board extends Rectangle {

    public Board(boolean color, int x, int y) {
        setHeight(100);
        setWidth(100);
        relocate(x, y);
        setFill(color ? Color.LIGHTGREEN : Color.ORANGERED);
    }


}
