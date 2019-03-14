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

/**
 * Send AMQP Messages
 */
public class RabbitMqExportProcessor implements IExportProcessor
{

	/**
	 * Name of the exchange name parameter
	 */
	private static final String EXCHANGE_NAME_PARAMETER = "exchangeName";
	/**
	 * Name of the routing key parameter
	 */
	private static final String ROUTING_KEY_PARAMETER = "routingKey";
	/**
	 * Name of the is durable queue parameter
	 */
	private static final String IS_DURABLE_QUEUE_PARAMETER = "isDurableQueue";
	/**
	 * Logger
	 */
	protected Logger log = LogManager.getLogger(getClass());

	@Override
	public void process(Properties ctx, MEXPProcessor expProcessor, Document document, Trx trx)
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
		if (processorParameters != null && processorParameters.length > 0)
		{
			for (I_EXP_ProcessorParameter processorParameter : processorParameters)
			{
				log.info("ProcesParameter          Value = " + processorParameter.getValue());
				log.info("ProcesParameter ParameterValue = " + processorParameter.getParameterValue());
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
		}

		if (exchangeName == null || exchangeName.length() == 0)
		{
			throw new Exception("Missing " + X_EXP_ProcessorParameter.Table_Name + " with key 'exchangeName'!");
		}

		if (routingKey == null || routingKey.length() == 0)
		{
			throw new Exception("Missing " + X_EXP_ProcessorParameter.Table_Name + " with key 'routingKey'!");
		}

		// Construct Transformer Factory and Transformer
		TransformerFactory tranFactory = TransformerFactory.newInstance();
		String jVersion = System.getProperty("java.version");
		if (jVersion.startsWith("1.5.0"))
			tranFactory.setAttribute("indent-number", 1);

		Transformer aTransformer = tranFactory.newTransformer();
		aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		Source src = new DOMSource(document);

		// =================================== Write to String
		Writer writer = new StringWriter();
		Result dest2 = new StreamResult(writer);
		aTransformer.transform(src, dest2);

		sendJMSMessage(host, port, writer.toString(), exchangeName, routingKey, account, password, isDurableQueue);

	}

	/**
	 * Send the message to an AMQP exchange
	 *
	 * @param host           the host of the AMQP server
	 * @param port           the port
	 * @param msg            the message to send
	 * @param exchangeName       the exchange
	 * @param routingKey     the routing key of the exchange
	 * @param userName       the username
	 * @param password       the password
	 * @param isDurableQueue true if the queue is durable
	 */
	private void sendJMSMessage(String host, int port, String msg, String exchangeName, String routingKey
			, String userName, String password, boolean isDurableQueue)
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
		connectionFactory.stop();

	}

	@Override
	public void createInitialParameters(MEXPProcessor processor)
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
