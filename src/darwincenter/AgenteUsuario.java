package darwincenter;

import static darwincenter.LoginFrame.usr;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jahir
 */
public class AgenteUsuario extends Agent {

    private String perfilUsuario;

    @Override
    protected void setup() {
        addBehaviour(new AgenteBehaviour());
    }

    private class AgenteBehaviour extends Behaviour {

        @Override
        public void action() {
            // Esperar solicitud de perfil del agente de recomendación
            ACLMessage solicitudPerfil = blockingReceive();
            if (solicitudPerfil != null && solicitudPerfil.getContent().equals("SolicitarPerfil")) {
                // Proporcionar datos de perfil al agente de recomendación
                enviarPerfil();
            }

            // Recibir recomendaciones del agente de recomendación
            ACLMessage mensajeRecomendaciones = blockingReceive();
            if (mensajeRecomendaciones != null && mensajeRecomendaciones.getPerformative() == ACLMessage.INFORM) {
                procesarRecomendaciones(mensajeRecomendaciones.getContent());
            }
        }

        @Override
        public boolean done() {
            // Condición de finalización del comportamiento
            return false;
        }

        private void enviarPerfil() {
            // Enviar datos de perfil al agente de recomendación
            perfilUsuario = usr.getEstiloAprendizajeId() + "," + usr.getIntMultiplesId() + "," + usr.getCocienteIntelectual();
            ACLMessage respuestaPerfil = new ACLMessage(ACLMessage.INFORM);
            respuestaPerfil.addReceiver(new AID("AgenteRecomendacion", AID.ISLOCALNAME));
            respuestaPerfil.setContent(perfilUsuario);
            send(respuestaPerfil);
        }

        private void procesarRecomendaciones(String recomendaciones) {
            // 4. Procesar y mostrar recomendaciones al usuario
            List<String> listaRecomendaciones = Arrays.asList(recomendaciones.split(","));
            System.out.println("Recomendaciones: " + listaRecomendaciones + "\n------------------------------------");
        }
    }
}
