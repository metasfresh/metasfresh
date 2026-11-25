package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfig;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfigsCollection;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptionsId;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.scannable_code.ScannedCode;
import org.junit.jupiter.api.Test;
import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

class PickingJobCUQtyEntryPick_CloseTargetsTest extends CUQtyEntryPickFixture
{
	@Test
	void closingAllTargets_queuesEachLinesTUOnItsOwnLinesSlot()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final TUOfCustomerA given = pickIntoTUOfCustomerA_thenScanSlotB("x", "3", label(PRODUCT_NO, LOT_A));

		helper.pickingJobService.closeLUAndTUPickingTargets(given.job);

		assertThat(queuedSlotsOf(given.tuA)).as("customer A's TU is queued on customer A's slot only").containsExactly(helper.pickingSlotId);
	}

	@Test
	void labelRequiredModeBooking_thenModeSwitchedToManual_closingAllTargets_queuesTheTUOnItsOwnLinesSlot()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final TUOfCustomerA given = pickIntoTUOfCustomerA_thenScanSlotB("x4", "3", label(PRODUCT_NO, LOT_A));

		setProfile(CUQtyEntryMode.MANUAL);

		helper.pickingJobService.closeLUAndTUPickingTargets(given.job);

		assertThat(queuedSlotsOf(given.tuA)).as("customer A's TU, booked in Scan once, is queued on customer A's slot only").containsExactly(helper.pickingSlotId);
	}

	@Test
	void manual_closingAllTargets_queuesTheTUOnTheLastScannedSlot()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final TUOfCustomerA given = pickIntoTUOfCustomerA_thenScanSlotB("x2", "1", null);

		helper.pickingJobService.closeLUAndTUPickingTargets(given.job);

		assertThat(queuedSlotsOf(given.tuA)).as("Manual: the close queues the TU on the slot scanned last").contains(given.slotB);
	}

	@Test
	void mixedJob_labelRequiredModeLineOnItsOwnSlot_manualLineOnTheLastScannedSlot()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final BPartnerLocationId customerB = helper.createBPartnerAndLocationId("customerB");
		final BPartnerId customerAId = customerAId();
		helper.updateMobileProfile(profile -> {
			final PickingJobOptionsId optionsA = helper.configService.createPickingJobOptions("customer A label-required mode", profile.getDefaultPickingJobOptions());
			helper.configService.updatePickingJobOptionsCUQtyEntry(optionsA, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId());
			return profile.toBuilder()
					.customerConfigs(PickingCustomerConfigsCollection.ofCollection(ImmutableList.of(
							PickingCustomerConfig.builder().customerId(customerAId).pickingJobOptionsId(optionsA).build())))
					.build();
		});
		final HUInfo sourceA = sourceTU("QR-X3A");
		final HUInfo sourceB = sourceTU("QR-X3B");
		PickingJob job = createTwoCustomerProductJob("x3", customerB);
		final PickingJobLine lineA = lineOfCustomerA(job);
		final PickingJobLine lineB = lineOfCustomerB(job);
		job = helper.pickingJobService.setTUPickingTarget(job, lineA.getId(), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
		job = pickOnLine(job, lineA.getId(), sourceA, "3", label(PRODUCT_NO, LOT_A));
		final HuId tuA = job.getTuPickingTargetEffective(lineA.getId()).orElseThrow(() -> new AssertionError("no TU of A")).getTuIdNotNull();
		final PickingSlotId slotB = createPickingSlot("SLOT-B");
		job = job.withPickingSlot(PickingSlotIdAndCaption.of(slotB, "SLOT-B"));
		job = helper.pickingJobService.setTUPickingTarget(job, lineB.getId(), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
		job = pickOnLine(job, lineB.getId(), sourceB, "1", null);
		final HuId tuB = job.getTuPickingTargetEffective(lineB.getId()).orElseThrow(() -> new AssertionError("no TU of B")).getTuIdNotNull();
		final PickingSlotId slotC = createPickingSlot("SLOT-C");
		job = job.withPickingSlot(PickingSlotIdAndCaption.of(slotC, "SLOT-C"));

		final PickingJob jobClosed = helper.pickingJobService.closeLUAndTUPickingTargets(job);

		assertThat(queuedSlotsOf(tuA)).as("label-required-mode customer A: own line slot only").containsExactly(helper.pickingSlotId);
		assertThat(queuedSlotsOf(tuB)).as("Manual customer B: the slot scanned last").contains(slotC);
		assertThat(jobClosed.getTuPickingTargetEffective(lineB.getId())).as("Manual line's target closed").isEmpty();
	}

	private final class TUOfCustomerA
	{
		private final PickingJob job;
		private final HuId tuA;
		private final PickingSlotId slotB;

		private TUOfCustomerA(final PickingJob job, final HuId tuA, final PickingSlotId slotB)
		{
			this.job = job;
			this.tuA = tuA;
			this.slotB = slotB;
		}
	}

	private TUOfCustomerA pickIntoTUOfCustomerA_thenScanSlotB(final String testId, final String qty, @Nullable final ScannedCode cuLabel)
	{
		final HUInfo source = sourceTU("QR-" + testId.toUpperCase());
		PickingJob job = createTwoCustomerProductJob(testId, helper.createBPartnerAndLocationId("customerB"));
		final PickingJobLine lineA = lineOfCustomerA(job);
		job = helper.pickingJobService.setTUPickingTarget(job, lineA.getId(), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
		job = pickOnLine(job, lineA.getId(), source, qty, cuLabel);
		final HuId tuA = job.getTuPickingTargetEffective(lineA.getId()).orElseThrow(() -> new AssertionError("no TU")).getTuIdNotNull();
		final PickingSlotId slotB = createPickingSlot("SLOT-B");
		job = job.withPickingSlot(PickingSlotIdAndCaption.of(slotB, "SLOT-B"));
		return new TUOfCustomerA(job, tuA, slotB);
	}
}
