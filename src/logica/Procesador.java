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
            obtenerProcesos();
        }
    }

    public void seccionCritica(Proceso c) {

        c.settComienzo(tiempo);
        l.setTiempoInicial(tiempo);
        c.settFinal(c.getRafaga() + tiempo);
        l.setTiempoFinal(c.gettFinal());
        c.settRetorno(c.gettFinal() - c.gettLlegada());
        c.settEspera(c.gettRetorno() - c.getRafaga());
        tiempo = tiempo + c.getRafaga();

        String[] datosTabla = new String[7];
        datosTabla[0] = c.getNombre() + "";
        datosTabla[1] = c.gettLlegada() + "";
        datosTabla[2] = c.getRafaga() + "";
        datosTabla[3] = c.gettComienzo() + "";
        datosTabla[4] = c.gettFinal() + "";
        datosTabla[5] = c.gettRetorno() + "";
        datosTabla[6] = c.gettEspera() + "";
        l.anadirProcesoTabla(datosTabla);

        int tF = c.gettFinal(),i;
        c.settFinal(tF - c.getRafaga());
        l.procesoEnEjecucion(c);
        for (i = 0; i < c.getRafaga(); i++) {
            try {
                l.avanceProceso();
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void obtenerProcesos(){
        Iterator iT = l.getColaProcesos().iterator();
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