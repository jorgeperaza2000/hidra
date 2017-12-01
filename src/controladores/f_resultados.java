package controladores;

import configuracion.ws_config;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
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

public class f_resultados {
    
    private final Integer id;
    private final String sorteo;

    public f_resultados() {
        this.id = null;
        this.sorteo = null;
    }
    
    public void cargaComboSorteosResultados(JComboBox jCombo) throws IOException, ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "sorteos_resultados");
        get.addHeader("accept", "application/json");
        HttpResponse response = client.execute(get);
        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);
        
        DefaultComboBoxModel temporalModel = new DefaultComboBoxModel();
        jCombo.setModel(temporalModel);
        if (resultObject instanceof JSONArray) {
            JSONArray array=(JSONArray)resultObject;
            for (Iterator it = array.iterator(); it.hasNext();) {
                Object object = it.next();
                JSONObject obj =(JSONObject)object;
                temporalModel.addElement(((String) obj.get("sorteo")));
            }
        }
    }
    
    
    
    public void obtenerResultados(JTextField jTextFieldFecha, JLabel jLabelLA10, JLabel jLabelLA11, JLabel jLabelLA12, JLabel jLabelLA1, JLabel jLabelLA4, JLabel jLabelLA5, JLabel jLabelLA6, JLabel jLabelLA7) throws IOException, ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "resultados/" + jTextFieldFecha.getText());
        get.addHeader("accept", "application/json");
        HttpResponse response = client.execute(get);
        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);
        jLabelLA10.setIcon(null);
        jLabelLA11.setIcon(null);
        jLabelLA12.setIcon(null);
        jLabelLA1.setIcon(null);
        jLabelLA4.setIcon(null);
        jLabelLA5.setIcon(null);
        jLabelLA6.setIcon(null);
        jLabelLA7.setIcon(null);
        if (resultObject instanceof JSONArray) {
            JSONArray array=(JSONArray)resultObject;
            String numero;
            for (Iterator it = array.iterator(); it.hasNext();) {
                Object object = it.next();
                JSONObject obj =(JSONObject)object;
                if ( ( obj.get("numero_apuesta").toString().length() == 1 ) && ( !"0".equals(obj.get("numero_apuesta").toString()) ) ) {
                    numero = "0" + obj.get("numero_apuesta").toString();
                } else {
                    numero = obj.get("numero_apuesta").toString();
                }
                if ( obj.get("id_sorteo").equals("1")) {
                    jLabelLA10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + numero + ".jpg")));
                } 
                if ( obj.get("id_sorteo").equals("2")) {
                    jLabelLA11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + numero + ".jpg")));
                } 
                if ( obj.get("id_sorteo").equals("3")) {
                    jLabelLA12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + numero + ".jpg")));
                } 
                if ( obj.get("id_sorteo").equals("4")) {
                    jLabelLA1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + numero + ".jpg")));
                } 
                if ( obj.get("id_sorteo").equals("5")) {
                    jLabelLA4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + numero + ".jpg")));
                } 
                if ( obj.get("id_sorteo").equals("6")) {
                    jLabelLA5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + numero + ".jpg")));
                } 
                if ( obj.get("id_sorteo").equals("7")) {
                    jLabelLA6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + numero + ".jpg")));
                } 
                if ( obj.get("id_sorteo").equals("8")) {
                    jLabelLA7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + numero + ".jpg")));
                }
            }
        }
    }
    
    public void obtenerResultadosSimple(String fecha, JTable jTable) throws IOException, ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        DefaultTableModel modelo = (DefaultTableModel) jTable.getModel();
        int rowCount = modelo.getRowCount();
        for (int i = rowCount-1; i>=0;i--){
            modelo.removeRow(i);
        }
        
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "resultados_simples/" + fecha);
        get.addHeader("accept", "application/json");
        HttpResponse response = client.execute(get);
        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);
        
        DefaultTableModel temporalModel = (DefaultTableModel) jTable.getModel();
        if (resultObject instanceof JSONArray) {
            JSONArray array=(JSONArray)resultObject;
            String numero = null;
            for (Iterator it = array.iterator(); it.hasNext();) {
                Object object = it.next();
                JSONObject obj =(JSONObject)object;
                if ( ( obj.get("numero_apuesta").toString().length() == 1 ) && ( !"0".equals(obj.get("numero_apuesta").toString()) ) ) {
                    numero = "0" + obj.get("numero_apuesta").toString();
                } else {
                    numero = obj.get("numero_apuesta").toString();
                }
                String nombreApuesta = f_taquilla.getnombreAnimal(numero);
                Object resultados[]= { obj.get("sorteo"), numero + " - " + nombreApuesta};
                temporalModel.addRow(resultados);     
            }
        }
    }
    
}