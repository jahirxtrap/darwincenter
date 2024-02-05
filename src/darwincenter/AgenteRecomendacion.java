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
public class AgenteRecomendacion extends Agent {

    int numUsuario = 1;

    @Override
    protected void setup() {
        addBehaviour(new AgenteBehaviour());
    }

    private class AgenteBehaviour extends Behaviour {

        @Override
        public void action() {
            // 1. Solicitar datos del perfil del usuario al agente de usuario
            AID Agente = new AID("AgenteUsuario", AID.ISLOCALNAME);
            ACLMessage solicitudPerfil = new ACLMessage(ACLMessage.REQUEST);
            solicitudPerfil.addReceiver(Agente);
            solicitudPerfil.setContent("SolicitarPerfil");
            send(solicitudPerfil);

            // 2. Recibir datos del perfil del usuario
            ACLMessage respuestaPerfil = blockingReceive();
            if (respuestaPerfil != null && respuestaPerfil.getPerformative() == ACLMessage.INFORM) {
                String perfilUsuario = respuestaPerfil.getContent();

                // 3. Analizar los datos del perfil del usuario para determinar estilos de
                // aprendizaje, inteligencias múltiples, etc.
                List<String> estilosAprendizaje = Arrays.asList(perfilUsuario.split(","));
                // Simulación de lógica de análisis del perfil
                analizarPerfil(estilosAprendizaje);

                // 4. Generar recomendaciones
                List<String> recomendaciones = generarRecomendaciones();

                // 5. Enviar recomendaciones al usuario
                ACLMessage mensajeRecomendaciones = new ACLMessage(ACLMessage.INFORM);
                mensajeRecomendaciones.addReceiver(Agente);
                mensajeRecomendaciones.setContent(String.join(",", recomendaciones));
                send(mensajeRecomendaciones);
            }
        }

        @Override
        public boolean done() {
            // Esperar antes de volver a ejecutar el comportamiento
            try {
                Thread.sleep(500);
                for (int i = 0; i < 10; i++) {
                    System.out.print(".");
                    Thread.sleep(500);
                }
                Thread.sleep(500);
                System.out.println("");
            } catch (InterruptedException e) {
            }
            return false;
        }

        private void analizarPerfil(List<String> estilosAprendizaje) {
            // Simulación de lógica de análisis del perfil del usuario
            System.out.println("Perfil de " + usr.getUsername() + " [id:" + usr.getId() + "]: " + estilosAprendizaje);
            numUsuario++;
        }

        private List<String> generarRecomendaciones() {
            // Simulación de lógica de generación de recomendaciones
            List<String> recomendaciones = Arrays.asList("Articulo1", "Blog2", "Noticia3");
            return recomendaciones;
        }
    }
}
