/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import configuracion.ws_config;
import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import vistas.agotados;

/**
 *
 * @author jorge
 */


public class f_taquilla {
    public static Boolean sorteosVerificados = false;
    public Boolean apuestaAgotada = false;
    private final f_botones botones = new f_botones();
    public String contentTicket =  "::"+(char)27+(char)112+(char)0+(char)10+(char)100+"CRUZ VERDE - T. Original::\n"+
                                "Fecha:{{fecha}}"+
                                " Hora:{{hora}}\n"+
                                "SN:{{serial}} TN:{{numero_ticket}} TJ:{{total_jugadas}}\n"+
                                "================================"+
                                "{{apuestaFinal}}\n"+
                                "================================\n"+
                                "Total Venta (Bs):  {{total}}\n"+
                                "VERIFIQUE TICKET. CADUCA 3 DIAS\n"+
                                "\n\n\n ";
    
    public void generaTicket(JTable jTableApuesta, JTable jTableSorteos, JTextField jTextMontoAnimal, JLabel jLabelNumeroJugadas, JLabel jLabelMontoJugadas, JLabel jLabelNumeroTicket) throws ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        Map<String,String> ticketParaImprimir = new HashMap<>();
        SortedMap ticketParaImprimirDatos = new TreeMap();
        ArrayList<NameValuePair> jugadas = new ArrayList<>();
        List<String> apuestasAgotadas2 = new ArrayList<>();
        List<List<String>> apuestasAgotadas = new ArrayList<>();
        
        int cuantos = 0;
        int id_usuario = f_datos_usuario.idUsuario;
        String agencia_usuario = f_datos_usuario.agenciaUsuario;
        
