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
package org.adempiere.server.rpl.exp;

import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.process.rpl.IExportProcessor;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.I_EXP_ProcessorParameter;
import org.compiere.model.MEXPProcessor;
import org.compiere.model.X_EXP_ProcessorParameter;
import org.compiere.util.Trx;
import org.slf4j.Logger;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

public class RabbitMqExportProcessor implements IExportProcessor
{

	private static final String EXCHANGE_NAME_PARAMETER = "exchangeName";

	private static final String ROUTING_KEY_PARAMETER = "routingKey";

	private static final String IS_DURABLE_QUEUE_PARAMETER = "isDurableQueue";

	protected Logger log = LogManager.getLogger(getClass());

	@Override
	public void process(final @NonNull Properties ctx, final @NonNull MEXPProcessor expProcessor,
			final @NonNull Document document, final Trx trx)
			throws Exception
	{
		String host = expProcessor.getHost();
		int port = expProcessor.getPort();
		String account = expProcessor.getAccount();
		String password = expProcessor.getPasswordInfo();
		String exchangeName = StringUtils.EMPTY;
		String routingKey = StringUtils.EMPTY;
		boolean isDurableQueue = true;

		// Read all processor parameters and set them!
		I_EXP_ProcessorParameter[] processorParameters = expProcessor.getEXP_ProcessorParameters();
		for (I_EXP_ProcessorParameter processorParameter : processorParameters)
		{
			log.info("ProcesParameter: Value = {} ; ParameterValue = {}",
					processorParameter.getValue(),
					processorParameter.getParameterValue());
			if (processorParameter.getValue().equals(EXCHANGE_NAME_PARAMETER))
			{
				exchangeName = processorParameter.getParameterValue();
			}
			else if (processorParameter.getValue().equals(ROUTING_KEY_PARAMETER))
			{
				routingKey = processorParameter.getParameterValue();
			}
			else if (processorParameter.getValue().equals(IS_DURABLE_QUEUE_PARAMETER))
			{
				isDurableQueue = Boolean.parseBoolean(processorParameter.getParameterValue());
			}
		}

		if (StringUtils.isEmpty(exchangeName))
		{
			throw new AdempiereException("Missing " + X_EXP_ProcessorParameter.Table_Name + " with key 'exchangeName'!");
		}

		if (StringUtils.isEmpty(routingKey))
		{
			throw new AdempiereException("Missing " + X_EXP_ProcessorParameter.Table_Name + " with key 'routingKey'!");
		}

		// Construct Transformer Factory and Transformer
		TransformerFactory tranFactory = TransformerFactory.newInstance();
		Transformer aTransformer = tranFactory.newTransformer();
		aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		Source src = new DOMSource(document);

		// =================================== Write to String
		Writer writer = new StringWriter();
		Result dest2 = new StreamResult(writer);
		aTransformer.transform(src, dest2);

		sendAMQPMessage(host, port, writer.toString(), exchangeName, routingKey, account, password, isDurableQueue);

	}

	private void sendAMQPMessage(final @NonNull String host, final int port, final @NonNull String msg, final @NonNull String exchangeName,
			final @NonNull String routingKey, final @NonNull String userName, final @NonNull String password, final boolean isDurableQueue)
	{

		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
		if (userName != null && password != null)
		{
			connectionFactory.setUsername(userName);
			connectionFactory.setPassword(password);
		}
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setRoutingKey(routingKey);
		template.setExchange(exchangeName);
		RabbitAdmin admin = new RabbitAdmin(template.getConnectionFactory());
		Queue queue = new Queue(routingKey, isDurableQueue);
		TopicExchange exchange = new TopicExchange(exchangeName, isDurableQueue, false);
		admin.declareExchange(exchange);
		admin.declareQueue(queue);
		// queue name and routing key are the same
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
		template.convertAndSend(msg);
		log.info("AMQP Message sent!");
		connectionFactory.destroy();
	}

	@Override
	public void createInitialParameters(final @NonNull MEXPProcessor processor)
	{
		processor.createParameter(
				EXCHANGE_NAME_PARAMETER,
				"Name of AMQP exchange from where xml will be exported",
				"Export Processor Parameter Description",
				"AMQP Export Processor Parameter Help",
				"ExampleExchange");
		processor.createParameter(
				ROUTING_KEY_PARAMETER,
				"AMQP routing key for the messages that will be exported",
				"Export Processor Parameter Description",
				"AMQP Export Processor Parameter Help",
				"ExpRoutingKey");
		processor.createParameter(
				IS_DURABLE_QUEUE_PARAMETER,
				"AMQP durable queue used for export",
				"Export Processor Parameter Description",
				"AMQP Export Processor Parameter Help",
				"true");

	}
}
