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
    
    private final int horaCierre = 55;
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
                    if ( minutos == horaCierre ) {
                        refrescaSorteos(jTableSorteos);
                        System.out.println("Se ejecuto dentro" + minutos);
                    } else {
                        System.out.println("Se ejecuto" + minutos);
                    }
                    
                } catch (ParseException | IOException ex) {
                    Logger.getLogger(f_sorteos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        // Empezamos dentro de 1ms y luego lanzamos la tarea cada 10000ms o 10Seg
        timer.schedule(task, 1, 60000);
    }
    
    public void refrescaSorteos(JTable jTableSorteos) throws IOException, ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        DefaultTableModel modelo = (DefaultTableModel) jTableSorteos.getModel();
        int rowCount = modelo.getRowCount();
        for (int i = rowCount-1; i>=0;i--){
            modelo.removeRow(i);
        }
        
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "sorteos");
        get.addHeader("accept", "application/json");
        HttpResponse response = client.execute(get);
        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);
        
        DefaultTableModel temporalModel = (DefaultTableModel) jTableSorteos.getModel();
        if (resultObject instanceof JSONArray) {
            JSONArray array=(JSONArray)resultObject;
            for (Iterator it = array.iterator(); it.hasNext();) {
                Object object = it.next();
                JSONObject obj =(JSONObject)object;
                Object sorteos[]= { false, obj.get("id"), obj.get("sorteo")};
                temporalModel.addRow(sorteos);
            }
        }
    }
    
    public String nombreSorteos(int id_sorteo) {
        Map<Integer, String> nombreSorteo = new HashMap<>();
        nombreSorteo.put(1, "@LottoActivo 10");
        nombreSorteo.put(2, "@LottoActivo 11");
        nombreSorteo.put(3, "@LottoActivo 12");
        nombreSorteo.put(4, "@LottoActivo 1");
        nombreSorteo.put(5, "@LottoActivo 4");
        nombreSorteo.put(6, "@LottoActivo 5");
        nombreSorteo.put(7, "@LottoActivo 6");
        nombreSorteo.put(8, "@LottoActivo 7");
        return nombreSorteo.get(id_sorteo);
    }
    
}