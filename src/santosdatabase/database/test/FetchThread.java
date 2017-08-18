/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author justdasc
 */
public class FetchThread extends Thread{
    
    private final ResultSet rs;
    private final Integer col;
    private RequestThreader rq;
    
    public FetchThread(ResultSet rs, Integer col, RequestThreader rq)
    {
        this.rs = rs;
        this.col = col;
        this.rq = rq;
    }
    
    @Override
    public void run()
    {
        try {
            rs.next();
            ArrayList<Object> list = new ArrayList<>();
            for(int i = 1; i < col; i++)
            {
                list.add(rs.getObject(i));
            }
            rq.table.add(list);
            if(rq.threads.contains(this))
            {
                rq.threads.remove(this);
            }
            
        } catch (SQLException e) {
            rq.stopLoop = true;
        }
    }
    
}
