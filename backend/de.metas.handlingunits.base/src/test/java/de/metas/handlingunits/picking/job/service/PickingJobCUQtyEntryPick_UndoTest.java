package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.collections.CollectionUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PickingJobCUQtyEntryPick_UndoTest extends CUQtyEntryPickFixture
{
	@Test
	void undoWithTargetScan_cuMovedIntoTarget()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-T");
		final HUInfo target = helper.createVHUInfo(productId, "10", "QR-T-TARGET");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());
		job = pickEach(job, source, LOT_A, "1");
		job = pickEach(job, source, LOT_A, "2");
		final HuId tuId = openTUId(job);
		final HuId cu1Id = HuId.ofRepoId(cuWithPackId(tuId, "1").getM_HU_ID());
		final HuId cu2Id = HuId.ofRepoId(cuWithPackId(tuId, "2").getM_HU_ID());

		job = undo(job, cu1Id, source.getQrCode(), target.getQrCode());

		assertThat(qtyOf(handlingUnitsBL.getById(target.getId()))).as("target took the CU").isEqualByComparingTo("11");
		assertThat(handlingUnitsBL.getById(tuId).getHUStatus()).as("TU stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
		assertThat(handlingUnitsBL.getById(cu2Id).getHUStatus()).as("CU 2 stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).containsExactly(cu2Id);
		assertThat(countQtyPickedRowsOfTU(tuId)).as("QtyPicked row of the 2nd CU stays").isEqualTo(1);
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("1");
	}

	@Test
	void undoOfTheTUsOnlyCU_lineKeepsAnOpenTUOfTheSameType()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-U");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());
		job = pickEach(job, source, LOT_A, "1");
		final HuId tuId = openTUId(job);
		final HuId cu1Id = HuId.ofRepoId(cuWithPackId(tuId, "1").getM_HU_ID());

		job = undo(job, cu1Id, source.getQrCode(), null);

		assertThat(handlingUnitsBL.getById(cu1Id).getHUStatus()).as("the CU is Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("nothing picked").isEmpty();
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("0");
		assertThat(countQtyPickedRowsOfTU(tuId)).as("no QtyPicked row left in the TU").isEqualTo(0);
		assertThat(countQtyPickedRowsOfCU(cu1Id)).as("no QtyPicked row of the CU left").isEqualTo(0);
		final TUPickingTarget target = openTUTarget(job);
		assertThat(target.isNewTU()).as("the emptied TU is not the target: an open TU of the same type is").isTrue();
		assertThat(target.getTuPIIdNotNull()).isEqualTo(tuPIId);

		job = pickEach(job, source, LOT_A, "2");

		final HuId nextTUId = openTUId(job);
		assertThat(cusOf(nextTUId)).as("the next CU went into a TU of that type").hasSize(1);
		assertThat(handlingUnitsBL.getEffectivePackingInstructionsIdAndCaption(handlingUnitsBL.getById(nextTUId)).getId()).isEqualTo(tuPIId);
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("1");
	}

	@Test
	void undoOfTheOnlyCUOfAGRAITU_openTUKeepsTheGRAI()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		mapGRAIToTheTUType();
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-W");
		PickingJob job = newJob().packedWith(tuPiipId).create();
		job = helper.pickingJobService.createTUFromGRAI(job, targetScope(job), ScannedCode.ofString(GRAI_CANONICAL));
		job = pickEach(job, source, LOT_A, "1");
		final HuId tuId = openTUId(job);
		final HuId cu1Id = HuId.ofRepoId(cuWithPackId(tuId, "1").getM_HU_ID());

		job = undo(job, cu1Id, source.getQrCode(), null);

		final TUPickingTarget target = openTUTarget(job);
		assertThat(target.isNewTU()).isTrue();
		assertThat(target.getGrai()).as("the open TU keeps the scanned GRAI").isEqualTo(GRAI.parse(GRAI_CANONICAL));

		job = pickEach(job, source, LOT_A, "2");

		final HuId nextTUId = openTUId(job);
		final GRAISet grais = CollectionUtils.singleElement(ImmutableList.copyOf(graiSnapshotsOfTUs(ImmutableSet.of(nextTUId)))).getAllGrais();
		assertThat(grais.toSet()).as("the next CU's TU carries the GRAI").containsExactly(GRAI.parse(GRAI_CANONICAL));
	}

	@Test
	void profileSwitchedToManualAfterBooking_unpickStillRemovesTheCUsRows()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-V");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());
		job = pickEach(job, source, LOT_A, "1");
		job = pickEach(job, source, LOT_A, "2");
		final HuId tuId = openTUId(job);
		final HuId cu1Id = HuId.ofRepoId(cuWithPackId(tuId, "1").getM_HU_ID());
		assertThat(countQtyPickedRowsOfTU(tuId)).isEqualTo(2);

		setProfile(CUQtyEntryMode.MANUAL);
		job = undo(job, cu1Id, source.getQrCode(), null);

		assertThat(countQtyPickedRowsOfCU(cu1Id)).as("no QtyPicked row of the un-picked CU left").isEqualTo(0);
		assertThat(countQtyPickedRowsOfTU(tuId)).as("the other CU's row stays").isEqualTo(1);
		assertThat(handlingUnitsBL.getById(cu1Id).getHUStatus()).as("the CU is Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("only CU 2 is still picked").hasSize(1).doesNotContain(cu1Id);
		assertThat(pickedQtyOfLine(job)).as("the line's picked quantity dropped by 1").isEqualByComparingTo("1");
	}

	@Test
	void profileSwitchedToManualAfterBooking_unpickOfTheOnlyCU_lineKeepsAnOpenTUOfTheSameType()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-W");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());
		job = pickEach(job, source, LOT_A, "1");
		final HuId tuId = openTUId(job);
		final HuId cu1Id = HuId.ofRepoId(cuWithPackId(tuId, "1").getM_HU_ID());

		setProfile(CUQtyEntryMode.MANUAL); // the decision follows the picked HU (a CU booked in a TU), not the current profile
		job = undo(job, cu1Id, source.getQrCode(), null);

		final TUPickingTarget target = openTUTarget(job);
		assertThat(target.isNewTU()).as("the emptied TU is not the target: an open TU of the same type is").isTrue();
		assertThat(target.getTuPIIdNotNull()).isEqualTo(tuPIId);
	}
}
