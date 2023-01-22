package interface_graphique;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;


public class Plateau extends VBox{
	private int longueur;
	private int largeur;
	private Piece piece_selectionnee;

	private Logger debug = Logger.getLogger(Plateau.class.getName());

	private HashMap<Coordonnes2D, Case> liste_cases = new HashMap<>();
	private List<Case> liste_cases_en_valeur = new ArrayList<>();
	private LinkedList<Piece> listePieces = new LinkedList<>();


	public Plateau(int longueur, int largeur, int witdh, int height) {

		super();

		this.longueur = longueur;
		this.largeur = largeur;
		
		this.setHeight(height);
		this.setWidth(witdh);

		debug.setLevel(Level.ALL);
		
		this.setStyle("-fx-background-color: rgb(59,54,51); -fx-margin: 100px");
		
		init(false);
		
	
		
	}
	

	private void init(boolean generateur_pieces) {
		double cote = this.getHeight();
		for (int i = longueur ; i >= 1 ; i--) {
			HBox ligne_actuelle = new HBox();
			ligne_actuelle.setAlignment(Pos.CENTER);


			if (generateur_pieces) {
				Support sup_gauche;
				if (i == longueur || i == 1){
					sup_gauche = new Support(null);
					sup_gauche.setSupportVide();
				} else{
					switch (i){
						case 7:
							sup_gauche = new Support(Piece.nouvellePiece(Piece.TypePiece.PION_BLANC, null));
							break;
						case 6:
							sup_gauche = new Support(Piece.nouvellePiece(Piece.TypePiece.CAVALIER_BLANC, null));
							break;
						case 5:
							sup_gauche = new Support(Piece.nouvellePiece(Piece.TypePiece.FOU_BLANC, null));
							break;
						case 4:
							sup_gauche = new Support(Piece.nouvellePiece(Piece.TypePiece.TOUR_BLANCHE, null));
							break;
						case 3:
							sup_gauche = new Support(Piece.nouvellePiece(Piece.TypePiece.DAME_BLANCHE, null));
							break;
						case 2:
							sup_gauche = new Support(Piece.nouvellePiece(Piece.TypePiece.ROI_BLANC, null));
							break;
						default:
							sup_gauche = new Support(null);
							break;
					}
				}
				sup_gauche.setPrefWidth(cote/(double)longueur);
				sup_gauche.setPrefHeight(cote/(double)longueur);
				ligne_actuelle.getChildren().add(sup_gauche);
			}

			for (int j = 1 ; j <= largeur ; j++) {
				

				
				
				Case c = new Case(i, j);
				

				c.setPrefWidth(cote/(double)longueur);
				c.setPrefHeight(cote/(double)longueur);
				

				
				// ajout de la case dans la HashMap
				liste_cases.put(new Coordonnes2D(j,i), c);
				
				// ajout de la case dans la ligne actuelle
				ligne_actuelle.getChildren().add(c);
			}

			if (generateur_pieces) {
				Support sup_droit;
				if (i == longueur || i == 1){
					sup_droit = new Support(null);
					sup_droit.setSupportVide();
				} else{
					switch (i){
						case 7:
							sup_droit = new Support(Piece.nouvellePiece(Piece.TypePiece.PION_NOIR, null));
							break;
						case 6:
							sup_droit = new Support(Piece.nouvellePiece(Piece.TypePiece.CAVALIER_NOIR, null));
							break;
						case 5:
							sup_droit = new Support(Piece.nouvellePiece(Piece.TypePiece.FOU_NOIR, null));
							break;
						case 4:
							sup_droit = new Support(Piece.nouvellePiece(Piece.TypePiece.TOUR_NOIRE, null));
							break;
						case 3:
							sup_droit = new Support(Piece.nouvellePiece(Piece.TypePiece.DAME_NOIRE, null));
							break;
						case 2:
							sup_droit = new Support(Piece.nouvellePiece(Piece.TypePiece.ROI_NOIR, null));
							break;
						default:
							sup_droit = new Support(null);
							break;
					}
				}
				sup_droit.setPrefWidth(cote/(double)longueur);
				sup_droit.setPrefHeight(cote/(double)longueur);
				ligne_actuelle.getChildren().add(sup_droit);
			}

			this.getChildren().add(ligne_actuelle);


		}
	}

