/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.test;

/**
 *
 * @author justdasc
 */
public class CommentGraveYard {
    //    private XSSFSheet getSheet(XSSFWorkbook wb, ResultSet rs, String tag) throws SQLException {
//        String sheetName = "";
//        System.out.println("Testing sheet condition.");
//        if (SantosWindow.outputSeparateSheets.isSelected() && !(rs.getMetaData().getTableName(1)).equals("")) {
//            sheetName = rs.getMetaData().getTableName(1);
//        } else if (tag.equals("") || SantosWindow.outputOneSheet.isSelected()) {
//            sheetName = "Sheet";
//        } else {
//            System.out.println("There is a tag!");
//            sheetName = tag;
//        }
//
//        sheetName = prefix + sheetName + suffix;
//
//        if (!(wb.getNumberOfSheets() > 0)) {
//            System.out.println("There isn't any sheets...");
//            return wb.createSheet(sheetName);
//        } else {
//            System.out.println("Need a new sheet name...");
//            String sheetNameFinal = this.getSheetName(wb, sheetName);
//            if (wb.getSheet(sheetNameFinal) == null) {
//                System.out.println("Sheet name failed, creating sheet.");
//                return wb.createSheet(sheetNameFinal);
//            } else {
//                System.out.println("Sheet name isn't null but needs to be created...");
//                return wb.getSheet(sheetNameFinal);
//            }
//        }
//
//    }

//    private String createFile(SanConnection sc, ResultSet rs, Boolean newSheet, String out, String tag) {
//        try {
//            //create object of XSSFWorkbook class
//            XSSFWorkbook wb;
//
//            if (new File(out).exists()) {
//                System.out.println("File exists already!");
//                if (SantosWindow.outputOverwriteExistingFiles.isSelected()) {
//                    if ((cancelOverride && !doNotSingleOverride) || (cancelOverride && SantosWindow.outputSeparateWorkbooks.isSelected())) {
//                        //case JOptionPane.YES_OPTION:
//                        new File(out).delete();
//                        wb = new XSSFWorkbook();
//                        //break;
//                    } else {
//                        InputStream ExcelFileToRead = new FileInputStream(out);
//                        wb = new XSSFWorkbook(ExcelFileToRead);
//                    }
//
//                } else {
//                    InputStream ExcelFileToRead = new FileInputStream(out);
//                    wb = new XSSFWorkbook(ExcelFileToRead);
//                }
//
//            } else {
//                System.out.println("File does not exist!");
//                wb = new XSSFWorkbook();
//            }
//
//            XSSFSheet sheet = getSheet(wb, rs, tag);
//
////            if (headers) {
////                XSSFRow rowhead;
////                rowhead = createColumnHeads(wb.getSheetAt(wb.getSheetIndex(sheet)), rs, sc.machine.tag);
////            }
//            FileOutputStream fileOut;
//            fileOut = new FileOutputStream(new File(out));
//            wb.write(fileOut);
//            fileOut.flush();
//            fileOut.close();
//            return sheet.getSheetName();
//        } catch (SQLException | FileNotFoundException ex) {
//            Logger.getLogger(ExportHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ExportHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "";
//    }

//    private String getSheetName(XSSFWorkbook wb, String sheetName) {
//        System.out.println("Grabbing new sheet name...");
//        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
//            System.out.println("Sheet being tested is name: " + wb.getSheetAt(i).getSheetName() + " against sheet named: " + sheetName);
//            if (wb.getSheetAt(i).getSheetName().equals(sheetName)) {
//                System.out.println("Matched to sheet " + sheetName + ".");
//                String sheet = wb.getSheetAt(i).getSheetName();
//                XSSFSheet ws = wb.getSheet(sheetName);
//                int r = ws.getLastRowNum() + 1;
//                System.out.println("Last row is:" + r);
//                System.out.println("Last row is: " + wb.getSheet(sheetName).getPhysicalNumberOfRows());
//                if (wb.getSheetAt(i).getLastRowNum() > 10000) {
//                    System.out.println("Getting new sheet, this sheet full.");
//                    return getSheetName(wb, sheetName, 0);
//                } else {
//                    System.out.println("The sheet isn't full yet.");
//                    return sheetName;
//                }
//            }
//        }
//        wb.createSheet(sheetName);
//        return sheetName;
//    }

//    private String getSheetName(XSSFWorkbook wb, String sheetName, Integer num) {
//        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
//            System.out.println("Sheet being test is name: " + wb.getSheetAt(i).getSheetName());
//            if (wb.getSheetAt(i).getSheetName().equals(sheetName + num)) {
//                if (wb.getSheet(sheetName + num).getLastRowNum() > 10000) {
//                    return getSheetName(wb, sheetName, num++);
//                } else {
//                    return sheetName + num;
//                }
//            }
//        }
//        wb.createSheet(sheetName + num);
//        return sheetName + num;
//    }

//    private static int Result = 0;

//    private void appendXRow(Integer rows, ResultSet rs, String out, String tag, String sheetName) {
//        System.out.println("Appending row... for " + tag);
//        switch (Result) {
//            case 0:
//                SantosWindow.completeLabel.setText("Running task for " + tag + ".");
//                break;
//            case 1:
//                SantosWindow.completeLabel.setText("Running task for " + tag + "..");
//                break;
//            default:
//                SantosWindow.completeLabel.setText("Running task for " + tag + "...");
//                break;
//        }
//        Result++;
//        if (Result >= 3) {
//            Result = 0;
//        }
//        try {
//            int shift = 0;
//            if (!tag.equals("") && SantosWindow.outputOneSheet.isSelected()) {
//                shift = 1;
//            }
//            int col = rs.getMetaData().getColumnCount() + shift;
//            FileInputStream in = null;
//            XSSFWorkbook wb;
//            try {
//                in = new FileInputStream(new File(out));
//                wb = new XSSFWorkbook(in);
//            } catch (NullPointerException e) {
//                wb = new XSSFWorkbook();
//            }
//            XSSFSheet ws = wb.getSheet(sheetName);
//            ws = this.getSheet(wb, rs, tag);
//            int r = ws.getLastRowNum() + 1;
//            System.out.println("Last row currently written:" + r);
//            //rs.next(); Not needed since we should call this before we append. Removing this adds flexability.
//            for (int j = 0; j < rows; j++) {
//
//                //System.out.println(j);
//                Row row = ws.createRow((short) r + j);
//                if (shift == 1) {
//                    row.createCell((short) 0).setCellValue(tag);
//                }
//                for (int i = 0 + shift; i < col && i < 255; i++) {
//                    row.createCell((short) i).setCellValue(String.valueOf(rs.getObject(i + 1 - shift)));
//
//                }
//                if (j + 1 < rows) {
//                    if (rs.next()) {
//
//                    } else {
//                        j = rows;
//                    }
//                }
//            }
//
//            FileOutputStream fileOut;
//            if (in != null) {
//                in.close();
//            }
//            fileOut = new FileOutputStream(new File(out));
//            wb.write(fileOut);
//            fileOut.flush();
//            fileOut.close();
//
//        } catch (SQLException | FileNotFoundException ex) {
//            Logger.getLogger(ExportHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ExportHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    
    //    private void exportLocationHandler(Boolean book, String tag) throws NullPointerException {
//        if (book && !setLocation) {
//            //Pick directory start...
//            System.out.println("Having user pick directory.");
//            this.location = new SanFileChooser(JFileChooser.DIRECTORIES_ONLY).getPath();
//            File file = new File(location);
//            if (!file.isDirectory()) {
//                System.out.println("It is not a location!");
//                exportLocationHandler(book, tag);
//                return;
//            }
//
//            System.out.println("Cut location:" + location);
//            if (this.location == null) {
//                SantosWindow.outputLabel.setText("User canceled the export.");
//                WindowHandler.stop = true;
//                throw new NullPointerException();
//            }
//            SantosWindow.outputFileLocation.setText(this.location);
//            if (SantosWindow.outputSeparateWorkbooks.isSelected()) {
//                setLocation = true;
//                loc = this.location;
//            }
//        } else if (SantosWindow.outputSeparateWorkbooks.isSelected() && setLocation) {
//            if (SantosWindow.outputSeparateWorkbooks.isSelected()) {
//                this.location = loc;
//            }
//        } else {
//            this.location = SantosWindow.outputFileLocation.getText();
//            if (SantosWindow.outputSeparateWorkbooks.isSelected()) {
//                setLocation = true;
//                loc = this.location;
//            }
//        }
//        //Pick directory end...
//        //Pick file name...
//        String name = "";
//        if (oneFileName == null) {
//            System.out.println("oneFileName is null...");
//            System.out.println("Tag is: " + tag);
//            if (SantosWindow.outputSeparateWorkbooks.isSelected()) {
//                if (SantosWindow.outputFileName.getText().equals("")) {
//                    name = JOptionPane.showInputDialog("Please input a file name to save to: ");
//                    if (name == null) {
//                        SantosWindow.outputLabel.setText("The filename was invalid.");
//                        WindowHandler.stop = true;
//
//                        throw new NullPointerException();
//                    }
//                } else {
//                    name = SantosWindow.outputFileName.getText() + tag;
//                    if (name == null) {
//                        SantosWindow.outputLabel.setText("The filename was invalid.");
//                        WindowHandler.stop = true;
//                        throw new NullPointerException();
//                    }
//                }
//            } else {
//                name = JOptionPane.showInputDialog("Please input a file name to save to: ");
//                System.out.println("Ran before null 1.");
//                oneFileName = name;
//                if (name == null) {
//                    SantosWindow.outputLabel.setText("The filename was invalid.");
//                    WindowHandler.stop = true;
//                    throw new NullPointerException();
//                }
//            }
//        } else {
//            System.out.println("OneFileName isn't null.");
//            name = oneFileName;
//            if (SantosWindow.outputSeparateWorkbooks.isSelected()) {
//                name = name + tag;
//            }
//        }
//        name = name + "";
//        if (name.equals("")) {
//            System.out.println("Name is blank, exiting method.");
//            //JOptionPane.showMessageDialog(null, "No file specified.");
//            return;
//        }
//        this.filename = name;
//        //Problem?
//        if (SantosWindow.outputOneSheet.isSelected() && oneFileName == null) {
//            oneFileName = name;
//        }
//        if (SantosWindow.outputOneSheet.isSelected()) {
//            oneFileName = name;
//        }
//        File f = new File(location + "\\" + prefix + filename + suffix + ".xlsx");
//        if (f.exists() && !SantosWindow.outputOverwriteExistingFiles.isSelected() && request) {
//            if (SantosWindow.outputOneSheet.isSelected()) {
//                request = false;
//            }
//            if (!cancelOverride) {
//                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to overwrite this file?", "Overwrite", JOptionPane.YES_NO_OPTION);
//                switch (reply) {
//                    case JOptionPane.NO_OPTION:
//                        System.out.println("Running no option.");
//                        try {
//                            exportLocationHandler(book, tag);
//                        } catch (NullPointerException e) {
//                            WindowHandler.stop = true;
//                        }
//                        break;
//                    case JOptionPane.YES_OPTION:
//                        cancelOverride = true;
//                        SantosWindow.outputOverwriteExistingFiles.setSelected(true);
//                        break;
//                    default:
//                        WindowHandler.stop = true;
//                        break;
//                }
//
//            }
//        }
//        if (SantosWindow.outputSeparateSheets.isSelected() || SantosWindow.outputOneSheet.isSelected()) {
//            cancelOverride = true;
//        }
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public void setFilename(String filename) {
//        this.filename = filename;
//    }
//
//    public void setNewSheet(Boolean newSheet) {
//        this.newSheet = newSheet;
//    }

//    /**
//     * Exports the data to an excel file. This does it per individual connection
//     * to the format that is required.
//     *
//     * @param sc The connection required to pull data from to populate the
//     * exported sheet.
//     * @param location
//     * @param filename
//     * @param newSheet
//     */
//    public void export(SanConnection sc, String location, String filename, Boolean newSheet) {
//        log.logTo("Starting export...", SantosWindow.logField);
//        log.logTo("Starting export...", SantosWindow.outputLabel);
//        ResultSet rs = sc.rs;
//        String out = location + "\\" + prefix + filename + suffix + ".xlsx";
//        String tag = sc.machine.tag;
//        writeOut(sc, rs, newSheet, out, tag);
//
//    }

//    public void writeOut(SanConnection sc, ResultSet rs, Boolean newSheet, String out, String tag) {
//        if (rs == null) {
//            //log.logTo("The query was invalid. Check the database login settings.", SantosWindow.logField);
//            log.logTo("The query was invalid. Check the database login settings.", SantosWindow.outputLabel);
//            return;
//        }
//        String sheet = createFile(sc, rs, newSheet, out, tag);
//        fillColumns(rs, out, sc.machine.tag, sheet);
//    }

//    public void export(SanConnection sc, String location, String filename) {
//        if (newSheet == null) {
//            export(sc, location, filename, false);
//        } else {
//            export(sc, location, filename, newSheet);
//        }
//    }

//    public void export(SanConnection sc, String filename) {
//        if (location == null) {
//            export(sc, new SanFileChooser(JFileChooser.DIRECTORIES_ONLY).getPath() + "\\", filename);
//        } else {
//            export(sc, location, filename);
//        }
//    }

//    public void export(SanConnection sc) {
//        if (filename == null) {
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.DATE, 1);
//            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd-HHmmssS");
//            //System.out.println(cal.getTime());
//            String formatted = format1.format(cal.getTime());
//            //System.out.println(formatted);
//
//            export(sc, formatted);
//        } else {
//            export(sc, filename);
//        }
//    }

//    private void fillColumns(ResultSet rs, String out, String tag, String sheetName) {
//        String name = sheetName;
//        try {
//            System.out.println("Appending more rows");
//            while (rs.next()) {
//                System.out.println("Checking the sheet again.");
//                System.out.println("Appending to sheet: " + name);
//                System.out.println("The row is not last!");
//                appendXRow(1000, rs, out, tag, sheetName);
//            }
//        } catch (Exception ex) {
//            log.logTo(ex.getMessage(), SantosWindow.logField);
//            log.logTo("An error occured while pulling the table. The data might be too large or an sql exception may have occured.", SantosWindow.outputLabel);
//        }
//        log.logTo("Finished writing...", SantosWindow.logField);
//        log.logTo("Finished writing...", SantosWindow.outputLabel);
//    }

//    public XSSFRow createColumnHeads(XSSFSheet sheet, ResultSet rs, String tag) {
//        int shift = 0;
//        try {
//            XSSFRow r = sheet.createRow((short) 0);
//            if (!tag.equals("") && SantosWindow.outputOneSheet.isSelected()) {
//                shift = 1;
//                r.createCell((short) 0).setCellValue("Name");
//            }
//
//            Integer col = rs.getMetaData().getColumnCount() + shift;
//            for (int i = 0 + shift; i < col && i < 254; i++) {
//                r.createCell((short) i).setCellValue(rs.getMetaData().getColumnName(i + 1 - shift));
//            }
//            return r;
//        } catch (SQLException ex) {
//            Logger.getLogger(SanConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

    //        System.out.println("Having user pick directory.");
//        this.location = new SanFileChooser(JFileChooser.DIRECTORIES_ONLY).getPath();
//        File file = new File(location);
//        if (!file.isDirectory()) {
//            System.out.println("It is not a location!");
//            getLocation();
//            return;
//        }
//
//        System.out.println("Cut location:" + location);
//        if (this.location == null) {
//            SantosWindow.outputLabel.setText("User canceled the export.");
//            WindowHandler.stop = true;
//            throw new NullPointerException();
//        }
//        SantosWindow.outputFileLocation.setText(this.location);
//        if (SantosWindow.outputSeparateWorkbooks.isSelected()) {
//            setLocation = true;
//            
//        }
//        setLocation = true;
//        loc = this.location;
    
//    private static String driver = "com.ibm.as400.access.AS400JDBCDriver";
//    private static String host = "RICWC01i";
//    private static String user = "JustDasc";
//    private static String pass = "buddy123";
//    private static String query = "SELECT * FROM fsdata.ititmf";
    //private static String database = "B680";
}
