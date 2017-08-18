/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.gui;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import static santosdatabase.SantosDatabase.log;

/**
 *
 * @author justdasc
 */
public class TableHandler {
    
    /**
     * Creates a table model from a result set. This method can be
     * taxing due to the requirements of having a result set have to
     * send a request back for each row when the row is needed. So it is
     * always recommended that the user defines which columns they want to
     * reduce the taxing cost of this method.
     * @param rs The result set that the table is needed to be built from.
     * @return Returns the default table model populated with the data from
     * the result set.
     */
    public DefaultTableModel buildTableModel(ResultSet rs) {

        /**
         * Here we declare the vector list for column names and another
         * 2d vector list for column data. We declare this up here because
         * it is used in multiple parts of the try and catch statements.
         */
        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> columnNames = new Vector<>();
        /**
         * We use try here because there probably will be an error
         * thrown if we are lacking x amount of lines. So we catch the
         * error and send back the data that we had.
         */
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            /**
             * Here we get the list of column names for the table header. 
             */
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            /**
             * Here we now start iterating by each row and populating a
             * 2d vector list we declared above. This way we can directly 
             * create the table with the 2d vector list of objects. 
             */
            
            /**
             * This sets the max amount of rows to load for preview.
             * We do not want to load the entire table so we only
             * load these few rows otherwise it will be taxing
             * on the system.
             */
            int previewMax = Integer.valueOf(SantosWindow.previewField.getText());
            int c = 0;
            while (rs.next() && c < previewMax) {
                Vector<Object> vector = new Vector<>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
                //The irony.
                c++;
            }
            
            //Return the results.
            log.logTo("Table preview created.", SantosWindow.outputLabel);
            return new DefaultTableModel(data, columnNames);
        } catch (Exception e) {
            //We ran out of rows so lets return what we have.
            log.logTo("Table preview created.", SantosWindow.outputLabel);
            return new DefaultTableModel(data, columnNames);
        }
        

    }
    
}