	public HashMap<Coordonnes2D, Case> getListe_cases() {
		return liste_cases;
	}

	/**
	 * pose les pieces dans leurs formation normale dans le plateau
	 */
	public void genere_plateau(){
		Boolean b = liste_cases.containsKey(new Coordonnes2D(2,3));

		// on pose les pieces blanches

		Case a1 = liste_cases.get(new Coordonnes2D(1,1));
		Case b1 = liste_cases.get(new Coordonnes2D(2,1));
		Case c1 = liste_cases.get(new Coordonnes2D(3,1));
		Case d1 = liste_cases.get(new Coordonnes2D(4,1));
		Case e1 = liste_cases.get(new Coordonnes2D(5,1));
		Case f1 = liste_cases.get(new Coordonnes2D(6,1));
		Case g1 = liste_cases.get(new Coordonnes2D(7,1));
		Case h1 = liste_cases.get(new Coordonnes2D(8,1));

		Case a2 = liste_cases.get(new Coordonnes2D(1,2));
		Case b2 = liste_cases.get(new Coordonnes2D(2,2));
		Case c2 = liste_cases.get(new Coordonnes2D(3,2));
		Case d2 = liste_cases.get(new Coordonnes2D(4,2));
		Case e2 = liste_cases.get(new Coordonnes2D(5,2));
		Case f2 = liste_cases.get(new Coordonnes2D(6,2));
		Case g2 = liste_cases.get(new Coordonnes2D(7,2));
		Case h2 = liste_cases.get(new Coordonnes2D(8,2));

		// on pose les pions blancs
		pose_pieces(new Case[]{a2,b2,c2,d2,e2,f2,g2,h2}, Piece.nouvellePiece(Piece.TypePiece.PION_BLANC, this), this);

		// on pose les cavaliers blancs
		pose_pieces(new Case[]{b1,g1}, Piece.nouvellePiece(Piece.TypePiece.CAVALIER_BLANC, this), this);

		// on pose les fous blancs
		pose_pieces(new Case[]{c1,f1}, Piece.nouvellePiece(Piece.TypePiece.FOU_BLANC, this), this);

		// on pose les tours blanche
		pose_pieces(new Case[]{a1,h1}, Piece.nouvellePiece(Piece.TypePiece.TOUR_BLANCHE, this), this);

		// on pose la dame blanche
		d1.accepte_piece(Piece.nouvellePiece(Piece.TypePiece.DAME_BLANCHE, this));

		// on pose le roi blanc
		e1.accepte_piece(Piece.nouvellePiece(Piece.TypePiece.ROI_BLANC,this));



		// on pose les pieces noires

		Case a8 = liste_cases.get(new Coordonnes2D(1,8));
		Case b8 = liste_cases.get(new Coordonnes2D(2,8));
		Case c8 = liste_cases.get(new Coordonnes2D(3,8));
		Case d8 = liste_cases.get(new Coordonnes2D(4,8));
		Case e8 = liste_cases.get(new Coordonnes2D(5,8));
		Case f8 = liste_cases.get(new Coordonnes2D(6,8));
		Case g8 = liste_cases.get(new Coordonnes2D(7,8));
		Case h8 = liste_cases.get(new Coordonnes2D(8,8));

		Case a7 = liste_cases.get(new Coordonnes2D(1,7));
		Case b7 = liste_cases.get(new Coordonnes2D(2,7));
		Case c7 = liste_cases.get(new Coordonnes2D(3,7));
		Case d7 = liste_cases.get(new Coordonnes2D(4,7));
		Case e7 = liste_cases.get(new Coordonnes2D(5,7));
		Case f7 = liste_cases.get(new Coordonnes2D(6,7));
		Case g7 = liste_cases.get(new Coordonnes2D(7,7));
		Case h7 = liste_cases.get(new Coordonnes2D(8,7));

		// on pose les pions noirs
		pose_pieces(new Case[]{a7,b7,c7,d7,e7,f7,g7,h7},Piece.nouvellePiece(Piece.TypePiece.PION_NOIR, this), this);

		// on pose les cavaliers noirs
		pose_pieces(new Case[]{b8,g8},Piece.nouvellePiece(Piece.TypePiece.CAVALIER_NOIR, this), this);

		// on pose les fous noirs
		pose_pieces(new Case[]{c8,f8},Piece.nouvellePiece(Piece.TypePiece.FOU_NOIR, this), this);

		// on pose les tours noires
		pose_pieces(new Case[]{a8,h8},Piece.nouvellePiece(Piece.TypePiece.TOUR_NOIRE, this),this);

		// on pose la dame noire
		d8.accepte_piece(Piece.nouvellePiece(Piece.TypePiece.DAME_NOIRE, this));

		// on pose le roi noir
		e8.accepte_piece(Piece.nouvellePiece(Piece.TypePiece.ROI_NOIR, this));



		GenereEvenements();
		// test

	}

