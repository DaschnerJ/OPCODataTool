/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.config;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import santosconfig.config.Config;
import static santosdatabase.SantosDatabase.log;
import static santosdatabase.SantosDatabase.opt;
import static santosdatabase.database.gui.OptionsWindow.model;
import santosdatabase.database.gui.SanFileChooser;
import santosdatabase.database.gui.SantosWindow;
import santosutils.SantosUtils;
import santosutils.file.FileManager;

/**
 *
 * @author justdasc
 */
public class ConfigHandler {

    //These variables hold the default and options config which are used in multiple config options.
    public static ConfigHandler def;
    public static ConfigHandler option;
    //Used for writing data and getting directories.
    SantosUtils u = new SantosUtils();
    //This particular config file.
    Config cf;

    public ConfigHandler(String name) {
        cf = new Config(name);
    }

    public ConfigHandler(String name, String location) {
        cf = new Config(name, location);
    }

    /**
     * Returns this config.
     * @return Returns the current config.
     */
    public Config getConfig() {
        return cf;
    }

    /**
     * Writes the server list to the configuration file form the options file.
     */
    public void writeServersList() {
        cf.writeValue("serverList", option.getConfig().safeLoad("AS 400"));
    }

    /**
     * Loads the servers list as a single string.
     * @return Formatted server list.
     */
    public String loadServersList() {
        return cf.loadValue("serverList");
    }

    /**
     * Loads the system configuration file and safe writes it to default.
     */
    private void loadSystem() {
        System.out.println("===============================Loading System============================");
        if (def.cf.loadValue("systemDefault") != null) {
            String path = def.cf.loadValue("systemDefault");
            File f = new File(path + "\\" + "System.sanfig");
            System.out.println("Loading from default system configuration file.");
            System.out.println("The system default file path is: " + path + "\\" + "System.sanfig");
            if (f.exists()) {
                System.out.println("Config System File does exist.");
            }
            if (f.exists() && !f.isDirectory()) {
                System.out.println("Found the file! Loading system defaults.");
                Config sys = new Config("System", path);
                //def.cf.safeWrite("allRadio", sys.safeLoad("allRadio"));
                System.out.println("System-previewRadio: " + sys.safeLoad("previewRadio"));
                def.cf.safeWrite("previewRadio", sys.safeLoad("previewRadio"));
                def.cf.safeWrite("selectDriverIndex", sys.safeLoad("selectDriverIndex"));
                def.cf.safeWrite("lastHost", sys.safeLoad("lastHost"));
                def.cf.safeWrite("lastDatabase", sys.safeLoad("lastDatabase"));
                def.cf.safeWrite("lastUsername", sys.safeLoad("lastUsername"));
                def.cf.safeWrite("lastPassword", sys.safeLoad("lastPassword"));
                def.cf.safeWrite("lastQuery", sys.safeLoad("lastQuery"));
                def.cf.safeWrite("lastPath", sys.safeLoad("lastPath"));
                def.cf.safeWrite("previewField", sys.safeLoad("previewField"));
                def.cf.safeWrite("fileDefaultName", sys.safeLoad("fileDefaultName"));

                def.cf.safeWrite("columnHeaders", sys.safeLoad("columnHeaders"));
                def.cf.safeWrite("overwriteFiles", sys.safeLoad("overwriteFiles"));
                def.cf.safeWrite("oneSheet", sys.safeLoad("oneSheet"));
                def.cf.safeWrite("separateSheets", sys.safeLoad("separateSheets"));
                def.cf.safeWrite("separateWorkbooks", sys.safeLoad("separateWorkbooks"));
                def.cf.safeWrite("prefix", sys.safeLoad("prefix"));
                def.cf.safeWrite("suffix", sys.safeLoad("suffix"));
            }

        }
        System.out.println("===============================Loading System END============================");
    }

