/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class UtilityDate {

    public UtilityDate() {
    }
    
    public static String DateToString(Date x){
        if(x!=null){
         return new SimpleDateFormat("dd-MM-yyyy").format(x);}
        else{
            return "";
        }
    }
    public static Date StringToDate(String x){
        try {      
            return new SimpleDateFormat("dd-MM-yyyy").parse(x);
        } catch (ParseException ex) {
            Logger.getLogger(UtilityDate.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
