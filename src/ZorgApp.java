import java.util.ArrayList;

public class ZorgApp {
    public static void main(String[] args) {
        System.out.println("==| ZORG-APP BY LUCAS NIJSTAD |==\n");

        // Auth object en login - is dit de juiste manier?
        Authentication authentication = new Authentication();
        User currentUser = null;
        while (currentUser == null){
            currentUser = authentication.logIn();
        }
        Administration administration = new Administration(currentUser);
        administration.menu();
    }
}

// powerpoint: access levels gedaan inheritance en intcalc
// powerpoint: consults gedaan, uitleg
// vragen: wat moet er in die consulten staan?
//         private setters?
//         abstract functions
//          hashmap as attribute?

/**
 TO DO:
 default height,weight = null
 bmi grafiek
 nieuwe medicatie toevoegen
 dosering medicatie aanpassen
 Apotheker moet beschikbare medicijnen kunnen aanpassen -> class medStorage

 meds --> list met keypairs?

 date format OVERAL FIXEN
 remove patient
 input blocken not numerical
 */
