package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.i18n.AdMessageKey;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.scannable_code.format.ParsedScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest.Part;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The new-mode pick ({@code SCAN_ONCE_ENTER_QTY}) through the real pick command path
 * ({@link PickingJobService#processStepEvent}): label read, CUs counted on TU lines, open-box refusal, one CU of N.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobCUQtyEntryPickTest
{
	private static final String GS = "\u001d";
	private static final String PRODUCT_NO = "PRD0001";
	private static final String LOT_A = "LOTAAAAAA";
	private static final String LOT_B = "LOTBBBBBB";
	private static final String GTIN = "04012345678901";
	private static final String GRAI_CANONICAL = "7613204.00307.999999";

	private static final AdMessageKey CU_NO_OPEN_BOX = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_NO_OPEN_BOX");
	private static final AdMessageKey CU_LABEL_FORMAT_MISMATCH = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_FORMAT_MISMATCH");
	private static final AdMessageKey CU_LABEL_ALREADY_PICKED = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_ALREADY_PICKED");
	private static final AdMessageKey CU_LABEL_NO_PACK_ID = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_NO_PACK_ID");
	private static final AdMessageKey HU_PRODUCT_NOT_MATCHING = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");

	private PickingJobTestHelper helper;
	private HUTestHelper huTestHelper;
	private IHandlingUnitsBL handlingUnitsBL;
	private ScannableCodeFormatService formatService;
	private ScannableCodeFormat format;

	private ProductId productId;
	private HuPackingInstructionsId tuPIId;
	private HUPIItemProductId tuPiipId;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper(() -> new FakePackIdPickingCULabelService(ScannableCodeFormatService.newInstanceForUnitTesting()));
		huTestHelper = helper.getHuTestHelper();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		formatService = ScannableCodeFormatService.newInstanceForUnitTesting();

		final I_M_Attribute packIdAttribute = new AttributesTestHelper().createM_Attribute(PickingCULabelService.ATTR_PackId.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		huTestHelper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(packIdAttribute)
				.setM_HU_PI(huTestHelper.huDefVirtual)
				.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));

		productId = BusinessTestHelper.createProductId(PRODUCT_NO, helper.uomEach);
		final I_M_HU_PI tuPI = huTestHelper.createHUDefinition("TU-4", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuPIItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		final I_M_HU_PI_Item_Product piip = huTestHelper.assignProduct(tuPIItem, productId, new BigDecimal("4"), helper.uomEach);
		piip.setIsDefaultForProduct(true);
		InterfaceWrapperHelper.save(piip);
		tuPIId = HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID());
		tuPiipId = HUPIItemProductId.ofRepoId(piip.getM_HU_PI_Item_Product_ID());

		format = formatService.create(ScannableCodeFormatCreateRequest.builder()
				.name("CU label")
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
						part(49, 49, ScannableCodeFormatPartType.Ignored)))
				.build());
	}

	/**
	 * Fake pack IDs: the pack ID part type is delivered by the label reader, not by this issue. The pack ID of the fake is the text after the last
	 * "GS 92" of the raw label; "-" = the label carries none.
	 */
	private static class FakePackIdPickingCULabelService extends PickingCULabelService
	{
		FakePackIdPickingCULabelService(final ScannableCodeFormatService scannableCodeFormatService) {super(scannableCodeFormatService);}

		@Override
		protected CULabelData readCULabelData(final ParsedScannedCode parsed, final ScannedCode label)
		{
			final String code = label.getAsString();
			final String packIdText = code.substring(code.lastIndexOf(GS + "92") + 3);
			return CULabelData.builder()
					.productNo(parsed.getProductNo())
					.lotNo(parsed.getLotNo())
					.bestBeforeDate(parsed.getBestBeforeDate())
					.packId("-".equals(packIdText) ? null : packIdText)
					.build();
		}
	}

	private static ScannedCode eachLabel(final String lot, final String packId)
	{
		return ScannedCode.ofString("01" + GTIN + "91" + PRODUCT_NO + GS + "17" + "261107" + "10" + lot + GS + "92" + packId);
	}

	private static Part constant(final int start, final int end, final String value)
	{
		return Part.builder().startPosition(start).endPosition(end).type(ScannableCodeFormatPartType.Constant).constantValue(value).build();
	}

	private static Part part(final int start, final int end, final ScannableCodeFormatPartType type)
	{
		return Part.builder().startPosition(start).endPosition(end).type(type).build();
	}

	private static ScannedCode label(final String productNo, final String lot)
	{
		return ScannedCode.ofString("01" + GTIN + "91" + productNo + GS + "17" + "261107" + "10" + lot + GS + "92" + "1");
	}

	private void setProfile(final CUQtyEntryMode mode)
	{
		helper.updateMobileProfile(profile -> profile.toBuilder()
				.defaultPickingJobOptions(profile.getDefaultPickingJobOptions().toBuilder()
						.cuQtyEntryMode(mode)
						.cuLabelFormatId(format.getId())
						.build())
				.build());
	}

	private PickingJob createJob(@Nullable final HUPIItemProductId packingPiipId, final boolean allowAnyHU)
	{
		return createJob("salesOrderCU", packingPiipId, allowAnyHU);
	}

	private PickingJob createJob(final String orderDocNo, @Nullable final HUPIItemProductId packingPiipId, final boolean allowAnyHU)
	{
		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId(orderDocNo);
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.huPIItemProductId(packingPiipId)
				.qtyToDeliver("100")
				.build();

		return helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.SALES_ORDER)
								.pickerId(UserId.ofRepoId(1234))
								.salesOrderId(orderAndLineId.getOrderId())
								.deliveryBPLocationId(helper.shipToBPLocationId)
								.isAllowPickingAnyHU(allowAnyHU)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
	}

	private PickingJob openBoxFromLinePacking(final PickingJob job)
	{
		return helper.pickingJobService.setTUPickingTarget(job, targetScope(job), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
	}

	/** the scope the pick command writes the target to: the line for line-level jobs, else the job (as the app sets it) */
	@Nullable
	private static de.metas.handlingunits.picking.job.model.PickingJobLineId targetScope(final PickingJob job)
	{
		return job.isLineLevelPickTarget() ? CollectionUtils.singleElement(job.getLines()).getId() : null;
	}

	private PickingJob pick(
			final PickingJob job,
			final HUInfo source,
			final String qty,
			@Nullable final ScannedCode cuLabel,
			final boolean isPickWholeTU,
			@Nullable final String manualLotNo)
	{
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		return helper.pickingJobService.processStepEvent(job, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(source.getQrCode().toScannedCode())
				.qtyPicked(new BigDecimal(qty))
				.isPickWholeTU(isPickWholeTU)
				.isSetLotNo(manualLotNo != null)
				.lotNo(manualLotNo)
				.cuLabel(cuLabel)
				.build());
	}

	private HuId openBoxId(final PickingJob job)
	{
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		final TUPickingTarget target = job.getTuPickingTargetEffective(line.getId()).orElseThrow(() -> new AssertionError("no TU target"));
		assertThat(target.isNewTU()).as("the box was materialised").isFalse();
		return target.getTuIdNotNull();
	}

	private List<I_M_HU> cusOf(final HuId boxId)
	{
		return handlingUnitsBL.retrieveIncludedHUs(handlingUnitsBL.getById(boxId));
	}

	private BigDecimal qtyOf(final I_M_HU hu)
	{
		final IHUStorage storage = huTestHelper.createMutableHUContextOutOfTransaction().getHUStorageFactory().getStorage(hu);
		return storage.getQuantity(productId, helper.uomEach).toBigDecimal();
	}

	private IAttributeStorage attributesOf(final I_M_HU hu)
	{
		final IHUContext huContext = huTestHelper.createMutableHUContextOutOfTransaction();
		return huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
	}

	@Nullable
	private String lotOf(final I_M_HU hu)
	{
		final IAttributeStorage storage = attributesOf(hu);
		assertThat(storage.hasAttribute(AttributeConstants.ATTR_LotNumber)).as("HU carries the lot attribute").isTrue();
		final String lot = storage.getValueAsString(AttributeConstants.ATTR_LotNumber);
		return lot == null || lot.trim().isEmpty() ? null : lot;
	}

	private static void assertFails(final Runnable runnable, final AdMessageKey expected)
	{
		assertThatThrownBy(runnable::run)
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(expected.toAD_Message()));
	}

	private I_M_HU cuWithQty(final HuId boxId, final String qty)
	{
		return cusOf(boxId).stream()
				.filter(cu -> qtyOf(cu).compareTo(new BigDecimal(qty)) == 0)
				.findFirst()
				.orElseThrow(() -> new AssertionError("no CU of " + qty + " in box " + boxId));
	}

	/** (a) TU line "TU x 4", Scan once, TU target from the line's packing, N=5: one TU on the line's PI with ONE CU of 5 carrying the label's lot and best-before. */
	@Test
	void a_tuLine_scanOnce_n5_oneBoxOnLinePacking_oneCUOf5_withLotAndBestBefore()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-A");
		PickingJob job = createJob(tuPiipId, true);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickingUnit().isTU()).as("TU line").isTrue();
		job = openBoxFromLinePacking(job);

		job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null);

		final HuId boxId = openBoxId(job);
		assertThat(handlingUnitsBL.getPI(handlingUnitsBL.getById(boxId)).getM_HU_PI_ID()).as("box on the line packing's PI").isEqualTo(tuPIId.getRepoId());
		final List<I_M_HU> cus = cusOf(boxId);
		assertThat(cus).as("one CU in the box").hasSize(1);
		assertThat(qtyOf(cus.get(0))).isEqualByComparingTo("5");
		assertThat(lotOf(cus.get(0))).isEqualTo(LOT_A);
		assertThat(attributesOf(cus.get(0)).getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate)).isEqualTo(java.time.LocalDate.of(2026, 11, 7));
		final ImmutableSet<HuId> pickedHUIds = CollectionUtils.singleElement(job.getLines()).getPickedHUIds();
		assertThat(pickedHUIds).as("the picked HU is the CU").containsExactly(HuId.ofRepoId(cus.get(0).getM_HU_ID()));
	}

	/** (b) second pick of 3 into the open box: a second CU of 3, no "Cannot pick TUs when TU pick target is set!". */
	@Test
	void b_secondPickIntoTheOpenBox_secondCUOf3()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-B");
		PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));
		job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null);

		job = pick(job, source, "3", label(PRODUCT_NO, LOT_A), false, null);

		final HuId boxId = openBoxId(job);
		assertThat(cusOf(boxId)).extracting(this::qtyOf).usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
				.containsExactlyInAnyOrder(new BigDecimal("5"), new BigDecimal("3"));
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).hasSize(2);
	}

	/** (c) unlimited-capacity line naming a TU PI, no TU target: refused CU_NO_OPEN_BOX, nothing booked. */
	@Test
	void c_unlimitedCapacityLineNamingATuPI_noTarget_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final I_M_HU_PI_Item_Product piip = InterfaceWrapperHelper.load(tuPiipId.getRepoId(), I_M_HU_PI_Item_Product.class);
		piip.setIsInfiniteCapacity(true);
		InterfaceWrapperHelper.save(piip);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-C");
		final PickingJob job = createJob(tuPiipId, true);

		assertFails(() -> pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null), CU_NO_OPEN_BOX);

		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).as("nothing booked").isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).as("source untouched").isEqualByComparingTo("100");
	}

	/** (d) virtual-packing line (no TU PI), no TU target: refused CU_NO_OPEN_BOX (no loose CU). */
	@Test
	void d_virtualPackingLine_noTarget_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-D");
		final PickingJob job = createJob(null, true);

		assertFails(() -> pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null), CU_NO_OPEN_BOX);

		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("100");
	}

	/** (e) Manual: no flag and no refusal: a TU line without a TU target is booked as today (the full TU, not a partial one). */
	@Test
	void e_manual_noTarget_notRefused_todaysFullTU()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-E");
		PickingJob job = createJob(tuPiipId, true);

		job = pick(job, source, "1", null, false, null); // Manual on a TU line: 1 TU = 4 CUs

		final ImmutableSet<HuId> picked = CollectionUtils.singleElement(job.getLines()).getPickedHUIds();
		assertThat(picked).hasSize(1);
		final I_M_HU pickedHU = handlingUnitsBL.getById(picked.iterator().next());
		assertThat(handlingUnitsBL.isTransportUnitOrAggregate(pickedHU)).as("today: the picked HU is the box (TU)").isTrue();
		assertThat(qtyOf(pickedHU)).isEqualByComparingTo("4");
	}

	/** (f) cuLabel of another product: HU_PRODUCT_NOT_MATCHING, nothing booked. */
	@Test
	void f_labelOfAnotherProduct_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-F");
		final PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

		assertFails(() -> pick(job, source, "5", label("OTHER99", LOT_A), false, null), HU_PRODUCT_NOT_MATCHING);

		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("100");
	}

	/** (g) TU target set by a GRAI scan: booked into it, the GRAI stays on the TU. */
	@Test
	void g_tuTargetFromGRAIScan_bookedIntoIt_graiKept()
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
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-G");
		PickingJob job = createJob(tuPiipId, true);
		job = helper.pickingJobService.createTUFromGRAI(job, targetScope(job), ScannedCode.ofString(GRAI_CANONICAL));

		job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null);

		final HuId boxId = openBoxId(job);
		assertThat(cusOf(boxId)).hasSize(1);
		assertThat(qtyOf(cusOf(boxId).get(0))).isEqualByComparingTo("5");
		final GRAISet grais = CollectionUtils.singleElement(ImmutableList.copyOf(helper.huService.getGraiSnapshots(ImmutableSet.of(boxId)))).getAllGrais();
		assertThat(grais.toSet()).as("GRAI kept on the box").containsExactly(GRAI.parse(GRAI_CANONICAL));
	}

	/** (h) Scan each CU, no TU target: refused CU_NO_OPEN_BOX, nothing booked. */
	@Test
	void h_scanEachCU_noTarget_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-H");
		final PickingJob job = createJob(tuPiipId, true);

		assertFails(() -> pick(job, source, "1", label(PRODUCT_NO, LOT_A), false, null), CU_NO_OPEN_BOX);

		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("100");
	}

	/** (i) Scan once with isPickWholeTU = true and an open box: one CU of N into the box, no whole TU taken. */
	@Test
	void i_isPickWholeTU_ignored_oneCUOfN()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-I");
		PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

		job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), true, null);

		final HuId boxId = openBoxId(job);
		final List<I_M_HU> cus = cusOf(boxId);
		assertThat(cus).hasSize(1);
		assertThat(qtyOf(cus.get(0))).isEqualByComparingTo("5");
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).containsExactly(HuId.ofRepoId(cus.get(0).getM_HU_ID()));
	}

	/** (j) new mode, open box, PICK with cuLabel = null: refused CU_LABEL_FORMAT_MISMATCH, nothing booked. */
	@Test
	void j_newMode_noCuLabel_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-J");
		final PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

		assertFails(() -> pick(job, source, "5", null, false, null), CU_LABEL_FORMAT_MISMATCH);

		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("100");
	}

	/** (k, added 2026-11-19 from review-A8 finding 3) Scan once, lots A then B into one open box: each CU keeps its lot AND the TU's lot is empty. */
	@Test
	void k_lotsAThenB_intoOneBox_eachCUKeepsItsLot_tuLotEmpty()
	{
		setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-K");
		PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

		job = pick(job, source, "2", label(PRODUCT_NO, LOT_A), false, null);
		job = pick(job, source, "3", label(PRODUCT_NO, LOT_B), false, null);

		final HuId boxId = openBoxId(job);
		assertThat(lotOf(cuWithQty(boxId, "2"))).isEqualTo(LOT_A);
		assertThat(lotOf(cuWithQty(boxId, "3"))).isEqualTo(LOT_B);
		assertThat(lotOf(handlingUnitsBL.getById(boxId))).as("TU lot empty (mixed lots)").isNull();
	}

	/** (l, added 2026-11-19 from review-A8 finding 4) flags off (Manual, TC29): lot overwritten as today. */
	@Test
	void l_manual_lotsAThenB_intoOneBox_lotOverwrittenAsToday()
	{
		setProfile(CUQtyEntryMode.MANUAL);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-L");
		final HUInfo source2 = helper.createVHUInfo(productId, "100", "QR-L2");
		PickingJob job = createJob(null, true); // virtual packing = a CU line, so Manual can book into an open box
		job = openBoxFromLinePacking(job);

		job = pick(job, source, "2", null, false, LOT_A);
		final HuId boxId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
		assertThat(handlingUnitsBL.isTransportUnitOrAggregate(handlingUnitsBL.getById(boxId))).as("today: the first booking's picked HU is the box").isTrue();
		job = helper.pickingJobService.setTUPickingTarget(job, targetScope(job), TUPickingTarget.ofExistingHU(boxId, helper.huService.getQRCodeByHuId(boxId)));
		job = pick(job, source2, "3", null, false, LOT_B);

		assertThat(lotOf(handlingUnitsBL.getById(boxId))).as("TU lot overwritten by the second booking").isEqualTo(LOT_B);
		assertThat(cusOf(boxId)).extracting(this::lotOf).as("today: the second lot is written on the whole box").containsOnly(LOT_B);
	}

	@Nullable
	private String packIdOf(final I_M_HU hu)
	{
		final IAttributeStorage storage = attributesOf(hu);
		assertThat(storage.hasAttribute(PickingCULabelService.ATTR_PackId)).as("HU carries the PackId attribute").isTrue();
		final String packId = storage.getValueAsString(PickingCULabelService.ATTR_PackId);
		return packId == null || packId.trim().isEmpty() ? null : packId;
	}

	private I_M_HU cuWithPackId(final HuId boxId, final String packId)
	{
		return cusOf(boxId).stream()
				.filter(cu -> packId.equals(packIdOf(cu)))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no CU with pack ID " + packId + " in box " + boxId));
	}

	private long countQtyPickedRowsOfBox(final HuId boxId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_TU_HU_ID, boxId)
				.addOnlyActiveRecordsFilter()
				.create()
				.count();
	}

	private PickingJob pickEach(final PickingJob job, final HUInfo source, final String lot, final String packId)
	{
		return pick(job, source, "1", eachLabel(lot, packId), false, null);
	}

	private void assertNothingBooked(final PickingJob job, final HUInfo source, final String sourceQty)
	{
		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).as("nothing booked").isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).as("source untouched").isEqualByComparingTo(sourceQty);
	}

	/** (m) Scan each CU, lots A, A, B (own pack IDs) into one open box: 3 CUs of 1, each with its own lot and PackId, the TU's lot empty. */
	@Test
	void m_scanEachCU_lotsAAB_threeCUsOfOne_ownLotAndPackId_tuLotEmpty()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-M");
		PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

		job = pickEach(job, source, LOT_A, "1");
		job = pickEach(job, source, LOT_A, "2");
		job = pickEach(job, source, LOT_B, "3");

		final HuId boxId = openBoxId(job);
		final List<I_M_HU> cus = cusOf(boxId);
		assertThat(cus).as("3 CUs in the box").hasSize(3);
		cus.forEach(cu -> assertThat(qtyOf(cu)).as("each CU is 1 pack").isEqualByComparingTo("1"));
		assertThat(cus).extracting(this::lotOf, this::packIdOf)
				.containsExactlyInAnyOrder(
						org.assertj.core.groups.Tuple.tuple(LOT_A, "1"),
						org.assertj.core.groups.Tuple.tuple(LOT_A, "2"),
						org.assertj.core.groups.Tuple.tuple(LOT_B, "3"));
		assertThat(lotOf(handlingUnitsBL.getById(boxId))).as("TU lot empty (mixed lots)").isNull();
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).hasSize(3);
	}

	/** (n) the server books qty 1 per label, whatever quantity the client sent. */
	@Test
	void n_scanEachCU_clientSendsQty3_oneCUOfOneBooked()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-N");
		PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

		job = pick(job, source, "3", eachLabel(LOT_A, "1"), false, null);

		final List<I_M_HU> cus = cusOf(openBoxId(job));
		assertThat(cus).hasSize(1);
		assertThat(qtyOf(cus.get(0))).isEqualByComparingTo("1");
		assertThat(packIdOf(cus.get(0))).isEqualTo("1");
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("99");
	}

	/** (o) the same (product, lot, pack ID) again in the job: CU_LABEL_ALREADY_PICKED, nothing booked. */
	@Test
	void o_repeatedLotAndPackId_refused_nothingBooked()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-O");
		PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));
		job = pickEach(job, source, LOT_A, "1");
		final PickingJob jobBefore = job;

		assertFails(() -> pickEach(jobBefore, source, LOT_A, "1"), CU_LABEL_ALREADY_PICKED);

		assertThat(cusOf(openBoxId(job))).as("still one CU").hasSize(1);
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("99");
	}

	/** (p) the same pack ID on another lot is a different pack: booked. */
	@Test
	void p_samePackIdOnAnotherLot_booked()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-P");
		PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

		job = pickEach(job, source, LOT_A, "1");
		job = pickEach(job, source, LOT_B, "1");

		final List<I_M_HU> cus = cusOf(openBoxId(job));
		assertThat(cus).hasSize(2);
		assertThat(cus).extracting(this::lotOf, this::packIdOf)
				.containsExactlyInAnyOrder(org.assertj.core.groups.Tuple.tuple(LOT_A, "1"), org.assertj.core.groups.Tuple.tuple(LOT_B, "1"));
	}

	/** (q) the same label in another job is booked: the lookup reads only the job's own picked HUs. */
	@Test
	void q_sameLabelInAnotherJob_booked()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-Q");
		PickingJob job1 = openBoxFromLinePacking(createJob("salesOrderCU1", tuPiipId, true));
		job1 = pickEach(job1, source, LOT_A, "1");
		PickingJob job2 = openBoxFromLinePacking(createJob("salesOrderCU2", tuPiipId, true));

		job2 = pickEach(job2, source, LOT_A, "1");

		final List<I_M_HU> cus2 = cusOf(openBoxId(job2));
		assertThat(cus2).hasSize(1);
		assertThat(packIdOf(cus2.get(0))).isEqualTo("1");
		assertThat(lotOf(cus2.get(0))).isEqualTo(LOT_A);
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("98");
	}

	/** (r) the box's first pack: its picked HU is the CU; a rescan is refused; undo leaves only that CU Active (box and 2nd CU stay Picked, their QtyPicked rows stay); the rescan books. */
	@Test
	void r_firstPackOfTheBox_duplicate_undo_rescan()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-R");
		PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));
		job = pickEach(job, source, LOT_A, "1");
		job = pickEach(job, source, LOT_A, "2");
		final HuId boxId = openBoxId(job);
		final I_M_HU cu1 = cuWithPackId(boxId, "1");
		final HuId cu1Id = HuId.ofRepoId(cu1.getM_HU_ID());
		final I_M_HU cu2 = cuWithPackId(boxId, "2");
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HUs are the CUs, not the box").containsExactlyInAnyOrder(cu1Id, HuId.ofRepoId(cu2.getM_HU_ID()));
		final PickingJob jobBeforeRescan = job;
		assertFails(() -> pickEach(jobBeforeRescan, source, LOT_A, "1"), CU_LABEL_ALREADY_PICKED);
		assertThat(cusOf(boxId)).as("still 2 CU HUs").hasSize(2);
		assertThat(countQtyPickedRowsOfBox(boxId)).isEqualTo(2);

		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		final de.metas.handlingunits.picking.job.model.PickingJobStep stepOfCU1 = line.streamSteps()
				.filter(step -> step.getPickedHUIds().contains(cu1Id))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no step with the CU of pack 1"));
		job = helper.pickingJobService.processStepEvent(job, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(stepOfCU1.getId())
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.UNPICK)
				.qrCode(source.getQrCode().toScannedCode())
				.build());

		assertThat(handlingUnitsBL.getById(cu1Id).getHUStatus()).as("CU of pack 1 Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
		assertThat(handlingUnitsBL.getById(boxId).getHUStatus()).as("box stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
		assertThat(handlingUnitsBL.getById(HuId.ofRepoId(cu2.getM_HU_ID())).getHUStatus()).as("CU of pack 2 stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("only the CU of pack 2 is still picked").containsExactly(HuId.ofRepoId(cu2.getM_HU_ID()));
		assertThat(countQtyPickedRowsOfBox(boxId)).as("QtyPicked row of the 2nd CU stays").isEqualTo(1);

		job = pickEach(job, source, LOT_A, "1");

		assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("rescan booked").hasSize(2);
	}

	/** (s) the label carries no pack ID: CU_LABEL_NO_PACK_ID, nothing booked. */
	@Test
	void s_labelWithoutPackId_refused()
	{
		setProfile(CUQtyEntryMode.SCAN_EACH_CU);
		final HUInfo source = helper.createVHUInfo(productId, "100", "QR-S");
		final PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

		assertFails(() -> pickEach(job, source, LOT_A, "-"), CU_LABEL_NO_PACK_ID);

		assertNothingBooked(job, source, "100");
	}
}
