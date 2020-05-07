package de.metas.event.jms;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.transport.TransportListener;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import de.metas.event.Event;
import de.metas.event.EventBusConstants;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.jms.IJMSService;
import lombok.NonNull;

public class ActiveMQJMSEndpoint implements IJMSEndpoint
{
	// services
	private static final transient Logger logger = EventBusConstants.getLogger(ActiveMQConnection.class);

	private static final String MSG_Event_RemoteEndpointDisconnected = "Event.RemoteEndpointDisconnected";
	private static final String MSG_Event_RemoteEndpointConnected = "Event.RemoteEndpointConnected";

	@VisibleForTesting
	public static final IEventSerializer DEFAULT_EVENT_SERIALIZER = JacksonJsonEventSerializer.instance;
	private final IEventSerializer eventSerializer = DEFAULT_EVENT_SERIALIZER;

	private final ExceptionListener exceptionListener = new ExceptionListener()
	{
		@Override
		public void onException(final JMSException jmsException)
		{
			ActiveMQJMSEndpoint.this.onJMSException(jmsException);
		}
	};

	/** Is connected ? */
	private final AtomicBoolean connected = new AtomicBoolean(false);
	private final TransportListener transportListener = new TransportListener()
	{

		@Override
		public void onCommand(Object command)
		{
			logger.trace("Transport command: {}", command);
		}

		@Override
		public void onException(IOException error)
		{
			logger.info("Transport exception", error);
		}

		@Override
		public void transportInterupted()
		{
			connected.set(false);
			logger.info("Transport interrupted");

			postConnectionStatusEvent();
		}

		@Override
		public void transportResumed()
		{
			// NOTE: at least on ActiveMQ we tested and found out that this method is called also when the connection is initially established.
			// So we can rely on this logic.
			connected.set(true);

			logger.info("Transport resumed");

			postConnectionStatusEvent();
		}
	};

	// private final ConnectionFactory _jmsConnectionFactory;
	private static final String JMS_PROPERTY_ClientID = de.metas.event.jms.ActiveMQJMSEndpoint.class.getName() + ".ClientID";
	private final String _jmsClientID;
	private Connection _jmsConnection;
	private Session _jmsSession;

	private final LoadingCache<String, MessageProducer> topicName2messageProducer = CacheBuilder.newBuilder().build(new CacheLoader<String, MessageProducer>()
	{
		@Override
		public MessageProducer load(final String topicName) throws Exception
		{
			return createTopicProducer(topicName);
		}

	});

	/**
	 * Asynchronous executor, used to execute ActiveMQ commands.
	 *
	 * The main reason we need this is because we activated the "failover" on activeMQ and in case the connection could not be established, activeMQ will block until it can solve it, and we want to
	 * avoid any delays on methods like {@link #sendEvent(String, Event)}, {@link #subscribe(String, IEventListener)}.
	 */
	private final ExecutorService asyncExecutor = Executors.newSingleThreadExecutor(CustomizableThreadFactory.builder()
			.setThreadNamePrefix(getClass().getName() + "-AsyncExecutor")
			.setDaemon(true)
			.build());

	private final IEventListener eventBus2JmsListener = new EventBus2JMSHandler(this);

	private ConnectionFactory _jmsConnectionFactory;

	public ActiveMQJMSEndpoint()
	{
		// JMS Client ID
		_jmsClientID = EventBusConstants.getSenderId();
		logger.info("JMS Client ID: {}", _jmsClientID);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("ClientID", _jmsClientID)
				.add("IsConnected", connected.get())
				.add("SubscribedTopicNames", topicName2messageProducer.asMap().keySet())
				.toString();
	}

	private final ConnectionFactory getConnectionFactory()
	{
		if (_jmsConnectionFactory == null)
		{
			_jmsConnectionFactory = Services.get(IJMSService.class).createConnectionFactory();
		}
		return _jmsConnectionFactory;
	}

	private final String getClientID()
	{
		return _jmsClientID;
	}

	private IEventListener getEventBus2JMSListener()
	{
		return eventBus2JmsListener;
	}

	private final Connection getConnection() throws JMSException
	{
		if (_jmsConnection == null)
		{
			_jmsConnection = createConnection();
		}
		return _jmsConnection;
	}

