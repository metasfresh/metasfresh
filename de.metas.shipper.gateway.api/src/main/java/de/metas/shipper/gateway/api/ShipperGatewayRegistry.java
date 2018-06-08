package de.metas.shipper.gateway.api;

import java.util.List;
import java.util.Optional;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;

import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.api
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
public class ShipperGatewayRegistry
{
	private static final Logger logger = LoggerFactory.getLogger(ShipperGatewayRegistry.class);

	private final ImmutableMap<String, ShipperGatewayService> servicesByGatewayId;

	public ShipperGatewayRegistry(final Optional<List<ShipperGatewayService>> services)
	{
		if (services.isPresent())
		{
			servicesByGatewayId = services.get()
					.stream()
					.filter(Predicates.notNull())
					.collect(GuavaCollectors.toImmutableMapByKey(ShipperGatewayService::getShipperGatewayId));
		}
		else
		{
			servicesByGatewayId = ImmutableMap.of();
		}
		logger.info("Services: {}", servicesByGatewayId);
	}

	public boolean hasServiceSupport(@NonNull final String shipperGatewayId)
	{
		return servicesByGatewayId.get(shipperGatewayId) != null;
	}

	public ShipperGatewayService getShipperGatewayService(@NonNull final String shipperGatewayId)
	{
		final ShipperGatewayService service = servicesByGatewayId.get(shipperGatewayId);
		Check.assumeNotNull(service, "service shall exist for shipperGatewayId={}", shipperGatewayId);
		return service;
	}
}
