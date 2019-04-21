
import java.awt.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;

public class Vue extends JFrame {

    private Glouton realGlouton;
    private Glouton glouton;
    private JFileChooser fileChooser;
    private JButton fileChooserBtn;
    private JButton generer;
    private JButton vider;
    private JButton gloutonNormal;
    private JButton gloutonSortAire;
    private JButton gloutonSortEncombrement;
    private JButton gloutonSortAleatoire;
    private JButton gloutonIteration;

    public Vue() {
        initFrame();
    }

    private void initFrame() {
        //------------Initialisation de la fenetre------------//
        setTitle("---Placement de bâtiments - Algorithme glouton---");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(new Dimension(1200, 450));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        //setResizable(false);
        setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().x - getSize().width / 2,
                GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint().y - getSize().height / 2);
        setLayout(new GridLayout(1, 2));

        JPanel gaucheJp = new JPanel(new GridLayout(6, 1));
        JPanel importerPanel = new JPanel(new GridLayout(1, 1));
        importerPanel.setBorder(BorderFactory.createTitledBorder(" -- Mode 1 -- Importer des instances (.txt) "));
        fileChooserBtn = new JButton("Importer un fichier");
        importerPanel.add(fileChooserBtn);

        JPanel generateurPanel = new JPanel(new GridLayout(1, 3));
        generateurPanel.setBorder(BorderFactory.createTitledBorder(" -- Mode 2 -- Générateur d'instances "));

        JLabel tailleLbl = new JLabel("Taille : ");
        JTextField tailleTF = new JTextField();
        generer = new JButton("Génerer");
        generateurPanel.add(tailleLbl);
        generateurPanel.add(tailleTF);
        generateurPanel.add(generer);
        gloutonNormal = new JButton("Glouton Normal");
        JPanel triPanel = new JPanel(new GridLayout(1, 3));
        gloutonSortAire = new JButton("Trier par Aire");
        gloutonSortEncombrement = new JButton("Trier par Encombrement");
        gloutonSortAleatoire = new JButton("Glouton Aleatoire");
        triPanel.add(gloutonSortAire);
        triPanel.add(gloutonSortEncombrement);
        triPanel.add(gloutonSortAleatoire);
        gloutonIteration = new JButton("Glouton 1000 Iterations");
        vider = new JButton("Vider");

        enableComponents(false);

        gaucheJp.add(importerPanel);
        gaucheJp.add(generateurPanel);
        gaucheJp.add(gloutonNormal);
        gaucheJp.add(triPanel);
        gaucheJp.add(gloutonIteration);
        gaucheJp.add(vider);

        JPanel droitJp = new JPanel(new GridLayout(2, 1));
        DroitPanel droitHautJp = new DroitPanel();
        JPanel droitBasJp = new JPanel();
        droitJp.add(droitHautJp);
        droitJp.add(droitBasJp);
        JLabel coordonnesLbl = new JLabel("");
        droitBasJp.add(coordonnesLbl);
        droitJp.setVisible(false);

        getContentPane().add(gaucheJp);
        getContentPane().add(droitJp);

        //Gestion du File Chooser
        fileChooserBtn.addActionListener((event) -> {
            chooseFile();
        });

        generer.addActionListener((event) -> {
            String taille = tailleTF.getText();
            if (!taille.equals("")) {
                try {
                    realGlouton = Glouton.generer(Integer.valueOf(taille));
                    enableComponents(true);
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez saisir un entier valide !");
                }
            }
        });

        gloutonNormal.addActionListener((event) -> {
            try {
                glouton = realGlouton.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
            }
            glouton.appliquer();
            coordonnesLbl.setText(glouton.getCordonnees());
            repaint();
            droitJp.setVisible(true);
        });

        gloutonSortAire.addActionListener((event) -> {
            try {
                glouton = realGlouton.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
            }
            Collections.sort(glouton.getBatiments(), new Grille.ComparateurAire());
            glouton.appliquer();
            coordonnesLbl.setText(glouton.getCordonnees());
            repaint();
            droitJp.setVisible(true);
        });