	private final Connection createConnection() throws JMSException
	{
		final ActiveMQConnection conn = (ActiveMQConnection)getConnectionFactory().createConnection();

		conn.setExceptionListener(exceptionListener);
		conn.addTransportListener(transportListener);

		//
		// Set ClientID
		final String clientID = getClientID();
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

		//
		// Start the connection
		conn.start();

		logger.info("Connection created and started: {}", conn);
		return conn;
	}

	private final Session getSession() throws JMSException
	{
		if (_jmsSession == null)
		{
			_jmsSession = createSession();
		}
		return _jmsSession;
	}

	private final Session createSession() throws JMSException
	{
		final Connection conn = getConnection();
		final Session session = conn.createSession(
				false, // transacted
				Session.AUTO_ACKNOWLEDGE // acknowledgeMode
		);
		logger.info("Session created: {}", session);
		return session;
	}

	private final MessageConsumer createTopicConsumer(final String topicName) throws JMSException
	{
		final Session session = getSession();
		final Topic topic = session.createTopic(topicName);
		final MessageConsumer consumer = session.createConsumer(topic);
		logger.debug("Message consumer for topic {} created: {}", new Object[] { topicName, consumer });
		return consumer;
	}

	private final String createEventBusId(final String topicName)
	{
		return getClientID() + "_" + topicName;
	}

	@Override
	public boolean bindIfNeeded(@NonNull final IEventBus eventBus)
	{
		// Forward events from event bus to JMS
		eventBus.subscribe(getEventBus2JMSListener());

		// Forward events from JMS to event bus
		forwardIncomingMessagesToEventBus(eventBus);

		return true;
	}

	private final void forwardIncomingMessagesToEventBus(@NonNull final IEventBus eventBus)
	{
		asyncExecutor.submit(() -> forwardIncomingMessagesToEventBusNow(eventBus));
	}

	private final void forwardIncomingMessagesToEventBusNow(final IEventBus eventBus)
	{
		try
		{
			MessageConsumer2EventBusForwarder.createAndBind(this, eventBus);
		}
		catch (final JMSException e)
		{
			logger.warn("Failed binding JMS->EventBus for " + eventBus + ". Ignored.", e);
		}
	}

	private final MessageProducer createTopicProducer(final String topicName) throws JMSException
	{
		final Session session = getSession();
		final Topic topic = session.createTopic(topicName);
		final MessageProducer producer = session.createProducer(topic);
		logger.debug("Message producer created: {}", producer);
		return producer;
	}

	private final MessageProducer getTopicProducer(final String topicName) throws JMSException
	{
		try
		{
			return topicName2messageProducer.get(topicName);
		}
		catch (final ExecutionException e)
		{
			final Throwable cause = e.getCause();
			if (cause instanceof JMSException)
			{
				throw (JMSException)cause;
			}
			else
			{
				throw AdempiereException.wrapIfNeeded(cause);
			}
		}
	}

	@Override
	public final void sendEvent(final String topicName, final Event event)
	{
		asyncExecutor.submit(() -> sendEventNow(topicName, event));
	}

	private final void sendEventNow(final String topicName, final Event event)
	{
		try
		{
			// If the event comes from this JMS, don't forward it back
			final String jmsEventBusId = createEventBusId(topicName);
			if (event.wasReceivedByEventBusId(jmsEventBusId))
			{
				return;
			}

			final String messageText = eventSerializer.toString(event);
			final Session jmsSession = getSession();
			final TextMessage jmsMessage = jmsSession.createTextMessage();
			jmsMessage.setText(messageText);
			jmsMessage.setStringProperty(JMS_PROPERTY_ClientID, getClientID()); // flag it so we know that we issued it

			final MessageProducer jmsProducer = getTopicProducer(topicName);
			jmsProducer.send(jmsMessage);

			logger.debug("JMS: send event: {}", event);
		}
		catch (final JMSException e)
		{
			logger.warn("Failed to send " + event + " to " + topicName + ". Ignored.", e);
		}

	}

	private final void onJMSException(final JMSException jmsException)
	{
		// TODO: close connections, sessions, topics cache etc
		logger.error("JMS Error: " + jmsException.getLocalizedMessage(), jmsException);
	}

	@Override
	public boolean isConnected()
	{
		return connected.get();
	}