    /**
     * Refreshes the GUI with the default values stored in config.
     */
    public void loadFromDefault() {
        System.out.println("===============================Loading From Default============================");
        //SantosWindow.selectRadio.setSelected(!Boolean.valueOf(def.cf.loadValue("allRadio")));
        SantosWindow.previewRadio.setSelected(Boolean.valueOf(def.cf.loadValue("previewRadio")));
        SantosWindow.excelRadio.setSelected(!Boolean.valueOf(def.cf.loadValue("previewRadio")));
        SantosWindow.driverBox.setSelectedIndex(Integer.valueOf(def.cf.loadValue("selectDriverIndex")));
        SantosWindow.hostField.setText(def.cf.loadValue("lastHost"));
        SantosWindow.databaseField.setText(def.cf.loadValue("lastDatabase"));
        SantosWindow.userField.setText(def.cf.loadValue("lastUsername"));
        SantosWindow.passField.setText(def.cf.loadValue("lastPassword"));
        SantosWindow.queryField.setText(def.cf.loadValue("lastQuery"));
        SantosWindow.previewField.setText(def.cf.loadValue("previewField"));
//        SanFileChooser.lastPath = def.cf.loadValue("lastPath");
//        SantosWindow.outputFileLocation.setText(def.cf.loadValue("lastPath"));
        SantosWindow.outputFileName.setText(def.cf.loadValue("fileDefaultName"));

        SantosWindow.outputCreateColumnHeaders.setSelected(Boolean.valueOf(def.cf.loadValue("columnHeaders")));
        SantosWindow.outputCreateColumnHeaders.setSelected(Boolean.valueOf(def.cf.loadValue("overwriteFiles")));
        SantosWindow.outputCreateColumnHeaders.setSelected(Boolean.valueOf(def.cf.loadValue("oneSheet")));
        SantosWindow.outputCreateColumnHeaders.setSelected(Boolean.valueOf(def.cf.loadValue("separateSheets")));
        SantosWindow.outputCreateColumnHeaders.setSelected(Boolean.valueOf(def.cf.loadValue("separateWorkbooks")));

        SantosWindow.outputPrefix.setText(def.cf.loadValue("prefix"));
        SantosWindow.outputSuffix.setText(def.cf.loadValue("suffix"));

        infoBox("Finished refreshing all options to defaults.", "Default Configuration");
        System.out.println("===============================Loading from Default END============================");
    }

