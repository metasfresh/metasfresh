/*
 * #%L
 * de.metas.shipper.gateway.dpd
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

package de.metas.shipper.gateway.dpd;

import de.metas.shipper.gateway.api.ShipperGatewayId;
import de.metas.shipper.gateway.dpd.logger.DpdDatabaseClientLogger;
import de.metas.shipper.gateway.dpd.model.DpdClientConfig;
import de.metas.shipper.gateway.dpd.model.DpdClientConfigRepository;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.ShipperGatewayClientFactory;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class DpdShipperGatewayClientFactory implements ShipperGatewayClientFactory
{
	private final DpdClientConfigRepository configRepo;

	public DpdShipperGatewayClientFactory(final DpdClientConfigRepository configRepo)
	{
		this.configRepo = configRepo;
	}

	@Override
	public ShipperGatewayId getShipperGatewayId()
	{
		return DpdConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		final DpdClientConfig config = configRepo.getByShipperId(shipperId);
		return DpdShipperGatewayClient.builder()
				.config(config)
				.databaseLogger(DpdDatabaseClientLogger.instance)
				.build();
	}
}
