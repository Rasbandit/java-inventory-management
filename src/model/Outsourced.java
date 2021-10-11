package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** An Outsourced part that extends the abstract class Part */
public class Outsourced extends Part {
    String companyName;

    private static ObservableList<Outsourced> allOutsourced = FXCollections.observableArrayList();

    /** Outsourced Constructor. */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Sets the companyName for the Outsourced Part. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** Gets the companyName for the Outsourced Part. */
    public String getCompanyName() {
        return this.companyName;
    }

    static {
        init();
    }

    /** Creates test data */
    private static void init() {
        if(allOutsourced.size() == 0) {
            allOutsourced.add(new Outsourced(13, "Garth", 24d, 1, 1, 10, "big bobs"));
            allOutsourced.add(new Outsourced(14, "Shannon", 22d, 2, 1, 10, "big bobs"));
            allOutsourced.add(new Outsourced(15, "Emily", 29d, 2, 1, 10, "big bobs"));
        }
    }

    /** Deletes the provided Part from the allOutsourced list. */
    public static void deletePart(Part selectedPart) {
        for (Integer i = 0; i < allOutsourced.size(); i++) {
            if (allOutsourced.get(i).getId() == selectedPart.getId()) {
                allOutsourced.remove(allOutsourced.get(i));
            }
        }
    }

    /** Returns a single Part by Id */
    public static Outsourced getPart(Integer id) {
        for (Outsourced part : allOutsourced) {
            if (part.getId() == id) {
                return part;
            }
        }
        return null;
    }

    /** Add an Outsourced Part to the allOutsourced list. */
    public static void addItem(Outsourced item) {
        allOutsourced.add(item);
    }

    /** Returns the allOutsourced list */
    public static ObservableList<Outsourced> getAllOutsourced() {
        return allOutsourced;
    }
}
