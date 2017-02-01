package de.metas.inoutcandidate.spi.impl;

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
import java.util.Collections;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.AbstractReceiptScheduleProducer;

/**
 *
 * NOTE: this producer is not actually creating new {@link I_M_ReceiptSchedule}s but it is customizing the existing ones creating/setting HU specific informations.
 *
 * @author tsa
 *
 */
public class HUReceiptScheduleProducer extends AbstractReceiptScheduleProducer
{

	@Override
	public List<de.metas.inoutcandidate.model.I_M_ReceiptSchedule> createOrUpdateReceiptSchedules(final Object model_NOTUSED,
			final List<de.metas.inoutcandidate.model.I_M_ReceiptSchedule> previousSchedules)
	{
		// Check if we really have something to customize
		if (previousSchedules == null || previousSchedules.isEmpty())
		{
			return Collections.emptyList();
		}

		for (final de.metas.inoutcandidate.model.I_M_ReceiptSchedule receiptSchedule : previousSchedules)
		{
			final I_M_ReceiptSchedule receiptScheduleHU = InterfaceWrapperHelper.create(receiptSchedule, I_M_ReceiptSchedule.class);
			updateFromOrderline(receiptScheduleHU);
		}

		return Collections.emptyList();
	}

	/**
	 * Copy HU relevant informations from Order Line to given <code>receiptSchedule</code>.
	 *
	 * This method it is also creating standard planning HUs (see {@link #generatePlanningHUs(de.metas.handlingunits.model.I_M_ReceiptSchedule)}).
	 *
	 * @param receiptSchedule
	 */
	private void updateFromOrderline(final I_M_ReceiptSchedule receiptSchedule)
	{
		// Don't touch processed receipt schedules
		if (receiptSchedule.isProcessed())
		{
			return;
		}

		// Update receipt schedules only if they were just created.
		// We do this because we want to perform this update only ONCE and not each time an receipt schedule or an order line gets updated (08168)
		if (!InterfaceWrapperHelper.isJustCreated(receiptSchedule))
		{
			return;
		}

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(receiptSchedule.getC_OrderLine(), I_C_OrderLine.class);

		final int itemProductID;
		final String packDescription;
		final BigDecimal qtyItemCapacity;
		final boolean isPackingMaterial;
		if (orderLine == null)
		{
			itemProductID = -1;
			packDescription = null;
			qtyItemCapacity = null;
			isPackingMaterial = false;
		}
		else
		{
			itemProductID = orderLine.getM_HU_PI_Item_Product_ID();
			packDescription = orderLine.getPackDescription();
			qtyItemCapacity = orderLine.getQtyItemCapacity();
			isPackingMaterial = orderLine.isPackagingMaterial();
		}

		receiptSchedule.setM_HU_PI_Item_Product_ID(itemProductID);
		receiptSchedule.setPackDescription(packDescription);
		receiptSchedule.setQtyItemCapacity(qtyItemCapacity); // i.e. Qty CUs/TU
		// receiptSchedule.setIsPackingMaterial(isPackingMaterial); // virtual column

		InterfaceWrapperHelper.save(receiptSchedule);

		if (!isPackingMaterial)
		{
			generatePlanningHUs(receiptSchedule);
		}
	}

	private void generatePlanningHUs(final I_M_ReceiptSchedule receiptSchedule)
	{
		M_ReceiptSchedule_GeneratePlanningHUs_WorkpackageProcessor.createWorkpackage(receiptSchedule);
	}

	@Override
	public void inactivateReceiptSchedules(final Object model)
	{
		// NOTE: this producer is not creating any receipt schedules, it's just customizing the existing one.
		// So the responsibility of deactivating receipt schedules is going to it's creator.
	}
}
