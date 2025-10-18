/*
 * #%L
 * de.metas.shipper.gateway.commons
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.commons.model.interceptor;

import de.metas.async.AsyncBatchId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.shipper.gateway.commons.async.AdviseDeliveryOrderWorkpackageProcessor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver,
			I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override,
			I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate,
			I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate_Override })
	public void requestCarrierAdvice(final I_M_ShipmentSchedule shipmentSchedule)
	{
		if (shipmentSchedule.isProcessed() || shipmentSchedule.isClosed() || !shipmentSchedule.isActive() || shipmentSchedule.getQtyToDeliver().signum() <= 0)
		{
			return;
		}

		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(shipmentSchedule.getC_Async_Batch_ID());
		AdviseDeliveryOrderWorkpackageProcessor.enqueueOnTrxCommit(shipmentScheduleId, asyncBatchId);
	}
}
