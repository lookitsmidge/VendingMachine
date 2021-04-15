package Main.Panels;

import Main.BuildUtilitiesVendV1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is to manage the keypad for user input and also the drink dispenser
 * @author James Martland
 */
public class KeypadAndDispenser extends BuildUtilitiesVendV1 implements Runnable, ActionListener {

    JButton btnA = new JButton();
    JButton btnB = new JButton();
    JButton btnC = new JButton();
    JButton btnClear = new JButton();
    JButton btn1 = new JButton();
    JButton btn2 = new JButton();
    JButton btn3 = new JButton();
    JButton btn4 = new JButton();
    JButton btn5 = new JButton();
    JButton btn6 = new JButton();
    JButton btnConfirm = new JButton();

    // Dispenser
    JLabel lblDrinkOut = new JLabel();
    JTextArea txtDispenser = new JTextArea();

    @Override
    public void run() {
        print("Thread - Building Keypad And Dispenser");
        initPanel();
        print("Thread - Finished Keypad And Dispenser");
    }

    /**
     * This method initialises all the components for this section
     */
    public void initPanel() {
        panel = new JPanel(null);
        panel.setSize(125, 600);

        addTextArea(txtScreen, panel, 3, 100, 120, 50);

        addButton(this, panel, btnA, 3, 155, "A", 40, 40, "A");

        addButton(this, panel, btn1, 43, 155, "1", 40, 40, "1");

        addButton(this, panel, btn2, 83, 155, "2", 40, 40, "2");

        addButton(this, panel, btnB, 3, 205, "B", 40, 40, "B");

        addButton(this, panel, btn3, 43, 205, "3", 40, 40, "3");

        addButton(this, panel, btn4, 83, 205, "4", 40, 40, "4");

        addButton(this, panel, btnC, 3, 255, "C", 40, 40, "C");

        addButton(this, panel, btn5, 43, 255, "5", 40, 40, "5");

        addButton(this, panel, btn6, 83, 255, "6", 40, 40, "6");

        addButton(this, panel, btnClear, 3, 305, "Clr", 57, 40, "Clear");

        addButton(this, panel, btnConfirm, 63, 305, "OK", 57, 40, "Confirm");

        // Dispenser
        addLabel(lblDrinkOut, panel, 10, 350, "Drink Dispenser", 100, 50 );

        txtDispenser.setEditable(false);
        addTextArea(txtDispenser, panel, 10, 400, 105, 100);


    }

    /**
     * This method adds a string to the main screen
     * @param t
     */
    public void append(String t){
        txtScreen.append(t);
    }

    /**
     * This method dispenses the drink ( because this is a demo it says it is dispensing and identified the drink )
     * @param text
     */
    public void dispense(String text) {
        print("Dispensing");
        new Thread() {
            @Override
            public void run() {
                txtDispenser.setText(text);
                try{
                    Thread.sleep(2000);
                } catch ( InterruptedException e) {
                    print("thread Interrupted");
                } finally {
                    clearDispenser();
                }
            }
        }.start();
    }

    /**
     * This method clears the dispenser text box
     */
    public void clearDispenser() {
        txtDispenser.setText("");
    }

    public void validateV2(String text){
        switch( text.length() ) {
            case 2:
                //correct
                if( text.charAt(0) == 'A' || text.charAt(0) == 'B' || text.charAt(0) == 'C' ) {
                    //correct ---> next check
                    if( text.charAt(1) != 'A' && text.charAt(1) != 'B' && text.charAt(1) != 'C' ) {
                        print("correct");
                        if( ddb.isAvailable(text) == true ) {
                            // drink is available

                            String drink = ddb.getType(text);
                            double cost = ddb.getCost(text);

                            if( pay(cost) == true ) {
                                // payment has been processed
                                ddb.purchaseDrink(text);
                                dispense(drink);
                                clearCard();
                            } else {
                                notify("Not Enough\n Balance", 2000, "");
                            }
                        } else {
                            notify("Out of\nStock", 2000, "");
                        }

                    } else {
                        notify("Incorrect\nSelection", 2000, "");
                    }
                } else {
                    notify("Incorrect\nSelection", 2000, "");
                }
                break;
            default:
                notify("Incorrect\nSelection", 2000, "");
                break;
        }
    }


    /**
     * This method validates the input and then processes it
     * @param text
     */
    public void validate(String text) {
        switch( text.length() ) {
            case 2:
                //correct
                if( text.charAt(0) == 'A' || text.charAt(0) == 'B' || text.charAt(0) == 'C' ) {
                    //correct ---> next check
                    if( text.charAt(1) != 'A' || text.charAt(1) != 'B' || text.charAt(1) != 'C' ) {
                        //correct again
                        int col = 0;
                        switch(text.charAt(0)) {
                            case 'A':
                                col=0;
                                break;
                            case 'B':
                                col=1;
                                break;
                            case 'C':
                                col=2;
                                break;
                        }
                        int row = 0;
                        try {
                            row = Integer.parseInt("" + text.charAt(1) );
                            row--;
                            String drink = drinkTableData[row][col].split("<br>")[1].substring(1);
                            //check balance
                            try {
                                double cost = Double.parseDouble(
                                        drinkTableData[row][col].split("<br>")[0].replace("£", "").substring(16)
                                );

                                if( pay(cost) == true ) {
                                    // payment has been processed
                                    ddb.purchaseDrink(text);
                                    dispense(drink);
                                    clearCard();
                                } else {
                                    notify("Not Enough\n Balance", 2000, "");
                                }
                            } catch ( NumberFormatException e ){
                                notify("Internal\nError", 2000, "");
                            }
                        } catch ( NumberFormatException e) {
                            notify("Error:\nTry Again", 2000, "");
                        }
                    } else {
                        notify("Incorrect\nSelection", 2000, "");
                    }
                } else {
                    notify("Incorrect\nSelection", 2000, "");
                }
                break;
            default:
                notify("Incorrect\nSelection", 2000, "");
                break;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnA) {
            append("A");
        }
        if(e.getSource() == btnB) {
            append("B");
        }
        if(e.getSource() == btnC) {
            append("C");
        }
        if(e.getSource() == btnClear) {
            txtScreen.setText("");
        }
        if(e.getSource() == btnConfirm) {
            // dispense drink ( method in VendingMachine to check cost and shit )
            validateV2(txtScreen.getText());
            txtScreen.setText("");
            //notify("Dispensing", 3000);
        }
        if(e.getSource() == btn1) {
            append("1");
        }
        if(e.getSource() == btn2) {
            append("2");
        }
        if(e.getSource() == btn3) {
            append("3");
        }
        if(e.getSource() == btn4) {
            append("4");
        }
        if(e.getSource() == btn5) {
            append("5");
        }
        if(e.getSource() == btn6) {
            append("6");
        }
    }
}
