/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import configuracion.ws_config;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jorge
 */
public class f_rpt_tickets {
    
    
    public void formatjTable(JTable jTable){
        // TABLA DE TICKETS
        jTable.getColumnModel().getColumn(0).setResizable(false);
        jTable.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable.getColumnModel().getColumn(0).setMinWidth(0);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(0); 
        
        jTable.getColumnModel().getColumn(1).setResizable(false);
        jTable.getColumnModel().getColumn(1).setMaxWidth(160);
        jTable.getColumnModel().getColumn(1).setMinWidth(160);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        jTable.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(160);
        jTable.getTableHeader().getColumnModel().getColumn(1).setMinWidth(160);
        jTable.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(160); 
        
        jTable.getColumnModel().getColumn(2).setResizable(false);
        jTable.getColumnModel().getColumn(2).setMaxWidth(70);
        jTable.getColumnModel().getColumn(2).setMinWidth(70);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(70);
        jTable.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(70);
        jTable.getTableHeader().getColumnModel().getColumn(2).setMinWidth(70);
        jTable.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(70); 
        
        jTable.getColumnModel().getColumn(3).setResizable(false);
        jTable.getColumnModel().getColumn(3).setMaxWidth(100);
        jTable.getColumnModel().getColumn(3).setMinWidth(100);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(3).setMinWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(3).setPreferredWidth(100); 
        
        jTable.getColumnModel().getColumn(4).setResizable(false);
        jTable.getColumnModel().getColumn(4).setMaxWidth(100);
        jTable.getColumnModel().getColumn(4).setMinWidth(100);
        jTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(4).setMinWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(4).setPreferredWidth(100); 
        
        jTable.getColumnModel().getColumn(5).setResizable(false);
        jTable.getColumnModel().getColumn(5).setMaxWidth(100);
        jTable.getColumnModel().getColumn(5).setMinWidth(100);
        jTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(5).setMinWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(5).setPreferredWidth(100); 
        
    }
    
    public void formatjTableDet(JTable jTable){
        // TABLA DE TICKETS
        jTable.getColumnModel().getColumn(0).setResizable(false);
        jTable.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable.getColumnModel().getColumn(0).setMinWidth(0);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(0); 
        
        jTable.getColumnModel().getColumn(1).setResizable(false);
        jTable.getColumnModel().getColumn(1).setMaxWidth(100);
        jTable.getColumnModel().getColumn(1).setMinWidth(100);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(100); 
        
        jTable.getColumnModel().getColumn(2).setResizable(false);
        jTable.getColumnModel().getColumn(2).setMaxWidth(0);
        jTable.getColumnModel().getColumn(2).setMinWidth(0);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
        jTable.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(0); 
        
        jTable.getColumnModel().getColumn(3).setResizable(false);
        jTable.getColumnModel().getColumn(3).setMaxWidth(120);
        jTable.getColumnModel().getColumn(3).setMinWidth(120);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTable.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(120);
        jTable.getTableHeader().getColumnModel().getColumn(3).setMinWidth(120);
        jTable.getTableHeader().getColumnModel().getColumn(3).setPreferredWidth(120); 
        
        jTable.getColumnModel().getColumn(4).setResizable(false);
        jTable.getColumnModel().getColumn(4).setMaxWidth(100);
        jTable.getColumnModel().getColumn(4).setMinWidth(100);
        jTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(4).setMinWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(4).setPreferredWidth(100); 
        
        jTable.getColumnModel().getColumn(5).setResizable(false);
        jTable.getColumnModel().getColumn(5).setMaxWidth(100);
        jTable.getColumnModel().getColumn(5).setMinWidth(100);
        jTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(5).setMinWidth(100);
        jTable.getTableHeader().getColumnModel().getColumn(5).setPreferredWidth(100); 
        
    }
    
