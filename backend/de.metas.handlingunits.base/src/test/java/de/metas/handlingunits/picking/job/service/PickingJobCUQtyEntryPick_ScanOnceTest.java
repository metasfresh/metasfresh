package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.service.commands.pick.PickingJobPickCommand;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatPart;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PickingJobCUQtyEntryPick_ScanOnceTest extends CUQtyEntryPickFixture
{
	@Test
	void labelWithGS_isBooked_withAFormatOfTheGSPositions()
	{
		format = formatService.create(ScannableCodeFormatCreateRequest.builder()
				.name("CU label with GS")
				.parts(ImmutableList.of(
						constant(1, 2, "01"),
						part(3, 16, ScannableCodeFormatPartType.Ignored),
						constant(17, 18, "91"),
						part(19, 25, ScannableCodeFormatPartType.ProductCode),
						part(26, 26, ScannableCodeFormatPartType.Ignored),
						constant(27, 28, "17"),
						part(29, 34, ScannableCodeFormatPartType.BestBeforeDate),
						constant(35, 36, "10"),
						part(37, 45, ScannableCodeFormatPartType.LotNo),
						part(46, 46, ScannableCodeFormatPartType.Ignored),
						constant(47, 48, "92"),
						part(49, ScannableCodeFormatPart.OPEN_END, ScannableCodeFormatPartType.PackId)))
				.build());
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-GS");
		PickingJob job = openTUFromLinePacking(newJob().orderDocNo("salesOrderCUGS").packedWith(tuPiipId).qtyToDeliver("2").create());

		job = pick(job, source, "2", labelWithGS(PRODUCT_NO, LOT_A), false, null);

		assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("2");
	}

	@Test
	void lineDone_furtherPick_refused_noParametersInTheMessage()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		helper.updateMobileProfile(profile -> profile.toBuilder()
				.defaultPickingJobOptions(profile.getDefaultPickingJobOptions().toBuilder()
						.isShowConfirmationPromptWhenOverPick(false)
						.build())
				.build());
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-SO");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).qtyToDeliver("2").create());
		job = pick(job, source, "2", label(PRODUCT_NO, LOT_A), false, null);
		assertThat(pickedQtyOfLine(job)).as("line done: 2 of 2").isEqualByComparingTo("2");
		final PickingJob jobWhenDone = job;

		final List<String> log = logOfPickCommand(() -> assertThatThrownBy(() -> pick(jobWhenDone, source, "1", label(PRODUCT_NO, LOT_A), false, null))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(PickingJobPickCommand.PICK_OVERDELIVERY_BLOCKED_ERROR_MSG.toAD_Message()))
				.satisfies(ex -> assertThat(((AdempiereException)ex).getMessage()).doesNotContain("pickingJobId", "pickingJobLineId", "qtyToPick", "qtyOrdered", "qtyAlreadyPicked")));
		assertThat(log).as("the discarded scan is logged with its values")
				.anySatisfy(msg -> assertThat(msg).contains("Over-pick refused", "SCAN_ONCE_ENTER_QTY", "pickingJobId=" + jobWhenDone.getId().getRepoId(), "pickingJobLineId=", "qtyToPick=1", "qtyOrdered=2", "qtyAlreadyPicked=2"));
		assertThat(pickedQtyOfLine(helper.pickingJobService.getById(job.getId()))).as("still 2 picked").isEqualByComparingTo("2");
	}

	@Test
	void tuLine_scanOnce_n5_oneTUOnLinePacking_oneCUOf5_withLotAndBestBefore()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = sourceTU("QR-A");
		PickingJob job = newJob().packedWith(tuPiipId).create();
		assertLabelRequiredLineCountsCUs(job);
		job = openTUFromLinePacking(job);

		job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null);

		final HuId tuId = openTUId(job);
		assertThat(handlingUnitsBL.getPI(handlingUnitsBL.getById(tuId)).getM_HU_PI_ID()).as("TU on the line packing's PI").isEqualTo(tuPIId.getRepoId());
		final List<I_M_HU> cus = cusOf(tuId);
		assertThat(cus).as("one CU in the TU").hasSize(1);
		assertThat(qtyOf(cus.get(0))).isEqualByComparingTo("5");
		assertThat(lotOf(cus.get(0))).isEqualTo(LOT_A);
		assertThat(attributesOf(cus.get(0)).getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate)).isEqualTo(LocalDate.of(2026, 11, 7));
		final ImmutableSet<HuId> pickedHUIds = CollectionUtils.singleElement(job.getLines()).getPickedHUIds();
		assertThat(pickedHUIds).as("the picked HU is the CU").containsExactly(HuId.ofRepoId(cus.get(0).getM_HU_ID()));
	}

	@Test
	void secondPickIntoTheOpenTU_secondCUOf3()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = sourceTU("QR-B");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());
		job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null);

		job = pick(job, source, "3", label(PRODUCT_NO, LOT_A), false, null);

		final HuId tuId = openTUId(job);
		assertThat(cusOf(tuId)).extracting(this::qtyOf).usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
				.containsExactlyInAnyOrder(new BigDecimal("5"), new BigDecimal("3"));
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).hasSize(2);
	}

	@Test
	void labelOfAnotherProduct_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = sourceTU("QR-F");
		final PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());

		assertFails(() -> pick(job, source, "5", label("OTHER99", LOT_A), false, null), MobileQRCodeMessages.HU_PRODUCT_NOT_MATCHING);

		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("8");
	}

	@Test
	void tuTargetFromGRAIScan_bookedIntoIt_graiKept()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final I_M_HU_PI graiTuPI = InterfaceWrapperHelper.load(tuPIId.getRepoId(), I_M_HU_PI.class);
		final I_M_HU_PI_GRAI map = InterfaceWrapperHelper.newInstance(I_M_HU_PI_GRAI.class);
		map.setGRAI_CompanyPrefix("7613204");
		map.setGRAI_AssetType("00307");
		map.setM_HU_PI_ID(graiTuPI.getM_HU_PI_ID());
		InterfaceWrapperHelper.save(map);
		final I_M_Attribute graiAttribute = new AttributesTestHelper().createM_Attribute(AttributeConstants.ATTR_GRAI.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		huTestHelper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(graiAttribute)
				.setM_HU_PI(graiTuPI)
				.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
		final HUInfo source = sourceTU("QR-G");
		PickingJob job = newJob().packedWith(tuPiipId).create();
		job = helper.pickingJobService.createTUFromGRAI(job, targetScope(job), ScannedCode.ofString(GRAI_CANONICAL));

		job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null);

		final HuId tuId = openTUId(job);
		assertThat(cusOf(tuId)).hasSize(1);
		assertThat(qtyOf(cusOf(tuId).get(0))).isEqualByComparingTo("5");
		final GRAISet grais = CollectionUtils.singleElement(ImmutableList.copyOf(graiSnapshotsOfTUs(ImmutableSet.of(tuId)))).getAllGrais();
		assertThat(grais.toSet()).as("GRAI kept on the TU").containsExactly(GRAI.parse(GRAI_CANONICAL));
	}

	@Test
	void isPickWholeTU_ignored_oneCUOfN()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = sourceTU("QR-I");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());

		job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), true, null);

		final HuId tuId = openTUId(job);
		final List<I_M_HU> cus = cusOf(tuId);
		assertThat(cus).hasSize(1);
		assertThat(qtyOf(cus.get(0))).isEqualByComparingTo("5");
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).containsExactly(HuId.ofRepoId(cus.get(0).getM_HU_ID()));
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the source TU is not the picked HU").doesNotContain(source.getId());
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).as("the source TU keeps the rest").isEqualByComparingTo("3");
	}

	@Test
	void labelRequiredMode_noCuLabel_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = sourceTU("QR-J");
		final PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());

		assertThatThrownBy(() -> pick(job, source, "5", null, false, null))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(PickingCULabelService.MSG_CU_LABEL_REQUIRED.toAD_Message()))
				.satisfies(ex -> assertThat(ex.getMessage()).as("no English technical text for the picker").doesNotContain("A CU label is required"));

		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("8");
	}

	@Test
	void lotsAThenB_intoOneTU_eachCUKeepsItsLot_tuLotEmpty()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = sourceTU("QR-K");
		PickingJob job = openTUFromLinePacking(newJob().packedWith(tuPiipId).create());

		job = pick(job, source, "2", label(PRODUCT_NO, LOT_A), false, null);
		job = pick(job, source, "3", label(PRODUCT_NO, LOT_B), false, null);

		final HuId tuId = openTUId(job);
		assertThat(lotOf(cuWithQty(tuId, "2"))).isEqualTo(LOT_A);
		assertThat(lotOf(cuWithQty(tuId, "3"))).isEqualTo(LOT_B);
		assertThat(lotOf(handlingUnitsBL.getById(tuId))).as("TU lot empty (mixed lots)").isNull();
	}
}
