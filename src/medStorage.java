public class medStorage {
    private double Diclofenac = 100;
    private double Amoxicilline = 100;
    private double Omeprazol = 100;
    private double Doxycycline = 100;
    private double Ibuprofen = 100;
    private double Metoprolol = 100;

    public double getDiclofenac() {return Diclofenac;}
    public double getAmoxicilline() {return Amoxicilline;}
    public double getOmeprazol() {return Omeprazol;}
    public double getDoxycycline() {return Doxycycline;}
    public double getIbuprofen() {return Ibuprofen;}
    public double getMetoprolol() {return Metoprolol;}

    public void setDiclofenac(double amount) {
        if (Administration.currentUser instanceof Pharmacist) {
            double Diclofenac = amount;
        } else {
            System.out.println("ERROR - Not authorized to change first name");
        }
    }

}
