import java.io.FileWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// abstract want er worden geen objects van gemaakt
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private final String userName;
    private final int pin;
    private String role;

    public User(int id, String userName, int pin) {
        this.id = id;
        this.userName = userName;
        this.pin = pin;
        role = "USER";
    }

    void viewData() {
        System.out.format("\n====| PATIENT ID: [%d] |%s| Logged in as: %s %s |==", id, "=".repeat(50), Administration.currentUser.getRole(), Administration.currentUser.getUserName());
        System.out.format("\n%-17s %s\n", "Surname:", Administration.currentPatient.getSurname());
        System.out.format("%-17s %s\n", "Name:", Administration.currentPatient.getFirstName());
        System.out.format("%-17s %s\n", "Gender:", Administration.currentPatient.getGender());
        System.out.format("%-17s %s\n", "Date of birth:", Administration.currentPatient.getDateOfBirth());
        //System.out.println(bmiLog.toString());
    }

    public void editPatientData() {
        System.out.format("\n====| EDIT PATIENT |%s| Logged in as: %s %s |==", "=".repeat(50), Administration.currentUser.getRole(), Administration.currentUser.getUserName());
        System.out.println("\nLeave blank to retain the current value\n");
        var scanner = new Scanner(System.in);
        System.out.format("First name (%s):\t", Administration.currentPatient.getFirstName());
        String newFirstName = scanner.nextLine(); if (newFirstName.isEmpty()) { newFirstName = Administration.currentPatient.getFirstName(); }
        System.out.format("Surname (%s):\t", Administration.currentPatient.getSurname());
        String newSurname = scanner.nextLine(); if (newSurname.isEmpty()) { newSurname = Administration.currentPatient.getSurname(); }

        System.out.format("Gender (%s):\t", Administration.currentPatient.getGender());
        String newGenderScan = scanner.nextLine(); char newGender;
        if (newGenderScan.isEmpty()) { newGender = Administration.currentPatient.getGender();
        } else { newGender = newGenderScan.charAt(0);
        }

        System.out.format("Date of birth (%s):\t", Administration.currentPatient.getDateOfBirth());
        String newDateOfBirthScan = scanner.nextLine(); LocalDate newDateOfBirth;
        if (newDateOfBirthScan.isEmpty()) { newDateOfBirth = Administration.currentPatient.getDateOfBirth();
        } else { newDateOfBirth = LocalDate.parse(newDateOfBirthScan);
        }

        Administration.currentPatient.updatePatient(Administration.currentPatient,newSurname,newFirstName,newGender,newDateOfBirth,null,null, null);
    }

    public void addPatient() {
        System.out.format("\n====| ADD PATIENT |%s| Logged in as: %s %s |==\n", "=".repeat(50), Administration.currentUser.getRole(), Administration.currentUser.getUserName());
        var scanner = new Scanner(System.in);
        System.out.format("First name:\t\t\t- ");
        String newFirstName = scanner.nextLine(); if (newFirstName.isEmpty()) { newFirstName = Administration.currentPatient.getFirstName(); }
        System.out.format("Surname :\t\t\t- ");
        String newSurname = scanner.nextLine(); if (newSurname.isEmpty()) { newSurname = Administration.currentPatient.getSurname(); }

        System.out.format("Gender:\t\t\t\t- ");
        String newGenderScan = scanner.nextLine(); char newGender;
        if (newGenderScan.isEmpty()) { newGender = Administration.currentPatient.getGender();
        } else { newGender = newGenderScan.charAt(0);
        }
        System.out.format("Born (yyyy-mm-dd):\t- ");
        String newDateOfBirthScan = scanner.nextLine(); LocalDate newDateOfBirth;
        if (newDateOfBirthScan.isEmpty()) { newDateOfBirth = Administration.currentPatient.getDateOfBirth();
        } else { newDateOfBirth = LocalDate.parse(newDateOfBirthScan);
        }

        // Create empty patient
        Patient newPatient = new Patient(Administration.patientArray.size() + 1, null, null,  null,null , 0.0, 0.0, new ArrayList<String>());
        Administration.currentPatient.updatePatient(newPatient,newSurname,newFirstName,newGender,newDateOfBirth,0.0,0.0, 0.0);
    }

    public int getUserID() { return id; }
    public int getPin() { return pin; }
    public String getUserName() { return userName; }
    public String getRole() { return role;}

    public void createConsult() { }
    public void editMedStorage() { }

    public void setRole(String role) { this.role = role;}
    /**
     * INHERITANCE:
     *
     * User
     *
     *  * Medical
     *  *  * Doctor
     *  *  * Dentist
     *  *  * Fysio, zorgverleners etc
     *
     *  *  NON-medical
     *  *  * Pharma, balie medewerkers, ?verzekeraar? etc
     *
     */

    static class Admin extends User {
        public Admin(int id, String userName, int pin) {
            super(id, userName, pin);
            setRole("Admin");
        }
    }

    // abstract want er worden geen objecten van gemaakt
    static abstract class Medical extends User {
        public Medical(int id, String userName, int pin) {
            super(id, userName, pin);
            setRole("Medical");
        }
        @Override
        void viewData(){
            super.viewData();
            if (!Administration.currentPatient.getMeds().isEmpty()) {
                System.out.format("\n%-17s \n", "Medication usage:");
                for (String med : Administration.currentPatient.getMeds().keySet()) {
                    Double dosage = 0.0;
                    if (Administration.currentPatient.getMeds().get(med) != null) {
                        dosage = Administration.currentPatient.getMeds().get(med);
                    } else {
                        dosage = 0.0;
                    }
                    System.out.format("> %.2f gr %s\n", dosage, med);
                }
            }
        }

        @Override
        public void createConsult() {
            System.out.format("\n====| CREATE CONSULT |%s| Logged in as: %s %s |==", "=".repeat(50), Administration.currentUser.getRole(), Administration.currentUser.getUserName());
            var scanner = new Scanner(System.in);
            System.out.print("\nEnter consult details:\n- ");
            String consultDetails = scanner.nextLine();
            Administration.currentPatient.addConsult(new Consult(LocalDate.now(), Administration.currentUser, consultDetails));
            Serializer.serializePatient(Administration.currentPatient);
        }

        @Override
        public void editPatientData() {
            super.editPatientData();
            var scanner = new Scanner(System.in);
            for (String med : Administration.currentMeds.getAttributes().keySet()) {

                // If currentDose == null, make it 0.0
                Double currentDose = 0.0;
                if (Administration.currentPatient.getMeds().get(med) == null) {
                    currentDose = 0.0;
                } else {
                    currentDose = Administration.currentPatient.getMeds().get(med);
                }

                System.out.format("%.2f gr %s (%.2f gr in stock):\t", currentDose, med, Administration.currentMeds.getAttributes().get(med));
                String newMedScan = scanner.nextLine();
                Double newDose = currentDose;
                if (!newMedScan.isEmpty()) {
                    newDose = Double.parseDouble(newMedScan);
                    // Check availability in currentMeds
                    if (newDose - currentDose < Administration.currentMeds.getAttributes().get(med)) {
                        Administration.currentMeds.getAttributes().put(med, Administration.currentMeds.getAttributes().get(med) + currentDose - newDose);
                        Administration.currentPatient.setMeds(med, newDose);
                    } else {
                        System.out.format("Not enough %s available!\n", med);
                    }
                }
            }
            Serializer.serializeMeds(Administration.currentMeds);
        }
    }

    // abstract want er worden geen objecten van gemaakt
    static abstract class nonMedical extends User {
        public nonMedical(int id, String userName, int pin) {
            super(id, userName, pin);
            setRole("nonMedical");
        }
    }


    static User readUser(int id) {
        Path filePath = Paths.get("data/user/userData.db");
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                if (Integer.parseInt(line.split(",")[0]) == id) {
                    String userName = line.split(",")[1];
                    int pin = Integer.parseInt(line.split(",")[2]);

                    if (line.split(",")[3].equals("Doctor")) {
                        return new Doctor(id, userName, pin);
                    } else if (line.split(",")[3].equals("Dentist")) {
                        return new Dentist(id, userName, pin);
                    } else if (line.split(",")[3].equals("Pharmacist")) {
                        return new Pharmacist(id, userName, pin);
                    } else if (line.split(",")[3].equals("Physiotherapist")) {
                        return new Physiotherapist(id, userName, pin);
                    } else if (line.split(",")[3].equals("Admin")) {
                        return new User.Admin(id, userName, pin);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR reading from file");
        }
        return null;
    }

    void writeUser() {
        Path patientPath = Paths.get("userData.db");
        try {
            List<String> lines = Files.readAllLines(patientPath);
            FileWriter fileWriter = new FileWriter("temp.db", true);

            // Write other users to temp DB
            for (String line : lines) {
                if (Integer.parseInt(line.split(",")[0]) != getUserID()) {
                    fileWriter.write(line + "\n");
                }
            }
            // Write current user to temp DB
            fileWriter.write(getUserID() + "," +
                    getUserName() + "," +
                    getPin() + "," +
                    getRole() + "\n");
            fileWriter.close();
            // Overwrite user DB with temp DB
            Path tempPath = Paths.get("temp.db");
            Files.move(tempPath, patientPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            System.out.println("ERROR writing to file");
        }
    }
}
