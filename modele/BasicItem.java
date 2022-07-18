package vscode_rpg_correction.modele;

import vscode_rpg_correction.utils.Model;

public abstract class BasicItem extends Model {

    protected int poids = 0;
    protected String icon = "";
    protected String nom = "";
    protected int id;


    public BasicItem(String nom) {
        this.nom = nom;
    }

    // #region getter setter
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // #endregion

}