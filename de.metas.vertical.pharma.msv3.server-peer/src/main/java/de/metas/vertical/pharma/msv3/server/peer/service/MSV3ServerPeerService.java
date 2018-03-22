package de.metas.vertical.pharma.msv3.server.peer.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import de.metas.vertical.pharma.msv3.server.peer.RabbitMQConfig;
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

	private final RabbitTemplate rabbitTemplate;

	private final String nodeId = UUID.randomUUID().toString();

	public MSV3ServerPeerService(@NonNull final RabbitTemplate rabbitTemplate)
	{
		this.rabbitTemplate = rabbitTemplate;
	}

	public void requestAllUpdates()
	{
		rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUENAME_MSV3ServerRequests, MSV3ServerRequest.requestAll(nodeId));
		logger.info("Requested all data from MSV3 server peer");
	}

	public void publishUserChangedEvent(@NonNull final MSV3UserChangedEvent event)
	{
		rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUENAME_UserChangedEvents, event);
	}

	public void publishStockAvailabilityUpdatedEvent(@NonNull final MSV3StockAvailabilityUpdatedEvent event)
	{
		rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUENAME_StockAvailabilityUpdatedEvent, event);
	}
}
