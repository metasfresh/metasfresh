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

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;

@Validator(I_C_OrderLine.class)
public class C_OrderLine_ShipmentSchedule
{
	final ITrxManager trxManager = Services.get(ITrxManager.class);

	/**
	 * Moved code here from <code>InOutCandidateValidator.orderLineChange()</code>.
	 * @param ol
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void invalidateShipmentSchedules(final I_C_OrderLine ol)
	{
		// if the line is not yet processed, there is no point to trigger shipment schedule invalidation
		if (!ol.isProcessed())
		{
			return;
		}

		// if it's not a sales order, there is no point to trigger invalidation
		if (!ol.getC_Order().isSOTrx())
		{
			return;
		}

		trxManager.runAfterCommit(() -> {
			final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL = Services.get(IShipmentScheduleInvalidateBL.class);
			shipmentScheduleInvalidateBL.invalidateJustForOrderLine(ol);
			shipmentScheduleInvalidateBL.notifySegmentChangedForOrderLine(ol);
		});
	}

	/**
	 * Cascades the delete onto the {@code M_ShipmentSchedule} of a sales order line, guarding against deleting a
	 * line whose schedule already has a real shipment (an active allocation to a non-voided/reversed inout).
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteOrGuardShipmentSchedules(final I_C_OrderLine ol)
	{
		if (!ol.getC_Order().isSOTrx())
		{
			return;
		}

		Services.get(IShipmentSchedulePA.class).deleteOrGuardForOrderLine(OrderLineId.ofRepoId(ol.getC_OrderLine_ID()));
	}
}
