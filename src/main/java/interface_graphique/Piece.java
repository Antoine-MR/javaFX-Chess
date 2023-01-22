package interface_graphique;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static interface_graphique.Piece.TypePiece.*;

public class Piece extends ImageView {

    public enum Camp {BLANC, NOIR}

    public static enum TypePiece {PION_BLANC,CAVALIER_BLANC,FOU_BLANC,TOUR_BLANCHE,DAME_BLANCHE,ROI_BLANC,PION_NOIR,CAVALIER_NOIR,FOU_NOIR, TOUR_NOIRE,DAME_NOIRE,ROI_NOIR;}
    private static final Logger debug = Logger.getLogger(Piece.class.getName());
    private Plateau plateau;

    public double mouseAnchorX;
    public double mouseAnchorY;
    private ArrayList<TypePiece> pieces_blanches = new ArrayList<>(){{
        add(TypePiece.PION_BLANC);
        add(TypePiece.CAVALIER_BLANC);
        add(FOU_BLANC);
        add(TypePiece.TOUR_BLANCHE);
        add(TypePiece.DAME_BLANCHE);
        add(TypePiece.ROI_BLANC);
    }};

    private ArrayList<TypePiece> pieces_noires = new ArrayList<>(){{
        add(TypePiece.PION_NOIR);
        add(TypePiece.CAVALIER_NOIR);
        add(TypePiece.FOU_NOIR);
        add(TypePiece.TOUR_NOIRE);
        add(TypePiece.DAME_NOIRE);
        add(TypePiece.ROI_NOIR);
    }};

    private TypePiece type;

    private boolean premier_tour_pion = true;

    private Piece(TypePiece type, String path, Plateau p) {
        super(new Image(path));
        debug.setLevel(Level.ALL);
        this.plateau = p;
        super.setFitHeight(60);
        super.setPreserveRatio(true);
        this.type = type;
        if (p != null){
            p.ajoute_piece(this);
        }
    }


    public Camp getCamp(){
        if (pieces_blanches.contains(this.type)){
            return Camp.BLANC;
        }
        return Camp.NOIR;
    }

    public TypePiece getType() {
        return type;
    }

    public boolean estPieceBlanche(){
        return pieces_blanches.contains(this.type);
    }
    public boolean estPieceNoire(){
        return pieces_noires.contains(this.type);
    }

    public ArrayList<TypePiece> getPieces_blanches() {
        return pieces_blanches;
    }

    public ArrayList<TypePiece> getPieces_noires() {
        return pieces_noires;
    }

