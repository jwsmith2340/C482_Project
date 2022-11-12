package Model;

/**
 * Class for creating Outsourced Models of Part, it's super class.
 */
public class Outsourced_Model extends Part {

    private String companyName;

    /**
     * The class constructor, this instantiates an object using the super class, Part, as
     * well as the outsourced specific companyName value to create an Outsourced_Model object.
     * @param id
     * @param name
     * @param stock
     * @param price
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced_Model(int id, String name, int stock, double price, int min, int max, String companyName) {
        super(id, name, stock, price, min, max);
        this.companyName = companyName;
    }

    /**
     * Returns the companyName
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the companyName
     * @param companyName
     */
    public void setCompanyName(int companyName) {
        this.companyName = String.valueOf(companyName);
    }

}
