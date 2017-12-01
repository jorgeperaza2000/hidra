/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import configuracion.ws_config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jorge
 */
public class f_anular_ticket {
    public String anular(JTextField jTextFieldNumeroTicket ) throws ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        
        ArrayList<NameValuePair> pagarTicket = new ArrayList<>();
        String respuesta = null;
        String agencia_usuario = f_datos_usuario.agenciaUsuario;
        
        pagarTicket.add(new BasicNameValuePair("numero_ticket", jTextFieldNumeroTicket.getText()));
        pagarTicket.add(new BasicNameValuePair("agencia", agencia_usuario));
        
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(pathToServer + "anular_ticket");
        try {
            post.setEntity(new UrlEncodedFormEntity(pagarTicket));
            HttpResponse response = client.execute(post);
            String json = EntityUtils.toString(response.getEntity(), "UTF-8");

            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(json);
            
            if (resultObject instanceof JSONArray) {
                JSONArray array = (JSONArray) resultObject;
                for (Iterator it = array.iterator(); it.hasNext();) {
                    
                    Object object = it.next();
                    JSONObject obj =(JSONObject)object;
                    if ( obj.get("codigoRespuesta").equals("ok") ) {
                        JOptionPane.showMessageDialog(null, "El ticket fue anulado con éxito!");
                        respuesta = "ok";
                    } else if ( obj.get("codigoRespuesta").equals("sorteos-cerrados") ) {
                        JOptionPane.showMessageDialog(null, "Existen sorteos cerrados en este ticket, por lo tanto no se puede anular.");
                        respuesta = "error";
                    } else if ( obj.get("codigoRespuesta").equals("ticket-anulado") ) {
                        JOptionPane.showMessageDialog(null, "El ticket ya ha sido anulado.");
                        respuesta = "error";
                    } else if ( obj.get("codigoRespuesta").equals("ticket-no-existe") ) {
                        JOptionPane.showMessageDialog(null, "El ticket no existe, verifique e intente nuevamente.");
                        respuesta = "error";
                    }
                }
            } else {
                respuesta = "error";
            }
        } catch (IOException e) {
        }
        return respuesta;
    }
}
