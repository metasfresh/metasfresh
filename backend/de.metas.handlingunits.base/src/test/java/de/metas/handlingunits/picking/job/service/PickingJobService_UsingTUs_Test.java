package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.service.commands.LUPackingInstructions;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityTU;
import de.metas.test.SnapshotFunctionFactory;
import de.metas.user.UserId;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static io.github.jsonSnapshot.SnapshotMatcher.start;

//@ExtendWith(AdempiereTestWatcher.class)
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class PickingJobService_UsingTUs_Test
{
	private PickingJobTestHelper helper;

	// Masterdata
	private OrderAndLineId orderAndLineId;
	private HuId lu1;
	private HuId lu2;

	private TestRecorder results;

	@BeforeAll
	static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG, SnapshotFunctionFactory.newFunction());
	}

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		results = helper.newTestRecorder();

		createMasterdata();
	}

	private void createMasterdata()
	{
		final I_C_UOM uomKg = BusinessTestHelper.createUomKg();
		final ProductId productId = BusinessTestHelper.createProductId("P1", uomKg);
		final HUPIItemProductId tuPackingInstructionsId = helper.createTUPackingInstructionsId("TU1", productId, Quantity.of("25", uomKg));
		final LUPackingInstructions luPackingInstructions = helper.createLUPackingInstructions("Pallet", tuPackingInstructionsId, QuantityTU.ofInt(10));

		//
		// Masterdata: HUs
		{
			lu1 = helper.createLU(luPackingInstructions, "75");
			helper.createQRCode(lu1, "QR-LU1");
			helper.dumpHU("LU1", lu1);

			lu2 = helper.createLU(luPackingInstructions, "250");
			helper.createQRCode(lu2, "QR-LU2");
			helper.dumpHU("LU2", lu2);
		}

		orderAndLineId = helper.createOrderAndLineId("salesOrder001");
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.huPIItemProductId(tuPackingInstructionsId)
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
						.isAllowPickingAnyHU(false) // we need a plan built
						.build());
		results.reportStep("Created Picking Job", pickingJob);
		results.reportStepWithAllHUs("HUs after created Picking Job");
		return pickingJob;
	}

	@Test
	void createJobAndGet()
	{
		final PickingJob pickingJob = createJob();
		results.assertMatchesSnapshot();

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

		results.reportStep("Picking Job after ABORT", pickingJob);
		results.reportStepWithAllHUs("HUs after Picking Job ABORT");
		results.assertMatchesSnapshot();
	}

	@Test
	void completeJob()
	{
		PickingJob pickingJob = createJob();
		final PickingJobLine line = CollectionUtils.singleElement(pickingJob.getLines());
		final ImmutableList<PickingJobStep> steps = line.getSteps();
		Assertions.assertThat(steps).hasSize(2);

		pickingJob = helper.pickingJobService.processStepEvent(
				pickingJob,
				PickingJobStepEvent.builder()
						.pickingLineId(line.getId())
						.pickingStepId(steps.get(0).getId())
						.pickFromKey(PickingJobStepPickFromKey.MAIN)
						.eventType(PickingJobStepEventType.PICK)
						.huQRCode(helper.huQRCodesRepository.getFirstQRCodeByHuId(lu1).get())
						.qtyPicked(QtyTU.THREE.toBigDecimal()) // 3 x 25kg
						.build());

		results.reportStep("Picking Job after Picked 1", pickingJob);
		results.reportStepWithAllHUs("HUs after Picked 1");

		pickingJob = helper.pickingJobService.processStepEvent(
				pickingJob,
				PickingJobStepEvent.builder()
						.pickingLineId(line.getId())
						.pickingStepId(steps.get(1).getId())
						.pickFromKey(PickingJobStepPickFromKey.MAIN)
						.eventType(PickingJobStepEventType.PICK)
						.huQRCode(helper.huQRCodesRepository.getFirstQRCodeByHuId(lu2).get())
						.qtyPicked(QtyTU.ONE.toBigDecimal()) // 1 x 25kg
						.build());
		results.reportStepWithAllHUs("HUs after Picked 2");

		pickingJob = helper.pickingJobService.complete(pickingJob);
		results.reportStep("Picking Job after Complete", pickingJob);
		results.reportStepWithAllHUs("HUs after Complete");

		results.assertMatchesSnapshot();
	}

}
