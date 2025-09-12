
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDate;
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
    static final int SEARCH = 3;
    static final int LIST = 4;
    static final int ADD = 5;
    static final int LOGOUT = 6;


    static ArrayList<Patient> patientArray = new ArrayList<>();
    static Patient currentPatient;            // The currently selected patient
    static User currentUser;               // the current user of the program.

    /**
     * Constructor
     */
    Administration(User user) {
        currentUser = user;

        // Load ALL patients from file into patientArray
        try {
            Path filePath = Paths.get("patientData.db");
            long linesCount = Files.lines(filePath).count();
            for (int i = 1; i <= linesCount; i++) {
                patientArray.add(Patient.readPatient(i));
            }
        } catch (Exception e) {
            System.out.println("ERROR reading from file");
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
            if (patient.getSurname().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    patient.getFirstName().toLowerCase().contains(searchTerm.toLowerCase()) ||
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

    static void addPatient() {
        System.out.flush();
        System.out.format("===== ADD NEW PATIENT ==============================\n");
        // Create empty patient
        Patient newPatient = new Patient(patientArray.size() + 1, "", "",  'x',LocalDate.of(1900, 1,1) , 0, 0, new ArrayList<>());

        Scanner scanner = new Scanner(System.in);
        System.out.print("First name: ");
        newPatient.setFirstName(scanner.nextLine());
        System.out.print("Surname: ");
        newPatient.setSurname(scanner.nextLine());
        boolean loop = true;
        while (loop) {
            System.out.print("Gender: ");
            if (newPatient.setGender(scanner.nextLine().charAt(0))) {
                loop = false;
            }
        }
        loop = true; while (loop) {
            int year = 0;int month = 13;int day = 1;
                System.out.print("Born (yyyy,mm,dd): ");
                String newBirth = scanner.nextLine();
                try {
                    year = Integer.parseInt(newBirth.split("[/\\-\\,\\.\\s]")[0]);
                    month = Integer.parseInt(newBirth.split("[/\\-\\,\\.\\s]")[1]);
                    day = Integer.parseInt(newBirth.split("[/\\-\\,\\.\\s]")[2]);

                } catch (Exception e) {
                    continue;
                }
            if (newPatient.setDateOfBirth(year, month, day)) {
                loop = false;
            }
        }
        newPatient.setBMI();
        newPatient.setBMILog();

        patientArray.add(newPatient);
        newPatient.writePatient();

        //patientArray = combinedArray;
        System.out.format("\nSUCCES - New patient %s added", newPatient.getFirstName());
    }

    static void menu() {
        while (currentPatient == null) {
            searchPatient();
        }

        System.out.flush();
        boolean nextCycle = true;
        while (nextCycle) {
            System.out.format("\n%s| Logged in as: %s %s |==", "=".repeat(70), currentUser.getRole(), currentUser.getUserName());
            System.out.format("\n > Current patient: [%d] %s %s, %d years\n", currentPatient.getPatientID(), currentPatient.getFirstName(), currentPatient.getSurname(), currentPatient.intCalc());
            /*
             Print menu on screen
            */
            System.out.format("%d:  View patient data\n", VIEW);
            System.out.format("%d:  Edit patient data\n |\n", EDIT);
            System.out.format("%d:  Search patients\n", SEARCH);
            System.out.format("%d:  List all patients\n", LIST);
            System.out.format("%d:  Add new patient\n", ADD);
            System.out.format("\n%d:  LOG OUT\n", STOP);
            //System.out.format("\n%d:  STOP\n", STOP);
            System.out.print("\nEnter #choice: ");
            try {
                var scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                    switch (choice) {
                        case STOP:
                            //nextCycle = false;
                            Authentication authentication = new Authentication();
                            currentUser = authentication.logIn();
                            break;

                        case VIEW:
                            currentPatient.viewData();
                            break;

                        case EDIT:
                            currentPatient.editData();
                            break;

                        case SEARCH:
                            searchPatient();
                            break;

                        case LIST:
                            listPatients();
                            break;

                        case ADD:
                            addPatient();
                            break;

                        case LOGOUT:
//                            Authentication authentication = new Authentication();
//                            currentUser = authentication.logIn();
                            break;

                        default:
                            System.out.println("Please enter a *valid* digit");
                            break;
                    }
            }   catch (Exception e){
                System.out.println("ERROR - That's not a digit");
            }
        }
    }
}
