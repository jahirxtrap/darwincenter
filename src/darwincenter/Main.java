package darwincenter;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

/**
 *
 * @author jahir
 */
public class Main extends Agent {

    @Override
    protected void setup() {
        System.out.println("-----Bienvenido a Darwin Center-----");

        // Crear agentes
        crearAgente("AgenteRecomendacion");
        crearAgente("AgenteUsuario");
    }

    @Override
    protected void takeDown() {
        // Acciones de limpieza, si es necesario
    }

    public void crearAgente(String agenteDestino) {
        try {
            AgentController ac = getContainerController().createNewAgent(
                    agenteDestino,
                    "darwincenter." + agenteDestino,
                    new Object[]{});
            ac.start();
        } catch (StaleProxyException e) {
        }
    }
}
