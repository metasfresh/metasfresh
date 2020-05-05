package de.metas.contracts.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.I_M_InOutLine;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Interceptor(I_M_ShipmentSchedule_QtyPicked.class)
public class M_ShipmentSchedule_QtyPicked
{	
	@ModelChange( //
			timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW }, //
			ifColumnsChanged = I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_InOutLine_ID)
	public void connectInOutLineToSubscriptionProgressIfPossible(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		if (hasNoInOutLine(shipmentScheduleQtyPicked))
		{
			return;
		}

		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleQtyPicked.getM_ShipmentSchedule();
		if (hasNoSubscriptionProgress(shipmentSchedule))
		{
			return;
		}

		connectInOutLineToSubscriptionProgress(shipmentScheduleQtyPicked);
	}

	private boolean hasNoInOutLine(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		return shipmentScheduleQtyPicked.getM_InOutLine_ID() <= 0;
	}

	private boolean hasNoSubscriptionProgress(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentSchedule.getAD_Table_ID() != getTableId(I_C_SubscriptionProgress.class);
	}

	private void connectInOutLineToSubscriptionProgress(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleQtyPicked.getM_ShipmentSchedule();

		final I_M_InOutLine inOutLine = create(shipmentScheduleQtyPicked.getM_InOutLine(), I_M_InOutLine.class);
		inOutLine.setC_SubscriptionProgress_ID(shipmentSchedule.getRecord_ID());
		save(inOutLine);
	}
}
