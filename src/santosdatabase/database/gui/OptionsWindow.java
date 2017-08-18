/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.gui;

import java.util.ArrayList;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import static santosdatabase.SantosDatabase.cf;
import static santosdatabase.SantosDatabase.data;
import static santosdatabase.database.gui.SantosWindow.optionsRemoveField;

/**
 *
 * @author justdasc
 */
public class OptionsWindow {

    public static ArrayList<String[]> list;
    public static DefaultTableModel model;
    public static String previous;

    public static ArrayList<String> driverNames;
    public static ArrayList<String> driverPaths;

    public OptionsWindow() {
        init();
    }

    private void init() {
        driverNames = data.loadDriverNames();
        driverPaths = data.loadDriverPaths();

        SantosWindow.optionsDriverBox.removeAllItems();
        for (String dm : driverNames) {
            SantosWindow.optionsDriverBox.addItem(dm);
        }
        loadTable(driverNames.get(0));

        SantosWindow.driverBox.removeAllItems();
        for (String dm : driverNames) {
            SantosWindow.driverBox.addItem(dm);
        }

        addSearchListener();

    }

    /**
     * Loads a table from config with the given driver name.
     * @param driverName The driver name of the table to load from config.
     */
    public void loadTable(String driverName) {
        ArrayList<String[]> list;
        try {
            list = cf.loadOPCO(driverName);
            this.list = list;
        } catch (NullPointerException e) {
            this.list = new ArrayList<String[]>();
        }

        model = new DefaultTableModel();

        //Create a couple of columns 
        model.addColumn("Name");
        model.addColumn("Host");
        model.addColumn("Database");
        for (String[] a : this.list) {
            if (!a[0].equals("") && !a[1].equals("")) {
                model.addRow(a);
            }
        }
        SantosWindow.optionTable.setModel(model);
        SantosWindow.opcoList.setModel(model);
    }

    /**
     * Saves a table with the given driver name to config.
     * @param driverName The driver name to associate the table with to save to config.
     */
    public void saveTable(String driverName) {
        cf.saveOPCO(driverName, list);
    }

    /**
     * Adds a server list entry to the table.
     * @param e The row to be added to the table.
     */
    public void addEntry(String[] e) {
        for(String[] s : list)
        {
            if(s[0].equals(e[0]))
                return;
        }
        list.add(e);
        model.addRow(e);
        SantosWindow.optionTable.setModel(model);
        SantosWindow.opcoList.setModel(model);
        saveTable(SantosWindow.optionsDriverBox.getSelectedItem().toString());
    }

    /**
     * Removes all selected rows from the table.
     */
    public void removeSelected() {
        int[] sel = SantosWindow.optionTable.getSelectedRows();
        if (sel.length > 0) {
            removeIndex(sel[0]);
            removeSelected();
        } else {
            saveTable(SantosWindow.optionsDriverBox.getSelectedItem().toString());
        }
    }
    
    /**
     * Clears out the table completely.
     */
    public void clearTable()
    {
        list.clear();
        for(int i = 0; i < model.getRowCount(); i++)
        {
            model.removeRow(0);
        }
        SantosWindow.optionTable.setModel(model);
        SantosWindow.opcoList.setModel(model);
        saveTable(SantosWindow.optionsDriverBox.getSelectedItem().toString());
    }
    
    /**
     * Clears a secondary list holding table info.
     */
    public void clear()
    {
        list.clear();
    }
    
    /**
     * Removes an index from the table.
     * @param index The index of the row that needs to be removed.
     */
    public void removeIndex(int index) {
        String[] entry = new String[3];
        entry[0] = String.valueOf(SantosWindow.optionTable.getValueAt(index, 0));
        entry[1] = String.valueOf(SantosWindow.optionTable.getValueAt(index, 1));
        entry[2] = String.valueOf(SantosWindow.optionTable.getValueAt(index, 2));
        String[] remove = null;
        for (String[] l : list) {
            if (l.length == 3) {
                if (l[0].equals(entry[0]) && l[1].equals(entry[1]) && (l[2].equals(entry[2]) || (l[2] == null && entry[2] == null))) {
                    System.out.println("Removing entry...");
                    remove = l;
                }
            } else {
                if (l[0].equals(entry[0]) && l[1].equals(entry[1]) && entry[2].equals("")) {
                    System.out.println("Removing entry...");
                    remove = l;
                }
            }
        }
        if (remove != null) {
            list.remove(remove);
        }
        model.removeRow(index);
        SantosWindow.optionTable.setModel(model);
        SantosWindow.opcoList.setModel(model);
    }

    /**
     * Check if the row is a unique entry in the table and is not a duplicate.
     * @param tag The row tag.
     * @param host The row host.
     * @param database The row database.
     * @return Returns true if the entry is unique.
     */
    public boolean checkUniqueEntry(String tag, String host, String database) {
        for (String[] l : list) {
            if (l[0].equals(tag)) {
                if (l[1].equals(host) && l[2].equals(database)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds the listener for the search box under the system tab.
     */
    public void addSearchListener() {
        // Listen for changes in the text
        SantosWindow.optionsRemoveField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!optionsRemoveField.getText().equals("Search") || !optionsRemoveField.getText().equals("")) {
                    TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + optionsRemoveField.getText()));
                    SantosWindow.optionTable.setRowSorter(sorter);
                    SantosWindow.optionTable.setModel(model);
                    //System.out.println("Triggered");
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!optionsRemoveField.getText().equals("Search") || !optionsRemoveField.getText().equals("")) {
                    TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" +optionsRemoveField.getText()));
                    SantosWindow.optionTable.setRowSorter(sorter);
                    SantosWindow.optionTable.setModel(model);
                    //System.out.println("Triggered");
                }
                //System.out.println("Triggered 1");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!optionsRemoveField.getText().equals("Search") || !optionsRemoveField.getText().equals("")) {
                    TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" +optionsRemoveField.getText()));
                    SantosWindow.optionTable.setRowSorter(sorter);
                    SantosWindow.optionTable.setModel(model);
                    //System.out.println("Triggered");
                }
                //System.out.println("Triggered 2");
            }

        });
    }

}
