package de.metas.vertical.pharma.msv3.server.peer.metasfresh.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.vertical.pharma.msv3.server.peer.RabbitMQConfig;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.services.MSV3CustomerConfigService;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3ServerRequest;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
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
public class MSV3ServerRequestsRabbitMQListener
{
	@Autowired
	private MSV3CustomerConfigService customerConfigService;

	@RabbitListener(queues = RabbitMQConfig.QUEUENAME_MSV3ServerRequests)
	public void onRequest(final MSV3ServerRequest request)
	{
		if (request.isRequestAllUsers())
		{
			customerConfigService.publishAllConfig();
		}
		if (request.isRequestAllStockAvailabilities())
		{
			// TODO
		}
	}
}