        gloutonSortEncombrement.addActionListener((event) -> {
            try {
                glouton = realGlouton.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
            }
            Collections.sort(glouton.getBatiments(), new Grille.ComparateurEncombrement());
            glouton.appliquer();
            coordonnesLbl.setText(glouton.getCordonnees());
            repaint();
            droitJp.setVisible(true);
        });

        gloutonSortAleatoire.addActionListener((event) -> {
            try {
                glouton = realGlouton.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
            }
            Collections.shuffle(glouton.getBatiments());
            glouton.appliquer();
            coordonnesLbl.setText(glouton.getCordonnees());
            repaint();
            droitJp.setVisible(true);
        });

        gloutonIteration.addActionListener((event) -> {
            int bestGlouton = 0;
            Map<Glouton, Integer> m = new HashMap();
            for (int i = 0; i < 1000; i++) {
                try {
                    Glouton g = realGlouton.clone();
                    Collections.shuffle(g.getBatiments());
                    g.getTerrain().reinitialiser();
                    g.reInitCordonnees();
                    g.appliquer();
                    if (g.getAireBatiments() > bestGlouton) {
                        bestGlouton = g.getAireBatiments();
                        m.put(g, bestGlouton);
                    }
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            glouton = Collections.max(m.entrySet(), Map.Entry.comparingByValue()).getKey();
            coordonnesLbl.setText(glouton.getCordonnees());
            repaint();
            droitJp.setVisible(true);
        });

        vider.addActionListener((event) -> {
            glouton = null;
            realGlouton = null;
            tailleTF.setText("");
            enableComponents(false);
            repaint();
            droitJp.setVisible(false);
        });
        //pack();
        setVisible(true);
    }

    private class DroitPanel extends JPanel {

        public final static int sizeCase = 20;

        @Override
        public void paint(Graphics g) {
            for (int x = 1; x <= glouton.getTerrain().getLargeur(); x++) {
                for (int y = 1; y <= glouton.getTerrain().getLongueur(); y++) {
                    g.setColor(java.awt.Color.black);
                    g.drawRect(x * sizeCase, y * sizeCase, sizeCase, sizeCase);
                }
            }

            int batiment = 1;
            while (glouton.getNbBatPlaces() >= batiment) {
                java.awt.Color c = new java.awt.Color((int) (Math.random() * 0x1000000));
                for (int x = 1; x <= glouton.getTerrain().getLargeur(); x++) {
                    for (int y = 1; y <= glouton.getTerrain().getLongueur(); y++) {
                        if (glouton.getTerrain().getMatrice()[x - 1][y - 1] == batiment) {
                            g.setColor(c);
                            g.fillRect(x * sizeCase, y * sizeCase, sizeCase, sizeCase);
                            g.setColor(java.awt.Color.black);
                            g.drawRect(x * sizeCase, y * sizeCase, sizeCase, sizeCase);
                        }
                    }
                }
                ++batiment;
            }
        }
    }

    private void chooseFile() {
        JButton open = new JButton();
        fileChooser = new JFileChooser(new java.io.File("./src"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        Action details = fileChooser.getActionMap().get("viewTypeDetails");
        details.actionPerformed(null);
        if (fileChooser.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
            try {
                ImportDonnees importDonnees = new ImportDonnees(fileChooser.getSelectedFile().getAbsolutePath());
                realGlouton = new Glouton(importDonnees.getTerrain(), importDonnees.getBatiments());
                enableComponents(true);
            } catch (Exception ex) {
                Logger.getLogger(Vue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void enableComponents(boolean tf) {
        gloutonNormal.setEnabled(tf);
        gloutonSortAire.setEnabled(tf);
        gloutonSortEncombrement.setEnabled(tf);
        gloutonSortAleatoire.setEnabled(tf);
        gloutonIteration.setEnabled(tf);
        vider.setEnabled(tf);
    }
}
