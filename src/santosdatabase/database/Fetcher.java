/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author justdasc the amazing coder who likes to bunny hop in COD.
 */
public class Fetcher {
    
    /**
     * These variables should not change so we keep them as finals.
     * We do not want to adjust the sets incase another function requires
     * a set interval and the actual result set should remain the same or
     * major errors can happen or incorrect data can be retrieved.
     */
    private final Integer rows;
    private final ResultSet rs;
    
    /**
     * This is used to fetch x amount of rows from a result set.
     * This is helpful when populating excel sheets when there is
     * large amount of data and multiple sheets may need to be 
     * created. Also allows for controlled data transfer.
     * @param rs The result set to pull data from.
     * @param rows The amount of rows to pull.
     */
    public Fetcher(ResultSet rs, Integer rows)
    {
        this.rows = rows;
        this.rs = rs;
    }
    
    /**
     * Returns the next x amount of rows as a 2d array list of objects.
     * @return Returns the next x amount of rows as a 2d array list of objects.
     */
    public ArrayList<ArrayList<Object>> getRowSet()
    {
        ArrayList<ArrayList<Object>> list = new ArrayList<>();
        try {
            int c = 0; 
            while(c < rows)
            {
                rs.next();
                System.out.println(c);
                ArrayList<Object> obj = new ArrayList<>();
                int col = rs.getMetaData().getColumnCount();
                for(int i = 1; i < col; i++)
                {
                    obj.add(rs.getObject(i));
                }
                list.add(obj);
                c++;
            }
            return list;
            
        } catch (SQLException ex) {
           return list;
        }
    }
    
}
