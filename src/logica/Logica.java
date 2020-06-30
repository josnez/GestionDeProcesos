package logica;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import vista.vistaPrincipal.VistaPrincipalComponent;

import java.awt.Color;

public class Logica {

    private Queue<Proceso> colaProcesos;
    private ArrayList<Proceso> colaProcesosGrafica;

    private VistaPrincipalComponent vistaPrincipalComponent;
    private Procesador procesador;

    private int tiempoInicial, tiempoFinal, tiempo;

    public Logica() {

        tiempo = 0;
        tiempoInicial = 0;
        tiempoFinal = 3;
        colaProcesos = new LinkedList<>();
        colaProcesosGrafica = new ArrayList<>();
        vistaPrincipalComponent = new VistaPrincipalComponent(this);
        procesador = new Procesador(this);
    }

    public void nuevosClientes() {
        int n = (int) Math.floor(Math.random() * 5 + 1);
        for (int i = 0; i < n; i++) {
            Proceso c = new Proceso(generarNombre(), generarColor(), tiempo, generarRafaga());
            tiempo++;
            colaProcesos.add(c);
            // Auxiliar para dibujar
            colaProcesosGrafica.add(c);
        }
        //ordenarCola();
        if (!procesador.isAlive())
            procesador.start();
    }

    private void ordenarCola() {
        ArrayList<Proceso> colaAux = new ArrayList<>();
        int menor;
        for (Proceso proceso : colaProcesosGrafica) {
            menor = colaProcesosGrafica.get(0).gettLlegada();

            if (proceso.gettLlegada() < menor) {
                menor = proceso.gettLlegada();
            } else {
                if (proceso.gettLlegada() > menor) {
                    menor = menor;
                }
            }
        }

        for (Proceso proceso : colaProcesos) {
            colaProcesos.poll();
        }

        colaProcesosGrafica.clear();
        for (Proceso proceso : colaAux) {
            colaProcesos.add(proceso);
            colaProcesosGrafica.add(proceso);
        }
        
    }

    private int generarTiempo() {
        return (int) Math.floor(Math.random() * (tiempoFinal - tiempoInicial + 1) + tiempoFinal);
    }

    private char generarNombre() {
        int n = (int) Math.floor(Math.random() * (90 - 64 + 1) + 64);
        return (char) n;
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

    public void actualizarColaProcesos() {
        vistaPrincipalComponent.actualizarColaProcesos();
    }

    public void anadirProcesoTabla(String[] proceso) {
        vistaPrincipalComponent.anadirProcesoTabla(proceso);
    }

    public void procesoEnEjecucion(Proceso p) {
        vistaPrincipalComponent.procesoEnEjecucion(p);
    }

    public void avanceProceso() {
        vistaPrincipalComponent.actualizarDiagrama();
    }

    public ArrayList<Proceso> getColaProcesosGrafica() {
        return colaProcesosGrafica;
    }

    public Queue<Proceso> getColaProcesos() {
        return colaProcesos;
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

}