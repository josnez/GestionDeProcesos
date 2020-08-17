package logica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import vista.vistaPrincipal.VistaPrincipalComponent;

import java.awt.Color;

public class Logica {

    private Queue<Proceso> colProRoundRobin, colProMenorRafaga, colProFIFO, colAux;
    private ArrayList<Proceso> colProGraRoundRobin, colProBloqueadosRoundRobin, colProGraMenorRafaga, colProGraFIFO;
    private Iterator<Proceso> iT;

    private VistaPrincipalComponent vistaPrincipalComponent;
    private Procesador procesador;

    private int tiempoInicial, tiempoFinal, tiempo, numeroN, envejecimientoMax;
    private boolean bloqueado, agregando;

    public Logica() {

        bloqueado = false;
        agregando = false;
        tiempo = 0;
        numeroN = 0;
        tiempoInicial = 0;
        tiempoFinal = 3;
        envejecimientoMax = 6;
        colProRoundRobin = new LinkedList<>();
        colProBloqueadosRoundRobin = new ArrayList<>();
        colProGraRoundRobin = new ArrayList<>();
        colProMenorRafaga = new LinkedList<>();
        colProGraMenorRafaga = new ArrayList<>();
        colProFIFO = new LinkedList<>();
        colProGraFIFO = new ArrayList<>();
        vistaPrincipalComponent = new VistaPrincipalComponent(this);
        procesador = new Procesador(this, 4);
    }

    // Cola Round Robin

    public void nuevosProcesosRoundRobin() {
        int n = (int) Math.floor(Math.random() * 5 + 1);
        for (int i = 0; i < n; i++) {
            Proceso c = new Proceso(generarNombre() + numeroN, generarColor(), tiempo, generarRafaga(),
                    generarPrioridad());
            numeroN++;
            colProRoundRobin.add(c);
            colProGraRoundRobin.add(c);
        }

        if (!procesador.isAlive()) {
            procesador.start();
        }
    }
    // ----------------------------------------------------------------

    // Cola Menor Rafaga

