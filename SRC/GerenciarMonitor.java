/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import static hibernate.Tipo.LCD;
import static hibernate.Tipo.LED;
import static hibernate.Tipo.OLED;
import static hibernate.Tipo.TFT;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IFPR
 */
public class GerenciarMonitor {
    
    
    private SimpleDateFormat sdf;
    private PreparedStatement pstm;
    private Singleton singleton;
    private Connection conn;
    
    public GerenciarMonitor() {
	sdf = new SimpleDateFormat("yyyy-MM-dd");  
        conn = Singleton.getConnection();
    }
	
    public void create(Monitor monitor){

        try {
            
        String nome = monitor.getNome();
        int id = monitor.getId();
        String modeloo = monitor.getModelo();
        int numSerie = monitor.getNumSerie();
        int tamanho = monitor.getTamanho();
        String tipoMonitor = monitor.getTipo().toString();
        String data = sdf.format(monitor.getDataFabricacao());
        
       
         String sql = "insert into tb_monitores(nome,numSerie,dataFabricacao,modelo,tamanho,tipo)"
                +" values (?,?,?,?,?,?);";
        
        pstm = conn.prepareStatement(sql);
        pstm.setString(1, nome);
        pstm.setInt(2, numSerie);
        pstm.setString(3, data);
        pstm.setString(4, modeloo);
        pstm.setInt(5, tamanho);
        pstm.setString(6, tipoMonitor);
 
        int rows = pstm.executeUpdate();
        
        pstm.close();
         
        
        } catch (SQLException ex) {
            Logger.getLogger(GerenciarMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
                
                
	}
    public Collection<Monitor> listar(){
        
        Collection <Monitor> monitores = new ArrayList<>();
 
        try {
            String sql = "SELECT * FROM tb_monitores;";
            
            pstm = conn.prepareStatement(sql);          
           
            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){             
                
               Monitor monitor = new Monitor ();            
               String nome = rs.getString("nome");
               String dataFab = rs.getString("dataFabricacao");
               int numSerie = rs.getInt("numSerie");
               String modelo = rs.getString("modelo");
               int id = rs.getInt("id");
               int tamanho = rs.getInt("tamanho");
       
               monitor.setId(id);
               monitor.setTamanho(tamanho);
                try {  
                    monitor.setDataFabricacao(sdf.parse(dataFab));
                } catch (ParseException ex) {                   
                }
               monitor.setNome(nome);
               monitor.setNumSerie(numSerie);
               monitor.setModelo(modelo);                         
               monitores.add(monitor);   
            }            
            pstm.close();                 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return monitores;           
        }
    
    public Collection<Monitor> listar(String pesquisa) throws ParseException{
    
        Collection <Monitor> monitores = new ArrayList<>();
       
        try {
           
            
            String sql = "SELECT * FROM tb_monitores WHERE tb_monitores.nome LIKE concat('%', ?, '%');";
            
            pstm = conn.prepareStatement(sql);
            
            pstm.setString(1,pesquisa);
            
            ResultSet rs = pstm.executeQuery();
            
            Monitor monitor = new Monitor ();
           
            while(rs.next()){
              
               String nome = rs.getString("nome");
               String dataFab = rs.getString("dataFabricacao");
               int numSerie = rs.getInt("numSerie");
               String modelo = rs.getString("modelo");
               int id = rs.getInt("id");
               int tamanho = rs.getInt("tamanho");
               String sTipo = rs.getString("tipo");
               Tipo tipo = null;
               
               if(sTipo == "LCD"){
                   tipo = LCD;
               }else if(sTipo == "LED"){
                   tipo = LED;
               }else if(sTipo == "OLED"){
                   tipo = OLED;
               }else if(sTipo == "TFT"){
                   tipo = TFT;
               }
   
               monitor.setId(id);
               monitor.setTamanho(tamanho);
               monitor.setDataFabricacao(sdf.parse(dataFab));  
               monitor.setNome(nome);
               monitor.setNumSerie(numSerie);
               monitor.setModelo(modelo);
               monitor.setTipo(tipo);
               monitores.add(monitor);
              
            }
             
            pstm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


             return monitores;       
        }
    
     public void remover(int id){       
        try {
        
      
        String sql = "delete from tb_monitores" + " where id = " + id + ";";
        
        pstm = conn.prepareStatement(sql);      
        pstm.executeUpdate();

        pstm.close();
        }
        catch (SQLException ex) {
          ex.printStackTrace();
        }
        
	}
     
     public void update(Monitor monitor){
        
        try {
        
            String sql = "update tb_monitores set nome = ?,"
                     + "numSerie = ?," 
                    + "dataFabricacao = ?," 
                    + "modelo = ?,"
                    +"tamanho = ?,"
                    + "tipo = ?" 
                    + " where id = ?";

            pstm = conn.prepareStatement(sql);

            pstm.setString(1, monitor.getNome());
            pstm.setInt(2, monitor.getNumSerie());
            pstm.setString(3, sdf.format(monitor.getDataFabricacao()));
            pstm.setString(4, monitor.getModelo());
            pstm.setInt(5, monitor.getTamanho());
            pstm.setString(6, monitor.getTipo().toString());
            pstm.setInt(7, monitor.getId());

            pstm.executeUpdate();

            pstm.close();

        } catch (SQLException ex) {
          ex.printStackTrace();
        }          
}
       
   
    
}

