import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Integer   id;
    private String          surname;
    private String          firstName;
    private Character       gender;
    private LocalDate       dateOfBirth;
    private Double          height;
    private Double          weight;
    private Double          bmi;
    private Double          lungCap;

    private HashMap<String, Double> medsMap;
    private ArrayList<String> bmiLog;
    private ArrayList<Consult> consults;

    /**
     * Constructor
     */

    Patient(Integer id, String surname, String firstName, Character gender, LocalDate dateOfBirth, Double height, Double weight, ArrayList<String> bmiLog) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        if (this.weight != null || this.height != null) {
            this.bmiLog = bmiLog;
            bmi = getBMI();
        }
        medsMap = new HashMap<>();
        //this.setBMILog();
    }

    public void updatePatient(Patient patient, String newSurname, String newFirstName, Character newGender, LocalDate newDateOfBirth, Double newHeight, Double newWeight, Double newLungCap) {
        if (newFirstName == null) {newFirstName = patient.getFirstName();}
        if (newSurname == null) {newSurname = patient.getSurname();}
        if (newGender == null) {newGender = patient.getGender();}
        if (newDateOfBirth == null) {newDateOfBirth = patient.getDateOfBirth();}
        if (newHeight == null) {newHeight = patient.getHeight();}
        if (newWeight == null) {newWeight = patient.getWeight();}
        if (newLungCap == null) {newLungCap = patient.getLungCap();}

        patient.setFirstName(newFirstName);
        patient.setSurname(newSurname);
        patient.setGender(newGender);
        patient.setDateOfBirth(newDateOfBirth);
        patient.setHeight(newHeight);
        patient.setWeight(newWeight);
        patient.setLungCap(newLungCap);

        if (patient != Administration.currentPatient) {
            Administration.patientArray.add(patient);
            System.out.format("\nSUCCES - New patient %s added", patient.getFirstName());
            // Switch to new patient
            Administration.currentPatient = patient;
        }
        Serializer.serializePatient(patient);
    }

    String fullName() {
        return String.format("%-15s %-10s [%s]", surname+",", firstName, dateOfBirth);
    }

    public long intCalc() {
        return (LocalDate.now().toEpochDay() - getDateOfBirth().toEpochDay())/365;
    }

    // Consults get/set
    public ArrayList<Consult> getConsults() {
        return consults;
    }
    public void addConsult(Consult consult) {
        if (consults == null) {
            consults = new ArrayList<Consult>();
        }
        consults.add(consult);
    }

    // Medication get/set
    public HashMap<String, Double> getMeds() {
        return medsMap;
    }
    public void setMeds(String key, Double value) {
        if (value != 0) {
            medsMap.put(key, value);
        } else {
            medsMap.remove(key);
        }
    }

    // GETTERS:
    public int getPatientID() {
        return id;
    }


    public String getFirstName() {return firstName;}
    public String getSurname() {return surname;}
    public Character getGender() {return gender;}
    public LocalDate getDateOfBirth() {return dateOfBirth;}
    public Double getHeight() {return height;}
    public Double getWeight() {return weight;}
    public Double getLungCap() {return lungCap;}
    public ArrayList<String> getBMILog() {return bmiLog;}

    public Double getBMI() {
        if (weight != 0 && height != 0) {
            bmi = weight / ((height / 100) * (height / 100));
            return bmi;
        }
        return 0.0;
    }

    // SETTERS:
    private void setFirstName(String firstName) {
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        this.firstName = firstName;
    }
    private void setSurname(String surname) {
        surname = surname.substring(0, 1).toUpperCase() + surname.substring(1);
        this.surname = surname;
    }

    private void setGender(Character gender) {
        if (gender == 'm' || gender == 'v' || gender == 'x') {
            this.gender = gender;
        } else {
            System.out.println("ERROR - Gender must be m, v or x");
        }
    }

    private void setDateOfBirth(LocalDate dateOfBirth) {
        try {
            this.dateOfBirth = dateOfBirth;
        } catch (Exception e) {
            System.out.println("ERROR - Invalid date of birth");
        }
    }

    private void setHeight(Double height) {
        try {
            if (height >= 0) {
                this.height = height;
            } else {
                System.out.println("ERROR - Height can't be negative!");
            }
        } catch (Exception e) {
            System.out.println("ERROR - Height has to be a number!");
        }
    }

    private void setWeight(Double weight) {
        try {
            if (weight >= 0) {
                this.weight = weight;
            } else {
                System.out.println("ERROR - Weight can't be negative!");
            }
        } catch (Exception e) {
            System.out.println("ERROR - Weight has to be a number!");
        }
    }

    private void setLungCap(Double lungCap) {
        try {
            if (lungCap >= 0) {
                this.lungCap = lungCap;
            } else {
                System.out.println("ERROR - Lung capacity can't be negative!");
            }
        } catch (Exception e) {
            System.out.println("ERROR - Lung capacity has to be a number!");
        }
    }

    void setBMI() {
        bmi = weight/ ((height/100)*(height/100));
    }

    void setBMILog() {
        long currentTime = System.currentTimeMillis() / 1000;
        //System.out.println(bmiLog);

        if (weight == 0 || height == 0) {
            bmiLog.add("0-0");
        } else {
            bmiLog.add(Math.round((weight / ((height / 100) * (height / 100))) * 10) / 10f + "-" + currentTime);
        }
    }
}
