import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class MedStorage implements Serializable {

    private HashMap<String, Double> medStorageAttributes = new HashMap<>();

    MedStorage() {
        medStorageAttributes.put("Paracetamol", 100.0);
        medStorageAttributes.put("Diclofenac", 100.0);
        medStorageAttributes.put("Ibuprofen", 100.0);
        medStorageAttributes.put("Antibiotics", 100.0);
    }

    public HashMap<String, Double> getAttributes() {
        return medStorageAttributes;
    }

    public void setAttribute(String key, Double value) {
        medStorageAttributes.put(key, value);
    }
}

