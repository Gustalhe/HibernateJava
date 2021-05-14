/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IFPR
 */
public class Singleton {

    private volatile static Connection conn;
    
        
        private Singleton(){
            
        }
       public synchronized static Connection getConnection(){
            if(conn == null){
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_monitores ", "root", "");             
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(GerenciarMonitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           
           return conn;  
    }    
}
