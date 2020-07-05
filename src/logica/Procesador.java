package logica;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Procesador extends Thread {

    private Logica l;
    private int tiempo;
    private Queue<Proceso> procesos;

    public Procesador(Logica log) {
        super("Procesaminto");
        this.l = log;
        tiempo = 0;
        procesos = new LinkedList<>();
    }

    private synchronized void administrarProcesos() {

        System.out.print("");
        obtenerProcesos();
        while (!procesos.isEmpty()) {
            Proceso c = procesos.poll();
            l.getColaProcesos().poll();
            // Auxiliar para dibujar
            l.getColaProcesosGrafica().remove(0);
            l.actualizarColaProcesos();
            seccionCritica(c);
            obtenerProcesos();
        }
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void seccionCritica(Proceso c) {

        String[] datosTabla = new String[8];

        datosTabla[0] = c.getNombre() + "";
        datosTabla[1] = c.gettLlegada() + "";
        datosTabla[2] = c.getPrioridad() + "";
        datosTabla[3] = c.getRafaga() + "";

        c.settComienzo(tiempo);
        datosTabla[4] = c.gettComienzo() + "";
        
        datosTabla[5] = "-";
        
        
        datosTabla[6] = "-";
        
        
        datosTabla[7] = "-";
        l.anadirProcesoTabla(datosTabla);
        int tF = c.getRafaga() + tiempo, i, aux;
        c.settFinal(tiempo);
        c.settRetorno(tF - c.gettLlegada());
        aux = c.getRafaga();
        c.settEspera(c.gettRetorno() - aux);
        /* l.setTiempoFinal(tF);
        l.setTiempoInicial(tiempo); */
        l.procesoEnEjecucion(c);
        for (i = 0; i < aux; i++) {
            if(l.verificarEstadoProceso()){
                l.getColaProcesosBloqueados().add(c);
                l.setBloqueado(false);
                l.actualizarColaProcesosBloqueados();
                return;
            }
            c.settRafaga(c.getRafaga()-1);
            tiempo++;
            try {
                l.avanceProceso();
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        c.settFinal(tF);
        datosTabla[5] = c.gettFinal() + "";
        datosTabla[6] = c.gettRetorno() + "";
        datosTabla[7] = c.gettEspera() + "";
        l.modificarProcesoTabla(datosTabla);
    }

    public void obtenerProcesos(){
        Iterator<Proceso> iT = l.getColaProcesos().iterator();
        this.procesos.clear();
        while (iT.hasNext()) {
            this.procesos.add((Proceso) iT.next());
        }
    }

    public void run() {
        while (true) {
            administrarProcesos();
        }
    }
}