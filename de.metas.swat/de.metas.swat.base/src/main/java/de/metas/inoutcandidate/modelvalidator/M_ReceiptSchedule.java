package de.metas.inoutcandidate.modelvalidator;

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

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleQtysBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;

@Interceptor(I_M_ReceiptSchedule.class)
public class M_ReceiptSchedule
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = {
					I_M_ReceiptSchedule.COLUMNNAME_Processed, // on line close we want to recalculate Qty Over/Under Delivery

					// https://github.com/metasfresh/metasfresh/issues/315:
					// call: onReceiptScheduleChanged if *any* for the "input" columns changed
					I_M_ReceiptSchedule.COLUMNNAME_QtyOrdered,
					I_M_ReceiptSchedule.COLUMNNAME_QtyToMove_Override,
					I_M_ReceiptSchedule.COLUMNNAME_QtyMoved
			})
	public void updateQtys(final I_M_ReceiptSchedule sched)
	{
		final IReceiptScheduleQtysBL receiptScheduleQtysHandler = Services.get(IReceiptScheduleQtysBL.class);
		receiptScheduleQtysHandler.onReceiptScheduleChanged(sched);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE }
			, ifColumnsChanged = {
					I_M_ReceiptSchedule.COLUMNNAME_QtyOrderedOverUnder
			})
	public void propagateQtysToOrderLine(final I_M_ReceiptSchedule sched, final int timing)
	{
		//
		// Fetch Order Line (if any)
		final I_C_OrderLine orderLine = sched.getC_OrderLine();
		if (orderLine == null || orderLine.getC_OrderLine_ID() <= 0)
		{
			// no order line => nothing to do
			return;
		}

		//
		// Values that will be propagated to order line
		final BigDecimal qtyOverUnderDelivery;

		//
		// Case: we are about to delete the receipt schedule => reset fields in order line
		if (ModelChangeType.valueOf(timing).isDelete())
		{
			qtyOverUnderDelivery = BigDecimal.ZERO;
		}
		//
		// Case: we just updated the receipt schedule => update fields in order line
		else
		{
			qtyOverUnderDelivery = Services.get(IReceiptScheduleQtysBL.class).getQtyOverUnderDelivery(sched);
		}

		orderLine.setQtyOrderedOverUnder(qtyOverUnderDelivery);
		InterfaceWrapperHelper.save(orderLine);
	}

	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			}
			, ifColumnsChanged = {
					I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_ID
					, I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_Location_ID
					, I_M_ReceiptSchedule.COLUMNNAME_AD_User_ID
			})
	public void updateBPartnerAddress(final I_M_ReceiptSchedule sched)
	{
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
		receiptScheduleBL.updateBPartnerAddress(sched);
	}

	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_NEW,
					ModelValidator.TYPE_BEFORE_CHANGE
			}
			, ifColumnsChanged = {
					I_M_ReceiptSchedule.COLUMNNAME_C_Order_ID
			})
	public void updatePreparationTime(final I_M_ReceiptSchedule sched)
	{
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
		receiptScheduleBL.updatePreparationTime(sched);
	}

	@ModelChange(
			timings = {
					// ModelValidator.TYPE_BEFORE_NEW, // not needed because we don't set overwrites on NEW
					ModelValidator.TYPE_BEFORE_CHANGE
			}
			, ifColumnsChanged = {
					I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_Override_ID
					, I_M_ReceiptSchedule.COLUMNNAME_C_BP_Location_Override_ID
					, I_M_ReceiptSchedule.COLUMNNAME_AD_User_Override_ID
			})
	public void updateBPartnerAddressOverride(final I_M_ReceiptSchedule sched)
	{
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
		receiptScheduleBL.updateBPartnerAddressOverride(sched);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateHeaderAggregationKey(final I_M_ReceiptSchedule sched)
	{
		if (sched.isProcessed())
		{
			return;
		}

		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
		final IAggregationKeyBuilder<I_M_ReceiptSchedule> headerAggregationKeyBuilder = receiptScheduleBL.getHeaderAggregationKeyBuilder();
		final String headerAggregationKey = headerAggregationKeyBuilder.buildKey(sched);
		sched.setHeaderAggregationKey(headerAggregationKey);
	}
}
