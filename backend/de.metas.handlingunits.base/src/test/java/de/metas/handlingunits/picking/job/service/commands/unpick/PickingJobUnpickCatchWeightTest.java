package de.metas.handlingunits.picking.job.service.commands.unpick;

import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.spi.impl.InOutProducerFromShipmentScheduleWithHU;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.DefaultInOutGenerateResult;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.picking.api.Packageable;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_InOutLine;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Un-packing a catch-weight CU that was picked into a TU must detach and re-activate the leaf CU.
 * <p>
 * Backend adaptation of {@code PickingJobUnpickToFloorTest} to the pre-refactor {@code UNPICK}
 * {@link PickingJobStepEvent} command API, where an un-pack targets a whole step/pickFrom (no
 * {@code unpickProductId}/{@code qtyToUnpick}) and is driven by {@code (pickingStepId, pickFromKey)} with a
 * {@code null} target (drop to floor).
 * <p>
 * Scenario: a CU is picked <b>into a TU</b> (a reusable transport crate) under header-level
 * {@code SALES_ORDER} aggregation, then un-packed to the floor. The bug this guards against: when the
 * picking-job step records the picked-to HU as the container TU plus the TU's QR
 * ({@code HUInfo.ofHuIdAndQRCode(cu.getId(), <the TU's QR>)} via {@code toPickingJobStepPickedToHU(tu, cu, ...)},
 * QR from {@code getOrCreateQRCodesByHuId(tu.getId())}), un-pick operates on the TU rather than extracting the
 * leaf CU, leaving the CU <b>Picked and nested inside the TU</b> instead of dropped as a standalone
 * {@code Active} (re-pickable) floor HU. The correct behaviour records the leaf CU + its own QR on the step, so
 * un-pick extracts and re-activates that CU.
 * <p>
 * This test covers the orphaned-Picked-CU status facet. The related runtime failure
 * ({@code assertQRCodeAssignedToHU} via {@code HUTransformService.extractToTopLevel}, driven by the same stale
 * TU-QR pair) and the catch-weight-quantity corruption ride the aggregate-HU + surplus/re-pick path that the
 * in-memory harness cannot model (it materialises picks as plain real HUs, never aggregate HUs); those are covered by the
 * mobile Playwright reproduction {@code e2e/mobile-webui/tests/spec/picking/picking_catchWeight_unpack.spec.js}.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobUnpickCatchWeightTest
{
	private PickingJobTestHelper helper;
	private HUTestHelper huTestHelper;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHandlingUnitsBL handlingUnitsBL;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		huTestHelper = helper.getHuTestHelper();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = huTestHelper.handlingUnitsBL();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
	}

	/** A finite-capacity TU PI for the product, so it can be used as a (new-TU) picking target the CU is packed into. */
	private HuPackingInstructionsId createTuPI(@NonNull final I_M_Product product, final int capacity)
	{
		final I_M_HU_PI tuPI = huTestHelper.createHUDefinition("UNPICK-TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item miItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		final I_M_HU_PI_Item_Product piip = huTestHelper.assignProduct(miItem, product, new BigDecimal(capacity), helper.uomEach);
		piip.setIsDefaultForProduct(true);
		InterfaceWrapperHelper.save(piip);
		return HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID());
	}

	@Test
	void unpickCuFromTU_toFloor_mustNotFailQRAssignment()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P-CW-UNPICK", helper.uomEach);
		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);
		product.setM_Product_Category_ID(BusinessTestHelper.createProductCategory("P-CW-UNPICK-Cat", null).getRepoId());
		InterfaceWrapperHelper.save(product);

		final HuPackingInstructionsId tuPIId = createTuPI(product, 100);

		final HUInfo pickFromVHU = helper.createVHUInfo(productId, "10", "QR-VHU-CW-UNPICK");

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrderCwUnpick");
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.qtyToDeliver("10")
				.build();

		PickingJob pickingJob = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(de.metas.user.UserId.ofRepoId(1234))
								.salesOrderId(orderAndLineId.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(false)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));

		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		assertThat(line.getPickingUnit()).as("line must be a CU pick").isEqualTo(PickingUnit.CU);

		// Header-level (SALES_ORDER aggregation) new-TU picking target: the picked CU is packed INTO this TU.
		pickingJob = helper.pickingJobService.setTUPickingTarget(pickingJob, /*lineId*/ null,
				TUPickingTarget.ofPackingInstructions(tuPIId, "UNPICK-TU-PI"));

		final PickingJobStepId stepId = CollectionUtils.singleElement(
				pickingJob.streamSteps().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

		// Pick 1 CU into the TU (partial TU -> the picked-to HU is recorded as the leaf CU with the TU's QR).
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(pickFromVHU.getQrCode().toScannedCode())
				.qtyPicked(BigDecimal.ONE)
				.qtyRejectedReasonCode(null)
				.build());

		// Resolve the physical TU that now holds the picked CU, and the child CU itself.
		final TUPickingTarget tuTarget = pickingJob.getTuPickingTargetEffective(line.getId()).orElse(null);
		assertThat(tuTarget).as("TU picking target after pick").isNotNull();
		assertThat(tuTarget.isExistingTU()).as("after pick the new-TU target must have materialised into a physical TU").isTrue();
		final HuId tuId = tuTarget.getTuIdNotNull();

		final List<I_M_HU> childrenBefore = handlingUnitsDAO.retrieveIncludedHUs(handlingUnitsBL.getById(tuId));
		assertThat(childrenBefore).as("the TU must hold exactly one child CU after the pick").hasSize(1);
		final HuId cuId = HuId.ofRepoId(childrenBefore.get(0).getM_HU_ID());
		assertThat(childrenBefore.get(0).getHUStatus()).as("child CU is Picked after the pick").isEqualTo(X_M_HU.HUSTATUS_Picked);

		// UN-PACK the picked CU, SKIPPING the target scan (drop to floor: unpickToTargetQRCode == null).
		// OLD whole-step UNPICK: identified by (pickingStepId, pickFromKey), not by product/qty.
		// With the picked-to HU recorded as the container TU + its QR, un-pick operates on the TU rather than
		// extracting the leaf CU, so the CU stays Picked/nested instead of being dropped Active on the floor.
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.UNPICK)
				.qrCode(pickFromVHU.getQrCode().toScannedCode())
				.unpickToTargetQRCode(null) // floor
				.build());

		// The removed CU must be a standalone ACTIVE floor HU, detached from the TU — no orphan Picked CU left.
		final I_M_HU cuAfter = handlingUnitsBL.getById(cuId);
		assertThat(cuAfter.getHUStatus())
				.as("unpicked CU dropped on the floor must be Active (re-pickable), not left Picked")
				.isEqualTo(X_M_HU.HUSTATUS_Active);
		assertThat(cuAfter.getM_HU_Item_Parent_ID())
				.as("unpicked CU must be detached from the TU (no parent)")
				.isLessThanOrEqualTo(0);

		final List<I_M_HU> childrenAfter = handlingUnitsDAO.retrieveIncludedHUs(handlingUnitsBL.getById(tuId));
		assertThat(childrenAfter)
				.as("the pick-to TU must hold NO orphan CU after skip-to-floor unpick")
				.isEmpty();
	}

	/**
	 * Product with a catch UOM (kg): UOM conversion each -> kg flagged as the catch UOM of the product.
	 */
	private ProductId createCatchWeightProductId(@NonNull final String value)
	{
		final ProductId productId = BusinessTestHelper.createProductId(value, helper.uomEach);
		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);
		product.setM_Product_Category_ID(BusinessTestHelper.createProductCategory(value + "-Cat", null).getRepoId());
		InterfaceWrapperHelper.save(product);

		final I_C_UOM_Conversion catchUOMConversion = InterfaceWrapperHelper.newInstance(I_C_UOM_Conversion.class);
		catchUOMConversion.setM_Product_ID(productId.getRepoId());
		catchUOMConversion.setC_UOM_ID(helper.uomEach.getC_UOM_ID());
		catchUOMConversion.setC_UOM_To_ID(huTestHelper.uomKgId.getRepoId());
		catchUOMConversion.setMultiplyRate(BigDecimal.TEN);
		catchUOMConversion.setDivideRate(new BigDecimal("0.1"));
		catchUOMConversion.setIsCatchUOMForProduct(true);
		catchUOMConversion.setIsActive(true);
		InterfaceWrapperHelper.save(catchUOMConversion);

		return productId;
	}

	/**
	 * Two bare-TU picks (2 pcs, 4.100 kg and 2 pcs, 3.900 kg) into one new TU; unpicking the second must keep the weight of the first,
	 * all the way down to the shipment line.
	 */
	@Test
	void unpickOneOfTwoBareTUPicks_keepsOtherPicksWeight()
	{
		final ProductId productId = createCatchWeightProductId("P-CW-UNPICK-2TU");
		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);
		final HuPackingInstructionsId tuPIId = createTuPI(product, 100);

		helper.createVHUInfo(productId, "10", "QR-VHU-CW-2TU-1");
		helper.createVHUInfo(productId, "10", "QR-VHU-CW-2TU-2");

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrderCwUnpick2TU");
		final Packageable packageable = helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.qtyToDeliver("20")
				.build();

		PickingJob pickingJob = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(de.metas.user.UserId.ofRepoId(1234))
								.salesOrderId(orderAndLineId.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(false)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));

		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		pickingJob = helper.pickingJobService.setTUPickingTarget(pickingJob, /*lineId*/ null,
				TUPickingTarget.ofPackingInstructions(tuPIId, "UNPICK-TU-PI"));
		// one step per pick-from HU
		final List<PickingJobStep> steps = pickingJob.streamSteps().collect(java.util.stream.Collectors.toList());
		assertThat(steps).as("one step per pick-from HU").hasSize(2);
		final PickingJobStepId stepId = steps.get(0).getId();
		final PickingJobStepId stepId2 = steps.get(1).getId();

		// first pick: 2 pcs, 4.100 kg
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(steps.get(0).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU().getQrCode().toScannedCode())
				.qtyPicked(new BigDecimal("2"))
				.catchWeight(new BigDecimal("4.100"))
				.build());

		final HuId tuId = pickingJob.getTuPickingTargetEffective(line.getId()).orElseThrow(() -> new AdempiereException("no TU target")).getTuIdNotNull();

		// Branch probe (do not assert): the harness defines WeightNet as top-down, so the TU's own WeightNet after the first pick may differ from production
		final IAttributeStorage tuAttributes = huTestHelper.createMutableHUContext()
				.getHUAttributeStorageFactory()
				.getAttributeStorage(handlingUnitsBL.getById(tuId));
		System.out.println("TASK8-PROBE: TU WeightNet after first pick = " + tuAttributes.getValueAsBigDecimal(Weightables.ATTR_WeightNet));

		// second pick: 2 pcs, 3.900 kg
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId2)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(steps.get(1).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU().getQrCode().toScannedCode())
				.qtyPicked(new BigDecimal("2"))
				.catchWeight(new BigDecimal("3.900"))
				.build());

		// unpick 2 pcs of the product (LIFO => the second pick)
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.eventType(PickingJobStepEventType.UNPICK)
				.qrCode(steps.get(1).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU().getQrCode().toScannedCode())
				.unpickProductId(productId)
				.qtyToUnpick(new BigDecimal("2"))
				.unpickToTargetQRCode(null)
				.build());

		// the remaining record keeps the first pick's weight
		final I_M_ShipmentSchedule_QtyPicked remainingRecord = CollectionUtils.singleElement(
				Services.get(IQueryBL.class).createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
						.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, packageable.getShipmentScheduleId())
						.create()
						.list());
		assertThat(remainingRecord.getQtyDeliveredCatch()).as("remaining record QtyDeliveredCatch").isEqualByComparingTo("4.100");
		assertThat(remainingRecord.getCatch_UOM_ID()).as("remaining record Catch_UOM_ID").isEqualTo(huTestHelper.uomKgId.getRepoId());

		// ... and so does the shipment line, built through the real breakdown + producer
		final IHUContext huContext = huTestHelper.createMutableHUContext();
		final List<ShipmentScheduleWithHU> candidates = Services.get(IHUShipmentScheduleBL.class)
				.createCandidatesForQtyPicked(remainingRecord, huContext, M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER);
		assertThat(candidates).as("candidates").isNotEmpty();

		final InOutGenerateResult result = Services.get(ITrxItemProcessorExecutorService.class)
				.<ShipmentScheduleWithHU, InOutGenerateResult>createExecutor()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setProcessor(new InOutProducerFromShipmentScheduleWithHU(new DefaultInOutGenerateResult(true)))
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.process(candidates);

		final I_M_InOutLine shipmentLine = CollectionUtils.singleElement(
				Services.get(IQueryBL.class).createQueryBuilder(I_M_InOutLine.class).create().list());
		assertThat(result.getInOuts()).as("generated shipments").isNotEmpty();
		assertThat(shipmentLine.getQtyDeliveredCatch()).as("shipment line QtyDeliveredCatch").isEqualByComparingTo("4.100");
	}
}
