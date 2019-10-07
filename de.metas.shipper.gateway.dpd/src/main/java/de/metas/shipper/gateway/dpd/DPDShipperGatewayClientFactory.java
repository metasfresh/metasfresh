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

import org.springframework.stereotype.Service;

import de.metas.shipper.gateway.dpd.model.DPDClientConfig;
import de.metas.shipper.gateway.dpd.model.DPDClientConfigRepository;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.ShipperGatewayClientFactory;

@Service
public class DPDShipperGatewayClientFactory implements ShipperGatewayClientFactory
{
	private final DPDClientConfigRepository configRepo;

	public DPDShipperGatewayClientFactory(final DPDClientConfigRepository configRepo)
	{
		this.configRepo = configRepo;
	}

	@Override
	public String getShipperGatewayId()
	{
		return DPDConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public ShipperGatewayClient newClientForShipperId(final int shipperId)
	{
		DPDClientConfig config = configRepo.getByShipperId(shipperId);
		return DPDShipperGatewayClient.builder()
				.config(config)
				.build();
	}
}
