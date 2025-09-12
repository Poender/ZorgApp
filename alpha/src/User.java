import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class User {
    private final int id;
    private final String userName;
    private final int pin;
    protected String role;

    public User(int id, String userName, int pin) {
        this.id = id;
        this.userName = userName;
        this.pin = pin;
        role = "Guest";
    }

    public int getUserID() {
        return id;
    }
    public int getPin() {return pin; }
    public String getUserName() {return userName; }
    public String getRole() { return role;}

    /**
     * User
     *  * Admin
     *
     *  * Medical
     *  *  * Doctor
     *  *  * Dentist
     *  *  * Fysio
     *
     *  *  Non medical
     *  *  * Pharmacist
     */

    static class Admin extends User {
        public Admin(int id, String userName, int pin) {
            super(id, userName, pin);
            role = "Admin";
        }
    }

    static class Medical extends User {
        public Medical(int id, String userName, int pin) {
            super(id, userName, pin);
            this.role = "Medical";
        }
    }

    static class nonMedical extends User {
        public nonMedical(int id, String userName, int pin) {
            super(id, userName, pin);
            this.role = "nonMedical";
        }
    }

    static class Doctor extends Medical {
        public Doctor(int id, String userName, int pin) {
            super(id, userName, pin);
            this.role = "Doctor";
        }
    }
    static class Dentist extends Medical {
        public Dentist(int id, String userName, int pin) {
            super(id, userName, pin);
            role = "Dentist";
        }
    }
    static class Physiotherapist extends Medical {
        public Physiotherapist(int id, String userName, int pin) {
            super(id, userName, pin);
            role = "Physiotherapist";
        }
    }
    static class Pharmacist extends nonMedical {
        public Pharmacist(int id, String userName, int pin) {
            super(id, userName, pin);
            role = "Pharmacist";
        }
    }

    static User readUser(int id) {
        Path filePath = Paths.get("userData.db");
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                if (Integer.parseInt(line.split(",")[0]) == id) {
                    String userName = line.split(",")[1];
                    int pin = Integer.parseInt(line.split(",")[2]);

                    if (line.split(",")[3].equals("Doctor")) {
                        return new User.Doctor(id, userName, pin);
                    } else if (line.split(",")[3].equals("Dentist")) {
                        return new User.Dentist(id, userName, pin);
                    } else if (line.split(",")[3].equals("Pharmacist")) {
                        return new User.Pharmacist(id, userName, pin);
                    } else if (line.split(",")[3].equals("Physiotherapist")) {
                        return new User.Physiotherapist(id, userName, pin);
                    } else if (line.split(",")[3].equals("Admin")) {
                        return new User.Admin(id, userName, pin);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR reading from file");
        }
        return null;
    }

    void writeUser() {
        Path patientPath = Paths.get("userData.db");
        try {
            List<String> lines = Files.readAllLines(patientPath);
            FileWriter fileWriter = new FileWriter("temp.db", true);

            // Write other users to temp DB
            for (String line : lines) {
                if (Integer.parseInt(line.split(",")[0]) != getUserID()) {
                    fileWriter.write(line + "\n");
                }
            }
            // Write current user to temp DB
            fileWriter.write(getUserID() + "," +
                    getUserName() + "," +
                    getPin() + "," +
                    getRole() + "\n");
            fileWriter.close();
            // Overwrite user DB with temp DB
            Path tempPath = Paths.get("temp.db");
            Files.move(tempPath, patientPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            System.out.println("ERROR writing to file");
        }
    }
}
