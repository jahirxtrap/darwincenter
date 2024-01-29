package darwincenter;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/**
 *
 * @author jahir
 */
public class Main extends Agent {

    public static void main(String[] args) {
        // Iniciar el contenedor principal
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        // Configuración para mostrar la interfaz de JADE
        p.setParameter(Profile.GUI, "true");
        ContainerController cc = rt.createMainContainer(p);

        // Crear el agente principal y asignarlo al contenedor
        try {
            AgentController ac = cc.createNewAgent(
                    "MainAgent",
                    "darwincenter.Main",
                    new Object[]{});
            ac.start();
        } catch (StaleProxyException e) { }
    }

    @Override
    protected void setup() {
        System.out.println("---Darwin Center---");
        
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
        } catch (StaleProxyException e) { }
    }
}
