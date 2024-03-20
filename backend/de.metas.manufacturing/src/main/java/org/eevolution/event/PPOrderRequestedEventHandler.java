package org.eevolution.event;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.inout.ShipmentScheduleId;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.planning.ProductPlanningId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.model.I_PP_Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

@Service
@Profile(Profiles.PROFILE_App) // only one handler should bother itself with these events
public class PPOrderRequestedEventHandler implements MaterialEventHandler<PPOrderRequestedEvent>
{
	private final IPPOrderBL ppOrderService = Services.get(IPPOrderBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	@Override
	public Collection<Class<? extends PPOrderRequestedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PPOrderRequestedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final PPOrderRequestedEvent event)
	{
		createProductionOrder(event);
	}

	/**
	 * Creates a production order. Note that it does not fire an event, because production orders can be created and changed for many reasons,<br>
	 * and therefore we leave the event-firing to a model interceptor.
	 */
	@VisibleForTesting
	I_PP_Order createProductionOrder(@NonNull final PPOrderRequestedEvent ppOrderRequestedEvent)
	{
		final PPOrder ppOrder = ppOrderRequestedEvent.getPpOrder();
		final PPOrderData ppOrderData = ppOrder.getPpOrderData();
		final Instant dateOrdered = ppOrderRequestedEvent.getDateOrdered();

		final ProductId productId = ProductId.ofRepoId(ppOrderData.getProductDescriptor().getProductId());
		final I_C_UOM uom = productBL.getStockUOM(productId);
		final Quantity qtyRequired = Quantity.of(ppOrderData.getQtyRequired(), uom);

		return ppOrderService.createOrder(PPOrderCreateRequest.builder()
												  .clientAndOrgId(ppOrderData.getClientAndOrgId())
												  .productPlanningId(ProductPlanningId.ofRepoIdOrNull(ppOrderData.getProductPlanningId()))
												  .materialDispoGroupId(ppOrderData.getMaterialDispoGroupId())
												  .plantId(ppOrderData.getPlantId())
												  .workstationId(ppOrderData.getWorkstationId())
												  .warehouseId(ppOrderData.getWarehouseId())
												  //
												  .productId(productId)
												  .attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(ppOrderData.getProductDescriptor().getAttributeSetInstanceId()))
												  .qtyRequired(qtyRequired)
												  //
												  .dateOrdered(dateOrdered)
												  .datePromised(ppOrderData.getDatePromised())
												  .dateStartSchedule(ppOrderData.getDateStartSchedule())
												  //
				                                  .shipmentScheduleId(ShipmentScheduleId.ofRepoIdOrNull(ppOrderData.getShipmentScheduleIdAsRepoId()))
												  .salesOrderLineId(OrderLineId.ofRepoIdOrNull(ppOrderData.getOrderLineIdAsRepoId()))
												  //
												  .build());
	}
}
