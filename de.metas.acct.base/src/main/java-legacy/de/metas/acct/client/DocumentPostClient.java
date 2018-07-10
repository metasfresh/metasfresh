package de.metas.acct.client;

import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;

import de.metas.acct.config.AMQPConfiguration;
import de.metas.acct.handler.DocumentPostRequest;
import de.metas.acct.handler.DocumentPostResponse;
import de.metas.logging.LogManager;
import lombok.NonNull;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class DocumentPostClient
{
	private static final Logger logger = LogManager.getLogger(DocumentPostClient.class);

	private final AmqpTemplate amqpTemplate;

	public DocumentPostClient(@NonNull final AmqpTemplate amqpTemplate)
	{
		this.amqpTemplate = amqpTemplate;
		logger.debug("Created {} using {}", DocumentPostClient.class, amqpTemplate);
	}

	public DocumentPostResponse postAndGetResponse(@NonNull final DocumentPostRequest request)
	{

		DocumentPostRequest requestEffective = request.withResponseRequired();
		logger.trace("Sending/receive {} to exchange={}, routingKey={}", requestEffective, AMQPConfiguration.EXCHANGE_NAME, AMQPConfiguration.ROUTING_KEY);

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final DocumentPostResponse response = (DocumentPostResponse)amqpTemplate.convertSendAndReceive(
				AMQPConfiguration.EXCHANGE_NAME,
				AMQPConfiguration.ROUTING_KEY,
				requestEffective);
		logger.trace("Got response: {} in {}", response, stopwatch);

		return response;
	}

	public void post(@NonNull final DocumentPostRequest request)
	{
		final DocumentPostRequest requestEffective = request.withResponseNotRequired();
		logger.trace("Sending {} to exchange={}, routingKey={}", requestEffective, AMQPConfiguration.EXCHANGE_NAME, AMQPConfiguration.ROUTING_KEY);

		amqpTemplate.convertAndSend(
				AMQPConfiguration.EXCHANGE_NAME,
				AMQPConfiguration.ROUTING_KEY,
				requestEffective);
	}
}
