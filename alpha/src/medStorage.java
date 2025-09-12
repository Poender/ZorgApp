public class medStorage {
    private double Diclofenac = 0;
    private double Amoxicilline = 0;
    private double Omeprazol = 0;
    private double Doxycycline = 0;
    private double Ibuprofen = 0;
    private double Metoprolol = 0;

    public double getDiclofenac() {return Diclofenac;}
    public double getAmoxicilline() {return Amoxicilline;}
    public double getOmeprazol() {return Omeprazol;}
    public double getDoxycycline() {return Doxycycline;}
    public double getIbuprofen() {return Ibuprofen;}
    public double getMetoprolol() {return Metoprolol;}

    public void setDiclofenac(double amount) {
        if (Administration.currentUser instanceof User.Pharmacist) {
            double Diclofenac = amount;
        } else {
            System.out.println("ERROR - Not authorized to change first name");
        }
    }

}
