package de.metas.handlingunits.picking.job.service;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.service.commands.pick.PickingJobPickCommand;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.product.ProductId;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

class PickingJobCUQtyEntryPick_ScanEachCUTest extends CUQtyEntryPickFixture
{
	@Test
	void scanEachCU_lotsAAB_threeCUsOfOne_ownLotAndPackId_tuLotEmpty()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-M");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());

		job = pickEach(job, source, LOT_A, "1");
		job = pickEach(job, source, LOT_A, "2");
		job = pickEach(job, source, LOT_B, "3");

		final HuId tuId = openTUId(job);
		final List<I_M_HU> cus = cusOf(tuId);
		assertThat(cus).as("3 CUs in the TU").hasSize(3);
		cus.forEach(cu -> assertThat(qtyOf(cu)).as("each CU has quantity 1").isEqualByComparingTo("1"));
		assertThat(cus).extracting(this::lotOf, this::packIdOf)
				.containsExactlyInAnyOrder(
						tuple(LOT_A, "1"),
						tuple(LOT_A, "2"),
						tuple(LOT_B, "3"));
		assertThat(lotOf(handlingUnitsBL.getById(tuId))).as("TU lot empty (mixed lots)").isNull();
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).hasSize(3);
	}

	@Test
	void scanEachCU_clientSendsQty3_oneCUOfOneBooked()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-N");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());

		job = pick(job, source, "3", eachLabel(LOT_A, "1"), false, null);

		final List<I_M_HU> cus = cusOf(openTUId(job));
		assertThat(cus).hasSize(1);
		assertThat(qtyOf(cus.get(0))).isEqualByComparingTo("1");
		assertThat(packIdOf(cus.get(0))).isEqualTo("1");
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("99");
	}

	@Test
	void repeatedLotAndPackId_refused_nothingBooked()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-O");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());
		job = pickEach(job, source, LOT_A, "1");
		final PickingJob jobBefore = job;

		assertFails(() -> pickEach(jobBefore, source, LOT_A, "1"), PickingCULabelService.MSG_CU_LABEL_ALREADY_PICKED);

		assertThat(cusOf(openTUId(job))).as("still one CU").hasSize(1);
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("99");
	}

	@Test
	void samePackIdOnAnotherLot_booked()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-P");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());

		job = pickEach(job, source, LOT_A, "1");
		job = pickEach(job, source, LOT_B, "1");

		final List<I_M_HU> cus = cusOf(openTUId(job));
		assertThat(cus).hasSize(2);
		assertThat(cus).extracting(this::lotOf, this::packIdOf)
				.containsExactlyInAnyOrder(tuple(LOT_A, "1"), tuple(LOT_B, "1"));
	}

	@Test
	void sameLabelInAnotherJob_booked()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-Q");
		PickingJob job1 = openTUFromLinePacking(newJob().orderDocNo("salesOrderCU1").packedWith(tuPiipId).create());
		job1 = pickEach(job1, source, LOT_A, "1");
		PickingJob job2 = openTUFromLinePacking(newJob().orderDocNo("salesOrderCU2").packedWith(tuPiipId).create());

		job2 = pickEach(job2, source, LOT_A, "1");

		final List<I_M_HU> cus2 = cusOf(openTUId(job2));
		assertThat(cus2).hasSize(1);
		assertThat(packIdOf(cus2.get(0))).isEqualTo("1");
		assertThat(lotOf(cus2.get(0))).isEqualTo(LOT_A);
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("98");
	}

	@Test
	void firstCUOfTheTU_duplicate_undo_rescan()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-R");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());
		job = pickEach(job, source, LOT_A, "1");
		job = pickEach(job, source, LOT_A, "2");
		final HuId tuId = openTUId(job);
		final I_M_HU cu1 = cuWithPackId(tuId, "1");
		final HuId cu1Id = HuId.ofRepoId(cu1.getM_HU_ID());
		final I_M_HU cu2 = cuWithPackId(tuId, "2");
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HUs are the CUs, not the TU").containsExactlyInAnyOrder(cu1Id, HuId.ofRepoId(cu2.getM_HU_ID()));
		assertThat(pickedQtyOfLine(job)).as("2 CUs picked").isEqualByComparingTo("2");
		assertThat(pickedHUOfStepWithCU(job, cu1Id).getQrCode()).as("the picked CU carries its own QR code, not the TU's").isNotEqualTo(helper.getQRCode(tuId));
		final PickingJob jobBeforeRescan = job;
		assertFails(() -> pickEach(jobBeforeRescan, source, LOT_A, "1"), PickingCULabelService.MSG_CU_LABEL_ALREADY_PICKED);
		assertThat(cusOf(tuId)).as("still 2 CU HUs").hasSize(2);
		assertThat(countQtyPickedRowsOfTU(tuId)).isEqualTo(2);

		job = undo(job, cu1Id, source.getQrCode(), null);

		assertThat(handlingUnitsBL.getById(cu1Id).getHUStatus()).as("CU 1 Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
		assertThat(handlingUnitsBL.getById(tuId).getHUStatus()).as("TU stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
		assertThat(handlingUnitsBL.getById(HuId.ofRepoId(cu2.getM_HU_ID())).getHUStatus()).as("CU 2 stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("only CU 2 is still picked").containsExactly(HuId.ofRepoId(cu2.getM_HU_ID()));
		assertThat(countQtyPickedRowsOfTU(tuId)).as("QtyPicked row of the 2nd CU stays").isEqualTo(1);
		assertThat(pickedQtyOfLine(job)).as("the line's picked quantity dropped by 1").isEqualByComparingTo("1");

		job = pickEach(job, source, LOT_A, "1");

		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("rescan booked").hasSize(2);
		assertThat(pickedQtyOfLine(job)).as("rescan: 2 CUs picked again").isEqualByComparingTo("2");
		final I_M_HU rescanned = cuWithPackId(tuId, "1");
		assertThat(lotOf(rescanned)).as("rescanned CU keeps its lot").isEqualTo(LOT_A);
		assertThat(countQtyPickedRowsOfTU(tuId)).as("QtyPicked rows of both CUs").isEqualTo(2);
	}

	@ParameterizedTest(name = "isShowConfirmationPromptWhenOverPick={0}")
	@ValueSource(booleans = { true, false })
	void lineDone_furtherScan_refused_nothingBooked(final boolean isShowConfirmationPromptWhenOverPick)
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		helper.updateMobileProfile(profile -> profile.toBuilder()
				.defaultPickingJobOptions(profile.getDefaultPickingJobOptions().toBuilder()
						.isShowConfirmationPromptWhenOverPick(isShowConfirmationPromptWhenOverPick)
						.build())
				.build());
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-T");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).qtyToDeliver("2").create());
		job = pickEach(job, source, LOT_A, "1");
		job = pickEach(job, source, LOT_A, "2");
		assertThat(pickedQtyOfLine(job)).as("line done: 2 of 2").isEqualByComparingTo("2");
		final PickingJob jobWhenDone = job;

		final List<String> log = logOfPickCommand(() -> assertThatThrownBy(() -> pickEach(jobWhenDone, source, LOT_A, "3"))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(PickingJobPickCommand.PICK_OVERDELIVERY_BLOCKED_ERROR_MSG.toAD_Message()))
				.as("no technical parameters in the message the picker sees")
				.satisfies(ex -> assertThat(((AdempiereException)ex).getMessage()).doesNotContain("pickingJobId", "pickingJobLineId", "qtyToPick", "qtyOrdered", "qtyAlreadyPicked")));
		assertThat(log).as("the discarded scan is logged with its values")
				.anySatisfy(msg -> assertThat(msg).contains("Over-pick refused", "SCAN_EACH_CU", "pickingJobId=PickingJobId(repoId=" + jobWhenDone.getId().getRepoId() + ")", "pickingJobLineId=", "qtyToPick=1", "qtyOrdered=2", "qtyAlreadyPicked=2"));

		assertThat(cusOf(openTUId(job))).as("still 2 CUs").hasSize(2);
		assertThat(pickedQtyOfLine(helper.pickingJobService.getById(job.getId()))).as("still 2 picked").isEqualByComparingTo("2");
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).as("source: only 2 taken").isEqualByComparingTo("98");
	}

	@Test
	void manual_overPickRefused_messageKeepsItsParameters()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		helper.updateMobileProfile(profile -> profile.toBuilder()
				.defaultPickingJobOptions(profile.getDefaultPickingJobOptions().toBuilder()
						.isShowConfirmationPromptWhenOverPick(false)
						.build())
				.build());
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-MO");
		PickingJob job = newJob().qtyToDeliver("2").create();
		job = pick(job, source, "2", null, false, null);
		assertThat(pickedQtyOfLine(job)).as("line done: 2 of 2").isEqualByComparingTo("2");
		final PickingJob jobWhenDone = job;

		assertThatThrownBy(() -> pick(jobWhenDone, source, "1", null, false, null))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(PickingJobPickCommand.PICK_OVERDELIVERY_BLOCKED_ERROR_MSG.toAD_Message()))
				.satisfies(ex -> assertThat(((AdempiereException)ex).getMessage()).contains("pickingJobId", "pickingJobLineId", "qtyToPick", "qtyOrdered", "qtyAlreadyPicked"));
		assertThat(pickedQtyOfLine(helper.pickingJobService.getById(job.getId()))).as("still 2 picked").isEqualByComparingTo("2");
	}

	@Test
	void kgLine_refusedWithApprovedMessage_nothingBooked()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final ProductId kgProductId = BusinessTestHelper.createProductId("PRD0002", huTestHelper.uomKg);
		final I_M_HU_PI_Item_Product kgPiip = huTestHelper.assignProduct(huTestHelper.createHU_PI_Item_Material(huTestHelper.createHUDefinition("TU-4kg", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit)), kgProductId, new BigDecimal("4"), huTestHelper.uomKg);
		kgPiip.setIsDefaultForProduct(true);
		InterfaceWrapperHelper.save(kgPiip);
		productId = kgProductId;
		final HuId sourceId = helper.createVHU(kgProductId, "100");
		final HUInfo source = HUInfo.builder().id(sourceId).qrCode(helper.createQRCode(sourceId, "QR-KG")).build();
		final PickingJob job = openTUFromLinePacking(newJob().packedWith(HUPIItemProductId.ofRepoId(kgPiip.getM_HU_PI_Item_Product_ID())).create());

		assertFails(() -> pick(job, source, "1", label("PRD0002", LOT_A), false, null), PickingJobPickCommand.CU_EACH_ONLY_FOR_PIECES_ERROR_MSG);

		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).as("nothing booked").isEmpty();
		final IHUStorage sourceStorage = huTestHelper.createMutableHUContextOutOfTransaction().getHUStorageFactory().getStorage(handlingUnitsBL.getById(sourceId));
		assertThat(sourceStorage.getQuantity(kgProductId, huTestHelper.uomKg).toBigDecimal()).as("source untouched").isEqualByComparingTo("100");
	}

	@Test
	void labelWithoutPackId_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-S");
		final PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());

		assertFails(() -> pickEach(job, source, LOT_A, null), PickingCULabelService.MSG_CU_LABEL_FORMAT_MISMATCH);

		assertNothingBooked(job, source, "100");
	}
}
