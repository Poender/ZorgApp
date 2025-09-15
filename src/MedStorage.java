import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class MedStorage implements Serializable {
    private static final long serialVersionUID = 1L;

    private HashMap<String, Double> medStorageAttributes = new HashMap<>();

    // Constructor only if using meds for first time, and medstorage.ser is not available
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