    /**
     * Refreshes the GUI and writes the defaults from system defaults.
     */
    public void loadFromSystemDefault() {
        System.out.println("===============================Loading From System Default============================");
        if (def.cf.loadValue("systemDefault") != null) {
            System.out.println("Key exists, time to get the path...");
            System.out.println("Loaded value is: " + def.cf.loadValue("systemDefault"));
            String path = def.cf.loadValue("systemDefault");
            File f = new File(path + "\\" + "System.sanfig");
            System.out.println("Loading from default system configuration file.");
            System.out.println("The system default file path is: " + path + "\\" + "System.sanfig");
            if (f.exists()) {
                System.out.println("Config System File does exist.");
            } else {
                infoBox("Default system file is missing.", "System Default Configuration");
            }
            if (f.exists() && !f.isDirectory()) {
                System.out.println("Found the file! Loading system defaults.");
                Config sys = new Config("System", path);
                //SantosWindow.selectRadio.setSelected(!Boolean.valueOf(sys.loadValue("allRadio")));
                SantosWindow.previewRadio.setSelected(Boolean.valueOf(sys.loadValue("previewRadio")));
                def.cf.safeWrite(sys.loadValue("previewRadio"), sys.loadValue("previewRadio"));
                SantosWindow.excelRadio.setSelected(!Boolean.valueOf(sys.loadValue("previewRadio")));
                SantosWindow.driverBox.setSelectedIndex(Integer.valueOf(sys.loadValue("selectDriverIndex")));
                def.cf.safeWrite(sys.loadValue("selectDriverIndex"), sys.loadValue("selectDriverIndex"));
                SantosWindow.hostField.setText(sys.loadValue("lastHost"));
                def.cf.safeWrite(sys.loadValue("lastHost"), sys.loadValue("lastHost"));
                SantosWindow.databaseField.setText(sys.loadValue("lastDatabase"));
                def.cf.safeWrite(sys.loadValue("lastDatabase"), sys.loadValue("lastDatabase"));
                SantosWindow.userField.setText(sys.loadValue("lastUsername"));
                def.cf.safeWrite(sys.loadValue("lastUsername"), sys.loadValue("lastUsername"));
                SantosWindow.passField.setText(sys.loadValue("lastPassword"));
                def.cf.safeWrite(sys.loadValue("lastPassword"), sys.loadValue("lastPassword"));
                SantosWindow.queryField.setText(sys.loadValue("lastQuery"));
                def.cf.safeWrite(sys.loadValue("lastQuery"), sys.loadValue("lastQuery"));
                SantosWindow.previewField.setText(sys.loadValue("previewField"));
                def.cf.safeWrite(sys.loadValue("previewField"), sys.loadValue("previewField"));
                SanFileChooser.lastPath = sys.loadValue("lastPath");
                def.cf.safeWrite(sys.loadValue("lastPath"), sys.loadValue("lastPath"));
                System.out.println("Last System path: " + sys.loadValue("lastPath"));
                SantosWindow.outputFileLocation.setText(sys.loadValue("lastPath"));
//        SanFileChooser.lastPath = def.cf.loadValue("lastPath");
//        SantosWindow..setText(def.cf.loadValue("lastPath"));
                SantosWindow.outputFileName.setText(sys.loadValue("fileDefaultName"));

                SantosWindow.outputCreateColumnHeaders.setSelected(Boolean.valueOf(def.cf.loadValue("columnHeaders")));
                SantosWindow.outputOverwriteExistingFiles.setSelected(Boolean.valueOf(def.cf.loadValue("overwriteFiles")));
                SantosWindow.outputOneSheet.setSelected(Boolean.valueOf(def.cf.loadValue("oneSheet")));
                SantosWindow.outputSeparateSheets.setSelected(Boolean.valueOf(def.cf.loadValue("separateSheets")));
                SantosWindow.outputSeparateWorkbooks.setSelected(Boolean.valueOf(def.cf.loadValue("separateWorkbooks")));

                SantosWindow.outputPrefix.setText(def.cf.loadValue("prefix"));
                SantosWindow.outputSuffix.setText(def.cf.loadValue("suffix"));
                infoBox("Finished refreshing all options to system defaults.", "System Default Configuration");
            } else {
                infoBox("Default system file is missing.", "System Default Configuration");
            }
        } else {
            infoBox("There is no default configuration file configured.", "System Default Configuration");
        }
        System.out.println("===============================Loading From System Default END============================");

    }

    /**
     * Loads the server list from system defaults.
     */
    public void loadServersFromSystemDefault() {
        System.out.println("===============================Loading Servers From System============================");
        if (def.cf.loadValue("systemDefault") != null) {
            System.out.println("Key exists, time to get the path...");
            System.out.println("Loaded value is: " + def.cf.loadValue("systemDefault"));
            String path = def.cf.loadValue("systemDefault");
            File f = new File(path + "\\" + "System.sanfig");
            System.out.println("Loading from default system configuration file.");
            System.out.println("The system default file path is: " + path + "\\" + "System.sanfig");
            if (f.exists()) {
                System.out.println("Config System File does exist.");
            } else {
                infoBox("Default system file is missing.", "System Default Configuration");
            }
            if (f.exists() && !f.isDirectory()) {
                System.out.println("Found the file! Loading system defaults.");
                Config sys = new Config("System", path);
                //SantosWindow.selectRadio.setSelected(!Boolean.valueOf(sys.loadValue("allRadio")));
                String list = sys.safeLoad("serverList");
                ArrayList<String[]> sList;
                try {
                    sList = loadOPCO(sys);
                } catch (NullPointerException e) {
                    sList = new ArrayList<>();
                }

                model = new DefaultTableModel();

                opt.clear();
                //Create a couple of columns 
                model.addColumn("Name");
                model.addColumn("Host");
                model.addColumn("Database");
                for (String[] a : sList) {
                    if (!a[0].equals("") && !a[1].equals("")) {
                        opt.addEntry(a);
                    }
                }
                SantosWindow.optionTable.setModel(model);
                SantosWindow.opcoList.setModel(model);
                infoBox("Finished refreshing all options to system defaults.", "System Default Configuration");
            } else {
                infoBox("Default system file is missing.", "System Default Configuration");
            }
        } else {
            infoBox("There is no default configuration file configured.", "System Default Configuration");
        }
        System.out.println("===============================Loading Servers From System END============================");

    }

