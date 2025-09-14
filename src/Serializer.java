import java.io.*;

public class Serializer {

    public static void serializePatient(Patient patient) {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/patient/p" + patient.getPatientID() + ".ser");
            ObjectOutputStream streamOut = new ObjectOutputStream(fileOut);
            streamOut.writeObject(patient);
            streamOut.close(); fileOut.close();
        } catch (IOException i) {
            System.err.println("ERROR - Cannot serialize " + patient.toString());
        }
    }

    public static Patient deserializePatient(Patient patient) {
        try {
            FileInputStream fileIn = new FileInputStream("data/patient/p" + patient.getPatientID() + ".ser");
            ObjectInputStream streamIn = new ObjectInputStream(fileIn);
            patient = (Patient) streamIn.readObject();
            streamIn.close(); fileIn.close();
            return patient;
        } catch (IOException i) {
            i.printStackTrace();
            System.err.println("ERROR - Cannot deserialize " + patient.toString());
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR - Patient Class not found");
            return null;
        }
    }

    public static void serializeUser(User user) {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/user/u" + user.getUserID() + ".ser");
            ObjectOutputStream streamOut = new ObjectOutputStream(fileOut);
            streamOut.writeObject(user);
            streamOut.close(); fileOut.close();
        } catch (IOException i) {
            System.err.println("ERROR - Cannot serialize " + user.toString());
        }
    }

    public static User deserializeUser(User user) {
        try {
            FileInputStream fileIn = new FileInputStream("data/user/u" + user.getUserID() + ".ser");
            ObjectInputStream streamIn = new ObjectInputStream(fileIn);
            user = (User) streamIn.readObject();
            streamIn.close(); fileIn.close();
            return user;
        } catch (IOException i) {
            i.printStackTrace();
            System.err.println("ERROR - Cannot deserialize " + user.toString());
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR - Patient Class not found");
            return null;
        }
    }
}
