/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          *
 * http://www.adempiere.org                                           *
 *                                                                    *
 * Copyright (C) Trifon Trifonov.                                     *
 * Copyright (C) Contributors                                         *
 *                                                                    *
 * This program is free software; you can redistribute it and/or      *
 * modify it under the terms of the GNU General Public License        *
 * as published by the Free Software Foundation; either version 2     *
 * of the License, or (at your option) any later version.             *
 *                                                                    *
 * This program is distributed in the hope that it will be useful,    *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       *
 * GNU General Public License for more details.                       *
 *                                                                    *
 * You should have received a copy of the GNU General Public License  *
 * along with this program; if not, write to the Free Software        *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         *
 * MA 02110-1301, USA.                                                *
 *                                                                    *
 * Contributors:                                                      *
 *  - Trifon Trifonov (trifonnt@users.sourceforge.net)                *
 *  - Antonio Cañaveral (antonio.canaveral@e-evolution.com)			  *
 *                                                                    *
 * Sponsors:                                                          *
 *  - E-evolution (http://www.e-evolution.com/)                       *
 **********************************************************************/

package org.adempiere.server.rpl.imp;

import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.process.rpl.XMLHelper;
import org.adempiere.server.rpl.IReplicationProcessor;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IImportHelper;
import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.adempiere.util.lang.Mutable;
import org.apache.activemq.ActiveMQConnectionFactory;
//import org.compiere.util.Trx;
import org.compiere.util.TrxRunnable;
import org.w3c.dom.Document;

/**
 * Listen for JMS Messages
 *
 * @author Trifon N. Trifonov
 * @author Antonio Cañaveral, e-Evolution <li>[ 2194986 ] Already connected ClientID issue. <li>http://sourceforge.net/tracker/index.php?func=detail&aid=2194986&group_id=176962&atid=879332
 */
public class TopicListener implements MessageListener, ExceptionListener
{

	/**
	 * Connection to JMS Server
	 */
	private Connection conn;

	/**
	 * JMS Session
	 */
	private Session session;

	/**
	 * Introducing a response producer that will be used to reply if an incoming message has a reply-to destination.
	 */
	// t.schoeneberg@metas.de, 03132
	private MessageProducer replyProducer;

	/**
	 * JMS Topic
	 */
	private Topic topic;

	// private String
	// url="tcp://localhost:61616?jms.dispatchAsync=true&jms.useAsyncSend=true&jms.optimizeAcknowledge=true&jms.disableTimeStampsByDefault=true&jms.optimizedMessageDispatch=true&wireFormat.cacheEnabled=false&wireFormat.tightEncodingEnabled=false";
	private String url = "tcp://localhost:61616";

	/**
	 * host where JMS server is running
	 */
	private String host = "localhost";

	/**
	 * port of JMS Server
	 */
	private int port = 61616;

	/**
	 * Network protocol
	 */
	private String protocol = "tcp";

	/**
	 * Context
	 */
	private Properties ctx = null;

	/**
	 * Transaction name
	 */
	private String trxName = null;

	/**
	 * Topic Name
	 */
	private String topicName = null;

	/**
	 * Replication processor
	 */
	private IReplicationProcessor replicationProcessor = null;

	/** Logger */
	protected Logger log = LogManager.getLogger(TopicListener.class);

	/**
	 * Is Durable Subscription
	 */
	private boolean isDurableSubscription = false;

	/**
	 * Subscription Name
	 */
	private String subscriptionName = null;

	/**
	 * JMS Connection ClientID
	 */
	private String clientID = null;

	/**
	 * String User Name
	 */
	private String userName = null;

	/**
	 * Password
	 */
	private String password = null;

	private boolean isStopping = false;

	public TopicListener(final Properties ctx, 
			final IReplicationProcessor replicationProcessor, 
			final String protocol, 
			final String host, 
			final int port,
			final boolean isDurableSubscription, 
			final String subscriptionName, 
			final String topicName,
			final String clientID, 
			final String userName, 
			final String password,
			final String options, 
			final String trxName)
	{
		if (host != null && !host.equals(""))
		{
			this.host = host;
		}

		if (port > 0)
		{
			this.port = port;
		}

		if (protocol != null && !protocol.equals(""))
		{
			this.protocol = protocol;
		}

		this.topicName = topicName;

		String uri = this.protocol + "://" + this.host + ":" + this.port;

		if (options != null && options.length() > 0)
		{
			if (!options.contains("?"))
			{
				uri += "?" + options;
			}
		}
		setUrl(uri);

		this.ctx = ctx;
		this.trxName = trxName;
		this.replicationProcessor = replicationProcessor;
		this.isDurableSubscription = isDurableSubscription;
		this.subscriptionName = subscriptionName;
		this.clientID = clientID;
		this.userName = userName;
		this.password = password;
	}

	public void run()
	{
		try
		{
			run0();
			replicationProcessor.setProcessRunning(true);
		}
		catch (final Exception e)
		{
			stop();
			throw new ReplicationException("TopicListener.run() caught Exception during startup: " + e.getMessage()
					+ "\n Processor: " + replicationProcessor
					, e);
		}
	}

	private void run0() throws JMSException
	{
		final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
		log.trace("ActiveMQConnectionFactory = " + factory);

		if (userName != null && password != null)
		{
			conn = factory.createConnection(userName, password);
		}
		else
		{
			conn = factory.createConnection();
		}
		conn.setExceptionListener(this);
		log.trace("conn = " + conn);

		if (conn.getClientID() == null)
		{
			conn.setClientID(clientID);
		}
		else
		{
			if (conn.getClientID().equals(clientID))
			{
				throw new AdempiereException("Connection with clientID '" + clientID + "' already exists");
			}
			else
			{
				conn.setClientID(clientID);
			}
		}

		session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE); // TODO - could be parameter
		log.trace("session = " + session);

		log.trace("topicName = " + topicName);
		log.trace("subscriptionName = " + subscriptionName);

		topic = session.createTopic(topicName);
		log.trace("topic = " + topic);

		final MessageConsumer consumer;
		if (isDurableSubscription)
		{
			// http://publib.boulder.ibm.com/infocenter/wasinfo/v6r0/index.jsp?topic=/com.ibm.websphere.pmc.express.doc/tasks/tjn0012_.html
			// The subscriptionName assigned to a durable subscription must be unique within a given client ID.
			consumer = session.createDurableSubscriber(topic, subscriptionName);
		}
		else
		{
			consumer = session.createConsumer(topic);
		}

		log.trace("consumer = " + consumer);

		consumer.setMessageListener(this);

		// t.schoneberg@metas.de, 03132
		// Setup a message producer to respond to messages from clients, we will get the destination
		// to send to from the JMSReplyTo header field from a Message
		replyProducer = session.createProducer(null);

		conn.start();
		log("Connected to JMS Server. Waiting for messages!", // summary
				null, // text
				"topicName=" + topicName + ", subscription=" + subscriptionName, // reference
				null); // exception
	}

	/**
	 *
	 */
	@Override
	public void onMessage(final Message message)
	{
		String text = null;
		String responseStr = null;
		// TextMessage txtMessage = null;
		try
		{
			text = getText(message);
			log.trace("Received message(text): \n{}", text);

			responseStr = importXMLDocument(text);

			log("Imported Document", text, null, null);
		}
		catch (final RuntimeException e)
		{
			// Processing errors: log them
			logException(e, text);
		}
		catch (final JMSException e)
		{
			logException(e, text);
			log.trace("Rollback = " + e.toString());
			stop();
		}
		finally
		{
			try
			{
				// t.schoeneberg@metas.de, 03132: if the message sender wants a reply, we send one
				if (message.getJMSReplyTo() != null)
				{
					// we send a reply, even if 'responseStr' is empty or NULL,
					// otherwise the other party might hang
					final TextMessage response = session.createTextMessage();
					response.setJMSCorrelationID(message.getJMSCorrelationID());
					response.setText(responseStr);
					replyProducer.send(message.getJMSReplyTo(), response);
				}
				// end of t.schoeneberg@metas.de, 03132

				// commit the JMS session(mark messages as consumed)
				session.commit();
			}
			catch (final JMSException e)
			{
				logException(e, responseStr);
				log.trace("Rollback = " + e.toString());
				stop();
			}
		}
	}

	public String getText(final Message message) throws JMSException
	{
		Check.assumeNotNull(message, "message not null");

		if (message instanceof TextMessage)
		{
			final TextMessage txtMessage = (TextMessage)message;
			final String text = txtMessage.getText();
			return text;
		}
		else if (message instanceof BytesMessage)
		{
			final BytesMessage bytesMessage = (BytesMessage)message;
			final int bytes_len = (int)bytesMessage.getBodyLength();
			final byte[] bytes = new byte[bytes_len];
			bytesMessage.readBytes(bytes);

			final String text = new String(bytes);
			return text;
		}
		else
		{
			throw new AdempiereException("Message not supported: " + message + " (class: " + message.getClass() + ")");
		}
	}

	/**
	 * Import xml document
	 *
	 * @param xml xml document (as string) to be imported
	 * @return xml response (as string)
	 */
	protected String importXMLDocument(final String xml)
	{
		final Document documentToBeImported;

		try
		{
			documentToBeImported = XMLHelper.createDocumentFromString(xml);
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e);
		}

		final Mutable<Document> responseDOM = new Mutable<Document>(); // 03132

		Services.get(ITrxManager.class).run(trxName, new TrxRunnable()
		{
			@Override
			public void run(final String trxName)
			{
				final IImportHelper impHelper = Services.get(IIMPProcessorBL.class).createImportHelper(ctx);
				try
				{
					final StringBuilder result = new StringBuilder();
					responseDOM.setValue(impHelper.importXMLDocument(result, documentToBeImported, trxName));
				}
				catch (final Exception e)
				{
					throw e instanceof AdempiereException ? (AdempiereException)e : new AdempiereException(e);
				}
			}
		});

		// t.schoeneberg@metas.de, 03132: return the DOM response as string
		if (responseDOM.getValue() == null)
		{
			return null;
		}
		return XMLHelper.createStringFromDOMNode(responseDOM.getValue());
	}

	public void setUrl(final String url)
	{
		this.url = url;
	}

	public void stop()
	{
		isStopping = true;
		try
		{
			log.trace("Closing JMS Connection!");
			if (session != null)
			{
				try
				{
					session.rollback();
				}
				catch (final JMSException e)
				{
				}
				session = null;
			}
			if (conn != null)
			{
				try
				{
					conn.close();
				}
				catch (final JMSException e)
				{
				}
				conn = null;
			}
		}
		finally
		{
			isStopping = false;

			// 02486 make sure that we inform 'replicationProcessor' so there is a change of beeing restarted
			replicationProcessor.setProcessRunning(false);
		}
	}

	private void logException(final Exception e, final String messageText)
	{
		log("Error: " + e.getLocalizedMessage(),
				messageText, // text
				null, // reference
				e// isError
		);
	}

	private void log(final String summary, final String text, final String reference, final Throwable error)
	{
		final org.compiere.model.I_IMP_Processor impProcessor = replicationProcessor.getMImportProcessor();
		Services.get(IIMPProcessorBL.class).createLog(impProcessor, summary, text, reference, error);
	}

	@Override
	public void onException(final JMSException e)
	{
		if (isStopping)
		{
			return;
		}
		logException(e, null);
		stop(); // Assume all JMSExceptions as being fatal
	}
}
