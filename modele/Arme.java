package vscode_rpg_correction.modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vscode_rpg_correction.utils.DBManager;

public class Arme extends BasicItem implements Equipable {

    public Connection conn = Maconnex.getConnection();;

    // protected String nom="";
    protected int degats = 0;
    protected float critique = 0.0f;
    protected int poids = 0;
    int id;

    public Arme() {
        super("pepette");

    }

    public Arme(int id) {
        super("");
        try {
            ResultSet resultat = DBManager.execute("SELECT * FROM armesWHERE id_Arme = " + id);
            if (resultat.next()) {
                this.nom = (resultat.getString("nom"));
                this.degats= resultat.getInt("degats");
                this.critique=resultat.getFloat("critique");
                this.poids= resultat.getInt("poids");
                this.icon=resultat.getString("icon");
                this.id=id;
            }

        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ((SQLException) ex).getSQLState());
            System.out.println("VendoError: " + ((SQLException) ex).getErrorCode());
        }

    }

    public Arme(String name) {
        super(name);

    }

    public Arme(String name, int degats, float critique, int poids) {
        super(name);
        this.degats = degats;
        this.critique = critique;
        this.poids = poids;
    }

    @Override
    public boolean equip(Personnage target) {
        if (target.getEquipedWeapon() != null) // si jai deja une arme equipe je lajoute dans linventaire
            target.ajouterItem(target.getEquipedWeapon());
        if (target.retirerItem(this)) {
            target.setEquipedWeapon(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Personnage target) {
        if (target.getEquipedWeapon() == this) {
            target.ajouterItem(this);
            target.setEquipedWeapon(null);

            return true;
        }
        return false;
    }

    // #region Get SEt
    public int getDegats() {
        return degats;
    }

    public void setDegats(int degats) {
        this.degats = degats;
    }

    public float getCritique() {
        return critique;
    }

    public void setCritique(float critique) {
        this.critique = critique;
    }
    // #endregion

    public void afficherArme() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultat2 = stmt.executeQuery("SELECT * FROM armes");
            while (resultat2.next()) {
                String titre = resultat2.getString("name_Arme");
                int degat = resultat2.getInt("degats");
                int crit = resultat2.getInt("critique");
                System.out.println(titre + " - " + degat + " - " + crit);
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ((SQLException) ex).getSQLState());
            System.out.println("VendoError: " + ((SQLException) ex).getErrorCode());
        }
    }

    public void insertArme(Arme ar1) {
        try {
            PreparedStatement preparedStatement = conn
                    .prepareStatement("INSERT INTO armes(name_Arme, degats, critique, poids) VALUES (?,?,?,?);");
            preparedStatement.setString(1, ar1.getNom());
            preparedStatement.setInt(2, ar1.getDegats());
            preparedStatement.setFloat(3, ar1.getCritique());
            preparedStatement.setInt(4, ar1.getPoids());
            preparedStatement.executeUpdate();

            // récupère
            Statement instruction = conn.createStatement();

            String SQL = "select MAX(id_Arme) as id_max from armes";
            ResultSet resultat = instruction.executeQuery(SQL);
            int id_max = 0;
            while (resultat.next())
                id_max = resultat.getInt("id_max");
            // ar1.(id_max);
            resultat.close();

        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ((SQLException) ex).getSQLState());
            System.out.println("VendoError: " + ((SQLException) ex).getErrorCode());
        }

    }

}
