
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ImportDonnees {

    private Grille terrain;
    private ArrayList<Grille> batiments;

    public ImportDonnees(String filename) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(filename));
            String[] largeurAndlongueur = allLines.get(0).split(" ");
            int larg = Integer.valueOf(largeurAndlongueur[0]);
            int haut = Integer.valueOf(largeurAndlongueur[1]);
            terrain = new Grille(larg, haut);
            int nbBat = Integer.valueOf(allLines.get(1));
            batiments = new ArrayList();
            for (int i = 2; i < nbBat + 2; i++) {
                String[] bat = allLines.get(i).split(" ");
                int largBat = Integer.valueOf(bat[0]);
                int hautBat = Integer.valueOf(bat[1]);
                batiments.add(new Grille(largBat, hautBat));
            }
        } catch (IOException e) {
            System.out.println("Erreur de la lecture !");
        }
    }

    public Grille getTerrain() {
        return terrain;
    }

    public ArrayList<Grille> getBatiments() {
        return batiments;
    }

}
