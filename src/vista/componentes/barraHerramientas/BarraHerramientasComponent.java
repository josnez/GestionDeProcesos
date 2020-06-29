package vista.componentes.barraHerramientas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BarraHerramientasComponent implements ActionListener {

    private BarraHerramientasTemplate barraHerramientasTemplate;

    public BarraHerramientasComponent() {

        barraHerramientasTemplate = new BarraHerramientasTemplate(this);
    }

    public BarraHerramientasTemplate gBarraHerramientasTemplate() {
        return barraHerramientasTemplate;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}