/*
 * #%L
 * de.metas.shipper.gateway.dhl
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.shipper.gateway.dhl;

import de.metas.shipper.gateway.dhl.logger.DhlDatabaseClientLogger;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import de.metas.shipper.gateway.dhl.model.DhlClientConfigRepository;
import de.metas.shipper.gateway.dhl.oauth2.CustomOAuth2TokenRetriever;
import de.metas.shipper.gateway.dhl.oauth2.OAuth2AuthorizationInterceptor;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.ShipperGatewayClientFactory;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DhlShipperGatewayClientFactory implements ShipperGatewayClientFactory
{
	private final DhlClientConfigRepository configRepo; // Holds the DHL client config

	public DhlShipperGatewayClientFactory(@NonNull final DhlClientConfigRepository configRepo)
	{
		this.configRepo = configRepo;
	}

	@Override
	public String getShipperGatewayId()
	{
		return DhlConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		final DhlClientConfig config = configRepo.getByShipperId(shipperId);

		// Create the RestTemplate configured with OAuth2
		final RestTemplate restTemplate = buildOAuth2RestTemplate(config);

		// Return the DhlShipperGatewayClient instance
		return DhlShipperGatewayClient.builder()
				.config(config)
				.databaseLogger(DhlDatabaseClientLogger.instance)
				.restTemplate(restTemplate)
				.build();
	}

	/**
	 * Creates a RestTemplate configured to authenticate with OAuth2 using the provided DhlClientConfig.
	 */
	private RestTemplate buildOAuth2RestTemplate(final DhlClientConfig clientConfig)
	{
		final RestTemplate restTemplate = new RestTemplate();

		// Add the CustomOAuth2TokenRetriever to handle DHL's OAuth2 flow
		final CustomOAuth2TokenRetriever tokenRetriever = new CustomOAuth2TokenRetriever(new RestTemplate());

		// Add an interceptor that dynamically retrieves and injects the `Authorization` header
		restTemplate.getInterceptors().add(
				new OAuth2AuthorizationInterceptor(tokenRetriever, clientConfig)
		);

		return restTemplate;
	}
}