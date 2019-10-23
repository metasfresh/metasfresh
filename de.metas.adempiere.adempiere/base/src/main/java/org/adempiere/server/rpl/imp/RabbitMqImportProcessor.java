package org.adempiere.server.rpl.imp;

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.server.rpl.IImportProcessor;
import org.adempiere.server.rpl.IReplicationProcessor;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IIMPProcessorDAO;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_IMP_ProcessorParameter;
import org.slf4j.Logger;

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

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

public class RabbitMqImportProcessor implements IImportProcessor
{

	protected Logger log = LogManager.getLogger(RabbitMqImportProcessor.class);

	private static final String PARAM_QUEUE_NAME = "queueName";

	private static final String PARAM_CONSUMER_TAG = "consumerTag";

	private static final String PARAM_EXCHANGE_NAME = "exchangeName";

	private static final String PARAM_IS_DURABLE_QUEUE = "isDurableQueue";

	private RabbitMqListener rabbitMqListener = null;

	@Override
	public void start(final @NonNull Properties ctx, final @NonNull IReplicationProcessor replicationProcessor, final String trxName) throws Exception
	{
		getLogger(Level.INFO).addLog("Starting {} ({})", replicationProcessor, replicationProcessor.getMImportProcessor());

		final I_IMP_Processor impProcessor = replicationProcessor.getMImportProcessor();
		final List<I_IMP_ProcessorParameter> processorParameters = Services.get(IIMPProcessorDAO.class).retrieveParameters(impProcessor, trxName);

		final String host = impProcessor.getHost();
		final int port = impProcessor.getPort();
		final String account = impProcessor.getAccount();
		final String password = impProcessor.getPasswordInfo();

		// mandatory parameters!
		String queueName = StringUtils.EMPTY;
		String consumerTag = StringUtils.EMPTY;
		String exchangeName = StringUtils.EMPTY;
		boolean isDurableQueue = true;

		for (final I_IMP_ProcessorParameter processorParameter : processorParameters)
		{
			final String parameterName = processorParameter.getValue();
			getLogger(Level.DEBUG).addLog("Parameters: {} = {}", parameterName, processorParameter.getParameterValue());

			if (parameterName.equals(PARAM_QUEUE_NAME))
			{
				queueName = processorParameter.getParameterValue();
			}
			else if (parameterName.equals(PARAM_CONSUMER_TAG))
			{
				consumerTag = processorParameter.getParameterValue();
			}
			else if (parameterName.equals(PARAM_EXCHANGE_NAME))
			{
				exchangeName = processorParameter.getParameterValue();
			}
			else if (parameterName.equals(PARAM_IS_DURABLE_QUEUE))
			{
				isDurableQueue = Boolean.parseBoolean(processorParameter.getParameterValue());
			}
		}

		if (StringUtils.isEmpty(queueName))
		{
			throw new AdempiereException(MessageFormat.format("Missing {0} with key '{1}'!", I_IMP_ProcessorParameter.Table_Name, PARAM_QUEUE_NAME));
		}

		if (StringUtils.isEmpty(exchangeName))
		{
			throw new AdempiereException(MessageFormat.format("Missing {0} with key '{1}'!", I_IMP_ProcessorParameter.Table_Name, PARAM_EXCHANGE_NAME));
		}

		if (StringUtils.isEmpty(consumerTag))
		{
			throw new AdempiereException(MessageFormat.format("Missing {0} with key '{1}'!", I_IMP_ProcessorParameter.Table_Name, PARAM_CONSUMER_TAG));
		}

		rabbitMqListener = new RabbitMqListener(ctx,
				replicationProcessor,
				host,
				port,
				consumerTag,
				queueName,
				account,
				password,
				trxName,
				exchangeName,
				isDurableQueue);

		rabbitMqListener.run();
		getLogger(Level.INFO).addLog("Listener started: {}", rabbitMqListener);
	}

	@Override
	public void stop()
	{

		if (rabbitMqListener != null)
		{
			rabbitMqListener.stop();
			getLogger(Level.INFO).addLog("Listener stopped: {}", rabbitMqListener);
		}
		rabbitMqListener = null;
	}

	@Override
	public void createInitialParameters(final @NonNull I_IMP_Processor processor)
	{
		final IIMPProcessorBL impProcessorBL = Services.get(IIMPProcessorBL.class);

		impProcessorBL.createParameter(processor,
				PARAM_QUEUE_NAME,
				"Name of AMQP Queue from where xml will be Imported",
				"Import Processor Parameter Description",
				"AMQP Import Processor Parameter Help",
				"ExampleQueue");
		impProcessorBL.createParameter(processor,
				PARAM_CONSUMER_TAG,
				"Cumsumer Tag",
				"Import Processor Parameter Description",
				"AMQP Import Processor Parameter Help",
				"exampleConsumerTag");
		impProcessorBL.createParameter(processor,
				PARAM_EXCHANGE_NAME,
				"Name of AMQP exchange from where xml will be imported",
				"Export Processor Parameter Description",
				"AMQP Export Processor Parameter Help",
				"ExampleExchange");
		impProcessorBL.createParameter(processor,
				PARAM_IS_DURABLE_QUEUE,
				"AMQP durable queue used for import",
				"Export Processor Parameter Description",
				"AMQP Export Processor Parameter Help",
				"true");
	}

	private ILoggable getLogger(@NonNull final Level level)
	{
		return Loggables.withLogger(log, level);
	}
}
