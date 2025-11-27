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

import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierProductId;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.shipper.gateway.commons.async.AdviseDeliveryOrderWorkpackageProcessor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Interceptor(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{
	@NonNull private final ShipmentScheduleService shipmentScheduleService;

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver,
			I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override,
			I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate,
			I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate_Override,
			I_M_ShipmentSchedule.COLUMNNAME_M_Shipper_ID })
	public void markAsCarrierAdviceRequested(final I_M_ShipmentSchedule shipmentSchedule)
	{
		if(InterfaceWrapperHelper.isValueChanged(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_M_Shipper_ID))
		{
			shipmentSchedule.setCarrier_Product_ID(CarrierProductId.toRepoId(null));
			shipmentSchedule.setCarrier_Goods_Type_ID(CarrierGoodsTypeId.toRepoId(null));
			shipmentSchedule.setCarrier_Advising_Status(CarrierAdviseStatus.NotRequested.getCode());

			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoIdOrNull(shipmentSchedule.getM_ShipmentSchedule_ID());
			if(shipmentScheduleId != null)
			{
				shipmentScheduleService.removeAssignedServiceIdsByShipmentScheduleIds(ImmutableSet.of(shipmentScheduleId));
			}
		}

		if (!shipmentScheduleService.isEligibleForAutoCarrierAdvise(shipmentSchedule))
		{
			return;
		}

		shipmentSchedule.setCarrier_Advising_Status(CarrierAdviseStatus.Requested.getCode());
		shipmentSchedule.setCarrier_Product_ID(CarrierProductId.toRepoId(null));
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_M_ShipmentSchedule.COLUMNNAME_Carrier_Advising_Status })
	public void requestCarrierAdvice(final I_M_ShipmentSchedule shipmentSchedule)
	{
		if (!isMarkedAsCarrierAdviceRequested(shipmentSchedule))
		{
			return;
		}
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(shipmentSchedule.getC_Async_Batch_ID());
		AdviseDeliveryOrderWorkpackageProcessor.enqueueOnTrxCommit(shipmentScheduleId, asyncBatchId);
	}

	private boolean isMarkedAsCarrierAdviceRequested(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final CarrierAdviseStatus carrierAdviseStatus = CarrierAdviseStatus.ofNullableCode(shipmentSchedule.getCarrier_Advising_Status());
		return carrierAdviseStatus != null && carrierAdviseStatus.isRequested();
	}
}
