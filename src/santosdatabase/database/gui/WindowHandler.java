/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static santosdatabase.SantosDatabase.log;
import santosdatabase.database.Queuer;
import santosdatabase.database.SanConnection;
import santosdatabase.database.type.Machine;

/**
 *
 * @author justdasc
 */
public class WindowHandler {

    public static boolean stop = false;
    public static boolean pause = false;
    public static Queuer q;

    public WindowHandler() {
        String command = getQuery();
        if (new SQLParser().validate(command)) {
            q = new Queuer(command);
        } else {
            stop = true;
        }
    }

    public Machine getSingleMachine() {
        return null;
    }

    /**
     * Gets the query statement from the window. We don't want direct access in
     * each method incase the access changes so we make one method to handle
     * this.
     *
     * @return Returns the query statement from the window.
     */
    private String getQuery() {
        return SantosWindow.queryField.getText();
    }

    /**
     * Do this when the run button is clicked.
     */
    public void buttonRun() {

    }

    /**
     * This clears the query field. There is a clear all and clear to allow easy
     * clearing of the query statement field since that may need to be cleared
     * by itself more often.
     */
    public void buttonClear() {
        //Setting the text to "" to clear.
        SantosWindow.queryField.setText("");
    }

    /**
     * This clears all the fields. This allows for a complete reset of all
     * fields incase a new set of queries need to be made or the user wants to
     * clear the windows back to when they opened up the application.
     */
    public void buttonClearAll() {
        //Use what we already have.
        //buttonClear();
        //And clear the rest.
        SantosWindow.hostField.setText("Host");
        SantosWindow.databaseField.setText("DB");
        SantosWindow.driverBox.setSelectedIndex(0);
        SantosWindow.passField.setText("");
        SantosWindow.userField.setText("Username");
        SantosWindow.previewTable.setModel(new DefaultTableModel());
    }

    /**
     * This method decides if the run is a single entry or is multiple runs from
     * a predefined list. This method is mainly a decider method.
     */
    public void typeRun() {

        if (stop) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "You may not use these commands in a SQL statement. Please use read only commands.",
                    "Blocked Commands",
                    JOptionPane.ERROR_MESSAGE);
            stop = false;
            return;
        }
        /**
         * We check if the query field is empty here since no matter what we do,
         * we still need a query statement.
         */
        if (SantosWindow.queryField.getText().equals("")) {
            //Stop here if the query field is empty and print to console. 
            //log.logTo("Query field was empty. Run cancelled.", SantosWindow.logField);
            JOptionPane.showMessageDialog(null, "Query is blank.");
            log.logTo("Query field was empty. Run cancelled.", SantosWindow.outputLabel);
        } else if (SantosWindow.opcoList.isVisible()) {
            //If we are on opco list we need a multi run statement here. 
            //This will be replaced soon but this is temporary.
            if (SantosWindow.opcoList.getRowCount() == 0) {
                //log.logTo("The table is blank, there is nothing to run a query for.", SantosWindow.logField);
                JOptionPane.showMessageDialog(null, "Table is blank.");
                log.logTo("The table is blank, there is nothing to run a query for.", SantosWindow.outputLabel);
            } else {
                //log.logTo("There are rows in the table! Lets run the query.", SantosWindow.logField);
                log.logTo("There are rows in the table! Lets run the query.", SantosWindow.outputLabel);
                //log.logTo("There are rows selected in the table, lets run the query on them.", SantosWindow.logField);
                log.logTo("There are rows selected in the table, lets run the query on them.", SantosWindow.outputLabel);
                if (SantosWindow.opcoList.getSelectedRowCount() > 0) {
                    runSelected();
                } else {
                    JOptionPane.showMessageDialog(null, "The selection was empty.");
                    //log.logTo("The table selection is empty, there is nothing to run a query for.", SantosWindow.logField);
                    log.logTo("The table selection is empty, there is nothing to run a query for.", SantosWindow.outputLabel);
                }

            }
        } else {
            //Otherwise run as a single entry.
            run(new Machine(getDriver(), SantosWindow.hostField.getText(),
                    SantosWindow.databaseField.getText(), SantosWindow.userField.getText(),
                    new String(SantosWindow.passField.getPassword()), SantosWindow.hostField.getText()));
            runQueue();
        }
    }

    /**
     * Gets the driver from the window field. This method also changes out known
     * drivers to an actual driver path name. So the displayed driver is not the
     * full path of the driver. These are two separate things.
     *
     * @return Returns the full path of a driver if there is one.
     */
    public String getDriver() {
        //Here we check if the driver has a class path and if it does, return the full class path.
        return OptionsWindow.driverPaths.get(SantosWindow.driverBox.getSelectedIndex());
    }
