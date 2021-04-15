package Main;

import Main.Drinks.Drink;
import Main.Drinks.DrinkDB;
import Main.LoyaltyCards.LoyaltyDB;
import Main.Panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This is the main class for the vending machine demo program
 * @author James Martland
 */
public class VendingMachine {

    private final JFrame frame = new JFrame("Vending Machine");
    private final JPanel mainPanel = new JPanel( null );
    private final WindowListener exitListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {

        }
    };
    //Main Screen
    protected static JTextArea txtScreen = new JTextArea();

    // Panels
    private DrinkViewer DV;
    private JPanel pnlDrinkViewer = new JPanel( null );
    protected static String[][] drinkTableData = new String[6][3];

    private Payment P;
    private JPanel pnlPayment = new JPanel( null );

    private KeypadAndDispenser K;
    private JPanel pnlKeypad = new JPanel( null );

    // End of panels

    // Variables
    private static double balance = 0.0;
    private static String cardNo = "###";
    protected static LoyaltyDB db = new LoyaltyDB();
    protected static DrinkDB ddb = new DrinkDB();

    /**
     * This is the main method
     * @param args
     */
    public static void main( String[] args) {
        VendingMachine vm = new VendingMachine();
        vm.start();
    }

    /**
     * This is the primary method that is called to start and build the vending machine demo
     */
    public void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch( Exception e ) {
            //do nothing
        }

        print( "Starting Interface" );

        this.frame.setTitle( "Vending Machine" );
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(520, 600);
        this.mainPanel.setSize(500, 600);
        this.frame.addWindowListener(exitListener);
        this.frame.setLayout( new GridLayout(1,1) );
        this.frame.setResizable(false);
        print( "init complete" );

        db.createCards();
        ddb.createDrinks();

        buildAllPanels();
    }

    /**
     * This method sets up all threads to build panels and then adds them to the frame
     */
    public void buildAllPanels() {

        DV = new DrinkViewer();
        Thread buildDrinkViewer = new Thread( DV );
        P = new Payment();
        Thread buildPayment = new Thread( P );
        K = new KeypadAndDispenser();
        Thread buildKeypad = new Thread( K );


        print("Starting Threads");
        buildDrinkViewer.start();
        buildPayment.start();
        buildKeypad.start();

        try {
            buildDrinkViewer.join();
            this.pnlDrinkViewer = DV.getPanel();
            buildPayment.join();
            this.pnlPayment = P.getPanel();
            buildKeypad.join();
            this.pnlKeypad = K.getPanel();
            formPanels();
        } catch ( InterruptedException e) {
            print("Error Building GUI... shutting down");
            System.exit(0);
        }
    }

    /**
     * This method positions panels on the frame
     */
    public void formPanels() {
        pnlPayment.setLocation(0,0);
        this.mainPanel.add(pnlPayment);

        pnlDrinkViewer.setLocation(125, 0);
        this.mainPanel.add(pnlDrinkViewer);

        pnlKeypad.setLocation(375,0);
        this.mainPanel.add(pnlKeypad);

        this.frame.add(mainPanel);

        this.frame.setVisible(true);
    }

    /**
     * This method adds amounts to the balance stored
     * @param n
     */
    public void addToBalance(double n){
        balance += n;
        print("Adding " + n + " to Balance\nNew Bal: " + balance);
        notify("New Bal: \n" + getBalance(), 2000, txtScreen.getText() );
    }

    /**
     * This method clears the balance of the system
     */
    public void clearBalance() {
        print("Balance Cleared");
        balance = 0;
    }

    /**
     * This method processes the payment for the drink
     * @param cost
     * @return
     */
    public boolean pay(double cost) {
        if ( cost <= balance ) {
            balance -= cost;
            return true;
        } else if ( cardNo.equals("###") == false) {
            // return makePayment from loyaltyDB

            return db.makePayment(cardNo, cost*0.95);
        }else {
            return false;
        }
    }

    /**
     * This is a getter method for balance
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * This method clears the loyalty card
     */
    public void clearCard() {
        this.cardNo = "###";
    }

    /**
     * This method sets the card number
     * this is called after the card has been validated
     * @param number
     */
    public void setCardNo(String number) {
        this.cardNo = number;
    }

    /**
     * This method is to notify the user what is going on in the system via the screen
     * @param notifText this is the text to notify the user
     * @param duration this is how long in millis
     * @param newText this is the text that will be displayed after the duration has finished
     */
    public void notify(String notifText, int duration, String newText){
        print("NOTIFY: " + notifText);
        new Thread() {
            @Override
            public void run() {
                txtScreen.setText(notifText);
                try {
                    Thread.sleep(duration);
                } catch (
                        InterruptedException ie) {
                    //do nothing
                } finally {
                    txtScreen.setText(newText);
                }
            }
        }.start();
    }

    /**
     * This is an easy print statement to aid in debugging
     * @param text
     */
    public void print(String text) {
        System.out.println(text);
    }
}

