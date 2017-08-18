/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static santosdatabase.SantosDatabase.log;
import santosdatabase.database.gui.ExportHandler;
import santosdatabase.database.gui.SantosWindow;
import santosdatabase.database.gui.WindowHandler;
import santosdatabase.database.type.Machine;

/**
 *
 * @author justdasc
 */
public class Queuer implements Runnable {

    String query;
    ArrayList<Machine> queue;
    ArrayList<SanConnection> runQueue;
    //ArrayList<Thread> exportQueue;

    //private static  Double complete = 0.0;

    /**
     * Handles the queue of machines to make tables from. This is needed to
     * handle the threads and download rates as well as not queue the same
     * machine in a row otherwise a potential security lock out of a user could
     * happen.
     *
     * @param query The query statement to be ran across the machines.
     */
    public Queuer(String query) {
        this.query = query;
        queue = new ArrayList<>();
        runQueue = new ArrayList<>();
        //exportQueue = new ArrayList<>();
    }

    /**
     * Adds a machine to be queried in the queue.
     * @param m 
     */
    public void addQueue(Machine m) {
        queue.add(m);
    }
    
    /**
     * Adds a new machine with the given information to be queried in the queue.
     * @param driver Driver needed to run the query.
     * @param host The host to run the query on.
     * @param database The database to run the query on.
     * @param user The user to run the query with.
     * @param pass The password to log in the user.
     * @param tag  Tag of the machine for referencing in output.
     */
    public void addQueue(String driver, String host, String database, String user, String pass, String tag) {
        queue.add(new Machine(driver, host, database, user, pass, tag));
    }

    public void addQueue(SanConnection sc) {
        runQueue.add(sc);
    }

    /**
     * Add a machine to the queue with just the machine details. This will be
     * mainly used in conjunction with the configuration options with listing
     * machines to run a query on.
     *
     * @param driver The driver to use to interact with the sql database.
     * @param host The machine name and host id to connect to.
     * @param database The database to read from.
     * @param tag Tag associated with the object being added to queue for export purposes.
     */
    public void addQueue(String driver, String host, String database, String tag) {
        queue.add(new Machine(driver, host, database, tag));
    }

    /**
     * Clears the queue of all machines.
     */
    public void clearQueue() {
        queue.clear();
        runQueue.clear();
    }

    /**
     * Allows for changing and setting the query. This method may be used for
     * filtering out queries to prevent users from using specific sql statements
     * that have certain keywords. This gives the option of using multiple
     * queries among a list of machines.
     *
     * @param query The new query to be set.
     */
    public void setQuery(String query) {
        this.query = query;
    }

