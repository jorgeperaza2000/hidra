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
import javax.swing.JLabel;
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
public class f_login {
    
    private f_datos_usuario datosUsuario = new f_datos_usuario();
    
    public String getLogin(String usuario, String clave, JLabel jLabelMensaje) throws IOException, ParseException, InterruptedException {
        
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        
        ArrayList<NameValuePair> datos = new ArrayList<>();
        datos.add(new BasicNameValuePair("usuario", usuario));
        datos.add(new BasicNameValuePair("clave", clave));
        
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(pathToServer + "login");
        String respuesta = null;
        
        post.setEntity(new UrlEncodedFormEntity(datos));
        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException ex) {
            respuesta = "error";
            return respuesta;
        }
        if (response.getStatusLine().getStatusCode() == 404) {
            respuesta = "error";
            return respuesta;
        }
        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);

        if (resultObject instanceof JSONArray) {
            JSONArray array=(JSONArray)resultObject;
            for (Iterator it = array.iterator(); it.hasNext();) {
                Object object = it.next();
                JSONObject obj =(JSONObject)object;
                if( obj.get("codigoRespuesta").equals("ok") ) {
                    datosUsuario.idUsuario = Integer.parseInt((String) obj.get("id"));
                    datosUsuario.nombreUsuario = (String) obj.get("nombre");
                    datosUsuario.agenciaUsuario = (String) obj.get("agencia");
                    datosUsuario.tipoUsuario = (String) obj.get("tipo");
                    respuesta = "ok";
                } else if ( obj.get("codigoRespuesta").equals("error") ) {
                    respuesta = "error";
                }
            }    
        }
        return respuesta;
    }
    
}
