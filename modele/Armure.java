package vscode_rpg_correction.modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import vscode_rpg_correction.utils.DBManager;

public class Armure extends BasicItem implements Equipable {
    // protected String name="";
    protected int defense = 1;

    public Armure() {
        super("tilt");
    }
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


    public boolean get(int id) {
        try{
            ResultSet resultat = DBManager.execute("SELECT * FROM armures WHERE id_Armure = " + id);
            if (resultat.next()) {
                this.nom = (resultat.getString("name_Arme"));
                this.defense = resultat.getInt("defense");
                this.poids = resultat.getInt("poids");
                this.icon = resultat.getString("icone");
                this.id=id;
                return true;
            }
        }
        catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return false;
    }
    public boolean get() {
        try{
            ResultSet resultat = DBManager.execute("SELECT * FROM armures WHERE id_Armure = " + this.id);
            if (resultat.next()) {
                this.nom = (resultat.getString("name"));
                this.defense = resultat.getInt("defense");
                this.poids = resultat.getInt("poids");
                this.icon = resultat.getString("icone");
            
                return true;
            }
        }
        catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return false;
    }

    
    public boolean save() {
        String sql;
        if (this.id != 0)
            sql = "UPDATE armures " +
                    "SET name = ?, defense = ?, poids = ?, icone = ?" +
                    "WHERE id_Armure = ?";
        else
            sql = "INSERT INTO armures (name, defense, poids, icone)" +
                    "VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement stmt = DBManager.conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.nom);
            stmt.setInt(2, this.defense);
            stmt.setInt(3, this.poids);
            stmt.setString(4, this.icon);
           
            if (id != 0)
                stmt.setInt(5, this.id);

                stmt.executeUpdate();

                ResultSet keys = stmt.getGeneratedKeys();
                if(this.id == 0 && keys.next()){
                    this.id = keys.getInt(1);
                    return true;
                }
                else if(this.id != 0)
                    return true;
                else
                    return false;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
        
    }

}