    public static Piece nouvellePiece(TypePiece t, Plateau p){
        switch (t){
            case PION_BLANC:
                return new Piece(Piece.TypePiece.PION_BLANC, "file:src/main/images/pion_blanc.png", p);
            case PION_NOIR:
                return new Piece(TypePiece.PION_NOIR, "file:src/main/images/pion_noir.png", p);
            case CAVALIER_BLANC:
                return new Piece(TypePiece.CAVALIER_BLANC, "file:src/main/images/cavalier_blanc.png", p);
            case CAVALIER_NOIR:
                return new Piece(TypePiece.CAVALIER_NOIR, "file:src/main/images/cavalier_noir.png", p);
            case FOU_BLANC:
                return new Piece(FOU_BLANC, "file:src/main/images/fou_blanc.png", p);
            case FOU_NOIR:
                return new Piece(TypePiece.FOU_NOIR, "file:src/main/images/fou_noir.png", p);
            case TOUR_BLANCHE:
                return new Piece(TypePiece.TOUR_BLANCHE, "file:src/main/images/tour_blanche.png", p);
            case TOUR_NOIRE:
                return new Piece(TypePiece.TOUR_NOIRE, "file:src/main/images/tour_noire.png", p);
            case DAME_BLANCHE:
                return new Piece(TypePiece.DAME_BLANCHE, "file:src/main/images/dame_blanche.png", p);
            case DAME_NOIRE:
                return new Piece(TypePiece.DAME_NOIRE, "file:src/main/images/dame_noire.png", p);
            case ROI_BLANC:
                return new Piece(TypePiece.ROI_BLANC, "file:src/main/images/roi_blanc.png", p);
            case ROI_NOIR:
                return new Piece(TypePiece.ROI_NOIR, "file:src/main/images/roi_noir.png", p);
        }
        return null;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Case getCaseActuelle(){
        for (Case i : plateau.getListe_cases().values()){
            if(i.getContenu() == this){
               return i;
            }
        }
        throw new Error("la piece n'est pas dans le plateau");
    }

    private Case[] getCoupsPion(){
        // TODO en passant + deux cases a la foi
        Case case_actuelle = getCaseActuelle();
        int x_temp = case_actuelle.getX();
        int y_temp = case_actuelle.getY();

        HashMap<Coordonnes2D, Case> cases = plateau.getListe_cases();
        ArrayList<Case> result = new ArrayList<>();
        Coordonnes2D haut_gauche;
        Coordonnes2D haut_droit;
        Coordonnes2D haut;
        Coordonnes2D haut_haut;

        if (this.type == TypePiece.PION_BLANC) {
            haut_gauche = new Coordonnes2D(x_temp - 1, y_temp + 1);
            haut_droit = new Coordonnes2D(x_temp + 1, y_temp + 1);
            haut = new Coordonnes2D(x_temp, y_temp+1);
            haut_haut = new Coordonnes2D(x_temp, y_temp+2);
        }
        else{
            haut_gauche = new Coordonnes2D(x_temp+1, y_temp-1);
            haut_droit  = new Coordonnes2D(x_temp-1, y_temp-1);
            haut = new Coordonnes2D(x_temp,y_temp-1);
            haut_haut = new Coordonnes2D(x_temp, y_temp-2);
        }

        if(cases.containsKey(haut_gauche)){
            Piece contenu = cases.get(haut_gauche).getContenu();
            if(contenu != null){
                boolean meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);
                if(!meme_couleur){
                    result.add(cases.get(haut_gauche));
                }
            }
        }

        if(cases.containsKey(haut_droit)){
            Piece contenu = cases.get(haut_droit).getContenu();
            if (contenu != null){
                boolean meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);
                if (!meme_couleur){
                    result.add(cases.get(haut_droit));
                }
            }
        }

        if(cases.containsKey(haut)){
            Piece contenu = cases.get(haut).getContenu();
            if(contenu == null){
                result.add(cases.get(haut));
            }
        }

        if(cases.containsKey(haut_haut) && this.premier_tour_pion){
            Piece contenu = cases.get(haut_haut).getContenu();
            if(contenu == null){
                result.add(cases.get(haut_haut));
            }
        }


