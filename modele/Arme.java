package vscode_rpg_correction.modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vscode_rpg_correction.utils.DBManager;

public class Arme extends BasicItem implements Equipable {

    // public Connection conn = Maconnex.getConnection();;

    // protected String nom="";
    protected int degats = 0;
    protected float critique = 0.0f;
    

    public Arme() {
        super("pepette");

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

    public Arme(int id) {
        super("");
        try {
            ResultSet resultat = DBManager.execute("SELECT * FROM armes WHERE id_Arme = " + id);
            if (resultat.next()) {
                this.nom = (resultat.getString("name_Arme"));
                this.degats = resultat.getInt("degats");
                this.critique = resultat.getFloat("critique");
                this.poids = resultat.getInt("poids");
                this.icon = resultat.getString("icone");
                this.id = id;
            }

        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ((SQLException) ex).getSQLState());
            System.out.println("VendoError: " + ((SQLException) ex).getErrorCode());
        }

    }

    public boolean get(int id) {
        try {
            ResultSet resultat = DBManager.execute("SELECT * FROM armes WHERE id_Arme = " + id);
            if (resultat.next()) {
                this.nom = (resultat.getString("name_Arme"));
                this.degats = resultat.getInt("degats");
                this.critique = resultat.getFloat("critique");
                this.poids = resultat.getInt("poids");
                this.icon = resultat.getString("icone");
                this.id = id;
                return true;
            }

        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ((SQLException) ex).getSQLState());
            System.out.println("VendoError: " + ((SQLException) ex).getErrorCode());

        }
        return false;

    }
    public boolean get(){
        try{
            ResultSet resultat = DBManager.execute("SELECT * FROM armes WHERE id_Arme = " + this.id);
            if (resultat.next()) {
                this.nom = (resultat.getString("name_Arme"));
                this.degats = resultat.getInt("degats");
                this.critique = resultat.getFloat("critique");
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
            sql = "UPDATE armes " +
                    "SET name_Arme = ?, degats = ?, critique = ?, poids = ?, icone = ?" +
                    "WHERE id_Arme = ?";
        else
            sql = "INSERT INTO armes (name_Arme, degats, critique, poids, icone)" +
                    "VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = DBManager.conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.nom);
            stmt.setInt(2, this.degats);
            stmt.setFloat(3, this.critique);
            stmt.setInt(4, this.poids);
            stmt.setString(5, this.icon);
            if (id != 0)
                stmt.setInt(6, this.id);

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
        /*
         * Old version
         * if(this.id != 0){
         * sql = "UPDATE armes SET "+
         * "nom = '"+this.nom+"', "+
         * "degats = "+this.degats+", "+
         * "critique = "+this.critique+", "+
         * "poids = "+this.poids+", "+
         * "icon = '"+this.icon+"' "+
         * "WHERE id_arme = "+id;
         * }
         * else{
         * sql = "INSERT INTO armes (nom, degats, critique, poids, icon) "+
         * "VALUES("+
         * "'"+this.nom+"',"+
         * ""+this.degats+","+
         * ""+this.critique+","+
         * ""+this.poids+","+
         * "'"+this.icon+"')";
         * }
         * 
         * return (DBManager.executeUpdate(sql) > 0);
         */
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
    // #endregion
    // #region ancienne version
    // public void afficherArme() {
    // try {
    // Statement stmt = conn.createStatement();
    // ResultSet resultat2 = stmt.executeQuery("SELECT * FROM armes");
    // while (resultat2.next()) {
    // String titre = resultat2.getString("name_Arme");
    // int degat = resultat2.getInt("degats");
    // int crit = resultat2.getInt("critique");
    // System.out.println(titre + " - " + degat + " - " + crit);
    // }

 

    // } catch (SQLException ex) {
    // System.out.println("SQLException: " + ex.getMessage());
    // System.out.println("SQLState: " + ((SQLException) ex).getSQLState());
    // System.out.println("VendoError: " + ((SQLException) ex).getErrorCode());
    // }
    // }

    // public void insertArme(Arme ar1) {
    // try {
    // PreparedStatement preparedStatement = conn
    // .prepareStatement("INSERT INTO armes(name_Arme, degats, critique, poids)
    // VALUES (?,?,?,?);");
    // preparedStatement.setString(1, ar1.getNom());
    // preparedStatement.setInt(2, ar1.getDegats());
    // preparedStatement.setFloat(3, ar1.getCritique());
    // preparedStatement.setInt(4, ar1.getPoids());
    // preparedStatement.executeUpdate();

    // // récupère
    // Statement instruction = conn.createStatement();

    // String SQL = "select MAX(id_Arme) as id_max from armes";
    // ResultSet resultat = instruction.executeQuery(SQL);
    // int id_max = 0;
    // while (resultat.next())
    // id_max = resultat.getInt("id_max");
    // // ar1.(id_max);
    // resultat.close();

    // } catch (Exception ex) {
    // System.out.println("SQLException: " + ex.getMessage());
    // System.out.println("SQLState: " + ((SQLException) ex).getSQLState());
    // System.out.println("VendoError: " + ((SQLException) ex).getErrorCode());
    // }

    // }
    // #endregion
}
