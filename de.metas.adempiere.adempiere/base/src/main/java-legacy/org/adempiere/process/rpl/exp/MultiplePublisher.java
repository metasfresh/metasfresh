package org.adempiere.process.rpl.exp;

/*
 * #%L
 * ADempiere ERP - Base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


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
