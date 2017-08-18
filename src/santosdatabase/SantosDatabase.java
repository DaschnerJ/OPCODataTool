/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import javax.swing.JFrame;
import santosconfig.SantosConfig;
import santosconfig.config.Config;
import santosdatabase.database.config.ConfigHandler;
import santosdatabase.database.gui.OptionsWindow;
import santosdatabase.database.gui.SQLParser;
import santosdatabase.database.gui.SantosWindow;
import santoslogger.gui.Log;

/**
 *
 * @author justdasc
 */
public class SantosDatabase {

    //Create the log file and a log variable to use.
    public static Log log = new Log("database_log");
    
    //Create config handlers to create and manage config files.
    public static ConfigHandler cf = new ConfigHandler("options");
    public static ConfigHandler data = new ConfigHandler("data");
    public static ConfigHandler def;
    //System needs it's own special config file for loading.
    public static Config systemConfig;
    //Create a config manager
    public static SantosConfig cfg = new SantosConfig();
    
    //The options window is needed to change out options and buttons.
    public static OptionsWindow opt;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SQLParser test = new SQLParser();
        //new QueryHandler();
        final JFrame mainWindow = new SantosWindow();
        //Centers window to center of screen on start up.
        centerWindow(mainWindow);
        //Shows the window and allows it to be displayed.
        mainWindow.setVisible(true);
        //Create a defaults config for the program to use. We create it here to ensure it gets created first.
        def = new ConfigHandler("defaults");
        //Set a static variable so other configs and classes can use.
        ConfigHandler.def = def;
        //Write out the default data options to the newly create default config file if it is possible.
        data.writeDefaultDataOptions();
        //Write out the default data options to the options config file if it is needed.
        cf.writeDefaultOptions();
        //Load previously saved or newly created options.
        cf.loadOptions();
        //Create an options window to edit options with other classes.
        opt = new OptionsWindow();
        //Stop the window from just closing and listen to it.
        mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //This is needed in order to properly close and save options at the end of the program.
        mainWindow.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent event) {
                System.out.println("Closing!");
                cf.closingOperations();
                mainWindow.setVisible(false);
                mainWindow.dispose();
                System.exit(0);
            }
        });
        //Update the GUI with infomration.
        log.updateLog(SantosWindow.logField);
        //Get a time stamp for each.
        log.logTo(Calendar.getInstance().getTime().toString(), SantosWindow.logField);
        log.logTo(Calendar.getInstance().getTime().toString(), SantosWindow.outputLabel);
        
        //log.showLog();

//        SanConnection sc;
//        sc = new SanConnection(driver, host, user, pass, query, database);
//        long startTime = System.nanoTime();
//        
//        Fetcher f = new Fetcher(sc.rs, 1000);
//        System.out.println(f.getRowSet().size());
//
//        SanExcel se = new SanExcel(sc.rs);
//        se.createWorkbook("Test", "C:\\Users\\justdasc\\Desktop\\Workbooks\\");
//        try {
//            se.writeResultSet("C:\\Users\\justdasc\\Desktop\\Workbooks\\Test.xls");
//        } catch (SQLException ex) {
//            Logger.getLogger(SantosDatabase.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime);
//        System.out.println("Time in Milliseconds: " + duration/1000000);
//        System.out.println("Done");
//        System.exit(0);
        //log.addFine(String.valueOf(sc.rs.getString("CSCUS")));
        
        //Let options be set as a static accessable variable as well.
        ConfigHandler.option = cf;
    }
    
    /**
     * Centers a frame when called.
     * @param frame The frame to center on the screen.
     */
    public static void centerWindow(JFrame frame) {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
    frame.setLocation(x, y);
}
    
}
