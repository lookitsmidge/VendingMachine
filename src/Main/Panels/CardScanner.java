package Main.Panels;

import Main.BuildUtilitiesVendV1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardScanner extends BuildUtilitiesVendV1 implements Runnable, ActionListener {
    JLabel lblSectionTitle = new JLabel();
    JTextField txtCardNo = new JTextField();
    JButton btnSubmitCard = new JButton();

    @Override
    public void run() {
        print("Thread - Building CardScanner");
        initPanel();
        print("Thread - Building CardScanner");
    }

    public void initPanel() {
        panel = new JPanel(null);
        panel.setSize(125, 300);

        addLabel(lblSectionTitle, panel, 10, 50, "Scanner", 100, 50);

        addTextField(txtCardNo, panel, 10, 100, 100, 50);

        addButton(this, panel, btnSubmitCard, 10, 150, "Submit Number",
                100, 50, "THis button will submit the card number");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSubmitCard ) {
            // check card number and balance
            // if balance is enough then do transaction
        }
    }
}
