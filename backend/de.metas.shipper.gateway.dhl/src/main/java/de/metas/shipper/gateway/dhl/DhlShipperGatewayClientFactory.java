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
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.ShipperGatewayClientFactory;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class DhlShipperGatewayClientFactory implements ShipperGatewayClientFactory
{
	private final DhlClientConfigRepository configRepo;

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
		return DhlShipperGatewayClient.builder()
				.config(config)
				.databaseLogger(DhlDatabaseClientLogger.instance)
				.build();
	}
}
