package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** A class for managing a list of all parts both InHouse and Outsourced. */
public class AllParts {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    static {
        gatherAllParts();
    }

    /** Add a part to the parts list. */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /** Return allParts. */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /** Combine the list of Outsourced and InHouse Parts */
    public static void gatherAllParts() {
        allParts = FXCollections.observableArrayList();
        ObservableList<InHouse> allInHouse = InHouse.getAllInHouse();
        ObservableList<Outsourced> allOutsourced = Outsourced.getAllOutsourced();

        for(InHouse inHouse : allInHouse) {
            addPart(inHouse);
        }
        for(Outsourced outsourced: allOutsourced) {
            addPart(outsourced);
        }
    }
}
