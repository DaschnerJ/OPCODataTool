/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author justdasc
 */
public class SanExcel {

    ResultSet rs = null;

    public SanExcel(ResultSet rs) {
        this.rs = rs;
    }

    public HSSFWorkbook loadWorkbook(String location) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(location));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            return wb;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SanExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SanExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void saveWorkbook(HSSFWorkbook wb, String location) {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(location);
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SanExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SanExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createWorkbook(String name, String location) {
        String filename = location + name + ".xls";
        HSSFWorkbook workbook = new HSSFWorkbook();
        saveWorkbook(workbook, filename);
    }

    public void createSheet(String name, String location) {
        HSSFWorkbook wb = loadWorkbook(location);
        wb.createSheet(name);
        saveWorkbook(wb, location);
    }

    //public void writeResultSet(JTable table, )
    public void writeResultSet(String location) throws SQLException {
        HSSFWorkbook wb = loadWorkbook(location);
        HSSFSheet ws;
        ws = wb.createSheet("DATA");
        //JTable table = new JTable(buildTableModel(rs));
            //while(rs.next())

            Integer col = rs.getMetaData().getColumnCount();
            Integer row = 1;
//            RequestThreader rq = new RequestThreader(rs, 10000);
//            rq.run();
//            rs.next();
//            System.out.println(String.valueOf(rs.getObject(3)));
//            rs.next();
//            System.out.println(String.valueOf(rs.getObject(3)));
            while (rs.next()) {
                //System.out.println(row);
                HSSFRow r = ws.createRow(row);
                for (int j = 0; j < col && j < 255; j++) {
                    HSSFCell c;
                    c = r.createCell(j);
                    c.setCellValue(String.valueOf(rs.getObject(j+1)));
                }
                row++;
            }
                //buildTableModel(rs);
                //resultSetToArrayList(rs);

        saveWorkbook(wb, location);
    }
}
