package de.metas.shipper.gateway.spi;

import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipping.ShipperGatewayId;
import lombok.NonNull;

import java.util.List;

/*
 * #%L
 * de.metas.shipper.gateway.api
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

public interface ShipperGatewayClient
{
	@NonNull
	ShipperGatewayId getShipperGatewayId();

	/**
	 * Effectively place the given order on the remote endpoint.
	 */
	@NonNull
	DeliveryOrder completeDeliveryOrder(@NonNull DeliveryOrder deliveryOrder) throws ShipperGatewayException;

	@NonNull
	List<PackageLabels> getPackageLabelsList(@NonNull DeliveryOrder deliveryOrder) throws ShipperGatewayException;
}
