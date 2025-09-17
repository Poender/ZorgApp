import java.io.File;

public class ZorgApp {
    public static void main(String[] args) {
        System.out.println("==| ZORG-APP ADSD 2025 |==\n");

        // Auth object en login - is dit de juiste manier?
        Authentication authentication = new Authentication();
        User currentUser = null;
        while (currentUser == null){
            currentUser = authentication.logIn();
        }

        // Create new medStorage or load existing if already exists as medstorage.ser
        MedStorage medStorage = new MedStorage();
        File medFile = new File("data/medstorage.ser");
        if (medFile.isFile()) {
            medStorage = Serializer.deserializeMeds();
        }

        Administration administration = new Administration(currentUser, medStorage);
        administration.menu();
    }
}

// powerpoint: access levels gedaan inheritance en intcalc
// powerpoint: consults gedaan, uitleg
// powerpoint: lung capacity toegevoegd
// pp: git aangemaakt en github
// medication toegevoegd en pharmacy werkend
// readme aangepast
// komma file naar object streaming



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
