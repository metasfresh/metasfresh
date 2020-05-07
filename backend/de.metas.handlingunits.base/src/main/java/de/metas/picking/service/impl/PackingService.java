package de.metas.picking.service.impl;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TrxRunnable;

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
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.service.IFreshPackingItem;
import de.metas.picking.service.IPackingContext;
import de.metas.picking.service.IPackingHandler;
import de.metas.picking.service.IPackingService;
import de.metas.picking.service.PackingItemsMap;
import de.metas.quantity.Quantity;
import lombok.NonNull;

public class PackingService implements IPackingService
{
	/**
	 * Cannot fully unload: hu. Result is: result
	 */
	private static final String ERR_CANNOT_FULLY_UNLOAD_RESULT = "@CannotFullyUnload@ {} @ResultIs@ {}";

	@Override
	public void removeProductQtyFromHU(
			final Properties ctx,
			final I_M_HU hu,
			final Map<I_M_ShipmentSchedule, Quantity> schedules2qty)
	{
		Services.get(ITrxManager.class).run((TrxRunnable)localTrxName -> {

			final IContextAware contextProvider = PlainContextAware.newWithTrxName(ctx, localTrxName);
			final IMutableHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(contextProvider);

			for (final Map.Entry<I_M_ShipmentSchedule, Quantity> e : schedules2qty.entrySet())
			{
				final I_M_ShipmentSchedule schedule = e.getKey();
				final Quantity qtyToRemove = e.getValue();

				removeProductQtyFromHU(huContext, hu, schedule, qtyToRemove);
			}
		});
	}

	private void removeProductQtyFromHU(
			final IHUContext huContext,
			final I_M_HU hu,
			final I_M_ShipmentSchedule schedule,
			final Quantity qtyToRemove)
	{
		//
		// Allocation Request
		final IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				schedule.getM_Product(),
				qtyToRemove.getQty(),
				qtyToRemove.getUOM(),
				SystemTime.asDate(),
				schedule // reference model
				);

		//
		// Allocation Destination
		final ShipmentScheduleQtyPickedProductStorage shipmentScheduleQtyPickedStorage = new ShipmentScheduleQtyPickedProductStorage(schedule);
		final IAllocationDestination destination = new GenericAllocationSourceDestination(shipmentScheduleQtyPickedStorage, schedule);

		//
		// Allocation Source
		final IAllocationSource source = HUListAllocationSourceDestination.of(hu);

		//
		// Move Qty from HU to shipment schedule (i.e. un-pick)
		final IAllocationResult result = HULoader.of(source, destination)
				.load(request);

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
	public void packItem(
			@NonNull final IPackingContext packingContext,
			@NonNull final IFreshPackingItem itemToPack,
			@NonNull final Quantity qtyToPack,
			@NonNull final IPackingHandler packingHandler)
	{
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
			final Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate = //
					shipmentSchedule ->  packingHandler.isPackingAllowedForShipmentSchedule(shipmentSchedule);

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
