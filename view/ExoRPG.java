package vscode_rpg_correction.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import vscode_rpg_correction.modele.Arme;
import vscode_rpg_correction.modele.Armure;
import vscode_rpg_correction.modele.BasicItem;
import vscode_rpg_correction.modele.Consommable;
import vscode_rpg_correction.modele.Personnage;
import vscode_rpg_correction.modele.PotionSoin;
import vscode_rpg_correction.utils.DBManager;

public class ExoRPG {
    static BasicItem[] availableItems = new BasicItem[5];
    static Arme[] availableWeapons = new Arme[10];
    static Armure[] availableArmors = new Armure[10];

    static Personnage[] monsters = new Personnage[10];
    static Scanner scan;
    public static Connection conn = null;

    public static void main(String[] args) {
        DBManager.init();
        // new Maconnex(); // j'appelle mon constructeur de mon fichier Maconnex.java

        Arme arme1 = new Arme(2);
        System.out.println(arme1.getNom());

        // Arme arme2= new Arme("");
        // if(true){
        // arme2.get(2); //arme2 prends les données de la bdd à l'ID 2
        // }

        // Arme recompense=new Arme(""); exemple de recuperation d"une arme recompenseu
        // une fois le combat finit
        // if(boolean combat ==false){
        // recompense.get(2);
        // }
        // hero.addInventaire(recompense);

        // Je viens setter le nom de l'arme dans l'ID 2
        arme1.setNom("Epée un peu rouillée");
        arme1.save();

        try {
            String sql = "INSERT INTO armes (nom_Arme, degats, critique, poids) VALUES(?,?,?,?)";
            PreparedStatement pstmt = DBManager.conn.prepareStatement(sql);
            pstmt.setString(1, "Epée neuve");
            pstmt.setInt(2, 30);
            pstmt.setFloat(3, 0.45f);
            pstmt.setInt(4, 5);
            pstmt.execute();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendoError: " + ex.getErrorCode());

        }

        // scan = new Scanner(System.in);
        // createItems();
        // generateDungeon();

        // for (Personnage personnage : monsters) {
        // System.out.println("Vous combattez " + personnage);
        // combattre(paul, personnage);
        // }
        // scan.close();

        // todo afficher la list Array Arme
        // Arme listArme= new Arme();
        // listArme.afficherArme();

    }

    // #region ancienne fonctions
    public static void combattre(Personnage p1, Personnage p2) {
        int i = 0;
        while (p1.getPv() > 0 && p2.getPv() > 0) {
            if (i % 2 == 0) {
                System.out.println("Choisissez la prochaine action : ");
                System.out.println("1 : Attaquer");
                System.out.println("2 : Utliser une potion");
                int decision = scan.nextInt();
                switch (decision) {
                    case 1:
                        p1.attaquer(p2);
                        break;
                    case 2:
                        int j = 0;
                        int nbPotions = 0;
                        for (BasicItem item : p1.getInventaire()) {
                            if (item instanceof PotionSoin) {
                                System.out.println(j + " : " + item.getNom());
                                nbPotions++;
                            }
                            j++;
                        }
                        // TODO gÃ©rer Ã§a mieux
                        if (nbPotions == 0)
                            p1.attaquer(p2);
                        else {
                            System.out.println("Quelle potion souhaitez vous utiliser ?");
                            int potionAUtiliser = scan.nextInt();
                            while (!(p1.getInventaire().get(potionAUtiliser) instanceof Consommable))
                                potionAUtiliser = scan.nextInt();

                            PotionSoin potion = (PotionSoin) (p1.getInventaire().get(potionAUtiliser));
                            potion.consommer(p1);
                            p1.retirerItem(potion);
                        }
                        break;
                    default:
                        p1.attaquer(p2);
                }
            } else
                p2.attaquer(p1);
            i++;
        }

        System.out.println("Le vainqueur est : " + ((p1.getPv() > 0) ? p1 : p2));
    }

    public static BasicItem[] initInventaire() {
        BasicItem[] monInventaire = new BasicItem[5];
        return monInventaire;
    }

    public static void generateDungeon() {
        String[] noms = new String[] { "Gobelin", "Orc", "Troll", "Elfe", "Fantôme" };
        String[] adj = new String[] { "peureux", "pretentieux", "stupide", "passionne", "pessimiste", "idealiste",
                "gigantesque", "courageux", "age", "jaune", "violet", "vert", "endurant", "prevoyant", "vigilant",
                "volontaire", "communiste", "de droite", "en marche", "ecolo" };

        for (int i = 0; i < monsters.length; i++) {
            monsters[i] = new Personnage(
                    noms[(int) (Math.random() * noms.length)] + " " + adj[(int) (Math.random() * adj.length)],
                    (int) (i + 1 * Math.random() * 30), (int) (i + 1 * Math.random()));
            System.out.println("Monstre : " + monsters[i]);

            monsters[i].setArmor(availableArmors[(int) (Math.random() * i)]);
        }
    }

    public static void createItems() {
        for (int i = 0; i < availableItems.length; i++) {
            availableItems[i] = new PotionSoin("Potion " + (i + 1) * 5 + "PV");
            ((PotionSoin) availableItems[i]).setPvRendu((i + 1) * 5);
        }
        String[] types = new String[] { "Papier", "Bois", "Cuivre", "Fer", "Or", "Diamand", "Flammes", "Glace", "Ether",
                "Divine" };
        for (int i = 0; i < availableArmors.length; i++) {
            availableArmors[i] = new Armure("Armure de " + types[i]);
            availableArmors[i].setDefense((int) (Math.random() * 5 * (i + 1)));
        }
        for (int i = 0; i < availableWeapons.length; i++) {
            availableWeapons[i] = new Arme("Epée de " + types[i]);
            availableWeapons[i].setDegats((int) (Math.random() * 5 * (i + 1)));
            availableWeapons[i].setCritique((float) Math.random() * 5 * (i + 1) / 100);
        }
    }
    // #endregion
}
