/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static santosdatabase.SantosDatabase.log;

/**
 *
 * @author justdasc
 */
public class CSVHandler {
    
        /**
     * Splits a list of lines by the delimiter. Mostly used for splitting .csv
     * files into usable java data.
     *
     * @param lines The list of lines to be split by the delimiter del.
     * @param del The delimiter to split by.
     * @return Returns the ArrayList of Arrays of lines without the delimiter.
     */
    public ArrayList<String[]> splitLines(ArrayList<String> lines, String del) {
        ArrayList<String[]> r = new ArrayList<>();
        for (String s : lines) {
            r.add(s.split(del));
        }
        return r;
    }

    /**
     * Combines a list of lines by a delimiter. Mostly used for combining Java
     * Data lines into a .csv format to be stored.
     *
     * @param lines The array list of lines to be combined with the delimiter
     * del.
     * @param del The delimiter to add to the lines.
     * @return Returns the lines combined with a delimiter.
     */
    public ArrayList<String> combineLines(ArrayList<String[]> lines, String del) {
        ArrayList<String> r = new ArrayList<>();
        for (String[] i : lines) {
            String s = null;
            for (String j : i) {
                if (s == null) {
                    s = j;
                } else {
                    s = s + del + j;
                }
            }
            r.add(s);
        }
        return r;
    }

    public ArrayList<String[]> getCSV(String path) {
        try {
            File f = new File(path);
            BufferedReader br = null;
            FileReader fr = null;
            try {
                fr = new FileReader(f);
                br = new BufferedReader(fr);
            } catch (FileNotFoundException e) {
                log.addSevere(e.getMessage());
            }
            ArrayList<String> r = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                r.add(line);
            }
            try
            {
                br.close();
                if(fr != null)
                    fr.close();
            }
            catch(IOException e)
            {
                log.addSevere(e.getMessage());
            }
            return splitLines(r, ",");
        } catch (IOException e) {
            log.addSevere(e.getMessage());
        }
        return null;
    }

    public void storeCSV(String path, ArrayList<String[]> lines) {
        ArrayList<String> l = combineLines(lines, ",");

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(path);
            bw = new BufferedWriter(fw);
            for (String s : l) {
                bw.write(s);
            }
            //System.out.println("Done");
        } catch (IOException e) {
            log.addSevere(e.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                log.addSevere(e.getMessage());
            }
        }
    }
    
}
