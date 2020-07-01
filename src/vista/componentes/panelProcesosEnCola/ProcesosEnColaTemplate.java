package vista.componentes.panelProcesosEnCola;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;

import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JScrollPane;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logica.Proceso;

public class ProcesosEnColaTemplate extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JScrollPane scrollProcesosListos, scrollProcesosBloqueados;
    private JLabel lProcesos, lProcesosBloqueados;
    private JButton bAddProcesos, bBloqueados;

    private Border borderT;
    private Font fuente, fuente17;

    private ArrayList<Proceso> procesos;
    private int x, y, iterador;

    public ProcesosEnColaTemplate(ProcesosEnColaComponent procesosEnColaComponent, ArrayList<Proceso> procesos) {

        this.procesos = procesos;
        fuente = new Font("Comic Sans MS", Font.PLAIN, 15);
        fuente17 = new Font("Comic Sans MS", Font.PLAIN, 17);
        borderT = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);

        bAddProcesos = new JButton("<html><div align='center'>Agregar Procesos</div></html>");
        bAddProcesos.setBounds(10, 10, 80, 40);
        bAddProcesos.setFocusable(false);
        bAddProcesos.setContentAreaFilled(false);
        bAddProcesos.addActionListener(procesosEnColaComponent);
        bAddProcesos.setFont(fuente);
        bAddProcesos.setForeground(Color.WHITE);
        bAddProcesos.setBackground(null);
        bAddProcesos.setBorder(borderT);
        bAddProcesos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(bAddProcesos);

        bBloqueados = new JButton("<html><div align='center'>Bloquear Proceso</div></html>");
        bBloqueados.setBounds(10, 60, 80, 40);
        bBloqueados.setFocusable(false);
        bBloqueados.setContentAreaFilled(false);
        bBloqueados.addActionListener(procesosEnColaComponent);
        bBloqueados.setFont(fuente);
        bBloqueados.setForeground(Color.WHITE);
        bBloqueados.setBackground(null);
        bBloqueados.setBorder(borderT);
        bBloqueados.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(bBloqueados);

        scrollProcesosListos = new JScrollPane();
        scrollProcesosListos.setBounds(100, 0, 550, 100);
        scrollProcesosListos.setBackground(null);
        scrollProcesosListos.setBorder(BorderFactory.createTitledBorder(borderT, "Cola de listos", SwingConstants.LEFT, 0,
                fuente17, Color.WHITE));
        this.add(scrollProcesosListos);

        lProcesos = new JLabel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public void paint(Graphics grph) {
                Graphics2D g2d = (Graphics2D) grph;
                grph.setFont(fuente);
                grph.setColor(new Color(50, 50, 51));
                grph.fillRect(0, 0, 550, 80);
                dibujaCola(g2d);
            }
        };
        lProcesos.setPreferredSize(new Dimension(520, 65));
        scrollProcesosListos.setViewportView(lProcesos);

        scrollProcesosBloqueados = new JScrollPane();
        scrollProcesosBloqueados.setBounds(650, 0, 550, 100);
        scrollProcesosBloqueados.setBackground(null);
        scrollProcesosBloqueados.setBorder(BorderFactory.createTitledBorder(borderT, "Cola de bloqueados", SwingConstants.LEFT, 0,
                fuente17, Color.WHITE));
        this.add(scrollProcesosBloqueados);

        lProcesosBloqueados = new JLabel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public void paint(Graphics grph) {
                Graphics2D g2d = (Graphics2D) grph;
                grph.setFont(fuente);
                grph.setColor(new Color(50, 50, 51));
                grph.fillRect(0, 0, 550, 80);
                //dibujaCola(g2d);
            }
        };
        lProcesosBloqueados.setPreferredSize(new Dimension(520, 65));
        scrollProcesosBloqueados.setViewportView(lProcesosBloqueados);

        this.setLayout(null);
        this.setSize(1200, 100);
        this.setBackground(null);
    }

    private void dibujaCola(Graphics grph) {
        int anchura = procesos.size();
        if (anchura == 0)
            return;
        y = 65;
        x = (anchura * 115) + 10;
        iterador = 0;
        lProcesos.setPreferredSize(new Dimension(x, y));
        grph.setColor(new Color(50, 50, 51));
        grph.fillRect(0, 0, x, y);
        dibujaCliente(grph, procesos.get(iterador), 10, 5);
    }

    private void dibujaCliente(Graphics grph, Proceso proceso, int x, int y) {
        if (proceso == null)
            return;

        grph.setColor(Color.BLACK);
        grph.drawRect(x-1, y-1, 101, 61);

        grph.setColor(proceso.getColor());
        grph.fillRect(x, y, 105, 60);

        grph.setColor(Color.WHITE);
        grph.drawString("Proceso " + proceso.getNombre(), x + 10, y + 20);
        grph.drawString("Rafaga: " + proceso.getRafaga(), x + 10, y + 35);
        grph.drawString("T. llegada: " + proceso.gettLlegada(), x + 10, y + 50);
        iterador++;
        if (!procesos.isEmpty() && iterador < procesos.size()) {
            dibujaCliente(grph, procesos.get(iterador), x + 115, y);
        }
    }

    public void actualizar() {
        lProcesos.repaint();
        lProcesos.updateUI();
    }
}