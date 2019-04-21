
import java.util.*;

public class Glouton {

    private Grille terrain;
    private ArrayList<Grille> batiments;
    private int aireBatiments = 0;
    private Integer nombreBatPlaces = null;
    private String cordonnees = "Coordonnées : [";

    public Glouton() {
    }

    public Glouton(Grille terrain, ArrayList<Grille> batiments) {
        this.terrain = terrain;
        this.batiments = batiments;
    }

    @Override
    public Glouton clone() throws CloneNotSupportedException {
        Glouton glouton = new Glouton();
        glouton.setTerrain(terrain.clone());
        ArrayList<Grille> bats = new ArrayList();
        for (Grille b : batiments) {
            bats.add(b.clone());
        }
        glouton.setBatiments(bats);
        glouton.setNombreBatPlaces(nombreBatPlaces);
        glouton.setAireBatiments(aireBatiments);
        return glouton;
    }

    public void reInitCordonnees() {
        this.cordonnees = "Coordonnées : [";
    }

    public String getCordonnees() {
        return cordonnees;
    }

    public Integer getNbBatPlaces() {
        return nombreBatPlaces;
    }

    public void setNombreBatPlaces(Integer nombreBatPlaces) {
        this.nombreBatPlaces = nombreBatPlaces;
    }

    public Grille getTerrain() {
        return terrain;
    }

    public void setTerrain(Grille terrain) {
        this.terrain = terrain;
    }

    public ArrayList<Grille> getBatiments() {
        return batiments;
    }

    public void setBatiments(ArrayList<Grille> batiments) {
        this.batiments = batiments;
    }

    public int getAireBatiments() {
        return aireBatiments;
    }

    public void setAireBatiments(int aireBatiments) {
        this.aireBatiments = aireBatiments;
    }

    public void appliquer() {
        int compteur = 1;
        for (Grille batiment : batiments) {
            outerloop:
            for (int i = 0; i < terrain.getLargeur(); i++) {
                for (int j = 0; j < terrain.getLongueur(); j++) {
                    if (terrain.partieLibre(batiment.getLargeur(), batiment.getLongueur(), i, j)) {
                        for (int k = i; k < batiment.getLargeur() + i; k++) {
                            for (int l = j; l < batiment.getLongueur() + j; l++) {
                                terrain.getMatrice()[k][l] = compteur;
                            }
                        }
                        aireBatiments += batiment.aire();
                        compteur++;
                        cordonnees += "   (" + i + "," + j + ")\n";
                        break outerloop;
                    }
                }
            }
        }
        cordonnees += " ]";
        nombreBatPlaces = compteur;
    }

    public static Glouton generer(int taille) {
        Grille terrain = new Grille(taille);
        ArrayList<Grille> batiments = new ArrayList<>();
        for (int i = 0; i < taille; i++) {
            Random r = new Random();
            int low = 1;
            int high = ((Long) Math.round(2 * Math.sqrt(taille))).intValue();
            batiments.add(new Grille(r.nextInt(high - low) + low, r.nextInt(high - low) + low));
        }
        return new Glouton(terrain, batiments);
    }

}
