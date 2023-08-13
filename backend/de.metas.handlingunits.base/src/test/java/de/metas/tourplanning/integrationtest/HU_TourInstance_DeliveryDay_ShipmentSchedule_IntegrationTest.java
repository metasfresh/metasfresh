package de.metas.tourplanning.integrationtest;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleRepository;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.handlingunits.tourplanning.spi.impl.HUShipmentScheduleDeliveryDayHandlerTest;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;

public class HU_TourInstance_DeliveryDay_ShipmentSchedule_IntegrationTest extends TourInstance_DeliveryDay_ShipmentSchedule_IntegrationTest
{
	@Override
	protected void afterInit()
	{
		super.afterInit();

		final DDOrderLowLevelDAO ddOrderLowLevelDAO = new DDOrderLowLevelDAO();
		final HUReservationService huReservationService = new HUReservationService(new HUReservationRepository());
		final DDOrderMoveScheduleService ddOrderMoveScheduleService = new DDOrderMoveScheduleService(
				ddOrderLowLevelDAO,
				new DDOrderMoveScheduleRepository(),
				huReservationService);
		final DDOrderLowLevelService ddOrderLowLevelService = new DDOrderLowLevelService(ddOrderLowLevelDAO);
		final DDOrderService ddOrderService = new DDOrderService(ddOrderLowLevelDAO, ddOrderLowLevelService, ddOrderMoveScheduleService);
		new de.metas.handlingunits.model.validator.Main(
				ddOrderMoveScheduleService,
				ddOrderService,
				new PickingBOMService()).setupTourPlanning();
	}

	@Override
	protected boolean performTourPlanningRelevantChange(final de.metas.tourplanning.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		super.performTourPlanningRelevantChange(shipmentSchedule);

		final I_M_ShipmentSchedule huShipmentSchedule = InterfaceWrapperHelper.create(shipmentSchedule, I_M_ShipmentSchedule.class);

		// Increase QryOrdered_LU by 10
		huShipmentSchedule.setQtyOrdered_LU(huShipmentSchedule.getQtyOrdered_LU().add(BigDecimal.valueOf(10)));

		// we expect that changing QtyOrdered_LU to be a releavant change for tour planning
		return true;
	}

	@Override
	protected I_M_DeliveryDay_Alloc assertDeliveryDayAlloc(final I_M_DeliveryDay deliveryDayExpected,
														   final de.metas.tourplanning.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final de.metas.tourplanning.model.I_M_DeliveryDay_Alloc alloc = super.assertDeliveryDayAlloc(
				deliveryDayExpected,
				InterfaceWrapperHelper.create(shipmentSchedule, de.metas.tourplanning.model.I_M_ShipmentSchedule.class));

		final I_M_DeliveryDay_Alloc huAlloc = InterfaceWrapperHelper.create(alloc, I_M_DeliveryDay_Alloc.class);
		final I_M_ShipmentSchedule huShipmentSchedule = InterfaceWrapperHelper.create(shipmentSchedule, I_M_ShipmentSchedule.class);
		HUShipmentScheduleDeliveryDayHandlerTest.assertEquals(huShipmentSchedule, huAlloc);

		return huAlloc;
	}

}
