package de.metas.picking.service.impl;

import java.text.MessageFormat;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
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
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.service.IPackingService;
import de.metas.picking.service.PackingItemPart;
import de.metas.picking.service.PackingItemParts;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

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
			PackingItemParts parts)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runInNewTrx((TrxRunnable)localTrxName -> {

			final IContextAware contextProvider = PlainContextAware.newWithTrxName(ctx, localTrxName);
			final IMutableHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(contextProvider);

			for (final PackingItemPart part : parts.toList())
			{
				removeProductQtyFromHU(huContext, hu, part);
			}
		});
	}

	private void removeProductQtyFromHU(
			final IHUContext huContext,
			final I_M_HU hu,
			final PackingItemPart part)
	{
		final ShipmentScheduleId shipmentScheduleId = part.getShipmentScheduleId();
		final I_M_ShipmentSchedule schedule = Services.get(IShipmentSchedulePA.class).getById(shipmentScheduleId);

		//
		// Allocation Request
		final IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				part.getProductId(),
				part.getQty(),
				SystemTime.asZonedDateTime(),
				schedule, // reference model
				false // forceQtyAllocation
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
}
