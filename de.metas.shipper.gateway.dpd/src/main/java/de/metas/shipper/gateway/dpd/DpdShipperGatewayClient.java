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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.metas.shipper.gateway.dpd.model.DpdClientConfig;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import lombok.Builder;
import lombok.NonNull;

public class DpdShipperGatewayClient implements ShipperGatewayClient
{
	private static final Logger logger = LoggerFactory.getLogger(DpdShipperGatewayClient.class);

	private final DpdClientConfig config;

	@Builder
	public DpdShipperGatewayClient(@NonNull final DpdClientConfig config)
	{
		this.config = config;
	}

	@Override
	public String getShipperGatewayId()
	{
		return DpdConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	@Deprecated
	public DeliveryOrder createDeliveryOrder(final DeliveryOrder draftDeliveryOrder) throws ShipperGatewayException
	{
		throw new ShipperGatewayException("(DRAFT) Delivery Orders shall never be created.");
	}

	@Override
	public DeliveryOrder completeDeliveryOrder(final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		return null;
	}

	@Override
	public List<PackageLabels> getPackageLabelsList(final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		return null;
	}
}
