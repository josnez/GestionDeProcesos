package logica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Procesador extends Thread {

    private Logica l;
    private int tiempo, quantum, velocidad, envejecimientoMax;
    private Queue<Proceso> procesosRoundRobin, procesosMenorRafaga, procesosFIFO;
    private ArrayList<Proceso> listaAux;
    private boolean envejeciendo;

    public Procesador(Logica log, int quantum) {
        super("Procesaminto");
        this.l = log;
        this.tiempo = 0;
        this.velocidad = 1000;
        this.quantum = quantum;
        this.envejecimientoMax = 8;
        this.envejeciendo = false;
        procesosRoundRobin = new LinkedList<>();
        procesosMenorRafaga = new LinkedList<>();
        procesosFIFO = new LinkedList<>();
        listaAux = new ArrayList<>();
    }

    private void administrarProcesos() {

        // Atencion a procesos de round robin PRIORIDAD 1
        obtenerProcesos();
        while (!procesosRoundRobin.isEmpty()) {
            Proceso c = procesosRoundRobin.poll();
            l.getColProRoundRobin().poll();
            // Auxiliar para dibujar
            l.getColProGraRoundRobin().remove(0);
            l.actualizarColaProcesos();
            seccionCriticaRoundRobin(c);
            if (c.getRafaga() > 0 && !l.estaBloqueado()) {
                c.settLlegadaAux(c.gettFinal());
                l.getColProRoundRobin().add(c);
                l.getColProGraRoundRobin().add(c);
                l.actualizarColaProcesos();
            } else {
                l.setBloqueado(false);
            }
            obtenerProcesos();
        }

        // Atencion a procesos de menor rafaga PRIORIDAD 2
        while (!procesosMenorRafaga.isEmpty()) {
            if (!procesosRoundRobin.isEmpty()) {
                break;
            }
            Proceso c = procesosMenorRafaga.poll();
            l.getColProMenorRafaga().poll();
            // Auxiliar para dibujar
            l.getColProGraMenorRafaga().remove(0);
            l.actualizarColaProcesos();
            seccionCriticaMenorRafaga(c);
            obtenerProcesos();
        }

        // Atencion a procesos de FIFO PRIORIDAD 3
        while (!procesosFIFO.isEmpty()) {
            if (!procesosRoundRobin.isEmpty() || !procesosMenorRafaga.isEmpty()) {
                break;
            }
            Proceso c = procesosFIFO.poll();
            l.getColProFIFO().poll();
            // Auxiliar para dibujar
            l.getColProGraFIFO().remove(0);
            l.actualizarColaProcesos();
            seccionCriticaFIFO(c);
            obtenerProcesos();
        }

        try {
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void seccionCriticaRoundRobin(Proceso c) {

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
        if (c.getRafaga() >= 4) {
            for (i = 1; i <= quantum; i++) {
                if (l.estaBloqueado()) {
                    c.settLlegadaAux(tiempo);
                    c.setMeBloquearonEn("RoundRobin");
                    l.getColProBloqueadosRoundRobin().add(c);
                    l.actualizarColaProcesosBloqueados();
                    return;
                }
                tiempo++;
                c.settRafaga(c.getRafaga() - 1);
                c.settRafagaEjecutada(c.getRafagaEjecutada() + 1);
                try {
                    l.avanceProceso();
                    envejecimientoPrioridad1();
                    sleep(velocidad);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            int rafaga = c.getRafaga();
            for (i = 1; i <= rafaga; i++) {
                if (l.estaBloqueado()) {
                    c.settLlegadaAux(c.getTiempoLlegadaAux() + i);
                    l.getColProBloqueadosRoundRobin().add(c);
                    l.actualizarColaProcesosBloqueados();
                    return;
                }
                c.settRafaga(c.getRafaga() - 1);
                c.settRafagaEjecutada(c.getRafagaEjecutada() + 1);
                tiempo++;
                try {
                    l.avanceProceso();
                    envejecimientoPrioridad1();
                    sleep(velocidad);
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

    private void envejecimientoPrioridad1() {
        obtenerProcesos();
        envejeciendo = true;
        boolean seEjecuto = false;
        while (!seEjecuto) {
            if (!l.estaAgregando()) {
                // Envejecer procesos de cola menor rafaga
                listaAux = new ArrayList<>();
                for (Proceso proceso : procesosMenorRafaga) {
                    listaAux.add(proceso);
                }
                Proceso p;
                int i = 0, j = 0, tam = listaAux.size();
                for (j = 0; j < tam; j++) {
                    p = listaAux.get(i);
                    p.setEnvejecimiento(p.getEnvejecimiento() + 1);
                    if (p.getEnvejecimiento() == envejecimientoMax) {

                        listaAux.remove(p);
                        p.setEnvejecimiento(0);
                        procesosRoundRobin.add(p);
                        l.getColProRoundRobin().add(p);
                        l.getColProGraRoundRobin().add(p);
                    } else 
                        i++;
                }

                // Actualizar cola en logica
                Iterator<Proceso> iT = l.getColProMenorRafaga().iterator();
                while (iT.hasNext()) {
                    l.getColProMenorRafaga().poll();
                }
                l.getColProGraMenorRafaga().clear();
                for (Proceso proceso : listaAux) {
                    l.getColProMenorRafaga().add(proceso);
                    // Auxiliar para dibujar
                    l.getColProGraMenorRafaga().add(proceso);
                }
                // End Actualizar cola en logica -------------------------------------
                // End envejecer cola menor rafaga -----------------------------------

                // Envejecer procesos de cola FIFO
                listaAux = new ArrayList<>();
                for (Proceso proceso : procesosFIFO) {
                    listaAux.add(proceso);
                }
                i = 0; 
                j = 0;
                tam = listaAux.size();
                for (j = 0; j < tam; j++) {
                    p = listaAux.get(i);
                    p.setEnvejecimiento(p.getEnvejecimiento() + 1);
                    if (p.getEnvejecimiento() == envejecimientoMax) {

                        listaAux.remove(p);
                        procesosMenorRafaga.add(p);
                        p.setEnvejecimiento(0);
                        l.getColProMenorRafaga().add(p);
                        l.getColProGraMenorRafaga().add(p);
                    } else 
                        i++;
                }

                // Actualizar cola en logica
                iT = l.getColProFIFO().iterator();
                while (iT.hasNext()) {
                    l.getColProFIFO().poll();
                }
                l.getColProGraFIFO().clear();
                for (Proceso proceso : listaAux) {
                    l.getColProFIFO().add(proceso);
                    // Auxiliar para dibujar
                    l.getColProGraFIFO().add(proceso);
                }
                // End Actualizar cola en logica -------------------------------------
                // End envejecer cola FIFO -----------------------------------

                l.actualizarColaProcesos();
                seEjecuto = true;
            }
        }
        envejeciendo = false;

    }

    private void seccionCriticaMenorRafaga(Proceso c) {
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
        int i, rafaga = c.getRafaga();
        c.settFinal(tiempo);
        l.procesoEnEjecucion(c);
        for (i = 1; i <= rafaga; i++) {
            if (l.estaBloqueado()) {
                c.settLlegadaAux(tiempo);
                c.setMeBloquearonEn("MenorRafaga");
                l.getColProBloqueadosRoundRobin().add(c);
                l.actualizarColaProcesosBloqueados();
                l.setBloqueado(false);
                return;
            }
            tiempo++;
            c.settRafaga(c.getRafaga() - 1);
            c.settRafagaEjecutada(c.getRafagaEjecutada() + 1);
            try {
                l.avanceProceso();
                envejecimientoPrioridad2();
                sleep(velocidad);
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    private void envejecimientoPrioridad2() {
        obtenerProcesos();
        envejeciendo = true;
        boolean seEjecuto = false;
        while (!seEjecuto) {
            if (!l.estaAgregando()) {                
                // Envejecer procesos de cola FIFO
                listaAux = new ArrayList<>();
                for (Proceso proceso : procesosFIFO) {
                    listaAux.add(proceso);
                }
                Proceso p;
                int i = 0, j = 0, tam = listaAux.size();
                for (j = 0; j < tam; j++) {
                    p = listaAux.get(i);
                    p.setEnvejecimiento(p.getEnvejecimiento() + 1);
                    if (p.getEnvejecimiento() == envejecimientoMax) {

                        listaAux.remove(p);
                        procesosMenorRafaga.add(p);
                        p.setEnvejecimiento(0);
                        l.getColProMenorRafaga().add(p);
                        l.getColProGraMenorRafaga().add(p);
                    } else 
                        i++;
                }

                // Actualizar cola en logica
                Iterator<Proceso> iT = l.getColProFIFO().iterator();
                while (iT.hasNext()) {
                    l.getColProFIFO().poll();
                }
                l.getColProGraFIFO().clear();
                for (Proceso proceso : listaAux) {
                    l.getColProFIFO().add(proceso);
                    // Auxiliar para dibujar
                    l.getColProGraFIFO().add(proceso);
                }
                // End Actualizar cola en logica -------------------------------------
                // End envejecer cola FIFO -----------------------------------

                l.actualizarColaProcesos();
                seEjecuto = true;
            }
        }
        envejeciendo = false;
    }

    private void seccionCriticaFIFO(Proceso c) {
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
        int i, rafaga = c.getRafaga();
        c.settFinal(tiempo);
        l.procesoEnEjecucion(c);
        for (i = 1; i <= rafaga; i++) {
            if (l.estaBloqueado()) {
                c.settLlegadaAux(tiempo);
                c.setMeBloquearonEn("FIFO");
                l.getColProBloqueadosRoundRobin().add(c);
                l.actualizarColaProcesosBloqueados();
                l.setBloqueado(false);
                return;
            }
            tiempo++;
            c.settRafaga(c.getRafaga() - 1);
            c.settRafagaEjecutada(c.getRafagaEjecutada() + 1);
            try {
                l.avanceProceso();
                sleep(velocidad);
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    public void obtenerProcesos() {
        Iterator<Proceso> iT = l.getColProRoundRobin().iterator();
        this.procesosRoundRobin.clear();
        while (iT.hasNext()) {
            this.procesosRoundRobin.add((Proceso) iT.next());
        }

        iT = l.getColProMenorRafaga().iterator();
        this.procesosMenorRafaga.clear();
        while (iT.hasNext()) {
            this.procesosMenorRafaga.add((Proceso) iT.next());
        }

        iT = l.getColProFIFO().iterator();
        this.procesosFIFO.clear();
        while (iT.hasNext()) {
            this.procesosFIFO.add((Proceso) iT.next());
        }
    }

    public void run() {
        while (true) {
            administrarProcesos();
        }
    }

    public boolean isEnvejeciendo() {
        return envejeciendo;
    }

    public void setEnvejeciendo(boolean envejeciendo) {
        this.envejeciendo = envejeciendo;
    }

}