package Main.Panels;

import Main.BuildUtilitiesVendV1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is to manage the payment area ( Left ) of the vending machine. this manages the insertion of cash,
 * scanning of card numbers and dispensing of change
 * @author James Martland
 */
public class Payment extends BuildUtilitiesVendV1 implements Runnable, ActionListener {

    JLabel lblSectionTitle = new JLabel();
    JTextField txtCashInput = new JTextField();
    JButton btnSubmit = new JButton();
    JButton btnChangeOut = new JButton();
    JTextArea txtCashOut = new JTextArea();

    // Loyalty Card
    JLabel lblScannerTitle = new JLabel();
    JTextField txtCardNo = new JTextField();
    JButton btnSubmitCard = new JButton();

    @Override
    public void run() {
        print("Thread - Building Payment Panel");
        initPanel();
        print("Thread - Finished Payment Panel");
    }

    /**
     * This method initialises the panel from the thread and build its components
     */
    public void initPanel() {
        print("Building CashIn");
        panel = new JPanel(null);
        panel.setSize(125, 600);

        addLabel(lblSectionTitle, panel, 10, 50, "Cash Input", 100, 50);

        addTextField(txtCashInput, panel, 10, 100, 100, 50);

        addButton(this, panel, btnSubmit, 10, 150, "Insert", 100, 50, "this button will input the cash");

        addButton(this, panel, btnChangeOut, 10, 200, "Change", 100, 50, "This button will eject change");

        txtCashOut.setEditable( false );
        addTextArea(txtCashOut, panel, 10, 250, 100, 100);

        print("Finished CashIn");
        //Loyalty Scanning
        print("Building LoyaltyScan");
        addLabel(lblScannerTitle, panel, 10, 350, "Scanner", 100, 50);

        addTextField(txtCardNo, panel, 10, 400, 100, 50);

        addButton(this, panel, btnSubmitCard, 10, 450, "Submit Number",
                100, 50, "THis button will submit the card number");

        print("Finished LoyaltyScan");
    }

    /**
     * This method is to dispense the change for the system
     * if this wasnt a demo then it would actually send the double figure to a dispensing subsystem
     */
    public void changeDispense() {
        print("Change Dispensing");
        txtCashOut.setText("Cash Ejected:\n£" + String.format("%.2f",getBalance()) );
        clearBalance();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch( InterruptedException e ){
                    //do nothing
                } finally{
                    txtCashOut.setText("");
                }
            }
        }.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSubmit ) {
            print("Cash Input Submit");
            try {
                addToBalance( Double.parseDouble(txtCashInput.getText()) );
                txtCashInput.setText("");
            } catch( NumberFormatException nfe ) {
                JOptionPane.showMessageDialog(null, "Cash inserted was not a number");
            }

        }
        if(e.getSource() == btnSubmitCard ) {
            if( db.verifyCardNumber(txtCardNo.getText()) == true ) {
                setCardNo(txtCardNo.getText());
                notify("Scan Successful", 1500, txtScreen.getText());
            } else {
                notify("Scan unsuccessful", 2000, txtScreen.getText());
            }
            txtCardNo.setText("");
            // check card number and balance
            // if balance is enough then do transaction
        }
        if(e.getSource() == btnChangeOut ) {
            changeDispense();
        }
    }
}
