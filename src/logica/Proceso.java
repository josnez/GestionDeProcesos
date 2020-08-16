package logica;

import java.awt.Color;

public class Proceso implements Cloneable {
    
    private String nombre;
    private int tLlegada, tLlegadaAux, rafaga, rafagaEjecutada, tComienzo, tFinal, tRetorno, tEspera, prioridad, envejecimiento;
    private Color color;
    
    public Proceso(String nombre, Color color, int tLlegada, int rafaga, int prioridad){
        this.nombre = nombre;
        this.color = color;
        this.tLlegada = tLlegada;
        this.tLlegadaAux = tLlegada;
        this.rafaga = rafaga;
        this.prioridad = prioridad;
        this.rafagaEjecutada = 0;
        this.envejecimiento = 0;
    }

    public Object clone() throws CloneNotSupportedException  { 
        return super.clone(); 
    } 

    public String getNombre() {
        return nombre;
    }

    public int gettLlegada() {
        return tLlegada;
    }

    public int getRafaga() {
        return rafaga;
    }

    public int gettComienzo() {
        return tComienzo;
    }

    public int gettFinal() {
        return tFinal;
    }

    public int gettRetorno() {
        return tRetorno;
    }

    public int gettEspera() {
        return tEspera;
    }

    public Color getColor() {
        return color;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getRafagaEjecutada() {
        return rafagaEjecutada;
    }

    public int getTiempoLlegadaAux() {
        return tLlegadaAux;
    }
    
    public void settComienzo(int tComienzo) {
        this.tComienzo = tComienzo;
    }

    public void settFinal(int tFinal) {
        this.tFinal = tFinal;
    }

    public void settRetorno(int tRetorno) {
        this.tRetorno = tRetorno;
    }

    public void settEspera(int tEspera) {
        this.tEspera = tEspera;
    }

    public void settRafaga(int rafaga) {
        this.rafaga = rafaga;
    }

    public void settRafagaEjecutada(int rafagaEjecutada) {
        this.rafagaEjecutada = rafagaEjecutada;
    }

    public void settLlegadaAux(int tLlegadaAux) {
        this.tLlegadaAux = tLlegadaAux;
    }

    public int getEnvejecimiento() {
        return envejecimiento;
    }

    public void setEnvejecimiento(int envejecimiento) {
        this.envejecimiento = envejecimiento;
    }


}