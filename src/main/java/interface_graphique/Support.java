package interface_graphique;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Support extends Case{
    public Support(Piece p) {
        super(-1, -1);
        accepte_piece(p);
        setBackground(new Background(new BackgroundFill(Color.rgb(38, 36, 33), CornerRadii.EMPTY,null)));
    }
    public void setSupportVide(){
        setBackground(null);
    }
}
