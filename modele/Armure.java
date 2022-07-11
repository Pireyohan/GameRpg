package vscode_rpg_correction.modele;

public class Armure extends BasicItem implements Equipable {
    // protected String name="";
    protected int defense = 1;

    public Armure(String name, int defense) {
        super(name);
        this.defense = defense;

    }
    public Armure(String name){
        super(name);
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

}
