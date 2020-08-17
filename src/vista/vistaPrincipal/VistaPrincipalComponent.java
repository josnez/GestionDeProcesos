package vista.vistaPrincipal;

import logica.Logica;
import logica.Proceso;
import vista.componentes.barraHerramientas.BarraHerramientasComponent;
import vista.componentes.diagramaGantt.DiagramaProcesosComponent;
import vista.componentes.panelProcesosEnCola.ProcesosEnColaComponent;
import vista.componentes.tablaProcesos.TablaProcesosComponent;
import vista.seccionCritica.SeccionCriticaComponent;

public class VistaPrincipalComponent {
    
    private VistaPrincipalTemplate vistaPrincipalTemplate;
    private BarraHerramientasComponent barraHerramientasComponent;
    private ProcesosEnColaComponent procesosEnColaComponent;
    private TablaProcesosComponent tablaProcesosComponent;
    private DiagramaProcesosComponent diagramaProcesosComponent;
    private SeccionCriticaComponent seccionCriticaComponent;

    public VistaPrincipalComponent(Logica logica){

        vistaPrincipalTemplate = new VistaPrincipalTemplate();
        barraHerramientasComponent = new BarraHerramientasComponent(this);
        vistaPrincipalTemplate.getpBarraHerramientas().add(barraHerramientasComponent.gBarraHerramientasTemplate());
        procesosEnColaComponent = new ProcesosEnColaComponent(logica);
        vistaPrincipalTemplate.getpColaProcesos().add(procesosEnColaComponent.gProcesosEnColaTemplate());
        tablaProcesosComponent = new TablaProcesosComponent();
        vistaPrincipalTemplate.getpTabla().add(tablaProcesosComponent.gTablaProcesosTemplate());
        diagramaProcesosComponent = new DiagramaProcesosComponent();
        vistaPrincipalTemplate.getpDiagrama().add(diagramaProcesosComponent.gDiagramaProcesosTemplate());
        seccionCriticaComponent = new SeccionCriticaComponent();
        vistaPrincipalTemplate.getpSeccionCritica().add(seccionCriticaComponent.gSeccionCriticaTemplate());
        vistaPrincipalTemplate.repaint();
    }

    public void actualizarColaProcesos(){
        procesosEnColaComponent.actualizar();
    }

    public void actualizarColaProcesosBloqueados(){
        procesosEnColaComponent.actualizarBloqueados();
    }

    public void actualizarDiagrama(){
        diagramaProcesosComponent.actualizar();
        seccionCriticaComponent.avanzaProceso();
    }

    public void anadirProcesoTabla(String[] proceso) {
        tablaProcesosComponent.anadirProceso(proceso);
    }

    public void modificarTablaProceso(String[] datosTabla) {
        tablaProcesosComponent.modificarProceso(datosTabla);
	}

    public void procesoEnEjecucion(Proceso p, String cola){
        diagramaProcesosComponent.addProceso(p);
        seccionCriticaComponent.entraProceso(p, cola);
    }


    public VistaPrincipalTemplate gVistaPrincipalTemplate(){
        return vistaPrincipalTemplate;
    }

	public void moverFrame(int i, int j) {
        vistaPrincipalTemplate.setLocation(i, j);
	}

	public void seVacio() {
        seccionCriticaComponent.seVacio();
	}
}