package de.metas.shipper.gateway.spi;

import java.util.List;

import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.PackageLabels;

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
	String getShipperGatewayId();

	/**
	 * Create a delivery order on the remote endpoint. The remote order is drafted and can still be changed.
	 */
	DeliveryOrder createDeliveryOrder(DeliveryOrder draftDeliveryOrder) throws ShipperGatewayException;

	/**
	 * Effectively place the given order on the remote endpoint.
	 */
	DeliveryOrder completeDeliveryOrder(DeliveryOrder deliveryOrder) throws ShipperGatewayException;

	DeliveryOrder voidDeliveryOrder(DeliveryOrder deliveryOrder) throws ShipperGatewayException;

	List<PackageLabels> getPackageLabelsList(DeliveryOrder deliveryOrder) throws ShipperGatewayException;;
}
