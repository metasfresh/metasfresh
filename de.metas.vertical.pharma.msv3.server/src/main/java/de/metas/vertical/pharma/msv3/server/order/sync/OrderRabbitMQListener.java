package de.metas.vertical.pharma.msv3.server.order.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.vertical.pharma.msv3.protocol.order.OrderCreateResponse;
import de.metas.vertical.pharma.msv3.server.order.OrderService;
import de.metas.vertical.pharma.msv3.server.peer.RabbitMQConfig;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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
public class OrderRabbitMQListener
{
	private final transient Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderService orderService;

	@RabbitListener(queues = RabbitMQConfig.QUEUENAME_CreateOrderResponseEvents)
	public void onEvent(@NonNull final OrderCreateResponse response)
	{
		try
		{
			orderService.confirmOrderSavedOnPeerServer(response);
		}
		catch (final Exception ex)
		{
			logger.error("Failed processing: {}", response, ex);
		}
	}
}
