import java.util.HashMap;

public class MedStorage {
    private HashMap<String, Double> medStorageAttributes = new HashMap<>();

    MedStorage() {
        medStorageAttributes.put("Paracetamol", 100.0);
        medStorageAttributes.put("Diclofenac", 100.0);
        medStorageAttributes.put("Ibuprofen", 100.0);
        medStorageAttributes.put("Antibiotics", 100.0);
    }

    public HashMap<String, Double> getAttributes() { return medStorageAttributes; }
    public void setAttribute(String key, Double value) { medStorageAttributes.put(key, value); }


    public void setParacetamol(Double amount) {
        medStorageAttributes.put("Paracetamol", amount);
    }
    public void setDiclofenac(Double amount) {
        medStorageAttributes.put("Diclofenac", amount);
    }
    public void setIbuprofen(Double amount) {
        medStorageAttributes.put("Ibuprofen", amount);
    }
    public void setAntibiotics(Double amount) {
        medStorageAttributes.put("Antibiotics", amount);
    }
}


