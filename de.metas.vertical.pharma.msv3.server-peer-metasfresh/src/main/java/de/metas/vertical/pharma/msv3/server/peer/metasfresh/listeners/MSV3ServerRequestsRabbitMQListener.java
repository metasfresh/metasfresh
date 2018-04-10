package de.metas.vertical.pharma.msv3.server.peer.metasfresh.listeners;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.util.web.security.UserAuthTokenService;
import de.metas.vertical.pharma.msv3.server.peer.RabbitMQConfig;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.services.MSV3CustomerConfigService;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.services.MSV3StockAvailabilityService;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3PeerAuthToken;
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
@Profile(Profiles.PROFILE_App)
public class MSV3ServerRequestsRabbitMQListener
{
	private static final Logger logger = LogManager.getLogger(MSV3ServerRequestsRabbitMQListener.class);

	@Autowired
	private UserAuthTokenService authService;
	@Autowired
	private MSV3CustomerConfigService customerConfigService;
	@Autowired
	private MSV3StockAvailabilityService stockAvailabilityService;

	@RabbitListener(queues = RabbitMQConfig.QUEUENAME_MSV3ServerRequests)
	public void onRequest(@Payload final MSV3ServerRequest request, @Header(MSV3PeerAuthToken.NAME) final MSV3PeerAuthToken authToken)
	{
		try
		{
			authService.run(authToken::getValueAsString, () -> process(request));
		}
		catch (final Exception ex)
		{
			logger.error("Failed processing: {}", request, ex);
		}
	}

	private void process(final MSV3ServerRequest request)
	{
		if (request.isRequestAllUsers())
		{
			customerConfigService.publishAllConfig();
		}
		if (request.isRequestAllStockAvailabilities())
		{
			stockAvailabilityService.publishAll();
		}
	}
}
