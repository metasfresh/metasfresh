/*
 * #%L
 * de.metas.deliveryplanning.webui
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.deliveryplanning.webui.process;

import de.metas.deliveryplanning.DeliveryInstructionRepository;
import de.metas.deliveryplanning.DeliveryInstructionService;
import de.metas.deliveryplanning.DeliveryPlanningAllocRepository;
import de.metas.deliveryplanning.DeliveryPlanningRepository;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.DeliveryStatusColorPaletteService;
import de.metas.deliveryplanning.MeansOfTransportationService;
import de.metas.document.dimension.DimensionService;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@code GenerateReceipt} and {@code GenerateShipment} refuse a closed planning in their precondition, the
 * same guard {@code GenerateShortageOverage} carries (proven in {@code de.metas.deliveryplanning.base}).
 * <p>
 * The guard is checked BEFORE either process reads a receipt/shipment schedule, so a closed selection needs no
 * further fixture.
 */
class DeliveryPlanningGenerateClosedGuardTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final DeliveryPlanningRepository deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));
		final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository = new DeliveryPlanningAllocRepository();
		final DeliveryInstructionRepository deliveryInstructionRepository = new DeliveryInstructionRepository(mock(DimensionService.class));
		final DeliveryInstructionService deliveryInstructionService = new DeliveryInstructionService(
				deliveryPlanningRepository, deliveryPlanningAllocRepository, deliveryInstructionRepository, new MPackageRepository());

		final DeliveryPlanningService deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				deliveryPlanningAllocRepository,
				deliveryInstructionRepository,
				deliveryInstructionService,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		SpringContextHolder.registerJUnitBean(DeliveryPlanningService.class, deliveryPlanningService);
		SpringContextHolder.registerJUnitBean(ShipmentService.class, Mockito.mock(ShipmentService.class));
		SpringContextHolder.registerJUnitBean(
				PurchaseOrderToShipperTransportationRepository.class,
				Mockito.mock(PurchaseOrderToShipperTransportationRepository.class));
	}

	private static int closedDeliveryPlanning()
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setIsClosed(true);
		InterfaceWrapperHelper.save(record);
		return record.getM_Delivery_Planning_ID();
	}

	private static IProcessPreconditionsContext contextSelecting(final int deliveryPlanningId)
	{
		final IProcessPreconditionsContext context = mock(IProcessPreconditionsContext.class);
		when(context.isSingleSelection()).thenReturn(true);
		when(context.getSingleSelectedRecordId()).thenReturn(deliveryPlanningId);
		return context;
	}

	private static void assertRefusedAsClosed(final ProcessPreconditionsResolution resolution, final int deliveryPlanningId)
	{
		assertThat(resolution.isAccepted()).isFalse();
		assertThat(resolution.getRejectReason().translate("en_US"))
				.isEqualTo(DeliveryPlanningService.MSG_M_Delivery_Planning_Closed.toAD_Message() + " - " + deliveryPlanningId);
	}

	@Test
	@DisplayName("GenerateReceipt refuses a closed planning, naming it")
	void generateReceipt_closedPlanningIsRefused()
	{
		final int closedId = closedDeliveryPlanning();

		final ProcessPreconditionsResolution resolution =
				new M_Delivery_Planning_GenerateReceipt().checkPreconditionsApplicable(contextSelecting(closedId));

		assertRefusedAsClosed(resolution, closedId);
	}

	@Test
	@DisplayName("GenerateShipment refuses a closed planning, naming it")
	void generateShipment_closedPlanningIsRefused()
	{
		final int closedId = closedDeliveryPlanning();

		final ProcessPreconditionsResolution resolution =
				new M_Delivery_Planning_GenerateShipment().checkPreconditionsApplicable(contextSelecting(closedId));

		assertRefusedAsClosed(resolution, closedId);
	}
}
