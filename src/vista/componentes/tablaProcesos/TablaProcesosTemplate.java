package vista.componentes.tablaProcesos;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;

import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablaProcesosTemplate extends JScrollPane {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private Font fuente;
    private Border borderT;
    
    public TablaProcesosTemplate(){

        fuente = new Font("Comic Sans MS", Font.PLAIN, 17);
        borderT = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);

        tabla = new JTable();
        tabla.setBounds(0, 0, 1200, 275);
        tabla.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        tabla.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Proceso");
        modeloTabla.addColumn("TiempoLlegada");
        modeloTabla.addColumn("Rafaga");
        modeloTabla.addColumn("Tiempo Comienzo");
        modeloTabla.addColumn("Tiempo Final");
        modeloTabla.addColumn("Tiempo Retorno");
        modeloTabla.addColumn("Tiempo Espera");        

        tabla.setModel(modeloTabla);

        this.setBorder(BorderFactory.createTitledBorder(borderT, "Tabla de datos", SwingConstants.LEFT, 0, fuente, Color.WHITE));
        this.setViewportView(tabla);
        this.setSize(1200, 275);
        this.setBackground(null);
    }

    public void anadirProceso(String[] proceso){
        modeloTabla.addRow(proceso);
    }
}