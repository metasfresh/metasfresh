package de.metas.handlingunits.model.validator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Collections;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.ShipmentScheduleHUPackingAware;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import lombok.NonNull;

@Interceptor(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void updateFromOrderline(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final int itemProductId;
		final String packDescription;

		if (shipmentSchedule.getC_OrderLine_ID() > 0)
		{
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(shipmentSchedule.getC_OrderLine(), I_C_OrderLine.class);
			itemProductId = orderLine.getM_HU_PI_Item_Product_ID();
			packDescription = orderLine.getPackDescription();
		}
		else
		{
			itemProductId = -1;
			packDescription = null;
		}

		shipmentSchedule.setM_HU_PI_Item_Product_ID(itemProductId);
		shipmentSchedule.setPackDescription(packDescription);

		updateLUTUQtys(shipmentSchedule);
	}

	private void updateLUTUQtys(final I_M_ShipmentSchedule shipmentSchedule)
	{
		if (shipmentSchedule.getC_OrderLine_ID() <= 0)
		{
			return;
		}

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(shipmentSchedule.getC_OrderLine(), I_C_OrderLine.class);

		final BigDecimal qtyOrderedTU = orderLine.getQtyEnteredTU();
		shipmentSchedule.setQtyOrdered_TU(qtyOrderedTU);

		// guard: if there is no PI Item Product => do nothing
		if (shipmentSchedule.getM_HU_PI_Item_Product_ID() <= 0)
		{
			return;
		}

		final I_M_HU_LUTU_Configuration lutuConfiguration = //
				Services.get(IHUShipmentScheduleBL.class).deriveM_HU_LUTU_Configuration(shipmentSchedule);

		final int qtyOrderedLU = //
				Services.get(ILUTUConfigurationFactory.class).calculateQtyLUForTotalQtyTUs(lutuConfiguration, qtyOrderedTU);
		shipmentSchedule.setQtyOrdered_LU(BigDecimal.valueOf(qtyOrderedLU));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void createEffectiveValues(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		// create the effective values (calculated, override)
		Services.get(IHUShipmentScheduleBL.class).updateHURelatedValuesFromOrderLine(shipmentSchedule);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_M_ShipmentSchedule.COLUMNNAME_QtyTU_Calculated,
			I_M_ShipmentSchedule.COLUMNNAME_QtyTU_Override,
			I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_TU,
			I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_Calculated_ID,
			I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_Override_ID,
			I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_ID
	})
	public void updateEffectiveValues(final I_M_ShipmentSchedule shipmentSchedule)
	{
		// update the effective values (override)
		Services.get(IHUShipmentScheduleBL.class).updateEffectiveValues(shipmentSchedule);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(shipmentSchedule.getC_OrderLine(), I_C_OrderLine.class);

		if (orderLine == null)
		{
			// nothing to do
			return;
		}

		final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
		final ShipmentScheduleHUPackingAware packingAware = new ShipmentScheduleHUPackingAware(shipmentSchedule);

		final BigDecimal qtyTUCalculated = shipmentSchedule.getQtyTU_Calculated();

		if (!qtyTUCalculated.equals(shipmentSchedule.getQtyOrdered_TU()))
		{
			// Calculate and set QtyEntered(CU) from M_HU_PI_Item_Product and QtyEnteredTU(aka QtyPacks)
			final int qtyTU = packingAware.getQtyTU().intValueExact();
			huPackingAwareBL.setQtyCUFromQtyTU(packingAware, qtyTU);
		}

		final int hupipCalculatedID = shipmentSchedule.getM_HU_PI_Item_Product_Calculated_ID();

		final int currentHUPIPID = shipmentSchedule.getM_HU_PI_Item_Product_Override_ID();

		if (hupipCalculatedID != currentHUPIPID)
		{
			final BigDecimal qtyTU = packingAware.getQtyTU();
			huPackingAwareBL.setQtyCUFromQtyTU(packingAware, qtyTU.intValueExact());

			shipmentSchedule.setQtyOrdered_Override(packingAware.getQty());
		}

		// update orderLine

		// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
		final BigDecimal qtyOrderedEffective = Services.get(IShipmentScheduleEffectiveBL.class).computeQtyOrdered(shipmentSchedule);
		orderLine.setQtyOrdered(qtyOrderedEffective);

		InterfaceWrapperHelper.save(orderLine);

	}

	/**
	 * Note: it's important that the schedule is only invalidated on certain value changes.<br>
	 * For example, a change of lock status or valid status may not cause an invalidation.<br>
	 * Also note that
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override,
			I_M_ShipmentSchedule.COLUMNNAME_QtyTU_Calculated,
			I_M_ShipmentSchedule.COLUMNNAME_QtyTU_Override,
			de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Calculated,
			I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_Override_ID,
			I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_ID,
			I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_Calculated_ID,
			de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered
	})
	public void invalidate(final I_M_ShipmentSchedule shipmentSchedule)
	{
		// 08746: make sure that at any rate, the schedule itself is invalidated, even if it has delivery rule "force"
		Services.get(IShipmentSchedulePA.class).invalidate(
				Collections.singletonList(
						InterfaceWrapperHelper.create(shipmentSchedule, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class)),
				InterfaceWrapperHelper.getTrxName(shipmentSchedule));

		Services.get(IShipmentScheduleInvalidateBL.class).invalidateSegmentForShipmentSchedule(shipmentSchedule);
	}
}
