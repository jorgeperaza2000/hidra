package controladores;

import configuracion.ws_config;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
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

public class f_sorteos {
    
    private final int horaMinCierre = 54;
    private final int horaMaxCierre = 59;
    public void getSorteos(final JTable jTableSorteos) {
        Timer timer;
        timer = new Timer();
        
        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                try {
                    Calendar calendario = Calendar.getInstance();
                    int minutos = calendario.get(Calendar.MINUTE);
                    if ( minutos >= horaMinCierre && minutos <= horaMaxCierre ) {
                        refrescaSorteos(jTableSorteos);
                    }                    
                } catch (ParseException | IOException ex) {
                    Logger.getLogger(f_sorteos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        // Empezamos dentro de 1ms y luego lanzamos la tarea cada 10000ms o 10Seg
        timer.schedule(task, 1, 30000);
    }
    
    public void refrescaSorteos(JTable jTableSorteos) throws IOException, ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "sorteos");
        get.addHeader("accept", "application/json");
        HttpResponse response = null;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 404 || response == null ) {
                
            } else {
                String json = EntityUtils.toString(response.getEntity(), "UTF-8");

                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(json);

                DefaultTableModel originalModel = (DefaultTableModel) jTableSorteos.getModel();
                if (resultObject instanceof JSONArray) {
                    JSONArray array=(JSONArray)resultObject;
                    //SOLO ACTUALIZAR 
                    if ( originalModel.getRowCount() != array.size() ) {
                        for (int i = originalModel.getRowCount()-1; i>=0;i--){
                            originalModel.removeRow(i);
                        }
                        for (Iterator it = array.iterator(); it.hasNext();) {
                            Object object = it.next();
                            JSONObject obj =(JSONObject)object;
                            String espacios = " ";
                            if ( obj.get("tecla").toString().length() == 4 ) {
                                espacios = "   ";
                            }
                            Object sorteos[]= { false, obj.get("id"), obj.get("tecla") + espacios + obj.get("sorteo")};
                            originalModel.addRow(sorteos);
                        }
                    }
                }
            }
        } catch (IOException ex) {
        }
    }
    
    public String nombreSorteos(int id_sorteo) {
        Map<Integer, String> nombreSorteo = new HashMap<>();
        nombreSorteo.put(9, "@LottoActivo 9");
        nombreSorteo.put(10, "@LottoActivo 10");
        nombreSorteo.put(11, "@LottoActivo 11");
        nombreSorteo.put(12, "@LottoActivo 12");
        nombreSorteo.put(1, "@LottoActivo 1");
        nombreSorteo.put(3, "@LottoActivo 3");
        nombreSorteo.put(4, "@LottoActivo 4");
        nombreSorteo.put(5, "@LottoActivo 5");
        nombreSorteo.put(6, "@LottoActivo 6");
        nombreSorteo.put(7, "@LottoActivo 7");
        return nombreSorteo.get(id_sorteo);
    }
    
}