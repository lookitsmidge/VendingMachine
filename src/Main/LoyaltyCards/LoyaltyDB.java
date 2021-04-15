package Main.LoyaltyCards;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class acts as the bank database that holds the loyalty card numbers and balances
 * it houses a verification feature and also a make payment feature
 * @author James Martland
 */
public class LoyaltyDB{

    ArrayList<Card> database = new ArrayList<Card>();

    /**
     * This is for testing purposes seeing as though this is a demo
     */
    public void createCards() {
        database.add(new Card("001", 100.00) );
        database.add(new Card("111", 0.50) );
    }

    /**
     * This is to verify the card number
     * @param number
     * @return
     */
    public boolean verifyCardNumber(String number) {
        Iterator i = database.iterator();
        boolean r = false;
        while( i.hasNext() ) {
            if( number.equals( ((Card) i.next()).getNum() ) ) {
                r = true;
                break;
            } else {
            }
        }
        return r;
    }

    /**
     * This is to make a payment on a card number
     * @param number
     * @param cost
     * @return
     */
    public boolean makePayment(String number, double cost) {
        Iterator i = database.iterator();
        boolean r = false;
        Card c;
        while( i.hasNext() ) {
            c = (Card) i.next();
            if (number.equals((c.getNum()))) {
                r = c.makePayment(cost);
                break;
            } else {
            }
        }
        return r;
    }
}
