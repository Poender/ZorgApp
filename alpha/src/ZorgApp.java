import java.util.ArrayList;

public class ZorgApp {
    public static void main(String[] args) {
        System.out.println("==| ZORG-APP BY LUCAS NIJSTAD |==\n");

        // Auth objectje en login
        Authentication authentication = new Authentication();
        User currentUser = null;
        while (currentUser == null){
            currentUser = authentication.logIn();
        }

        Administration administration = new Administration(currentUser);
        Administration.menu();
    }
}

// powerpoint: access levels gedaan inheritance en intcalc
// vragen: wat moet er in die consulten staan?

/**
 TO DO:
 bmi grafiek
 consulten bijhouden met datum
 nieuwe medicatie toevoegen
 dosering medicatie aanpassen
 Apotheker moet beschikbare medicijnen kunnen aanpassen -> class medsInStorage

 meds --> list met keypairs?

 date format in newUser input
 remove patient
 input blocken not numerical
 */
