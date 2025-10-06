package de.metas.shipper.gateway.commons;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipping.ShipperGatewayId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.shipper.gateway.commons
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
public class ShipperGatewayServicesRegistry
{
	@NonNull private static final Logger logger = LogManager.getLogger(ShipperGatewayServicesRegistry.class);

	@NonNull private final ImmutableMap<ShipperGatewayId, DeliveryOrderService> servicesByGatewayId;
	@Nullable private final DeliveryOrderService defaultDeliveryOrderService;

	public ShipperGatewayServicesRegistry(
			@NonNull final Optional<List<DeliveryOrderService>> deliveryOrderServices)
	{
		this.servicesByGatewayId = deliveryOrderServices.orElse(ImmutableList.of())
				.stream()
				.filter(service -> service != null && service.getShipperGatewayId() != null)
				.collect(GuavaCollectors.toImmutableMapByKey(DeliveryOrderService::getShipperGatewayId));
		logger.info("servicesByGatewayId: {}", this.servicesByGatewayId);

		this.defaultDeliveryOrderService = deliveryOrderServices.orElse(ImmutableList.of())
				.stream()
				.filter(service -> service != null && service.getShipperGatewayId() == null)
				.collect(GuavaCollectors.noneOrSingleElementOrThrow(() -> new AdempiereException("One and only one service can be default: " + deliveryOrderServices)));
		logger.info("defaultDeliveryOrderService: {}", this.defaultDeliveryOrderService);
	}

	public DeliveryOrderService getDeliveryOrderService(@NonNull final ShipperGatewayId shipperGatewayId)
	{
		final DeliveryOrderService deliveryOrderService = servicesByGatewayId.getOrDefault(shipperGatewayId, defaultDeliveryOrderService);
		return Check.assumeNotNull(deliveryOrderService, "deliveryOrderService shall exist for shipperGatewayId={}", shipperGatewayId);
	}

	public boolean hasServiceSupport(@Nullable final ShipperGatewayId shipperGatewayId)
	{
		return defaultDeliveryOrderService != null || servicesByGatewayId.containsKey(shipperGatewayId);
	}
}
