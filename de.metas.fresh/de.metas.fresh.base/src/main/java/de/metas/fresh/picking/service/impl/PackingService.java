package de.metas.fresh.picking.service.impl;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.Predicate;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TrxRunnable;

import de.metas.adempiere.form.PackingItemsMap;
import de.metas.fresh.picking.form.IFreshPackingItem;
import de.metas.fresh.picking.service.IPackingContext;
import de.metas.fresh.picking.service.IPackingHandler;
import de.metas.fresh.picking.service.IPackingService;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.api.impl.ShipmentScheduleQtyPickedProductStorage;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public class PackingService implements IPackingService
{
	/**
	 * Cannot fully unload: hu. Result is: result
	 */
	private static final String ERR_CANNOT_FULLY_UNLOAD_RESULT = "@CannotFullyUnload@ {} @ResultIs@ {}";

	@Override
	public void removeProductQtyFromHU(final Properties ctx, final I_M_HU hu, final Map<I_M_ShipmentSchedule, BigDecimal> schedules2qty)
	{
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final IContextAware contextProvider = new PlainContextAware(ctx, localTrxName);
				final IMutableHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(contextProvider);

				for (final Map.Entry<I_M_ShipmentSchedule, BigDecimal> e : schedules2qty.entrySet())
				{
					final I_M_ShipmentSchedule schedule = e.getKey();
					final BigDecimal qtyToRemove = e.getValue();

					removeProductQtyFromHU(huContext, hu, schedule, qtyToRemove);
				}
			}
		});
	}

	private void removeProductQtyFromHU(final IHUContext huContext, final I_M_HU hu, final I_M_ShipmentSchedule schedule, final BigDecimal qtyToRemove)
	{
		//
		// Allocation Request
		final IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				schedule.getM_Product(),
				qtyToRemove,
				Services.get(IShipmentScheduleBL.class).getC_UOM(schedule), // uom
				SystemTime.asDate(),
				schedule // reference model
				);

		//
		// Allocation Destination
		final ShipmentScheduleQtyPickedProductStorage shipmentScheduleQtyPickedStorage = new ShipmentScheduleQtyPickedProductStorage(schedule);
		final IAllocationDestination destination = new GenericAllocationSourceDestination(shipmentScheduleQtyPickedStorage, schedule);

		//
		// Allocation Source
		final IAllocationSource source = new HUListAllocationSourceDestination(Collections.singletonList(hu));

		//
		// Move Qty from HU to shipment schedule (i.e. un-pick)
		final HULoader loader = new HULoader(source, destination);
		final IAllocationResult result = loader.load(request);

		// Make sure result is completed
		// NOTE: this issue could happen if we want to take out more then we have in our HU
		if (!result.isCompleted())
		{
			final String errmsg = MessageFormat.format(PackingService.ERR_CANNOT_FULLY_UNLOAD_RESULT, hu, result);
			throw new AdempiereException(errmsg);
		}
	}

	@Override
	public IPackingContext createPackingContext(final Properties ctx)
	{
		return new PackingContext(ctx);
	}

	@Override
	public void packItem(final IPackingContext packingContext,
			final IFreshPackingItem itemToPack,
			final BigDecimal qtyToPack,
			final IPackingHandler packingHandler)
	{
		Check.assumeNotNull(packingContext, "packingContext not null");
		Check.assumeNotNull(packingHandler, "packingHandler not null");

		final int key = packingContext.getPackingItemsMapKey();

		// Packing items
		// NOTE: we are doing a copy and work on it, in case something fails. At the end we will set it back
		final PackingItemsMap packingItems = packingContext.getPackingItemsMap().copy();

		//
		// Remove the itemToPack from unpacked items
		// NOTE: If there will be remaining qty, a NEW item with remaining Qty will be added to unpacked items
		packingItems.removeUnpackedItem(itemToPack);

		//
		// Pack our "itemToPack": it will be splitted into 2 items as follows
		// => itemToPackRemaining will be added back to unpacked items
		// => itemPacked will be added to packed items
		{
			final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate = new Predicate<I_M_ShipmentSchedule>()
			{

				@Override
				public boolean evaluate(final I_M_ShipmentSchedule shipmentSchedule)
				{
					return packingHandler.isPackingAllowedForShipmentSchedule(shipmentSchedule);
				}
			};
			
			final IFreshPackingItem itemToPackRemaining = itemToPack.copy();
			final IFreshPackingItem itemPacked = itemToPackRemaining.subtractToPackingItem(qtyToPack, acceptShipmentSchedulePredicate);

			//
			// Process our packed item
			packingHandler.itemPacked(itemPacked);

			//
			// Add our itemPacked to packed items
			// If an existing matching packed item will be found, our item will be merged there
			// If not, it will be added as a new packed item
			packingItems.appendPackedItem(key, itemPacked);

			//
			// Update back "itemToPack" to have a up2date version
			// NOTE: so far we worked on a copy (to avoid inconsistencies in case an exception is thrown in the middle)
			itemToPack.setSchedules(itemToPackRemaining);

			//
			// If there was a remaining qty in "itemToPack" then add it back to unpacked items
			// NOTE: we keep the old object instead of adding "itemToPackRemaining" because if we are not doing like this then subsequent calls to this method, using the same itemToPack will fail
			if (itemToPack.getQtySum().signum() != 0)
			{
				packingItems.addUnpackedItem(itemToPack);
			}
		}

		// Set back the packing items
		packingContext.setPackingItemsMap(packingItems);
	}
}
