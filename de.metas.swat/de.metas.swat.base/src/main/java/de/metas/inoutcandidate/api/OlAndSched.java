package de.metas.inoutcandidate.api;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.misc.service.IPOService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.X_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;

/**
 * 
 * @author ts
 * 
 */
public final class OlAndSched
{
	private final I_C_OrderLine orderLine;

	private final I_M_ShipmentSchedule shipmentSchedule;

	private boolean availForShipmentRun;

	private final BigDecimal initialSchedQtyDelivered;
	
	public OlAndSched(final org.compiere.model.I_C_OrderLine ol, final I_M_ShipmentSchedule sched)
	{
		super();
		orderLine = InterfaceWrapperHelper.create(ol, I_C_OrderLine.class);
		shipmentSchedule = sched;
		initialSchedQtyDelivered = sched.getQtyDelivered();
	}

	public I_C_OrderLine getOl()
	{
		return orderLine;
	}

	public I_M_ShipmentSchedule getSched()
	{
		return shipmentSchedule;
	}

	/**
	 * This method doesn't use {@link X_M_ShipmentSchedule#getQtyToDeliver_Override()} because that method returns
	 * {@link BigDecimal#ZERO} if the database value is NULL.
	 * 
	 * @return
	 */
	public BigDecimal getQtyOverride()
	{
		final IPOService poService = Services.get(IPOService.class);

		final BigDecimal result = (BigDecimal)poService.getValue(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override);
		return result;
	}

	public boolean isAvailForShipmentRun()
	{
		return availForShipmentRun;
	}

	public void setAvailForShipmentRun(boolean availForShipmentRun)
	{
		this.availForShipmentRun = availForShipmentRun;
	}

	public BigDecimal getInitialSchedQtyDelivered()
	{
		return initialSchedQtyDelivered;
	}

	@Override
	public String toString()
	{
		return orderLine + " / " + shipmentSchedule;
	}
}
