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

import de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder;
import de.metas.shipper.gateway.spi.CreateDraftDeliveryOrderRequest;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DpdDeliveryOrderService implements DeliveryOrderService
{
	private final DpdDraftDeliveryOrderCreator deliveryOrderCreator;
	private final DpdDeliveryOrderRepository dpdDeliveryOrderRepository;
	private final DpdShipperGatewayClientFactory clientFactory;

	@Override
	public ShipperGatewayId getShipperGatewayId()
	{
		return DpdConstants.SHIPPER_GATEWAY_ID;
	}

	@NonNull
	@Override
	public @NotNull DeliveryOrder createDraftDeliveryOrder(@NonNull final CreateDraftDeliveryOrderRequest request)
	{
		return deliveryOrderCreator.createDraftDeliveryOrder(request);
	}

	@NonNull
	@Override
	public ITableRecordReference toTableRecordReference(@NonNull final DeliveryOrder deliveryOrder)
	{
		final DeliveryOrderId deliveryOrderRepoId = deliveryOrder.getId();
		Check.assumeNotNull(deliveryOrderRepoId, "DeliveryOrder ID must not be null");
		return TableRecordReference.of(I_DPD_StoreOrder.Table_Name, deliveryOrderRepoId);
	}

	@Override
	public DeliveryOrder getByRepoId(final DeliveryOrderId deliveryOrderRepoId)
	{

		return dpdDeliveryOrderRepository.getByRepoId(deliveryOrderRepoId);
	}

	/**
	 * Explanation of the different data structures:
	 * <p>
	 * - DeliveryOrder is the DTO
	 * - I_Dpd_StoreOrder is the persisted object for that DTO (which has lines), with data relevant for DPD.
	 * Each different shipper has its own "shipper-po" with its own relevant data.
	 */
	@Override
	@NonNull
	public DeliveryOrder save(@NonNull final DeliveryOrder deliveryOrder)
	{
		return dpdDeliveryOrderRepository.save(deliveryOrder);
	}
	
	@Override
	public @NonNull @NotNull ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		return clientFactory.newClientForShipperId(shipperId);
	}
}
