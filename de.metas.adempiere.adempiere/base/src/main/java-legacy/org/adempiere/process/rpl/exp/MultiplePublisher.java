package org.adempiere.process.rpl.exp;

//MultiplePublisher.java

import javax.jms.*;
import javax.naming.*;

public class MultiplePublisher {
	TopicConnection topicConnection = null;
	TopicSession topicSession = null;
	Topic topic = null;
	TopicPublisher topicPublisher = null;
	public final String topicName = "asunto";
	static int startindex = 0;

	public MultiplePublisher() {
		TopicConnectionFactory topicConnectionFactory = null;

		try {
			InitialContext contexto = new InitialContext();
			topicConnectionFactory = (TopicConnectionFactory) contexto.lookup("TopicDurable");
			topicConnection = topicConnectionFactory.createTopicConnection();
			topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = (Topic) contexto.lookup("asunto");
			topicPublisher = topicSession.createPublisher(topic);
		} catch (Exception e) {
			System.out.println("Error en constructor de MultiplePublisher");
		}
	}

	public void publishMessages() {
		TextMessage message = null;
		int i;
		final int NUMMSGS = 3;
		final String MSG_TEXT = new String("Message");

		try {
			message = topicSession.createTextMessage();
			for (i = startindex; i < startindex + NUMMSGS; i++) {
				message.setText(MSG_TEXT + " " + (i + 1));
				System.out.println("Publicado mensaje: " + message.getText());
				topicPublisher.publish( message );
			}

			topicPublisher.publish( topicSession.createMessage() );
			startindex = i;
		} catch (Exception e) {
			System.out.println("Error en metodo publishMessages() de MultiplePublisher");
			e.printStackTrace();
		}
	}

	public void finish() {
		if (topicConnection != null) {
			try {
				topicConnection.close();
			} catch (JMSException e) {
			}
		}
	}
}
