package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PickingJobCUQtyEntryPick_LabelRequiredModeAndLUTest extends CUQtyEntryPickFixture
{
	private PickingJob jobWithLUAndTU()
	{
		final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-NM", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(luPI, InterfaceWrapperHelper.load(tuPIId.getRepoId(), I_M_HU_PI.class), new BigDecimal("10"));
		PickingJob job = newJob().packedWith(tuPiipId).create();
		job = helper.pickingJobService.setLUPickingTarget(job, targetScope(job), LUPickingTarget.ofPackingInstructions(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()), "LU-NM"));
		return openTUFromLinePacking(job);
	}

	private HuId tuOf(final HuId cuId)
	{
		final I_M_HU tu = handlingUnitsBL.getTransportUnitHU(handlingUnitsBL.getById(cuId));
		assertThat(tu).as("the CU sits in a TU").isNotNull();
		assertThat(handlingUnitsBL.isTransportUnit(tu)).as("the CU's parent is a TU").isTrue();
		return HuId.ofRepoId(tu.getM_HU_ID());
	}

	private HuId luOf(final HuId huId)
	{
		final I_M_HU lu = handlingUnitsBL.getLoadingUnitHU(handlingUnitsBL.getById(huId));
		assertThat(lu).as("on a LU").isNotNull();
		return HuId.ofRepoId(lu.getM_HU_ID());
	}

	@Test
	void scanEachCU_luAndTU_firstScan_oneCUOfOneBooked()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-EACH");
		PickingJob job = jobWithLUAndTU();

		job = pickEach(job, source, LOT_A, "1");

		final HuId pickedHuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
		final I_M_HU pickedHU = handlingUnitsBL.getById(pickedHuId);
		assertThat(handlingUnitsBL.isVirtual(pickedHU)).as("the picked HU is the booked CU").isTrue();
		assertThat(qtyOf(pickedHU)).isEqualByComparingTo("1");
		assertThat(packIdOf(pickedHU)).isEqualTo("1");
		final HuId tuId = tuOf(pickedHuId);
		assertThat(cusOf(tuId)).as("the TU holds only the booked CU").hasSize(1);
		assertThat(luOf(tuId)).as("TU on the pallet").isNotNull();
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("1");
	}

	@Test
	void scanOnce_luAndTU_firstBooking_pickedHUIsTheCU()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-ONCE");
		PickingJob job = jobWithLUAndTU();

		job = pick(job, source, "3", label(PRODUCT_NO, LOT_A), false, null);

		final HuId pickedHuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
		final I_M_HU pickedHU = handlingUnitsBL.getById(pickedHuId);
		assertThat(handlingUnitsBL.isVirtual(pickedHU)).as("the picked HU is the booked CU, not the TU").isTrue();
		assertThat(qtyOf(pickedHU)).isEqualByComparingTo("3");
		final HuId tuId = tuOf(pickedHuId);
		assertThat(cusOf(tuId)).as("the TU holds only the booked CU").hasSize(1);
		assertThat(luOf(tuId)).as("TU on the pallet").isNotNull();
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("3");
	}

	@Test
	void scanOnce_luAndTU_firstBookingFillsTheTU_oneCUOf4_undo()
	{
		assertLUAndTU_firstBookingOfQty_oneCU_undo("4", "QR-LU-ONCE-FULL");
	}

	@Test
	void scanOnce_luAndTU_firstBookingAboveTUCapacity_oneCUOf5_undo()
	{
		assertLUAndTU_firstBookingOfQty_oneCU_undo("5", "QR-LU-ONCE-OVER");
	}

	private void assertLUAndTU_firstBookingOfQty_oneCU_undo(final String qty, final String sourceQRCode)
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = helper.createVHUInfo(productId, "100", sourceQRCode);
		PickingJob job = jobWithLUAndTU();

		job = pick(job, source, qty, label(PRODUCT_NO, LOT_A), false, null);

		final HuId cuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
		final I_M_HU cu = handlingUnitsBL.getById(cuId);
		assertThat(handlingUnitsBL.isVirtual(cu)).as("the picked HU is the booked CU, not the TU").isTrue();
		assertThat(qtyOf(cu)).isEqualByComparingTo(qty);
		final HuId tuId = tuOf(cuId);
		assertThat(handlingUnitsBL.isAggregateHU(handlingUnitsBL.getById(tuId))).as("a real TU, not an aggregated TU").isFalse();
		assertThat(cusOf(tuId)).as("the TU holds only the booked CU").extracting(I_M_HU::getM_HU_ID).containsExactly(cuId.getRepoId());
		assertThat(luOf(tuId)).as("the TU sits on the pallet").isNotNull();
		assertThat(openTUId(job)).as("the TU is the open TU").isEqualTo(tuId);
		assertThat(countQtyPickedRowsOfCU(cuId)).isEqualTo(1);
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo(qty);

		job = undo(job, cuId, source.getQrCode(), null);

		assertThat(handlingUnitsBL.getById(cuId).getHUStatus()).as("the CU is Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
		assertThat(countQtyPickedRowsOfCU(cuId)).as("no QtyPicked row of the un-picked CU left").isEqualTo(0);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("nothing picked").isEmpty();
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("0");
		assertThat(cusOf(tuId)).as("the CU left the TU").isEmpty();
	}

	@Test
	void scanEachCU_luAndTU_twoCUs_undoFirst_removesOnlyThatCU()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-EACH-U");
		PickingJob job = jobWithLUAndTU();
		job = pickEach(job, source, LOT_A, "1");
		job = pickEach(job, source, LOT_A, "2");

		final HuId tuId = openTUId(job);
		final HuId cu1Id = HuId.ofRepoId(cuWithPackId(tuId, "1").getM_HU_ID());
		final HuId cu2Id = HuId.ofRepoId(cuWithPackId(tuId, "2").getM_HU_ID());
		assertThat(tuOf(cu1Id)).as("CU 1 in the TU").isEqualTo(tuId);
		final HuId luId = luOf(tuId);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HUs are the CUs").containsExactlyInAnyOrder(cu1Id, cu2Id);
		assertThat(countQtyPickedRowsOfTU(tuId)).isEqualTo(2);
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("2");

		job = undo(job, cu1Id, source.getQrCode(), null);

		assertThat(handlingUnitsBL.getById(cu1Id).getHUStatus()).as("CU 1 Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
		assertThat(handlingUnitsBL.getById(cu2Id).getHUStatus()).as("CU 2 stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
		assertThat(handlingUnitsBL.getById(tuId).getHUStatus()).as("TU stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
		assertThat(handlingUnitsBL.getById(luId).getHUStatus()).as("pallet stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
		assertThat(cusOf(tuId)).as("only CU 2 is left in the TU").extracting(cu -> cu.getM_HU_ID()).containsExactly(cu2Id.getRepoId());
		assertThat(luOf(tuId)).as("the TU stays on the pallet").isEqualTo(luId);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("only CU 2 is still picked").containsExactly(cu2Id);
		assertThat(countQtyPickedRowsOfCU(cu1Id)).as("no QtyPicked row of the un-picked CU left").isEqualTo(0);
		assertThat(countQtyPickedRowsOfCU(cu2Id)).as("the QtyPicked row of CU 2 stays").isEqualTo(1);
		assertThat(pickedQtyOfLine(job)).as("the line's picked quantity dropped by 1").isEqualByComparingTo("1");
	}

	@Test
	void filterCUsInTU_twoCUsOfTheSameTU_bothAccepted()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-EACH-F");
		PickingJob job = jobWithLUAndTU();
		job = pickEach(job, source, LOT_A, "1");
		job = pickEach(job, source, LOT_A, "2");
		final HuId tuId = openTUId(job);
		final HuId cu1Id = HuId.ofRepoId(cuWithPackId(tuId, "1").getM_HU_ID());
		final HuId cu2Id = HuId.ofRepoId(cuWithPackId(tuId, "2").getM_HU_ID());
		assertThat(handlingUnitsBL.getById(cu1Id).getM_HU_Item_Parent_ID()).as("both CUs share the TU's item")
				.isEqualTo(handlingUnitsBL.getById(cu2Id).getM_HU_Item_Parent_ID());

		assertThat(helper.huService.filterCUsInTU(ImmutableSet.of(cu1Id, cu2Id))).containsExactlyInAnyOrder(cu1Id, cu2Id);
		assertThat(helper.huService.filterCUsInTU(ImmutableSet.of(cu1Id, cu2Id, tuId))).as("the TU itself is no CU").containsExactlyInAnyOrder(cu1Id, cu2Id);
	}

	@Test
	void scanOnce_luAndTU_twoBookings_undoFirst_removesOnlyThatCU()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-ONCE-U");
		PickingJob job = jobWithLUAndTU();
		job = pick(job, source, "3", label(PRODUCT_NO, LOT_A), false, null);
		final HuId cu3Id = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
		job = pick(job, source, "2", label(PRODUCT_NO, LOT_A), false, null);

		final HuId tuId = openTUId(job);
		assertThat(tuOf(cu3Id)).as("the CU of 3 in the TU").isEqualTo(tuId);
		final HuId cu2Id = HuId.ofRepoId(cuWithQty(tuId, "2").getM_HU_ID());
		final HuId luId = luOf(tuId);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HUs are the CUs").containsExactlyInAnyOrder(cu3Id, cu2Id);
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("5");

		job = undo(job, cu3Id, source.getQrCode(), null);

		assertThat(handlingUnitsBL.getById(cu3Id).getHUStatus()).as("the CU of 3 Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
		assertThat(handlingUnitsBL.getById(cu2Id).getHUStatus()).as("the CU of 2 stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
		assertThat(handlingUnitsBL.getById(tuId).getHUStatus()).as("TU stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
		assertThat(cusOf(tuId)).as("only the CU of 2 is left in the TU").extracting(cu -> cu.getM_HU_ID()).containsExactly(cu2Id.getRepoId());
		assertThat(luOf(tuId)).as("the TU stays on the pallet").isEqualTo(luId);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("only the CU of 2 is still picked").containsExactly(cu2Id);
		assertThat(countQtyPickedRowsOfCU(cu3Id)).as("no QtyPicked row of the un-picked CU left").isEqualTo(0);
		assertThat(countQtyPickedRowsOfCU(cu2Id)).as("the QtyPicked row of the CU of 2 stays").isEqualTo(1);
		assertThat(pickedQtyOfLine(job)).as("the line's picked quantity dropped by 3").isEqualByComparingTo("2");
	}

	@Test
	void scanEachCU_existingLUAndSecondTU_cuInNewTUOnSameLU()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-EACH-2");
		PickingJob job = jobWithLUAndTU();
		job = pickEach(job, source, LOT_A, "1");
		final HuId cu1Id = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
		final HuId tu1Id = tuOf(cu1Id);
		final HuId luId = luOf(tu1Id);
		assertThat(job.getLuPickingTargetEffective(CollectionUtils.singleElement(job.getLines()).getId()).map(LUPickingTarget::isExistingLU))
				.as("the pallet made by CU 1 is the pallet target").contains(true);

		job = openTUFromLinePacking(job);
		job = pickEach(job, source, LOT_A, "2");

		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		assertThat(line.getPickedHUIds()).hasSize(2);
		final HuId cu2Id = line.getPickedHUIds().stream().filter(id -> !id.equals(cu1Id)).findFirst().orElseThrow(() -> new AssertionError("no 2nd picked HU"));
		final I_M_HU cu2 = handlingUnitsBL.getById(cu2Id);
		assertThat(handlingUnitsBL.isVirtual(cu2)).as("the picked HU is the booked CU").isTrue();
		assertThat(packIdOf(cu2)).isEqualTo("2");
		final HuId tu2Id = tuOf(cu2Id);
		assertThat(tu2Id).as("CU 2 is in a new TU").isNotEqualTo(tu1Id);
		assertThat(cusOf(tu2Id)).as("the new TU holds only CU 2").hasSize(1);
		assertThat(luOf(tu2Id)).as("the new TU sits on the same pallet").isEqualTo(luId);
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("2");
	}
}
