/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import configuracion.ws_config;
import java.io.IOException;
import java.util.Iterator;
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
public class f_testWS {
    
    public String getTestWS() throws IOException, ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        String respuesta = null;
        
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "test_ws");
        get.addHeader("accept", "application/json");
        HttpResponse response = null;
        try {
            response = client.execute(get);
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
                respuesta = (String) obj.get("respuesta");
            }
        }
        return respuesta;
    }
}
