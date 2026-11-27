package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.service.commands.grai.PickingJobGRAIValidator;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.util.Collection;
import de.metas.handlingunits.grai.HUGraiSnapshot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PickingJobCUQtyEntryPick_CompletionTest extends CUQtyEntryPickFixture
{
	@Test
	void labelRequiredMode_cuPickedInTUWithoutGrai_graiRequiredYes_isRefused()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		setCustomerGraiRequired(GRAIRequired.Yes);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-GRAI-N");
		final PickingJob job = pickEach(openTUFromLinePacking(newJob().packedWith(tuPiipId).create()), source, LOT_A, "1");
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HU is the CU, not the TU").doesNotContain(openTUId(job));

		assertThatThrownBy(() -> helper.pickingJobService.complete(job))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(HUGraiSnapshot.GRAI_COUNT_MISMATCH.toAD_Message());
	}

	@Test
	void graiCheck_looksUpBookedCUsOnlyOfLinesThatRequireGrai()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		setCustomerGraiRequired(GRAIRequired.Yes);
		final BPartnerLocationId customerB = helper.createBPartnerAndLocationId("customerB");
		setCustomerGraiRequired(customerB.getBpartnerId(), GRAIRequired.No);
		final HUInfo sourceA = sourceTU("QR-G1A");
		final HUInfo sourceB = sourceTU("QR-G1B");
		PickingJob job = createTwoCustomerProductJob("g1", customerB);
		final PickingJobLineId lineAId = lineOfCustomerA(job).getId();
		final PickingJobLineId lineBId = lineOfCustomerB(job).getId();
		job = helper.pickingJobService.setTUPickingTarget(job, lineAId, TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
		job = pickOnLine(job, lineAId, sourceA, "3", label(PRODUCT_NO, LOT_A));
		job = helper.pickingJobService.setTUPickingTarget(job, lineBId, TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
		job = pickOnLine(job, lineBId, sourceB, "2", label(PRODUCT_NO, LOT_B));
		final PickingJob pickedJob = job;
		final ImmutableSet<HuId> cusOfA = pickedJob.getLineById(lineAId).getPickedHUIds();
		assertThat(cusOfA).as("A booked one CU").hasSize(1);
		assertThat(pickedJob.getLineById(lineBId).getPickedHUIds()).as("B booked one CU").hasSize(1);
		final PickingJobHUService huServiceSpy = Mockito.spy(helper.huService);

		assertThatThrownBy(() -> PickingJobGRAIValidator.builder()
				.huService(huServiceSpy)
				.bpartnerService(helper.bpartnerService)
				.pickingJob(pickedJob)
				.build()
				.validate())
				.hasMessageContaining(HUGraiSnapshot.GRAI_COUNT_MISMATCH.toAD_Message());

		@SuppressWarnings("unchecked") final ArgumentCaptor<Collection<HUInfo>> lookedUp = ArgumentCaptor.forClass(Collection.class);
		Mockito.verify(huServiceSpy).filterBookedWithLabel(lookedUp.capture());
		assertThat(lookedUp.getValue()).extracting(HUInfo::getId).as("only the CUs of the GRAI customer are looked up").containsExactlyElementsOf(cusOfA);
	}

	@Test
	void labelRequiredModePick_thenModeSwitchedToManual_cuPickedInTUWithoutGrai_isStillRefused()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		setCustomerGraiRequired(GRAIRequired.Yes);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-GRAI-S");
		final PickingJob job = pickEach(openTUFromLinePacking(newJob().packedWith(tuPiipId).create()), source, LOT_A, "1");
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HU is the CU, not the TU").doesNotContain(openTUId(job));

		setProfile(CUQtyEntryMode.MANUAL);

		assertThatThrownBy(() -> helper.pickingJobService.complete(job))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(HUGraiSnapshot.GRAI_COUNT_MISMATCH.toAD_Message());
	}

	@Test
	void manualPick_thenModeSwitchedToLabelRequiredMode_tuWithoutGrai_isRefused()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		setCustomerGraiRequired(GRAIRequired.Yes);
		final HUInfo source = sourceTU("QR-GRAI-R");
		final PickingJob job = pick(openTUFromLinePacking(newJob().create()), source, "5", null, false, null);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("Manual: the picked HU is the TU").containsExactly(openTUId(job));

		setProfile(CUQtyEntryMode.SCAN_EACH_CU);

		assertThatThrownBy(() -> helper.pickingJobService.complete(job))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(HUGraiSnapshot.GRAI_COUNT_MISMATCH.toAD_Message());
	}

	@Test
	void cuInTU_notBookedWithOwnQRCode_isCheckedAsItIs_bookedWithOwnQRCode_viaItsTU()
	{
		final HuId tuId = helper.createHU(tuPIId, productId, helper.qty("4", productId));
		final HuId cuId = HuId.ofRepoId(cusOf(tuId).get(0).getM_HU_ID());
		assertThat(helper.huService.filterCUsInTU(ImmutableSet.of(cuId))).as("the CU sits in the TU").containsExactly(cuId);

		assertThat(helper.huService.getGraiSnapshotsReplacingCUsBookedWithLabelByTheirTU(ImmutableSet.of(cuId), ImmutableSet.of()))
				.as("Manual-shaped CU: checked as it is, the CU has no GRAI snapshot").isEmpty();
		assertThat(helper.huService.getGraiSnapshotsReplacingCUsBookedWithLabelByTheirTU(ImmutableSet.of(cuId), ImmutableSet.of(cuId)))
				.as("CU booked with its own QR code: its TU is checked").extracting(snapshot -> snapshot.getHuId()).containsExactly(tuId);
	}

	@Test
	void cuBookedWithLabelButWithoutParentItem_isRefusedWithAClearMessage()
	{
		final HuId cuWithoutParent = helper.createVHU(productId, "5");

		assertThatThrownBy(() -> helper.huService.getGraiSnapshotsReplacingCUsBookedWithLabelByTheirTU(ImmutableSet.of(cuWithoutParent), ImmutableSet.of(cuWithoutParent)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("no parent item")
				.hasMessageContaining(String.valueOf(cuWithoutParent.getRepoId()));
	}

	@Test
	void pickedHUWithQRCodeSharedBySeveralHUs_graiNotRequired_completes()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		setCustomerGraiRequired(GRAIRequired.No);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-SHARED-N");
		final PickingJob job = pickEach(openTUFromLinePacking(newJob().packedWith(tuPiipId).create()), source, LOT_A, "1");
		final HuId otherHuSharingTheQRCode = helper.createVHU(productId, "4");
		final HUInfo pickedCU = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUInfos());
		helper.huService.getHuQRCodesService().assign(pickedCU.getQrCode(), ImmutableSet.of(pickedCU.getId(), otherHuSharingTheQRCode));

		assertThatCode(() -> helper.pickingJobService.complete(job)).doesNotThrowAnyException();
	}

	@Test
	void pickedHUWithQRCodeSharedBySeveralHUs_graiRequiredYes_isRefusedForTheMissingGrai()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		setCustomerGraiRequired(GRAIRequired.Yes);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-SHARED-Y");
		final PickingJob job = pickEach(openTUFromLinePacking(newJob().packedWith(tuPiipId).create()), source, LOT_A, "1");
		final HuId otherHuSharingTheQRCode = helper.createVHU(productId, "4");
		final HUInfo pickedCU = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUInfos());
		helper.huService.getHuQRCodesService().assign(pickedCU.getQrCode(), ImmutableSet.of(pickedCU.getId(), otherHuSharingTheQRCode));

		assertThatThrownBy(() -> helper.pickingJobService.complete(job))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(HUGraiSnapshot.GRAI_COUNT_MISMATCH.toAD_Message());
	}

	@Test
	void manualMode_tuWithoutGrai_graiRequiredYes_isRefused()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		setCustomerGraiRequired(GRAIRequired.Yes);
		final HUInfo source = sourceTU("QR-GRAI-M");
		final PickingJob job = pick(openTUFromLinePacking(newJob().create()), source, "5", null, false, null);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("Manual: the picked HU is the TU").containsExactly(openTUId(job));

		assertThatThrownBy(() -> helper.pickingJobService.complete(job))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(HUGraiSnapshot.GRAI_COUNT_MISMATCH.toAD_Message());
	}
}
