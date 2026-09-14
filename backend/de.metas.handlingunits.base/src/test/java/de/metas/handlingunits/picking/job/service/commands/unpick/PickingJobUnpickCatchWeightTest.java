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
}
