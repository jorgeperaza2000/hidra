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
public class f_pagar_ticket {
    
    public String comprobarTicket(JTextField jTextFieldSerial, JTextField jTextFieldFecha ) throws ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        String pagar = null;
        ArrayList<NameValuePair> pagarTicket = new ArrayList<>();
        pagarTicket.add(new BasicNameValuePair("serial", jTextFieldSerial.getText()));
        pagarTicket.add(new BasicNameValuePair("fecha", jTextFieldFecha.getText()));
        pagarTicket.add(new BasicNameValuePair("opcion", "comprobar"));
        
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(pathToServer + "pagarticket");
        try {
            post.setEntity(new UrlEncodedFormEntity(pagarTicket));
            HttpResponse response = client.execute(post);
            //System.out.println(EntityUtils.toString(response.getEntity()));
            String json = EntityUtils.toString(response.getEntity(), "UTF-8");

            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(json);
            
            if (resultObject instanceof JSONArray) {
                JSONArray array = (JSONArray) resultObject;
                //System.out.println(array);
                //int i = 0;
                for (Iterator it = array.iterator(); it.hasNext();) {
                    Object object = it.next();
                    JSONObject obj =(JSONObject)object;
                    if ( obj.get("codigoRespuesta").equals("ok") ) {
                        if ( JOptionPane.showConfirmDialog(null, "Monto premiado: " + obj.get("montoPremiado") + " Desea Pagar?") == JOptionPane.OK_OPTION ) {
                            pagar = this.pagarTicket(jTextFieldSerial, jTextFieldFecha);
                        } 
                    } else if ( obj.get("codigoRespuesta").equals("pagado") ) {
                        JOptionPane.showMessageDialog(null, "El ticket fue pagado anteriormente.");
                        pagar = "error";
                    } else if ( obj.get("codigoRespuesta").equals("no-premio")) {
                        JOptionPane.showMessageDialog(null, "El ticket no posee premios.");
                        pagar = "error";
                    } else if ( obj.get("codigoRespuesta").equals("ticket-no-existe")) {
                        JOptionPane.showMessageDialog(null, "El ticket no existe, verifique e intente nuevamente.");
                        pagar = "error";
                    }
                }
            }
        } catch (IOException e) {
        }
        
        return pagar;
        
    }
    
    public String pagarTicket(JTextField jTextFieldSerial, JTextField jTextFieldFecha ) throws ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        
        ArrayList<NameValuePair> pagarTicket = new ArrayList<>();
        String respuesta = null;
        pagarTicket.add(new BasicNameValuePair("serial", jTextFieldSerial.getText()));
        pagarTicket.add(new BasicNameValuePair("fecha", jTextFieldFecha.getText()));
        pagarTicket.add(new BasicNameValuePair("opcion", "pagar"));
        
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(pathToServer + "pagarticket");
        try {
            post.setEntity(new UrlEncodedFormEntity(pagarTicket));
            HttpResponse response = client.execute(post);
            //System.out.println(EntityUtils.toString(response.getEntity()));
            String json = EntityUtils.toString(response.getEntity(), "UTF-8");

            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(json);
            
            if (resultObject instanceof JSONArray) {
                JSONArray array = (JSONArray) resultObject;
                for (Iterator it = array.iterator(); it.hasNext();) {
                    
                    Object object = it.next();
                    JSONObject obj =(JSONObject)object;
                    //System.out.println(obj.get("montoPremiado"));
                    if ( obj.get("codigoRespuesta").equals("ok") ) {
                        JOptionPane.showMessageDialog(null, "El ticket fue pagado con éxito!");
                        respuesta = "ok";
                    } else {
                        JOptionPane.showMessageDialog(null, "Ocurrio un error pagando el ticket.");
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
