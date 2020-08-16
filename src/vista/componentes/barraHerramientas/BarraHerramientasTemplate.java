package vista.componentes.barraHerramientas;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Image;


public class BarraHerramientasTemplate extends JPanel {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JLabel lTItulo;
    private JButton bCerrar;
    
    //Objetos decoradores
    private Font fuenteTitulo;
    private ImageIcon iAux, iLogo, iCerrar;
    private Cursor cMano;

    public BarraHerramientasTemplate(BarraHerramientasComponent barraHerramientasComponent) {

        iLogo = new ImageIcon("resources/img/gestionProcesos.png");
        iCerrar = new ImageIcon("resources/img/cerrar.png");

        iAux = new ImageIcon(iLogo.getImage().getScaledInstance(45, 45, Image.SCALE_AREA_AVERAGING));
        fuenteTitulo = new Font("Milton Keynes", Font.BOLD, 20);
        lTItulo = new JLabel("  MULTICOLAS");
        lTItulo.setFont(fuenteTitulo);
        lTItulo.setBounds(465, 0, 420 , 50);
        lTItulo.setForeground(Color.WHITE);
        lTItulo.setIcon(iAux);
        lTItulo.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lTItulo);

        iAux = new ImageIcon(iCerrar.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING));
        bCerrar = new JButton();
        bCerrar.setBounds(1300, 0, 50, 50);
        bCerrar.addActionListener(barraHerramientasComponent);
        bCerrar.setFocusable(false);
        bCerrar.setContentAreaFilled(false);
        bCerrar.setBorder(null);
        bCerrar.addMouseListener(barraHerramientasComponent);
        cMano = new Cursor(Cursor.HAND_CURSOR);
        bCerrar.setIcon(iAux);
        bCerrar.setCursor(cMano);
        this.add(bCerrar);

        this.setSize(1350, 50);
        this.setLayout(null);
        this.addMouseMotionListener(barraHerramientasComponent);
        this.addMouseListener(barraHerramientasComponent);
        this.setBackground(new Color(25, 93, 150));
    }
}