
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * class Administration represents the core of the application by showing
 * the main menu, from where all other functionality is accessible, either
 * directly or via sub-menus.
 * An Administration instance needs a User as input, which is passed via the
 * constructor to the data member 'currentUser'.
 * The patient data is available via the data member currentPatient.
 */
public class Administration {
    static final int STOP = 0;
    static final int VIEW = 1;
    static final int EDIT = 2;
    static final int CONSULT = 3;
    static final int SEARCH = 4;
    static final int LIST = 5;
    static final int ADD = 6;

    static ArrayList<Patient> patientArray;
    static Patient currentPatient;            // The currently selected patient
    static User currentUser;               // the current user of the program.
    static MedStorage currentMeds;

    /**
     * Constructor
     */
    Administration(User user, MedStorage medStorage) {
        currentUser = user;
        currentMeds = medStorage;
        patientArray = new ArrayList<>();

        // Load ALL patients from file into patientArray
        try {
            File folder = new File("data/patient");
            File[] files = folder.listFiles();
            for (File file : files) {
                patientArray.add(Serializer.deserializePatient(file.getName()));
            }
        } catch (Exception e) {
            System.err.println("ERROR - Cannot load patient data");
        }
    }

    static void searchPatient() {
        System.out.flush();
        System.out.format("\n====| SEARCH PATIENT |%s| Logged in as: %s %s |==", "=".repeat(50), currentUser.getRole(), currentUser.getUserName());
        var scanner = new Scanner(System.in);
        System.out.format("\n\nSearch by patient ID or name ");
        System.out.format("\nLeave blank to view all patients");
        System.out.format("\n > ");

        String searchTerm = scanner.nextLine();
        ArrayList<Patient> searchResults = new ArrayList<>();

        for (Patient patient : patientArray) {
            if ((patient.getFirstName().toLowerCase() +" "+ patient.getSurname().toLowerCase()).contains(searchTerm.toLowerCase()) ||
                    String.valueOf(patient.getPatientID()).equals(searchTerm)) {
                searchResults.add(patient);
            }
        }

        if (searchResults.isEmpty()) {
            System.out.format("ERROR - No matches found for '%s'\n", searchTerm);
        } else {
            if (searchResults.size() == 1) {
                currentPatient = searchResults.get(0);
            } else {
                System.out.format("\n%s \t\t %s\n", "ID", "Name:");
                for (Patient patient : searchResults) {
                    System.out.format("[%d] \t %s\n", patient.getPatientID(), patient.fullName());
                }
                Scanner sc = new Scanner(System.in);
                System.out.format("\nPlease enter patient ID: ");
                int inputID = sc.nextInt();
                for (Patient patient : searchResults) {
                    if (patient.getPatientID() == inputID) {
                        currentPatient = patient;
                    }
                }
            }
        }
    }

    static void listPatients() {
        System.out.flush();
        System.out.format("===== CHOOSE PATIENT ==============================\n");
        System.out.format("%s \t\t %s\n", "ID", "Name:");
        for (Patient patient : patientArray) {
            System.out.format("[%d] \t %s\n", patient.getPatientID(), patient.fullName());
        }

        var scanner = new Scanner(System.in);
        System.out.format("\nPlease enter patient ID: ");

        while (!scanner.hasNextInt()) {
            System.out.println("ERROR - ID has to be a number!");
            scanner.nextLine();
            System.out.print("\nPlease enter patient ID: ");
        }
        int patientID = scanner.nextInt();
        if (patientID > patientArray.size()) {
            System.out.println("Please enter a *valid* ID");
        } else {
            currentPatient = patientArray.get(patientID - 1);
            }
    }

    static void menu() {
        while (currentPatient == null) {
            searchPatient();
        }

        while(true) {
            System.out.flush();
            System.out.format("\n%s| Logged in as: %s %s |==", "=".repeat(70), currentUser.getRole(), currentUser.getUserName());
            System.out.format("\n > Current patient: [%d] %s %s, %d years\n", currentPatient.getPatientID(), currentPatient.getFirstName(), currentPatient.getSurname(), currentPatient.intCalc());
            /*
             Print menu on screen
            */
            System.out.format("%d:  View patient data\n", VIEW);
            System.out.format("%d:  Edit patient data\n", EDIT);
            if (currentUser instanceof Pharmacist) {
                System.out.format("%d:  Manage medicine storage\n |\n", CONSULT);
            } else {
                System.out.format("%d:  Add new consult\n |\n", CONSULT);
            }
            System.out.format("%d:  Search patients\n", SEARCH);
            System.out.format("%d:  List all patients\n", LIST);
            System.out.format("%d:  Add new patient\n", ADD);
            System.out.format("\n%d:  LOG OUT\n", STOP);
            //System.out.format("\n%d:  STOP\n", STOP);
            System.out.print("\nEnter #choice: ");
//            try {
            var scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case STOP:
                    Authentication authentication = new Authentication();
                    currentUser = authentication.logIn();
                    break;

                case VIEW:
                    currentUser.viewData();
                    break;

                case EDIT:
                    currentUser.editPatientData();
                    break;

                case SEARCH:
                    searchPatient();
                    break;

                case LIST:
                    listPatients();
                    break;

                case ADD:
                    currentUser.addPatient();
                    break;

                case CONSULT:
                    if (currentUser instanceof Pharmacist) {
                        currentUser.editMedStorage();
                    } else {
                        currentUser.createConsult();
                    }
                    break;

                default:
                    System.err.println("Please enter a *valid* digit");
                    break;
            }
//            }   catch (Exception e){
//                System.out.println("ERROR - That's not a digit");
//            }
        }
    }
}