    /**
     * Loads the last saved options.
     */
    public void loadOptions() {
        System.out.println("===============================Loading Options============================");

        //SantosWindow.allRadio.setSelected(Boolean.valueOf(cf.loadValue("allRadio")));
        //SantosWindow.selectRadio.setSelected(!Boolean.valueOf(cf.loadValue("allRadio")));
        SantosWindow.previewRadio.setSelected(Boolean.valueOf(cf.loadValue("previewRadio")));
        SantosWindow.excelRadio.setSelected(!Boolean.valueOf(cf.loadValue("previewRadio")));
        SantosWindow.driverBox.setSelectedIndex(Integer.valueOf(cf.loadValue("selectDriverIndex")));
        SantosWindow.hostField.setText(cf.loadValue("lastHost"));
        SantosWindow.databaseField.setText(cf.loadValue("lastDatabase"));
        SantosWindow.userField.setText(cf.loadValue("lastUsername"));
        SantosWindow.passField.setText(cf.loadValue("lastPassword"));
        SantosWindow.queryField.setText(cf.loadValue("lastQuery"));
        SantosWindow.previewField.setText(cf.loadValue("previewField"));
        SanFileChooser.lastPath = cf.loadValue("lastPath");
        System.out.println("Last options path: " + cf.loadValue("lastPath"));
        SantosWindow.outputFileLocation.setText(cf.loadValue("lastPath"));
        SantosWindow.outputFileName.setText(cf.loadValue("fileDefaultName"));
        
        SantosWindow.outputCreateColumnHeaders.setSelected(Boolean.valueOf(cf.loadValue("columnHeaders")));
        SantosWindow.outputOverwriteExistingFiles.setSelected(Boolean.valueOf(cf.loadValue("overwriteFiles")));
        SantosWindow.outputOneSheet.setSelected(Boolean.valueOf(cf.loadValue("oneSheet")));
        SantosWindow.outputSeparateSheets.setSelected(Boolean.valueOf(cf.loadValue("separateSheets")));
        SantosWindow.outputSeparateWorkbooks.setSelected(Boolean.valueOf(cf.loadValue("separateWorkbooks")));

        SantosWindow.outputPrefix.setText(cf.loadValue("prefix"));
        SantosWindow.outputSuffix.setText(cf.loadValue("suffix"));

        if (!SantosWindow.outputSeparateWorkbooks.isSelected()) {
            SantosWindow.outputFileName.setEnabled(false);
        }
        System.out.println("===============================Loading Options END============================");
    }

