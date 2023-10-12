package de.metas.shipper.gateway.derkurier;

import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import static de.metas.shipper.gateway.derkurier.DerKurierConstants.SHIPPER_GATEWAY_ID;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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
@AllArgsConstructor
public class DerKurierDeliveryOrderService implements DeliveryOrderService
{
	private final DerKurierDeliveryOrderRepository derKurierDeliveryOrderRepository;

	@Override
	@NonNull
	public String getShipperGatewayId()
	{
		return SHIPPER_GATEWAY_ID;
	}

	@Override
	@NonNull
	public ITableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		return TableRecordReference.of(I_DerKurier_DeliveryOrder.Table_Name, deliveryOrder.getId());
	}

	@Override
	public DeliveryOrder getByRepoId(@NonNull final DeliveryOrderId deliveryOrderId)
	{
		return derKurierDeliveryOrderRepository.getByRepoId(deliveryOrderId);
	}

	@Override
	public DeliveryOrder save(@NonNull final DeliveryOrder deliveryOrder)
	{
		return derKurierDeliveryOrderRepository.save(deliveryOrder);
	}
}
