/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import santosdatabase.database.SanConnection;

/**
 *
 * @author justdasc
 */
public class ExcelSheetCreator {

    String sheetName = "sheet1";

    String fileLocation = null;
    String fileName = null;

    public boolean exportResultSetAsExcel(ResultSet rs) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(sheetName);
            HSSFRow rowhead;

            rowhead = createColumnHeads(sheet, rs);
            fillColumns(sheet, rs);

            String out = fileLocation + fileName;
            FileOutputStream fileOut;
            fileOut = new FileOutputStream(out);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private HSSFSheet fillColumns(HSSFSheet sheet, ResultSet rs) {
        try {
            int i = 1;
            int col = rs.getMetaData().getColumnCount();
            //Loop through the rows...
            while (rs.next()) {
                //We create the row here for the ith row.
                HSSFRow row = sheet.createRow((short) i);
                for (int j = 0; j < col; j++) {
                    if (rs.getObject(j) != null) {
                        row.createCell((short) j).setCellValue(String.valueOf(rs.getObject(j)));
                    }
                }
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sheet;
    }

    public HSSFRow createColumnHeads(HSSFSheet sheet, ResultSet rs) {
        try {
            HSSFRow r = sheet.createRow((short) 0);
            Integer col = rs.getMetaData().getColumnCount();
            for (int i = 0; i < col; i++) {
                r.createCell((short) i).setCellValue(rs.getMetaData().getColumnClassName(i));
            }
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(SanConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