//    We are currently keeping this here since it may be needed in the future.
//    public void singleRun() {
//        SanConnection sc;
//        String driver = getDriver();
//        String query = SantosWindow.queryField.getText();
//        if (SantosWindow.hostField.getText().equals("") || SantosWindow.hostField.getText().equals("Host")) {
//            log.logTo("Host field was empty. Run cancelled.", SantosWindow.logField);
//            return;
//        }
//        String host = SantosWindow.hostField.getText();
//        if (SantosWindow.databaseField.getText().equals("") || SantosWindow.databaseField.getText().equals("DB")) {
//            log.logTo("Database field was empty. Run cancelled.", SantosWindow.logField);
//            return;
//        }
//        String database = SantosWindow.databaseField.getText();
//        if (SantosWindow.userField.getText().equals("") || SantosWindow.userField.getText().equals("Username")) {
//            log.logTo("The username and password fields were left blank. The server may request this later...", SantosWindow.logField);
//        }
//        String user = SantosWindow.userField.getText();
//        String password = new String(SantosWindow.passField.getPassword());
//        //log.logTo(password, SantosWindow.logField);
//        sc = new ConnectionHandler().getConnection(driver, host, database, user, password, query);
//        if (SantosWindow.previewRadio.isSelected()) {
//            preview(sc);
//        } else {
//            export(sc);
//        }
//
//    }

    /**
     * This method populates the right hand table in order to preview what a
     * query will look like when exported to excel or before it is ran. This is
     * most important when running a query on a larger list of machines.
     *
     * @param sc In order to fill the table, the method needs the connection to
     * receive the data.
     */
    public void preview(SanConnection sc) {
        //Sets the table model to the populated table model.
        if (Integer.valueOf(SantosWindow.previewField.getText()) > 0) {
            SantosWindow.previewTable.setModel(new TableHandler().buildTableModel(sc.rs));
        } else {
            log.logTo("Invalid preview size.", SantosWindow.outputLabel);
            //log.logTo("Invalid preview size.", SantosWindow.logField);
        }
    }

    /**
     * Runs the preview or export by creating a connection and deciding if the
     * user wants to preview or export from the radio buttons. This method also
     * checks if the all the required fields are filled out. We pass a machine
     * here to also simplify the group of variables into one class for easier
     * variable management.
     *
     * @param m The machine class variable holding the required information to
     * create a connection.
     */
    public void run(Machine m) {
        //Check that the required fields are not null or empty to ensure that a proper connection gets created.
        if (m == null || m.database == null || m.host.equals("") || m.host == null) {
            /**
             * Note: We do not need to check query field here because it was
             * already checked before this method. Otherwise, if we are missing
             * some of this information then we need to cancel the attempt and
             * wait for the user to input more information or skip over this
             * entry and notify the user that the entry was skipped.
             */
            //log.logTo("Machine information is incomplete and is either null, missing a query, missing a database, or missing a host.", SantosWindow.logField);
            log.logTo("Machine information is incomplete and is either null, missing a query, missing a database, or missing a host.", SantosWindow.outputLabel);
            return;
        }
        //log.logTo(password, SantosWindow.logField);
//        SanConnection psc = null;
//        try
//        {
//            log.logTo("Attempting to preformat the table.", SantosWindow.logField);
//            psc = new SanConnection(getDriver(), m.host, m.database, m.user, m.pass, getQuery()+" ORDER BY 1");
//        }
//        catch(Exception e)
//        {
//            log.logTo("Table is already formatted.", SantosWindow.logField);
//        }
//        finally
//        {
//            if(psc != null)
//                psc.closeConnection();
//        }

        /**
         * We declare a variable up here because we may have to use this
         * variable in two different places below, so we do this to reduce
         * redundancy.
         */
        SanConnection sc = new SanConnection(getDriver(), m.host, m.database, m.user, m.pass, getQuery(), m.tag);
        System.out.println("Tag2:" + m.tag);
        //Based on the radio button, decide to preview or export to excel.
        if (SantosWindow.previewRadio.isSelected()) {
            preview(sc);
        } else {

            q.addQueue(sc);
        }
    }

    public void runAll() {
//        int rows = SantosWindow.opcoList.getRowCount();
//        System.out.println("Counted " + rows + " rows.");
//        for(int i = 0; i < rows; i++)
//        {
//            String host = String.valueOf(SantosWindow.optionTable.getValueAt(i, 1));
//            String database = String.valueOf(SantosWindow.optionTable.getValueAt(i, 2));
//            run(new Machine(getDriver(), host, database));
//            if (SantosWindow.previewRadio.isSelected())
//                return;
//        }
        runSelected();
        //runQueue();
    }

    public void runSelected() {
        q = new Queuer(getQuery());
        //log.logTo("Running selected.", SantosWindow.logField);
        log.logTo("Running selected.", SantosWindow.outputLabel);
        int[] sel = SantosWindow.opcoList.getSelectedRows();
        System.out.println("The selection size is: " + sel.length);
        for (int i : sel) {
            System.out.println("Running for " + i + ".");
            try {
                String host = String.valueOf(SantosWindow.optionTable.getValueAt(i, 1));
                String database = String.valueOf(SantosWindow.optionTable.getValueAt(i, 2));
                String tag = String.valueOf(SantosWindow.optionTable.getValueAt(i, 0));
                System.out.println(host + " " + database);
                System.out.println("Tag:" + tag);
                run(new Machine(getDriver(), host, database, tag));
                if (SantosWindow.previewRadio.isSelected()) {
                    System.out.println("Ran preview?");
                    return;
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
        if (sel.length > 0) {

            runQueue();
        } else {
            //log.logTo("The selection was empty. No queue to be ran.", SantosWindow.logField);
            log.logTo("The selection was empty. No queue to be ran.", SantosWindow.outputLabel);
        }
    }

    public void runQueue() {
        if (!SantosWindow.previewRadio.isSelected()) {
//            try
//            {
            q.runQuery();
//            }
//            catch(NullPointerException e)
//            {
//                System.out.println("q was null!");
//                //log.logTo("Query queue was empty.", SantosWindow.logField);
//                log.logTo("Query queue was empty.", SantosWindow.outputLabel);
//            }
        }
    }
}
