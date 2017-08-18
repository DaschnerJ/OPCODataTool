/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database;

import com.ibm.as400.access.AS400JDBCDataSource;
import com.ibm.as400.access.AS400JDBCDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static santosdatabase.SantosDatabase.log;
import santosdatabase.database.gui.SantosWindow;
import santosdatabase.database.gui.WindowHandler;
import santosdatabase.database.type.Machine;

/**
 * @author justdasc
 */
public class SanConnection {

    //public Machine machine = null;

    //The query associated with this connection and that needs to be ran.
    String QUERY = null;
    
    //public String tag = "";

    //The machine is an object to hold connection information such as host and user.
    public Machine machine = null;
    
    /**
     * Note: A prepared statement is used for optimized performance and efficient resultset handling.
     * Using a normal statement does not help with this and causes the program to take excessive
     * amount of time when pulling larger data sets.
     */
    //The connection variables that hold the connection, statement, resultset, and datasource
    private Connection con;
    private PreparedStatement stmt;
    public ResultSet rs;
    private AS400JDBCDataSource ds;

    public SanConnection(String DRIVER, String HOST, String USER, String PASS, String TAG) {
        machine = new Machine(DRIVER, HOST, USER, PASS, TAG);
    }

    public SanConnection(String DRIVER, String HOST, String USER, String PASS, String QUERY, String TAG) {
        machine = new Machine(DRIVER, HOST, USER, PASS, TAG);
        this.QUERY = QUERY;
    }

    public SanConnection(String DRIVER, String HOST, String DATABASE, String USER, String PASS, String QUERY, String TAG) {
        System.out.println("Checking it three times?...");
        machine = new Machine(DRIVER, HOST, USER, PASS, TAG);
//        if(machine.user.equals(""))
//        {
//            machine.user = System.getProperty("user.name");
//        }
        this.executeDBQuery(QUERY, DATABASE);
    }

    public SanConnection(Machine m, String query) {
        System.out.println("Checking it twice...");
//        if(m.user.equals(""))
//        {
//            m.user = System.getProperty("user.name");
//        }
        machine = m;

        this.executeDBQuery(query, m.database);
    }

    /**
     * Creates and registers a driver for the connection to be created with.
     * @return Returns true if the driver was successfully found and registered.
     */
    public boolean createDriverConnector() {
        try {
            Class.forName(machine.driver);
            //con = DriverManager.getConnection(HOST, USER, PASS);
            DriverManager.registerDriver(new AS400JDBCDriver());
            ds = new AS400JDBCDataSource(machine.host);
        } catch (SQLException | ClassNotFoundException ex) {
            log.addSevere(ex.getMessage());
        }
        return true;
    }

    /**
     * Sets the prepared statement's credentials. In most cases, this is not set
     * other than in single server option.
     * @return 
     */
    public boolean setCredentials() {
        if(!machine.user.equals(""))
        {
            ds.setUser(machine.user);
        }
        if(!machine.pass.equals(""))
        {
           ds.setPassword(machine.pass);
        }
        ds.setNaming("sql");
        //We also assign the database to be ran on since this is technically a
        //credential.
        if(!machine.database.equals(""))
            ds.setDatabaseName(machine.database);
        return true;
    }

    /**
     * This is used in other areas of code but we also need to keep track of the
     * current database this connection is using.
     * @param DATABASE 
     */
    public void setDatabase(String DATABASE) {
        machine.database = DATABASE;
    }

    /**
     * Executes the query on the specific database.
     * @param QUERY The query to be executed.
     * @param DATABASE The database to be ran on.
     * @return Returns true if the connection was successfully established and
     * the query executed.
     */
    private boolean executeDBQuery(String QUERY, String DATABASE) {
        //Register the initial required drivers (jt400).
        createDriverConnector();
        //Store the query statement for later use.
        setQuery(QUERY);
        //Store the database information for later use.
        setDatabase(DATABASE);
        //Set the credentials in the prepared statement.
        setCredentials();
        //Attempt to create a connection.
        if (!createConnection()) {
            return false;
        }
        //Execute the query on the newly created connection.
        executeQuery();
        return true;
    }

    /**
     * Creates a new connection with the prepared statement and information.
     * @return Returns true if the connection was established and false if it was not.
     */
    public boolean createConnection() {
        try {
            ds.setToolboxTraceCategory("all");
            ds.setTrace(true);
            if (ds.getConnection() == null) {
                //log.logTo("Connection is null...", SantosWindow.logField);
                log.logTo("Connection is null...", SantosWindow.outputLabel);
            }
            con = ds.getConnection();
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Query was invalid.");
                WindowHandler.stop = true;
                //log.logTo("The query " + QUERY + " was invalid. Run cancelled.", SantosWindow.logField);
                log.logTo("The query " + QUERY + " was invalid. Run cancelled.", SantosWindow.outputLabel);
                return false;
            }
        } catch (SQLException ex) {
            log.addSevere(ex.toString());
        }
        return true;
    }

    /**
     * Executes the query on the created connection.
     * @return Returns true if the query was executed and resultset was retrieved
     * successfully.
     */
    public boolean executeQuery() {
        if (QUERY != null) {
            try {
                con.setAutoCommit(false);
                try {
                    this.stmt = con.prepareStatement(QUERY + " ORDER BY 1");
                    this.stmt.setFetchSize(50);
                    rs = this.stmt.executeQuery();    
                }
                catch(SQLException e)
                {
                    //log.logTo("Table was unable to be preformatted or it may already be included in the statement!", SantosWindow.logField);
                    log.logTo("Table was unable to be preformatted or it may already be included in the statement!", SantosWindow.outputLabel);
                }
                finally
                {
                    if(rs != null)
                        rs.close();
                    if(stmt != null)
                        stmt.close();
                }
                this.stmt = con.prepareStatement(QUERY);
                this.stmt.setFetchSize(50);
                rs = stmt.executeQuery();
            } catch (SQLException | NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Query was invalid.");
                WindowHandler.stop = true;
                //log.logTo("The query " + QUERY + " or connection was invalid. Run cancelled.", SantosWindow.logField);
                log.logTo("The query " + QUERY + " or connection was invalid. Run cancelled.", SantosWindow.outputLabel);
            }
        }
        return true;
    }

    /**
     * Sets the query for later use in the program.
     * @param QUERY The query to be stored.
     * @return Returns true if stored.
     */
    public boolean setQuery(String QUERY) {
        this.QUERY = QUERY;
        return true;
    }

    /**
     * Closes the connection once the connection is done begin used for good
     * java practice and reduce memory leaks.
     * @return Returns true if the connection was closed.
     */
    public boolean closeConnection() {
        try {
            if (!con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            log.addSevere(ex.getMessage());
        }
        return true;
    }

}
