import java.util.Scanner;

//User is gesplit in medical en nonmedical
class Pharmacist extends User.nonMedical {
    public Pharmacist(int id, String userName, int pin) {
        super(id, userName, pin);
        setRole("Pharmacist");
    }

    @Override
    public void viewData() {
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
    public void editPatientData() {
        super.editPatientData();
        // ONLY subclasses from User.Medical + pharmacist can edit patient medication
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
    }

    public void editMedStorage() {
        System.out.format("\n====| EDIT MEDICINES |%s| Logged in as: %s %s |==\n", "=".repeat(50), Administration.currentUser.getRole(), Administration.currentUser.getUserName());
        var scanner = new Scanner(System.in);
        for (String med : Administration.currentMeds.getAttributes().keySet()) {
            System.out.format("%s (%.2f gr in stock):\t", med, Administration.currentMeds.getAttributes().get(med));
            String newMedScan = scanner.nextLine(); double newDose;
            if (newMedScan.isEmpty()) {
                newDose = Administration.currentMeds.getAttributes().get(med);
            } else {
                newDose = Double.parseDouble(newMedScan);
            }
            Administration.currentMeds.setAttribute(med, newDose);
        }
        addMedsToStorage();
    }

    public void addMedsToStorage() {
        System.out.format("Add new medicines? (y/n): ");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextLine().charAt(0);
            if (choice == 'y') {
                System.out.format("New medicine name: ");
                String newMedName = scanner.nextLine();
                System.out.format("How much in stock (gr): ");
                Double newStock = scanner.nextDouble();
                Administration.currentMeds.setAttribute(newMedName, newStock);
                System.out.format("Add more new medicines? (y/n): ");
            }
            if (choice == 'n') {
                break;
            }
        }
    }
}