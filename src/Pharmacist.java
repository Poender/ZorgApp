//User is gesplit in medical en nonmedical
class Pharmacist extends User.nonMedical {
    public Pharmacist(int id, String userName, int pin) {
        super(id, userName, pin);
        setRole("Pharmacist");
    }
}