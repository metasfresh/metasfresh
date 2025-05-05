/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.inoutcandidate.spi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.inoutcandidate.invalidation.impl.ShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleAttributeSegment;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@Profile(Profiles.PROFILE_App)
public class ShipmentScheduleStockChangedEventHandler implements MaterialEventHandler<StockChangedEvent>
{
	private final ShipmentScheduleInvalidateBL scheduleInvalidateBL;
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	public ShipmentScheduleStockChangedEventHandler(@NonNull final ShipmentScheduleInvalidateBL scheduleInvalidateBL)
	{
		this.scheduleInvalidateBL = scheduleInvalidateBL;
	}

	@Override
	public Collection<Class<? extends StockChangedEvent>> getHandledEventType()
	{
		return ImmutableList.of(StockChangedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final StockChangedEvent event)
	{
		final ProductDescriptor productDescriptor = event.getProductDescriptor();
		final ShipmentScheduleAttributeSegment shipmentScheduleAttributeSegment = ShipmentScheduleAttributeSegment.ofAttributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(productDescriptor.getAttributeSetInstanceId()));

		final Set<WarehouseId> warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(event.getWarehouseId());
		final ImmutableSet<LocatorId> locatorIds = warehouseDAO.getLocatorIdsByWarehouseIds(warehouseIds);

		final IShipmentScheduleSegment storageSegment = ImmutableShipmentScheduleSegment.builder()
				.productIds(ImmutableSet.of(productDescriptor.getProductId()))
				.attributes(ImmutableSet.of(shipmentScheduleAttributeSegment))
				.locatorIds(LocatorId.toRepoIds(locatorIds))
				.bpartnerId(IShipmentScheduleSegment.ANY)
				.build();

		scheduleInvalidateBL.flagForRecomputeStorageSegment(storageSegment);
	}
}
