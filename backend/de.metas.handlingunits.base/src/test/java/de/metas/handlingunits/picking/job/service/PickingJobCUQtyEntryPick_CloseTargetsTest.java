package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfig;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfigsCollection;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptionsId;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.I_MobileUI_UserProfile_Picking_Job;
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
	void manual_closingAllTargets_queuesTheTUOnTheLastScannedSlot()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final TUOfCustomerA given = pickIntoTUOfCustomerA_thenScanSlotB("x2", "1", null);

		helper.pickingJobService.closeLUAndTUPickingTargets(given.job);

		assertThat(queuedSlotsOf(given.tuA)).as("Manual: the close queues the TU on the slot scanned last").contains(given.slotB);
	}

	@Test
	void manual_productAggregation_cuLine_closingAllTargets_queuesTheTUOnTheLastScannedSlot()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final HUInfo source = sourceTU("QR-X5");
		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId("salesOrder-x5");
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.huPIItemProductId(null) // no TU packing: the line is picked in CUs
				.qtyToDeliver("100")
				.build();
		PickingJob job = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.PRODUCT)
								.pickerId(UserId.ofRepoId(1234))
								.isAllowPickingAnyHU(true)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "SLOT-A"));
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		assertThat(line.getPickingUnit().isCU()).as("CU line").isTrue();
		job = helper.pickingJobService.setTUPickingTarget(job, line.getId(), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
		job = pickOnLine(job, line.getId(), source, "2", null);
		final HuId tu = job.getTuPickingTargetEffective(line.getId()).orElseThrow(() -> new AssertionError("no TU")).getTuIdNotNull();
		final PickingSlotId slotB = createPickingSlot("SLOT-B");
		job = job.withPickingSlot(PickingSlotIdAndCaption.of(slotB, "SLOT-B"));

		helper.pickingJobService.closeLUAndTUPickingTargets(job);

		assertThat(queuedSlotsOf(tu)).as("Manual, product aggregation, CU line: the close queues the TU on the slot scanned last").contains(slotB);
	}

	@Test
	void mixedJob_labelRequiredModeLineOnItsOwnSlot_manualLineOnTheLastScannedSlot()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final BPartnerLocationId customerB = helper.createBPartnerAndLocationId("customerB");
		final BPartnerId customerAId = customerAId();
		helper.updateMobileProfile(profile -> {
			// A job options row replaces the profile's options for the customer, so it repeats the profile's values and differs only in CU mode and label format.
			// Written by hand: the frontend tests' writer (PickingJobOptionsRepository) lives in frontend-testing, which this module cannot depend on.
			final I_MobileUI_UserProfile_Picking profileRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MobileUI_UserProfile_Picking.class).create().firstOnlyNotNull(I_MobileUI_UserProfile_Picking.class);
			final I_MobileUI_UserProfile_Picking_Job optionsRecordA = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_Job.class);
			optionsRecordA.setName("customer A label-required mode");
			optionsRecordA.setPickingJobAggregationType(profileRecord.getPickingJobAggregationType());
			optionsRecordA.setIsAllowPickingAnyHU(profileRecord.isAllowPickingAnyHU());
			optionsRecordA.setIsAlwaysSplitHUsEnabled(profileRecord.isAlwaysSplitHUsEnabled());
			optionsRecordA.setIsShipOnCloseLU(profileRecord.isShipOnCloseLU());
			optionsRecordA.setAllowPickToStructure_LU_TU(StringUtils.ofBoolean(profileRecord.isAllowPickToStructure_LU_TU()));
			optionsRecordA.setAllowPickToStructure_TU(StringUtils.ofBoolean(profileRecord.isAllowPickToStructure_TU()));
			optionsRecordA.setAllowPickToStructure_LU_CU(StringUtils.ofBoolean(profileRecord.isAllowPickToStructure_LU_CU()));
			optionsRecordA.setAllowPickToStructure_CU(StringUtils.ofBoolean(profileRecord.isAllowPickToStructure_CU()));
			optionsRecordA.setIsCatchWeightTUPickingEnabled(profileRecord.isCatchWeightTUPickingEnabled());
			optionsRecordA.setIsConsiderSalesOrderCapacity(profileRecord.isConsiderSalesOrderCapacity());
			optionsRecordA.setIsAllowSkippingRejectedReason(profileRecord.isAllowSkippingRejectedReason());
			optionsRecordA.setIsShowConfirmationPromptWhenOverPick(profileRecord.isShowConfirmationPromptWhenOverPick());
			optionsRecordA.setIsAllowCompletingPartialPickingJob(profileRecord.isAllowCompletingPartialPickingJob());
			optionsRecordA.setIsShowLastPickedBestBeforeDateForLines(profileRecord.isShowLastPickedBestBeforeDateForLines());
			optionsRecordA.setIsAnonymousHuPickedOnTheFly(profileRecord.isAnonymousHuPickedOnTheFly());
			optionsRecordA.setIsDisplayPickingSlotSuggestions(StringUtils.ofBoolean(profileRecord.isDisplayPickingSlotSuggestions()));
			optionsRecordA.setCreateShipmentPolicy(profileRecord.getCreateShipmentPolicy());
			optionsRecordA.setCUQtyEntryMode(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY.getCode());
			optionsRecordA.setC_ScannableCode_Format_ID(format.getId().getRepoId());
			InterfaceWrapperHelper.saveRecord(optionsRecordA);
			final PickingJobOptionsId optionsA = PickingJobOptionsId.ofRepoId(optionsRecordA.getMobileUI_UserProfile_Picking_Job_ID());
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
