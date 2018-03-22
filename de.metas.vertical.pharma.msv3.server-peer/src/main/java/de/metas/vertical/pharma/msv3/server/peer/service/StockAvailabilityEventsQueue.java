package de.metas.vertical.pharma.msv3.server.peer.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import de.metas.vertical.pharma.msv3.server.peer.RabbitMQConfig;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3StockAvailabilityUpdatedEvent;
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
public class StockAvailabilityEventsQueue
{
	private final RabbitTemplate rabbitTemplate;

	public StockAvailabilityEventsQueue(@NonNull final RabbitTemplate rabbitTemplate)
	{
		this.rabbitTemplate = rabbitTemplate;
	}

	public void publish(@NonNull final MSV3StockAvailabilityUpdatedEvent event)
	{
		rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUENAME_StockAvailabilityUpdatedEvent, event);
	}

}
