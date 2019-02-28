package org.adempiere.server.rpl.imp;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.process.rpl.XMLHelper;
import org.adempiere.server.rpl.IReplicationProcessor;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IImportHelper;
import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.adempiere.util.lang.Mutable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.w3c.dom.Document;

import javax.naming.Binding;
import java.util.Properties;

/**
 * Listen for AMQP Messages
 */
public class RabbitMqListener implements MessageListener
{

	/**
	 * connection factory to AMQP server
	 */
	private CachingConnectionFactory connectionFactory;

	/**
	 * host where AMQP server is running
	 */
	private String host = "localhost";

	/**
	 * port of JMS Server
	 */
	private int port = 5672;

	/**
	 * Context
	 */
	private Properties ctx;

	/**
	 * Transaction name
	 */
	private String trxName;

	/**
	 * Queue Name
	 */
	private String queueName;

	/**
	 * Is durable queue
	 */
	private boolean isDurableQueue;

	/**
	 * Exchange name
	 */
	private String exchangeName;

	/**
	 * Replication processor
	 */
	private IReplicationProcessor replicationProcessor;

	/**
	 * Logger
	 */
	protected Logger log = LogManager.getLogger(TopicListener.class);

	/**
	 * consumer tag
	 */
	private String consumerTag;

	/**
	 * String User Name
	 */
	private String userName;

	/**
	 * Password
	 */
	private String password;

	private boolean isStopping = false;

	public RabbitMqListener(final Properties ctx,
			final IReplicationProcessor replicationProcessor,
			final String host,
			final int port,
			final String consumerTag,
			final String queueName,
			final String userName,
			final String password,
			final String trxName,
			final String exchangeName,
			final boolean isDurableQueue)
	{
		if (host != null && !host.equals(""))
		{
			this.host = host;
		}

		if (port > 0)
		{
			this.port = port;
		}

		this.queueName = queueName;

		this.ctx = ctx;
		this.trxName = trxName;
		this.replicationProcessor = replicationProcessor;
		this.consumerTag = consumerTag;
		this.userName = userName;
		this.password = password;
		this.exchangeName = exchangeName;
		this.isDurableQueue = isDurableQueue;
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
			throw new ReplicationException("RabbitMqListener.run() caught Exception during startup: " + e.getMessage()
					+ "\n Processor: " + replicationProcessor
					, e);
		}
	}

	private void run0() throws Exception
	{

		connectionFactory = new CachingConnectionFactory(host, port);
		if (userName != null && password != null)
		{
			connectionFactory.setUsername(userName);
			connectionFactory.setUsername(password);
		}
		RabbitAdmin admin = new RabbitAdmin(connectionFactory);
		Queue queue = new Queue(queueName, isDurableQueue);
		exchangeName = "de.metas.esb.durable.exchange";
		TopicExchange exchange = new TopicExchange(exchangeName, isDurableQueue, false);
		admin.declareExchange(exchange);
		admin.declareQueue(queue);
		// queue name and routing key are the same
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(queueName));
		final ConsumerSimpleMessageListenerContainer container = new ConsumerSimpleMessageListenerContainer();
		container.setConsumerTagStrategy(q -> consumerTag);
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);

		container.setErrorHandler(t -> {
			if (isStopping)
			{
				return;
			}
			logException(t, null);
			stop();
		});
		container.setMessageListener(this);
		container.startConsumers();

		log("Connected to AMQP Server. Waiting for messages!", // summary
				null, // text
				"queueName=" + queueName + ", consumerTag=" + consumerTag, // reference
				null); // exception
	}

	/**
	 * Import xml document
	 *
	 * @param xml xml document (as string) to be imported
	 */
	private void importXMLDocument(final String xml)
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

		final Mutable<Document> responseDOM = new Mutable<>(); // 03132

		Services.get(ITrxManager.class).run(trxName, trxName -> {
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
		});

	}

	public void stop()
	{
		isStopping = true;
		try
		{
			log.trace("Closing AMQP Connection!");
			if (connectionFactory != null)
			{
				connectionFactory.destroy();
				connectionFactory.stop();
				connectionFactory = null;
			}
		}
		finally
		{
			isStopping = false;

			// 02486 make sure that we inform 'replicationProcessor' so there is a change of being restarted
			replicationProcessor.setProcessRunning(false);
		}
	}

	private void logException(final Throwable e, final String messageText)
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

	@Override public void onMessage(Message message)
	{
		String text = null;
		try
		{
			text = new String(message.getBody());
			log.trace("Received message(text): \n{}", text);

			importXMLDocument(text);

			log("Imported Document", text, null, null);
		}
		catch (final RuntimeException e)
		{
			logException(e, text);
		}
		finally
		{
			// not sending reply
			if (StringUtils.isNotEmpty(message.getMessageProperties().getReplyTo()))
			{
				log.warn("Sending reply currently not supported with rabbitmq");
			}
		}
	}
}

