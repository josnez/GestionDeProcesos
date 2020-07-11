package vista.vistaPrincipal;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class VistaPrincipalTemplate extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JPanel pBarraHerramientas, pColaProcesos, pTabla, pDiagrama;
    
    public VistaPrincipalTemplate(){

        crearJPanels();

        this.setSize(1200, 700);
        this.setLayout(null);
        this.setUndecorated(true);
        this.getContentPane().setBackground(new Color(50, 50, 51));
        this.setLocationRelativeTo(this);
        this.setVisible(true);
    }

    private void crearJPanels() {

        pBarraHerramientas = new JPanel();
        pBarraHerramientas.setBounds(0, 0, 1200, 50);
        pBarraHerramientas.setLayout(null);
        this.add(pBarraHerramientas);

        pColaProcesos = new JPanel();
        pColaProcesos.setBounds(0, 50, 1200, 100);
        pColaProcesos.setLayout(null);
        pColaProcesos.setBackground(null);
        this.add(pColaProcesos);

        pTabla = new JPanel();
        pTabla.setBounds(0, 150, 1200, 275);
        pTabla.setLayout(null);
        pTabla.setBackground(null);
        this.add(pTabla);

        pDiagrama = new JPanel();
        pDiagrama.setBounds(0, 425, 1200, 275);
        pDiagrama.setLayout(null);
        pDiagrama.setBackground(null);
        this.add(pDiagrama);
    }

    public JPanel getpBarraHerramientas() {
        return pBarraHerramientas;
    }

    public JPanel getpColaProcesos() {
        return pColaProcesos;
    }

    public JPanel getpTabla() {
        return pTabla;
    }

    public JPanel getpDiagrama() {
        return pDiagrama;
    }
    
}