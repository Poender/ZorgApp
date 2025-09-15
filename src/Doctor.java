import java.util.Scanner;

// User is verdeeld in medical en nonmedical
class Doctor extends User.Medical {
    public Doctor(int id, String userName, int pin) {
        super(id, userName, pin);
        setRole("Doctor");
    }

    @Override
    public void viewData() {
        super.viewData();
        System.out.format("\n%-17s %s\n", "Height:", Administration.currentPatient.getHeight() + " cm");
        System.out.format("%-17s %s\n", "Weight:", Administration.currentPatient.getWeight() + " kg");
        System.out.format("%-17s %.1f\n", "BMI:", Administration.currentPatient.getBMI());
        if (Administration.currentPatient.getLungCap() != null) {
            System.out.format("%-17s %.2f liter\n", "Lung capacity:", Administration.currentPatient.getLungCap());
        }
        if (Administration.currentPatient.getConsults() != null) {
            System.out.format("\n%-17s \n", "ALL consult history:");
            for (Consult consult : Administration.currentPatient.getConsults()) {
                System.out.format("> %s\n", consult.getAllDetails());
            }
        }
    }

    @Override
    public void editPatientData() {
        super.editPatientData();
        var scanner = new Scanner(System.in);
        System.out.format("Height (%s cm):\t", Administration.currentPatient.getHeight());
        String newHeightScan = scanner.nextLine(); double newHeight;
        if (newHeightScan.isEmpty()) { newHeight = Administration.currentPatient.getHeight();
        } else { newHeight = Double.parseDouble(newHeightScan); }
        System.out.format("Weight (%s kg):\t", Administration.currentPatient.getWeight());
        String newWeightScan = scanner.nextLine(); double newWeight;
        if (newWeightScan.isEmpty()) { newWeight = Administration.currentPatient.getWeight();
        } else { newWeight = Double.parseDouble(newWeightScan); }
        System.out.format("Lung capacity (%.2f liter):\t", Administration.currentPatient.getLungCap());
        String newLungCapScan = scanner.nextLine();
        double newLungCap; if (newLungCapScan.isEmpty()) {
            newLungCap = Administration.currentPatient.getLungCap();
        } else { newLungCap = Double.parseDouble(newLungCapScan);
        }

        Administration.currentPatient.updatePatient(Administration.currentPatient,null,null,null,null,newHeight,newWeight, newLungCap);
        System.out.format("Update BMI? (y/n): ");
        String input; int choice = 'n';
        if (!scanner.next().isEmpty()) {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                choice = input.charAt(0);
            }
        }
        if (choice == 'y') {
            Administration.currentPatient.setBMI();
            Administration.currentPatient.setBMILog();
        }
    }
}