    /**
     * Writes the default options if they do not exist.
     */
    public void writeDefaultOptions() {
        System.out.println("===============================Loading Default Options============================");
        loadSystem();
        //def.cf.safeWrite("allRadio", "false");
        def.cf.safeWrite("previewRadio", "true");
        def.cf.safeWrite("selectDriverIndex", "0");
        def.cf.safeWrite("lastHost", "Host");
        def.cf.safeWrite("lastDatabase", "Test");
        def.cf.safeWrite("lastUsername", "Username");
        def.cf.safeWrite("lastPassword", "Password");
        def.cf.safeWrite("lastQuery", "");
        def.cf.safeWrite("previewField", "25");
        def.cf.safeWrite("fileDefaultName", "OPCO-Worbook-");
        def.cf.safeWrite("lastPath", new FileManager().GetExecutionPath());
        
        def.cf.safeWrite("columnHeaders", "true");
        def.cf.safeWrite("overwriteFiles", "false");
        def.cf.safeWrite("oneSheet", "true");
        def.cf.safeWrite("separateSheets", "false");
        def.cf.safeWrite("separateWorkbooks", "false");
        
        def.cf.safeWrite("prefix", "");
        def.cf.safeWrite("suffix", "");

        //cf.safeWrite("allRadio", def.cf.safeLoad("allRadio"));
        cf.safeWrite("previewRadio", def.cf.safeLoad("previewRadio"));
        cf.safeWrite("selectDriverIndex", def.cf.safeLoad("selectDriverIndex"));
        cf.safeWrite("lastHost", def.cf.safeLoad("lastHost"));
        cf.safeWrite("lastDatabase", def.cf.safeLoad("lastDatabase"));
        cf.safeWrite("lastUsername", def.cf.safeLoad("lastUsername"));
        cf.safeWrite("lastPassword", def.cf.safeLoad("lastPassword"));
        cf.safeWrite("lastQuery", def.cf.safeLoad("lastQuery"));
        cf.safeWrite("previewField", def.cf.safeLoad("previewField"));
        cf.safeWrite("lastPath", def.cf.safeLoad("lastPath"));
        cf.safeWrite("fileDefaultName", def.cf.safeLoad("fileDefaultName"));
        
        cf.safeWrite("columnHeaders", def.cf.safeLoad("columnHeaders"));
        cf.safeWrite("overwriteFiles",def.cf.safeLoad("overwriteFiles"));
        cf.safeWrite("oneSheet", def.cf.safeLoad("oneSheet"));
        cf.safeWrite("separateSheets", def.cf.safeLoad("separateSheets"));
        cf.safeWrite("separateWorkbooks", def.cf.safeLoad("separateWorkbooks"));
        
        cf.safeWrite("prefix", def.cf.safeLoad("prefix"));
        cf.safeWrite("suffix", def.cf.safeLoad("suffix"));
        System.out.println("===============================Loading Default Options END============================");
        
    }

    /**
     * Writes the driver options to their own file, since these are standard across the board.
     */
    public void writeDefaultDataOptions() {
        def.cf.safeWrite("driverNames", "AS 400");
        def.cf.safeWrite("driverPaths", "com.ibm.as400.access.AS400JDBCDriver");

        cf.safeWrite("driverNames", def.cf.safeLoad("driverNames"));
        cf.safeWrite("driverPaths", def.cf.safeLoad("driverPaths"));
    }

    /**
     * The config operations that are done to save the current options on program exit.
     */
    public void closingOperations() {
        //cf.writeValue("allRadio", String.valueOf(SantosWindow.allRadio.isSelected()));
        cf.writeValue("previewRadio", String.valueOf(SantosWindow.previewRadio.isSelected()));
        cf.writeValue("selectDriverIndex", String.valueOf(SantosWindow.driverBox.getSelectedIndex()));
        cf.writeValue("lastHost", SantosWindow.hostField.getText());
        cf.writeValue("lastDatabase", SantosWindow.databaseField.getText());
        cf.writeValue("lastUsername", SantosWindow.userField.getText());
        cf.writeValue("lastPassword", "");
        cf.writeValue("lastQuery", SantosWindow.queryField.getText());
        cf.writeValue("previewField", SantosWindow.previewField.getText());
        cf.writeValue("lastPath", SantosWindow.outputFileLocation.getText());
        cf.writeValue("fileDefaultName", SantosWindow.outputFileName.getText());
        
        cf.writeValue("columnHeaders", String.valueOf(SantosWindow.outputCreateColumnHeaders.isSelected()));
        cf.writeValue("overwriteFiles",String.valueOf(SantosWindow.outputOverwriteExistingFiles.isSelected()));
        cf.writeValue("oneSheet", String.valueOf(SantosWindow.outputOneSheet.isSelected()));
        cf.writeValue("separateSheets", String.valueOf(SantosWindow.outputSeparateSheets.isSelected()));
        cf.writeValue("separateWorkbooks", String.valueOf(SantosWindow.outputSeparateWorkbooks.isSelected()));
        
        cf.writeValue("prefix", SantosWindow.outputPrefix.getText());
        cf.writeValue("suffix", SantosWindow.outputSuffix.getText());

    }

    /**
     * Loads the list of driver names.
     * @return The driver name.
     */
    public ArrayList<String> loadDriverNames() {
        return cf.loadValueList("driverNames");
    }

    /**
     * Loads the list of driver paths.
     * @return The driver path.
     */
    public ArrayList<String> loadDriverPaths() {
        return cf.loadValueList("driverPaths");
    }

