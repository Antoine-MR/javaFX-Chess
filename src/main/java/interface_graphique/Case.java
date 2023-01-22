package interface_graphique;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Case extends Label{
	private int x;
	private int y;


	private boolean enValeur;
	private Color couleur;
	private Background b_normal;
	private Background b_en_valeur;


	public Case(int x, int y) {
		super();
		setAlignment(Pos.CENTER);
		this.y = x;
		this.x = y;

		init();
	}



	private void init() {


		if ((this.x + this.y)%2==0) {
			this.couleur = Color.rgb(113, 140, 81);
            b_en_valeur = new Background(new BackgroundFill(Color.rgb(158,119,249),CornerRadii.EMPTY,null));

        }
		else {
			this.couleur = Color.WHITE;
            b_en_valeur = new Background(new BackgroundFill(Color.rgb(182,149,253),CornerRadii.EMPTY,null));
		}

		b_normal = new Background(new BackgroundFill(couleur,CornerRadii.EMPTY,null));

		super.setBackground(b_normal);
		
	}

	public void accepte_piece(Piece p){
		this.setGraphic(p);
	}

	public Piece getContenu(){
		return (Piece) this.graphicProperty().getValue();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void met_en_valeur(){
		super.setBackground(b_en_valeur);
		enValeur = true;
	}

	public void enleve_en_valeur(){
		super.setBackground(b_normal);
		enValeur = false;
	}

	@Override
	public String toString() {
		return "Case{" +
				"x=" + x +
				", y=" + y +
				", couleur=" + couleur +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Case aCase = (Case) o;
		return x == aCase.x && y == aCase.y && Objects.equals(couleur, aCase.couleur) && Objects.equals(b_normal, aCase.b_normal) && Objects.equals(b_en_valeur, aCase.b_en_valeur);
	}

	public boolean estEnValeur() {
		return enValeur;
	}
}
