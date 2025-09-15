import java.io.*;

public class Serializer {

    public static void serializePatient(Patient patient) {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/patient/p" + patient.getPatientID() + ".ser");
            ObjectOutputStream streamOut = new ObjectOutputStream(fileOut);
            streamOut.writeObject(patient);
            streamOut.close(); fileOut.close();
        } catch (IOException i) {
//            i.printStackTrace();
            System.err.println("ERROR - Cannot serialize " + patient);
        }
    }

    public static Patient deserializePatient(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream("data/patient/" + fileName);
            ObjectInputStream streamIn = new ObjectInputStream(fileIn);
            Patient patient = (Patient) streamIn.readObject();
            streamIn.close(); fileIn.close();
            return patient;
        } catch (IOException i) {
//            i.printStackTrace();
            System.err.println("ERROR - Cannot deserialize " + fileName);
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
            //            i.printStackTrace();
            System.err.println("ERROR - Cannot serialize " + user);
        }
    }

    public static User deserializeUser(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream("data/user/" + fileName);
            ObjectInputStream streamIn = new ObjectInputStream(fileIn);
            User user = (User) streamIn.readObject();
            streamIn.close(); fileIn.close();
            return user;
        } catch (IOException i) {
//            i.printStackTrace();
            System.err.println("ERROR - Cannot deserialize " + fileName);
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR - User Class not found");
            return null;
        }
    }

    public static void serializeMeds(MedStorage medStorage) {
        try {
            FileOutputStream fileOut = new FileOutputStream("data/medstorage.ser");
            ObjectOutputStream streamOut = new ObjectOutputStream(fileOut);
            streamOut.writeObject(medStorage);
            streamOut.close(); fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
            System.err.println("ERROR - Cannot serialize " + medStorage);
        }
    }

    public static MedStorage deserializeMeds() {
        try {
            FileInputStream fileIn = new FileInputStream("data/medstorage.ser");
            ObjectInputStream streamIn = new ObjectInputStream(fileIn);
            MedStorage medStorage = (MedStorage) streamIn.readObject();
            streamIn.close(); fileIn.close();
            return medStorage;
        } catch (IOException i) {
//            i.printStackTrace();
            System.err.println("ERROR - Cannot deserialize medstorage.ser");
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR - MedStorage Class not found");
            return null;
        }
    }

}
