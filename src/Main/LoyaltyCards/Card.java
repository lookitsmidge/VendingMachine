package Main.LoyaltyCards;

/**
 * This class models the loyalty card that will be used to pay
 * @author James Martland
 */
public class Card {
    String num;
    double balance;

    /**
     * Constuctor for card ( to set details )
     * @param number
     * @param bal
     */
    public Card(String number, double bal) {
        this.num = number;
        this.balance = bal;
    }

    /**
     * This is a getter for card number
     * @return num
     */
    public String getNum() {
        return num;
    }

    /**
     * This is a getter for balance
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * This is to add amounts of money to the card ( not currently in use )
     * @param n
     */
    public void addToBalance(double n) {
        this.balance += n;
    }

    /**
     * This method is to make a payment onto a card ( even though the new updated details are not saved because this is a demo
     * @param price
     * @return
     */
    public boolean makePayment(double price) {
        if( price <= balance ) {
            balance -= price;
            System.out.println( "New Card Balance: " + balance );
            return true;
        } else {
            return false;
        }
    }
}
