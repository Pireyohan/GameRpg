package vscode_rpg_correction.modele;

public class PotionSoin extends BasicItem implements Consommable {
    protected int pvRendu = 0;

    public PotionSoin(String nom) {
        super(nom);
    }

    @Override // putot basic pour l'instant , a faire évoluer 
    public void consommer(Personnage cible) {
        cible.setPv((cible.getPv()) + pvRendu);
    }
    //#region
    public int getPvRendu() {
        return pvRendu;
    }

    public void setPvRendu(int pvRendu) {
        this.pvRendu = pvRendu;
    }
    //#endregion

    @Override
    public boolean get(int id) {
        
        return false;
    }

    @Override
    public boolean save() {
        
        return false;
    }
}
