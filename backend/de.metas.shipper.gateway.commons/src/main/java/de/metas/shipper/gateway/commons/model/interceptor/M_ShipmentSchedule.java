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
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentScheduleCarrierServiceRepository;
import de.metas.inoutcandidate.ShipmentScheduleService;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.shipper.gateway.commons.async.AdviseDeliveryOrderWorkpackageProcessor;
import de.metas.shipping.CarrierProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Interceptor(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@NonNull private final ShipmentScheduleService shipmentScheduleService;
	@NonNull private final ShipmentScheduleCarrierServiceRepository shipmentScheduleCarrierServiceRepository;

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_M_ShipmentSchedule.COLUMNNAME_Carrier_Advising_Status)
	public void ifAdviseFailedUnsetCarrierProduct(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final CarrierAdviseStatus carrierAdviseStatus = CarrierAdviseStatus.ofNullableCode(shipmentSchedule.getCarrier_Advising_Status());
		if (carrierAdviseStatus != null && carrierAdviseStatus.isFailed())
		{
			shipmentSchedule.setCarrier_Product_ID(CarrierProductId.toRepoId(null));
		}
	}

	// NOTE: QtyToDeliver / QtyToDeliver_Override are intentionally NOT triggers — the advise is per-unit
	// (qty-independent), and QtyToDeliver recomputes on every availability recompute (DeliveryRule='A'), which
	// would otherwise churn the advise on each recompute.
	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate,
			I_M_ShipmentSchedule.COLUMNNAME_DeliveryDate_Override,
			I_M_ShipmentSchedule.COLUMNNAME_M_Shipper_ID })
	public void markAsCarrierAdviceRequested(final I_M_ShipmentSchedule shipmentSchedule)
	{
		// prevent overriding manual carrier from order
		// only on new shipment schedules, after the shipment schedule manual process should be used
		if (InterfaceWrapperHelper.isNew(shipmentSchedule))
		{
			final OrderId orderId = OrderId.ofRepoIdOrNull(shipmentSchedule.getC_Order_ID());
			if (orderId != null)
			{
				final I_C_Order order = orderDAO.getById(orderId);
				final CarrierProductId orderCarrierProductId = CarrierProductId.ofRepoIdOrNull(order.getCarrier_Product_ID());
				if (orderCarrierProductId != null)
				{
					propagateCarrierFieldsFromOrder(shipmentSchedule, order, orderCarrierProductId);
					return;
				}
			}
		}
		else if (InterfaceWrapperHelper.isValueChanged(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_M_Shipper_ID))
		{
			shipmentSchedule.setCarrier_Product_ID(CarrierProductId.toRepoId(null));
			shipmentSchedule.setCarrier_Goods_Type_ID(CarrierGoodsTypeId.toRepoId(null));
			shipmentSchedule.setCarrier_Advising_Status(CarrierAdviseStatus.NotRequested.getCode());

			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoIdOrNull(shipmentSchedule.getM_ShipmentSchedule_ID());
			if (shipmentScheduleId != null)
			{
				shipmentScheduleService.removeAssignedServiceIdsByShipmentScheduleIds(ImmutableSet.of(shipmentScheduleId));
			}
		}

		if (shipmentScheduleService.isEligibleForAutoCarrierAdvise(shipmentSchedule))
		{
			shipmentSchedule.setCarrier_Advising_Status(CarrierAdviseStatus.Requested.getCode());
		}
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_M_ShipmentSchedule.COLUMNNAME_Carrier_Advising_Status })
	public void requestCarrierAdvice(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
		if (isManualPropagationFromOrder(shipmentSchedule))
		{
			final OrderId orderId = OrderId.ofRepoIdOrNull(shipmentSchedule.getC_Order_ID());
			if (orderId != null)
			{
				final Set<CarrierServiceId> orderServiceIds = shipmentScheduleCarrierServiceRepository.getCarrierServiceIdsByOrderId(orderId);
				shipmentScheduleCarrierServiceRepository.assignServicesToShipmentSchedule(shipmentScheduleId, orderServiceIds);
			}
		}
		else if (isMarkedAsCarrierAdviceRequested(shipmentSchedule))
		{
			final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(shipmentSchedule.getC_Async_Batch_ID());
			AdviseDeliveryOrderWorkpackageProcessor.enqueueOnTrxCommit(shipmentScheduleId, asyncBatchId);
		}
	}

	private boolean isMarkedAsCarrierAdviceRequested(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final CarrierAdviseStatus carrierAdviseStatus = CarrierAdviseStatus.ofNullableCode(shipmentSchedule.getCarrier_Advising_Status());
		return carrierAdviseStatus != null && carrierAdviseStatus.isRequested();
	}

	private boolean isManualPropagationFromOrder(final I_M_ShipmentSchedule shipmentSchedule)
	{
		if (!InterfaceWrapperHelper.isNew(shipmentSchedule))
		{
			return false;
		}

		final CarrierAdviseStatus carrierAdviseStatus = CarrierAdviseStatus.ofNullableCode(shipmentSchedule.getCarrier_Advising_Status());
		return carrierAdviseStatus != null && carrierAdviseStatus.isManual();
	}

	/**
	 * Copies Carrier_Product_ID, Carrier_Goods_Type_ID, and Carrier_Advising_Status=Manual from the order header
	 * to the given shipment schedule.
	 *
	 * <p>Status Manual makes the schedule ineligible for auto-advise
	 * ({@link CarrierAdviseStatus#isEligibleForAutoEnqueue()} returns false), so a subsequent automatic
	 * carrier-advise pass will skip it.
	 */
	private void propagateCarrierFieldsFromOrder(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_C_Order order,
			@NonNull final CarrierProductId orderCarrierProductId)
	{
		shipmentSchedule.setCarrier_Product_ID(orderCarrierProductId.getRepoId());

		final CarrierGoodsTypeId goodsTypeId = CarrierGoodsTypeId.ofRepoIdOrNull(order.getCarrier_Goods_Type_ID());
		shipmentSchedule.setCarrier_Goods_Type_ID(CarrierGoodsTypeId.toRepoId(goodsTypeId));

		shipmentSchedule.setCarrier_Advising_Status(CarrierAdviseStatus.Manual.getCode());
	}
}
