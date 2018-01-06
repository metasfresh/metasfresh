package de.metas.shipper.gateway.go;

import org.adempiere.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * #%L
 * de.metas.shipper.go
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Configuration
public class GOConfiguration
{
	private static final Logger logger = LoggerFactory.getLogger(GOConfiguration.class);

	@Value("${de.metas.shipper.go.url:}")
	private String url;
	@Value("${de.metas.shipper.go.auth.username:}")
	private String authUsername;
	@Value("${de.metas.shipper.go.auth.password:}")
	private String authPassword;
	@Value("${de.metas.shipper.go.request.username:}")
	private String requestUsername;
	@Value("${de.metas.shipper.go.request.senderId:}")
	private String requestSenderId;

	public boolean isEnabled()
	{
		return !Check.isEmpty(url, true);
	}

	@Bean
	public GOClient goClient() throws Exception
	{
		if (!isEnabled())
		{
			logger.info("GO not configured. Skip inializing {}", GOClient.class);
			return null;
		}

		final GOClient client = GOClient.builder()
				.url(url)
				.authUsername(authUsername)
				.authPassword(authPassword)
				.requestUsername(requestUsername)
				.requestSenderId(requestSenderId)
				.build();
		logger.info("GO Client initialized: {}", client);
		return client;
	}

	@Bean
	public GOShipperGatewayService goShipperGatewayService(final GODeliveryOrderRepository deliveryOrderRepository)
	{
		if (!isEnabled())
		{
			logger.info("GO not configured. Skip inializing {}", GOShipperGatewayService.class);
			return null;
		}

		return new GOShipperGatewayService(deliveryOrderRepository);
	}
}