    public void runQuery() {
        System.out.println(this.queue.size());
        //log.logTo("Constructing the export queue...", SantosWindow.logField);
        log.logTo("Constructing the export queue...", SantosWindow.outputLabel);
        //log.logTo("Converting machines to connections to queue...", SantosWindow.logField);
        log.logTo("Converting machines to connections to queue...", SantosWindow.outputLabel);
        convertForQuery();
//        SantosWindow.completionBar.setStringPainted(true);
//        SantosWindow.completionBar.setValue(0);
        //log.logTo("Populating the queue...", SantosWindow.logField);
        log.logTo("Populating the queue...", SantosWindow.outputLabel);
        Boolean newBook = true;
        System.out.println("Creating threads for a queue size of:" + runQueue.size());
        for(SanConnection s : runQueue)
        {
            if(!WindowHandler.stop)
            {
                ExportHandler exportHandler = new ExportHandler(s);
            }
        }
//        for (SanConnection s : runQueue) {
//            System.out.println("Starting thread... ");
//            Thread ex = new Thread(new ExportHandler(s, newBook));
//            if(!SantosWindow.outputSeparateWorkbooks.isSelected())
//                newBook = false;
//            exportQueue.add(ex);
//            ex.start();
//        }
//        Thread ex = null;

        //Double step = 100.0 / runQueue.size();
       
//        while (!WindowHandler.stop && !runQueue.isEmpty()) {
//            handlers.add(new ExportHandler(runQueue.get(0), newBook));
//            runQueue.remove(0);
//            if (ex != null) {
//                System.out.println("There is a thread...");
//                if (!ex.isAlive()) {
//                    System.out.println("Setting thread to null..");
//                    SantosWindow.completeLabel.setText("Closing thread.");
//                    ex = null;
//                    complete = complete + step;
//                    SantosWindow.completeLabel.setText("Finished task.");
//                } else {
//                    //System.out.println("Thread is running, cotninue.");
//                    continue;
//                }
//            } else {
//                System.out.println("Creating new thread...");
//                //SantosWindow.completeLabel.setText("Running task for " + runQueue.get(0).machine.tag + ".");
//                ex = new Thread(new ExportHandler(runQueue.get(0), newBook));
//                //ex = new Thread(new ExportHandler(runQueue.get(0), newBook));
//                System.out.println("Thread tag is " + runQueue.get(0).tag);
//                if (!SantosWindow.outputSeparateWorkbooks.isSelected()) {
//                    newBook = false;
//                }
//                exportQueue.add(ex);
//                ex.start();
//                
//                runQueue.remove(0);
                
//            }
//        }
//        while(!handlers.isEmpty() && !WindowHandler.stop)
//        {
//            //ArrayList<ExportHandler> h = new ArrayList<>();
//            for(int i = 0; i < handlers.size(); i++)
//            {
//                if(handlers.get(i).done)
//                {
//                    System.out.println("Handler done! Removing...");
//                   handlers.remove(i);
//                }
//            }
//        }
        if(!ExportHandler.outSpot.equals(""))
        {
            try {
                System.out.println("Printing out the final output for one file: " + ExportHandler.outSpot);
                //We are using an export handler to handle the exporting of the files because it makes it easier to work with and manage.
                ExportHandler.writeToFile(ExportHandler.swb, ExportHandler.outSpot);
            } catch (IOException ex) {
                Logger.getLogger(Queuer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Resetting is required to clear the static variables used in static context with file writing set by the program.
        ExportHandler.resetExport();
        //log.logTo("Waiting for queues to finish...", SantosWindow.logField);
        log.logTo("Waiting for queues to finish...", SantosWindow.outputLabel);
//        Runnable r = new Runnable() {
//
//            private ArrayList<Thread> removeQueue = new ArrayList<>();
//
//            public void run() {
//                System.out.println("Run queue size: " + runQueue.size());
////                System.out.println("Step size: " + step);
//                while (!exportQueue.isEmpty()) {
//                    removeQueue.clear();
//                    for (Thread t : exportQueue) {
//                        if (!t.isAlive() || WindowHandler.stop) {
//                            removeQueue.add(t);
//                        }
//                    }
//                    for (Thread t : removeQueue) {
//                        t.stop();
//                        exportQueue.remove(t);
//
//                        //System.out.println(complete.intValue());
//                        SantosWindow.completeLabel.setText("Finished running all tasks.");
////                        SantosWindow.completionBar.setValue(complete.intValue());
//
//                    }
//                }
//                SantosWindow.completionBar.setValue(100);
                //Ensure the variables are reset.
                SantosWindow.runButton.setEnabled(true);
                ExportHandler.oneFileName = null;
                ExportHandler.cancelOverride = false;
                ExportHandler.doNotSingleOverride = false;
                ExportHandler.loc = "";
                ExportHandler.setLocation = false;
                //log.logTo("Finished exporting all tables.", SantosWindow.logField);
                log.logTo("Finished exporting all tables.", SantosWindow.outputLabel);
//            }
//        };

//        Thread thread = new Thread(r);
//        thread.start();

    }

    /**
     * Converts machines into SanConnections before the query.
     */
    public void convertForQuery() {
        if (!queue.isEmpty()) {
            for (Machine m : queue) {
                runQueue.add(new SanConnection(m, query));
            }
        }
    }

    /**
     * Starts running the query with exports.
     */
    public void start() {
        SantosWindow.runButton.setEnabled(false);
        runQuery();
        ExportHandler.resetExport();
        //SantosWindow.runButton.setEnabled(true);
    }

    /**
     * Do start instead of run incase run is included.
     */
    @Override
    public void run() {
        start(); //To change body of generated methods, choose Tools | Templates.
    }

}
