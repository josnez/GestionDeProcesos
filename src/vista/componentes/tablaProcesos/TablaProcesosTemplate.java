package vista.componentes.tablaProcesos;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

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
        tabla.setBounds(0, 0, 1100, 275);
        tabla.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        tabla.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        tabla.setShowGrid(false);
        tabla.setRowHeight(30);
        
        ((DefaultTableCellRenderer) tabla.getDefaultRenderer(Object.class)).setHorizontalAlignment(SwingConstants.CENTER);
        tabla.getTableHeader().setFont(fuente);
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
        this.setSize(1100, 275);
        this.setBackground(null);
    }

    public void anadirProceso(String[] proceso){
        modeloTabla.getRowCount();
        modeloTabla.addRow(proceso);
        
    }

	public void modificarProceso(String[] datosTabla) {
        modeloTabla.removeRow(modeloTabla.getRowCount()-1);
        modeloTabla.addRow(datosTabla);
	}
}