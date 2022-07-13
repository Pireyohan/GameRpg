package vscode_rpg_correction.modele;

import java.sql.ResultSet;
import java.sql.SQLException;

import vscode_rpg_correction.utils.DBManager;

public class Armure extends BasicItem implements Equipable {
    // protected String name="";
    protected int defense = 1;

    public Armure(String name) {
        super(name);
    }

    public Armure(String name, int defense) {
        super(name);
        this.defense = defense;

    }

    public Armure(int id) {
        super("");
        try {
            ResultSet resultat = DBManager.execute("SELECT * FROM armures WHERE id_Armure = " + id);
            if (resultat.next()) {
                this.nom = (resultat.getString("name"));
                this.defense = resultat.getInt("defense");
                this.icon = resultat.getString("icone");
                this.id = id;
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ((SQLException) ex).getSQLState());
            System.out.println("VendoError: " + ((SQLException) ex).getErrorCode());
        }

    }

    // #region/*GETTERS ------------SETTERS */

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
    // #endregion

    @Override
    public boolean equip(Personnage target) {
        if (target.getArmor() != null) // si jai deja une arme equipe je lajoute dans linventaire
            target.ajouterItem(target.getArmor());
        if (target.retirerItem(this)) {
            target.setArmor(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Personnage target) {
        if (target.getArmor() == this)
            target.ajouterItem(this);
        target.setArmor(null);

        return false;
    }

    @Override
    public boolean get(int id) {
       
        return false;
    }

    @Override
    public boolean save() {
        
        return false;
    }

}
