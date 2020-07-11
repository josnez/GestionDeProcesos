package vista.componentes.diagramaGantt;

import java.util.LinkedList;

import logica.Proceso;

public class DiagramaProcesosComponent {
    
    private DiagramaProcesosTemplate diagramaProcesosTemplate;
    private LinkedList<Proceso> procesos;
    
    public DiagramaProcesosComponent() {
        diagramaProcesosTemplate = new DiagramaProcesosTemplate();
    }

    public DiagramaProcesosTemplate gDiagramaProcesosTemplate() {
        return diagramaProcesosTemplate;
    }

    public void addProceso(Proceso proceso){
        diagramaProcesosTemplate.addProDiagrama(proceso);
    }
    
    public void actualizar(){
        diagramaProcesosTemplate.avanceProceso();
    }

    public LinkedList<Proceso> gProcesos(){
        return procesos;
    }
}