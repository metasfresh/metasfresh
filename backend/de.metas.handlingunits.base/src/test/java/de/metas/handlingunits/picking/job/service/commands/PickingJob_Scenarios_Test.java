package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.expectations.HUStorageExpectation;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(AdempiereTestWatcher.class)
class PickingJob_Scenarios_Test
{
	private PickingJobTestHelper helper;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
	}

	@Disabled("Disabled because splitting of a part of top level VHU leads to something which is not physically identifiable (no QR code)")
	@Test
	void pickCU_QtyToPick_LessThan_HUQty()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P1", helper.uomEach);
		final HuId vhu1 = helper.createVHU(productId, "130");
		helper.createQRCode(vhu1, "QR-VHU1");
		System.out.println("VHU1: " + vhu1);

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrder001");
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
						.isAllowPickingAnyHU(false) // we need a plan built
						.build());
		System.out.println("Created " + pickingJob);
		final PickingJobStepId stepId = CollectionUtils.singleElement(pickingJob.streamSteps().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

		//
		// Pick the whole quantity
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.huQRCode(helper.getQRCode(vhu1))
				.qtyPicked(new BigDecimal("100"))
				.qtyRejectedReasonCode(null)
				.build());
		HuId pickFromHUId;
		{
			System.out.println("After pick: " + pickingJob);
			assertThat(pickFromHUId = pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHUId()).isNotEqualTo(vhu1);
			HUStorageExpectation.newExpectation().product(productId).qty("100").assertExpected(pickFromHUId);
			HUStorageExpectation.newExpectation().product(productId).qty("30").assertExpected(vhu1);

			{
				final PickingJobStepPickedTo pickedTo = pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo();
				assertThat(pickedTo).isNotNull();
				assertThat(pickedTo.getQtyPicked()).isEqualTo(Quantity.of("100", helper.uomEach));

				assertThat(pickedTo.getActualPickedHUs()).hasSize(1);
				{
					final PickingJobStepPickedToHU pickedToHU = pickedTo.getActualPickedHUs().get(0);
					assertThat(pickedToHU.getActualPickedHUId()).isEqualTo(pickFromHUId);
					HUStorageExpectation.newExpectation().product(productId).qty(pickedTo.getQtyPicked()).assertExpected(pickFromHUId);

					final PickingCandidate pickingCandidate = helper.pickingCandidateRepository.getById(pickedToHU.getPickingCandidateId());
					assertThat(pickingCandidate.getPickFrom()).isEqualTo(PickFrom.ofHuId(pickFromHUId));
					assertThat(pickingCandidate.getPackedToHuId()).isEqualTo(pickFromHUId);
				}
			}
		}

		//
		// Unpick
		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.UNPICK)
				.huQRCode(helper.getQRCode(vhu1))
				.build());
		{
			System.out.println("After unpick: " + pickingJob);
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU())
					.isEqualTo(HUInfo.builder().id(pickFromHUId).qrCode(helper.getQRCode(pickFromHUId)).build());
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo()).isNull();
			HUStorageExpectation.newExpectation().product(productId).qty("100").assertExpected(pickFromHUId);
		}
	}

	@Test
	void pickCU_QtyToPick_EqualsTo_HUQty()
	{
		final ProductId productId = BusinessTestHelper.createProductId("P1", helper.uomEach);
		final HuId vhu1 = helper.createVHU(productId, "100");
		final HUQRCode vhu1_qrCode = helper.createQRCode(vhu1, "QR-VHU1");

		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrder002");
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
						.isAllowPickingAnyHU(false) // we need a plan built
						.build());
		System.out.println("Created " + pickingJob);
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		final PickingJobStepId stepId = CollectionUtils.singleElement(line.getSteps().stream().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet()));

		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.huQRCode(vhu1_qrCode)
				.qtyPicked(new BigDecimal("100"))
				.qtyRejectedReasonCode(null)
				.build());
		{
			System.out.println("After pick: " + pickingJob);

			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU())
					.isEqualTo(HUInfo.builder().id(vhu1).qrCode(vhu1_qrCode).build());

			final PickingJobStepPickedTo pickedTo = pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo();
			assertThat(pickedTo)
					.isNotNull()
					.isEqualTo(PickingJobStepPickedTo.builder()
							.actualPickedHUs(ImmutableList.of(
									PickingJobStepPickedToHU.builder()
											.qtyPicked(Quantity.of("100", helper.uomEach))
											.pickFromHUId(vhu1)
											.actualPickedHUId(vhu1)
											.pickingCandidateId(pickedTo.getActualPickedHUs().get(0).getPickingCandidateId()) // N/A
											.build()))
									   .productId(productId)
							.build());
		}

		pickingJob = helper.pickingJobService.processStepEvent(pickingJob, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.UNPICK)
				.huQRCode(vhu1_qrCode)
				.build());
		{
			System.out.println("After unpick: " + pickingJob);
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU())
					.isEqualTo(HUInfo.builder().id(vhu1).qrCode(vhu1_qrCode).build());
			assertThat(pickingJob.getStepById(stepId).getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo()).isNull();
		}
	}

}