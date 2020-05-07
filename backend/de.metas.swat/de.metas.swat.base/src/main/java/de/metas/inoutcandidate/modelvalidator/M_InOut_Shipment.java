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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;

@Validator(I_M_InOut.class)
public class M_InOut_Shipment
{
	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_COMPLETE })
	public void invalidateShipmentSchedsForLines(final I_M_InOut shipment)
	{
		// Only if it's a shipment
		if (!shipment.isSOTrx())
		{
			return;
		}
		// we only need to invalidate for the respective lines, because basically we only need to shift the qty from QtyPicked to QtyDelivered.
		// No other shipment schedule will have anything more or less after that.
		Services.get(IShipmentScheduleInvalidateBL.class).invalidateJustForLines(shipment);
	}

	/**
	 * Note: a deletion of an InOut in the GUI doesn't cause M_InOutLine's model validator to be fired
	 *
	 * @param shipment
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDelete(final I_M_InOut shipment)
	{
		// Only if it's a shipment
		if (!shipment.isSOTrx())
		{
			return;
		}

		final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL = Services.get(IShipmentScheduleInvalidateBL.class);
		shipmentScheduleInvalidateBL.invalidateJustForLines(shipment); // make sure that at least the lines themselves are invalidated
		shipmentScheduleInvalidateBL.invalidateSegmentsForLines(shipment);
	}

	@ModelChange(timings =  ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged= I_M_InOut.COLUMNNAME_Processed )
	public void updateM_ShipmentSchedule_QtyPicked_Processed(final I_M_InOut shipment)
	{
		final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
		shipmentScheduleAllocDAO.updateProcessedFlagsForShipment(shipment);
	}
}
