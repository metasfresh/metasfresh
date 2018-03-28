package de.metas.vertical.pharma.msv3.server.peer.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateRequest;
import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateResponse;
import de.metas.vertical.pharma.msv3.server.peer.RabbitMQConfig;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3PeerAuthToken;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ServerRequest;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailabilityUpdatedEvent;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer
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

@Service
public class MSV3ServerPeerService
{
	private static final Logger logger = LoggerFactory.getLogger(MSV3ServerPeerService.class);

	private final AmqpTemplate amqpTemplate;
	private final MSV3PeerAuthToken msv3PeerAuthToken;

	public MSV3ServerPeerService(
			final Optional<AmqpTemplate> amqpTemplate,
			final Optional<MSV3PeerAuthToken> msv3PeerAuthToken)
	{
		this.amqpTemplate = amqpTemplate.orElse(null); // tolerate the case when AMQP is not enabled
		this.msv3PeerAuthToken = msv3PeerAuthToken.orElse(null);
	}

	private void convertAndSend(final String routingKey, final Object message)
	{
		if (amqpTemplate == null)
		{
			throw new IllegalStateException("AMQP is not enabled");
		}
		amqpTemplate.convertAndSend(routingKey, message, this::messagePostProcess);
	}

	private Message messagePostProcess(final Message message)
	{
		if (msv3PeerAuthToken != null)
		{
			message.getMessageProperties().getHeaders().put(MSV3PeerAuthToken.NAME, msv3PeerAuthToken.toJson());
		}

		return message;
	}

	public void requestAllUpdates()
	{
		convertAndSend(RabbitMQConfig.QUEUENAME_MSV3ServerRequests, MSV3ServerRequest.requestAll());
		logger.info("Requested all data from MSV3 server peer");
	}

	public void publishUserChangedEvent(@NonNull final MSV3UserChangedEvent event)
	{
		convertAndSend(RabbitMQConfig.QUEUENAME_UserChangedEvents, event);
	}

	public void publishStockAvailabilityUpdatedEvent(@NonNull final MSV3StockAvailabilityUpdatedEvent event)
	{
		convertAndSend(RabbitMQConfig.QUEUENAME_StockAvailabilityUpdatedEvent, event);
	}

	public void publishOrderCreateRequest(final OrderCreateRequest request)
	{
		convertAndSend(RabbitMQConfig.QUEUENAME_CreateOrderRequestEvents, request);
	}

	public void publishOrderCreateResponse(@NonNull final OrderCreateResponse response)
	{
		convertAndSend(RabbitMQConfig.QUEUENAME_CreateOrderResponseEvents, response);
	}

}
