package pt.uc.dei.aor.paj;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.uc.dei.aor.paj.xml.TransformXML;

public class Publisher {
	
	private static final Logger log = LoggerFactory.getLogger(TransformXML.class);

	private ConnectionFactory cf;
	private Topic t;
	
	private boolean error = false;

	// inicializa
	public Publisher() {
		try {
			this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
			this.t = InitialContext.doLookup("jms/topic/PlayTopic");
		} catch (NamingException e) {
			error = true;
			log.error("Error: "+e.getMessage());
		}
	}

	// envia mensagem aos subscribers
	public void publish(String textToSend) {
		try (JMSContext jcontext = cf.createContext("joao", "pedro");) {
			JMSProducer mp = jcontext.createProducer();
			mp.send(t, textToSend);	
		} catch (JMSRuntimeException re) {
			error = true;
			log.error("Error: "+re.getMessage());
		}
	}

	// **** Getters e Setters ****
	
	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
	
}