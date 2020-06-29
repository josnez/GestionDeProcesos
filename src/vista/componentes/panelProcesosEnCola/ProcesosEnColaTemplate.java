package vista.componentes.panelProcesosEnCola;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import logica.Proceso;

public class ProcesosEnColaTemplate extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JScrollPane scrollProcesos;
    private JLabel lProcesos;
    private JButton bAddClientes;

    private Font fuente;

    private ArrayList<Proceso> procesos;
    private int x, y, iterador;

    public ProcesosEnColaTemplate(ProcesosEnColaComponent procesosEnColaComponent, ArrayList<Proceso> procesos) {

        this.procesos = procesos;
        fuente = new Font("Comic Sans MS", Font.PLAIN, 15);

        bAddClientes = new JButton("<html><div align='center'>Agregar Procesos</div></html>");
        bAddClientes.setBounds(10, 10, 80, 80);
        bAddClientes.setFocusable(false);
        bAddClientes.setContentAreaFilled(false);
        bAddClientes.addActionListener(procesosEnColaComponent);
        bAddClientes.setFont(fuente);
        bAddClientes.setForeground(Color.WHITE);
        bAddClientes.setBackground(null);
        bAddClientes.setBorder(null);
        bAddClientes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(bAddClientes);

        scrollProcesos = new JScrollPane();
        scrollProcesos.setBounds(100, 0, 1100, 100);
        scrollProcesos.setBackground(null);
        this.add(scrollProcesos);
        
        lProcesos = new JLabel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public void paint(Graphics grph) {
                grph.setFont(fuente);
                dibujaCola(grph);
            }
        };
        lProcesos.setPreferredSize(new Dimension(1080, 80));
        scrollProcesos.setViewportView(lProcesos);

        this.setLayout(null);
        this.setSize(1200, 100);
        this.setBackground(null);
    }

    private void dibujaCola(Graphics grph) {
        int anchura = procesos.size();
        if (anchura == 0)
            return;
        grph.setColor(Color.WHITE);
        grph.clearRect(0, 0, x, y);
        y = 80;
        x = (anchura * 110)+10;
        iterador = 0;
        grph.clearRect(0, 0, x, y);
        lProcesos.setPreferredSize(new Dimension(x, y));
        dibujaCliente(grph, procesos.get(iterador), 10, 15);
    }

    private void dibujaCliente(Graphics grph, Proceso proceso, int x, int y) {
        if (proceso == null)
            return;

        grph.setColor(proceso.getColor());
        grph.fillRect(x, y, 100, 50);

        grph.setColor(Color.WHITE);
        grph.drawString("Proceso " + proceso.getNombre(), x + 10, y + 20);
        grph.drawString("T llegada: " + proceso.gettLlegada(), x + 10, y + 35);
        iterador++;
        if (!procesos.isEmpty() && iterador < procesos.size()) {
            dibujaCliente(grph, procesos.get(iterador), x + 110, y);
        }
    }

	public void actualizar() {
        lProcesos.repaint();
        lProcesos.updateUI();
	}
}