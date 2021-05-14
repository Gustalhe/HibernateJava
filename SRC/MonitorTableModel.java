/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author IFPR
 */
public class MonitorTableModel extends AbstractTableModel{
    
    private GerenciarMonitor gerMonitor;
    private String [] columns;
    private Collection<Monitor> monitores;
    private Object [][] tableData;
    private SimpleDateFormat sdf;
    
     public MonitorTableModel(GerenciarMonitor gerMonitor) throws ParseException{
        columns = new String [] {"nome","numero de serie","data de fabricação","modelo"};
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.gerMonitor = gerMonitor;
        monitores = gerMonitor.listar();
        popularTableData();
                
    } 
     
    
    public int getRowCount() {
          return monitores.size();
    }
    public int getColumnCount() {
         return columns.length;
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableData [rowIndex] [columnIndex];
    }
    public String getColumnName(int column) {
        return columns [column];
    }
    public void setMonitores(Collection<Monitor> monitores) {
        this.monitores = monitores;
    }
    
     public void atualizarTabela() throws ParseException{
       monitores = gerMonitor.listar();
        popularTableData();
        fireTableDataChanged();
    }
    
    public void popularTableData() {
       
        tableData = new Object[monitores.size()][columns.length+1];

        int i = 0;
        for(Monitor m  : monitores){
           tableData[i][0] = m.getNome();
           tableData[i][1] = m.getNumSerie();
           tableData[i][2] = sdf.format(m.getDataFabricacao());
           tableData[i][3] = m.getModelo();
           tableData[i][4] = m;          
           i++;
        }

    
    }
}
