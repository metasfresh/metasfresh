package de.metas.handlingunits.picking.job.service.commands.unpick;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit-tests the LIFO selection of {@link PickingJobUnPickCommand#selectLifoUnpickInstructions} for a partial
 * (subset) unpick spread across MULTIPLE top-level CUs — the branch the single-CU Playwright spec
 * ({@code picking_partial_unpack_cu.spec.js}) cannot reach (it only has one picked CU).
 * <p>
 * Two bare CUs are picked in two separate steps (older first, newer last); a subset unpick of more than the
 * newest CU's qty must take the WHOLE newest CU and BOUNDARY-SPLIT the older one (LIFO, newest-first). We
 * assert the SELECTION (which step is whole vs the boundary, and the boundary carve qty) rather than executing
 * the physical split: per this module's CLAUDE.md the in-memory HU harness does not materialise a partial-CU
 * boundary split; the executed split is proven against the running stack by the Playwright spec above.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobUnPick_LifoSelection_Test
{
	private PickingJobTestHelper helper;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
	}

	@Test
	void subsetUnpick_takesWholeNewestCU_thenBoundarySplitsOlderCU()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P1", helper.uomEach);
		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);
		product.setM_Product_Category_ID(BusinessTestHelper.createProductCategory("P1-Cat-LIFO", null).getRepoId());
		InterfaceWrapperHelper.save(product);

		// Two source VHUs of 6 each → the planner builds one step per source HU for the 12-PCE line.
		final HUInfo vhuOlder = helper.createVHUInfo(productId, "6", "QR-VHU-OLDER");
		final HUInfo vhuNewer = helper.createVHUInfo(productId, "6", "QR-VHU-NEWER");

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrderLIFO");
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.qtyToDeliver("12")
				.build();

		PickingJob pickingJob = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(UserId.ofRepoId(1234))
								.salesOrderId(orderAndLineId.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(false) // build a plan over the two HUs
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		final PickingJobLine line = pickingJob.getLines().get(0);

		// Pick the OLDER CU first, then the NEWER CU, so LIFO (newest-first) is driven by creation order.
		final PickingJobStepId olderStepId = stepIdForPickFromHU(pickingJob, line, vhuOlder.getId());
		pickingJob = pick(pickingJob, line, olderStepId, vhuOlder, "6");

		final PickingJobStepId newerStepId = stepIdForPickFromHU(pickingJob, line, vhuNewer.getId());
		pickingJob = pick(pickingJob, line, newerStepId, vhuNewer, "6");

		assertThat(pickingJob.getStepById(olderStepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getQtyPicked())
				.contains(Quantity.of("6", helper.uomEach));
		assertThat(pickingJob.getStepById(newerStepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getQtyPicked())
				.contains(Quantity.of("6", helper.uomEach));

		//
		// Subset unpick of 8 across the two 6-PCE CUs: the selection must take exactly ONE whole CU (6) plus
		// ONE boundary split carving the remaining 2 — total 8. (Both picked CUs carry the same createdAt
		// under the test's fixed clock, so WHICH step is the whole one vs the boundary is not deterministic;
		// the meaningful, deterministic invariant is the whole-vs-boundary split of the 8 across the steps.)
		final Map<PickingJobStepId, StepUnpickInstructions> instructionsByStep = PickingJobUnPickCommand
				.selectLifoUnpickInstructions(pickingJob, productId, Quantity.of("8", helper.uomEach))
				.collect(Collectors.toMap(StepUnpickInstructions::getStepId, i -> i));

		assertThat(instructionsByStep).containsOnlyKeys(newerStepId, olderStepId);

		// Exactly one step contributes a WHOLE picked-to HU (qty 6) and exactly one is the BOUNDARY split.
		final long wholeHuSteps = instructionsByStep.values().stream()
				.filter(i -> i.getPickedToHUsToUnpick() != null && !i.getPickedToHUsToUnpick().isEmpty())
				.count();
		final long boundarySteps = instructionsByStep.values().stream()
				.filter(i -> i.getBoundaryHuToSplit() != null)
				.count();
		assertThat(wholeHuSteps).as("exactly one CU taken whole").isEqualTo(1);
		assertThat(boundarySteps).as("exactly one CU boundary-split").isEqualTo(1);

		// The whole CU contributes 6, the boundary carves 2 — together the requested 8.
		final Quantity wholeQty = instructionsByStep.values().stream()
				.filter(i -> i.getPickedToHUsToUnpick() != null && !i.getPickedToHUsToUnpick().isEmpty())
				.flatMap(i -> i.getPickedToHUsToUnpick().stream())
				.map(hu -> hu.getQtyPicked())
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("no whole-HU selection"));
		assertThat(wholeQty).as("whole CU qty").isEqualTo(Quantity.of("6", helper.uomEach));

		final StepUnpickInstructions boundary = instructionsByStep.values().stream()
				.filter(i -> i.getBoundaryHuToSplit() != null)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("no boundary split"));
		assertThat(boundary.getBoundarySplitQty())
				.as("boundary carves the remaining 2 (6 - 2 = 4 stays packed)")
				.isEqualTo(Quantity.of("2", helper.uomEach));
	}

	private PickingJob pick(
			@NonNull final PickingJob pickingJob,
			@NonNull final PickingJobLine line,
			@NonNull final PickingJobStepId stepId,
			@NonNull final HUInfo vhu,
			@NonNull final String qty)
	{
		return helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(vhu.getQrCode().toScannedCode())
				.qtyPicked(new BigDecimal(qty))
				.qtyRejectedReasonCode(null)
				.build());
	}

	private PickingJobStepId stepIdForPickFromHU(
			@NonNull final PickingJob pickingJob,
			@NonNull final PickingJobLine line,
			@NonNull final HuId pickFromHuId)
	{
		return line.getSteps().stream()
				.filter(step -> step.getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHUId().equals(pickFromHuId))
				.map(PickingJobStep::getId)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No step picks from HU " + pickFromHuId + " in " + pickingJob));
	}
}
