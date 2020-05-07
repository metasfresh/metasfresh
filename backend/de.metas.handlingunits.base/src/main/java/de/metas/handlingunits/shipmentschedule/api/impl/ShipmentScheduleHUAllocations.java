package de.metas.handlingunits.shipmentschedule.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.Collection;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.document.impl.AbstractHUAllocations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.storage.IProductStorage;

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
	protected final void createAllocation(final I_M_HU luHU, final I_M_HU tuHU, final I_M_HU vhu,
			final BigDecimal qtyToAllocate, final I_C_UOM uom,
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
