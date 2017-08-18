/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.ListSelectionModel;
import static santosdatabase.SantosDatabase.log;

/**
 *
 * @author justdasc
 */
public class QueryHandler {

    //The selections of a table.
    private ListSelectionModel sm;
    //The query in the query box to be saved.
    private String query;
    //The path to export the file to.
    private String path;

    Properties prop = new Properties();

    OutputStream output;
    InputStream input;

    /**
     * Creates a new handler for loading a query.
     */
    public QueryHandler() {
        this.input = null;
        this.output = null;
        requestLocation("Load");
        load();
    }

    /**
     * Creates a new handler for saving a query.
     * @param sm The selected list of rows.
     * @param query The query to be saved.
     */
    public QueryHandler(ListSelectionModel sm, String query) {
        this.input = null;
        this.output = null;
        this.sm = sm;
        this.query = query;
        requestLocation();
        save();
    }

    /**
     * Saves a query at the specified location with the selected rows and query.
     */
    private void save() {
        prop.put("query", query);
        prop.put("rows", getRows());
        try {
            output = new FileOutputStream(path);
            prop.store(output, null);
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            //log.logTo("The window was closed or the file was invalid.", SantosWindow.logField);
            log.logTo("The window was closed or the file was invalid.", SantosWindow.outputLabel);
        }
    }

    /**
     * Loads the query and highlighted rows.
     */
    private void load() {
        try {
            input = new FileInputStream(path);
            // load a properties file
            prop.load(input);
            if (prop.containsKey("query")) {
                if (prop.containsKey("rows")) {
                    SantosWindow.queryField.setText(prop.getProperty("query"));
                    ArrayList<String> rowList = new ArrayList(Arrays.asList(prop.getProperty("rows").split("%-%")));
                    ArrayList<Row> rows = new ArrayList<>();
                    for (String str : rowList) {
                        rows.add(new Row(str));
                    }

                    int indexes = SantosWindow.opcoList.getRowCount();
                    for (int index = 0; index < indexes; index++) {

                        SantosWindow.opcoList.getSelectionModel().removeSelectionInterval(index, index);
                    }
                    for (Row r : rows) {
                        for (int index = 0; index < indexes; index++) {
                            if (SantosWindow.opcoList.getValueAt(index, 0).toString().equals(r.tag)) {
                                if (SantosWindow.opcoList.getValueAt(index, 1).toString().equals(r.host)) {
                                    if (SantosWindow.opcoList.getValueAt(index, 2).toString().equals(r.database)) {
                                        SantosWindow.opcoList.getSelectionModel().addSelectionInterval(index, index);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    log.logTo("Failed to load query file. Missing fields.", SantosWindow.logField);
                }
            } else {
                log.logTo("Failed to load query file. Missing fields.", SantosWindow.logField);
            }
        } catch (Exception ex) {
            log.logTo("File failed to load or the window was closed.", SantosWindow.logField);
        }

    }

    /**
     * Requests a valid location to save the query to.
     */
    private void requestLocation() {
        SanFileChooser f = new SanFileChooser();
        path = f.getPath();
    }

    /**
     * Requests a valid location to save the query to.
     * @param text The button text.
     */
    private void requestLocation(String text) {
        SanFileChooser f = new SanFileChooser(JFileChooser.FILES_ONLY, text);
        path = f.getPath();
    }

    /**
     * Request a valid location to save the query to.
     * @param text The button text.
     * @return Returns a valid location.
     */
    private String requestDefaultLocation(String text) {
        SanFileChooser f = new SanFileChooser(JFileChooser.DIRECTORIES_ONLY, text);
        return f.getPath();
    }

    /**
     * Creates a new handler. Does nothing.
     * @param location The location of where to put the file.
     */
    public QueryHandler(String location) {
        this.input = null;

    }

    /**
     * Gets the highlighted rows to be saved.
     * @return Returns the formatted string of highlight rows.
     */
    public String getRows() {
        ArrayList<Row> rows = new ArrayList<>();
        int[] rowIndexes = SantosWindow.opcoList.getSelectedRows();
        for (int ri : rowIndexes) {
            String tag = String.valueOf(SantosWindow.opcoList.getValueAt(ri, 0));
            String host = String.valueOf(SantosWindow.opcoList.getValueAt(ri, 1));
            String database = String.valueOf(SantosWindow.opcoList.getValueAt(ri, 2));
            rows.add(new Row(tag, host, database));
        }

        ArrayList<String> rowList = new ArrayList<>();
        for (Row r : rows) {
            rowList.add(r.toString());
        }

        String res = "";
        for (String r : rowList) {
            if (res.equals("")) {
                res = r;
            } else {
                res = res + "%-%" + r;
            }
        }
        return res;
    }

    /**
     * Class of saveable rows in order to allow easy loading and unloading.
     */
    private class Row implements Serializable {

        String tag;
        String host;
        String database;

        public Row(String tag, String host, String database) {
            this.tag = tag;
            this.host = host;
            this.database = database;
        }

        public Row(String o) {
            Pattern spl;
            spl = Pattern.compile("%+%", Pattern.LITERAL);

            System.out.println(o);
            String[] s = spl.split(o);
            System.out.println(s.length);
            tag = s[0];
            host = s[1];
            database = s[2];
        }

        @Override
        public String toString() {
            return tag + "%+%" + host + "%+%" + database;
        }

    }

}
