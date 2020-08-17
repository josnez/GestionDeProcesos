package vista.seccionCritica;

import logica.Proceso;

public class SeccionCriticaComponent {

    private SeccionCriticaTemplate seccionCriticaTemplate;

    public SeccionCriticaComponent() {

        seccionCriticaTemplate = new SeccionCriticaTemplate();
    }

    public SeccionCriticaTemplate gSeccionCriticaTemplate() {

        return seccionCriticaTemplate;
    }

	public void entraProceso(Proceso p, String cola) {
        seccionCriticaTemplate.entraProceso(p, cola);
	}

	public void avanzaProceso() {
        seccionCriticaTemplate.avanzaProceso();
        seccionCriticaTemplate.repaint();
	}

	public void seVacio() {
        seccionCriticaTemplate.seVacio();
	}
    
}