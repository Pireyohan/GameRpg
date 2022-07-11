package vscode_rpg_correction.modele;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Arme extends BasicItem implements Equipable {

    public Connection conn= Maconnex.getConnection();;
     
    // protected String nom="";
    protected int degats = 0;
    protected float critique = 0.0f;



    public Arme() {
        super("pepette");
        
    }

    public Arme(String name) {
        super(name);

    }

    public Arme(String name, int degats, float critique) {
        super(name);
        this.degats = degats;
        this.critique = critique;
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

    public  void afficherArme(){
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultat2 = stmt.executeQuery("SELECT * FROM armes");
            while (resultat2.next()) {
                String titre = resultat2.getString("name_Arme");
                int degat = resultat2.getInt("degats");
                int crit = resultat2.getInt("critique");
                System.out.println(titre + "-" + degat+ " - "+ crit);
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ((SQLException) ex).getSQLState());
            System.out.println("VendoError: " + ((SQLException) ex).getErrorCode());
        }
    }
}
