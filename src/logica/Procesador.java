package logica;

public class Procesador extends Thread {

    private Logica l;
    private int tiempo;

    public Procesador(Logica log) {
        super("Procesaminto");
        this.l = log;
        tiempo = 0;
    }

    private void administrarProcesos() {
        System.out.print("");
        while (!l.getColaProcesos().isEmpty()) {
            Proceso c = l.getColaProcesos().poll();
            // Auxiliar para dibujar
            l.getColaProcesosGrafica().remove(0);
            l.actualizarColaProcesos();
            seccionCritica(c);
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
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(c.gettFinal()+i==tF)
        System.out.println("Proceso Completado");
    }

    public void run() {
        while (true) {
            administrarProcesos();
        }
    }
}