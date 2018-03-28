package de.metas.vertical.pharma.msv3.server.security.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.vertical.pharma.msv3.server.peer.RabbitMQConfig;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3UserChangedEvent;
import de.metas.vertical.pharma.msv3.server.security.MSV3ServerAuthenticationService;

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
public class UserSyncRabbitMQListener
{
	private static final Logger logger = LoggerFactory.getLogger(UserSyncRabbitMQListener.class);

	@Autowired
	private MSV3ServerAuthenticationService authService;

	@RabbitListener(queues = RabbitMQConfig.QUEUENAME_UserChangedEvents)
	public void onUserEvent(final MSV3UserChangedEvent event)
	{
		try
		{
			authService.handleEvent(event);
		}
		catch (Exception ex)
		{
			logger.warn("Failed handling event: {}", event, ex);
		}
	}
}
