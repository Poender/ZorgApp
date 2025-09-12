import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Patient {
    private final int       id;
    private String          surname;
    private String          firstName;
    private char            gender;
    private LocalDate       dateOfBirth;
    private double          height;
    private double          weight;
    private double          bmi;
    private ArrayList<String> bmiLog;


    /**
     * Constructor
     */

    Patient(int id, String surname, String firstName, char gender, LocalDate dateOfBirth, double height, double weight, ArrayList<String> bmiLog) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.bmiLog = bmiLog;
        this.bmi = getBMI();
        //this.setBMILog();
    }

    void viewData() {
        System.out.format("===== Patient ID: [%d] ==============================\n", id);
        System.out.format("%-17s %s\n", "Surname:", surname);
        System.out.format("%-17s %s\n", "Name:", firstName);
        System.out.format("%-17s %s\n", "Gender:", gender);
        System.out.format("%-17s %s\n", "Date  of birth:", dateOfBirth);
        System.out.format("%-17s %s\n", "Height:", height + " cm");
        System.out.format("%-17s %s\n", "Weight:", weight + " kg");
        System.out.format("%-17s %.1f\n", "BMI:", bmi);
        //System.out.println(bmiLog.toString());

    }

    void editData() {
        final int STOP = 0;
        final int NAME = 1;
        final int SURNAME = 2;
        final int HEIGHT = 3;
        final int WEIGHT = 4;

        boolean loop = true;
        while (loop) {
            var scanner = new Scanner(System.in);
            System.out.println("===== EDIT PATIENT ==============================\n");
            System.out.format("\n%d:  First name\n", NAME);
            System.out.format("%d:  Surname\n", SURNAME);
            System.out.format("%d:  Height\n", HEIGHT);
            System.out.format("%d:  Weight\n", WEIGHT);
            System.out.format("\n%d:  Back\n", STOP);
            System.out.print("Enter #choice: ");
            int choice = scanner.nextInt();
            System.out.println("=============");

            switch (choice) {
                case STOP:
                    setBMILog();
                    loop = false;
                    break;

                case NAME:
                    System.out.println("Current first name: " + getFirstName());
                    System.out.print("New name: ");
                    String newName = scanner.next();
                    setFirstName(newName);
                    break;
                case SURNAME:
                    System.out.println("Current surname: " + getSurname());
                    System.out.print("New surname: ");
                    String newSurname = scanner.next();
                    setSurname(newSurname);
                    break;

                case HEIGHT:
                    System.out.println("Current height: " + getHeight());
                    System.out.print("New height (cm): ");
                    double newHeight = Double.parseDouble(scanner.next());
                    setHeight(newHeight);
                    setBMI();
                    break;

                case WEIGHT:
                    System.out.println("Current weight: " + getWeight());
                    System.out.print("New weight (kg): ");
                    double newWeight = Double.parseDouble(scanner.next());
                    setWeight(newWeight);
                    setBMI();
                    break;
            }
        }
        writePatient();
    }

    void writePatient() {
        Path patientPath = Paths.get("patientData.db");
        try {
            List<String> lines = Files.readAllLines(patientPath);
            FileWriter fileWriter = new FileWriter("temp.db", true);

            // Write other patients to temp DB
            for (String line : lines) {
                if (Integer.parseInt(line.split(",")[0]) != getPatientID()) {
                    fileWriter.write(line + "\n");
                }
            }
            // Append current patient to temp DB
            fileWriter.write(getPatientID() + "," +
                    getSurname() + "," +
                    getFirstName() + "," +
                    getGender() + "," +
                    getDateOfBirth() + "," +
                    getHeight() + "," +
                    getWeight() + "," +
                    getBMILog() + "\n");
            fileWriter.close();

            // Overwrite old patient DB with temp DB
            Path tempPath = Paths.get("temp.db");
            Files.move(tempPath, patientPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            System.out.println("ERROR writing to file");
        }
    }

    static Patient readPatient(int id) {
        Path filePath = Paths.get("patientData.db");
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                if (Integer.parseInt(line.split(",")[0]) == id) {
                    ArrayList<String> bmiLog = new ArrayList<String>();
                    bmiLog.add(line.split(",")[7].replace("[", "").replace ("]", ""));
                    return new Patient(id,
                            line.split(",")[1],
                            line.split(",")[2],
                            line.split(",")[3].charAt(0),
                            LocalDate.parse(line.split(",")[4]),
                            Double.parseDouble(line.split(",")[5]),
                            Double.parseDouble(line.split(",")[6]),
                            bmiLog);
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR reading from file");
        }
        return null;
    }

    String fullName() {
        return String.format("%-15s %-10s [%s]", surname+",", firstName, dateOfBirth);
    }

    public long intCalc() {
        return (LocalDate.now().toEpochDay() - getDateOfBirth().toEpochDay())/365;
    }

    // GETTERS:
    public int getPatientID() {return id;}
    public String getFirstName() {return firstName;}
    public String getSurname() {return surname;}
    public char getGender() {return gender;}
    public LocalDate getDateOfBirth() {return dateOfBirth;}
    public double getHeight() {return height;}
    public double getWeight() {return weight;}
    public ArrayList<String> getBMILog() {return bmiLog;}

    public double getBMI() {
        bmi = weight/((height/100)*(height/100));
        return bmi;
    }

    // SETTERS:
    public void setFirstName(String firstName) {
        if (Administration.currentUser instanceof User ) {
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            this.firstName = firstName;
        } else {
            System.out.println("ERROR - Not authorized to change first name as " + Administration.currentUser.getRole());
        }
    }
    public void setSurname(String surname) {
        if (Administration.currentUser instanceof User ) {
            surname = surname.substring(0, 1).toUpperCase() + surname.substring(1);
            this.surname = surname;
        } else {
            System.out.println("ERROR - Not authorized to change surname as " + Administration.currentUser.getRole());
        }
    }
    public boolean setGender(char gender) {
        if (Administration.currentUser instanceof User.Medical ) {
            if (gender == 'm' || gender == 'v' || gender == 'x') {
                this.gender = gender;
                return true;
            } else {
                System.out.println("ERROR - Gender must be m, v or x");
                return false;
            }
        } else {
            System.out.println("ERROR - Not authorized to change gender as " + Administration.currentUser.getRole());
            return false;
        }
    }

    public boolean setDateOfBirth(int year, int month, int day) {
        if (Administration.currentUser instanceof User.Medical ) {
            try {
                dateOfBirth = LocalDate.of(year, month, day);
                return true;
            } catch (Exception e) {
                System.out.println("ERROR - Invalid date of birth");
                return false;
            }
        } else {
            System.out.println("ERROR - Not authorized to change date of birth as " + Administration.currentUser.getRole());
            return false;
        }
    }

    public boolean setHeight(double height) {
        if (Administration.currentUser instanceof User.Doctor ||
                Administration.currentUser instanceof User.Admin) {
            try {
                if (height >= 0) {
                    this.height = height;
                    return true;
                } else {
                    System.out.println("ERROR - Height can't be negative!");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("ERROR - Height has to be a number!");
                return false;
            }
        } else {
            System.out.println("ERROR - Not authorized to change height as " + Administration.currentUser.getRole());
            return false;
        }
    }

    public boolean setWeight(double weight) {
        if (Administration.currentUser instanceof User.Doctor ||
                Administration.currentUser instanceof User.Admin ) {
            try {
                if (weight >= 0) {
                    this.weight = weight;
                    return true;
                } else {
                    System.out.println("ERROR - Weight can't be negative!");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("ERROR - Weight has to be a number!");
                return false;
            }
        } else {
            System.out.println("ERROR - Not authorized to change weight  as " + Administration.currentUser.getRole());
            return false;
        }
    }

    public void setBMI() {
        bmi = weight/ ((height/100)*(height/100));
    }

    public void setBMILog() {
        long currentTime = System.currentTimeMillis() / 1000;
        if (bmiLog.isEmpty()) {
            if (weight == 0 || height == 0) {
                bmiLog.add("");
            } else {
                bmiLog.add(Math.round((weight / ((height / 100) * (height / 100))) * 10) / 10f + "-" + currentTime);
            }
        } else {
            // Only once every minute
            if (Integer.parseInt(bmiLog.getLast().split("-")[1]) < currentTime - 60) {
                bmiLog.add(Math.round((weight / ((height / 100) * (height / 100))) * 10) / 10f + "-" + currentTime);
            }
        }
    }
}
