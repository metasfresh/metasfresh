package de.metas.handlingunits.shipmentschedule.api.impl;

import java.util.Collection;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.document.impl.AbstractHUAllocations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Services;

public class ShipmentScheduleHUAllocations extends AbstractHUAllocations
{
	//
	// Services
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

	public ShipmentScheduleHUAllocations(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule, final IProductStorage productStorage)
	{
		super(InterfaceWrapperHelper.create(shipmentSchedule, I_M_ShipmentSchedule.class),
				productStorage);
	}

	@Override
	protected final I_M_ShipmentSchedule getDocumentLineModel()
	{
		return (I_M_ShipmentSchedule)super.getDocumentLineModel();
	}

	@Override
	protected final void createAllocation(
			final I_M_HU luHU,
			final I_M_HU tuHU,
			final I_M_HU vhu,
			final StockQtyAndUOMQty qtyToAllocate,
			final boolean deleteOldTUAllocations)
	{
		// nothing for now
	}

	@Override
	protected final void deleteAllocations(final Collection<I_M_HU> husToUnassign)
	{
		final I_M_ShipmentSchedule shipmentSchedule = getDocumentLineModel();

		for (final I_M_HU hu : husToUnassign)
		{
			huShipmentScheduleBL.unallocateTU(shipmentSchedule, hu, getTrxName());
		}
	}

	@Override
	protected final void deleteAllocations()
	{
		// nothing for now
	}
}