	@Override
	public void checkConnection()
	{
		// If connection is already up, we are fine.
		if (isConnected())
		{
			return;
		}

		// notify the user that the connection is disconnected
		postConnectionStatusEvent();
	}

	private final void postConnectionStatusEvent()
	{
		final boolean connected = this.connected.get();

		Services.get(IEventBusFactory.class)
				.getEventBus(EventBusConstants.TOPIC_GeneralNotificationsLocal)
				.postEvent(Event.builder()
						.setDetailADMessage(connected ? MSG_Event_RemoteEndpointConnected : MSG_Event_RemoteEndpointDisconnected)
						.build());
	}

	private static final class MessageConsumer2EventBusForwarder implements MessageListener
	{
		public static final void createAndBind(final ActiveMQJMSEndpoint jms, final IEventBus eventBus) throws JMSException
		{
			new MessageConsumer2EventBusForwarder(jms, eventBus);
		}

		private final WeakReference<IEventBus> eventBusRef;
		private final MessageConsumer jmsConsumer;
		private final IEventSerializer eventSerializer;
		private final String eventBusId;
		private final String jmsClientId;
		//
		private boolean destoyed = false;

		private MessageConsumer2EventBusForwarder(final ActiveMQJMSEndpoint jms, final IEventBus eventBus) throws JMSException
		{
			Check.assumeNotNull(eventBus, "eventBus not null");
			this.eventBusRef = new WeakReference<>(eventBus);

			final String topicName = eventBus.getName();
			this.eventBusId = jms.createEventBusId(topicName);
			this.jmsConsumer = jms.createTopicConsumer(topicName);
			this.jmsClientId = jms.getClientID();
			this.eventSerializer = jms.eventSerializer;

			this.jmsConsumer.setMessageListener(this);
		}

		@Override
		public void onMessage(final Message jmsMessage)
		{
			// Do nothing if already destroyed.
			if (destoyed)
			{
				return;
			}
			// Make sure the event bus was not destroyed
			final IEventBus eventBus = eventBusRef.get();
			if (eventBus == null || eventBus.isDestroyed())
			{
				destroy();
				return;
			}

			String eventAsString = null;
			try
			{
				// Avoid message which were sent by our topic producer
				final String jmsMessageClientId = jmsMessage.getStringProperty(JMS_PROPERTY_ClientID);
				if (Objects.equals(jmsMessageClientId, jmsClientId))
				{
					return;
				}

				eventAsString = extractText(jmsMessage);
				logger.trace("Received message(text): \n{}", eventAsString);

				final Event event = eventSerializer.fromString(eventAsString);
				logger.trace("Received event: {}", event);

				// Flag the event that it was received by JMS
				event.markReceivedByEventBusId(eventBusId);
				logger.debug("JMS: received event: {}", event);

				// Forward the event to bus
				eventBus.postEvent(event);
			}
			catch (final RuntimeException e)
			{
				logger.warn("Failed receiving event", e);
			}
			catch (final JMSException e)
			{
				logger.warn("Failed receiving event", e);
			}
		}

		private final void destroy()
		{
			this.destoyed = true;

			//
			// Destroy the JMS consumer (we no longer need it, if the EventBus was destroyed)
			try
			{
				jmsConsumer.close();
			}
			catch (JMSException e)
			{
				logger.info("Failed closing the JMS consumer. Ingnored.", e);
			}

			// Clear the EventBus reference
			eventBusRef.clear();
		}

		private static final String extractText(final Message jmsMessage) throws JMSException
		{
			Check.assumeNotNull(jmsMessage, "message not null");

			if (jmsMessage instanceof TextMessage)
			{
				final TextMessage txtMessage = (TextMessage)jmsMessage;
				final String text = txtMessage.getText();
				return text;
			}
			else if (jmsMessage instanceof BytesMessage)
			{
				final BytesMessage bytesMessage = (BytesMessage)jmsMessage;
				final int bytes_len = (int)bytesMessage.getBodyLength();
				final byte[] bytes = new byte[bytes_len];
				bytesMessage.readBytes(bytes);

				final String text = new String(bytes);
				return text;
			}
			else
			{
				throw new AdempiereException("Message not supported: " + jmsMessage + " (class: " + jmsMessage.getClass() + ")");
			}
		}

	}
}
