/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author jorge
 */
public class f_botones {
    
    public Boolean seleccionado;
    
    public void agregarIconoBotones(JPanel jPanelBonotesAnimales){
        Component[] comp = jPanelBonotesAnimales.getComponents();
        for (Component comp1 : comp) {
            if (comp1 instanceof JToggleButton) {
                JToggleButton button = (JToggleButton) comp1;
                if ( getClass().getResource("/imagenes/" + button.getToolTipText() + ".jpg") != null ){
                    button.setText(null);
                    button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + button.getToolTipText() + ".jpg")));
                }
            }
        }
    }
    
    public void deseleccionarTodosAnimales(JPanel jPanelBonotesAnimales, JTable jTableApuesta){
        DefaultTableModel modelo = (DefaultTableModel) jTableApuesta.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        jTableApuesta.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
                
        Component[] comp = jPanelBonotesAnimales.getComponents();
        for (Component comp1 : comp) {
            if (comp1 instanceof JToggleButton) {
                JToggleButton button = (JToggleButton) comp1;
                if ( button.isSelected() ) {
                    button.setSelected(false);
                }
            }
        }
    }
    
    public void seleccionarTodosAnimales(JPanel jPanelBonotesAnimales, JTable jTableApuesta){
        DefaultTableModel modelo = (DefaultTableModel) jTableApuesta.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        jTableApuesta.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
                
        Component[] comp = jPanelBonotesAnimales.getComponents();
        for (Component comp1 : comp) {
            if (comp1 instanceof JToggleButton) {
                JToggleButton button = (JToggleButton) comp1;
                button.setSelected(true);
            }
        }
    }
    
    public void verificaEstadoBotones(JPanel jPanelBonotesAnimales, JTextField jTextNumeroAnimal, JTextField jTextMontoAnimal){
        this.seleccionado = false;
        Component[] comp = jPanelBonotesAnimales.getComponents();
        for (Component comp1 : comp) {
            if (comp1 instanceof JToggleButton) {
                JToggleButton button = (JToggleButton) comp1;
                if ( button.isSelected() ) {
                    seleccionado = true;
                    if ( getClass().getResource("/imagenes/" + button.getToolTipText() + "_on.jpg") != null ){
                        button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + button.getToolTipText() + "_on.jpg")));
                    }
                } else {
                    if ( getClass().getResource("/imagenes/" + button.getToolTipText() + ".jpg") != null ){
                        button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + button.getToolTipText() + ".jpg")));
                    }
                }
            }
        }
        if ( seleccionado ) {
            jTextNumeroAnimal.setText(null);
            jTextNumeroAnimal.setEnabled(false);
            jTextMontoAnimal.requestFocus();
        } else {
            jTextNumeroAnimal.setText(null);
            jTextNumeroAnimal.setEnabled(true);
            jTextNumeroAnimal.requestFocus();
        }
    }
    
}
