package vista.componentes.panelProcesosEnCola;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logica.Logica;

public class ProcesosEnColaComponent implements ActionListener {

    private ProcesosEnColaTemplate procesosEnColaTemplate;
    private Logica logica;

    public ProcesosEnColaComponent(Logica logica) {

        this.logica = logica;
        procesosEnColaTemplate = new ProcesosEnColaTemplate(this, logica.getColaProcesosGrafica(), logica.getColaProcesosBloqueados());
    }

    public ProcesosEnColaTemplate gProcesosEnColaTemplate() {
        return procesosEnColaTemplate;
    }

    public void actualizar(){
        procesosEnColaTemplate.actualizar();
    }

    public void actualizarBloqueados(){
        procesosEnColaTemplate.actualizarBloqueados();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("<html><div align='center'>Agregar Procesos</div></html>")) {
            
            logica.nuevosProcesos();
            procesosEnColaTemplate.actualizar();
        } else {
            logica.setBloqueado(true);
        }
    }
}