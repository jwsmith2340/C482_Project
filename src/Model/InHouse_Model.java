package Model;

/**
 * Class for creating InHouse Models of Part, it's super class.
 */
public class InHouse_Model extends Part {

    private int machineId;

    /**
     * The class constructor, this instantiates an object using the super class, Part, as
     * well as the inhouse specific machineId value to create an InHouse object.
     * @param id
     * @param name
     * @param stock
     * @param price
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse_Model(int id, String name, int stock, double price, int min, int max, int machineId) {
        super(id, name, stock, price, min, max);
        this.machineId = machineId;
    }

    /**
     * Returns the machineId
     * @return
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets the machineId
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
