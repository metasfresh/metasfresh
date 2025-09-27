/*
 * #%L
 * de.metas.shipper.gateway.carrier
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.carrier.service;

import de.metas.shipper.gateway.carrier.CarrierConstants;
import de.metas.shipper.gateway.carrier.model.ShipmentOrderRepository;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.util.Check;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarrierOrderService implements DeliveryOrderService
{
	private final ShipmentOrderRepository shipmentOrderRepository;

	@Override
	public String getShipperGatewayId()
	{
		return CarrierConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public ITableRecordReference toTableRecordReference(final DeliveryOrder deliveryOrder)
	{
		final DeliveryOrderId deliveryOrderRepoId = deliveryOrder.getId();
		Check.assumeNotNull(deliveryOrderRepoId, "DeliveryOrder ID must not be null for deliveryOrder " + deliveryOrder);
		return TableRecordReference.of(I_Carrier_ShipmentOrder.Table_Name, deliveryOrderRepoId);
	}

	@Override
	public DeliveryOrder getByRepoId(final DeliveryOrderId deliveryOrderRepoId)
	{
		return shipmentOrderRepository.getById(deliveryOrderRepoId);
	}

	@Override
	public DeliveryOrder save(final DeliveryOrder order)
	{
		return shipmentOrderRepository.save(order);
	}
}
