/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.edi;

import com.google.common.collect.ImmutableMap;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import de.metas.CommandLineParser;
import de.metas.ServerBoot;
import de.metas.cucumber.stepdefs.edi.impprocessor.IMP_Processor_StepDefData;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_IMP_ProcessorParameter;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EDIToMetasfreshRabbitMQ_StepDef
{
	private static final Logger logger = LogManager.getLogger(EDIToMetasfreshRabbitMQ_StepDef.class);
	private static final String EXCHANGE_NAME = "exchangeName";
	private static final String QUEUE_NAME = "queueName";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CachingConnectionFactory rabbitMQToMetasfreshConnectionFactory;

	private final IMP_Processor_StepDefData impProcessorStepDefData;

	public EDIToMetasfreshRabbitMQ_StepDef(
			@NonNull final IMP_Processor_StepDefData impProcessorStepDefData)
	{
		this.impProcessorStepDefData = impProcessorStepDefData;

		final ServerBoot serverBoot = SpringContextHolder.instance.getBean(ServerBoot.class);
		final CommandLineParser.CommandLineOptions commandLineOptions = serverBoot.getCommandLineOptions();
		assertThat(commandLineOptions.getRabbitPort()).isNotNull(); // guard

		rabbitMQToMetasfreshConnectionFactory = new CachingConnectionFactory();
		rabbitMQToMetasfreshConnectionFactory.setHost(commandLineOptions.getRabbitHost());
		rabbitMQToMetasfreshConnectionFactory.setPort(commandLineOptions.getRabbitPort());
		rabbitMQToMetasfreshConnectionFactory.setUsername(commandLineOptions.getRabbitUser());
		rabbitMQToMetasfreshConnectionFactory.setPassword(commandLineOptions.getRabbitPassword());
	}

	@And("^send message to RabbitMQ queue defined by:(.*)$")
	public void sendMessage(@NonNull final String impProcessorIdentifier, @NonNull final String message) throws IOException, TimeoutException
	{
		final I_IMP_Processor impProcessor = impProcessorStepDefData.get(impProcessorIdentifier);
		assertThat(impProcessor).isNotNull();

		final ImmutableMap<String, String> impProcessorParameters = retrieveParams(impProcessor);
		final String exchangeName = impProcessorParameters.get(EXCHANGE_NAME);
		final String queueName = impProcessorParameters.get(QUEUE_NAME);

		try (final Connection connection = rabbitMQToMetasfreshConnectionFactory.getRabbitConnectionFactory().newConnection())
		{
			final AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.contentEncoding(StandardCharsets.UTF_8.displayName())
					.build();

			final Channel channel = connection.createChannel();

			channel.basicPublish(exchangeName, queueName, properties, message.getBytes(StandardCharsets.UTF_8));
			logger.info("Sent on queue '" + queueName + "': message= '" + message + "'");
		}
	}

	@NonNull
	private ImmutableMap<String, String> retrieveParams(@NonNull final I_IMP_Processor impProcessor)
	{
		return queryBL.createQueryBuilder(I_IMP_ProcessorParameter.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_IMP_ProcessorParameter.COLUMNNAME_IMP_Processor_ID, impProcessor.getIMP_Processor_ID())
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(I_IMP_ProcessorParameter::getValue, I_IMP_ProcessorParameter::getParameterValue));
	}
}
