/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.shipmentschedule.api.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IInOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.handlingunits.shipmentschedule.api.QtyToDeliverMap;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule;
import de.metas.inout.InOutId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;

import java.util.List;
import java.util.Set;

import static de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromShipmentSchedules.computeShippingDateRule;

/**
 * Test class used for legacy integration tests of {@link de.metas.handlingunits.shipmentschedule.api.HUShippingFacade}.
 * <p>
 * The purpose is to bypass the workpackage processing and directly call  {@link IInOutProducerFromShipmentScheduleWithHU#createShipments(java.util.List)}
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ShipmentServiceTestImpl implements IShipmentService
{
	@NonNull private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	@NonNull private final ShipmentScheduleWithHUService shipmentScheduleWithHUService;
	@NonNull private final PickingJobScheduleService pickingJobScheduleService;

	public static ShipmentServiceTestImpl newInstanceForUnitTesting()
	{
		Adempiere.enableUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(
				ShipmentServiceTestImpl.class,
				() -> new ShipmentServiceTestImpl(
						ShipmentScheduleWithHUService.newInstanceForUnitTesting(),
						PickingJobScheduleService.newInstanceForUnitTesting()
				)
		);
	}

	/**
	 * Always creates shipments synchronously and directly.
	 * Ignores {@link GenerateShipmentsForSchedulesRequest#isWaitForShipments()}.
	 */
	@NonNull
	@VisibleForTesting
	public Set<InOutId> generateShipmentsForScheduleIds(@NonNull final GenerateShipmentsForSchedulesRequest request)
	{
		final ShipmentScheduleAndJobScheduleIdSet scheduleIds = request.getScheduleIds();
		if (scheduleIds == null || scheduleIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final List<ShipmentScheduleWithHU> shipmentScheduleWithHUS = shipmentScheduleWithHUService.prepareShipmentSchedulesWithHU(
				PrepareForShipmentSchedulesRequest.builder()
						.schedules(pickingJobScheduleService.getShipmentScheduleAndJobSchedulesCollection(scheduleIds))
						.quantityTypeToUse(request.getQuantityTypeToUse())
						.onTheFlyPickToPackingInstructions(request.isOnTheFlyPickToPackingInstructions())
						.qtyToDeliverOverrides(QtyToDeliverMap.EMPTY)
						.isFailIfNoPickedHUs(true) // backwards compatibility: true - fail if no picked HUs found
						.build()
		);

		final CalculateShippingDateRule calculateShippingDateRule = computeShippingDateRule(request.getIsShipDateToday());

		return huShipmentScheduleBL
				.createInOutProducerFromShipmentSchedule()
				.setProcessShipments(request.getIsCompleteShipment())
				.computeShipmentDate(calculateShippingDateRule)
				.setTrxItemExceptionHandler(FailTrxItemExceptionHandler.instance)
				.createShipments(shipmentScheduleWithHUS)
				.getInOuts()
				.stream()
				.map(I_M_InOut::getM_InOut_ID)
				.map(InOutId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
