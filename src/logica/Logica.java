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

    private int tiempo;

    public Logica(){

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
            //Auxiliar para dibujar
            colaProcesosGrafica.add(c);
            System.out.println("");
        }
        if (!procesador.isAlive())
            procesador.start(); 
    }

    private char generarNombre() {
        int n = (int) Math.floor(Math.random() * (90 - 64 + 1) + 64);
        return (char) n;
    }

    private int generarRafaga() {
            int n = (int) Math.floor(Math.random() * 20 + 1);
            return n;
        
    }

    private Color generarColor() {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color c = new Color(r, g, b);
        return c;
    }

    public void actualizarColaProcesos(){
        vistaPrincipalComponent.actualizarColaProcesos();
    }

    public void anadirProcesoTabla(String[] proceso) {
        vistaPrincipalComponent.anadirProcesoTabla(proceso);
    }

    public void procesoEnEjecucion(Proceso p){
        vistaPrincipalComponent.procesoEnEjecucion(p);
    }

    public void avanceProceso(){
        vistaPrincipalComponent.actualizarDiagrama();
    }

    public ArrayList<Proceso> getColaProcesosGrafica() {
        return colaProcesosGrafica;
    }

    public Queue<Proceso> getColaProcesos() {
        return colaProcesos;
    }

}