package vista.componentes.panelProcesosEnCola;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
    private JButton bAddProcesos, bBloqueados, bDesbloquear;

    private Border borderT;
    private Font fuente, fuente17;
    private ImageIcon iAux, iBloquear, iDesbloquear;

    private ArrayList<Proceso> procesos, procesosBloqueados;
    private int x, y, iterador;

    public ProcesosEnColaTemplate(ProcesosEnColaComponent procesosEnColaComponent, ArrayList<Proceso> procesos, ArrayList<Proceso> procesosBloqueados) {

        this.procesos = procesos;
        this.procesosBloqueados = procesosBloqueados;
        fuente = new Font("Comic Sans MS", Font.PLAIN, 15);
        fuente17 = new Font("Comic Sans MS", Font.PLAIN, 17);
        borderT = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);
        iBloquear = new ImageIcon("resources/img/bloquear.png");
        iDesbloquear = new ImageIcon("resources/img/desbloquear.png");

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

        bBloqueados = new JButton();
        bBloqueados.setBounds(10, 60, 40, 40);
        bBloqueados.setFocusable(false);
        bBloqueados.setContentAreaFilled(false);
        bBloqueados.addActionListener(procesosEnColaComponent);
        bBloqueados.setFont(fuente);
        bBloqueados.setForeground(Color.WHITE);
        bBloqueados.setBackground(null);
        bBloqueados.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iAux = new ImageIcon(iBloquear.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
        bBloqueados.setIcon(iAux);
        bBloqueados.setBorder(null);
        this.add(bBloqueados);

        bDesbloquear = new JButton();
        bDesbloquear.setBounds(50, 60, 40, 40);
        bDesbloquear.setFocusable(false);
        bDesbloquear.setContentAreaFilled(false);
        bDesbloquear.addActionListener(procesosEnColaComponent);
        bDesbloquear.setFont(fuente);
        bDesbloquear.setForeground(Color.WHITE);
        bDesbloquear.setBackground(null);
        bDesbloquear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iAux = new ImageIcon(iDesbloquear.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
        bDesbloquear.setIcon(iAux);
        bDesbloquear.setBorder(null);
        this.add(bDesbloquear);

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
                dibujaColaBloqueados(g2d);
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
        grph.drawRect(x-1, y-1, 106, 61);

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

    private void dibujaColaBloqueados(Graphics grph) {
        int anchura = procesosBloqueados.size();
        if (anchura == 0)
            return;
        y = 65;
        x = (anchura * 115) + 10;
        iterador = 0;
        lProcesosBloqueados.setPreferredSize(new Dimension(x, y));
        grph.setColor(new Color(50, 50, 51));
        grph.fillRect(0, 0, x, y);
        dibujaProcesoBloqueado(grph, procesosBloqueados.get(iterador), 10, 5);
    }

    private void dibujaProcesoBloqueado(Graphics grph, Proceso proceso, int x, int y) {
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
        if (!procesosBloqueados.isEmpty() && iterador < procesosBloqueados.size()) {
            dibujaProcesoBloqueado(grph, procesosBloqueados.get(iterador), x + 115, y);
        }
    }

    public void actualizar() {
        lProcesos.repaint();
        lProcesos.updateUI();
    }

    public void actualizarBloqueados() {
        lProcesosBloqueados.repaint();
        lProcesosBloqueados.updateUI();
    }

    public JButton getbBloqueados() {
        return bBloqueados;
    }

    public JButton getbDesbloquear() {
        return bDesbloquear;
    }

}