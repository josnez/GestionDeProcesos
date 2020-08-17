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
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logica.Proceso;

public class ProcesosEnColaTemplate extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JScrollPane scrollProcesosRoundRobin, scrollProcesosBloqueados, scrollColaFIFO, scrollColaMenorRafaga;
    private JLabel lProcesosRoundRobin, lProcesosBloqueados, lProcesosFIFO, lProcesosMenorRafaga;
    private JButton bAddProcesos, bBloqueados, bDesbloquear, bAddColMenorRagafa, bAddColFIFO, bColaBloqueados;

    private Border borderT;
    private Font fuente, fuente17;
    private ImageIcon iAux, iBloquear, iDesbloquear, iAnadir, iCambio, iCambio2, iAnadir2, iCambioV, iCambioV2;
    private Color colorAzul;
    private Cursor cMano;

    private ArrayList<Proceso> procesosRoundRobin, procesosBloqueados, procesosFIFO, procesosMenorRafaga;
    private Iterator<Proceso> iT;
    private int x, y, iterador;

    private ProcesosEnColaComponent procesosEnColaComponent;

    public ProcesosEnColaTemplate(
        ProcesosEnColaComponent procesosEnColaComponent, ArrayList<Proceso> procesosRoundRobin, 
        ArrayList<Proceso> procesosBloqueados, ArrayList<Proceso> procesosMenorRafaga, ArrayList<Proceso> procesosFIFO) {

        this.procesosRoundRobin = procesosRoundRobin;
        this.procesosBloqueados = procesosBloqueados;
        this.procesosMenorRafaga = procesosMenorRafaga;
        this.procesosFIFO = procesosFIFO;
        this.procesosEnColaComponent = procesosEnColaComponent;

        crearObjetosDecoradores();
        crearBotones();
        crearRoundRobin();
        crearColaBloqueados();
        crearFIFO();
        crearMenorRafaga();

        this.setLayout(null);
        this.setSize(1350, 100);
        this.setBackground(null);
    }

    private void crearColaBloqueados() {
        scrollProcesosBloqueados = new JScrollPane();
        scrollProcesosBloqueados.setBounds(100, 0, 400, 100);
        scrollProcesosBloqueados.setBackground(null);
        scrollProcesosBloqueados.setBorder(BorderFactory.createTitledBorder(borderT, "Cola de bloqueados",
                SwingConstants.LEFT, 0, fuente17, Color.WHITE));
        this.add(scrollProcesosBloqueados);

        lProcesosBloqueados = new JLabel() {

            private static final long serialVersionUID = 1L;

            public void paint(Graphics grph) {
                Graphics2D g2d = (Graphics2D) grph;
                grph.setFont(fuente);
                grph.setColor(new Color(50, 50, 51));
                grph.fillRect(0, 0, 440, 80);
                dibujaColaBloqueados(g2d);
            }
        };
        lProcesosBloqueados.setPreferredSize(new Dimension(380, 65));
        scrollProcesosBloqueados.setViewportView(lProcesosBloqueados);
        scrollProcesosBloqueados.setVisible(false);
    }

    private void dibujaColaBloqueados(Graphics grph) {
        int anchura = procesosBloqueados.size();
        if (anchura == 0)
            return;
        y = 55;
        x = (anchura * 115) + 10;
        iterador = 0;
        lProcesosBloqueados.setPreferredSize(new Dimension(x, y));
        grph.setColor(new Color(50, 50, 51));
        grph.fillRect(0, 0, x, y);
        x = 10;
        y = 3;
        iT = procesosBloqueados.iterator();
        Proceso proceso;

        while (iT.hasNext()) {
            if (iterador == procesosBloqueados.size())
                break;

            proceso = procesosBloqueados.get(iterador);

            grph.setColor(colorAzul);
            grph.fillRect(x - 2, y - 2, 109, 59);

            grph.setColor(proceso.getColor());
            grph.fillRect(x, y, 105, 55);

            grph.setColor(Color.WHITE);
            grph.drawString("Proceso " + proceso.getNombre(), x + 10, y + 15);
            grph.drawString("Rafaga: " + proceso.getRafaga(), x + 10, y + 30);
            grph.drawString("T. llegada: " + proceso.gettLlegada(), x + 10, y + 45);
            iterador++;
            x += 115;
        }
        scrollProcesosBloqueados.doLayout();
    }

    private void crearFIFO() {
        scrollColaFIFO = new JScrollPane();
        scrollColaFIFO.setBounds(948, 0, 400, 100);
        scrollColaFIFO.setBackground(null);
        scrollColaFIFO.setBorder(
                BorderFactory.createTitledBorder(borderT, "Cola FIFO", SwingConstants.LEFT, 0, fuente17, Color.WHITE));
        this.add(scrollColaFIFO);

        lProcesosFIFO = new JLabel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public void paint(Graphics grph) {
                Graphics2D g2d = (Graphics2D) grph;
                grph.setFont(fuente);
                grph.setColor(new Color(50, 50, 51));
                grph.fillRect(0, 0, 440, 80);
                dibujaColaFIFO(g2d);
            }
        };
        lProcesosFIFO.setPreferredSize(new Dimension(380, 65));
        scrollColaFIFO.setViewportView(lProcesosFIFO);
    }

    protected void dibujaColaFIFO(Graphics2D g2d) {
        int anchura = procesosFIFO.size();
        if (anchura == 0)
            return;
        y = 55;
        x = (anchura * 115) + 10;
        iterador = 0;
        lProcesosFIFO.setPreferredSize(new Dimension(x, y));
        g2d.setColor(new Color(50, 50, 51));
        g2d.fillRect(0, 0, x, y);
        x = 10;
        y = 3;
        iT = procesosFIFO.iterator();
        Proceso proceso;

        while (iT.hasNext()) {
            if (iterador == procesosFIFO.size())
                break;

            proceso = procesosFIFO.get(iterador);

            g2d.setColor(colorAzul);
            g2d.fillRect(x - 2, y - 2, 109, 59);

            g2d.setColor(proceso.getColor());
            g2d.fillRect(x, y, 105, 55);

            g2d.setColor(Color.WHITE);
            g2d.drawString("Proceso " + proceso.getNombre(), x + 10, y + 15);
            g2d.drawString("Rafaga: " + proceso.getRafaga(), x + 10, y + 30);
            g2d.drawString("Enveje: " + proceso.getEnvejecimiento(), x + 10, y + 45);
            iterador++;
            x += 115;
        }
        scrollColaFIFO.doLayout();
    }

    private void crearMenorRafaga() {
        scrollColaMenorRafaga = new JScrollPane();
        scrollColaMenorRafaga.setBounds(524, 0, 400, 100);
        scrollColaMenorRafaga.setBackground(null);
        scrollColaMenorRafaga.setBorder(BorderFactory.createTitledBorder(borderT, "Cola Menor Rafaga",
                SwingConstants.LEFT, 0, fuente17, Color.WHITE));
        this.add(scrollColaMenorRafaga);

        lProcesosMenorRafaga = new JLabel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public void paint(Graphics grph) {
                Graphics2D g2d = (Graphics2D) grph;
                grph.setFont(fuente);
                grph.setColor(new Color(50, 50, 51));
                grph.fillRect(0, 0, 440, 80);
                dibujaColaMenorRafaga(g2d);
            }
        };
        lProcesosMenorRafaga.setPreferredSize(new Dimension(380, 65));
        scrollColaMenorRafaga.setViewportView(lProcesosMenorRafaga);
    }

    protected void dibujaColaMenorRafaga(Graphics2D g2d) {
        int anchura = procesosMenorRafaga.size();
        if (anchura == 0)
            return;
        y = 55;
        x = (anchura * 115) + 10;
        iterador = 0;
        lProcesosMenorRafaga.setPreferredSize(new Dimension(x, y));
        g2d.setColor(new Color(50, 50, 51));
        g2d.fillRect(0, 0, x, y);
        x = 10;
        y = 3;
        iT = procesosMenorRafaga.iterator();
        Proceso proceso;
        while (iT.hasNext()) {
            if (iterador == procesosMenorRafaga.size())
                break;

            proceso = procesosMenorRafaga.get(iterador);

            g2d.setColor(colorAzul);
            g2d.fillRect(x - 2, y - 2, 109, 59);

            g2d.setColor(proceso.getColor());
            g2d.fillRect(x, y, 105, 55);

            g2d.setColor(Color.WHITE);
            g2d.drawString("Proceso " + proceso.getNombre(), x + 10, y + 15);
            g2d.drawString("Rafaga: " + proceso.getRafaga(), x + 10, y + 30);
            g2d.drawString("Enve: " + proceso.getEnvejecimiento(), x + 10, y + 45);
            iterador++;
            x += 115;
        }
        scrollColaMenorRafaga.doLayout();
    }

    private void crearRoundRobin() {
        scrollProcesosRoundRobin = new JScrollPane();
        scrollProcesosRoundRobin.setBounds(100, 0, 400, 100);
        scrollProcesosRoundRobin.setBackground(null);
        scrollProcesosRoundRobin.setBorder(BorderFactory.createTitledBorder(borderT, "Cola Round Robin",
                SwingConstants.LEFT, 0, fuente17, Color.WHITE));
        this.add(scrollProcesosRoundRobin);

        lProcesosRoundRobin = new JLabel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public void paint(Graphics grph) {
                Graphics2D g2d = (Graphics2D) grph;
                grph.setFont(fuente);
                grph.setColor(new Color(50, 50, 51));
                grph.fillRect(0, 0, 440, 80);
                dibujaColaRoundRobin(g2d);
            }
        };
        lProcesosRoundRobin.setPreferredSize(new Dimension(380, 65));
        scrollProcesosRoundRobin.setViewportView(lProcesosRoundRobin);

    }

    private void dibujaColaRoundRobin(Graphics grph) {

        int anchura = procesosRoundRobin.size();
        if (anchura == 0)
            return;
        y = 55;
        x = (anchura * 115) + 10;
        iterador = 0;
        lProcesosRoundRobin.setPreferredSize(new Dimension(x, y));
        grph.setColor(new Color(50, 50, 51));
        grph.fillRect(0, 0, x, y);
        x = 10;
        y = 3;
        iT = procesosRoundRobin.iterator();
        Proceso proceso;
        while (iT.hasNext()) {
            if (iterador == procesosRoundRobin.size())
                break;

            proceso = procesosRoundRobin.get(iterador);

            grph.setColor(colorAzul);
            grph.fillRect(x - 2, y - 2, 109, 59);

            grph.setColor(proceso.getColor());
            grph.fillRect(x, y, 105, 55);

            grph.setColor(Color.WHITE);
            grph.drawString("Proceso " + proceso.getNombre(), x + 10, y + 15);
            grph.drawString("Rafaga: " + proceso.getRafaga(), x + 10, y + 30);
            grph.drawString("T. llegada: " + proceso.gettLlegada(), x + 10, y + 45);
            iterador++;
            x += 115;
        }
        scrollProcesosRoundRobin.doLayout();
    }

    private void crearObjetosDecoradores() {
        fuente = new Font("Comic Sans MS", Font.PLAIN, 15);
        fuente17 = new Font("Comic Sans MS", Font.PLAIN, 17);
        borderT = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);
        iBloquear = new ImageIcon("resources/img/bloquear.png");
        iDesbloquear = new ImageIcon("resources/img/desbloquear.png");
        iAnadir = new ImageIcon("resources/img/anadir.png");
        iAnadir2 = new ImageIcon("resources/img/anadir1.png");
        iCambio = new ImageIcon("resources/img/cambiarPestana.png");
        iCambio2 = new ImageIcon("resources/img/cambiarPestana1.png");
        iCambioV = new ImageIcon("resources/img/cambiarPestanaV.png");
        iCambioV2 = new ImageIcon("resources/img/cambiarPestanaV1.png");
        colorAzul = new Color(25, 93, 150);
        cMano = new Cursor(Cursor.HAND_CURSOR);
    }

    private void crearBotones() {

        bAddProcesos = new JButton("<html><div align='center'>Agregar Procesos</div></html>");
        bAddProcesos.setBounds(10, 10, 80, 40);
        bAddProcesos.setFocusable(false);
        bAddProcesos.setContentAreaFilled(false);
        bAddProcesos.addActionListener(procesosEnColaComponent);
        bAddProcesos.setFont(fuente);
        bAddProcesos.setForeground(Color.WHITE);
        bAddProcesos.setBackground(null);
        bAddProcesos.setBorder(borderT);
        bAddProcesos.setCursor(cMano);
        this.add(bAddProcesos);

        bBloqueados = new JButton();
        bBloqueados.setBounds(10, 60, 40, 40);
        bBloqueados.setFocusable(false);
        bBloqueados.setContentAreaFilled(false);
        bBloqueados.addActionListener(procesosEnColaComponent);
        bBloqueados.setFont(fuente);
        bBloqueados.setForeground(Color.WHITE);
        bBloqueados.setBackground(null);
        bBloqueados.setCursor(cMano);
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
        bDesbloquear.setCursor(cMano);
        iAux = new ImageIcon(iDesbloquear.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
        bDesbloquear.setIcon(iAux);
        bDesbloquear.setBorder(null);
        this.add(bDesbloquear);

        bAddColMenorRagafa = new JButton();
        bAddColMenorRagafa.setBounds(502, 40, 20, 20);
        bAddColMenorRagafa.setContentAreaFilled(false);
        iAux = new ImageIcon(iAnadir.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
        bAddColMenorRagafa.setIcon(iAux);
        bAddColMenorRagafa.setFocusable(false);
        bAddColMenorRagafa.setBorder(null);
        bAddColMenorRagafa.addMouseListener(procesosEnColaComponent);
        bAddColMenorRagafa.setCursor(cMano);
        this.add(bAddColMenorRagafa);

        bColaBloqueados = new JButton();
        bColaBloqueados.setBounds(502, 12, 20, 20);
        bColaBloqueados.addActionListener(procesosEnColaComponent);
        bColaBloqueados.addMouseListener(procesosEnColaComponent);
        bColaBloqueados.setBackground(null);
        bColaBloqueados.setCursor(cMano);
        iAux = new ImageIcon(iCambio.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
        bColaBloqueados.setIcon(iAux);
        bColaBloqueados.setBorder(null);
        bColaBloqueados.setContentAreaFilled(false);
        this.add(bColaBloqueados);

        bAddColFIFO = new JButton();
        bAddColFIFO.setBounds(926, 40, 20, 20);
        bAddColFIFO.setContentAreaFilled(false);
        iAux = new ImageIcon(iAnadir.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
        bAddColFIFO.setIcon(iAux);
        bAddColFIFO.setFocusable(false);
        bAddColFIFO.setBorder(null);
        bAddColFIFO.addMouseListener(procesosEnColaComponent);
        bAddColFIFO.setCursor(cMano);
        this.add(bAddColFIFO);
    }

    public void cambiarVista() {
        if (scrollProcesosRoundRobin.isVisible()) {

            scrollProcesosRoundRobin.setVisible(false);
            scrollProcesosBloqueados.setVisible(true);
            iAux = new ImageIcon(iCambio2.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
            bColaBloqueados.setIcon(iAux);
            scrollProcesosBloqueados.repaint();
        } else {

            scrollProcesosBloqueados.setVisible(false);
            scrollProcesosRoundRobin.setVisible(true);
            iAux = new ImageIcon(iCambio.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
            bColaBloqueados.setIcon(iAux);
            scrollProcesosRoundRobin.repaint();
        }
    }

    public void actualizar() {
        scrollProcesosRoundRobin.repaint();
        scrollColaMenorRafaga.repaint();
        scrollColaFIFO.repaint();
    }

    public void actualizarBloqueados() {
        lProcesosBloqueados.repaint();
        lProcesosBloqueados.updateUI();
    }

    public JButton getbBloqueados() {
        return bBloqueados;
    }

    public JButton getbAddProcesos() {
        return bAddProcesos;
    }

    public JButton getbDesbloquear() {
        return bDesbloquear;
    }

    public JButton getbColaBloqueados() {
        return bColaBloqueados;
    }

    public JScrollPane getScrollProcesosRoundRobin() {
        return scrollProcesosRoundRobin;
    }

    public JScrollPane getScrollProcesosBloqueados() {
        return scrollProcesosBloqueados;
    }

    public JScrollPane getScrollColaFIFO() {
        return scrollColaFIFO;
    }

    public JScrollPane getScrollColaMenorRafaga() {
        return scrollColaMenorRafaga;
    }

    public JLabel getlProcesosRoundRobin() {
        return lProcesosRoundRobin;
    }

    public JButton getbAddColMenorRagafa() {
        return bAddColMenorRagafa;
    }

    public JButton getbAddColFIFO() {
        return bAddColFIFO;
    }

    public void botonAnadir2(JButton b) {
        iAux = new ImageIcon(iAnadir2.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
        b.setIcon(iAux);
    }

    public void botonAnadir(JButton b) {
        iAux = new ImageIcon(iAnadir.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
        b.setIcon(iAux);
    }

    public void botonPasarPestana(boolean pes) {
        if (pes){
            
            iAux = new ImageIcon(iCambio2.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
            bColaBloqueados.setIcon(iAux);
        } else {
            
            iAux = new ImageIcon(iCambio.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
            bColaBloqueados.setIcon(iAux);
        }
    }

    public void botonPasarPestana2(boolean pes) {
        if (pes){
            
            iAux = new ImageIcon(iCambioV2.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
            bColaBloqueados.setIcon(iAux);
        } else {    
            
            iAux = new ImageIcon(iCambioV.getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
            bColaBloqueados.setIcon(iAux);
        }
    }

}