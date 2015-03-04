package ch.heigvd.res.lab01.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    /**
     * This method looks for the next new line separators (\r, \n, \r\n) to extract
     * the next line in the string passed in arguments.
     *
     * @param lines a string that may contain 0, 1 or more lines
     * @return an array with 2 elements; the first element is the next line with the
     * line separator, the second element is the remaining text. If the argument does
     * not contain any line separator, then the first element is an empty string.
     */
    public static String[] getNextLine(String lines) {
        int indexSep = 0;
        String separator = "";
        
        
        //Unix
        if (lines.indexOf("\n") != -1) {
            separator = "\n";
        }
        
        //MacOS9
        if (lines.indexOf("\r") != -1) {
            separator = "\r";
        }
        
        //Windows
        if (lines.indexOf("\r\n") != -1) {
            separator = "\r\n";
        }
        
        String temp[] = new String[2];
        
        if(separator.equals("")){
            temp[0] = "";
            temp[1] = lines;
            return temp;
        }
        indexSep = lines.indexOf(separator);
        
        /*
        if (indexSep == -1) {
            temp[0] = "";
            temp[1] = lines;
            return temp;
        }        
        */
        
        temp = lines.split(separator, 2);
        temp[0] += separator;

        return temp;
    }

}
