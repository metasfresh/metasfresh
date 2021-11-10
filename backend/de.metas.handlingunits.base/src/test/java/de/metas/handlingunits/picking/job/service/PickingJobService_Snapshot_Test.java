package de.metas.handlingunits.picking.job.service;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.test.SnapshotFunctionFactory;
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;

class PickingJobService_Snapshot_Test
{
	private PickingJobTestHelper helper;

	// Masterdata
	final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(1, 2);

	@BeforeAll
	static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG, SnapshotFunctionFactory.newFunction());
	}

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();

		final ProductId productId = BusinessTestHelper.createProductId("P1", helper.uomEach);
		final HuId vhu1 = helper.createVHU(productId, "130");
		System.out.println("VHU1: " + vhu1);

		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.qtyToDeliver("100")
				.date(Instant.parse("2021-11-09T08:58:52Z"))
				.build();

	}

	private PickingJob createJob()
	{
		final PickingJob pickingJob = helper.pickingJobService.createPickingJob(
				PickingJobCreateRequest.builder()
						.pickerId(UserId.ofRepoId(1234))
						.salesOrderId(orderAndLineId.getOrderId())
						.deliveryBPLocationId(helper.shipToBPLocationId)
						.build());
		System.out.println("Created " + pickingJob);
		return pickingJob;
	}

	@Test
	void createJobAndGet()
	{
		final PickingJob pickingJob = createJob();
		expect(pickingJob).toMatchSnapshot();

		final PickingJob jobLoaded = helper.pickingJobService.getById(pickingJob.getId());
		Assertions.assertThat(jobLoaded)
				.usingRecursiveComparison()
				.isEqualTo(pickingJob);
	}

	@Test
	void abortJob()
	{
		PickingJob pickingJob = createJob();
		pickingJob = helper.pickingJobService.abort(pickingJob);
		expect(pickingJob).toMatchSnapshot();
	}

	@Test
	void completeJob()
	{
		PickingJob pickingJob = createJob();
		final PickingJobStep step = helper.extractSingleStep(pickingJob);

		pickingJob = helper.pickingJobService.processStepEvent(
				pickingJob,
				PickingJobStepEvent.builder()
						.pickingStepId(step.getId())
						.pickFromKey(PickingJobStepPickFromKey.MAIN)
						.eventType(PickingJobStepEventType.PICK)
						.huBarcode(step.getPickFrom(PickingJobStepPickFromKey.MAIN).getPickFromHU().getBarcode())
						.qtyPicked(new BigDecimal("100"))
						.build());

		pickingJob = helper.pickingJobService.complete(pickingJob);
		expect(pickingJob).toMatchSnapshot();
	}
}