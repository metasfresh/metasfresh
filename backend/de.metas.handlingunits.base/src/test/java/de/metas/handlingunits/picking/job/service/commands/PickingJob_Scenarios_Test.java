package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.expectations.HUStorageExpectation;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class PickingJob_Scenarios_Test
{
	private PickingJobTestHelper helper;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
	}

	@Test
	void pickCU_QtyToPick_LessThan_HUQty()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P1", helper.uomEach);
		final HuId vhu1 = helper.createVHU(productId, "130");
		System.out.println("VHU1: " + vhu1);

		final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(1, 2);
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.qtyToDeliver("100")
				.build();

		//
		// Create job
		PickingJob pickingJob = helper.pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.pickerId(UserId.ofRepoId(1234))
						.salesOrderId(orderAndLineId.getOrderId())
						.deliveryBPLocationId(helper.shipToBPLocationId)
						.build());
		System.out.println("Created " + pickingJob);
		final PickingJobStepId stepId = CollectionUtils.singleElement(pickingJob.streamSteps().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

		//
		// Pick the whole quantity
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.huBarcode(HUBarcode.ofHuId(vhu1))
				.qtyPicked(new BigDecimal("100"))
				.qtyRejectedReasonCode(null)
				.build());
		HuId pickFromHUId;
		{
			System.out.println("After pick: " + pickingJob);
			assertThat(pickFromHUId = pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU().getId()).isNotEqualTo(vhu1);
			HUStorageExpectation.newExpectation().product(productId).qty("100").assertExpected(pickFromHUId);
			HUStorageExpectation.newExpectation().product(productId).qty("30").assertExpected(vhu1);

			final PickingJobStepPickedTo picked = pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo();
			assertThat(picked).isNotNull();
			assertThat(picked.getQtyPicked()).isEqualTo(Quantity.of("100", helper.uomEach));
			assertThat(picked.getActualPickedHUId()).isEqualTo(pickFromHUId);
			HUStorageExpectation.newExpectation().product(productId).qty(picked.getQtyPicked()).assertExpected(pickFromHUId);

			final PickingCandidate pickingCandidate = helper.pickingCandidateRepository.getById(picked.getPickingCandidateId());
			assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromHUId));
			assertThat(pickingCandidate.getPackedToHuId()).isEqualTo(pickFromHUId);
		}

		//
		// Unpick
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.UNPICK)
				.huBarcode(HUBarcode.ofHuId(vhu1))
				.build());
		{
			System.out.println("After unpick: " + pickingJob);
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU()).isEqualTo(HUInfo.ofHuId(pickFromHUId));
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo()).isNull();
			HUStorageExpectation.newExpectation().product(productId).qty("100").assertExpected(pickFromHUId);
		}
	}

	@Test
	void pickCU_QtyToPick_EqualsTo_HUQty()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P1", helper.uomEach);
		final HuId vhu1 = helper.createVHU(productId, "100");

		final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(1, 2);
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.qtyToDeliver("100")
				.build();

		PickingJob pickingJob = helper.pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.pickerId(UserId.ofRepoId(1234))
						.salesOrderId(orderAndLineId.getOrderId())
						.deliveryBPLocationId(helper.shipToBPLocationId)
						.build());
		System.out.println("Created " + pickingJob);
		final PickingJobStepId stepId = CollectionUtils.singleElement(pickingJob.streamSteps().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.huBarcode(HUBarcode.ofHuId(vhu1))
				.qtyPicked(new BigDecimal("100"))
				.qtyRejectedReasonCode(null)
				.build());
		{
			System.out.println("After pick: " + pickingJob);

			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU()).isEqualTo(HUInfo.ofHuId(vhu1));

			final PickingJobStepPickedTo picked = pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo();
			assertThat(picked)
					.isNotNull()
					.isEqualTo(PickingJobStepPickedTo.builder()
							.qtyPicked(Quantity.of("100", helper.uomEach))
							.actualPickedHUId(vhu1)
							.pickingCandidateId(picked.getPickingCandidateId()) // N/A
							.build());
		}

		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.UNPICK)
				.huBarcode(HUBarcode.ofHuId(vhu1))
				.build());
		{
			System.out.println("After unpick: " + pickingJob);
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU()).isEqualTo(HUInfo.ofHuId(vhu1));
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo()).isNull();
		}
	}

}