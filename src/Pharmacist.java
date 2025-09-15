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
                Double dosage;
                if (Administration.currentPatient.getMeds().get(med) != null) {
                    dosage = Administration.currentPatient.getMeds().get(med);
                } else {
                    dosage = 0.0;
                }
                System.out.format("> %.2f gr %s\n", dosage, med);
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
        Serializer.serializeMeds(Administration.currentMeds);
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
            } else {
                break;
            }
        }
    }
}