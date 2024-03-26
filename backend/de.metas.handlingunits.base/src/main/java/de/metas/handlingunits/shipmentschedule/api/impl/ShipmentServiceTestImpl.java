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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IInOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService;
import de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;

import java.util.List;
import java.util.Set;

import static de.metas.handlingunits.shipmentschedule.async.GenerateInOutFromShipmentSchedules.computeShippingDateRule;

/**
 * Test class used for legacy integration tests of {@link de.metas.handlingunits.shipmentschedule.api.HUShippingFacade}.
 * <p>
 * The purpose is to bypass the workpackage processing and directly call  {@link IInOutProducerFromShipmentScheduleWithHU#createShipments(java.util.List)}
 */
public class ShipmentServiceTestImpl implements IShipmentService
{
	private final ShipmentScheduleWithHUService shipmentScheduleWithHUService;

	public ShipmentServiceTestImpl(final ShipmentScheduleWithHUService shipmentScheduleWithHUService)
	{
		this.shipmentScheduleWithHUService = shipmentScheduleWithHUService;
	}

	/**
	 * Always creates shipments synchronously and directly.
	 * Ignores {@link GenerateShipmentsForSchedulesRequest#isWaitForShipments()}.
	 */
	@NonNull
	@VisibleForTesting
	public Set<InOutId> generateShipmentsForScheduleIds(@NonNull final GenerateShipmentsForSchedulesRequest request)
	{
		final List<de.metas.inoutcandidate.model.I_M_ShipmentSchedule> shipmentSchedules = InterfaceWrapperHelper.loadByRepoIdAwares(request.getScheduleIds(), de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);

		final List<ShipmentScheduleWithHU> shipmentScheduleWithHUS = shipmentScheduleWithHUService
				.createShipmentSchedulesWithHU(shipmentSchedules,
											   request.getQuantityTypeToUse(),
											   request.isOnTheFlyPickToPackingInstructions(),
											   ImmutableMap.of(),
											   true  /* backwards compatibility: true - fail if no picked HUs found*/
				);

		final CalculateShippingDateRule calculateShippingDateRule = computeShippingDateRule(request.getIsShipDateToday(), null);

		return Services.get(IHUShipmentScheduleBL.class)
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
