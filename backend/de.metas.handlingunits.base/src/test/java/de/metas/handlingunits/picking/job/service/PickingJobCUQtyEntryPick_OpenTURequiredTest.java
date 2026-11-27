package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.service.commands.pick.PickingJobPickCommand;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PickingJobCUQtyEntryPick_OpenTURequiredTest extends CUQtyEntryPickFixture
{
	@Test
	void unlimitedCapacityLineNamingATuPI_noTarget_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final I_M_HU_PI_Item_Product piip = InterfaceWrapperHelper.load(tuPiipId.getRepoId(), I_M_HU_PI_Item_Product.class);
		piip.setIsInfiniteCapacity(true);
		InterfaceWrapperHelper.save(piip);
		final HUInfo source = sourceTU("QR-C");
		final PickingJob job = newJob().packedWith(tuPiipId).create();

		assertFails(() -> pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null), PickingJobPickCommand.CU_NO_OPEN_TU_ERROR_MSG);

		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).as("nothing booked").isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).as("source untouched").isEqualByComparingTo("8");
	}

	@Test
	void virtualPackingLine_noTarget_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = sourceTU("QR-D");
		final PickingJob job = newJob().create();

		assertFails(() -> pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null), PickingJobPickCommand.CU_NO_OPEN_TU_ERROR_MSG);

		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("8");
	}

	@Test
	void manual_noTarget_notRefused_fullTU()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-E");
		PickingJob job = newJob().packedWith(tuPiipId).create();
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickingUnit().isTU()).as("Manual: a TU packing line still counts TUs").isTrue();

		job = pick(job, source, "1", null, false, null);

		final ImmutableSet<HuId> picked = CollectionUtils.singleElement(job.getLines()).getPickedHUIds();
		assertThat(picked).hasSize(1);
		final I_M_HU pickedHU = handlingUnitsBL.getById(picked.iterator().next());
		assertThat(handlingUnitsBL.isTransportUnitOrAggregate(pickedHU)).as("the picked HU is the TU").isTrue();
		assertThat(qtyOf(pickedHU)).isEqualByComparingTo("4");
	}

	@Test
	void manual_fiveFromTwoPieces_noTarget_onePickedHUOf5()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final HUInfo source = sourceTU("QR-E2");
		PickingJob job = newJob().create();

		job = pick(job, source, "5", null, false, null);

		final ImmutableSet<HuId> picked = CollectionUtils.singleElement(job.getLines()).getPickedHUIds();
		assertThat(picked).hasSize(1);
		final HuId pickedId = picked.iterator().next();
		assertThat(qtyOf(handlingUnitsBL.getById(pickedId))).isEqualByComparingTo("5");
		assertThat(cusOf(pickedId)).as("no CU split per source piece").isEmpty();
	}

	@Test
	void manual_fiveFromTwoPieces_openTU_oneCUPerSourcePiece()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final HUInfo source = sourceTU("QR-E3");
		PickingJob job = openTUFromLinePacking(newJob().create());

		job = pick(job, source, "5", null, false, null);

		final HuId pickedTUId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
		assertThat(cusOf(pickedTUId)).as("one CU per source piece").extracting(this::qtyOf).usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
				.containsExactlyInAnyOrder(new BigDecimal("4"), new BigDecimal("1"));
	}

	@Test
	void scanEachCU_noTarget_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = sourceTU("QR-H");
		final PickingJob job = newJob().packedWith(tuPiipId).create();

		assertFails(() -> pick(job, source, "1", label(PRODUCT_NO, LOT_A), false, null), PickingJobPickCommand.CU_NO_OPEN_TU_ERROR_MSG);

		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("8");
	}
}
