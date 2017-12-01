/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author jorge
 */
public class RowsRenderer extends DefaultTableCellRenderer {
    private final int columna;
    
    public RowsRenderer(int colpatron) {
        this.columna = colpatron;
    }
    
    @Override
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
        table.setBackground(Color.white);
        table.setForeground(Color.black);
        super.getTableCellRendererComponent(table, value, focused, focused, row, column);
        if (table.getValueAt(row, columna).equals("Sorteo Cerrado")) {
            this.setForeground(Color.red);
        } else if ( table.getValueAt(row, columna).equals("Agotado")) {
            this.setForeground(Color.green);
        } else {
           this.setForeground(Color.black); 
        }
        return this;
    }    
}
