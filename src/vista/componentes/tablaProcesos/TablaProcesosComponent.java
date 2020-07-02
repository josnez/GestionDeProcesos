package vista.componentes.tablaProcesos;

public class TablaProcesosComponent {
    
    private TablaProcesosTemplate tablaProcesosTemplate;

    public TablaProcesosComponent(){

        tablaProcesosTemplate = new TablaProcesosTemplate();
    }

    public TablaProcesosTemplate gTablaProcesosTemplate(){
        return tablaProcesosTemplate;
    }

    public void anadirProceso(String[] proceso){
        tablaProcesosTemplate.anadirProceso(proceso);
    }

	public void modificarProceso(String[] datosTabla) {
        tablaProcesosTemplate.modificarProceso(datosTabla);
	}
}