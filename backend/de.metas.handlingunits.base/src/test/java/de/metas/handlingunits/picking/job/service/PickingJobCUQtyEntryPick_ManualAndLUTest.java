package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PickingJobCUQtyEntryPick_ManualAndLUTest extends CUQtyEntryPickFixture
{
	@Test
	void manual_lotsAThenB_intoOneTU_lotOverwritten()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-L");
		final HUInfo source2 = helper.createVHUInfo(productId, "100", "QR-L2");
		PickingJob job = newJob().create();
		job = openTUFromLinePacking(job);

		job = pick(job, source, "2", null, false, LOT_A);
		final HuId tuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
		assertThat(handlingUnitsBL.isTransportUnitOrAggregate(handlingUnitsBL.getById(tuId))).as("the first booking's picked HU is the TU").isTrue();
		job = helper.pickingJobService.setTUPickingTarget(job, targetScope(job), TUPickingTarget.ofExistingHU(tuId, helper.huService.getQRCodeByHuId(tuId)));
		job = pick(job, source2, "3", null, false, LOT_B);

		assertThat(lotOf(handlingUnitsBL.getById(tuId))).as("TU lot overwritten by the second booking").isEqualTo(LOT_B);
		assertThat(cusOf(tuId)).extracting(this::lotOf).as("the second lot is written on the whole TU").containsOnly(LOT_B);
	}

	@Test
	void manual_luAndTU_pickedHUIsTheTU_undoLeavesNothingPicked()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-Y");
		final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-Y", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(luPI, InterfaceWrapperHelper.load(tuPIId.getRepoId(), I_M_HU_PI.class), new BigDecimal("10"));
		PickingJob job = newJob().create();
		job = helper.pickingJobService.setLUPickingTarget(job, targetScope(job), LUPickingTarget.ofPackingInstructions(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()), "LU-Y"));
		job = openTUFromLinePacking(job);

		job = pick(job, source, "2", null, false, LOT_A);

		final HuId tuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
		final I_M_HU tu = handlingUnitsBL.getById(tuId);
		assertThat(handlingUnitsBL.isTransportUnit(tu)).as("Manual: the picked HU is the TU").isTrue();
		assertThat(helper.huService.filterCUsInTU(ImmutableSet.of(tuId))).isEmpty();
		assertThat(countQtyPickedRowsOfTU(tuId)).isEqualTo(1);
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("2");

		final TUPickingTarget targetBeforeUndo = openTUTarget(job);

		job = undo(job, tuId, source.getQrCode(), null);

		assertThat(openTUTarget(job)).as("Manual: the pick target is left as it was, not replaced by a new TU").isEqualTo(targetBeforeUndo);
		assertThat(countQtyPickedRowsOfTU(tuId)).as("no QtyPicked row of the TU left").isEqualTo(0);
		assertThat(pickedQtyOfLine(job)).as("nothing picked").isEqualByComparingTo("0");
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).isEmpty();
	}

	/** Pins a known base defect: un-picking a CU picked directly onto an LU leaves its QtyPicked row. */
	@Test
	void manual_cuPickedOntoLU_unpick_restoresAsBase()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LUCU");
		final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-CU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(luPI, huTestHelper.huDefVirtual, new BigDecimal("10"));
		PickingJob job = newJob().create();
		job = helper.pickingJobService.setLUPickingTarget(job, targetScope(job), LUPickingTarget.ofPackingInstructions(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()), "LU-CU"));

		job = pick(job, source, "2", null, false, LOT_A);

		final HuId pickedHuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
		final I_M_HU pickedHU = handlingUnitsBL.getById(pickedHuId);
		assertThat(handlingUnitsBL.isVirtual(pickedHU)).as("the picked HU is a CU").isTrue();
		assertThat(handlingUnitsBL.getLoadingUnitHU(pickedHU)).as("on the pallet").isNotNull();
		assertThat(handlingUnitsBL.getTransportUnitHU(pickedHU)).as("no TU around it").satisfies(tu -> assertThat(tu == null || !handlingUnitsBL.isTransportUnit(tu)).isTrue());
		assertThat(helper.huService.filterCUsInTU(ImmutableSet.of(pickedHuId))).isEmpty();
		assertThat(countQtyPickedRowsOfCU(pickedHuId)).as("the pick booked its QtyPicked row").isEqualTo(1);
		assertThat(countAllQtyPickedRows()).isEqualTo(1);

		job = undo(job, pickedHuId, source.getQrCode(), null);

		assertThat(countQtyPickedRowsOfCU(pickedHuId)).as("KNOWN BASE DEFECT, pinned so Manual stays as base: the QtyPicked row of the CU is left; a fix of the defect must update this assertion").isEqualTo(1);
		assertThat(countAllQtyPickedRows()).as("no other QtyPicked row was touched").isEqualTo(1);
		assertThat(handlingUnitsBL.getById(pickedHuId).getHUStatus()).as("not picked").isEqualTo(X_M_HU.HUSTATUS_Active);
		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("0");
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).isEmpty();
	}

	@Test
	void cuDirectlyOnLU_isNotACUInATU()
	{
		final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-Z", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI_Item luItem = huTestHelper.createHU_PI_Item_IncludedHU(luPI, InterfaceWrapperHelper.load(tuPIId.getRepoId(), I_M_HU_PI.class), new BigDecimal("10"));
		final I_M_HU lu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		lu.setM_HU_PI_Version_ID(handlingUnitsDAO.retrievePICurrentVersion(luPI).getM_HU_PI_Version_ID());
		InterfaceWrapperHelper.save(lu);
		final I_M_HU_Item luHUItem = handlingUnitsDAO.createHUItem(lu, luItem);

		final I_M_HU cuOnLU = handlingUnitsBL.getById(helper.createVHU(productId, "5"));
		cuOnLU.setM_HU_Item_Parent_ID(luHUItem.getM_HU_Item_ID());
		InterfaceWrapperHelper.save(cuOnLU);

		assertThat(helper.huService.filterCUsInTU(ImmutableSet.of(HuId.ofRepoId(cuOnLU.getM_HU_ID())))).isEmpty();
	}
}
