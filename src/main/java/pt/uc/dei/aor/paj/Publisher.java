package pt.uc.dei.aor.paj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try (JMSContext jcontext = cf.createContext("joao", "pedro");) {
			JMSProducer mp = jcontext.createProducer();
			mp.send(t, textToSend);

			//Send messages
//			String messageText = null;
//			while (true) {
//				System.out.println("Enter message to send or 'quit':");
//				messageText = reader.readLine();
//				System.out.println("Subscriber 1 ou 2? ");
//				int i = Integer.parseInt(reader.readLine());
//				if ("quit".equals(messageText))
//					break;
//				if ("stop".equals(messageText))
//					mp.send(t, messageText);
//				if (i == 1) mp.setProperty("Content", "HTML");
//				else mp.setProperty("Content", "Stats");
//				mp.send(t, messageText);
//			}
			//Exit
//			System.out.println("Exiting...");
//			reader.close();
//			System.out.println("Goodbye!");
			
		} catch (JMSRuntimeException re) {
			re.printStackTrace();
		}
	}
	
}