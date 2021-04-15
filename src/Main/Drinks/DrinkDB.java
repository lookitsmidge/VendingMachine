package Main.Drinks;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class holds all the drinks that are to be stores in the vending machine
 * @author James Martland
 */
public class DrinkDB {

    ArrayList<Drink> database = new ArrayList<Drink>();

    /**
     * This method creates some drinks to test on
     */
    public void createDrinks() {
        database.add( new Drink("Pepsi", 5, 1.5) );
        database.add( new Drink("Cherry Pepsi", 6, 1.6) );
        database.add( new Drink("Water", 10, 1.5) );
        database.add( new Drink("Monster", 8, 2.5) );
        database.add( new Drink("Vimto", 6, 1.2) );
        database.add( new Drink("Lucazade", 2, 1) );
        database.add( new Drink("Coca Cola", 1, 1.5) );
        database.add( new Drink("Hot Chocolate", 0, 3) );
    }

    /**
     * This method is to check if the drink is available
     * @param code
     * @return
     */
    public boolean isAvailable(String code){
        int index = decipher(code);
        if( index == -1 ){
            return false;
        } else {
            return ( (Drink) database.toArray()[index]).available();
        }
    }

    /**
     * This method is to decrement the amount of drink left
     * @param code
     */
    public void purchaseDrink(String code) {
        int index = decipher(code);
        if( index == -1 ){
        } else {
            database.get(index).purchaseMade();
        }
        System.out.println( "Available: " + ( (Drink) database.toArray()[index]).available() );
    }

    /**
     * This method is to get the array list so that it can be used to fill the jtable
     * @param itemCount
     * @return
     */
    public ArrayList<String> getAL(int itemCount) {
        ArrayList<String> d = new ArrayList<String>();
        Iterator<Drink> drink = database.iterator();

        for(int i=0; i<itemCount; i++) {
            if( drink.hasNext() ) {
                d.add( drink.next().formatUnique() );
            } else {
                d.add("<br> EMPTY");
            }
        }
        return d;
    }

    /**
     * This method is to get the type of drink
     * @param code
     * @return
     */
    public String getType(String code) {
        int index = decipher(code);
        if( index == -1 ){
            return "ERROR";
        } else {
             return ( (Drink) database.toArray()[index]).getType();
        }
    }

    /**
     * This method is to get the cost of the drink
     * @param code
     * @return
     */
    public double getCost(String code) {
        int index = decipher(code);
        if( index == -1 ){
            return 2.0;
        } else {
            return ( (Drink) database.toArray()[index]).getCost();
        }
    }

    /**
     * This method is to decypher the code that is sent that references to a drink
     * @param code
     * @return
     */
    public int decipher(String code){
        int index = -1;
        try {
            index = Integer.parseInt(code.charAt(1) + "");
            index--;
            index *= 3;
            switch(code.charAt(0)) {
                case 'A':
                    //do nothing
                    break;
                case 'B':
                    index++;
                    break;
                default:
                    index++;
                    index++;
                    break;
            }
        } catch ( NumberFormatException nfe ) {
            //do nothing
        }
        return index;
    }


}
