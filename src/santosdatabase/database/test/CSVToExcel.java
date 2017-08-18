/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

/**
 *
 * @author justdasc
 */
public class CSVToExcel {

    public void convert(String path) {
        FileInputStream fis = null;
        try {
            ArrayList arList = null;
            ArrayList al = null;
            String thisLine;
            int count = 0;
            fis = new FileInputStream(path);
            DataInputStream myInput = new DataInputStream(fis);
            int i = 0;
            arList = new ArrayList();
            while ((thisLine = myInput.readLine()) != null) {
                al = new ArrayList();
                String strar[] = thisLine.split(",");
                for (int j = 0; j < strar.length; j++) {
                    al.add(strar[j]);
                }
                arList.add(al);
                System.out.println();
                i++;
            }
            try {
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");
                for (int k = 0; k < arList.size(); k++) {
                    ArrayList ardata = (ArrayList) arList.get(k);
                    HSSFRow row = sheet.createRow((short) 0 + k);
                    for (int p = 0; p < ardata.size(); p++) {
                        HSSFCell cell = row.createCell((short) p);
                        String data = ardata.get(p).toString();
                        if (data.startsWith("=")) {
                            cell.setCellType(CellType.STRING);
                            data = data.replaceAll("\"", "");
                            data = data.replaceAll("=", "");
                            cell.setCellValue(data);
                        } else if (data.startsWith("\"")) {
                            data = data.replaceAll("\"", "");
                            cell.setCellType(CellType.STRING);
                            cell.setCellValue(data);
                        } else {
                            data = data.replaceAll("\"", "");
                            cell.setCellType(CellType.NUMERIC);
                            cell.setCellValue(data);
                        }
                        //*/
                        //   cell.setCellValue(ardata.get(p).toString());
                    }
                    System.out.println();
                }
                FileOutputStream fileOut = new FileOutputStream("test.xls");
                hwb.write(fileOut);
                fileOut.close();
                System.out.println("Your excel file has been generated");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVToExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSVToExcel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(CSVToExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
