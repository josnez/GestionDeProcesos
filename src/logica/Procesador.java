package logica;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Procesador extends Thread {

    private Logica l;
    private int quantum, tiempo;
    private Queue<Proceso> procesos;

    public Procesador(Logica log, int quantum) {
        super("Procesaminto");
        this.l = log;
        this.quantum = quantum;
        tiempo = 0;
        procesos = new LinkedList<>();
    }

    private void administrarProcesos() {

        System.out.print("");
        obtenerProcesos();
        while (!procesos.isEmpty()) {
            Proceso c = procesos.poll();
            l.getColaProcesos().poll();
            // Auxiliar para dibujar
            l.getColaProcesosGrafica().remove(0);
            l.actualizarColaProcesos();
            seccionCritica(c);
            if (c.getRafaga() > 0 && !l.estaBloqueado()) {
                c.settLlegadaAux(c.gettFinal());
                l.getColaProcesos().add(c);
                l.getColaProcesosGrafica().add(c);
                l.actualizarColaProcesos();                
            } else {
                l.setBloqueado(false);
            }
            obtenerProcesos();
        }
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
    }

    public void seccionCritica(Proceso c) {

        String[] datosTabla = new String[7];

        datosTabla[0] = c.getNombre() + "";
        datosTabla[1] = c.gettLlegada() + "";
        datosTabla[2] = c.getRafaga() + "";

        c.settComienzo(tiempo);
        datosTabla[3] = c.gettComienzo() + "";
        
        datosTabla[4] = "-";
        
        
        datosTabla[5] = "-";
        
        
        datosTabla[6] = "-";
        l.anadirProcesoTabla(datosTabla);
        int i;
        c.settFinal(tiempo);
        l.procesoEnEjecucion(c);
        if (c.getRafaga()>=4) {
            for (i = 1; i <= quantum; i++) {
                if(l.estaBloqueado()){
                    //c.settLlegadaAux(c.getTiempoLlegadaAux()+1);
                    l.getColaProcesosBloqueados().add(c);                    
                    l.actualizarColaProcesosBloqueados();
                    return;
                }
                c.settRafaga(c.getRafaga()-1);
                c.settRafagaEjecutada(c.getRafagaEjecutada()+1);
                tiempo++;
                try {
                    l.avanceProceso();
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            int rafaga = c.getRafaga();
            for (i = 1; i <= rafaga; i++) {
                if(l.estaBloqueado()){
                    //c.settLlegadaAux(c.getTiempoLlegadaAux()+1);
                    l.getColaProcesosBloqueados().add(c);
                    //l.setBloqueado(false);
                    l.actualizarColaProcesosBloqueados();
                    return;
                }
                c.settRafaga(c.getRafaga()-1);
                c.settRafagaEjecutada(c.getRafagaEjecutada()+1);
                tiempo++;
                try {
                    l.avanceProceso();
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        c.settFinal(tiempo);
        datosTabla[4] = c.gettFinal() + "";
        c.settRetorno(c.gettFinal() - c.gettLlegada());
        datosTabla[5] = c.gettRetorno() + "";
        c.settEspera(c.gettRetorno() - c.getRafagaEjecutada());
        datosTabla[6] = c.gettEspera() + "";
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