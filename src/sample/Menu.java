package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Menu extends VBox {

    public Menu(MenuItem...items){
        getChildren().add(createSeperator());

        for(MenuItem item : items){
            getChildren().addAll(item, createSeperator());
        }
    }

    private Line createSeperator(){
        Line sep = new Line();
        sep.setEndX(200);
        sep.setStroke(Color.BLACK);
        return sep;
    }

}

class MenuItem extends StackPane {

    public MenuItem(String name) {

        Rectangle bg = new Rectangle(200, 30);
        bg.setOpacity(0.3);

        Text text = new Text(name);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);

    }
}
