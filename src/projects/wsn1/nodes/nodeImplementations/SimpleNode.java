package projects.wsn1.nodes.nodeImplementations;

import projects.wsn1.nodes.messages.WsnMsg;
import projects.wsn1.nodes.timers.WsnMessageTimer;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;
import sinalgo.runtime.Global;

public class SimpleNode extends Node {

    private Node proximoNoAteEstacaoBase;
    private Integer sequenceNumber = 0;
    private boolean isSinkNode = false;

    @Override
    public void handleMessages(Inbox inbox) {
        while (inbox.hasNext()) {
            Message message = inbox.next();
            if (message instanceof WsnMsg) {
                WsnMsg wsnMessage = (WsnMsg) message;
                boolean encaminhar = true;

                if (wsnMessage.forwardingHop.equals(this)) {
                    encaminhar = false;  // Evitar loop
                } else if (wsnMessage.tipoMsg == 0) {  // Mensagem de Roteamento
                    if (proximoNoAteEstacaoBase == null || sequenceNumber < wsnMessage.sequenceID) {
                        proximoNoAteEstacaoBase = inbox.getSender();
                        sequenceNumber = wsnMessage.sequenceID;
                    } else {
                        encaminhar = false;
                    }
                } else if (wsnMessage.tipoMsg == 1) {  // Mensagem de Dados
                    if (isSinkNode) {
                        System.out.println("SinkNode " + this.ID + " recebeu dados de " + inbox.getSender().ID);
                        encaminhar = false;  // No Sink, não encaminha mensagens de dados
                    } else {
                        System.out.println(this.ID + " recebeu dados de " + wsnMessage.origem.ID);
                    }
                }

                if (encaminhar) {
                    wsnMessage.forwardingHop = this;
                    broadcast(wsnMessage);
                }
            }
        }
    }

    @Override
    public void preStep() {
        if (isSinkNode && Global.currentTime == 1) {  // Mudança aqui
            construirRoteamento();
        } else if (proximoNoAteEstacaoBase != null) {
            // Enviar mensagem de dados
            WsnMsg dataMsg = new WsnMsg(sequenceNumber + 1, this, null, this, 1);
            send(dataMsg, proximoNoAteEstacaoBase);
        }
    }

    @NodePopupMethod(menuText = "Construir Arvore de Roteamento")
    public void construirRoteamento() {
        isSinkNode = true;
        proximoNoAteEstacaoBase = this;
        WsnMsg wsnMessage = new WsnMsg(1, this, null, this, 0);
        WsnMessageTimer timer = new WsnMessageTimer(wsnMessage);
        timer.startRelative(1, this);
    }

    @Override
    public void init() {}

    @Override
    public void neighborhoodChange() {}

    @Override
    public void postStep() {}

    @Override
    public void checkRequirements() throws WrongConfigurationException {}
}
