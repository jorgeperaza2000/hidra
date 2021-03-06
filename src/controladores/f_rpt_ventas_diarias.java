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
public class f_rpt_ventas_diarias {
    
    public void obtenerVentasDiarias(JTable jTable, JTextField jTextFieldFecha, JLabel jLabelMensajeRespuesta) throws IOException, ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        
        DefaultTableModel modelo = (DefaultTableModel) jTable.getModel();
        int rowCount = modelo.getRowCount();
        for (int i = rowCount-1; i>=0;i--){
            modelo.removeRow(i);
        }
        String agencia_usuario = f_datos_usuario.agenciaUsuario;
        jLabelMensajeRespuesta.setText("");
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "rpt_ventas_diarias/" + jTextFieldFecha.getText() + "/" + agencia_usuario);
        get.addHeader("accept", "application/json");
        HttpResponse response = null;
        try {
            response = client.execute(get);
        
            String json = EntityUtils.toString(response.getEntity(), "UTF-8");

            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(json);
            float venta = 0;
            float premios = 0;
            float total = 0;
            DefaultTableModel temporalModel = (DefaultTableModel) jTable.getModel();
            if (resultObject instanceof JSONArray) {
                JSONArray array = (JSONArray)resultObject;
                DecimalFormat df = new DecimalFormat();
                df.setMinimumFractionDigits(2);
                df.setMaximumFractionDigits(2);
                
                for (Iterator it = array.iterator(); it.hasNext();) {
                    Object object = it.next();
                    JSONObject obj =(JSONObject)object;
                    venta = Float.parseFloat(String.valueOf((obj.get("venta")==null)?0:obj.get("venta")));
                    premios = Float.parseFloat(String.valueOf((obj.get("premios")==null)?0:obj.get("premios")));
                    total = Float.parseFloat(String.valueOf((obj.get("total")==null)?0:obj.get("total")));
                    
                    if ( obj.get("venta") == null ) {
                        jLabelMensajeRespuesta.setText("No hay datos para mostrar..."); 
                    } else {
                        Object ventasGenerales[] = { String.valueOf(df.format(venta)), String.valueOf(df.format(premios)), String.valueOf(df.format(total))};
                        temporalModel.addRow(ventasGenerales);
                    }
                }
            }
        } catch (IOException ex) {
            jLabelMensajeRespuesta.setText("Ocurrio un error consultando los datos... comprueba su conexion a internet.");
        }
    }
}


