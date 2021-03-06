package vscode_rpg_correction.modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vscode_rpg_correction.utils.DBManager;
import vscode_rpg_correction.utils.Model;

public class Personnage extends Model {
    protected static Arme poings = new Arme("Poings", 1, 0.01f, 1);
    protected static Armure aucune = new Armure("Aucune", 0);
    protected String nom;
    public int pv = 50;
    protected int force = 1;
    protected boolean type;
    protected int pvMax=50;
    protected boolean alive= false;

    // faire une arraylist
    protected ArrayList<BasicItem> inventaire = new ArrayList<BasicItem>();

    // protected Map<BasicItem, Integer> inventaire2= new HashMap<>();
    protected Armure armor = aucune;
    protected Arme equipedWeapon = poings;
    // Rajouter son inventaire
    // Inventaire[] inventaire= new Inventaire[10];

    public Personnage(String nom, int pv, int force) {
        this.nom = nom;
        this.pv = pv;
        this.force = force;
        this.alive= pv > 0 ? true : false;
    }

    public boolean IsAlive(){
        return alive;
    }

    // #region get set
    public Personnage(String nom) {
        this.nom = nom;
    }

    public ArrayList<BasicItem> getInventaire() {
        return inventaire;
    }

    public void setInventaire(ArrayList<BasicItem> inventaire) {
        this.inventaire = inventaire;
    }

    @Override
    public String toString() {
        return this.nom + " (Pv => " + pv + ")" + "(Force => " + force + ")";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public Armure getArmor() {
        return armor;
    }

    public void setArmor(Armure armor) {
        this.armor = armor;
    }

    public Arme getEquipedWeapon() {
        return equipedWeapon;
    }

    public void setEquipedWeapon(Arme equipedWeapons) {
        this.equipedWeapon = equipedWeapons;
    }
    // #endregion

    public float attaquer(Personnage autre) {
        int degats = equipedWeapon.getDegats();
        if (Math.random() < equipedWeapon.getCritique() + this.force / 100) {
            degats *= 2;
        }
        degats *= (1 + 0.1f * this.force);
        System.out.println(
                this.nom + " utilise l'arme =>" + equipedWeapon.getNom() + " et tente d'infliger " + degats + " ?? "
                        + autre);
        autre.prendreCoup(degats);
        return degats;

    }

    public float prendreCoup(float degats) {
        degats *= (1 - (this.armor.getDefense() / 100.0f));
        this.pv -= degats;
        System.out.println(this.nom + " re??oit " + degats + " de d??g??ts ! Il lui reste " + this.pv + " points de vie");
        return degats;
    }

    public boolean ajouterItem(BasicItem item) {
        return inventaire.add(item);

    }

    public boolean retirerItem(BasicItem item) {
        return inventaire.remove(item);

    }

    @Override
    public boolean get(int id) {
        String sql = "SELECT * FROM personnages WHERE id_personnage = ?";
        try {
            PreparedStatement stmt = DBManager.conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet resultat = stmt.executeQuery();
            if (resultat.next()) {
                this.id = id;
                this.nom = resultat.getString("nom");
                this.type=resultat.getBoolean("type");
                this.pv = resultat.getInt("pv");
                this.pvMax = resultat.getInt("pvMax");
                this.force = resultat.getInt("force");
                this.armor = new Armure(resultat.getInt("id_armure"));
                this.equipedWeapon = new Arme(resultat.getInt("id_arme"));
                return true;
            }

        } catch (SQLException ex) {

        }
        return false;
    }

    @Override
    public boolean save() {
        
        return false;
    }

}