    public void obtenerTicketsPorEstatus(JTable jTable, JTextField jTextFieldFecha, JComboBox jComboBoxEstatus) throws IOException, ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        
        DefaultTableModel modelo = (DefaultTableModel) jTable.getModel();
        int rowCount = modelo.getRowCount();
        for (int i = rowCount-1; i>=0;i--){
            modelo.removeRow(i);
        }
        String agencia_usuario = f_datos_usuario.agenciaUsuario;
        String tipo = (String) jComboBoxEstatus.getSelectedItem();
        
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "rpt_tickets/" + jTextFieldFecha.getText() + "/" + tipo.replace(" ", "") + "/" + agencia_usuario);
        get.addHeader("accept", "application/json");
        HttpResponse response = client.execute(get);
        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);
        
        int T_total_ticket = 0;
        float T_total_monto = 0;
        float T_monto_premiado = 0;
        float T_monto_pagado = 0;
        float total_monto = 0;
        float monto_premiado = 0;
        float monto_pagado = 0;
        
        DefaultTableModel temporalModel = (DefaultTableModel) jTable.getModel();
        if (resultObject instanceof JSONArray) {
            JSONArray array=(JSONArray)resultObject;
            int i = 0;
            DecimalFormat df = new DecimalFormat();
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            for (Iterator it = array.iterator(); it.hasNext();) {
                Object object = it.next();
                JSONObject obj =(JSONObject)object;
                
                
                System.out.println(obj.get("monto_pagado"));
                
                total_monto = Float.parseFloat(String.valueOf(obj.get("total_monto")));
                monto_premiado = Float.parseFloat(String.valueOf(obj.get("monto_premiado")));
                monto_pagado = Float.parseFloat(String.valueOf((obj.get("monto_pagado")==null?0:obj.get("monto_pagado"))));
                
                Object tickets[] = { obj.get("id"), obj.get("fecha") + " " + obj.get("hora"), obj.get("numero_ticket"), String.valueOf(df.format(total_monto)), String.valueOf(df.format(monto_premiado)), String.valueOf(df.format(monto_pagado)), obj.get("pagado")};
                temporalModel.addRow(tickets);
                //TOTALES
                T_total_ticket = ++i;
                T_total_monto += total_monto;
                T_monto_premiado += monto_premiado;
                T_monto_pagado += monto_pagado;
            }
            Object tickets[] = { "", "    Totales:", T_total_ticket, String.valueOf(df.format(T_total_monto)), String.valueOf(df.format(T_monto_premiado)), String.valueOf(df.format(T_monto_pagado)), ""};
            temporalModel.addRow(tickets);
        }
    }
    
    public void obtenerDetalleTicketsPorId(JTable jTable, JLabel jLabelIdTicket) throws IOException, ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        DefaultTableModel modelo = (DefaultTableModel) jTable.getModel();
        int rowCount = modelo.getRowCount();
        for (int i = rowCount-1; i>=0;i--){
            modelo.removeRow(i);
        }
        String idTicket = jLabelIdTicket.getText();
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "rpt_det_tickets/" + idTicket);
        get.addHeader("accept", "application/json");
        HttpResponse response = client.execute(get);
        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);
        
        float monto_apuesta = 0;
        float monto_premiado = 0;
        DefaultTableModel temporalModel = (DefaultTableModel) jTable.getModel();
        if (resultObject instanceof JSONArray) {
            JSONArray array=(JSONArray)resultObject;
            DecimalFormat df = new DecimalFormat();
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            
            for (Iterator it = array.iterator(); it.hasNext();) {
                Object object = it.next();
                JSONObject obj =(JSONObject)object;
                monto_apuesta = Float.parseFloat(String.valueOf(obj.get("monto_apuesta")));
                monto_premiado = Float.parseFloat(String.valueOf(obj.get("monto_premiado")));
                
                Object tickets[] = { obj.get("numero_apuesta"), obj.get("apuesta"), obj.get("id_sorteo"), obj.get("sorteo"), String.valueOf(df.format(monto_apuesta)), String.valueOf(df.format(monto_premiado)), obj.get("fecha_pagado")};
                temporalModel.addRow(tickets);
            }
        }
    }
}

