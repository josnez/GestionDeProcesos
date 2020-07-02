package logica;

import java.awt.Color;

public class Proceso implements Cloneable {
    
    private char nombre;
    private int tLlegada,rafaga, tComienzo, tFinal, tRetorno, tEspera;
    private Color color;
    
    public Proceso(char nombre, Color color, int tLlegada, int rafaga){
        this.nombre = nombre;
        this.color = color;
        this.tLlegada = tLlegada;
        this.rafaga = rafaga;
    }

    public Object clone() throws CloneNotSupportedException  { 
        return super.clone(); 
    } 

    public char getNombre() {
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

}