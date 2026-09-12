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
import org.compiere.model.X_M_Delivery_Planning;
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
 * The {@code Qty} override on the single-row generate is written back onto the planning's own planned figure - a
 * shipment occupies the load end, a receipt the discharge end. The production generation chain is stubbed via
 * the package-visible {@code helper} field, so only the two classes' OWN {@code doIt()} logic is under test.
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
		deliveryPlanning.setC_BPartner_ID(2000000);
		deliveryPlanning.setTransportDirection(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
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
		// The write-back itself is real production logic under test, so it is forwarded to the real JUnit-registered
		// repository. NOTE what this does NOT cover: the doAnswer hardcodes the CORRECT repository method, so the
		// helper -> service -> repository routing is not under test here - swapping writeBackPlannedLoadedQuantity to
		// call setPlannedDischargeQuantity would keep this test green. Cucumber pins that routing in both directions.
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
	@DisplayName("GenerateReceipt.doIt() leaves PlannedDischargeQuantity alone, so a short receive stays visible")
	void generateReceipt_doesNotOverwritePlannedDischargeQuantity()
	{
		final UomId uomId = createUom();
		final ProductId productId = createProduct(uomId);
		final I_M_Delivery_Planning deliveryPlanning = createDeliveryPlanning(productId, uomId);
		final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanning.getM_Delivery_Planning_ID());

		// The plan: 4 expected. The receive below brings 3.
		deliveryPlanningRepository.setPlannedDischargeQuantity(deliveryPlanningId, Quantitys.of(new BigDecimal("4"), productId));

		final M_Delivery_Planning_GenerateReceipt process = new M_Delivery_Planning_GenerateReceipt();
		final DeliveryPlanningGenerateProcessesHelper mockHelper = mock(DeliveryPlanningGenerateProcessesHelper.class);
		process.helper = mockHelper;
		process.init(processInfoFor(deliveryPlanning));

		final DeliveryPlanningGenerateReceiptResult receiptResult = DeliveryPlanningGenerateReceiptResult.builder()
				.receiptId(InOutId.ofRepoId(1))
				.receivedVHUId(HuId.ofRepoId(1))
				.productId(productId)
				.qty(Quantitys.of(new BigDecimal("3"), productId))
				.build();
		when(mockHelper.generateReceipt(ArgumentMatchers.argThat(
				request -> request.getQtyToReceiveBD().compareTo(new BigDecimal("3")) == 0
						&& request.getDeliveryPlanningId().equals(deliveryPlanningId))))
				.thenReturn(receiptResult);

		setPrivateField(process, "p_ReceiptDate", Instant.parse("2026-09-03T00:00:00Z"));
		setPrivateField(process, "p_QtyBD", new BigDecimal("3"));

		process.doIt();

		InterfaceWrapperHelper.refresh(deliveryPlanning);
		// A planning is exactly ONE receipt, so the missing 1 does not stay open on it - it becomes a new
		// planning. Which is only visible if the PLAN survives: planned 4 against actual 3 says "4 was
		// expected, 3 arrived". Overwriting the plan with what arrived would read 3/3 and hide the shortfall.
		assertThat(deliveryPlanning.getPlannedDischargeQuantity())
				.as("PlannedDischargeQuantity is left as planned, not overwritten with what was received")
				.isEqualByComparingTo("4");
	}
}
