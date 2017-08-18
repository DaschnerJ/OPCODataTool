/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author justdasc
 */
public class RequestThreader extends Thread {
    
    public ArrayList<Thread> threads;
    public ArrayList<ArrayList<Object>> table;
    public boolean stopLoop = false;
    
    private ResultSet rs;
    private Integer columns;
    private final Integer maxThreads;
    
    public RequestThreader(ResultSet rs, Integer maxThreads)
    {
        threads = new ArrayList<>();
        table = new ArrayList<>();
        this.rs = rs;
        try {
            this.columns = rs.getMetaData().getColumnCount();
        } catch (SQLException ex) {
            Logger.getLogger(RequestThreader.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.maxThreads = maxThreads;
        stopLoop = false;
    }
    
    @Override
    public void run()
    {
        int running = 0;
        while(!stopLoop)
        {
            
            if(threads.size() < maxThreads)
            {
                FetchThread fThread = new FetchThread(rs, columns, this);
                threads.add(fThread);
                fThread.run();
                running++;
                System.out.println(running);
            }
        }
        System.out.println("We have finished queueing threads...");
        System.out.println("So the final count of rows is...");
        System.out.println(table.size());
    }
    
    
}
