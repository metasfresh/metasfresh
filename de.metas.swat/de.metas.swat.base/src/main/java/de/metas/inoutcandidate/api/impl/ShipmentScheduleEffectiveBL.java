package de.metas.inoutcandidate.api.impl;

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
import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.model.I_AD_User;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;

public class ShipmentScheduleEffectiveBL implements IShipmentScheduleEffectiveBL
{

	@Override
	public I_C_BPartner_Location getBPartnerLocation(final I_M_ShipmentSchedule sched)
	{
		final I_C_BPartner_Location location =
				InterfaceWrapperHelper.create(
						sched.getC_BP_Location_Override_ID() <= 0 ? sched.getC_BPartner_Location() : sched.getC_BP_Location_Override(),
						I_C_BPartner_Location.class);
		return location;
	}

	@Override
	public int getC_BPartner_ID(final I_M_ShipmentSchedule sched)
	{
		if (sched.getC_BPartner_Override_ID() <= 0)
		{
			return sched.getC_BPartner_ID();
		}
		else
		{
			return sched.getC_BPartner_Override_ID();
		}
	}

	@Override
	public String getDeliveryRule(final I_M_ShipmentSchedule sched)
	{
		Check.assume(sched != null, "'sched' parameter may not be null");

		final String deliveryRule =
				Check.isEmpty(sched.getDeliveryRule_Override(), true) ?
						sched.getDeliveryRule() : sched.getDeliveryRule_Override();
		return deliveryRule;
	}

	@Override
	public I_M_Warehouse getWarehouse(final I_M_ShipmentSchedule sched)
	{
		Check.assumeNotNull(sched, "sched not null");
		if (!InterfaceWrapperHelper.isNull(sched, I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID))
		{
			return sched.getM_Warehouse_Override();
		}
		return sched.getM_Warehouse();
	}

	@Override
	public int getWarehouseId(final I_M_ShipmentSchedule sched)
	{
		Check.assumeNotNull(sched, "sched not null");
		if (!InterfaceWrapperHelper.isNull(sched, I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID))
		{
			return sched.getM_Warehouse_Override_ID();
		}
		return sched.getM_Warehouse_ID();
	}

	@Override
	public I_M_Locator getM_Locator(final I_M_ShipmentSchedule sched)
	{
		final I_M_Warehouse warehouse = getWarehouse(sched);
		final I_M_Locator locator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);
		return locator;
	}

	@Override
	public BigDecimal getQtyToDeliver(final I_M_ShipmentSchedule sched)
	{
		Check.assumeNotNull(sched, "sched not null");
		if (!InterfaceWrapperHelper.isNull(sched, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override))
		{
			return sched.getQtyToDeliver_Override();
		}
		return sched.getQtyToDeliver();
	}

	@Override
	public I_C_BPartner getBPartner(final I_M_ShipmentSchedule sched)
	{
		final I_C_BPartner bPartner =
				InterfaceWrapperHelper.create(
						sched.getC_BPartner_Override_ID() <= 0 ? sched.getC_BPartner() : sched.getC_BPartner_Override(),
						I_C_BPartner.class);
		return bPartner;
	}

	@Override
	public int getC_BP_Location_ID(final I_M_ShipmentSchedule sched)
	{
		if (sched.getC_BP_Location_Override_ID() <= 0)
		{
			return sched.getC_BPartner_Location_ID();
		}
		else
		{
			return sched.getC_BP_Location_Override_ID();
		}
	}

	@Override
	public int getAD_User_ID(final I_M_ShipmentSchedule sched)
	{
		return sched.getAD_User_Override_ID() > 0 ?
				sched.getAD_User_Override_ID() : sched.getAD_User_ID();

	}

	@Override
	public I_AD_User getAD_User(final I_M_ShipmentSchedule sched)
	{
		final I_AD_User user =
				sched.getAD_User_Override_ID() <= 0 ?
						InterfaceWrapperHelper.create(sched.getAD_User(), I_AD_User.class) : InterfaceWrapperHelper.create(sched.getAD_User_Override(), I_AD_User.class);
		return user;
	}

	@Override
	public BigDecimal getQtyOrdered(final I_M_ShipmentSchedule shipmentSchedule)
	{
		if (InterfaceWrapperHelper.isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override))
		{
			return shipmentSchedule.getQtyOrdered_Calculated();
		}
		return shipmentSchedule.getQtyOrdered_Override();
	}
	
	@Override
	public Timestamp getDeliveryDate(final I_M_ShipmentSchedule sched)
	{
		final Timestamp deliveryDateOverride = sched.getDeliveryDate_Override();
		if (deliveryDateOverride != null)
		{
			return deliveryDateOverride;
		}

		return sched.getDeliveryDate();
	}

	@Override
	public Timestamp getPreparationDate(final I_M_ShipmentSchedule sched)
	{
		final Timestamp preparationDateOverride = sched.getPreparationDate_Override();
		if (preparationDateOverride != null)
		{
			return preparationDateOverride;
		}

		return sched.getPreparationDate();
	}
}
