/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import controladores.f_botones;
import controladores.f_datos_usuario;
import controladores.f_resultados;
import controladores.f_taquilla;
import controladores.f_sorteos;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jorge
 */
public class taquilla extends javax.swing.JFrame {
    
    /***************************/
    private Timer timer;
    /*
     * 1000ms ---- 1s
     *    xms ---- 60sx2m/s 
     *    xms = 120 x 1000 = 120.000ms
     * */
    private int delay = 120000; // every 1 second = 1000 milisegundos
    /***************************/    
    
    public static DefaultTableModel modeloTabla;
    private final f_taquilla taquilla = new f_taquilla();
    private final f_resultados resultados = new f_resultados();
    private final f_sorteos sorteos = new f_sorteos();
    private final f_botones botones = new f_botones();
    
    public taquilla() throws IOException, ParseException {
        botones.seleccionado = false;
        initComponents();
        this.jTextNumeroAnimal.requestFocus(true);
        botones.agregarIconoBotones(jPanelBonotesAnimales);
        this.setLocationRelativeTo(null);
        taquilla.formatjTableApuesta(jTableApuesta);
        taquilla.formatjTableSorteos(jTableSorteos);
        sorteos.refrescaSorteos(jTableSorteos);
        jTextNuevoMonto.setEnabled(false);
        sorteos.getSorteos(jTableSorteos);
        taquilla.getNextNumeroTicket(jLabelNumeroTicket, taquilla.cargarFechaHoy());
        this.correr(taquilla.cargarFechaHoy(), this.jTableResultados);
        
        
        if ( f_datos_usuario.tipoUsuario.equals("Admin") ) {
            this.jMenuConfiguracion.setVisible(true);
            this.jMenuItemVentasGenerales.setVisible(true);
            this.jMenuItemVentasDiarias.setVisible(false);
        } else {
            this.jMenuConfiguracion.setVisible(false);
            this.jMenuItemVentasGenerales.setVisible(false);
            this.jMenuItemVentasDiarias.setVisible(true);
        }
        
        
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0), "F1");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0), "F2");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0), "F3");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0), "F4");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0), "F5");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0), "F6");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0), "F7");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0), "F8");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0), "F8");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PLUS, 0), "+");
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ADD, 0), "+");
            
            getRootPane().getActionMap().put("+", new javax.swing.AbstractAction(){
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    if ( jTableApuesta.getRowCount() > 0 ) {
                        try {
                            taquilla.generaTicket(jTableApuesta, jTableSorteos, jTextMontoAnimal, jLabelNumeroJugadas, jLabelMontoJugadas, jLabelNumeroTicket );
                        } catch (ParseException ex) {
                            Logger.getLogger(taquilla.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El ticket que intenta imprimir esta vacio.", "Impresión de tickets", 1);
                    }
                }
            });
            getRootPane().getActionMap().put("F1", new javax.swing.AbstractAction(){
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    marcarSorteo("F1");
                }
            });
            getRootPane().getActionMap().put("F2", new javax.swing.AbstractAction(){
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    marcarSorteo("F2");
                }
            });
            getRootPane().getActionMap().put("F3", new javax.swing.AbstractAction(){
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    marcarSorteo("F3");
                }
            });
            getRootPane().getActionMap().put("F4", new javax.swing.AbstractAction(){
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    marcarSorteo("F4");
                }
            });
            
            getRootPane().getActionMap().put("F5", new javax.swing.AbstractAction(){
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    marcarSorteo("F5");
                }
            });
            getRootPane().getActionMap().put("F6", new javax.swing.AbstractAction(){
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    marcarSorteo("F6");
                }
            });
            getRootPane().getActionMap().put("F7", new javax.swing.AbstractAction(){
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    marcarSorteo("F7");
                }
            });
            getRootPane().getActionMap().put("F8", new javax.swing.AbstractAction(){
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e)
                {
                    marcarSorteo("F8");
                }
            });
            
    }
    
    public void correr(final String fecha, final JTable jTableApuesta ){       
        Timer timer;
        timer = new Timer();
        
        TimerTask task;
        task = new TimerTask() {
            @Override
            public void run()
            {
                try {
                    resultados.obtenerResultadosSimple(fecha, jTableApuesta);
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(taquilla.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        // Empezamos dentro de 1ms y luego lanzamos la tarea cada 10000ms o 10Seg
        timer.schedule(task, 1, 600000);
    }
    
    public void marcarSorteo(String tecla) {
        
        DefaultTableModel modelo = (DefaultTableModel) jTableSorteos.getModel();
        int rows = modelo.getRowCount();
        String sorteoId = null;
        String sorteo = null;
        if ( "F1".equals(tecla) ) { sorteoId = "1";}
        if ( "F2".equals(tecla) ) { sorteoId = "2";}
        if ( "F3".equals(tecla) ) { sorteoId = "3";}
        if ( "F4".equals(tecla) ) { sorteoId = "4";}
        if ( "F5".equals(tecla) ) { sorteoId = "5";}
        if ( "F6".equals(tecla) ) { sorteoId = "6";}
        if ( "F7".equals(tecla) ) { sorteoId = "7";}
        if ( "F8".equals(tecla) ) { sorteoId = "8";}
        for( int i = 0; i < rows; i++ ) {
            sorteo = modelo.getValueAt(i, 1).toString();
            if ( sorteo.equals(sorteoId) ) {
                if ( modelo.getValueAt(i, 0).equals(false) ) {
                    modelo.setValueAt(true, i, 0);
                } else {
                    modelo.setValueAt(false, i, 0);
                }
            } 
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * @return 
     */
    //@Override 
    //public Image getIconImage() {
        //Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagenes/favicon.png"));
        //return retValue;
    //}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu4 = new javax.swing.JMenu();
        jPanelPrincipal = new javax.swing.JPanel();
        jPanelBonotesAnimales = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jToggleButton5 = new javax.swing.JToggleButton();
        jToggleButton7 = new javax.swing.JToggleButton();
        jToggleButton8 = new javax.swing.JToggleButton();
        jToggleButton10 = new javax.swing.JToggleButton();
        jToggleButton12 = new javax.swing.JToggleButton();
        jToggleButton14 = new javax.swing.JToggleButton();
        jToggleButton15 = new javax.swing.JToggleButton();
        jToggleButton16 = new javax.swing.JToggleButton();
        jToggleButton18 = new javax.swing.JToggleButton();
        jToggleButton19 = new javax.swing.JToggleButton();
        jToggleButton20 = new javax.swing.JToggleButton();
        jToggleButton21 = new javax.swing.JToggleButton();
        jToggleButton22 = new javax.swing.JToggleButton();
        jToggleButton23 = new javax.swing.JToggleButton();
        jToggleButton6 = new javax.swing.JToggleButton();
        jToggleButton24 = new javax.swing.JToggleButton();
        jToggleButton9 = new javax.swing.JToggleButton();
        jToggleButton25 = new javax.swing.JToggleButton();
        jToggleButton11 = new javax.swing.JToggleButton();
        jToggleButton26 = new javax.swing.JToggleButton();
        jToggleButton13 = new javax.swing.JToggleButton();
        jToggleButton27 = new javax.swing.JToggleButton();
        jToggleButton17 = new javax.swing.JToggleButton();
        jToggleButton28 = new javax.swing.JToggleButton();
        jToggleButton29 = new javax.swing.JToggleButton();
        jToggleButton30 = new javax.swing.JToggleButton();
        jToggleButton31 = new javax.swing.JToggleButton();
        jToggleButton32 = new javax.swing.JToggleButton();
        jToggleButton33 = new javax.swing.JToggleButton();
        jToggleButton34 = new javax.swing.JToggleButton();
        jToggleButton35 = new javax.swing.JToggleButton();
        jToggleButton36 = new javax.swing.JToggleButton();
        jToggleButton37 = new javax.swing.JToggleButton();
        jToggleButton38 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jTextNumeroAnimal = new javax.swing.JTextField();
        jTextMontoAnimal = new javax.swing.JTextField();
        jButtonAgregarAnimal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButtonElimarApuesta = new javax.swing.JButton();
        jTextNuevoMonto = new javax.swing.JTextField();
        jButtonCancelarJugada = new javax.swing.JButton();
        jButtonEditarMonto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableApuesta = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex,int colIndex) {
                return false;
            }
        }
        ;
        jButtonJugar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableResultados = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabelNumeroTicket = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabelMontoJugadas = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelNumeroJugadas = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableSorteos = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenuItemSalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemVentasGenerales = new javax.swing.JMenuItem();
        jMenuItemVentasDiarias = new javax.swing.JMenuItem();
        jMenuItemResultados = new javax.swing.JMenuItem();
        jMenuItemTickets = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemPagarTicket = new javax.swing.JMenuItem();
        jMenuItemAnularTicket = new javax.swing.JMenuItem();
        jMenuItemClonarTicket = new javax.swing.JMenuItem();
        jMenuConfiguracion = new javax.swing.JMenu();
        jMenuItemSistema = new javax.swing.JMenuItem();

        jMenu4.setText("jMenu4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LottoActivo :: Ventas");
        setBackground(new java.awt.Color(0, 255, 153));
        setIconImage(getIconImage());
        setResizable(false);

        jPanelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        jPanelPrincipal.setForeground(new java.awt.Color(51, 204, 0));
        jPanelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelBonotesAnimales.setBackground(new java.awt.Color(255, 255, 255));

        jToggleButton1.setText("01");
        jToggleButton1.setToolTipText("01");
        jToggleButton1.setBorder(null);
        jToggleButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton1.setFocusable(false);
        jToggleButton1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton1ItemStateChanged(evt);
            }
        });

        jToggleButton2.setText("02");
        jToggleButton2.setToolTipText("02");
        jToggleButton2.setBorder(null);
        jToggleButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton2.setFocusable(false);
        jToggleButton2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton2ItemStateChanged(evt);
            }
        });

        jToggleButton3.setText("03");
        jToggleButton3.setToolTipText("03");
        jToggleButton3.setBorder(null);
        jToggleButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton3.setFocusable(false);
        jToggleButton3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton3ItemStateChanged(evt);
            }
        });

        jToggleButton4.setText("04");
        jToggleButton4.setToolTipText("04");
        jToggleButton4.setBorder(null);
        jToggleButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton4.setFocusable(false);
        jToggleButton4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton4ItemStateChanged(evt);
            }
        });

        jToggleButton5.setText("05");
        jToggleButton5.setToolTipText("05");
        jToggleButton5.setBorder(null);
        jToggleButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton5.setFocusable(false);
        jToggleButton5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton5ItemStateChanged(evt);
            }
        });

        jToggleButton7.setText("07");
        jToggleButton7.setToolTipText("07");
        jToggleButton7.setBorder(null);
        jToggleButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton7.setFocusable(false);
        jToggleButton7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton7ItemStateChanged(evt);
            }
        });

        jToggleButton8.setText("08");
        jToggleButton8.setToolTipText("08");
        jToggleButton8.setBorder(null);
        jToggleButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton8.setFocusable(false);
        jToggleButton8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton8ItemStateChanged(evt);
            }
        });

        jToggleButton10.setText("11");
        jToggleButton10.setToolTipText("11");
        jToggleButton10.setBorder(null);
        jToggleButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton10.setFocusable(false);
        jToggleButton10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton10ItemStateChanged(evt);
            }
        });

        jToggleButton12.setText("10");
        jToggleButton12.setToolTipText("10");
        jToggleButton12.setBorder(null);
        jToggleButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton12.setFocusable(false);
        jToggleButton12.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton12ItemStateChanged(evt);
            }
        });

        jToggleButton14.setText("14");
        jToggleButton14.setToolTipText("14");
        jToggleButton14.setBorder(null);
        jToggleButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton14.setFocusable(false);
        jToggleButton14.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton14ItemStateChanged(evt);
            }
        });

        jToggleButton15.setText("13");
        jToggleButton15.setToolTipText("13");
        jToggleButton15.setBorder(null);
        jToggleButton15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton15.setFocusable(false);
        jToggleButton15.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton15ItemStateChanged(evt);
            }
        });

        jToggleButton16.setText("17");
        jToggleButton16.setToolTipText("17");
        jToggleButton16.setBorder(null);
        jToggleButton16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton16.setFocusable(false);
        jToggleButton16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton16ItemStateChanged(evt);
            }
        });

        jToggleButton18.setText("16");
        jToggleButton18.setToolTipText("16");
        jToggleButton18.setBorder(null);
        jToggleButton18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton18.setFocusable(false);
        jToggleButton18.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton18ItemStateChanged(evt);
            }
        });

        jToggleButton19.setText("06");
        jToggleButton19.setToolTipText("06");
        jToggleButton19.setBorder(null);
        jToggleButton19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton19.setFocusable(false);
        jToggleButton19.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton19ItemStateChanged(evt);
            }
        });

        jToggleButton20.setText("12");
        jToggleButton20.setToolTipText("12");
        jToggleButton20.setBorder(null);
        jToggleButton20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton20.setFocusable(false);
        jToggleButton20.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton20ItemStateChanged(evt);
            }
        });

        jToggleButton21.setText("09");
        jToggleButton21.setToolTipText("09");
        jToggleButton21.setBorder(null);
        jToggleButton21.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton21.setFocusable(false);
        jToggleButton21.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton21ItemStateChanged(evt);
            }
        });

        jToggleButton22.setText("15");
        jToggleButton22.setToolTipText("15");
        jToggleButton22.setBorder(null);
        jToggleButton22.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton22.setFocusable(false);
        jToggleButton22.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton22ItemStateChanged(evt);
            }
        });
        jToggleButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton22ActionPerformed(evt);
            }
        });

        jToggleButton23.setText("18");
        jToggleButton23.setToolTipText("18");
        jToggleButton23.setBorder(null);
        jToggleButton23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton23.setFocusable(false);
        jToggleButton23.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton23ItemStateChanged(evt);
            }
        });
        jToggleButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton23ActionPerformed(evt);
            }
        });

        jToggleButton6.setText("22");
        jToggleButton6.setToolTipText("22");
        jToggleButton6.setBorder(null);
        jToggleButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton6.setFocusable(false);
        jToggleButton6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton6ItemStateChanged(evt);
            }
        });

        jToggleButton24.setText("30");
        jToggleButton24.setToolTipText("30");
        jToggleButton24.setBorder(null);
        jToggleButton24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton24.setFocusable(false);
        jToggleButton24.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton24ItemStateChanged(evt);
            }
        });

        jToggleButton9.setText("23");
        jToggleButton9.setToolTipText("23");
        jToggleButton9.setBorder(null);
        jToggleButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton9.setFocusable(false);
        jToggleButton9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton9ItemStateChanged(evt);
            }
        });

        jToggleButton25.setText("27");
        jToggleButton25.setToolTipText("27");
        jToggleButton25.setBorder(null);
        jToggleButton25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton25.setFocusable(false);
        jToggleButton25.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton25ItemStateChanged(evt);
            }
        });

        jToggleButton11.setText("25");
        jToggleButton11.setToolTipText("25");
        jToggleButton11.setBorder(null);
        jToggleButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton11.setFocusable(false);
        jToggleButton11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton11ItemStateChanged(evt);
            }
        });

        jToggleButton26.setText("33");
        jToggleButton26.setToolTipText("33");
        jToggleButton26.setBorder(null);
        jToggleButton26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton26.setFocusable(false);
        jToggleButton26.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton26ItemStateChanged(evt);
            }
        });
        jToggleButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton26ActionPerformed(evt);
            }
        });

        jToggleButton13.setText("26");
        jToggleButton13.setToolTipText("26");
        jToggleButton13.setBorder(null);
        jToggleButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton13.setFocusable(false);
        jToggleButton13.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton13ItemStateChanged(evt);
            }
        });

        jToggleButton27.setText("36");
        jToggleButton27.setToolTipText("36");
        jToggleButton27.setBorder(null);
        jToggleButton27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton27.setFocusable(false);
        jToggleButton27.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton27ItemStateChanged(evt);
            }
        });
        jToggleButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton27ActionPerformed(evt);
            }
        });

        jToggleButton17.setText("29");
        jToggleButton17.setToolTipText("29");
        jToggleButton17.setBorder(null);
        jToggleButton17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton17.setFocusable(false);
        jToggleButton17.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton17ItemStateChanged(evt);
            }
        });

        jToggleButton28.setText("28");
        jToggleButton28.setToolTipText("28");
        jToggleButton28.setBorder(null);
        jToggleButton28.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton28.setFocusable(false);
        jToggleButton28.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton28ItemStateChanged(evt);
            }
        });

        jToggleButton29.setText("32");
        jToggleButton29.setToolTipText("32");
        jToggleButton29.setBorder(null);
        jToggleButton29.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton29.setFocusable(false);
        jToggleButton29.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton29ItemStateChanged(evt);
            }
        });

        jToggleButton30.setText("31");
        jToggleButton30.setToolTipText("31");
        jToggleButton30.setBorder(null);
        jToggleButton30.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton30.setFocusable(false);
        jToggleButton30.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton30ItemStateChanged(evt);
            }
        });

        jToggleButton31.setText("19");
        jToggleButton31.setToolTipText("19");
        jToggleButton31.setBorder(null);
        jToggleButton31.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton31.setFocusable(false);
        jToggleButton31.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton31ItemStateChanged(evt);
            }
        });
        jToggleButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton31ActionPerformed(evt);
            }
        });

        jToggleButton32.setText("35");
        jToggleButton32.setToolTipText("35");
        jToggleButton32.setBorder(null);
        jToggleButton32.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton32.setFocusable(false);
        jToggleButton32.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton32ItemStateChanged(evt);
            }
        });

        jToggleButton33.setText("20");
        jToggleButton33.setToolTipText("20");
        jToggleButton33.setBorder(null);
        jToggleButton33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton33.setFocusable(false);
        jToggleButton33.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton33ItemStateChanged(evt);
            }
        });
        jToggleButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton33ActionPerformed(evt);
            }
        });

        jToggleButton34.setText("34");
        jToggleButton34.setToolTipText("34");
        jToggleButton34.setBorder(null);
        jToggleButton34.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton34.setFocusable(false);
        jToggleButton34.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton34ItemStateChanged(evt);
            }
        });

        jToggleButton35.setText("21");
        jToggleButton35.setToolTipText("21");
        jToggleButton35.setBorder(null);
        jToggleButton35.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton35.setFocusable(false);
        jToggleButton35.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton35ItemStateChanged(evt);
            }
        });

        jToggleButton36.setText("24");
        jToggleButton36.setToolTipText("24");
        jToggleButton36.setBorder(null);
        jToggleButton36.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton36.setFocusable(false);
        jToggleButton36.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton36ItemStateChanged(evt);
            }
        });

        jToggleButton37.setText("0");
        jToggleButton37.setToolTipText("0");
        jToggleButton37.setBorder(null);
        jToggleButton37.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton37.setFocusable(false);
        jToggleButton37.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton37ItemStateChanged(evt);
            }
        });

        jToggleButton38.setText("00");
        jToggleButton38.setToolTipText("00");
        jToggleButton38.setBorder(null);
        jToggleButton38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToggleButton38.setFocusable(false);
        jToggleButton38.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jToggleButton38ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanelBonotesAnimalesLayout = new javax.swing.GroupLayout(jPanelBonotesAnimales);
        jPanelBonotesAnimales.setLayout(jPanelBonotesAnimalesLayout);
        jPanelBonotesAnimalesLayout.setHorizontalGroup(
            jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jToggleButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBonotesAnimalesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                            .addComponent(jToggleButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9)
                            .addComponent(jToggleButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jToggleButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                            .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                                    .addComponent(jToggleButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(9, 9, 9)
                                    .addComponent(jToggleButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                                    .addComponent(jToggleButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(9, 9, 9)
                                    .addComponent(jToggleButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jToggleButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jToggleButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                            .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(9, 9, 9)
                            .addComponent(jToggleButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jToggleButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                            .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                                    .addComponent(jToggleButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(9, 9, 9)
                                    .addComponent(jToggleButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                                    .addComponent(jToggleButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(9, 9, 9)
                                    .addComponent(jToggleButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jToggleButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jToggleButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                            .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                                    .addComponent(jToggleButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(9, 9, 9)
                                    .addComponent(jToggleButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                                    .addComponent(jToggleButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(9, 9, 9)
                                    .addComponent(jToggleButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jToggleButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jToggleButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                            .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(9, 9, 9)
                            .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelBonotesAnimalesLayout.createSequentialGroup()
                                    .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jToggleButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBonotesAnimalesLayout.createSequentialGroup()
                                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBonotesAnimalesLayout.createSequentialGroup()
                        .addComponent(jToggleButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jToggleButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jToggleButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBonotesAnimalesLayout.createSequentialGroup()
                        .addComponent(jToggleButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jToggleButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jToggleButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelBonotesAnimalesLayout.setVerticalGroup(
            jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBonotesAnimalesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jToggleButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jToggleButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jToggleButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jToggleButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jToggleButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jToggleButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jToggleButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBonotesAnimalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jToggleButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jPanelPrincipal.add(jPanelBonotesAnimales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 610));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(19, 127, 141));
        jLabel1.setText("Animal:");
        jPanelPrincipal.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, -1, -1));

        jTextNumeroAnimal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextNumeroAnimal.setForeground(new java.awt.Color(19, 127, 141));
        jTextNumeroAnimal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextNumeroAnimal.setBorder(null);
        jTextNumeroAnimal.setName(""); // NOI18N
        jTextNumeroAnimal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextNumeroAnimalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextNumeroAnimalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextNumeroAnimalKeyTyped(evt);
            }
        });
        jPanelPrincipal.add(jTextNumeroAnimal, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 60, 20));

        jTextMontoAnimal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextMontoAnimal.setForeground(new java.awt.Color(19, 127, 141));
        jTextMontoAnimal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextMontoAnimal.setBorder(null);
        jTextMontoAnimal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextMontoAnimalFocusGained(evt);
            }
        });
        jTextMontoAnimal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextMontoAnimalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextMontoAnimalKeyTyped(evt);
            }
        });
        jPanelPrincipal.add(jTextMontoAnimal, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 70, 20));

        jButtonAgregarAnimal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonAgregarAnimal.setText("Jugar");
        jButtonAgregarAnimal.setToolTipText("Presione (Enter) para realizar apuesta");
        jButtonAgregarAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonAgregarAnimalMouseClicked(evt);
            }
        });
        jPanelPrincipal.add(jButtonAgregarAnimal, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 53, 70, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(19, 127, 141));
        jLabel2.setText("Monto:");
        jPanelPrincipal.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/monedas.jpg"))); // NOI18N
        jButton1.setToolTipText("Presione (P) para pagar ticket");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setMaximumSize(new java.awt.Dimension(80, 70));
        jButton1.setMinimumSize(new java.awt.Dimension(80, 70));
        jButton1.setPreferredSize(new java.awt.Dimension(80, 68));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanelPrincipal.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 84, 73));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/duplicar.jpg"))); // NOI18N
        jButton2.setToolTipText("Presione (C) para clonar ticket");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.setMaximumSize(new java.awt.Dimension(80, 70));
        jButton2.setMinimumSize(new java.awt.Dimension(80, 70));
        jButton2.setPreferredSize(new java.awt.Dimension(80, 70));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanelPrincipal.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 310, 79, 73));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anular.jpg"))); // NOI18N
        jButton3.setToolTipText("Presione (A) para anular ticket");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setMaximumSize(new java.awt.Dimension(100, 70));
        jButton3.setPreferredSize(new java.awt.Dimension(100, 70));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanelPrincipal.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 310, 80, 73));

        jButton4.setText("Imprimir Copia");
        jPanelPrincipal.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(241, 390, 260, 32));

        jButtonElimarApuesta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/delete2.jpg"))); // NOI18N
        jButtonElimarApuesta.setToolTipText("Eliminar Jugada");
        jButtonElimarApuesta.setBorder(null);
        jButtonElimarApuesta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonElimarApuestaActionPerformed(evt);
            }
        });
        jPanelPrincipal.add(jButtonElimarApuesta, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, 40, 40));

        jTextNuevoMonto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextNuevoMonto.setForeground(new java.awt.Color(19, 127, 141));
        jTextNuevoMonto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextNuevoMonto.setBorder(null);
        jTextNuevoMonto.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextNuevoMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNuevoMontoActionPerformed(evt);
            }
        });
        jTextNuevoMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextNuevoMontoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextNuevoMontoKeyPressed(evt);
            }
        });
        jPanelPrincipal.add(jTextNuevoMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 80, 20));

        jButtonCancelarJugada.setText("Cancelar");
        jButtonCancelarJugada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarJugadaActionPerformed(evt);
            }
        });
        jPanelPrincipal.add(jButtonCancelarJugada, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 50, -1, 40));

        jButtonEditarMonto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/edit.jpg"))); // NOI18N
        jButtonEditarMonto.setToolTipText("Editar Monto");
        jButtonEditarMonto.setBorder(null);
        jButtonEditarMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarMontoActionPerformed(evt);
            }
        });
        jPanelPrincipal.add(jButtonEditarMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 53, 40, 40));

        jTableApuesta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Jugada", "id_sorteo", "Sorteo", "Apuesta (Bs)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableApuesta.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableApuesta);
        if (jTableApuesta.getColumnModel().getColumnCount() > 0) {
            jTableApuesta.getColumnModel().getColumn(0).setResizable(false);
            jTableApuesta.getColumnModel().getColumn(1).setResizable(false);
            jTableApuesta.getColumnModel().getColumn(2).setResizable(false);
            jTableApuesta.getColumnModel().getColumn(3).setResizable(false);
            jTableApuesta.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(512, 97, 519, 510));

        jButtonJugar.setBackground(new java.awt.Color(19, 127, 141));
        jButtonJugar.setForeground(java.awt.Color.white);
        jButtonJugar.setText("Imprimir");
        jButtonJugar.setToolTipText("Presione (+) para imprimir");
        jButtonJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonJugarActionPerformed(evt);
            }
        });
        jPanelPrincipal.add(jButtonJugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(928, 50, 90, 40));

        jTableResultados.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sorteo", "Resultado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableResultados.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTableResultados);
        if (jTableResultados.getColumnModel().getColumnCount() > 0) {
            jTableResultados.getColumnModel().getColumn(0).setResizable(false);
            jTableResultados.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanelPrincipal.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 450, 260, 160));

        jPanel1.setBackground(new java.awt.Color(19, 127, 141));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("# Ticket:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jLabelNumeroTicket.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelNumeroTicket.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNumeroTicket.setText("0");
        jPanel1.add(jLabelNumeroTicket, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 36, 20));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Monto Total:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, 20));

        jLabelMontoJugadas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelMontoJugadas.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMontoJugadas.setText("0,00");
        jPanel1.add(jLabelMontoJugadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 96, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Numero de jugadas:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, 20));

        jLabelNumeroJugadas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelNumeroJugadas.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNumeroJugadas.setText("0");
        jPanel1.add(jLabelNumeroJugadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 29, 20));

        jPanelPrincipal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 520, 40));

        jSeparator2.setBackground(new java.awt.Color(19, 127, 141));
        jSeparator2.setForeground(new java.awt.Color(19, 127, 141));
        jPanelPrincipal.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 70, 10));

        jSeparator1.setBackground(new java.awt.Color(19, 127, 141));
        jSeparator1.setForeground(new java.awt.Color(19, 127, 141));
        jPanelPrincipal.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 90, 10));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(19, 127, 141));
        jLabel7.setText("Bs");
        jPanelPrincipal.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(19, 127, 141));
        jLabel8.setText("#");
        jPanelPrincipal.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, -1, -1));

        jSeparator3.setForeground(new java.awt.Color(19, 127, 141));
        jPanelPrincipal.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 80, -1));

        jTableSorteos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableSorteos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "", "Sorteo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSorteos.getTableHeader().setReorderingAllowed(false);
        jTableSorteos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSorteosMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableSorteosMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(jTableSorteos);
        if (jTableSorteos.getColumnModel().getColumnCount() > 0) {
            jTableSorteos.getColumnModel().getColumn(0).setResizable(false);
            jTableSorteos.getColumnModel().getColumn(1).setResizable(false);
            jTableSorteos.getColumnModel().getColumn(1).setPreferredWidth(0);
            jTableSorteos.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanelPrincipal.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(241, 97, 260, 205));

        jLabel6.setText("Ultimos Resultados del día");
        jPanelPrincipal.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 430, -1, -1));

        jMenu5.setText("Archivo");

        jMenuItemSalir.setText("Salir");
        jMenuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalirActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItemSalir);

        jMenuBar1.add(jMenu5);

        jMenu2.setText("Reportes");

        jMenuItemVentasGenerales.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, 0));
        jMenuItemVentasGenerales.setText("Ventas");
        jMenuItemVentasGenerales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVentasGeneralesActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemVentasGenerales);

        jMenuItemVentasDiarias.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, 0));
        jMenuItemVentasDiarias.setText("Ventas del Dia");
        jMenuItemVentasDiarias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemVentasDiariasActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemVentasDiarias);

        jMenuItemResultados.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, 0));
        jMenuItemResultados.setText("Resultados");
        jMenuItemResultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemResultadosActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemResultados);

        jMenuItemTickets.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, 0));
        jMenuItemTickets.setText("Tickets");
        jMenuItemTickets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTicketsActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemTickets);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Acciones");

        jMenuItemPagarTicket.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, 0));
        jMenuItemPagarTicket.setText("Pagar Ticket");
        jMenuItemPagarTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPagarTicketActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemPagarTicket);

        jMenuItemAnularTicket.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, 0));
        jMenuItemAnularTicket.setText("Anular Ticket");
        jMenuItemAnularTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAnularTicketActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemAnularTicket);

        jMenuItemClonarTicket.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, 0));
        jMenuItemClonarTicket.setText("Clonar Ticket");
        jMenuItemClonarTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemClonarTicketActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemClonarTicket);

        jMenuBar1.add(jMenu1);

        jMenuConfiguracion.setText("Configuracion");

        jMenuItemSistema.setText("Sistema");
        jMenuConfiguracion.add(jMenuItemSistema);

        jMenuBar1.add(jMenuConfiguracion);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 1043, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jMenuItemPagarTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPagarTicketActionPerformed
        new pagarTicket().setVisible(true);        
    }//GEN-LAST:event_jMenuItemPagarTicketActionPerformed

    private void jMenuItemAnularTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAnularTicketActionPerformed
        new anularTicket().setVisible(true);
    }//GEN-LAST:event_jMenuItemAnularTicketActionPerformed

    private void jMenuItemResultadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemResultadosActionPerformed
        try {
            new resultados().setVisible(true);
        } catch (ParseException | IOException ex) {
            Logger.getLogger(taquilla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemResultadosActionPerformed

    private void jMenuItemVentasGeneralesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVentasGeneralesActionPerformed
        new rpt_ventas_generales(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItemVentasGeneralesActionPerformed

    private void jMenuItemTicketsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTicketsActionPerformed
       new rpt_tickets().setVisible(true);
    }//GEN-LAST:event_jMenuItemTicketsActionPerformed

    private void jButtonJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonJugarActionPerformed
        if ( jTableApuesta.getRowCount() > 0 ) {
            try {
                taquilla.generaTicket(jTableApuesta, jTableSorteos, jTextMontoAnimal, jLabelNumeroJugadas, jLabelMontoJugadas, jLabelNumeroTicket );
            } catch (ParseException ex) {
                Logger.getLogger(taquilla.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "El ticket que intenta imprimir esta vacio.", "Impresión de tickets", 1);
        }
    }//GEN-LAST:event_jButtonJugarActionPerformed

    private void jButtonCancelarJugadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarJugadaActionPerformed
        taquilla.limpiarApuesta(jTableApuesta, jTableSorteos, jTextMontoAnimal);
        taquilla.setNumeroJugadas(jTableApuesta, jLabelNumeroJugadas);
        taquilla.setMontoJugadas(jTableApuesta, jLabelMontoJugadas);
    }//GEN-LAST:event_jButtonCancelarJugadaActionPerformed

    private void jTextNuevoMontoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNuevoMontoKeyPressed
        if ( evt.getKeyCode() == 10 ) {
            if ( this.jTextNuevoMonto.getText().length() == 0 ) {
                jTextNuevoMonto.setText(null);
                jTextNuevoMonto.setEnabled(false);
                jTableApuesta.clearSelection();
                taquilla.setNumeroJugadas(jTableApuesta, jLabelNumeroJugadas);
                taquilla.setMontoJugadas(jTableApuesta, jLabelMontoJugadas);
                taquilla.devuelveFoco(jTextNumeroAnimal);
            } else {
                int[] rows = jTableApuesta.getSelectedRows();
                for ( int i = 0; i <= rows.length-1; i++ ) {
                    jTableApuesta.setValueAt(jTextNuevoMonto.getText(), rows[i], 4);
                }
                jTextNuevoMonto.setText(null);
                jTextNuevoMonto.setEnabled(false);
                jTableApuesta.clearSelection();
                taquilla.setNumeroJugadas(jTableApuesta, jLabelNumeroJugadas);
                taquilla.setMontoJugadas(jTableApuesta, jLabelMontoJugadas);
                taquilla.devuelveFoco(jTextNumeroAnimal);
            }
        } else {
            
        }
        
    }//GEN-LAST:event_jTextNuevoMontoKeyPressed

    private void jButtonElimarApuestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonElimarApuestaActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) jTableApuesta.getModel();
        int[] rows = jTableApuesta.getSelectedRows();

        while( rows.length > 0 ) {
            modelo.removeRow(jTableApuesta.convertRowIndexToModel(rows[0]));
            rows = jTableApuesta.getSelectedRows();
        }
        jTableApuesta.clearSelection();
        taquilla.setNumeroJugadas(jTableApuesta, jLabelNumeroJugadas);
        taquilla.setMontoJugadas(jTableApuesta, jLabelMontoJugadas);
        taquilla.devuelveFoco(jTextNumeroAnimal);
    }//GEN-LAST:event_jButtonElimarApuestaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.jMenuItemAnularTicket.doClick();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.jMenuItemClonarTicket.doClick();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.jMenuItemPagarTicket.doClick();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonAgregarAnimalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAgregarAnimalMouseClicked
        Boolean haySorteoSeleccionado = false;
        for (int i = 0; i < jTableSorteos.getRowCount(); i++) {
            Boolean sorteoSeleccionado = Boolean.valueOf(jTableSorteos.getValueAt(i, 0).toString());
            if ( sorteoSeleccionado ) {
                haySorteoSeleccionado = true;
            }
        }
        if ( haySorteoSeleccionado ) {
            if ( botones.seleccionado ){
                if ( jTextMontoAnimal.getText().length() >= 2 ) {
                    taquilla.agregarApuestaBotones(jPanelBonotesAnimales, jTableApuesta, jTableSorteos, jTextMontoAnimal);
                    taquilla.devuelveFoco(jTextNumeroAnimal);
                    taquilla.setearTotalJugada(jTableApuesta, jLabelNumeroJugadas, jLabelMontoJugadas);
                } else {
                    getToolkit().beep();
                    jTextMontoAnimal.requestFocus();
                    evt.consume();
                }
            } else {
                if ( jTextNumeroAnimal.getText().length() > 0 ) {
                    taquilla.agregarApuesta(jTableApuesta, jTableSorteos, jTextNumeroAnimal, jTextMontoAnimal);
                    taquilla.devuelveFoco(jTextNumeroAnimal);
                    taquilla.setearTotalJugada(jTableApuesta, jLabelNumeroJugadas, jLabelMontoJugadas);
                } else {
                    getToolkit().beep();
                    jTextNumeroAnimal.requestFocus();
                    evt.consume();
                }
            }
        } else {
            getToolkit().beep();
        }
    }//GEN-LAST:event_jButtonAgregarAnimalMouseClicked

    private void jTextMontoAnimalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextMontoAnimalKeyTyped
        char c = evt.getKeyChar();
        if(Character.isDigit(c)) {
            
        } else {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextMontoAnimalKeyTyped

    private void jTextMontoAnimalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextMontoAnimalKeyPressed
        Boolean haySorteoSeleccionado = false;
        if ( evt.getKeyCode() == 10 ){
            if ( jTextMontoAnimal.getText().length() >= 2 ) {
                for (int i = 0; i < jTableSorteos.getRowCount(); i++) {
                    Boolean sorteoSeleccionado = Boolean.valueOf(jTableSorteos.getValueAt(i, 0).toString());
                    if ( sorteoSeleccionado ) {
                        haySorteoSeleccionado = true;
                    }
                }
                if ( haySorteoSeleccionado ) {
                    if ( botones.seleccionado ){
                        if ( jTextMontoAnimal.getText().length() >= 2 ) {
                            taquilla.agregarApuestaBotones(jPanelBonotesAnimales, jTableApuesta, jTableSorteos, jTextMontoAnimal);
                            taquilla.devuelveFoco(jTextNumeroAnimal);
                            taquilla.setearTotalJugada(jTableApuesta, jLabelNumeroJugadas, jLabelMontoJugadas);
                        } else {
                            getToolkit().beep();
                            jTextMontoAnimal.requestFocus();
                            evt.consume();
                        }
                    } else {
                        if ( jTextNumeroAnimal.getText().length() > 0 ) {
                            taquilla.agregarApuesta(jTableApuesta, jTableSorteos, jTextNumeroAnimal, jTextMontoAnimal);
                            taquilla.devuelveFoco(jTextNumeroAnimal);
                            taquilla.setearTotalJugada(jTableApuesta, jLabelNumeroJugadas, jLabelMontoJugadas);
                        } else {
                            getToolkit().beep();
                            jTextNumeroAnimal.requestFocus();
                            evt.consume();
                        }
                    }
                } else {
                    getToolkit().beep();
                }
            } else {
                getToolkit().beep();
                evt.consume();
            }
        }
    }//GEN-LAST:event_jTextMontoAnimalKeyPressed

    private void jTextMontoAnimalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextMontoAnimalFocusGained
        jTextMontoAnimal.selectAll();
    }//GEN-LAST:event_jTextMontoAnimalFocusGained

    private void jTextNumeroAnimalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNumeroAnimalKeyTyped
        if ( jTextNumeroAnimal.getText().length() >= 2 ) {
            evt.consume();
        }
        char c=evt.getKeyChar();
        if(Character.isDigit(c)) {
            
        } else {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextNumeroAnimalKeyTyped

    private void jTextNumeroAnimalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNumeroAnimalKeyReleased
        if ( 10 == evt.getKeyCode() ) {
            if ( jTextNumeroAnimal.getText().length() > 0 ) {
                if ( ( Integer.valueOf(jTextNumeroAnimal.getText()) > 36 ) || ( Integer.valueOf(jTextNumeroAnimal.getText()) < 0 ) ) {
                    jTextNumeroAnimal.setText("");
                    jTextNumeroAnimal.requestFocus();
                    getToolkit().beep();
                } else {
                    jTextMontoAnimal.requestFocus();
                }
            } else {
                evt.consume();
            }
        }
    }//GEN-LAST:event_jTextNumeroAnimalKeyReleased

    private void jTextNumeroAnimalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNumeroAnimalKeyPressed
        
    }//GEN-LAST:event_jTextNumeroAnimalKeyPressed

    private void jToggleButton38ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton38ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton38ItemStateChanged

    private void jToggleButton37ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton37ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton37ItemStateChanged

    private void jToggleButton36ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton36ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton36ItemStateChanged

    private void jToggleButton35ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton35ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton35ItemStateChanged

    private void jToggleButton34ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton34ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton34ItemStateChanged

    private void jToggleButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton33ActionPerformed
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton33ActionPerformed

    private void jToggleButton33ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton33ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton33ItemStateChanged

    private void jToggleButton32ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton32ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton32ItemStateChanged

    private void jToggleButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton31ActionPerformed
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton31ActionPerformed

    private void jToggleButton31ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton31ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton31ItemStateChanged

    private void jToggleButton30ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton30ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton30ItemStateChanged

    private void jToggleButton29ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton29ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton29ItemStateChanged

    private void jToggleButton28ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton28ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton28ItemStateChanged

    private void jToggleButton17ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton17ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton17ItemStateChanged

    private void jToggleButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton27ActionPerformed
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton27ActionPerformed

    private void jToggleButton27ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton27ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton27ItemStateChanged

    private void jToggleButton13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton13ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton13ItemStateChanged

    private void jToggleButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton26ActionPerformed
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton26ActionPerformed

    private void jToggleButton26ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton26ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton26ItemStateChanged

    private void jToggleButton11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton11ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton11ItemStateChanged

    private void jToggleButton25ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton25ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton25ItemStateChanged

    private void jToggleButton9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton9ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton9ItemStateChanged

    private void jToggleButton24ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton24ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton24ItemStateChanged

    private void jToggleButton6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton6ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton6ItemStateChanged

    private void jToggleButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton23ActionPerformed
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton23ActionPerformed

    private void jToggleButton23ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton23ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton23ItemStateChanged

    private void jToggleButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton22ActionPerformed
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton22ActionPerformed

    private void jToggleButton22ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton22ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton22ItemStateChanged

    private void jToggleButton21ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton21ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton21ItemStateChanged

    private void jToggleButton20ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton20ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton20ItemStateChanged

    private void jToggleButton19ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton19ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton19ItemStateChanged

    private void jToggleButton18ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton18ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton18ItemStateChanged

    private void jToggleButton16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton16ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton16ItemStateChanged

    private void jToggleButton15ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton15ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton15ItemStateChanged

    private void jToggleButton14ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton14ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton14ItemStateChanged

    private void jToggleButton12ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton12ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton12ItemStateChanged

    private void jToggleButton10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton10ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton10ItemStateChanged

    private void jToggleButton8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton8ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton8ItemStateChanged

    private void jToggleButton7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton7ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton7ItemStateChanged

    private void jToggleButton5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton5ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton5ItemStateChanged

    private void jToggleButton4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton4ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton4ItemStateChanged

    private void jToggleButton3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton3ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton3ItemStateChanged

    private void jToggleButton2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton2ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton2ItemStateChanged

    private void jToggleButton1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jToggleButton1ItemStateChanged
        botones.verificaEstadoBotones(jPanelBonotesAnimales, jTextNumeroAnimal, jTextMontoAnimal);
    }//GEN-LAST:event_jToggleButton1ItemStateChanged

    private void jTableSorteosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSorteosMouseClicked
        if ( jTextNumeroAnimal.isEnabled() ) {
            jTextNumeroAnimal.requestFocus();
        } else {
            jTextMontoAnimal.requestFocus();
        }
    }//GEN-LAST:event_jTableSorteosMouseClicked

    private void jTableSorteosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSorteosMouseReleased
        if ( !jTextNumeroAnimal.isEnabled() ) {
            jTextMontoAnimal.requestFocus();
        } else {
            jTextNumeroAnimal.requestFocus();
        }
    }//GEN-LAST:event_jTableSorteosMouseReleased

    private void jButtonEditarMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarMontoActionPerformed
        if ( jTableApuesta.getSelectedRowCount() > 0 ) {
            jTextNuevoMonto.setEnabled(true);
            jTextNuevoMonto.requestFocus();
        }
    }//GEN-LAST:event_jButtonEditarMontoActionPerformed

    private void jMenuItemClonarTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemClonarTicketActionPerformed
        new clonarTicket(this, true).setVisible(true);
        this.jTableApuesta.setModel(modeloTabla);
        new f_taquilla().formatjTableApuesta(this.jTableApuesta);
        taquilla.setearTotalJugada(jTableApuesta, jLabelNumeroJugadas, jLabelMontoJugadas);
    }//GEN-LAST:event_jMenuItemClonarTicketActionPerformed

    private void jMenuItemVentasDiariasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemVentasDiariasActionPerformed
        new rpt_ventas_diarias(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItemVentasDiariasActionPerformed

    private void jMenuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemSalirActionPerformed

    private void jTextNuevoMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNuevoMontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNuevoMontoActionPerformed

    private void jTextNuevoMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNuevoMontoKeyTyped
        char c = evt.getKeyChar();
        if(Character.isDigit(c)) {
         
        } else {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextNuevoMontoKeyTyped
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(taquilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new taquilla().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(taquilla.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(taquilla.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonAgregarAnimal;
    private javax.swing.JButton jButtonCancelarJugada;
    private javax.swing.JButton jButtonEditarMonto;
    private javax.swing.JButton jButtonElimarApuesta;
    private javax.swing.JButton jButtonJugar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelMontoJugadas;
    private javax.swing.JLabel jLabelNumeroJugadas;
    private javax.swing.JLabel jLabelNumeroTicket;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuConfiguracion;
    private javax.swing.JMenuItem jMenuItemAnularTicket;
    private javax.swing.JMenuItem jMenuItemClonarTicket;
    private javax.swing.JMenuItem jMenuItemPagarTicket;
    private javax.swing.JMenuItem jMenuItemResultados;
    private javax.swing.JMenuItem jMenuItemSalir;
    private javax.swing.JMenuItem jMenuItemSistema;
    private javax.swing.JMenuItem jMenuItemTickets;
    private javax.swing.JMenuItem jMenuItemVentasDiarias;
    private javax.swing.JMenuItem jMenuItemVentasGenerales;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBonotesAnimales;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    public javax.swing.JTable jTableApuesta;
    private javax.swing.JTable jTableResultados;
    private javax.swing.JTable jTableSorteos;
    private javax.swing.JTextField jTextMontoAnimal;
    private javax.swing.JTextField jTextNuevoMonto;
    private javax.swing.JTextField jTextNumeroAnimal;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton10;
    private javax.swing.JToggleButton jToggleButton11;
    private javax.swing.JToggleButton jToggleButton12;
    private javax.swing.JToggleButton jToggleButton13;
    private javax.swing.JToggleButton jToggleButton14;
    private javax.swing.JToggleButton jToggleButton15;
    private javax.swing.JToggleButton jToggleButton16;
    private javax.swing.JToggleButton jToggleButton17;
    private javax.swing.JToggleButton jToggleButton18;
    private javax.swing.JToggleButton jToggleButton19;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton20;
    private javax.swing.JToggleButton jToggleButton21;
    private javax.swing.JToggleButton jToggleButton22;
    private javax.swing.JToggleButton jToggleButton23;
    private javax.swing.JToggleButton jToggleButton24;
    private javax.swing.JToggleButton jToggleButton25;
    private javax.swing.JToggleButton jToggleButton26;
    private javax.swing.JToggleButton jToggleButton27;
    private javax.swing.JToggleButton jToggleButton28;
    private javax.swing.JToggleButton jToggleButton29;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton30;
    private javax.swing.JToggleButton jToggleButton31;
    private javax.swing.JToggleButton jToggleButton32;
    private javax.swing.JToggleButton jToggleButton33;
    private javax.swing.JToggleButton jToggleButton34;
    private javax.swing.JToggleButton jToggleButton35;
    private javax.swing.JToggleButton jToggleButton36;
    private javax.swing.JToggleButton jToggleButton37;
    private javax.swing.JToggleButton jToggleButton38;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    private javax.swing.JToggleButton jToggleButton7;
    private javax.swing.JToggleButton jToggleButton8;
    private javax.swing.JToggleButton jToggleButton9;
    // End of variables declaration//GEN-END:variables
}
