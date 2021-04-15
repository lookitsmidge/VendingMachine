package Main.Drinks;

/**
 * This class is to model a drink object
 * @author James Martland
 */
public class Drink {
    String type;
    int amount = 0;
    double cost;
    enum Status {
        IN_STOCK,
        EMPTY
    }
    Status status;


    /**
     * Constructor for drink
     * @param type
     * @param amount
     */
    public Drink(String type, int amount, double price) {
        this.type = type;
        this.amount = amount;
        this.cost = price;
        updateStatus();
    }

    /**
     * This method decrements the amount of available drinks of this type
     */
    public void purchaseMade(){
        amount--;
        updateStatus();
        System.out.println("Amount: " + amount);
    }

    /**
     * This method is to retrieve the status of the drink
     * @return
     */
    public Status getStatus() {
        return status;
    }

    /**
     * This method is to retrieve the type of drink
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * This method is to retrieve the cost of the drink
     * @return
     */
    public double getCost() {
        return cost;
    }

    /**
     * This method is to check availability of a drink
     * @return
     */
    public boolean available(){
        boolean r = false;
        switch(status) {
            case IN_STOCK:
                r = true;
                break;
            default:
                r = false;
                break;
        }
        return r;
    }

    /**
     * This is my unique format for showing it in a jtable
     * @return
     */
    public String formatUnique() {
        return ":\t£"+String.format("%.2f", cost) + " <br> " + type;
    }

    /**
     * This is an internal method that updates status
     */
    private void updateStatus() {
        if ( amount >= 1 ) {
            status = Status.IN_STOCK;
        } else {
            status = Status.EMPTY;
        }    }
}
