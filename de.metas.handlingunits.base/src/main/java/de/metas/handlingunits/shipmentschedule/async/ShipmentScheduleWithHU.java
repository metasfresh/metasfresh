package de.metas.handlingunits.shipmentschedule.async;

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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import de.metas.handlingunits.IHUPackageBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IShipmentScheduleWithHU;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;

/* package */class ShipmentScheduleWithHU implements IShipmentScheduleWithHU
{
	private final I_M_ShipmentSchedule shipmentSchedule;
	private final BigDecimal qtyPicked;
	private final I_M_HU vhu;
	private final I_M_HU tuHU;
	private final I_M_HU luHU;
	private I_M_ShipmentSchedule_QtyPicked shipmentScheduleAlloc;

	private I_M_InOutLine shipmentLine = null;

	public ShipmentScheduleWithHU(final I_M_ShipmentSchedule_QtyPicked shipmentScheduleAlloc)
	{
		super();

		Check.assumeNotNull(shipmentScheduleAlloc, "shipmentScheduleAlloc not null");
		this.shipmentScheduleAlloc = shipmentScheduleAlloc;

		if (Services.get(IShipmentScheduleAllocBL.class).isDelivered(shipmentScheduleAlloc))
		{
			throw new AdempiereException("Shipment schedule allocation " + shipmentScheduleAlloc + " was already delivered");
		}

		shipmentSchedule = InterfaceWrapperHelper.create(shipmentScheduleAlloc.getM_ShipmentSchedule(), I_M_ShipmentSchedule.class);
		qtyPicked = shipmentScheduleAlloc.getQtyPicked();
		vhu = shipmentScheduleAlloc.getVHU_ID() > 0 ? shipmentScheduleAlloc.getVHU() : null;
		tuHU = shipmentScheduleAlloc.getM_TU_HU_ID() > 0 ? shipmentScheduleAlloc.getM_TU_HU() : null;
		luHU = shipmentScheduleAlloc.getM_LU_HU_ID() > 0 ? shipmentScheduleAlloc.getM_LU_HU() : null;
	}

	public ShipmentScheduleWithHU(final I_M_ShipmentSchedule shipmentSchedule, final BigDecimal qtyPicked)
	{
		super();

		shipmentScheduleAlloc = null; // no allocation, will be created on fly when needed

		Check.assumeNotNull(shipmentSchedule, "shipmentSchedule not null");
		this.shipmentSchedule = shipmentSchedule;

		Check.assumeNotNull(qtyPicked, "qtyPicked not null");
		this.qtyPicked = qtyPicked;

		vhu = null; // no VHU
		tuHU = null; // no TU
		luHU = null; // no LU
	}

	@Override
	public String toString()
	{
		return "ShipmentScheduleWithHU ["
				+ "\n    shipmentSchedule=" + shipmentSchedule
				+ "\n    qtyPicked=" + qtyPicked
				+ "\n    vhu=" + vhu
				+ "\n    tuHU=" + tuHU
				+ "\n    luHU=" + luHU
				+ "\n    shipmentScheduleAlloc=" + shipmentScheduleAlloc
				+ "\n    shipmentLine=" + shipmentLine
				+ "\n]";
	}

	@Override
	public I_M_ShipmentSchedule getM_ShipmentSchedule()
	{
		return shipmentSchedule;
	}

	@Override
	public BigDecimal getQtyPicked()
	{
		return qtyPicked;
	}

	@Override
	public I_M_HU getVHU()
	{
		return vhu;
	}

	@Override
	public I_M_HU getM_TU_HU()
	{
		return tuHU;
	}

	@Override
	public I_M_HU getM_LU_HU()
	{
		return luHU;
	}

	/**
	 * Gets the underlying LU or TU or VHU, depends on which is the first not null
	 * 
	 * @return LU/TU/VHU
	 */
	private I_M_HU getTopLevelHU()
	{
		if (luHU != null)
		{
			return luHU;
		}
		else if (tuHU != null)
		{
			return tuHU;
		}
		else if (vhu != null)
		{
			return vhu;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setM_InOutLine(final I_M_InOutLine shipmentLine)
	{
		this.shipmentLine = shipmentLine;
	}

	@Override
	public I_M_InOutLine getM_InOutLine()
	{
		return shipmentLine;
	}

	@Override
	public void setM_InOut(final I_M_InOut inout)
	{
		final IContextAware context = InterfaceWrapperHelper.getContextAware(inout);

		//
		// Create/Update ShipmentSchedule to Shipment Line allocation
		createUpdateShipmentLineAlloc(context);

		//
		// Update all M_Packages that point to our top level HU (i.e. LU/TU/VHU if any)
		// and set M_InOut to them.
		final I_M_HU topLevelHU = getTopLevelHU();
		if (topLevelHU != null)
		{
			final String trxName = context.getTrxName();
			Services.get(IHUPackageBL.class).assignShipmentToPackages(topLevelHU, inout, trxName);
		}
	}

	private void createUpdateShipmentLineAlloc(final IContextAware context)
	{
		// At this point we assume setM_InOutLine() method was called by API
		Check.assumeNotNull(shipmentLine, "shipmentLine not null");

		// If there is no shipment schedule allocation atm, create one now
		// This could happen if you called the constructor which is without shipmentScheduleAlloc parameter
		if (shipmentScheduleAlloc == null)
		{
			shipmentScheduleAlloc = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class, context);
			shipmentScheduleAlloc.setM_ShipmentSchedule(shipmentSchedule);
			shipmentScheduleAlloc.setVHU(vhu);
			shipmentScheduleAlloc.setM_LU_HU(luHU);
			shipmentScheduleAlloc.setM_TU_HU(tuHU);
			shipmentScheduleAlloc.setQtyPicked(qtyPicked);
		}

		//
		// Set shipment line link
		shipmentScheduleAlloc.setM_InOutLine(shipmentLine);

		//
		// Save allocation
		InterfaceWrapperHelper.save(shipmentScheduleAlloc, context.getTrxName());
	}
}
