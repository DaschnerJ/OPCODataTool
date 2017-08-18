/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.type;

/**
 *
 * @author justdasc
 */
public class Machine {
    
    public String driver;
    public String host;
    public String database = "";
    public String tag = "";
    //We preset this data because it must have a value and not be null.
    public String user = "";
    public String pass = "";
    
    public Machine(String driver, String host)
    {
        this.driver = driver;
        this.host = host;
    }
    
    public Machine(String driver, String host, String database, String tag)
    {
        this.driver = driver;
        this.host = host;
        this.database = database;
        this.tag = tag;
    }
    
    public Machine(String driver, String host, String user, String pass, String tag)
    {
        this.driver = driver;
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.tag = tag;
    }
    
    public Machine(String driver, String host, String database, String user, String pass, String tag)
    {
        this.driver = driver;
        this.host = host;
        this.database = database;
        this.user = user;
        this.pass = pass;
        this.tag = tag;
    }
    
}
