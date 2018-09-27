package de.metas.picking.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Helper class used to manage {@link IPackingItem} instances.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public final class PackingItems
{
	/**
	 * @return a new {@link TransactionalPackingItem} from the given map.
	 */
	public static IPackingItem newPackingItem(@NonNull final ShipmentScheduleQtyPickedMap scheds2Qtys)
	{
		return new TransactionalPackingItem(scheds2Qtys);
	}

	public static ImmutableList<IPackingItem> createPackingItems(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		final Map<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedules = shipmentSchedulesRepo.getByIdsOutOfTrx(shipmentScheduleIds);

		final Map<PackingItemGroupingKey, IPackingItem> packingItems = new HashMap<>();

		for (final I_M_ShipmentSchedule sched : shipmentSchedules.values())
		{
			final Quantity qtyToDeliverTarget = shipmentScheduleBL.getQtyToDeliver(sched);
			if (qtyToDeliverTarget.signum() <= 0)
			{
				continue;
			}

			// task 08153: these code-lines are obsolete now, because the sched's qtyToDeliver(_Override) has the qtyPicked already factored in
			// final BigDecimal qtyPicked = shipmentScheduleAllocBL.getQtyPicked(sched);
			// final BigDecimal qtyToDeliver = qtyToDeliverTarget.subtract(qtyPicked == null ? BigDecimal.ZERO : qtyPicked);
			final ShipmentScheduleQtyPickedMap schedWithQty = ShipmentScheduleQtyPickedMap.singleton(sched, qtyToDeliverTarget);

			final IPackingItem newItem = newPackingItem(schedWithQty);
			final IPackingItem existingItem = packingItems.get(newItem.getGroupingKey());
			if (existingItem != null)
			{
				existingItem.addSchedules(newItem);
			}
			else
			{
				packingItems.put(newItem.getGroupingKey(), newItem);
			}
		}

		return ImmutableList.copyOf(packingItems.values());
	}
}
