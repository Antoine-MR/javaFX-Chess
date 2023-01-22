package interface_graphique;

import com.sun.javafx.scene.control.ReadOnlyUnbackedObservableList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Fenetre extends Application {


	static class Delta { double x, y; }
	// make a node movable by dragging it around with the mouse.

	/*
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setHeight(720);
		primaryStage.setWidth(1280);
		
		
		BorderPane principal_contener = new BorderPane();
		Plateau p = new Plateau(8,8);
		principal_contener.setCenter(p);
		
		Scene s = new Scene(principal_contener);
		
		primaryStage.setTitle("jeu d'échecs");
		primaryStage.setScene(s);
		primaryStage.show();
	}
	*/
	
	public void start(Stage primaryStage) throws Exception {
		
		
		
		// construction du stage
		
		primaryStage.setTitle("echecs");
		primaryStage.setWidth(1280);
		primaryStage.setHeight(720);
		
		
		// creations de backgrounds
		

		Background b2 = new Background(new BackgroundFill(Color.rgb(120,120,220),CornerRadii.EMPTY,null));

		
		// construction de la scene
		BorderPane bp_le_tout = new BorderPane();
		
		
		bp_le_tout.setBackground(b2);
	
		
		Plateau plateau = new Plateau(8, 8, 600, 600);
		plateau.genere_plateau();
		plateau.setAlignment(Pos.CENTER);



		bp_le_tout.setCenter(plateau);
		

		// affichage
		Scene primaryScene = new Scene(bp_le_tout);
		primaryStage.setScene(primaryScene);
		primaryStage.show();
		
		
	}
	public static void lancement(String[] args) {
		Fenetre.launch(args);
	}

	public static void makeDraggable(Node node) {
		final Delta dragDelta = new Delta();

		node.setOnMouseEntered(me -> {
			System.out.println("mouse entered");
			if (!me.isPrimaryButtonDown()) {
				node.getScene().setCursor(Cursor.HAND);
			}
		});
		node.setOnMouseExited(me -> {
			System.out.println("mouse exited");
			if (!me.isPrimaryButtonDown()) {
				node.getScene().setCursor(Cursor.DEFAULT);
			}
		});
		node.setOnMousePressed(me -> {
			System.out.println("mouse pressed");
			if (me.isPrimaryButtonDown()) {
				node.getScene().setCursor(Cursor.DEFAULT);
			}
			dragDelta.x = me.getX();
			dragDelta.y = me.getY();
			node.getScene().setCursor(Cursor.MOVE);
		});
		node.setOnMouseReleased(me -> {
			System.out.println("mouse released");
			if (!me.isPrimaryButtonDown()) {
				node.getScene().setCursor(Cursor.DEFAULT);
			}
		});
		node.setOnMouseDragged(me -> {
			System.out.println("mouse dragged");
			node.setLayoutX(node.getLayoutX() + me.getX() - dragDelta.x);
			node.setLayoutY(node.getLayoutY() + me.getY() - dragDelta.y);
		});
	}
}
