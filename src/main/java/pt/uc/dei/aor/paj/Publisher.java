package pt.uc.dei.aor.paj;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Publisher {
	
	private ConnectionFactory cf;
	private Topic t;

	public Publisher() {
		try {
			this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
			this.t = InitialContext.doLookup("jms/topic/PlayTopic");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void publish(String textToSend) {
		try (JMSContext jcontext = cf.createContext("joao", "pedro");) {
			JMSProducer mp = jcontext.createProducer();
			mp.send(t, textToSend);			
		} catch (JMSRuntimeException re) {
			re.printStackTrace();
		}
	}
	
}