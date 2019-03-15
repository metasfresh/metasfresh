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
import lombok.NonNull;
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
import org.w3c.dom.Document;

import java.util.Properties;

public class RabbitMqListener implements MessageListener
{

	private CachingConnectionFactory connectionFactory;

	private String host = "localhost";

	private int port = 5672;

	private Properties ctx;

	private String trxName;

	private String queueName;

	private boolean isDurableQueue;

	private String exchangeName;

	private IReplicationProcessor replicationProcessor;

	protected Logger log = LogManager.getLogger(RabbitMqListener.class);

	private String consumerTag;

	private String userName;

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
			connectionFactory.setPassword(password);
		}
		RabbitAdmin admin = new RabbitAdmin(connectionFactory);
		Queue queue = new Queue(queueName, isDurableQueue);
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

	private void importXMLDocument(final @NonNull String xml)
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
				throw AdempiereException.wrapIfNeeded(e);
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

