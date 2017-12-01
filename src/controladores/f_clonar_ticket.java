/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import configuracion.ws_config;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;
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
public class f_clonar_ticket {
    
    public void buscarTicket(JTable jTableApuestas, JTable jTableSorteos, JTable jTableSorteosDisponibles, JTextField jTextFieldFecha, JTextField jTextFieldNumeroTicket) throws IOException, ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        //SORTEOS DISPONIBLES
        this.buscarSorteosDisponibles(jTableSorteosDisponibles);
        
        //SORTEOS DEL TICKET
        DefaultTableModel modelo = (DefaultTableModel) jTableSorteos.getModel();
        for (int i = modelo.getRowCount()-1; i>=0;i--){
            modelo.removeRow(i);
        }
        DefaultTableModel modeloApuestas = (DefaultTableModel) jTableApuestas.getModel();
        for (int i = modeloApuestas.getRowCount()-1; i>=0;i--){
            modeloApuestas.removeRow(i);
        }
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "clonar_ticket/" + jTextFieldNumeroTicket.getText()+ "/" + jTextFieldFecha.getText());
        get.addHeader("accept", "application/json");
        
        HttpResponse response = client.execute(get);
        String json = EntityUtils.toString(response.getEntity(), "UTF-8");
        
        if ( !"null".equals(json) ) {
            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(json);

            DefaultTableModel temporalModelSorteos = (DefaultTableModel) jTableSorteos.getModel();
            DefaultTableModel temporalModelApuestas = (DefaultTableModel) jTableApuestas.getModel();
            if (resultObject instanceof JSONArray) {
                JSONArray array = (JSONArray) resultObject;
                for (Iterator it = array.iterator(); it.hasNext();) {
                    Object object = it.next();
                    JSONObject obj = (JSONObject)object;

                    JSONParser parserSorteos = new JSONParser();
                    Object resultObjectSorteos = parserSorteos.parse(obj.get("sorteos").toString());
                    JSONArray arraySorteos = (JSONArray) resultObjectSorteos;
                    for (Iterator itSorteos = arraySorteos.iterator(); itSorteos.hasNext();) {
                        Object objectSorteos = itSorteos.next();
                        JSONObject objSorteos = (JSONObject)objectSorteos;

                        Object resultadosSorteos[] = {objSorteos.get("id_sorteo"), objSorteos.get("sorteo")};
                        temporalModelSorteos.addRow(resultadosSorteos);
                    }

                    JSONParser parserTicketDet = new JSONParser();
                    Object resultObjectTicketDet = parserTicketDet.parse(obj.get("ticket_det").toString());
                    JSONArray arrayTicketDet = (JSONArray) resultObjectTicketDet;
                    for (Iterator itTicketDet = arrayTicketDet.iterator(); itTicketDet.hasNext();) {
                        Object objectTicketDet = itTicketDet.next();
                        JSONObject objTicketDet = (JSONObject)objectTicketDet;

                        Object resultadosTicketDet[] = { objTicketDet.get("numero_apuesta"), objTicketDet.get("numero_apuesta") + "-" + new f_taquilla().getnombreAnimal((String) objTicketDet.get("numero_apuesta")), objTicketDet.get("id_sorteo"), new f_sorteos().nombreSorteos(Integer.valueOf(String.valueOf(objTicketDet.get("id_sorteo")))), objTicketDet.get("monto")};
                        temporalModelApuestas.addRow(resultadosTicketDet);
                    } 
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "El numero de ticket que intenta clonar no existe. Verifique y vuelva a intentar", "Clonar Ticket", 1);
        }
    }
    
    public void buscarSorteosDisponibles(JTable jTableSorteosDisponibles) throws IOException, ParseException{
        
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();

        //SORTEOS DISPONIBLES
        DefaultTableModel modeloDisponibles = (DefaultTableModel) jTableSorteosDisponibles.getModel();
        int rowCount = modeloDisponibles.getRowCount();
        for (int i = rowCount-1; i>=0;i--){
            modeloDisponibles.removeRow(i);
        }
        
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "sorteos");
        get.addHeader("accept", "application/json");
        
        HttpResponse response = client.execute(get);
        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);
        
        DefaultTableModel temporalModel = (DefaultTableModel) jTableSorteosDisponibles.getModel();
        if (resultObject instanceof JSONArray) {
            JSONArray array=(JSONArray)resultObject;
            for (Iterator it = array.iterator(); it.hasNext();) {
                Object object = it.next();
                JSONObject obj =(JSONObject)object;
                Object resultados[] = {obj.get("id"), obj.get("sorteo")};
                temporalModel.addRow(resultados);
            }
        }
    }
    
    public void formatjTableSorteos(JTable jTableSorteos){
        
        // TABLA DE SORTEOS
        jTableSorteos.getColumnModel().getColumn(0).setResizable(false);
        jTableSorteos.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableSorteos.getColumnModel().getColumn(0).setMinWidth(0);
        jTableSorteos.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(0);
        
        jTableSorteos.getColumnModel().getColumn(2).setResizable(false);
        jTableSorteos.getColumnModel().getColumn(2).setMaxWidth(0);
        jTableSorteos.getColumnModel().getColumn(2).setMinWidth(0);
        jTableSorteos.getColumnModel().getColumn(2).setPreferredWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(0);
    }
    
    public void formatjTableSorteosDisponibles(JTable jTableSorteos){

        // TABLA DE SORTEOS
        jTableSorteos.getColumnModel().getColumn(0).setResizable(false);
        jTableSorteos.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableSorteos.getColumnModel().getColumn(0).setMinWidth(0);
        jTableSorteos.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(0);
        
    }
    
}
