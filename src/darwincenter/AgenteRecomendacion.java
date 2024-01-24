package darwincenter;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.util.List;

/**
 *
 * @author jahir
 */
public class AgenteRecomendacion extends Agent {
    
    @Override
    protected void setup() {
        addBehaviour(new AgenteBehaviour());
    }
    
    private class AgenteBehaviour extends Behaviour {
        
        @Override
        public void action() {
            // Dentro del método "action" de ComportamientoRecomendacion
            // 1. Solicitar datos del perfil del usuario al agente de usuario
            AID Agente = new AID("AgenteUsuario", AID.ISLOCALNAME);
            ACLMessage solicitudPerfil = new ACLMessage(ACLMessage.REQUEST);
            solicitudPerfil.addReceiver(Agente);
            solicitudPerfil.setContent("SolicitarPerfil");
            send(solicitudPerfil);

            // 2. Recibir datos del perfil del usuario
            ACLMessage respuestaPerfil = blockingReceive();
            String perfilUsuario = respuestaPerfil.getContent();

            // 3. Analizar los datos del perfil del usuario para determinar estilos de aprendizaje, inteligencias múltiples, etc.
            // Implementar la lógica de análisis aquí

            // 4. Generar recomendaciones
            List<String> recomendaciones = generarRecomendaciones(perfilUsuario);

            // 5. Enviar recomendaciones al usuario
            ACLMessage mensajeRecomendaciones = new ACLMessage(ACLMessage.INFORM);
            mensajeRecomendaciones.addReceiver(new AID("AgenteUsuario", AID.ISLOCALNAME));
            mensajeRecomendaciones.setContent(String.join(",", recomendaciones));
            send(mensajeRecomendaciones);
        }

        @Override
        public boolean done() {
            // Condición de finalización del comportamiento
            return true; // Aquí puedes ajustar según tus necesidades
        }
    }
    
    private List<String> generarRecomendaciones(String perfilUsuario) {
        List<String> recomendaciones = null;
        return recomendaciones;
    }
}