    /**
     * Saves the driver names to config file.
     * @param driverNames The driver names to store.
     */
    public void saveDriverNames(ArrayList<String> driverNames) {
        cf.writeValueList("driverNames", driverNames);
    }

    /**
     * Saves the driver paths to the config file.
     * @param driverPaths The driver paths to store.
     */
    public void saveDriverPaths(ArrayList<String> driverPaths) {
        cf.writeValueList("driverPaths", driverPaths);
    }

    /**
     * Loads and OPCOs as a list associated with the given driver name.
     * @param driverName The driver name that the OPCO uses.
     * @return Returns an array list of string arrays of OPCOs associated with the driver name.
     */
    public ArrayList<String[]> loadOPCO(String driverName) {
        ArrayList<String[]> list = new ArrayList<>();
        ArrayList<String> load = cf.loadValueList(driverName);
        for (String s : load) {
            list.add(s.split("%-%"));
        }
        return list;
    }

    /**
     * Loads the OPCO server list from the specified config handler.
     * @param df The given config handler to load the server list from.
     * @return Returns an array list of string arrays of OPCOs Server Lists.
     */
    public ArrayList<String[]> loadOPCO(ConfigHandler df) {
        ArrayList<String[]> list = new ArrayList<>();
        ArrayList<String> load = df.cf.loadValueList("serverList");
        for (String s : load) {
            list.add(s.split("%-%"));
        }
        return list;
    }

    /**
     * Loads the OPCO server list from the specified config file.
     * @param df The given config to load the server list from.
     * @return Returns an array list of string arrays of OPCOs Server Lists.
     */
    public ArrayList<String[]> loadOPCO(Config df) {
        ArrayList<String[]> list = new ArrayList<>();
        ArrayList<String> load = df.loadValueList("serverList");
        for (String s : load) {
            list.add(s.split("%-%"));
        }
        return list;
    }

    /**
     * Saves the list of servers associated with the given driver name.
     * @param driverName The driver the OPCO list is associated with.
     * @param list The list of servers from the table.
     */
    public void saveOPCO(String driverName, ArrayList<String[]> list) {
        log.logTo("Saving opco list with driverName " + driverName + " and list size of " + list.size(), SantosWindow.logField);
        log.logTo("Saving opco list with driverName " + driverName + " and list size of " + list.size(), SantosWindow.outputLabel);
        ArrayList<String> save = new ArrayList<>();
        for (String[] a : list) {
            if (a.length == 3) {
                save.add(a[0] + "%-%" + a[1] + "%-%" + a[2]);
            }
        }
        cf.writeValueList(driverName, save);
    }

    /**
     * Creates an info box pop up message to notify the user of something related to configuration.
     * @param infoMessage The message in the pop up.
     * @param titleBar The title of the message box.
     */
    public void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Loads the OPCO server list to the tables.
     */
    public void loadServerList() {
        if (def.cf.loadValue("serverList") != null) {
            System.out.println("Key exists, time to get the path...");
            System.out.println("Loaded value is: " + def.cf.loadValue("serverList"));
            String path = def.cf.loadValue("serverList");
            File f = new File(path + "\\" + "System.sanfig");
            System.out.println("Loading from default system configuration file.");
            System.out.println("The system default file path is: " + path + "\\" + "System.sanfig");
            if (f.exists()) {
                System.out.println("Config System File does exist.");
            } else {
                infoBox("Default system file is missing.", "System Default Configuration");
            }
            if (f.exists() && !f.isDirectory()) {
                System.out.println("Found the file! Loading system defaults.");
                Config sys = new Config("System", path);

                opt.clearTable();

                String list = sys.loadValue("dataList");
                String[] spl = list.split(",");
                for (String s : spl) {
                    String[] split = s.split("%-%");
                    if (opt.checkUniqueEntry(split[0], split[1], split[2])) {
                        opt.addEntry(split);
                    }

                }
            } else {
                infoBox("Default system file is missing.", "System Default Configuration");
            }
        } else {
            infoBox("There is no default configuration file configured.", "System Default Configuration");
        }

    }

}
