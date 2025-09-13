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
        // med usage
    }

    public void addMeds() {
        // add meds
    }

    @Override
    public void editMeds() {
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
    }
}