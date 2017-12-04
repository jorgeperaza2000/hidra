/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexiones;

import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author jorge
 */
public class utilitarios {
    public void setEnabled2(JPanel jPanel, Boolean en){
        setComponentsEnabled(jPanel, en);
    }
    
    private void setComponentsEnabled(JPanel jPanel, Boolean en ){
        Component[] components = jPanel.getComponents();
        for (Component comp: components) {
            if (comp instanceof JPanel) {
                setComponentsEnabled((JPanel) comp, en);
            }
            comp.setEnabled(en);
        }
    }
}
