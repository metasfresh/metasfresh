package org.adempiere.server.rpl.imp;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
import static de.metas.common.util.CoalesceUtil.firstNotEmptyTrimmed;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.process.rpl.XMLHelper;
import org.adempiere.server.rpl.IReplicationProcessor;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IImportHelper;
import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.Mutable;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.I_IMP_Processor;
import org.slf4j.Logger;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.w3c.dom.Document;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

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

public class RabbitMqListener implements MessageListener
{
	private CachingConnectionFactory connectionFactory;

	private final String host;

	private final int port;

	private final Properties ctx;

	private final String trxName;

	private final String queueName;

	private final boolean isDurableQueue;

	private final String exchangeName;

	private final IReplicationProcessor replicationProcessor;

	private final Logger logger = LogManager.getLogger(RabbitMqListener.class);

	private final String consumerTag;

	private final String userName;

	private final String password;

	private boolean isStopping = false;

	private final String listenerReference;

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
		this.host = firstNotEmptyTrimmed(host, "localhost");
		this.port = firstGreaterThanZero(port, 5672);

		this.queueName = queueName;

		this.ctx = ctx;
		this.trxName = trxName;
		this.replicationProcessor = replicationProcessor;
		this.consumerTag = consumerTag;
		this.userName = userName;
		this.password = password;
		this.exchangeName = exchangeName;
		this.isDurableQueue = isDurableQueue;

		this.listenerReference = "queueName=" + queueName + ", consumerTag=" + consumerTag;
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
					+ "\n Processor: " + replicationProcessor, e);
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

		final RabbitAdmin admin = new RabbitAdmin(connectionFactory);
		final Queue queue = new Queue(queueName, isDurableQueue);
		final TopicExchange exchange = new TopicExchange(exchangeName, isDurableQueue, false);

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

		// set logger
		log("Connected to AMQP Server. Waiting for messages!", // summary
				null, // text
				listenerReference, // reference
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
			Loggables.withLogger(logger, Level.TRACE).addLog("Closing AMQP Connection!");

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
		final I_IMP_Processor impProcessor = replicationProcessor.getMImportProcessor();
		Services.get(IIMPProcessorBL.class).createLog(impProcessor, summary, text, reference, error);
	}

	@Override
	public void onMessage(@NonNull final Message message)
	{
		String text = null;

		final String messageReference = "onMessage-startAt-millis-" + Long.toString(SystemTime.millis());
		try (final IAutoCloseable closable = setupLoggable(messageReference))
		{
			text = extractMessageBodyAsString(message);

			Loggables.withLogger(logger, Level.TRACE)
					.addLog("Received message(text): \n{}", text);

			importXMLDocument(text);

			log("Imported Document", text, null, null); // loggable can't store the message-text for us

			// not sending reply
			if (StringUtils.isNotEmpty(message.getMessageProperties().getReplyTo()))
			{
				Loggables.withLogger(logger, Level.WARN)
						.addLog("Sending reply currently not supported with RabbitMQ");
			}
		}
		catch (final RuntimeException e)
		{
			logException(e, text);
		}
	}

	private String extractMessageBodyAsString(@NonNull final Message message)
	{
		final String encoding = message.getMessageProperties().getContentEncoding();
		if (Check.isEmpty(encoding))
		{
			Loggables.withLogger(logger, Level.WARN)
					.addLog("Incoming RabbitMQ message lacks content encoding info; assuming UTF-8; messageId={}", message.getMessageProperties().getMessageId());

			new String(message.getBody(), StandardCharsets.UTF_8);
		}

		try
		{
			return new String(message.getBody(), encoding);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new AdempiereException("Incoming RabbitMQ message has unsupportred encoding='" + encoding + "'; messageId=" + message.getMessageProperties().getMessageId(), e);
		}
	}

	private IAutoCloseable setupLoggable(@NonNull final String reference)
	{
		final IIMPProcessorBL impProcessorBL = Services.get(IIMPProcessorBL.class);
		final I_IMP_Processor importProcessor = replicationProcessor.getMImportProcessor();
		return impProcessorBL.setupTemporaryLoggable(importProcessor, reference);
	}
}