    public void nuevosProcesosMenorRafaga() {
        agregando = true;
        boolean seEjecuto = false;
        while (!seEjecuto) {
            if (!procesador.isEnvejeciendo()) {
                int n = (int) Math.floor(Math.random() * 5 + 1);
                ArrayList<Proceso> colaAux = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    Proceso c = new Proceso(generarNombre() + numeroN, generarColor(), tiempo, generarRafaga(),
                            generarPrioridad());
                    colaAux.add(c);
                    numeroN++;
                }

                if (!procesador.isAlive()) {
                    ordenarPorRafaga(colaAux);
                    procesador.start();
                } else {
                    ordenarPorRafaga(colaAux);
                }
                seEjecuto = true;
            }
        }
        agregando = false;
    }

    public void ordenarPorRafaga(ArrayList<Proceso> colaAux) {
        for (Proceso proceso : colProMenorRafaga) {
            colaAux.add(proceso);
        }
        Proceso temporal;
        for (int i = 0; i < colaAux.size(); i++) {
            for (int j = 1; j < (colaAux.size() - i); j++) {
                if (colaAux.get(j - 1).getRafaga() > colaAux.get(j).getRafaga()) {
                    temporal = colaAux.get(j - 1);
                    colaAux.set(j - 1, colaAux.get(j));
                    colaAux.set(j, temporal);
                }
            }
        }

        iT = colProMenorRafaga.iterator();
        while (iT.hasNext()) {
            colProMenorRafaga.poll();
        }
        colProGraMenorRafaga.clear();
        for (Proceso proceso : colaAux) {
            colProMenorRafaga.add(proceso);
            // Auxiliar para dibujar
            colProGraMenorRafaga.add(proceso);
        }

    }
    // -----------------------------------------------------------------

    // Cola FIFO

    public void nuevosProcesosFIFO() {
        agregando = true;
        boolean seEjecuto = false;
        while (!seEjecuto) {
            if (!procesador.isEnvejeciendo()) {
                int n = (int) Math.floor(Math.random() * 5 + 1);
                for (int i = 0; i < n; i++) {
                    Proceso c = new Proceso(generarNombre() + numeroN, generarColor(), tiempo, generarRafaga(),
                            generarPrioridad());
                    numeroN++;
                    colProFIFO.add(c);
                    colProGraFIFO.add(c);
                }

                if (!procesador.isAlive()) {
                    procesador.start();
                }
                seEjecuto = true;
            }
        }
        agregando = false;
    }

    // -------------------------------------------------------------------

    public void desbloquear() {
        Proceso p = colProBloqueadosRoundRobin.remove(0);

        switch (p.getMeBloquearonEn()) {
            case "RoundRobin":
                colProRoundRobin.add(p);
                colProGraRoundRobin.add(p);
                break;
            case "MenorRafaga":
                ArrayList<Proceso> colita = new ArrayList<>();
                colita.add(p);
                ordenarPorRafaga(colita);
                break;
            case "FIFO":
                colProFIFO.add(p);
                colProGraFIFO.add(p);
                break;
            default:
                System.err.println("Algo paso desbloqueando");
                break;
        }
        actualizarColaProcesos();
        actualizarColaProcesosBloqueados();
    }

    private int generarPrioridad() {
        return (int) Math.floor(Math.random() * 4 + 1);
    }

    private String generarNombre() {
        int n = (int) Math.floor(Math.random() * (90 - 64 + 1) + 64);
        return "" + (char) n;
    }

    private int generarRafaga() {
        return (int) Math.floor(Math.random() * 10 + 1);
    }

    private Color generarColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color c = new Color(r, g, b);
        return c;
    }

    public int rafagaMasCorta() {
        return colProRoundRobin.peek().getRafaga();
    }

    public void actualizarColaProcesos() {
        vistaPrincipalComponent.actualizarColaProcesos();
    }

    public void actualizarColaProcesosBloqueados() {
        vistaPrincipalComponent.actualizarColaProcesosBloqueados();
    }

    public void anadirProcesoTabla(String[] proceso) {
        vistaPrincipalComponent.anadirProcesoTabla(proceso);
    }

    public void modificarProcesoTabla(String[] datosTabla) {
        vistaPrincipalComponent.modificarTablaProceso(datosTabla);
    }

    public void procesoEnEjecucion(Proceso p, String cola) {
        vistaPrincipalComponent.procesoEnEjecucion(p, cola);
    }

    public void avanceProceso() {
        vistaPrincipalComponent.actualizarDiagrama();
    }

    public boolean estaBloqueado() {
        return bloqueado;
    }

    public boolean estaAgregando() {
        return agregando;
    }

    public void aumentaTiempo() {
        this.tiempo++;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getTiempoInicial() {
        return tiempoInicial;
    }

    public void setTiempoInicial(int tiempoInicial) {
        this.tiempoInicial = tiempoInicial;
    }

    public int getTiempoFinal() {
        return tiempoFinal;
    }

    public void setTiempoFinal(int tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }

    public void setBloqueado(boolean b) {
        this.bloqueado = b;
    }

    public Queue<Proceso> getColProRoundRobin() {
        return colProRoundRobin;
    }

    public Queue<Proceso> getColProMenorRafaga() {
        return colProMenorRafaga;
    }

    public Queue<Proceso> getColProFIFO() {
        return colProFIFO;
    }

    public ArrayList<Proceso> getColProGraRoundRobin() {
        return colProGraRoundRobin;
    }

    public ArrayList<Proceso> getColProBloqueadosRoundRobin() {
        return colProBloqueadosRoundRobin;
    }

    public ArrayList<Proceso> getColProGraMenorRafaga() {
        return colProGraMenorRafaga;
    }

    public ArrayList<Proceso> getColProGraFIFO() {
        return colProGraFIFO;
    }

	public void seVacio() {
        vistaPrincipalComponent.seVacio();
	}

}