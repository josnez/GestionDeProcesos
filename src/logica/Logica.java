package logica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import vista.vistaPrincipal.VistaPrincipalComponent;

import java.awt.Color;

public class Logica {

    private Queue<Proceso> colaProcesos;
    private ArrayList<Proceso> colaProcesosGrafica, colaProcesosBloqueados;
    private Iterator<Proceso> iT;

    private VistaPrincipalComponent vistaPrincipalComponent;
    private Procesador procesador;

    private int tiempoInicial, tiempoFinal, tiempo, numeroN;
    private boolean bloqueado;

    public Logica() {

        bloqueado = false;
        tiempo = 0;
        numeroN = 0;
        tiempoInicial = 0;
        tiempoFinal = 3;
        colaProcesos = new LinkedList<>();
        colaProcesosBloqueados = new ArrayList<>();
        colaProcesosGrafica = new ArrayList<>();
        vistaPrincipalComponent = new VistaPrincipalComponent(this);
        procesador = new Procesador(this, 4);
    }

    public void nuevosProcesos() {
        int n = (int) Math.floor(Math.random() * 5 + 1);
        ArrayList<Proceso> colaAux = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Proceso c = new Proceso(generarNombre()+numeroN, generarColor(), tiempo, generarRafaga(), generarPrioridad());
            System.out.println(c.getRafaga());
            numeroN++;
            colaAux.add(c);
        }
        tiempo++;

        if (!procesador.isAlive()){
            ordenarPorRafaga(colaAux);
            procesador.start();
        }
        else {
            ordenarPorRafaga(colaAux);
        }
    }

    public void ordenarPorRafaga(ArrayList<Proceso> colaAux) {
        for (Proceso proceso : colaProcesos) {
            colaAux.add(proceso);
        }
        Proceso temporal;
        for (int i = 0; i < colaAux.size(); i++) {
            for (int j = 1; j < (colaAux.size() - i); j++) {
                if (colaAux.get(j-1).getRafaga() > colaAux.get(j).getRafaga()) {
                    temporal = colaAux.get(j-1);
                    colaAux.set(j-1, colaAux.get(j));
                    colaAux.set(j, temporal);
                }
            }
        }
        
        iT = colaProcesos.iterator();
        while (iT.hasNext()) {
            colaProcesos.poll();
        }
        System.out.println("Ordenado");
        colaProcesosGrafica.clear();
        for (Proceso proceso : colaAux) {
            System.out.println(proceso.getRafaga());
            colaProcesos.add(proceso);
            // Auxiliar para dibujar
            colaProcesosGrafica.add(proceso);
        }
        
    }

    private void ordenarPorPrioridad(ArrayList<Proceso> colaAux) {
        for (Proceso proceso : colaProcesos) {
            colaAux.add(proceso);
        }
        Proceso temporal;
        for (int i = 0; i < colaAux.size(); i++) {
            for (int j = 1; j < (colaAux.size() - i); j++) {
                if (colaAux.get(j-1).getPrioridad() > colaAux.get(j).getPrioridad()) {
                    temporal = colaAux.get(j-1);
                    colaAux.set(j-1, colaAux.get(j));
                    colaAux.set(j, temporal);
                }
            }
        }
        
        iT = colaProcesos.iterator();
        while (iT.hasNext()) {
            colaProcesos.poll();
        }
        System.out.println("Ordenado");
        colaProcesosGrafica.clear();
        for (Proceso proceso : colaAux) {
            System.out.println(proceso.getRafaga());
            colaProcesos.add(proceso);
            // Auxiliar para dibujar
            colaProcesosGrafica.add(proceso);
        }
        
    }

    public void desbloquear() {
        ArrayList<Proceso> colita = new ArrayList<>();
        colita.add(colaProcesosBloqueados.remove(0));
        ordenarPorRafaga(colita);
        actualizarColaProcesos();
        actualizarColaProcesosBloqueados();
    }

    private int generarPrioridad() {
        return (int) Math.floor(Math.random() * 4 + 1);
    }

    private String generarNombre() {
        int n = (int) Math.floor(Math.random() * (90 - 64 + 1) + 64);
        return ""+(char) n;
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

    public int rafagaMasCorta(){
        return colaProcesos.peek().getRafaga();
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

    public void procesoEnEjecucion(Proceso p) {
        vistaPrincipalComponent.procesoEnEjecucion(p);
    }

    public void avanceProceso() {
        vistaPrincipalComponent.actualizarDiagrama();
    }

    public boolean estaBloqueado(){
        return bloqueado;
    }

    public ArrayList<Proceso> getColaProcesosGrafica() {
        return colaProcesosGrafica;
    }

    public Queue<Proceso> getColaProcesos() {
        return colaProcesos;
    }

    public ArrayList<Proceso> getColaProcesosBloqueados() {
        return colaProcesosBloqueados;
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

}