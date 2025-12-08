package de.metas.shipper.gateway.go;

import de.metas.shipper.gateway.go.model.I_GO_DeliveryOrder;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.util.Check;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

/*
 * #%L
 * de.metas.shipper.gateway.go
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

/**
 * Service used to save and load {@link DeliveryOrder}s.
 *
 */
@Service
@AllArgsConstructor
public class GODeliveryOrderService implements DeliveryOrderService
{
	private final GODeliveryOrderRepository goDeliveryOrderRepository;

	@Override
	public TableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		final DeliveryOrderId deliveryOrderRepoId = deliveryOrder.getId();
		Check.assumeNotNull(deliveryOrderRepoId, "DeliveryOrder ID must not be null");
		return TableRecordReference.of(I_GO_DeliveryOrder.Table_Name, deliveryOrderRepoId);
	}

	@Override
	public DeliveryOrder getByRepoId(@NonNull final DeliveryOrderId deliveryOrderId)
	{
		return goDeliveryOrderRepository.getByRepoId(deliveryOrderId);
	}

	@Override
	public DeliveryOrder save(final DeliveryOrder order)
	{
		return goDeliveryOrderRepository.save(order);
	}

	@Override
	public String getShipperGatewayId()
	{
		return GOConstants.SHIPPER_GATEWAY_ID;
	}
}
