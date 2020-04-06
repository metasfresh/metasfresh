package de.metas.shipper.gateway.commons;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.DraftDeliveryOrderCreator;
import de.metas.shipper.gateway.spi.ShipperGatewayClientFactory;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;

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
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final ImmutableMap<String, DeliveryOrderRepository> repositoriesByGatewayId;
	private final ImmutableMap<String, ShipperGatewayClientFactory> clientFactoriesByGatewayId;
	private final ImmutableMap<String, DraftDeliveryOrderCreator> draftDeliveryOrderCreatorsByGatewayId;

	public ShipperGatewayServicesRegistry(
			@NonNull final Optional<List<DeliveryOrderRepository>> deliveryOrderRepositories,
			@NonNull final Optional<List<ShipperGatewayClientFactory>> shipperGatewayClientFactories,
			@NonNull final Optional<List<DraftDeliveryOrderCreator>> services)
	{
		repositoriesByGatewayId = deliveryOrderRepositories.orElse(ImmutableList.of())
				.stream()
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableMapByKey(DeliveryOrderRepository::getShipperGatewayId));

		clientFactoriesByGatewayId = shipperGatewayClientFactories.orElse(ImmutableList.of())
				.stream()
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableMapByKey(ShipperGatewayClientFactory::getShipperGatewayId));

		draftDeliveryOrderCreatorsByGatewayId = services.orElse(ImmutableList.of())
				.stream()
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableMapByKey(DraftDeliveryOrderCreator::getShipperGatewayId));

		logger.info("deliveryOrderRepositories: {}", repositoriesByGatewayId);
	}

	public DeliveryOrderRepository getDeliveryOrderRepository(@NonNull final String shipperGatewayId)
	{
		final DeliveryOrderRepository deliveryOrderRepository = repositoriesByGatewayId.get(shipperGatewayId);
		return Check.assumeNotNull(deliveryOrderRepository, "deliveryOrderRepository shall exist for shipperGatewayId={}", shipperGatewayId);
	}

	public ShipperGatewayClientFactory getClientFactory(@NonNull final String shipperGatewayId)
	{
		final ShipperGatewayClientFactory shipperGatewayClientFactory = clientFactoriesByGatewayId.get(shipperGatewayId);
		return Check.assumeNotNull(shipperGatewayClientFactory, "shipperGatewayClientFactory shall exist for shipperGatewayId={}", shipperGatewayId);
	}

	public DraftDeliveryOrderCreator getShipperGatewayService(@NonNull final String shipperGatewayId)
	{
		final DraftDeliveryOrderCreator service = draftDeliveryOrderCreatorsByGatewayId.get(shipperGatewayId);
		return Check.assumeNotNull(service, "service shall exist for shipperGatewayId={}", shipperGatewayId);
	}

	public boolean hasServiceSupport(@Nullable final String shipperGatewayId)
	{
		if(Check.isEmpty(shipperGatewayId,true))
		{
			return false;
		}
		return draftDeliveryOrderCreatorsByGatewayId.containsKey(shipperGatewayId);
	}
}
