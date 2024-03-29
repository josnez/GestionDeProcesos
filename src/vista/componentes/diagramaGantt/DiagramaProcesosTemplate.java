package vista.componentes.diagramaGantt;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;

import javax.swing.SwingConstants;
import javax.swing.border.Border;
import logica.Proceso;

import java.util.LinkedList;

public class DiagramaProcesosTemplate extends JScrollPane {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private LinkedList<Proceso> procesos;
    private JLabel lDiagrama;
    private Font fuente;
    private Border borderT;

    public DiagramaProcesosTemplate() {

        fuente = new Font("Comic Sans MS", Font.PLAIN, 17);
        borderT = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY);
        procesos = new LinkedList<>();
        lDiagrama = new JLabel() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
                dibujar(g2d);
            }
        };
        this.setBackground(null);
        this.setViewportView(lDiagrama);
        this.setBorder(BorderFactory.createTitledBorder(borderT, "Diagrama de Gantt", SwingConstants.LEFT, 0, fuente, Color.WHITE));
        this.setSize(1200, 275);
    }

    private void dibujar(Graphics g) {
        if (procesos.isEmpty())
            return;
        int x, y, i, max = 0;
        x = 48;
        y = 30;

        for (Proceso proceso : procesos) {
            if (proceso.gettFinal()>max) 
                max = proceso.gettFinal();
        }
        
        for (i = 0; i <= max; i++) {
            g.drawString(i + "", x, y);
            x += 20;
        }
        x = 40;
        y += 10;
        for (Proceso proceso : procesos) {
            dibujarProceso(g, proceso, x, y);
            y += 20; 
        }
        calcularTamano();
    }

    private void dibujarProceso(Graphics g, Proceso procesoActual, int x, int y) {
        int i;
        g.setColor(Color.BLACK);
        g.drawString(procesoActual.getNombre() + "", x, y+15);
        x += 15;

        g.setColor(Color.WHITE);
        for(i = 0; i<procesoActual.gettLlegada(); i++){
            g.drawRect(x, y, 20, 20);
            x+=20;
        }

        g.setColor(Color.LIGHT_GRAY);
        for(i = procesoActual.gettLlegada(); i<procesoActual.gettComienzo(); i++){
            g.fillRect(x, y, 20, 20);
            x+=20;
        }

        g.setColor(procesoActual.getColor());
        for(i = procesoActual.gettComienzo(); i<procesoActual.gettFinal(); i++){
            g.fillRect(x, y, 20, 20);
            x+=20;
        }
    }

    public void addProDiagrama(Proceso p) {
        procesos.add(p);
    }

    public void avanceProceso() {
        procesos.getLast().settFinal(procesos.getLast().gettFinal() + 1);
    }

    private void calcularTamano() {
        int x = 100 + (procesos.getLast().gettFinal() *20);
        int y = 80 + (procesos.size()*20);
        lDiagrama.setPreferredSize(new Dimension(x, y));
    }

    public JLabel getlDiagrama() {
        return lDiagrama;
    }

}