	private void GenereEvenements(){
		for (Case c : liste_cases.values()){
			c.setOnMousePressed(mouseEvent-> {

				Piece p = c.getContenu();



				// si on reclique sur la piece
				if (this.piece_selectionnee == p && p!=null) {
					enleve_en_valeur(this.piece_selectionnee.getCoupsPossibles());
					this.piece_selectionnee = null;
					return;
				}
				// si on clique sur une piece de notre camp
				if (p != null && (piece_selectionnee == null || p.getCamp() == piece_selectionnee.getCamp())) {
					if (this.piece_selectionnee != null) {
						enleve_en_valeur(this.piece_selectionnee.getCoupsPossibles());
						this.piece_selectionnee = p;
					}
					met_en_valeur(p.getCoupsPossibles());
					this.piece_selectionnee = p;
				}
				else {

					if (liste_cases_en_valeur.contains(c)){
						enleve_en_valeur(piece_selectionnee.getCoupsPossibles());
						piece_selectionnee.getCaseActuelle().accepte_piece(null);

						Piece piece_a_poser = Piece.nouvellePiece(piece_selectionnee.getType(), this);
						if(piece_a_poser.getType() == Piece.TypePiece.PION_BLANC || piece_a_poser.getType() == Piece.TypePiece.PION_NOIR){
							piece_a_poser.setPremier_tour_pion(false);
						}
						listePieces.remove(c.getContenu());
						c.accepte_piece(piece_a_poser);
						piece_selectionnee = null;
					}
				}
			});
		}
	}

	// TODO effacer fonction test
	private void test(){
		Case f4 = this.liste_cases.get(new Coordonnes2D(6,4));
		f4.accepte_piece(Piece.nouvellePiece(Piece.TypePiece.CAVALIER_BLANC, this));
		Case f5 = this.liste_cases.get(new Coordonnes2D(5,5));
		f5.accepte_piece(Piece.nouvellePiece(Piece.TypePiece.PION_NOIR, this));
		//met_en_valeur(f4.getContenu().getCoupsPossibles(this));
		//met_en_valeur(this.liste_cases.get(new Coordonnes2D(6,1)).getContenu().getCoupsPossibles(this));



	}

	private void met_en_valeur(Case[] cases){
		for (Case i : cases){
			i.met_en_valeur();
			liste_cases_en_valeur.add(i);
		}
	}

	private void enleve_en_valeur(Case[] cases){
		for (Case i : cases){
			i.enleve_en_valeur();
		}
	}

	public void pose_pieces(Case[]liste_cases, Piece p, Plateau plateau){
		liste_cases[0].accepte_piece(p);
		for (int i = 1 ; i < liste_cases.length ; i++){
			Piece temp = Piece.nouvellePiece(p.getType(), plateau);
			liste_cases[i].accepte_piece(temp);
		}
	}

	public int getLongueur() {
		return longueur;
	}

	public int getLargeur() {
		return largeur;
	}

	public Piece[] getListePieces(){
		return this.listePieces.toArray(new Piece[0]);
	}

	public void ajoute_piece(Piece p){
		this.listePieces.add(p);
	}
}
