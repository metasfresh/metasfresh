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

import de.metas.deliveryplanning.DeliveryPlanningAllocRepository;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningRepository;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.DeliveryInstructionRepository;
import de.metas.deliveryplanning.DeliveryInstructionService;
import de.metas.document.dimension.DimensionService;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.deliveryplanning.MeansOfTransportationService;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.ProcessInfo;
import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The Qty the two planning-window generate processes PRE-FILL.
 * <p>
 * Both are {@code mandatory = true} and neither carried a default - not in Java, and not as an
 * {@code AD_Process_Para.DefaultValue} (the Qty para rows in {@code 5673510_..._GenerateReceipt_process.sql}
 * and {@code 5673600_..._GenerateShipment_process.sql} are inserted without that column, while siblings like
 * MovementDate get {@code @#Date@}). So the operator was offered an empty/0 quantity and had to retype the
 * figure the planning already holds - most visibly on a SPLIT planning, where the right number is that
 * planning's own share rather than anything the operator can infer from the screen.
 * <p>
 * The HU receive path on the receipt-disposition window already gets this right by capping its LU/TU
 * configuration to the planning's share; these two processes sit beside it and offered nothing.
 */
class M_Delivery_Planning_GenerateQtyDefaultTest
{
	private static final BigDecimal PLANNED_DISCHARGE = new BigDecimal("7");
	private static final BigDecimal PLANNED_LOAD = new BigDecimal("9");

	private I_M_Delivery_Planning deliveryPlanning;
	private DeliveryPlanningId deliveryPlanningId;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		// The processes' `helper` field initialiser runs at construction and resolves these beans, before a test
		// can swap in its stub - so they must exist even though every assertion here goes through the stub.
		registerBeansTheHelperResolvesAtConstruction();

		deliveryPlanning = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		deliveryPlanning.setPlannedDischargeQuantity(PLANNED_DISCHARGE);
		deliveryPlanning.setPlannedLoadedQuantity(PLANNED_LOAD);
		InterfaceWrapperHelper.save(deliveryPlanning);

		deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID());
	}

	private static void registerBeansTheHelperResolvesAtConstruction()
	{
		final DeliveryPlanningRepository deliveryPlanningRepository = new DeliveryPlanningRepository(mock(DimensionService.class));
		final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository = new DeliveryPlanningAllocRepository();
		final DeliveryInstructionService deliveryInstructionService = new DeliveryInstructionService(
				deliveryPlanningRepository,
				deliveryPlanningAllocRepository,
				new DeliveryInstructionRepository(mock(DimensionService.class)),
				new MPackageRepository());

		SpringContextHolder.registerJUnitBean(DeliveryPlanningService.class, new DeliveryPlanningService(
				mock(ShipperRepository.class),
				deliveryPlanningRepository,
				deliveryPlanningAllocRepository,
				deliveryInstructionService,
				mock(DimensionService.class),
				mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard()));
		SpringContextHolder.registerJUnitBean(DeliveryPlanningRepository.class, deliveryPlanningRepository);
		SpringContextHolder.registerJUnitBean(ShipmentService.class, mock(ShipmentService.class));
		SpringContextHolder.registerJUnitBean(ReceiptFromReceiptScheduleService.class, mock(ReceiptFromReceiptScheduleService.class));
		SpringContextHolder.registerJUnitBean(
				PurchaseOrderToShipperTransportationRepository.class,
				mock(PurchaseOrderToShipperTransportationRepository.class));
	}

	private static IProcessDefaultParameter parameterNamed(final String columnName)
	{
		final IProcessDefaultParameter parameter = mock(IProcessDefaultParameter.class);
		when(parameter.getColumnName()).thenReturn(columnName);
		return parameter;
	}

	private ProcessInfo processInfo()
	{
		return ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setRecord(I_M_Delivery_Planning.Table_Name, deliveryPlanning.getM_Delivery_Planning_ID())
				.build();
	}

	@Test
	@DisplayName("generate RECEIPT pre-fills Qty with the planning's planned DISCHARGE quantity")
	void receiptQtyDefaultsToPlannedDischarge()
	{
		final DeliveryPlanningGenerateProcessesHelper mockHelper = mock(DeliveryPlanningGenerateProcessesHelper.class);
		when(mockHelper.getPlannedDischargeQuantity(deliveryPlanningId)).thenReturn(PLANNED_DISCHARGE);

		final M_Delivery_Planning_GenerateReceipt process = new M_Delivery_Planning_GenerateReceipt();
		process.helper = mockHelper;
		process.init(processInfo());

		assertThat(process.getParameterDefaultValue(parameterNamed("Qty")))
				.as("the operator must be offered this planning's own share, not an empty field")
				.isEqualTo(PLANNED_DISCHARGE);
	}

	@Test
	@DisplayName("generate SHIPMENT pre-fills Qty with the planning's planned LOAD quantity")
	void shipmentQtyDefaultsToPlannedLoad()
	{
		final DeliveryPlanningGenerateProcessesHelper mockHelper = mock(DeliveryPlanningGenerateProcessesHelper.class);
		when(mockHelper.getPlannedLoadedQuantity(deliveryPlanningId)).thenReturn(PLANNED_LOAD);

		final M_Delivery_Planning_GenerateShipment process = new M_Delivery_Planning_GenerateShipment();
		process.helper = mockHelper;
		process.init(processInfo());

		assertThat(process.getParameterDefaultValue(parameterNamed("Qty")))
				.as("the outgoing mirror of the receipt default - the planning's planned LOAD")
				.isEqualTo(PLANNED_LOAD);
	}

	@Test
	@DisplayName("the REAL helper reads discharge and load off the planning, and does not cross them over")
	void realHelperReadsEachQuantityFromItsOwnColumn()
	{
		// Deliberately NOT the mock: the two getters are new, adjacent and near-identical mirrors, so the
		// mistake they invite is a crossed delegate - which a stubbed helper can never catch, because the stub
		// replaces the very body that would be wrong. The two values differ so a swap cannot pass.
		final DeliveryPlanningGenerateProcessesHelper realHelper = DeliveryPlanningGenerateProcessesHelper.newInstance();

		assertThat(realHelper.getPlannedDischargeQuantity(deliveryPlanningId))
				.as("discharge must come from PlannedDischargeQuantity")
				.isEqualByComparingTo(PLANNED_DISCHARGE);
		assertThat(realHelper.getPlannedLoadedQuantity(deliveryPlanningId))
				.as("load must come from PlannedLoadedQuantity")
				.isEqualByComparingTo(PLANNED_LOAD);
	}

	@Test
	@DisplayName("a planning that states no quantity leaves the field EMPTY, never a pre-filled 0")
	void zeroQuantityLeavesTheFieldEmpty()
	{
		// A split whose remainder was nothing still creates its siblings carrying 0, and those plannings are
		// open and reach this dialog. A pre-filled 0 reads as an entered value and is only refused after submit.
		final DeliveryPlanningGenerateProcessesHelper mockHelper = mock(DeliveryPlanningGenerateProcessesHelper.class);
		when(mockHelper.getPlannedDischargeQuantity(deliveryPlanningId)).thenReturn(BigDecimal.ZERO);

		final M_Delivery_Planning_GenerateReceipt process = new M_Delivery_Planning_GenerateReceipt();
		process.helper = mockHelper;
		process.init(processInfo());

		assertThat(process.getParameterDefaultValue(parameterNamed("Qty")))
				.as("empty visibly asks for input; 0 looks answered and fails only at assumePositive")
				.isNull();
	}

	@Test
	@DisplayName("an unrelated parameter is still left to its own default")
	void unrelatedParameterIsUntouched()
	{
		final DeliveryPlanningGenerateProcessesHelper mockHelper = mock(DeliveryPlanningGenerateProcessesHelper.class);

		final M_Delivery_Planning_GenerateReceipt process = new M_Delivery_Planning_GenerateReceipt();
		process.helper = mockHelper;
		process.init(processInfo());

		assertThat(process.getParameterDefaultValue(parameterNamed("SomeOtherParam")))
				.as("only Qty is claimed here; everything else keeps whatever the AD or the process says")
				.isNull();
	}
}
