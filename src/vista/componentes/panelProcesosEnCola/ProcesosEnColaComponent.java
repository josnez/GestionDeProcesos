package vista.componentes.panelProcesosEnCola;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import logica.Logica;

public class ProcesosEnColaComponent implements ActionListener, MouseListener {

    private ProcesosEnColaTemplate procesosEnColaTemplate;
    private Logica logica;

    private boolean pes;

    public ProcesosEnColaComponent(Logica logica) {

        this.logica = logica;
        pes = false;
        procesosEnColaTemplate = new ProcesosEnColaTemplate(
            this, logica.getColProGraRoundRobin(), logica.getColProBloqueadosRoundRobin(), logica.getColProGraMenorRafaga(),
            logica.getColProGraFIFO()
        );
    }

    public ProcesosEnColaTemplate gProcesosEnColaTemplate() {
        return procesosEnColaTemplate;
    }

    public void actualizar() {
        procesosEnColaTemplate.actualizar();
    }

    public void actualizarBloqueados() {
        procesosEnColaTemplate.actualizarBloqueados();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == procesosEnColaTemplate.getbAddProcesos()) {

            logica.nuevosProcesosRoundRobin();
            procesosEnColaTemplate.getScrollProcesosRoundRobin().repaint();
        } else if (e.getSource() == procesosEnColaTemplate.getbBloqueados()) {
            logica.setBloqueado(true);
        } else if (e.getSource() == procesosEnColaTemplate.getbDesbloquear()) {
            logica.desbloquear();
        } else if (e.getSource() == procesosEnColaTemplate.getbColaBloqueados()) {
            pes = !pes;
            procesosEnColaTemplate.cambiarVista();
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

        if (arg0.getSource() == procesosEnColaTemplate.getbAddColMenorRagafa()) {

            logica.nuevosProcesosMenorRafaga();
            procesosEnColaTemplate.getScrollColaMenorRafaga().repaint();
        } else if (arg0.getSource() == procesosEnColaTemplate.getbAddColFIFO()) {

            logica.nuevosProcesosFIFO();
            procesosEnColaTemplate.getScrollColaFIFO().repaint();
        }

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        if (arg0.getSource() == procesosEnColaTemplate.getbAddColMenorRagafa() ||
            arg0.getSource() == procesosEnColaTemplate.getbAddColFIFO()) {
            procesosEnColaTemplate.botonAnadir2((JButton) arg0.getSource());
        } else if (arg0.getSource() == procesosEnColaTemplate.getbColaBloqueados()) {
            procesosEnColaTemplate.botonPasarPestana2(pes);
        }
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        if (arg0.getSource() == procesosEnColaTemplate.getbAddColMenorRagafa() ||
            arg0.getSource() == procesosEnColaTemplate.getbAddColFIFO()) {
            procesosEnColaTemplate.botonAnadir((JButton) arg0.getSource());
        } else if (arg0.getSource() == procesosEnColaTemplate.getbColaBloqueados()) {
            procesosEnColaTemplate.botonPasarPestana(pes);
        }
    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }
}