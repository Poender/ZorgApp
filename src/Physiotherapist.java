import java.util.Scanner;

// User is gesplit in medical en nonmedical
class Physiotherapist extends User.Medical {
    public Physiotherapist(int id, String userName, int pin) {
        super(id, userName, pin);
        setRole("Physiotherapist");
    }
    @Override
    void viewData() {
        super.viewData();
        if (Administration.currentPatient.getLungCap() != null) {
            System.out.format("%-17s %.2f liter\n", "Lung capacity:", Administration.currentPatient.getLungCap());
        }
        if (Administration.currentPatient.getConsults() != null) {
            System.out.format("%-17s \n", "Physio consult history:");
            for (Consult consult : Administration.currentPatient.getConsults()) {
                if (consult.getUser().getRole().equals("Physiotherapist")) {
                    System.out.format("> %s\n", consult.getAllDetails());
                }
            }
        }
    }
    @Override
    public void editPatientData() {
        super.editPatientData();
        var scanner = new Scanner(System.in);
        System.out.format("Lung capacity (%.2f liter):\t", Administration.currentPatient.getLungCap());
        String newLungCapScan = scanner.nextLine();
        double newLungCap; if (newLungCapScan.isEmpty()) {
            newLungCap = Administration.currentPatient.getLungCap();
        } else { newLungCap = Double.parseDouble(newLungCapScan);
        }
        Administration.currentPatient.updatePatient(Administration.currentPatient,null,null,null,null,null,null,newLungCap);
    }

}