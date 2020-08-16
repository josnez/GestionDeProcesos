package vista.componentes.barraHerramientas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;

import vista.vistaPrincipal.VistaPrincipalComponent;

public class BarraHerramientasComponent implements ActionListener, MouseListener, MouseMotionListener {

    private BarraHerramientasTemplate barraHerramientasTemplate;
    private VistaPrincipalComponent vistaPrincipalComponent;

    private int x, y;

    public BarraHerramientasComponent(VistaPrincipalComponent vistaPrincipalComponent) {

        this.vistaPrincipalComponent = vistaPrincipalComponent;
        barraHerramientasTemplate = new BarraHerramientasTemplate(this);
    }

    public BarraHerramientasTemplate gBarraHerramientasTemplate() {
        return barraHerramientasTemplate;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        if (arg0.getSource() instanceof JButton) {
            
            JButton b = (JButton) arg0.getSource();
            b.setContentAreaFilled(true);
            b.setBackground(Color.DARK_GRAY);
        }
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        if (arg0.getSource() instanceof JButton) {
            
            JButton b = (JButton) arg0.getSource();
            b.setContentAreaFilled(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        x = arg0.getX();
        y = arg0.getY();
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    @Override
    public void mouseDragged(MouseEvent arg0) {

        vistaPrincipalComponent.moverFrame(arg0.getXOnScreen() - x, arg0.getYOnScreen() - y);
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
    }
}