        for (int i = 0; i <= jTableApuesta.getRowCount()-1; i++) {
            //jTableApuesta.setValueAt(jTextNuevoMonto.getText(), rows[i], 3);
            String numeroAnimal = String.valueOf(jTableApuesta.getValueAt(i, 0));
            String nombreAnimal = getnombreAnimal(String.valueOf(jTableApuesta.getValueAt(i, 0)));
            String idSorteo = String.valueOf(jTableApuesta.getValueAt(i, 2));
            String sorteo = String.valueOf(jTableApuesta.getValueAt(i, 3));
            String monto = String.valueOf(jTableApuesta.getValueAt(i, 4));
            String datosJugada = numeroAnimal+"|"+nombreAnimal+"|"+idSorteo+"|"+sorteo+"|"+monto+"|"+id_usuario+"|"+agencia_usuario;
            jugadas.add(new BasicNameValuePair("jugada"+i, datosJugada));
        }
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(pathToServer + "tickets");
        try {
            post.setEntity(new UrlEncodedFormEntity(jugadas));
            HttpResponse response = client.execute(post);
            String json = EntityUtils.toString(response.getEntity(), "UTF-8");

            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(json);
            
            if (resultObject instanceof JSONArray) {
                JSONArray array=(JSONArray)resultObject;
                int i = 0;
                for (Iterator it = array.iterator(); it.hasNext();) {
                    
                    //apuestasAgotadas = new ArrayList<>();
                    apuestasAgotadas.add(apuestasAgotadas2);
                    Object object = it.next();
                    JSONObject obj =(JSONObject)object;
                    if ( obj.get("codigoRespuesta").equals("ok")) {
                        
                        this.apuestaAgotada = false;
                        
                        ticketParaImprimir.put("numero_ticket", (String) obj.get("numero_ticket"));
                        ticketParaImprimir.put("fecha", (String) obj.get("fecha"));
                        ticketParaImprimir.put("hora", (String) obj.get("hora"));
                        ticketParaImprimir.put("serial", (String) obj.get("serial"));
                        ticketParaImprimir.put("total_apuestas", (String) obj.get("total_apuestas"));
                        ticketParaImprimir.put("total_monto", (String) obj.get("total_monto"));
                        String datos = obj.get("datos").toString();
                        JSONParser parserDatos = new JSONParser();
                        Object resultObjectDatos = parserDatos.parse(datos);
                        JSONArray arrayDatos=(JSONArray)resultObjectDatos;
                        int j = 0;
                        for (Iterator itDatos = arrayDatos.iterator(); itDatos.hasNext();) {
                            Object objectDatos = itDatos.next();
                            JSONObject objDatos =(JSONObject)objectDatos;
                            ticketParaImprimirDatos.put((String) objDatos.get("nombreApuesta"), (String) objDatos.get("monto"));
                            j = j + 1;
                        }
                        
                        limpiarApuesta(jTableApuesta, jTableSorteos, jTextMontoAnimal);
                        this.setMontoJugadas(jTableApuesta, jLabelMontoJugadas);
                        this.setNumeroJugadas(jTableApuesta, jLabelNumeroJugadas);
                        this.getNextNumeroTicket(jLabelNumeroTicket, this.cargarFechaHoy());
                        
                        //System.out.println(ticketParaImprimir);
                        this.printTicket(ticketParaImprimir.get("fecha"), ticketParaImprimir.get("hora"), ticketParaImprimir.get("serial"), ticketParaImprimir.get("numero_ticket"), ticketParaImprimir.get("total_apuestas"), ticketParaImprimir.get("total_monto"), ticketParaImprimirDatos);                        
                        
                        //JOptionPane.showMessageDialog(null, "Ticket creado con exito");
                        
                    } else if ( obj.get("codigoRespuesta").equals("sorteoCerrado")) {
                        
                        this.apuestaAgotada = true;
                        Integer montoDisponible = Integer.parseInt(obj.get("montoDisponible").toString());
                        apuestasAgotadas.get(i).add((String) obj.get("numeroApuesta") + "-" + obj.get("nombreApuesta"));
                        apuestasAgotadas.get(i).add((String) obj.get("sorteo"));
                        apuestasAgotadas.get(i).add((String) montoDisponible.toString());
                        apuestasAgotadas.get(i).add("Sorteo Cerrado");
                        i = i + 1;
                        cuantos = jTableApuesta.getRowCount();
                        actualizaMontosApuesta(jTableApuesta, cuantos, (String) obj.get("numeroApuesta"), (String) obj.get("idSorteo"), montoDisponible);
                        this.setMontoJugadas(jTableApuesta, jLabelMontoJugadas);
                        this.setNumeroJugadas(jTableApuesta, jLabelNumeroJugadas);
                        
                    } else if ( obj.get("codigoRespuesta").equals("montoLimite")) {
                        
                        this.apuestaAgotada = true;
                        Integer montoDisponible = Integer.parseInt(obj.get("montoDisponible").toString());
                        apuestasAgotadas.get(i).add((String) obj.get("numeroApuesta") + "-" + obj.get("nombreApuesta"));
                        apuestasAgotadas.get(i).add((String) obj.get("sorteo"));
                        apuestasAgotadas.get(i).add((String) montoDisponible.toString());
                        apuestasAgotadas.get(i).add("Agotado");
                        i = i + 1;
                        cuantos = jTableApuesta.getRowCount();
                        actualizaMontosApuesta(jTableApuesta, cuantos, (String) obj.get("numeroApuesta"), (String) obj.get("idSorteo"), montoDisponible);
                        this.setMontoJugadas(jTableApuesta, jLabelMontoJugadas);
                        this.setNumeroJugadas(jTableApuesta, jLabelNumeroJugadas);
                        
                    }                   
                }
                if ( this.apuestaAgotada == true ) {
                    new agotados().setVisible(true);
                    agotados.cargarAgotados(apuestasAgotadas);
                }
            }
        } catch (IOException e) {
        }
        
    }
    
    public void printTicket(String fecha, String hora, String serial, String numero_ticket, String total_jugadas, String total, SortedMap apuestas) throws IOException {
        this.contentTicket = this.contentTicket.replace("{{fecha}}", fecha);
        this.contentTicket = this.contentTicket.replace("{{hora}}", hora);
        this.contentTicket = this.contentTicket.replace("{{serial}}", serial);
        this.contentTicket = this.contentTicket.replace("{{numero_ticket}}", numero_ticket);
        this.contentTicket = this.contentTicket.replace("{{total_jugadas}}", total_jugadas);
        this.contentTicket = this.contentTicket.replace("{{total}}", total);
        
        String strApuesta = "";
        String apuestaFinal = "";
        String espacios = "";
        int cantEspacios = 0;
        
        
        Iterator iterator = apuestas.keySet().iterator();
        int i = 0;
        int j = 4;
        String id_sorteoAnterior = null;
        f_sorteos sorteos = new f_sorteos();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            String sorteo_NumeroApuesta = key.toString();
            String separador = Pattern.quote("|");
            String[] parts = sorteo_NumeroApuesta.split(separador);
            String id_sorteo = parts[0];
            String numero_apuesta = parts[1];
            
            if ( id_sorteo.equals(id_sorteoAnterior) ) {
                if ( i%2 == 0 ) {
                    strApuesta = numero_apuesta + " " + apuestas.get(key);
                    cantEspacios = 16 - strApuesta.length();
                    for (int x = 0; x < cantEspacios; x++) {
                        espacios = espacios + " ";
                    }
                    strApuesta = "\n" + strApuesta + espacios;
                    //printer.printTextWrap(j, j, 0, 17, numero_apuesta + " " + apuestas.get(key));
                } else {
                    strApuesta = numero_apuesta + " " + apuestas.get(key);
                    //printer.printTextWrap(j, j, 18, 36, numero_apuesta + " " + apuestas.get(key));
                    j = j + 1;
                }
            } else {
                //String strEnter = "\n";
                if ( i%2 != 0 ) {
                    j = j + 1;
                    //strEnter = "\n";
                }
                strApuesta = "[" + sorteos.nombreSorteos(Integer.valueOf(id_sorteo)) + "]";
                cantEspacios = (32 - strApuesta.length()) / 2;
                for (int x = 0; x < cantEspacios; x++) {
                    espacios = espacios + " ";
                }
                strApuesta = "\n" + espacios + strApuesta + "\n";
                
                strApuesta = strApuesta + numero_apuesta + " " + apuestas.get(key);
                String strApuestaAnimal = numero_apuesta + " " + apuestas.get(key);
                cantEspacios = 16 - strApuestaAnimal.length();
                espacios = "";
                for (int x = 0; x < cantEspacios; x++) {
                    espacios = espacios + " ";
                }
                strApuesta = strApuesta + espacios;
                //printer.printTextWrap(j, j, 4, 36, "[" + sorteos.nombreSorteos(Integer.valueOf(id_sorteo)) + "]");
                j = j + 1;
                //printer.printTextWrap(j, j, 0, 17, numero_apuesta + " " + apuestas.get(key));
                i = 0;
            }
            apuestaFinal = apuestaFinal + strApuesta;
            espacios = "";
            
            id_sorteoAnterior = id_sorteo;
            i = i + 1;
        }
        
        this.contentTicket = this.contentTicket.replace("{{apuestaFinal}}", apuestaFinal);
        
        File archivo = new File("lastTicket.txt");
        
        FileWriter bw = new FileWriter(archivo,false);

        bw.write(contentTicket);

        bw.close();
         
        byte[] bytes = this.contentTicket.getBytes();
        
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(bytes,flavor,null);
        
        if (service != null) {
            DocPrintJob job = service.createPrintJob();
            try {
                job.print(doc, null);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.err.println("No existen impresoras instaladas");
        }
        
        contentTicket =  "::"+(char)27+(char)112+(char)0+(char)10+(char)100+"CRUZ VERDE - T. Original::\n"+
                                "Fecha:{{fecha}}"+
                                " Hora:{{hora}}\n"+
                                "SN:{{serial}} TN:{{numero_ticket}} TJ:{{total_jugadas}}\n"+
                                "================================"+
                                "{{apuestaFinal}}\n"+
                                "================================\n"+
                                "Total Venta (Bs):  {{total}}\n"+
                                "VERIFIQUE TICKET. CADUCA 3 DIAS\n"+
                                "\n\n\n ";
        
    }
    
    public void actualizaMontosApuesta(JTable jTableApuesta, int cuantos, String numeroAnimal, String idSorteo, Integer monto){
        DefaultTableModel temporalModel = (DefaultTableModel) jTableApuesta.getModel();
        int t = cuantos;
        String sorteoId = null;
        String numero = null;
        int i = 0;
        
        for (i = 0; i < t; i++ ){//ANIMALES AGREGADOS A LA TABLA APUESTA
            sorteoId = String.valueOf(temporalModel.getValueAt(i, 2));
            numero = String.valueOf(temporalModel.getValueAt(i, 0));
            if ( numero.equals(numeroAnimal) ) {
                if ( sorteoId.equals(idSorteo) ) {
                    if ( monto > 0 ) {
                        temporalModel.setValueAt(monto, i, 4);
                    } else {
                        temporalModel.removeRow(i);
                        i = t;
                    }
                }
            }
        }
    }
    
    public void formatjTableApuesta(JTable jTableApuesta){
        // TABLA DE APUESTAS
        jTableApuesta.getColumnModel().getColumn(0).setResizable(false);
        jTableApuesta.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableApuesta.getColumnModel().getColumn(0).setMinWidth(0);
        jTableApuesta.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTableApuesta.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        jTableApuesta.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        jTableApuesta.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(0); 
        
        jTableApuesta.getColumnModel().getColumn(2).setResizable(false);
        jTableApuesta.getColumnModel().getColumn(2).setMaxWidth(0);
        jTableApuesta.getColumnModel().getColumn(2).setMinWidth(0);
        jTableApuesta.getColumnModel().getColumn(2).setPreferredWidth(0);
        jTableApuesta.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
        jTableApuesta.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
        jTableApuesta.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(0); 
    }
    
    public void formatjTableSorteos(JTable jTableSorteos){
        // TABLA DE SORTEOS
        jTableSorteos.getColumnModel().getColumn(0).setResizable(false);
        jTableSorteos.getColumnModel().getColumn(0).setMaxWidth(20);
        jTableSorteos.getColumnModel().getColumn(0).setMinWidth(20);
        jTableSorteos.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(20);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(20);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(20);
        
        jTableSorteos.getColumnModel().getColumn(1).setResizable(false);
        jTableSorteos.getColumnModel().getColumn(1).setMaxWidth(0);
        jTableSorteos.getColumnModel().getColumn(1).setMinWidth(0);
        jTableSorteos.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
        jTableSorteos.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(0);
    }
    
    public String formatNumeroAnimal(String numeroAnimal) {
        if ( ( 1 == numeroAnimal.length() ) && ( !"0".equals(numeroAnimal) ) ) {
            numeroAnimal = "0" + numeroAnimal;
        }
        return numeroAnimal;
    }
    
    public static String getnombreAnimal(String numeroAnimal){
        Map<String, String> nombreAnimal = new HashMap<>();
        nombreAnimal.put("0", "Delfin");
        nombreAnimal.put("00", "Ballena");
        nombreAnimal.put("01", "Carnero");
        nombreAnimal.put("02", "Toro");
        nombreAnimal.put("03", "Ciempies");
        nombreAnimal.put("04", "Alacran");
        nombreAnimal.put("05", "Leon");
        nombreAnimal.put("06", "Rana");
        nombreAnimal.put("07", "Perico");
        nombreAnimal.put("08", "Raton");
        nombreAnimal.put("09", "Aguila");
        nombreAnimal.put("10", "Tigre");
        nombreAnimal.put("11", "Gato");
        nombreAnimal.put("12", "Caballo");
        nombreAnimal.put("13", "Mono");
        nombreAnimal.put("14", "Paloma");
        nombreAnimal.put("15", "Zorro");
        nombreAnimal.put("16", "Oso");
        nombreAnimal.put("17", "Pavo");
        nombreAnimal.put("18", "Burro");
        nombreAnimal.put("19", "Chivo");
        nombreAnimal.put("20", "Cochino");
        nombreAnimal.put("21", "Gallo");
        nombreAnimal.put("22", "Camello");
        nombreAnimal.put("23", "Zebra");
        nombreAnimal.put("24", "Iguana");
        nombreAnimal.put("25", "Gallina");
        nombreAnimal.put("26", "Vaca");
        nombreAnimal.put("27", "Perro");
        nombreAnimal.put("28", "Zamuro");
        nombreAnimal.put("29", "Elefante");
        nombreAnimal.put("30", "Caiman");
        nombreAnimal.put("31", "Lapa");
        nombreAnimal.put("32", "Ardilla");
        nombreAnimal.put("33", "Pescado");
        nombreAnimal.put("34", "Venado");
        nombreAnimal.put("35", "Jirafa");
        nombreAnimal.put("36", "Culebra");
        return nombreAnimal.get(numeroAnimal);
    }
    
    public void setNumeroJugadas(JTable jTableApuesta, JLabel jLabelNumeroJugadas){
        int numeroJugadas = jTableApuesta.getRowCount();
        jLabelNumeroJugadas.setText(String.valueOf(numeroJugadas));
    }
    
    public void setMontoJugadas(JTable jTableApuesta, JLabel jLabelMontoJugadas){
        float montoJugadas = 0;
        for (int i = 0; i <= jTableApuesta.getRowCount()-1; i++) {
            montoJugadas += Float.parseFloat(String.valueOf(jTableApuesta.getValueAt(i, 4)));
        }
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        jLabelMontoJugadas.setText(String.valueOf(df.format(montoJugadas)));
    }
    
    public void getNextNumeroTicket(JLabel jLabelNumeroTicket, String fecha) throws IOException, ParseException {
        ws_config ws = new ws_config();
        String pathToServer = ws.getPath();
        String agencia_usuario = f_datos_usuario.agenciaUsuario;
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(pathToServer + "next_numero_ticket/" + fecha + "/" + agencia_usuario);
        get.addHeader("accept", "application/json");
        HttpResponse response = client.execute(get);
        String json = EntityUtils.toString(response.getEntity(), "UTF-8");

        JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);
        
        if (resultObject instanceof JSONArray) {
            JSONArray array=(JSONArray)resultObject;
            for (Iterator it = array.iterator(); it.hasNext();) {
                Object object = it.next();
                JSONObject obj =(JSONObject)object;
                jLabelNumeroTicket.setText( obj.get("numero_ticket").toString() );
            }
        }
    }
    
    public String cargarFechaHoy() {
        Calendar calendario = Calendar.getInstance();
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int ano = calendario.get(Calendar.YEAR);
        int mesCompleto = mes + 1;
        return dia+"-"+mesCompleto+"-"+ano;
    }
    
    public void agregarApuestaBotones(JPanel jPanelBonotesAnimales, JTable jTableApuesta, JTable jTableSorteos, JTextField jTextMontoAnimal){
        Component[] comp = jPanelBonotesAnimales.getComponents();
        String montoAnimal = jTextMontoAnimal.getText();
        Boolean existeAnimalEnSorteo = false;
        List<String> numeroAnimal = new ArrayList<>();
        int apuestas = jTableApuesta.getRowCount();
        
        for (Component comp1 : comp) {
            if (comp1 instanceof JToggleButton) {
                JToggleButton button = (JToggleButton) comp1;
                if ( button.isSelected() ) {
                    numeroAnimal.add(button.getToolTipText());
                }
            }
        }
        
        DefaultTableModel temporalModel = (DefaultTableModel) jTableApuesta.getModel();
        for ( int x = 0; x < numeroAnimal.size(); x++ ) { //ANIMALES SELECCIONADOS
            if ( 0 < apuestas ) { // SI LA TABLA APUESTAS NO ESTA VACIA
                for (int j = 0; j < jTableSorteos.getRowCount(); j++) { // RECORREMOS LA TABLA SORTEOS
                    Boolean sorteoSeleccionado = Boolean.valueOf(jTableSorteos.getValueAt(j, 0).toString());
                    if ( sorteoSeleccionado ) { // SI EL SORTEO ESTA SELECCIONADO
                        for (int i = 0; i < apuestas; i++ ) { //RECORREMOS LA TABLA APUESTAS
                            String sorteo1 = String.valueOf(jTableApuesta.getValueAt(i, 2));
                            String sorteo2 = String.valueOf(jTableSorteos.getValueAt(j, 1));
                            if ( sorteo1.equals(sorteo2) && String.valueOf(jTableApuesta.getValueAt(i, 0)).equals(numeroAnimal.get(x)) ) { 
                                Integer nuevoMontoAnimal = Integer.parseInt(String.valueOf(jTableApuesta.getValueAt(i, 4))) + Integer.parseInt(montoAnimal);
                                jTableApuesta.setValueAt(nuevoMontoAnimal, i, 4);
                                existeAnimalEnSorteo = false;
                                i = apuestas;
                            } else {
                                existeAnimalEnSorteo = true;
                            }
                        }
                        if ( existeAnimalEnSorteo ) {
                            Object apuesta[]= { numeroAnimal.get(x), numeroAnimal.get(x) + "-" + this.getnombreAnimal(numeroAnimal.get(x)), jTableSorteos.getValueAt(j, 1), jTableSorteos.getValueAt(j, 2), montoAnimal};
                            temporalModel.addRow(apuesta);
                        }
                    }
                }
            } else {
                for (int j = 0; j < jTableSorteos.getRowCount(); j++) {
                    Boolean sorteoSeleccionado = Boolean.valueOf(jTableSorteos.getValueAt(j, 0).toString());
                    if ( sorteoSeleccionado ) {
                        Object apuesta[]= { numeroAnimal.get(x), numeroAnimal.get(x) + "-" + this.getnombreAnimal(numeroAnimal.get(x)), jTableSorteos.getValueAt(j, 1), jTableSorteos.getValueAt(j, 2), montoAnimal};
                        temporalModel.addRow(apuesta);
                    }
                }
            }
        }
        
        botones.deseleccionarTodosAnimales(jPanelBonotesAnimales, jTableApuesta);
    }
    
    public void agregarApuesta(JTable jTableApuesta, JTable jTableSorteos, JTextField jTextNumeroAnimal, JTextField jTextMontoAnimal) {
        String numeroAnimal = this.formatNumeroAnimal(jTextNumeroAnimal.getText());
        String montoAnimal = jTextMontoAnimal.getText();
        Boolean existeAnimalEnSorteo = false;
        int apuestas = jTableApuesta.getRowCount();
        
        DefaultTableModel temporalModel = (DefaultTableModel) jTableApuesta.getModel();
        
        if ( 0 < apuestas ) { // SI LA TABLA APUESTAS NO ESTA VACIA
            for (int j = 0; j < jTableSorteos.getRowCount(); j++) { // RECORREMOS LA TABLA SORTEOS
                Boolean sorteoSeleccionado = Boolean.valueOf(jTableSorteos.getValueAt(j, 0).toString());
                if ( sorteoSeleccionado ) { // SI EL SORTEO ESTA SELECCIONADO
                    for (int i = 0; i < apuestas; i++ ) { //RECORREMOS LA TABLA APUESTAS
                        String sorteo1 = String.valueOf(jTableApuesta.getValueAt(i, 2));
                        String sorteo2 = String.valueOf(jTableSorteos.getValueAt(j, 1));
                        if ( sorteo1.equals(sorteo2) && String.valueOf(jTableApuesta.getValueAt(i, 0)).equals(numeroAnimal) ) { 
                            Integer nuevoMontoAnimal = Integer.parseInt(String.valueOf(jTableApuesta.getValueAt(i, 4))) + Integer.parseInt(montoAnimal);
                            jTableApuesta.setValueAt(nuevoMontoAnimal, i, 4);
                            existeAnimalEnSorteo = false;
                            i = apuestas;
                        } else {
                            existeAnimalEnSorteo = true;
                        }
                    }
                    if ( existeAnimalEnSorteo ) {
                        Object apuesta[]= { numeroAnimal, numeroAnimal + "-" + this.getnombreAnimal(numeroAnimal), jTableSorteos.getValueAt(j, 1), jTableSorteos.getValueAt(j, 2), montoAnimal};
                        temporalModel.addRow(apuesta);
                    }
                }
            }
        } else {
            for (int j = 0; j < jTableSorteos.getRowCount(); j++) {
                Boolean sorteoSeleccionado = Boolean.valueOf(jTableSorteos.getValueAt(j, 0).toString());
                if ( sorteoSeleccionado ) {
                    Object apuesta[]= { numeroAnimal, numeroAnimal + "-" + this.getnombreAnimal(numeroAnimal), jTableSorteos.getValueAt(j, 1), jTableSorteos.getValueAt(j, 2), montoAnimal};
                    temporalModel.addRow(apuesta);
                }
            }
        }
    }
    
    public void devuelveFoco(JTextField jTextNumeroAnimal) {
        jTextNumeroAnimal.requestFocus();
        jTextNumeroAnimal.setText(null);
    }
    
    public void setearTotalJugada(JTable jTableApuesta, JLabel jLabelNumeroJugadas, JLabel jLabelMontoJugadas) {
        this.setNumeroJugadas(jTableApuesta, jLabelNumeroJugadas);
        this.setMontoJugadas(jTableApuesta, jLabelMontoJugadas);
    }
    
    public void limpiarApuesta(JTable jTableApuesta, JTable jTableSorteos, JTextField jTextMontoAnimal) {
        DefaultTableModel modelo = (DefaultTableModel) jTableApuesta.getModel();
        int rowCount = modelo.getRowCount();
        for (int i = rowCount-1; i>=0;i--){
            modelo.removeRow(i);
        }
        
        DefaultTableModel temp = (DefaultTableModel) jTableSorteos.getModel();
        int rowCount2 = temp.getRowCount();
        for (int i = rowCount2-1; i>=0;i--){
            temp.setValueAt(false, i, 0);
        }
        jTextMontoAnimal.setText(null);
    }
}