        return result.toArray(new Case[0]);
    }
    private Case[] getCoupsCavalier(){
        // TODO verifier ou il peut aller
        Case case_actuelle = getCaseActuelle();
        int x_temp = case_actuelle.getX();
        int y_temp = case_actuelle.getY();

        HashMap<Coordonnes2D, Case> cases = plateau.getListe_cases();
        ArrayList<Case> result = new ArrayList<>();

        // vertical
        Coordonnes2D haut_gauche = new Coordonnes2D(x_temp-1, y_temp+2);
        Coordonnes2D haut_droit  = new Coordonnes2D(x_temp+1, y_temp+2);
        Coordonnes2D bas_gauche  = new Coordonnes2D(x_temp-1, y_temp-2);
        Coordonnes2D bas_droit   = new Coordonnes2D(x_temp+1, y_temp-2);

        // horizontal
        Coordonnes2D gauche_haut = new Coordonnes2D(x_temp-2, y_temp+1);
        Coordonnes2D gauche_bas  = new Coordonnes2D(x_temp-2, y_temp-1);
        Coordonnes2D droit_haut  = new Coordonnes2D(x_temp+2, y_temp+1);
        Coordonnes2D droit_bas   = new Coordonnes2D(x_temp+2, y_temp-1);

        if(cases.containsKey(haut_gauche)){
            Piece contenu = cases.get(haut_gauche).getContenu();
            if (contenu == null){
                result.add(cases.get(haut_gauche));
            }
            else{
                boolean meme_couleur = (this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type)) || (this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type));
                if (!meme_couleur){
                    result.add(cases.get(haut_gauche));
                }
            }
        }
        if(cases.containsKey(haut_droit)){
            Piece contenu = cases.get(haut_droit).getContenu();
            if (contenu == null){
                result.add(cases.get(haut_droit));
            }
            else {
                boolean meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);
                if (!meme_couleur){
                    result.add(cases.get(haut_droit));
                }
            }
        }
        if(cases.containsKey(bas_gauche)){
            Piece contenu = cases.get(bas_gauche).getContenu();
            if (contenu == null){
                result.add(cases.get(bas_gauche));
            }
            else{
                boolean meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);
                if (!meme_couleur){
                    result.add(cases.get(bas_gauche));
                }
            }
        }
        if(cases.containsKey(bas_droit)){
            Piece contenu = cases.get(bas_droit).getContenu();
            if (contenu == null){
                result.add(cases.get(bas_droit));
            }
            else {
                boolean meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);
                if (!meme_couleur){
                    result.add(cases.get(bas_droit));
                }
            }
        }

        if(cases.containsKey(gauche_haut)){
            Piece contenu = cases.get(gauche_haut).getContenu();
            if (contenu == null){
                result.add(cases.get(gauche_haut));
            }
            else{
                boolean meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);
                if (!meme_couleur){
                    result.add(cases.get(gauche_haut));
                }
            }
        }

        if(cases.containsKey(gauche_bas)){
            Piece contenu = cases.get(gauche_bas).getContenu();
            if (contenu == null){
                result.add(cases.get(gauche_bas));
            }
            else {
                boolean meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);
                if (!meme_couleur){
                    result.add(cases.get(gauche_bas));
                }
            }
        }
        if(cases.containsKey(droit_haut)){
            Piece contenu = cases.get(droit_haut).getContenu();
            if (contenu == null){
                result.add(cases.get(droit_haut));
            }
            else{
                boolean meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);
                if (!meme_couleur){
                    result.add(cases.get(droit_haut));
                }
            }
        }
        if(cases.containsKey(droit_bas)){
            Piece contenu = cases.get(droit_bas).getContenu();
            if (contenu == null){
                result.add(cases.get(droit_bas));
            }
            else{
                boolean meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);
                if (!meme_couleur){
                    result.add(cases.get(droit_bas));
                }
            }
        }

        return result.toArray(new Case[0]);
    }

    private Case[] getCoupsFou(){


        Case case_actuelle = getCaseActuelle();
        int x_temp = case_actuelle.getX();
        int y_temp = case_actuelle.getY();

        HashMap<Coordonnes2D, Case> cases = plateau.getListe_cases();
        ArrayList<Case> result = new ArrayList<>();


        // diagonale haut-droite
        while (true){

            x_temp++;
            y_temp++;
            Coordonnes2D emplacement = new Coordonnes2D(x_temp,y_temp);
            if(cases.containsKey(emplacement)){
                Piece contenu = cases.get(emplacement).getContenu();
                if(contenu == null){
                    result.add(cases.get(emplacement));
                }
                else{
                    /*
                        on regarde si la piece regardee dans le tableau est de la meme couleur que la piece dont on detecte les mouvements
                     */
                    boolean meme_couleur;

                    meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);

                    // si c'est de la meme couleur, on est bloque donc pas besoin d'aller plus loin
                    if(meme_couleur)
                        break;

                    // sinon on ajoute la case car il est possible de prendre
                    result.add(cases.get(emplacement));
                    break;
                }
            }
            // si la case n'est pas contenue, on sort du tableau donc on break
            else{
                break;
            }

        }

        // diagonale haut_gauche
        x_temp = case_actuelle.getX();
        y_temp = case_actuelle.getY();
        while (true){

            x_temp--;
            y_temp++;
            Coordonnes2D emplacement = new Coordonnes2D(x_temp,y_temp);
            if(cases.containsKey(emplacement)){
                Piece contenu = cases.get(emplacement).getContenu();
                if(contenu == null){
                    result.add(cases.get(emplacement));
                }
                else{
                    /*
                        on regarde si la piece regardee dans le tableau est de la meme couleur que la piece dont on detecte les mouvements
                     */
                    boolean meme_couleur;

                    meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);

                    // si c'est de la meme couleur, on est bloque donc pas besoin d'aller plus loin
                    if(meme_couleur)
                        break;

                    // sinon on ajoute la case car il est possible de prendre
                    result.add(cases.get(emplacement));
                    break;
                }
            }
            // si la case n'est pas contenue, on sort du tableau donc on break
            else{
                break;
            }

        }

        // diagonale bas_droite
        x_temp = case_actuelle.getX();
        y_temp = case_actuelle.getY();
        while (true){

            x_temp++;
            y_temp--;
            Coordonnes2D emplacement = new Coordonnes2D(x_temp,y_temp);
            if(cases.containsKey(emplacement)){
                Piece contenu = cases.get(emplacement).getContenu();
                if(contenu == null){
                    result.add(cases.get(emplacement));
                }
                else{
                    /*
                        on regarde si la piece regardee dans le tableau est de la meme couleur que la piece dont on detecte les mouvements
                     */
                    boolean meme_couleur;

                    meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);

                    // si c'est de la meme couleur, on est bloque donc pas besoin d'aller plus loin
                    if(meme_couleur)
                        break;

                    // sinon on ajoute la case car il est possible de prendre
                    result.add(cases.get(emplacement));
                    break;
                }
            }
            // si la case n'est pas contenue, on sort du tableau donc on break
            else{
                break;
            }

        }

        // diagonale bas-gauche
        x_temp = case_actuelle.getX();
        y_temp = case_actuelle.getY();

        while (true){

            x_temp--;
            y_temp--;
            Coordonnes2D emplacement = new Coordonnes2D(x_temp,y_temp);
            if(cases.containsKey(emplacement)){
                Piece contenu = cases.get(emplacement).getContenu();
                if(contenu == null){
                    result.add(cases.get(emplacement));
                }
                else{
                    /*
                        on regarde si la piece regardee dans le tableau est de la meme couleur que la piece dont on detecte les mouvements
                     */
                    boolean meme_couleur;

                    meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);

                    // si c'est de la meme couleur, on est bloque donc pas besoin d'aller plus loin
                    if(meme_couleur)
                        break;

                    // sinon on ajoute la case car il est possible de prendre
                    result.add(cases.get(emplacement));
                    break;
                }
            }
            // si la case n'est pas contenue, on sort du tableau donc on break
            else{
                break;
            }

        }
        return result.toArray(new Case[0]);
    }

    private Case[] getCoupsTour(){


        Case case_actuelle = getCaseActuelle();
        int x_temp = case_actuelle.getX();
        int y_temp = case_actuelle.getY();

        HashMap<Coordonnes2D, Case> cases = plateau.getListe_cases();
        ArrayList<Case> result = new ArrayList<>();


        // haut
        while (true){
            y_temp++;
            Coordonnes2D emplacement = new Coordonnes2D(x_temp,y_temp);
            if(cases.containsKey(emplacement)){
                Piece contenu = cases.get(emplacement).getContenu();
                if(contenu == null){
                    result.add(cases.get(emplacement));
                }
                else{
                    /*
                        on regarde si la piece regardee dans le tableau est de la meme couleur que la piece dont on detecte les mouvements
                     */
                    boolean meme_couleur;

                    meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);

                    // si c'est de la meme couleur, on est bloque donc pas besoin d'aller plus loin
                    if(meme_couleur)
                        break;

                    // sinon on ajoute la case car il est possible de prendre
                    result.add(cases.get(emplacement));
                    break;
                }
            }
            // si la case n'est pas contenue, on sort du tableau donc on break
            else{
                break;
            }

        }

        x_temp = case_actuelle.getX();
        y_temp = case_actuelle.getY();

        // bas
        while (true){
            y_temp--;
            Coordonnes2D emplacement = new Coordonnes2D(x_temp,y_temp);
            if(cases.containsKey(emplacement)){
                Piece contenu = cases.get(emplacement).getContenu();
                if(contenu == null){
                    result.add(cases.get(emplacement));
                }
                else{
                    /*
                        on regarde si la piece regardee dans le tableau est de la meme couleur que la piece dont on detecte les mouvements
                     */
                    boolean meme_couleur;

                    meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);

                    // si c'est de la meme couleur, on est bloque donc pas besoin d'aller plus loin
                    if(meme_couleur)
                        break;

                    // sinon on ajoute la case car il est possible de prendre
                    result.add(cases.get(emplacement));
                    break;
                }
            }
            // si la case n'est pas contenue, on sort du tableau donc on break
            else{
                break;
            }

        }

        x_temp = case_actuelle.getX();
        y_temp = case_actuelle.getY();

        // gauche
        while (true){
            x_temp--;
            Coordonnes2D emplacement = new Coordonnes2D(x_temp,y_temp);
            if(cases.containsKey(emplacement)){
                Piece contenu = cases.get(emplacement).getContenu();
                if(contenu == null){
                    result.add(cases.get(emplacement));
                }
                else{

                    // on regarde si la piece regardee dans le tableau est de la meme couleur que la piece dont on detecte les mouvements

                    boolean meme_couleur;

                    meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);

                    // si c'est de la meme couleur, on est bloque donc pas besoin d'aller plus loin
                    if(meme_couleur)
                        break;

                    // sinon on ajoute la case car il est possible de prendre
                    result.add(cases.get(emplacement));
                    break;
                }
            }
            // si la case n'est pas contenue, on sort du tableau donc on break
            else{
                break;
            }

        }
        x_temp = case_actuelle.getX();
        y_temp = case_actuelle.getY();

        // droite
        while (true){
            x_temp++;
            Coordonnes2D emplacement = new Coordonnes2D(x_temp,y_temp);
            if(cases.containsKey(emplacement)){
                Piece contenu = cases.get(emplacement).getContenu();
                if(contenu == null){
                    result.add(cases.get(emplacement));
                }
                else{
                    /*
                        on regarde si la piece regardee dans le tableau est de la meme couleur que la piece dont on detecte les mouvements
                     */
                    boolean meme_couleur;

                    meme_couleur = this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type) || this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type);

                    // si c'est de la meme couleur, on est bloque donc pas besoin d'aller plus loin
                    if(meme_couleur)
                        break;

                    // sinon on ajoute la case car il est possible de prendre
                    result.add(cases.get(emplacement));
                    break;
                }
            }
            // si la case n'est pas contenue, on sort du tableau donc on break
            else{
                break;
            }

        }

        x_temp = case_actuelle.getX();
        y_temp = case_actuelle.getY();

        return result.toArray(new Case[0]);
    }
    private Case[] getCoupsDame(){
        ArrayList<Case> a1 = new ArrayList<>(Arrays.asList(this.getCoupsFou()));
        ArrayList<Case> a2 = new ArrayList<>(Arrays.asList(this.getCoupsTour()));
        a2.addAll(a1);

        return a2.toArray(new Case[0]);
    }

    private Case[] getCoupsRoi(boolean echecs){
//
//        // TODO finir les deplacements des rois : verifier qu'il est en echec (garder les references des pieces dans le plateau)
//        Case case_actuelle = getCaseActuelle();
//        int x_temp = case_actuelle.getX();
//        int y_temp = case_actuelle.getY();
//
//        HashMap<Coordonnes2D, Case> cases = plateau.getListe_cases();
//        ArrayList<Case> result = new ArrayList<>();
//
//        Coordonnes2D haut_gauche = new Coordonnes2D(x_temp-1, y_temp+1);
//        Coordonnes2D haut_milieu = new Coordonnes2D(x_temp, y_temp+1);
//        Coordonnes2D haut_droit  = new Coordonnes2D(x_temp+1, y_temp+1);
//
//        Coordonnes2D gauche      = new Coordonnes2D(x_temp-1, y_temp);
//        Coordonnes2D droite      = new Coordonnes2D(x_temp+1, y_temp);
//
//        Coordonnes2D bas_gauche  = new Coordonnes2D(x_temp-1, y_temp-1);
//        Coordonnes2D bas_milieu  = new Coordonnes2D(x_temp, y_temp-1);
//        Coordonnes2D bas_droit   = new Coordonnes2D(x_temp+1, y_temp-1);
//
//        if(cases.containsKey(haut_gauche)){
//            Piece contenu = cases.get(haut_gauche).getContenu();
//            if (contenu == null){
//                result.add(cases.get(haut_gauche));
//            }
//            else{
//                boolean meme_couleur = (this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type)) || (this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type));
//                if (!meme_couleur){
//                    result.add(cases.get(haut_gauche));
//                }
//            }
//        }
//        if(cases.containsKey(haut_milieu)){
//            Piece contenu = cases.get(haut_milieu).getContenu();
//            if (contenu == null){
//                result.add(cases.get(haut_milieu));
//            }
//            else{
//                boolean meme_couleur = (this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type)) || (this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type));
//                if (!meme_couleur){
//                    result.add(cases.get(haut_milieu));
//                }
//            }
//        }
//        if(cases.containsKey(haut_droit)){
//            Piece contenu = cases.get(haut_droit).getContenu();
//            if (contenu == null){
//                result.add(cases.get(haut_droit));
//            }
//            else{
//                boolean meme_couleur = (this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type)) || (this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type));
//                if (!meme_couleur){
//                    result.add(cases.get(haut_droit));
//                }
//            }
//        }
//        if(cases.containsKey(gauche)){
//            Piece contenu = cases.get(gauche).getContenu();
//            if (contenu == null){
//                result.add(cases.get(gauche));
//            }
//            else{
//                boolean meme_couleur = (this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type)) || (this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type));
//                if (!meme_couleur){
//                    result.add(cases.get(gauche));
//                }
//            }
//        }
//        if(cases.containsKey(droite)){
//            Piece contenu = cases.get(droite).getContenu();
//            if (contenu == null){
//                result.add(cases.get(droite));
//            }
//            else{
//                boolean meme_couleur = (this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type)) || (this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type));
//                if (!meme_couleur){
//                    result.add(cases.get(droite));
//                }
//            }
//        }
//        if(cases.containsKey(bas_gauche)){
//            Piece contenu = cases.get(bas_gauche).getContenu();
//            if (contenu == null){
//                result.add(cases.get(bas_gauche));
//            }
//            else{
//                boolean meme_couleur = (this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type)) || (this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type));
//                if (!meme_couleur){
//                    result.add(cases.get(bas_gauche));
//                }
//            }
//        }
//        if(cases.containsKey(bas_milieu)){
//            Piece contenu = cases.get(bas_milieu).getContenu();
//            if (contenu == null){
//                result.add(cases.get(bas_milieu));
//            }
//            else{
//                boolean meme_couleur = (this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type)) || (this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type));
//                if (!meme_couleur){
//                    result.add(cases.get(bas_milieu));
//                }
//            }
//        }
//        if(cases.containsKey(bas_droit)){
//            Piece contenu = cases.get(bas_droit).getContenu();
//            if (contenu == null){
//                result.add(cases.get(bas_droit));
//            }
//            else{
//                boolean meme_couleur = (this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(contenu.type)) || (this.pieces_noires.contains(this.type) && this.pieces_noires.contains(contenu.type));
//                if (!meme_couleur){
//                    result.add(cases.get(bas_droit));
//                }
//            }
//        }
//
//
//
//        if (echecs){
//            for (Piece p : this.plateau.getListePieces()){
//                boolean meme_couleur = (this.pieces_blanches.contains(this.type) && this.pieces_blanches.contains(p.type)) || (this.pieces_noires.contains(this.type) && this.pieces_noires.contains(p.type));
//                Case case_etudiee = p.getCaseActuelle();
//
//                if (!meme_couleur){
//                    if (p.type == TypePiece.PION_BLANC || p.type == TypePiece.PION_NOIR){
//                        Coordonnes2D case_gauche;
//                        Coordonnes2D case_droite;
//                        // on sauvegarde les coups du pion
//                        Case[] test = p.getCoupsPossibles();
//                        ArrayList<Case> coups_p = new ArrayList<>(List.of(test));
//                        Case actuelle = p.getCaseActuelle();
//
//                        if (p.type == TypePiece.PION_BLANC) {
//                            case_gauche = new Coordonnes2D(actuelle.getX() - 1, actuelle.getY() + 1);
//                            case_droite = new Coordonnes2D(actuelle.getX() + 1, actuelle.getY() + 1);
//
//                        } else {
//                            case_gauche = new Coordonnes2D(actuelle.getX() - 1, actuelle.getY() - 1);
//                            case_droite = new Coordonnes2D(actuelle.getX() + 1, actuelle.getY() - 1);
//
//                        }
//
//                        ArrayList<Case> testos1 = new ArrayList<Case>(List.of(this.getCoupsRoi(false)));
//                        boolean testos2 = testos1.contains(case_gauche);
//
//                        // si la case existe :
//                        if (this.plateau.getListe_cases().containsKey(case_gauche)){
//                            // si le roi peut aller dans la case
//                            if (new ArrayList<Case>(List.of(this.getCoupsRoi(false))).contains(this.plateau.getListe_cases().get(case_gauche))){
//                                enleve(result,case_gauche);
//                                result.remove(case_gauche);
//                            }
//                        }
//
//                        if (this.plateau.getListe_cases().containsKey(case_droite)) {
//                            if (new ArrayList<Case>(List.of(this.getCoupsRoi(false))).contains(this.plateau.getListe_cases().get(case_droite))) {
//                                enleve(result, case_droite);
//                                result.remove(case_droite);
//                            }
//                        }
//                        /*
//
//                            if (new ArrayList<Case>(List.of(this.getCoupsRoi(false))).contains(case_gauche)){
//                            //if (this.plateau.getListe_cases().containsKey(case_gauche)) {
//                                enleve(result,case_gauche);
//                                result.remove(case_gauche);
//                            }
//                            if (new ArrayList<Case>(List.of(this.getCoupsRoi(false))).contains(case_droite)){
//                            //if (this.plateau.getListe_cases().containsKey(case_droite)) {
//                                enleve(result, case_droite);
//                                result.remove(case_droite);
//                            }
//
//                        */
//                    }
//                    else if (p.type == TypePiece.ROI_BLANC  || p.type == TypePiece.ROI_BLANC){
//                        ArrayList<Case> coups_roi_adverse = new ArrayList<>(List.of(p.getCoupsRoi(false)));
//                        for (Case i : result){
//                            if (coups_roi_adverse.contains(i)){
//                                result.remove(i);
//                            }
//                        }
//                    }
//                    else{
//                        Case[] cases_p = p.getCoupsPossibles();
//
//                        for (Case case_temp : (ArrayList<Case>) result.clone()) {
//                            if (Arrays.asList(cases_p).contains(case_temp)) {
//                                result.remove(case_temp);
//
//                            }
//                        }
//
//                    }
//                }
//
//                /*
//
//                if (p.type != TypePiece.ROI_BLANC && p.type != TypePiece.ROI_NOIR){
//                    // on verifie que la couleur des pieces est differente
//                    // TODO les pions se comportent differament des autres pieces (pour les echecs au roi)
//                    if ((!meme_couleur) && p.type != TypePiece.PION_BLANC && p.type != TypePiece.PION_NOIR) {
//                        Case[] cases_p = p.getCoupsPossibles();
//
//                        for (Case case_temp : (ArrayList<Case>) result.clone()) {
//                            if (Arrays.asList(cases_p).contains(case_temp)) {
//                                result.remove(case_temp);
//
//                            }
//                        }
//                    } else if (!meme_couleur) {
//                        Coordonnes2D case_gauche;
//                        Coordonnes2D case_droite;
//                        // on sauvegarde les coups du pion
//                        Case[] test = p.getCoupsPossibles();
//                        ArrayList<Case> coups_p = new ArrayList<>(List.of(test));
//                        Case actuelle = p.getCaseActuelle();
//
//                        if (p.type == TypePiece.PION_BLANC) {
//                            case_gauche = new Coordonnes2D(actuelle.getX() - 1, actuelle.getY() + 1);
//                            case_droite = new Coordonnes2D(actuelle.getX() + 1, actuelle.getY() + 1);
//
//                        } else {
//                            case_gauche = new Coordonnes2D(actuelle.getX() - 1, actuelle.getY() - 1);
//                            case_droite = new Coordonnes2D(actuelle.getX() + 1, actuelle.getY() - 1);
//
//                        }
//                        if (this.plateau.getListe_cases().containsKey(case_gauche)) {
//                            result.remove(case_gauche);
//                        }
//                        if (this.plateau.getListe_cases().containsKey(case_droite)) {
//                            result.remove(case_droite);
//                        }
//                    }
//                }
//                else if (!meme_couleur){
//                    ArrayList<Case> coups_roi_adverse = new ArrayList<>(List.of(p.getCoupsRoi(false)));
//                    for (Case i : result){
//                        if (coups_roi_adverse.contains(i)){
//                            result.remove(i);
//                        }
//                    }
//                }
//                */
//            }
//
//        }
          return new Case[0];
//        return result.toArray(new Case[0]);
    }


    public Case[] getCoupsPossibles(){

        if (this.type == TypePiece.PION_BLANC || this.type == TypePiece.PION_NOIR){
            return getCoupsPion();
        }
        if (this.type == TypePiece.CAVALIER_BLANC || this.type == TypePiece.CAVALIER_NOIR){
            return getCoupsCavalier();
        }
        if (this.type == TypePiece.FOU_NOIR || this.type == FOU_BLANC){
            return getCoupsFou();
        }
        if (this.type == TypePiece.TOUR_BLANCHE || this.type == TypePiece.TOUR_NOIRE){
            return getCoupsTour();
        }
        if (this.type == TypePiece.DAME_BLANCHE || this.type == TypePiece.DAME_NOIRE){
            return getCoupsDame();
        }
        if (this.type == TypePiece.ROI_BLANC || this.type == TypePiece.ROI_NOIR){
            return getCoupsRoi(true);
        }


        return null;
    }

    private static void enleve(ArrayList<Case> coups, Coordonnes2D c){
        int compteur = 0;
        Coordonnes2D temp;
        for (Case la_case : coups){
            temp = new Coordonnes2D(la_case.getX(), la_case.getY());
            if(temp.equals(c)) {
                coups.remove(compteur);
                return;
            }
            compteur++;
        }
    }




    @Override
    public String toString() {
        try{
            Case actuelle = this.getCaseActuelle();
            return "x : " + actuelle.getX() + " y : " + actuelle.getY();
        } catch (Exception e) {
            var x = "";
            var y = "";
            return "piece orpheline";
        }
    }


    public void setPremier_tour_pion(boolean premier_tour_pion) {
        this.premier_tour_pion = premier_tour_pion;
    }
}
