package vscode_rpg_correction.modele;

import java.util.Scanner;

public class Inputs {
    protected Scanner scanner;
    protected Personnage personnage;
    protected Personnage personnage2;

    public Inputs(Scanner scanner, Personnage personnage, Personnage personnage2) {
        this.scanner = scanner;
        this.personnage = personnage;
        this.personnage2=personnage2;
    }

    public void processNextInput() {
        // if (scanner.next() == "1")
        //     character.Hit(10);
        String str = scanner.next();
        if(str.equals("1")){
            personnage.attaquer(personnage2);
            System.out.println(personnage.toString());
        }
    }
}
