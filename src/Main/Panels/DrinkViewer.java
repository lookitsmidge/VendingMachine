package Main.Panels;

import Main.BuildUtilitiesVendV1;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is for the section that lets the user see what drinks are available
 * @author James Martland
 */
public class DrinkViewer extends BuildUtilitiesVendV1 implements Runnable{
    JLabel lblTemp = new JLabel();

    String[] headings = {"A", "B", "C"};
    DefaultTableModel drinkTableModel = new DefaultTableModel(drinkTableData, headings);
    JTable tableDrinks = new JTable(drinkTableModel);

    @Override
    public void run() {
        panel = new JPanel(null);

        panel.setSize(250, 600);

        //Table stuff
        addElements();
        drinkTableModel = new DefaultTableModel(drinkTableData, headings);
        tableDrinks = new JTable(drinkTableModel);
        tableDrinks.setRowHeight(50);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
        tableDrinks.setDefaultRenderer(String.class, centerRenderer);

        addTable(tableDrinks, panel, 1, 100, 248, 500);

    }

    /**
     * This method decodes the ABC values on the keypad into an index for an array
     * @param x
     * @return
     */
    public String getLetter(int x){
        switch(x){
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            default:
                return "ERROR";
        }
    }

    /**
     * This method adds the drinks to an array list and then returns it
     * @return
     */
    public ArrayList populateDrinks(){
        ArrayList<String> d = new ArrayList<String>();
        d.add(":\t£1.50 <br> Pepsi");
        d.add(":\t£1.60 <br> Cherry pepsi");
        d.add(":\t£1.50 <br> Water");
        d.add(":\t£2.50 <br> Monster");
        d.add(":\t£1.20 <br> Vimto");
        d.add(":\t£1.00 <br> Lucazade");
        d.add(":\t£1.50 <br> Coca Cola");
        d.add(":\t£3.00 <br> Hot Chocolate");
        for(int i=0;i<10;i++){
            d.add("<br> EMPTY");
        }
        return d;
    }

    /**
     * This method adds elements to the table and also the array
     */
    public void addElements() {
        print("Adding elements");
        ArrayList<String> drinks = ddb.getAL(18);
        //ArrayList<String> drinks = populateDrinks();
        Iterator<String> drink = drinks.iterator();

        for(int y=0; y<6; y++) {

            for( int x=0; x<3; x++) {
                // http://www.jguru.com/faq/view.jsp?EID=121123
                drinkTableData[y][x] = "<html><body>" + getLetter(x) + (y+1) + drink.next() + "<br></body></html>";
                print("added: " + drinkTableData[y][x] );
            }
        }
    }

}
