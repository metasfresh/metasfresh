package de.metas.handlingunits.inoutcandidate.callout;

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


import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Services;

import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.ShipmentScheduleHUPackingAware;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;

@Callout(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{
	public static final M_ShipmentSchedule instance = new M_ShipmentSchedule();

	@CalloutMethod(columnNames = {
			I_M_ShipmentSchedule.COLUMNNAME_QtyTU_Calculated,
			I_M_ShipmentSchedule.COLUMNNAME_QtyTU_Override,
			I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_TU,
			I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_Calculated_ID,
			I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_Override_ID,
			I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_ID

	})
	public void updateShipmentScheduleQtys(final I_M_ShipmentSchedule shipmentSchedule, final ICalloutField field)
	{
		// in case of non virtual hupip update qtys of shipment schedule
		Services.get(IHUShipmentScheduleBL.class).updateEffectiveValues(shipmentSchedule);

		//
		// Calculate and set QtyEntered(CU) from M_HU_PI_Item_Product and QtyEnteredTU(aka QtyPacks)
		final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
		final ShipmentScheduleHUPackingAware packingAware = new ShipmentScheduleHUPackingAware(shipmentSchedule);
		final int qtyTU = packingAware.getQtyTU().intValueExact();
		huPackingAwareBL.setQtyCUFromQtyTU(packingAware, qtyTU);

	}
}
