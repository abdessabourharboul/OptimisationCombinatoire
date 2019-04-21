
import java.util.Comparator;

public class Grille {

    protected int largeur;
    protected int longueur;
    protected int[][] matrice;

    public Grille(Grille grille) {
        this.largeur = grille.getLargeur();
        this.longueur = grille.getLongueur();
        reinitialiser();
    }

    public Grille(int largeur, int longueur) {
        this.largeur = largeur;
        this.longueur = longueur;
        matrice = new int[largeur][longueur];
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < longueur; j++) {
                matrice[i][j] = 0;
            }
        }
    }

    public Grille(int taille) {
        this.largeur = taille;
        this.longueur = taille;
        matrice = new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                matrice[i][j] = 0;
            }
        }
    }

    @Override
    public Grille clone() throws CloneNotSupportedException {
        Grille grille = new Grille(longueur, largeur);
        grille.reinitialiser();
        return grille;
    }

    public final void reinitialiser() {
        matrice = new int[largeur][longueur];
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < longueur; j++) {
                matrice[i][j] = 0;
            }
        }
    }

    public void afficher() {
        String str = "|";
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < longueur; j++) {
                str += matrice[i][j] + " ";
            }
            System.out.println(str + "|");
            str = "|";
        }
    }

    public boolean partieLibre(int largPartie, int longueurPartie, int coordX, int coordY) {
        if (largeur - coordX < largPartie || longueur - coordY < longueurPartie) {
            return false;
        } else {
            for (int i = coordX; i < coordX + largPartie; i++) {
                for (int j = coordY; j < coordY + longueurPartie; j++) {
                    if (matrice[i][j] != 0) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public int[][] getMatrice() {
        return matrice;
    }

    public void setMatrice(int[][] matrice) {
        this.matrice = matrice;
    }

    public int aire() {
        return largeur * longueur;
    }

    public int encombrement() {
        return largeur + longueur;
    }

    public static class ComparateurEncombrement implements Comparator<Grille> {

        @Override
        public int compare(Grille b1, Grille b2) {
            return b2.encombrement() - b1.encombrement();
        }
    }

    public static class ComparateurAire implements Comparator<Grille> {

        @Override
        public int compare(Grille b1, Grille b2) {
            return b2.aire() - b1.aire();
        }
    }

}
