/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package santosdatabase.database.gui;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author justdasc
 */
public class SQLParser {
    
    //List of black listed words. 
    ArrayList<String> blacklist = new ArrayList<>(Arrays.asList(
    "delete", "update", "alter", "truncate",
    "create", "drop", "insert", "modify"
    ));
    
    public SQLParser()
    {
        
    }
    
    /**
     * Splits the command up into an array list for parsing.
     * @param s The query command that is attempting to be ran.
     * @return Returns the array list of the command string.
     */
    private ArrayList<String> splitCommand(String s)
    {
        ArrayList<String> split = new ArrayList<String>();
        String[] spl = s.split("'");
        for(String str : spl)
        {
            String[] spli = str.split("\"");
            for(String st : spli)
            {
                split.add(st);
            }
        }
        return split;
    }
    
    /**
     * Validates if the string created does not contain the black listed words in the command.
     * @param s The string to validate.
     * @return Returns true if the string does not contain banned commands.
     */
    public boolean validate(String s)
    {
        ArrayList<String> list = splitCommand(s);
        for(String bl : blacklist)
        {
            for(int i = 0; i < list.size(); i++)
            {
                if(i%2 == 0)
                {
                    if(list.get(i).toLowerCase().contains(bl.toLowerCase()))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    
}
