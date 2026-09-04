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

import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryInstructionRepository;
import de.metas.deliveryplanning.DeliveryInstructionService;
import de.metas.deliveryplanning.DeliveryPlanningAllocRepository;
import de.metas.deliveryplanning.DeliveryPlanningRepository;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.MeansOfTransportationService;
import de.metas.document.dimension.DimensionService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.inout.InOutId;
import de.metas.process.ProcessInfo;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.shipping.PurchaseOrderToShipperTransportationRepository;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Task Q12: the {@code Qty} override on the single-row generate is written back onto the planning's
 * own planned figure - a shipment occupies the load end, a receipt the discharge end (spec direction
 * rule). The production shipment/receipt generation chain (async batch + {@code ShipmentService}, real
 * HU allocation) is not driven here - {@link DeliveryPlanningGenerateProcessesHelper#generateShipment}
 * and {@code #generateReceipt} are stubbed via the package-visible {@code helper} field, exactly the
 * seam {@link DeliveryPlanningGenerateClosedGuardTest} already relies on for the precondition tests in
 * this same package - so that only the two production classes' OWN doIt() logic (parameter validation +
 * write-back) is under test.
 */
class M_Delivery_Planning_GenerateWriteBackTest
{
	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;
	private DeliveryInstructionRepository deliveryInstructionRepository;
	private DeliveryInstructionService deliveryInstructionService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		deliveryPlanningRepository = new DeliveryPlanningRepository(mock(DimensionService.class));
		deliveryPlanningAllocRepository = new DeliveryPlanningAllocRepository();
		deliveryInstructionRepository = new DeliveryInstructionRepository(mock(DimensionService.class));
		deliveryInstructionService = new DeliveryInstructionService(
				deliveryPlanningRepository, deliveryPlanningAllocRepository, deliveryInstructionRepository, new MPackageRepository());

		final DeliveryPlanningService deliveryPlanningService = new DeliveryPlanningService(
				mock(ShipperRepository.class),
				deliveryPlanningRepository,
				deliveryPlanningAllocRepository,
				deliveryInstructionService,
				mock(DimensionService.class),
				mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		SpringContextHolder.registerJUnitBean(DeliveryPlanningService.class, deliveryPlanningService);
		SpringContextHolder.registerJUnitBean(DeliveryPlanningRepository.class, deliveryPlanningRepository);
		SpringContextHolder.registerJUnitBean(ShipmentService.class, mock(ShipmentService.class));
		// The helper now delegates the receipt itself to the shared receive path; newInstance() therefore
		// resolves it, although these tests replace the whole helper with a stub before anything runs.
		SpringContextHolder.registerJUnitBean(ReceiptFromReceiptScheduleService.class, mock(ReceiptFromReceiptScheduleService.class));
		SpringContextHolder.registerJUnitBean(
				PurchaseOrderToShipperTransportationRepository.class,
				mock(PurchaseOrderToShipperTransportationRepository.class));
	}

	/** The test POJO store has no pre-seeded system UOMs, so a real one is created here (not {@link UomId#EACH}). */
	private static UomId createUom()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setStdPrecision(2);
		uom.setCostingPrecision(2);
		InterfaceWrapperHelper.save(uom);
		return UomId.ofRepoId(uom.getC_UOM_ID());
	}

	private static ProductId createProduct(final UomId uomId)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setC_UOM_ID(uomId.getRepoId());
		InterfaceWrapperHelper.save(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private static I_M_Delivery_Planning createDeliveryPlanning(final ProductId productId, final UomId uomId)
	{
		final I_M_Delivery_Planning deliveryPlanning = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		deliveryPlanning.setM_Product_ID(productId.getRepoId());
		deliveryPlanning.setC_UOM_ID(uomId.getRepoId());
		InterfaceWrapperHelper.save(deliveryPlanning);
		return deliveryPlanning;
	}

	/** Sets a private {@code @Param}-annotated field directly, bypassing the AD-parameter-loading framework. */
	private static void setPrivateField(final Object target, final String fieldName, final Object value)
	{
		try
		{
			final Field field = target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(target, value);
		}
		catch (final ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static ProcessInfo processInfoFor(final I_M_Delivery_Planning deliveryPlanning)
	{
		return ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setRecord(I_M_Delivery_Planning.Table_Name, deliveryPlanning.getM_Delivery_Planning_ID())
				.build();
	}

	@Test
	@DisplayName("GenerateShipment.doIt() writes the Qty override back onto PlannedLoadedQuantity")
	void generateShipment_writesBackPlannedLoadedQuantity()
	{
		final UomId uomId = createUom();
		final ProductId productId = createProduct(uomId);
		final I_M_Delivery_Planning deliveryPlanning = createDeliveryPlanning(productId, uomId);

		final M_Delivery_Planning_GenerateShipment process = new M_Delivery_Planning_GenerateShipment();
		final DeliveryPlanningGenerateProcessesHelper mockHelper = mock(DeliveryPlanningGenerateProcessesHelper.class);
		// The write-back itself (helper.writeBackPlannedLoadedQuantity) is real production logic under test here,
		// not part of the heavy chain being stubbed - forward it to the real, JUnit-registered repository so the
		// process's doIt() write-back is genuinely exercised and observable below, exactly as it was before that
		// write-back moved from an inline SpringContextHolder.getBean(DeliveryPlanningRepository.class) call into
		// this helper method (Task Q12 fix round: JavaProcess.doIt() must not grab a @Repository directly).
		// NOTE what this no longer covers: the doAnswer hardcodes the CORRECT repository method, so the
		// helper -> service -> repository routing is not under test here - swapping writeBackPlannedLoadedQuantity
		// to call setPlannedDischargeQuantity would keep this test green. That routing is pinned end-to-end by
		// cucumber, in both directions: @Id:S31789_TC_Q11_OutgoingCompletionWritesBothEnds asserts
		// PlannedLoadedQuantity and @Id:S31789_TC_Q11_GenerateReceiptProcessOrdering asserts
		// PlannedDischargeQuantity, each after the real generate process.
		Mockito.doAnswer(invocation -> {
			deliveryPlanningRepository.setPlannedLoadedQuantity(invocation.getArgument(0), invocation.getArgument(1));
			return null;
		}).when(mockHelper).writeBackPlannedLoadedQuantity(ArgumentMatchers.any(), ArgumentMatchers.any());
		process.helper = mockHelper;
		process.init(processInfoFor(deliveryPlanning));

		setPrivateField(process, "p_DeliveryDate", LocalDate.of(2026, 9, 3));
		setPrivateField(process, "p_QtyBD", new BigDecimal("6"));

		process.doIt();

		Mockito.verify(mockHelper).generateShipment(ArgumentMatchers.argThat(
				request -> request.getQtyToShipBD().compareTo(new BigDecimal("6")) == 0
						&& request.getDeliveryPlanningId().equals(DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID()))));

		InterfaceWrapperHelper.refresh(deliveryPlanning);
		assertThat(deliveryPlanning.getPlannedLoadedQuantity())
				.as("PlannedLoadedQuantity written back from the Qty override")
				.isEqualByComparingTo("6");
	}

	@Test
	@DisplayName("GenerateReceipt.doIt() writes the Qty override back onto PlannedDischargeQuantity")
	void generateReceipt_writesBackPlannedDischargeQuantity()
	{
		final UomId uomId = createUom();
		final ProductId productId = createProduct(uomId);
		final I_M_Delivery_Planning deliveryPlanning = createDeliveryPlanning(productId, uomId);
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID());

		final M_Delivery_Planning_GenerateReceipt process = new M_Delivery_Planning_GenerateReceipt();
		final DeliveryPlanningGenerateProcessesHelper mockHelper = mock(DeliveryPlanningGenerateProcessesHelper.class);
		// See generateShipment_writesBackPlannedLoadedQuantity's comment: the write-back is real logic under
		// test, forwarded to the real repository rather than left as a no-op mock stub.
		Mockito.doAnswer(invocation -> {
			deliveryPlanningRepository.setPlannedDischargeQuantity(invocation.getArgument(0), invocation.getArgument(1));
			return null;
		}).when(mockHelper).writeBackPlannedDischargeQuantity(ArgumentMatchers.any(), ArgumentMatchers.any());
		process.helper = mockHelper;
		process.init(processInfoFor(deliveryPlanning));

		final DeliveryPlanningGenerateReceiptResult receiptResult = DeliveryPlanningGenerateReceiptResult.builder()
				.receiptId(InOutId.ofRepoId(1))
				.receivedVHUId(HuId.ofRepoId(1))
				.productId(productId)
				.qty(Quantitys.of(new BigDecimal("4"), productId))
				.build();
		when(mockHelper.generateReceipt(ArgumentMatchers.argThat(
				request -> request.getQtyToReceiveBD().compareTo(new BigDecimal("4")) == 0
						&& request.getDeliveryPlanningId().equals(deliveryPlanningId))))
				.thenReturn(receiptResult);

		setPrivateField(process, "p_ReceiptDate", Instant.parse("2026-09-03T00:00:00Z"));
		setPrivateField(process, "p_QtyBD", new BigDecimal("4"));

		process.doIt();

		InterfaceWrapperHelper.refresh(deliveryPlanning);
		assertThat(deliveryPlanning.getPlannedDischargeQuantity())
				.as("PlannedDischargeQuantity written back from the Qty override")
				.isEqualByComparingTo("4");
	}
}
