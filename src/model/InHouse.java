package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** An InHouse Part that extends the abstract class Part. */
public class InHouse extends Part {
    int machineId;

    private static ObservableList<InHouse> allInHouse = FXCollections.observableArrayList();

    /** Constructor for the InHouse class. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;

    }

    /** Updates the machineId of the instance. */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** Gets the machineId of the instance. */
    public int getMachineId() {
        return this.machineId;
    }

    static {
        init();
    }

    /** Creates test data for InHouse Parts */
    private static void init() {
        if(allInHouse.size() == 0) {
            allInHouse.add(new InHouse(3, "bob", 24d, 1, 1, 10, 1));
            allInHouse.add(new InHouse(4, "allen", 22d, 2, 1, 10, 2));
        }
    }

    /** Deletes the InHouse Part from the allInHouse list. */
    public static void deletePart(Part selectedPart) {
        for (Integer i = 0; i < allInHouse.size(); i++) {
            if (allInHouse.get(i).getId() == selectedPart.getId()) {
                allInHouse.remove(allInHouse.get(i));
            }
        }
    }

    /** Gets a single InHouse Part by id. */
    public static InHouse getPart(Integer id) {
        for (InHouse part : allInHouse) {
            if (part.getId() == id) {
                return part;
            }
        }
        return null;
    }

    /** Add an InHouse Part to the allInHouse list */
    public static void addItem(InHouse item) {
        allInHouse.add(item);
    }

    /** Return the allInHouse list */
    public static ObservableList<InHouse> getAllInHouse() {
        return allInHouse;
    }
}
