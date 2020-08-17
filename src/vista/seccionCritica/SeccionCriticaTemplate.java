package vista.seccionCritica;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logica.Proceso;

public class SeccionCriticaTemplate extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JLabel lColor, lDescripcion, lRafaga, lNombre;
    private Proceso proceso;

    private Font fuente, fuente14;
    private Color colorAzul;

    public SeccionCriticaTemplate() {

        proceso = null;

        crearLabels();

        this.setSize(200, 200);
        this.setLayout(null);
        this.setBackground(null);
        this.setBorder(
            BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY), "Seccion Critica",
            SwingConstants.LEFT, 0, new Font("Comic Sans MS", Font.PLAIN, 17), Color.WHITE)
        );
    }

    private void crearLabels() {

        fuente = new Font("Comic Sans MS", Font.BOLD, 15);
        fuente14 = new Font("Comic Sans MS", Font.BOLD, 14);
        colorAzul = new Color(25, 93, 150);

        lNombre = new JLabel();
        lNombre.setBounds(50, 30, 100, 50);
        lNombre.setBorder(null);
        lNombre.setBackground(null);
        lNombre.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        lNombre.setForeground(Color.WHITE);
        lNombre.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lNombre);

        lColor = new JLabel();
        lColor.setBounds(50, 30, 100, 50);
        lColor.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, colorAzul));
        lColor.setBackground(null);
        lColor.setOpaque(true);
        lColor.setVisible(false);
        this.add(lColor);        

        lRafaga = new JLabel();
        lRafaga.setBounds(50, 80, 100, 25);
        lRafaga.setOpaque(true);
        lRafaga.setBackground(null);
        lRafaga.setFont(fuente);
        lRafaga.setForeground(Color.WHITE);
        lRafaga.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lRafaga);

        lDescripcion = new JLabel();
        lDescripcion.setBounds(20, 115, 160, 65);
        lDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        lDescripcion.setForeground(Color.WHITE);
        lDescripcion.setFont(fuente14);
        this.add(lDescripcion);
    }

    public void entraProceso(Proceso p, String cola) {
        this.proceso = p;
        lColor.setVisible(true);
        lColor.setBackground(p.getColor());
        lDescripcion.setText(
            "<html><div align='center'>"+
            "Tiempo de Llegada: "+proceso.gettLlegada()+"<br>"+
            "Tiempo de Inicio: "+proceso.gettComienzo()+"<br>"+
            "Cola: "+cola+
            "</div></html>"
        );
        lNombre.setText(p.getNombre());
        avanzaProceso();
    }
    
    public void avanzaProceso() {
        lRafaga.setText("Rafaga: "+proceso.getRafaga());
    }

	public void seVacio() {
        lColor.setVisible(false);
        lDescripcion.setText("");
        lNombre.setText("");
        lRafaga.setText("");
	}
    
}