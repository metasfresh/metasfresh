package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfig;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfigsCollection;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptionsId;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepEvent;
import de.metas.handlingunits.picking.job.model.PickingJobStepEventType;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.commands.PickingJobCreateRequest;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.i18n.AdMessageKey;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest.Part;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatPart;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

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
		helper = new PickingJobTestHelper();
		huTestHelper = helper.getHuTestHelper();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		formatService = ScannableCodeFormatService.newInstanceForUnitTesting();

		final I_M_Attribute packIdAttribute = new AttributesTestHelper().createM_Attribute(AttributeConstants.PackId.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
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
						part(49, ScannableCodeFormatPart.OPEN_END, ScannableCodeFormatPartType.PackId)))
				.build());
	}

	/** @param packId null = a label that ends before the pack ID */
	private static ScannedCode eachLabel(final String lot, @Nullable final String packId)
	{
		return ScannedCode.ofString("01" + GTIN + "91" + PRODUCT_NO + GS + "17" + "261107" + "10" + lot + GS + "92" + (packId != null ? packId : ""));
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

	private PickingJob createJob(@Nullable final HUPIItemProductId packingPiipId, final boolean isAllowAnyHU)
	{
		return createJob("salesOrderCU", packingPiipId, isAllowAnyHU);
	}

	private PickingJob createJob(final String orderDocNo, @Nullable final HUPIItemProductId packingPiipId, final boolean isAllowAnyHU)
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
								.isAllowPickingAnyHU(isAllowAnyHU)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
	}

	private PickingJob openBoxFromLinePacking(final PickingJob job)
	{
		return helper.pickingJobService.setTUPickingTarget(job, targetScope(job), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
	}

	/** the scope the pick command writes the target to: the line for line-level jobs, else the job (as the app sets it) */
	@Nullable
	private static PickingJobLineId targetScope(final PickingJob job)
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

	/** the source of a pick: a box holding two pieces of 4 (the pick takes N across the pieces) */
	private HUInfo sourceBox(final String qrCodeId)
	{
		final HuId boxId = helper.createHU(tuPIId, productId, helper.qty("4", productId));
		final HuId secondPiece = helper.createVHU(productId, "4");
		HUTransformService.newInstance(huTestHelper.createMutableHUContextOutOfTransaction())
				.addCUsToTU(ImmutableList.of(handlingUnitsBL.getById(secondPiece)), handlingUnitsBL.getById(boxId));
		return HUInfo.builder().id(boxId).qrCode(helper.createQRCode(boxId, qrCodeId)).build();
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

	/** a new-mode line counts CUs even when its packing is a box (TU) packing: the line's picking unit is CU and the quantities are in CUs (100 CU, not 25 TU) */
	private void assertNewModeLineCountsCUs(final PickingJob job)
	{
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		assertThat(line.getPickingUnit().isCU()).as("new mode: picking unit is CU").isTrue();
		assertThat(line.getQtyToPick().toBigDecimal()).as("qty to pick in CUs").isEqualByComparingTo("100");
		assertThat(line.getQtyRemainingToPick().toBigDecimal()).as("qty remaining in CUs").isEqualByComparingTo("100");
	}

	@Nullable
	private String packIdOf(final I_M_HU hu)
	{
		final IAttributeStorage storage = attributesOf(hu);
		assertThat(storage.hasAttribute(AttributeConstants.PackId)).as("HU carries the PackId attribute").isTrue();
		final String packId = storage.getValueAsString(AttributeConstants.PackId);
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

	private PickingJob pickEach(final PickingJob job, final HUInfo source, final String lot, @Nullable final String packId)
	{
		return pick(job, source, "1", eachLabel(lot, packId), false, null);
	}

	private void assertNothingBooked(final PickingJob job, final HUInfo source, final String sourceQty)
	{
		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).as("nothing booked").isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).as("source untouched").isEqualByComparingTo(sourceQty);
	}

	private OrderAndLineId createPackageable(final String orderDocNo, final BPartnerLocationId shipToBPLocationId)
	{
		final OrderAndLineId orderAndLineId = helper.createOrderAndLineId(orderDocNo);
		helper.packageable()
				.orderAndLineId(orderAndLineId)
				.productId(productId)
				.shipToBPLocationId(shipToBPLocationId)
				.huPIItemProductId(tuPiipId)
				.qtyToDeliver("100")
				.build();
		return orderAndLineId;
	}

	private PickingJob pickOnLine(final PickingJob job, final PickingJobLineId lineId, final HUInfo source, final String qty, final ScannedCode cuLabel)
	{
		return helper.pickingJobService.processStepEvent(job, PickingJobStepEvent.builder()
				.pickingLineId(lineId)
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.PICK)
				.qrCode(source.getQrCode().toScannedCode())
				.qtyPicked(new BigDecimal(qty))
				.cuLabel(cuLabel)
				.build());
	}

	private static PickingSlotId createPickingSlot(final String name)
	{
		final I_M_PickingSlot pickingSlot = InterfaceWrapperHelper.newInstance(I_M_PickingSlot.class);
		pickingSlot.setPickingSlot(name);
		InterfaceWrapperHelper.save(pickingSlot);
		return PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID());
	}

	private static List<PickingSlotId> queuedSlotsOf(final HuId huId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_PickingSlot_HU.class)
				.addEqualsFilter(I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID, huId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(row -> PickingSlotId.ofRepoId(row.getM_PickingSlot_ID()))
				.collect(ImmutableList.toImmutableList());
	}

	private void mapGRAIToTheBoxType()
	{
		final I_M_HU_PI_GRAI map = InterfaceWrapperHelper.newInstance(I_M_HU_PI_GRAI.class);
		map.setGRAI_CompanyPrefix("7613204");
		map.setGRAI_AssetType("00307");
		map.setM_HU_PI_ID(tuPIId.getRepoId());
		InterfaceWrapperHelper.save(map);
		final I_M_Attribute graiAttribute = new AttributesTestHelper().createM_Attribute(AttributeConstants.ATTR_GRAI.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		huTestHelper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(graiAttribute)
				.setM_HU_PI(InterfaceWrapperHelper.load(tuPIId.getRepoId(), I_M_HU_PI.class))
				.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
	}

	private static TUPickingTarget openBoxTarget(final PickingJob job)
	{
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		return job.getTuPickingTargetEffective(line.getId()).orElseThrow(() -> new AssertionError("no TU target"));
	}

	private static long countAllQtyPickedRows()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.count();
	}

	private static long countQtyPickedRowsOfCU(final HuId cuId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID, cuId)
				.addOnlyActiveRecordsFilter()
				.create()
				.count();
	}

	private PickingJob undo(final PickingJob job, final HuId cuId, final HUQRCode sourceQr, @Nullable final HUQRCode unpickToTargetQr)
	{
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		final PickingJobStep step = line.streamSteps()
				.filter(candidate -> candidate.getPickedHUIds().contains(cuId))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no step with the CU " + cuId));
		return helper.pickingJobService.processStepEvent(job, PickingJobStepEvent.builder()
				.pickingLineId(line.getId())
				.pickingStepId(step.getId())
				.pickFromKey(PickingJobStepPickFromKey.MAIN)
				.eventType(PickingJobStepEventType.UNPICK)
				.qrCode(sourceQr.toScannedCode())
				.unpickToTargetQRCode(unpickToTargetQr)
				.build());
	}

	private static HUInfo pickedHUOfStepWithCU(final PickingJob job, final HuId cuId)
	{
		return CollectionUtils.singleElement(job.getLines()).streamSteps()
				.flatMap(step -> step.getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo().getActualPickedHUs().stream())
				.map(PickingJobStepPickedToHU::getActualPickedHU)
				.filter(hu -> hu.getId().equals(cuId))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no picked HU " + cuId));
	}

	private static BigDecimal pickedQtyOfLine(final PickingJob job)
	{
		return CollectionUtils.singleElement(job.getLines()).getQtyPicked().toBigDecimal();
	}

	private static final AdMessageKey GRAI_COUNT_MISMATCH = AdMessageKey.of("de.metas.handlingunits.picking.GRAICountMismatch");

	private void setCustomerGraiRequired(final GRAIRequired graiRequired)
	{
		final I_C_BPartner customer = InterfaceWrapperHelper.load(helper.shipToBPLocationId.getBpartnerId(), I_C_BPartner.class);
		customer.setGRAIRequired(graiRequired.getCode());
		InterfaceWrapperHelper.save(customer);
	}

	@Nested
	class Completion
	{
		/**
		 * Completion through the real {@link PickingJobService#complete} wiring: the customer's mode is resolved from its job options
		 * (not passed as a flag). Scan-each-CU picks the CU itself, which sits in a box without GRAI: the box is the GRAI carrier, so completion is refused.
		 */
		@Test
		void newMode_cuPickedInBoxWithoutGrai_graiRequiredYes_isRefused()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			setCustomerGraiRequired(GRAIRequired.Yes);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-GRAI-N");
			final PickingJob job = pickEach(openBoxFromLinePacking(createJob(tuPiipId, true)), source, LOT_A, "1");
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HU is the CU, not the box").doesNotContain(openBoxId(job));

			assertThatThrownBy(() -> helper.pickingJobService.complete(job))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(GRAI_COUNT_MISMATCH.toAD_Message());
		}

		/** Manual mode: the picked HU is the box itself (asserted below); the box has no GRAI, so completion is refused. */
		@Test
		void manualMode_boxWithoutGrai_graiRequiredYes_isRefusedAsBefore()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			setCustomerGraiRequired(GRAIRequired.Yes);
			final HUInfo source = sourceBox("QR-GRAI-M");
			final PickingJob job = pick(openBoxFromLinePacking(createJob(null, true)), source, "5", null, false, null);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("Manual: the picked HU is the box").containsExactly(openBoxId(job));

			assertThatThrownBy(() -> helper.pickingJobService.complete(job))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(GRAI_COUNT_MISMATCH.toAD_Message());
		}
	}

	@Nested
	class ScanOnce
	{
		/** TU line "TU x 4", Scan once, TU target from the line's packing, N=5: one TU on the line's PI with ONE CU of 5 carrying the label's lot and best-before. */
		@Test
		void tuLine_scanOnce_n5_oneBoxOnLinePacking_oneCUOf5_withLotAndBestBefore()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceBox("QR-A");
			PickingJob job = createJob(tuPiipId, true);
			assertNewModeLineCountsCUs(job);
			job = openBoxFromLinePacking(job);

			job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null);

			final HuId boxId = openBoxId(job);
			assertThat(handlingUnitsBL.getPI(handlingUnitsBL.getById(boxId)).getM_HU_PI_ID()).as("box on the line packing's PI").isEqualTo(tuPIId.getRepoId());
			final List<I_M_HU> cus = cusOf(boxId);
			assertThat(cus).as("one CU in the box").hasSize(1);
			assertThat(qtyOf(cus.get(0))).isEqualByComparingTo("5");
			assertThat(lotOf(cus.get(0))).isEqualTo(LOT_A);
			assertThat(attributesOf(cus.get(0)).getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate)).isEqualTo(LocalDate.of(2026, 11, 7));
			final ImmutableSet<HuId> pickedHUIds = CollectionUtils.singleElement(job.getLines()).getPickedHUIds();
			assertThat(pickedHUIds).as("the picked HU is the CU").containsExactly(HuId.ofRepoId(cus.get(0).getM_HU_ID()));
		}

		/** second pick of 3 into the open box: a second CU of 3, no "Cannot pick TUs when TU pick target is set!". */
		@Test
		void secondPickIntoTheOpenBox_secondCUOf3()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceBox("QR-B");
			PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));
			job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null);

			job = pick(job, source, "3", label(PRODUCT_NO, LOT_A), false, null);

			final HuId boxId = openBoxId(job);
			assertThat(cusOf(boxId)).extracting(PickingJobCUQtyEntryPickTest.this::qtyOf).usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
					.containsExactlyInAnyOrder(new BigDecimal("5"), new BigDecimal("3"));
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).hasSize(2);
		}

		/** cuLabel of another product: HU_PRODUCT_NOT_MATCHING, nothing booked. */
		@Test
		void labelOfAnotherProduct_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceBox("QR-F");
			final PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

			assertFails(() -> pick(job, source, "5", label("OTHER99", LOT_A), false, null), MobileQRCodeMessages.HU_PRODUCT_NOT_MATCHING);

			assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
			assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("8");
		}

		/** TU target set by a GRAI scan: booked into it, the GRAI stays on the TU. */
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
			final HUInfo source = sourceBox("QR-G");
			PickingJob job = createJob(tuPiipId, true);
			job = helper.pickingJobService.createTUFromGRAI(job, targetScope(job), ScannedCode.ofString(GRAI_CANONICAL));

			job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null);

			final HuId boxId = openBoxId(job);
			assertThat(cusOf(boxId)).hasSize(1);
			assertThat(qtyOf(cusOf(boxId).get(0))).isEqualByComparingTo("5");
			final GRAISet grais = CollectionUtils.singleElement(ImmutableList.copyOf(helper.huService.getGraiSnapshotsOfBoxes(ImmutableSet.of(boxId)))).getAllGrais();
			assertThat(grais.toSet()).as("GRAI kept on the box").containsExactly(GRAI.parse(GRAI_CANONICAL));
		}

		/** Scan once with isPickWholeTU = true and an open box: one CU of N into the box, no whole TU taken. */
		@Test
		void isPickWholeTU_ignored_oneCUOfN()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceBox("QR-I");
			PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

			job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), true, null);

			final HuId boxId = openBoxId(job);
			final List<I_M_HU> cus = cusOf(boxId);
			assertThat(cus).hasSize(1);
			assertThat(qtyOf(cus.get(0))).isEqualByComparingTo("5");
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).containsExactly(HuId.ofRepoId(cus.get(0).getM_HU_ID()));
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the source box is not the picked HU").doesNotContain(source.getId());
			assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).as("the source box keeps the rest").isEqualByComparingTo("3");
		}

		/** new mode, open box, PICK with cuLabel = null: refused as a contract violation (no user message), nothing booked. */
		@Test
		void newMode_noCuLabel_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceBox("QR-J");
			final PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

			assertThatThrownBy(() -> pick(job, source, "5", null, false, null))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("A CU label is required");

			assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
			assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("8");
		}

		/** Scan once, lots A then B into one open box: each CU keeps its lot AND the TU's lot is empty. */
		@Test
		void lotsAThenB_intoOneBox_eachCUKeepsItsLot_tuLotEmpty()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceBox("QR-K");
			PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

			job = pick(job, source, "2", label(PRODUCT_NO, LOT_A), false, null);
			job = pick(job, source, "3", label(PRODUCT_NO, LOT_B), false, null);

			final HuId boxId = openBoxId(job);
			assertThat(lotOf(cuWithQty(boxId, "2"))).isEqualTo(LOT_A);
			assertThat(lotOf(cuWithQty(boxId, "3"))).isEqualTo(LOT_B);
			assertThat(lotOf(handlingUnitsBL.getById(boxId))).as("TU lot empty (mixed lots)").isNull();
		}
	}

	@Nested
	class OpenBoxRequired
	{
		/** unlimited-capacity line naming a TU PI, no TU target: refused CU_NO_OPEN_BOX, nothing booked. */
		@Test
		void unlimitedCapacityLineNamingATuPI_noTarget_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final I_M_HU_PI_Item_Product piip = InterfaceWrapperHelper.load(tuPiipId.getRepoId(), I_M_HU_PI_Item_Product.class);
			piip.setIsInfiniteCapacity(true);
			InterfaceWrapperHelper.save(piip);
			final HUInfo source = sourceBox("QR-C");
			final PickingJob job = createJob(tuPiipId, true);

			assertFails(() -> pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null), CU_NO_OPEN_BOX);

			assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).as("nothing booked").isEmpty();
			assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).as("source untouched").isEqualByComparingTo("8");
		}

		/** virtual-packing line (no TU PI), no TU target: refused CU_NO_OPEN_BOX (no loose CU). */
		@Test
		void virtualPackingLine_noTarget_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceBox("QR-D");
			final PickingJob job = createJob(null, true);

			assertFails(() -> pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null), CU_NO_OPEN_BOX);

			assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
			assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("8");
		}

		/** Manual: no flag and no refusal: a TU line without a TU target is booked as today (the full TU, not a partial one). */
		@Test
		void manual_noTarget_notRefused_todaysFullTU()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-E");
			PickingJob job = createJob(tuPiipId, true);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickingUnit().isTU()).as("Manual: a TU packing line still counts TUs").isTrue();

			job = pick(job, source, "1", null, false, null); // Manual on a TU line: 1 TU = 4 CUs

			final ImmutableSet<HuId> picked = CollectionUtils.singleElement(job.getLines()).getPickedHUIds();
			assertThat(picked).hasSize(1);
			final I_M_HU pickedHU = handlingUnitsBL.getById(picked.iterator().next());
			assertThat(handlingUnitsBL.isTransportUnitOrAggregate(pickedHU)).as("today: the picked HU is the box (TU)").isTrue();
			assertThat(qtyOf(pickedHU)).isEqualByComparingTo("4");
		}

		/** Manual, virtual-packing line, no TU target, 5 from a box holding two pieces of 4: today's result, one picked HU of 5 without CUs. */
		@Test
		void manual_fiveFromTwoPieces_noTarget_onePickedHUOf5()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final HUInfo source = sourceBox("QR-E2");
			PickingJob job = createJob(null, true);

			job = pick(job, source, "5", null, false, null);

			final ImmutableSet<HuId> picked = CollectionUtils.singleElement(job.getLines()).getPickedHUIds();
			assertThat(picked).hasSize(1);
			final HuId pickedId = picked.iterator().next();
			assertThat(qtyOf(handlingUnitsBL.getById(pickedId))).isEqualByComparingTo("5");
			assertThat(cusOf(pickedId)).as("no CU split per source piece").isEmpty();
		}

		/** Manual, virtual-packing line, open box, 5 from a box holding two pieces of 4: today's result, one CU per source piece (4 + 1) in the box. */
		@Test
		void manual_fiveFromTwoPieces_openBox_oneCUPerSourcePiece()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final HUInfo source = sourceBox("QR-E3");
			PickingJob job = openBoxFromLinePacking(createJob(null, true));

			job = pick(job, source, "5", null, false, null);

			final HuId pickedBoxId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			assertThat(cusOf(pickedBoxId)).as("one CU per source piece").extracting(PickingJobCUQtyEntryPickTest.this::qtyOf).usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
					.containsExactlyInAnyOrder(new BigDecimal("4"), new BigDecimal("1"));
		}

		/** Scan each CU, no TU target: refused CU_NO_OPEN_BOX, nothing booked. */
		@Test
		void scanEachCU_noTarget_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = sourceBox("QR-H");
			final PickingJob job = createJob(tuPiipId, true);

			assertFails(() -> pick(job, source, "1", label(PRODUCT_NO, LOT_A), false, null), CU_NO_OPEN_BOX);

			assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
			assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("8");
		}
	}

	@Nested
	class ManualAndPallet
	{
		/** Manual (new-mode flags off), lots A then B into one open box: the TU lot is overwritten as today. */
		@Test
		void manual_lotsAThenB_intoOneBox_lotOverwrittenAsToday()
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
			assertThat(cusOf(boxId)).extracting(PickingJobCUQtyEntryPickTest.this::lotOf).as("today: the second lot is written on the whole box").containsOnly(LOT_B);
		}

		/**
		 * (y) Manual with a pallet (LU) and a box (TU) target: a booking of 2 (box capacity 4) books the box on the pallet as the picked HU,
		 * not a CU in a box, so the un-pick's CU-in-box delete does not apply; "Entpacken" leaves no QtyPicked row of the box and nothing
		 * picked (the Manual un-pick of a box, unchanged).
		 */
		@Test
		void manual_palletAndBox_pickedHUIsTheBox_undoLeavesNothingPicked()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-Y");
			final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-Y", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			huTestHelper.createHU_PI_Item_IncludedHU(luPI, InterfaceWrapperHelper.load(tuPIId.getRepoId(), I_M_HU_PI.class), new BigDecimal("10"));
			PickingJob job = createJob(null, true); // virtual packing = a CU line
			job = helper.pickingJobService.setLUPickingTarget(job, targetScope(job), LUPickingTarget.ofPackingInstructions(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()), "LU-Y"));
			job = openBoxFromLinePacking(job);

			job = pick(job, source, "2", null, false, LOT_A);

			final HuId boxId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final I_M_HU box = handlingUnitsBL.getById(boxId);
			assertThat(handlingUnitsBL.isTransportUnit(box)).as("Manual: the picked HU is the box").isTrue();
			assertThat(helper.huService.isCUInTU(box)).isFalse();
			assertThat(countQtyPickedRowsOfBox(boxId)).isEqualTo(1);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("2");

			job = undo(job, boxId, source.getQrCode(), null);

			assertThat(countQtyPickedRowsOfBox(boxId)).as("no QtyPicked row of the box left").isEqualTo(0);
			assertThat(pickedQtyOfLine(job)).as("nothing picked any more").isEqualByComparingTo("0");
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).isEmpty();
		}

		/**
		 * Manual, CU line, pallet (LU) target and no box (TU): the real pick (the LU_CU structure, as in the over-picking Playwright spec)
		 * puts the booked CU directly on the pallet. It is not a CU in a box, so the un-pick does not take the CU-in-box path and runs exactly
		 * the code of before this feature: the HU is not picked any more, the line has nothing picked, and the QtyPicked row of the CU is
		 * left as it is on the base branch (checked by running this test with the CU-in-box delete removed from the un-pick: same result).
		 * <p>
		 * KNOWN BASE DEFECT: the un-pick of a CU picked directly onto an LU leaves its {@code M_ShipmentSchedule_QtyPicked} row, because the
		 * row has {@code M_TU_HU_ID} = the CU at pick time ({@code HandlingUnitsBL.getTransportUnitHU} returns the CU itself, HandlingUnitsBL.java:846-851),
		 * while the un-pick lookup requires {@code M_TU_HU_ID IS NULL} (HUShipmentScheduleDAO.java:87-92). It is pinned here only so that Manual
		 * stays exactly as base; whoever fixes that defect must update the row assertions below.
		 */
		@Test
		void manual_cuPickedOntoPallet_unpick_restoresAsBase()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LUCU");
			final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-CU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			huTestHelper.createHU_PI_Item_IncludedHU(luPI, huTestHelper.huDefVirtual, new BigDecimal("10"));
			PickingJob job = createJob(null, true); // virtual packing = a CU line
			job = helper.pickingJobService.setLUPickingTarget(job, targetScope(job), LUPickingTarget.ofPackingInstructions(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()), "LU-CU"));

			job = pick(job, source, "2", null, false, LOT_A);

			final HuId pickedHuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final I_M_HU pickedHU = handlingUnitsBL.getById(pickedHuId);
			assertThat(handlingUnitsBL.isVirtual(pickedHU)).as("the picked HU is a CU").isTrue();
			assertThat(handlingUnitsBL.getLoadingUnitHU(pickedHU)).as("on the pallet").isNotNull();
			assertThat(handlingUnitsBL.getTransportUnitHU(pickedHU)).as("no box (TU) around it").satisfies(tu -> assertThat(tu == null || !handlingUnitsBL.isTransportUnit(tu)).isTrue());
			assertThat(helper.huService.isCUInTU(pickedHU)).isFalse();
			assertThat(countQtyPickedRowsOfCU(pickedHuId)).as("the pick booked its QtyPicked row").isEqualTo(1);
			assertThat(countAllQtyPickedRows()).isEqualTo(1);

			job = undo(job, pickedHuId, source.getQrCode(), null);

			assertThat(countQtyPickedRowsOfCU(pickedHuId)).as("KNOWN BASE DEFECT, pinned so Manual stays as base: the QtyPicked row of the CU is left; a fix of the defect must update this assertion").isEqualTo(1);
			assertThat(countAllQtyPickedRows()).as("no other QtyPicked row was touched").isEqualTo(1);
			assertThat(handlingUnitsBL.getById(pickedHuId).getHUStatus()).as("not picked any more").isEqualTo(X_M_HU.HUSTATUS_Active);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("0");
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).isEmpty();
		}

		/**
		 * A CU that sits directly on a pallet (LU), without a box (TU), is not a CU in a box: the un-pick's CU-in-box delete (and the
		 * GRAI check on the box) must not treat it as one. The tree is hand-built; the real pick's result is pinned by
		 * {@code manual_cuPickedOntoPallet_unpick_restoresAsBase}.
		 */
		@Test
		void cuDirectlyOnPallet_isNotACUInABox()
		{
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-Z", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			final I_M_HU_PI_Item luItem = huTestHelper.createHU_PI_Item_IncludedHU(luPI, InterfaceWrapperHelper.load(tuPIId.getRepoId(), I_M_HU_PI.class), new BigDecimal("10"));
			final I_M_HU lu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
			lu.setM_HU_PI_Version_ID(handlingUnitsDAO.retrievePICurrentVersion(luPI).getM_HU_PI_Version_ID());
			InterfaceWrapperHelper.save(lu);
			final I_M_HU_Item luHUItem = handlingUnitsDAO.createHUItem(lu, luItem);

			final I_M_HU cuOnPallet = handlingUnitsBL.getById(helper.createVHU(productId, "5"));
			cuOnPallet.setM_HU_Item_Parent_ID(luHUItem.getM_HU_Item_ID());
			InterfaceWrapperHelper.save(cuOnPallet);

			assertThat(helper.huService.isCUInTU(cuOnPallet)).isFalse();
		}
	}

	/**
	 * New modes with a pallet (LU) target AND a new box (TU) target: the booking works as without a pallet. The box holds the booked CU,
	 * the box sits on the pallet, the picked HU is the CU (with its PackId in Scan each CU), and the un-pick of that CU removes only that CU.
	 */
	@Nested
	class NewModeAndPallet
	{
		private PickingJob jobWithPalletAndNewBox()
		{
			final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-NM", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			huTestHelper.createHU_PI_Item_IncludedHU(luPI, InterfaceWrapperHelper.load(tuPIId.getRepoId(), I_M_HU_PI.class), new BigDecimal("10"));
			PickingJob job = createJob(tuPiipId, true);
			job = helper.pickingJobService.setLUPickingTarget(job, targetScope(job), LUPickingTarget.ofPackingInstructions(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()), "LU-NM"));
			return openBoxFromLinePacking(job);
		}

		private HuId boxOf(final HuId cuId)
		{
			final I_M_HU box = handlingUnitsBL.getTransportUnitHU(handlingUnitsBL.getById(cuId));
			assertThat(box).as("the CU sits in a box").isNotNull();
			assertThat(handlingUnitsBL.isTransportUnit(box)).as("the CU's parent is a box (TU)").isTrue();
			return HuId.ofRepoId(box.getM_HU_ID());
		}

		private HuId palletOf(final HuId huId)
		{
			final I_M_HU pallet = handlingUnitsBL.getLoadingUnitHU(handlingUnitsBL.getById(huId));
			assertThat(pallet).as("on a pallet").isNotNull();
			return HuId.ofRepoId(pallet.getM_HU_ID());
		}

		/** Scan each CU, pallet + new box, first scan: one CU of 1 with its PackId is booked, it is the picked HU and its box sits on the pallet. */
		@Test
		void scanEachCU_palletAndNewBox_firstScan_oneCUOfOneBooked()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-EACH");
			PickingJob job = jobWithPalletAndNewBox();

			job = pickEach(job, source, LOT_A, "1");

			final HuId pickedHuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final I_M_HU pickedHU = handlingUnitsBL.getById(pickedHuId);
			assertThat(handlingUnitsBL.isVirtual(pickedHU)).as("the picked HU is the booked CU").isTrue();
			assertThat(qtyOf(pickedHU)).isEqualByComparingTo("1");
			assertThat(packIdOf(pickedHU)).isEqualTo("1");
			final HuId boxId = boxOf(pickedHuId);
			assertThat(cusOf(boxId)).as("the box holds only the booked CU").hasSize(1);
			assertThat(palletOf(boxId)).as("box on the pallet").isNotNull();
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("1");
		}

		/** Scan once, pallet + new box, first booking of 3: the picked HU is the CU of 3, not the whole box, and its box sits on the pallet. */
		@Test
		void scanOnce_palletAndNewBox_firstBooking_pickedHUIsTheCU()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-ONCE");
			PickingJob job = jobWithPalletAndNewBox();

			job = pick(job, source, "3", label(PRODUCT_NO, LOT_A), false, null);

			final HuId pickedHuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final I_M_HU pickedHU = handlingUnitsBL.getById(pickedHuId);
			assertThat(handlingUnitsBL.isVirtual(pickedHU)).as("the picked HU is the booked CU, not the box").isTrue();
			assertThat(qtyOf(pickedHU)).isEqualByComparingTo("3");
			final HuId boxId = boxOf(pickedHuId);
			assertThat(cusOf(boxId)).as("the box holds only the booked CU").hasSize(1);
			assertThat(palletOf(boxId)).as("box on the pallet").isNotNull();
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("3");
		}

		/** Scan once, pallet + new box, first booking fills the box (4 into a box of 4): as without a pallet, the box holds ONE CU of 4, sits on the pallet, the CU is the picked HU; undo removes it. */
		@Test
		void scanOnce_palletAndNewBox_firstBookingFillsTheBox_oneCUOf4_undo()
		{
			assertPalletAndNewBox_firstBookingOfQty_oneCU_undo("4", "QR-LU-ONCE-FULL");
		}

		/** Scan once, pallet + new box, first booking above the box capacity (5 into a box of 4): as without a pallet, ONE CU of 5 in one box on the pallet; undo removes it. */
		@Test
		void scanOnce_palletAndNewBox_firstBookingAboveBoxCapacity_oneCUOf5_undo()
		{
			assertPalletAndNewBox_firstBookingOfQty_oneCU_undo("5", "QR-LU-ONCE-OVER");
		}

		private void assertPalletAndNewBox_firstBookingOfQty_oneCU_undo(final String qty, final String sourceQRCode)
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = helper.createVHUInfo(productId, "100", sourceQRCode);
			PickingJob job = jobWithPalletAndNewBox();

			job = pick(job, source, qty, label(PRODUCT_NO, LOT_A), false, null);

			final HuId cuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final I_M_HU cu = handlingUnitsBL.getById(cuId);
			assertThat(handlingUnitsBL.isVirtual(cu)).as("the picked HU is the booked CU, not the box").isTrue();
			assertThat(qtyOf(cu)).isEqualByComparingTo(qty);
			final HuId boxId = boxOf(cuId);
			assertThat(handlingUnitsBL.isAggregateHU(handlingUnitsBL.getById(boxId))).as("a real box, not an aggregated TU").isFalse();
			assertThat(cusOf(boxId)).as("the box holds only the booked CU").extracting(I_M_HU::getM_HU_ID).containsExactly(cuId.getRepoId());
			assertThat(palletOf(boxId)).as("the box sits on the pallet").isNotNull();
			assertThat(openBoxId(job)).as("the box is the open box").isEqualTo(boxId);
			assertThat(countQtyPickedRowsOfCU(cuId)).isEqualTo(1);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo(qty);

			job = undo(job, cuId, source.getQrCode(), null);

			assertThat(handlingUnitsBL.getById(cuId).getHUStatus()).as("the CU is Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
			assertThat(countQtyPickedRowsOfCU(cuId)).as("no QtyPicked row of the un-picked CU left").isEqualTo(0);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("nothing picked any more").isEmpty();
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("0");
			assertThat(cusOf(boxId)).as("the CU left the box").isEmpty();
		}

		/** Scan each CU, pallet + new box, packs 1 and 2 go into the same box on the pallet; undo of pack 1 removes only that CU. */
		@Test
		void scanEachCU_palletAndNewBox_twoPacks_undoFirst_removesOnlyThatCU()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-EACH-U");
			PickingJob job = jobWithPalletAndNewBox();
			job = pickEach(job, source, LOT_A, "1");
			job = pickEach(job, source, LOT_A, "2");

			final HuId boxId = openBoxId(job);
			final HuId cu1Id = HuId.ofRepoId(cuWithPackId(boxId, "1").getM_HU_ID());
			final HuId cu2Id = HuId.ofRepoId(cuWithPackId(boxId, "2").getM_HU_ID());
			assertThat(boxOf(cu1Id)).as("pack 1 in the box").isEqualTo(boxId);
			final HuId palletId = palletOf(boxId);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HUs are the CUs").containsExactlyInAnyOrder(cu1Id, cu2Id);
			assertThat(countQtyPickedRowsOfBox(boxId)).isEqualTo(2);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("2");

			job = undo(job, cu1Id, source.getQrCode(), null);

			assertThat(handlingUnitsBL.getById(cu1Id).getHUStatus()).as("CU of pack 1 Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
			assertThat(handlingUnitsBL.getById(cu2Id).getHUStatus()).as("CU of pack 2 stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(handlingUnitsBL.getById(boxId).getHUStatus()).as("box stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(handlingUnitsBL.getById(palletId).getHUStatus()).as("pallet stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(cusOf(boxId)).as("only the CU of pack 2 is left in the box").extracting(cu -> cu.getM_HU_ID()).containsExactly(cu2Id.getRepoId());
			assertThat(palletOf(boxId)).as("the box stays on the pallet").isEqualTo(palletId);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("only the CU of pack 2 is still picked").containsExactly(cu2Id);
			assertThat(countQtyPickedRowsOfCU(cu1Id)).as("no QtyPicked row of the un-picked CU left").isEqualTo(0);
			assertThat(countQtyPickedRowsOfCU(cu2Id)).as("the QtyPicked row of pack 2 stays").isEqualTo(1);
			assertThat(pickedQtyOfLine(job)).as("the line's picked quantity dropped by 1").isEqualByComparingTo("1");
		}

		/** Scan once, pallet + new box, bookings of 3 then 2 into the same box on the pallet; undo of the 3 removes only that CU. */
		@Test
		void scanOnce_palletAndNewBox_twoBookings_undoFirst_removesOnlyThatCU()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-ONCE-U");
			PickingJob job = jobWithPalletAndNewBox();
			job = pick(job, source, "3", label(PRODUCT_NO, LOT_A), false, null);
			final HuId cu3Id = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			job = pick(job, source, "2", label(PRODUCT_NO, LOT_A), false, null);

			final HuId boxId = openBoxId(job);
			assertThat(boxOf(cu3Id)).as("the CU of 3 in the box").isEqualTo(boxId);
			final HuId cu2Id = HuId.ofRepoId(cuWithQty(boxId, "2").getM_HU_ID());
			final HuId palletId = palletOf(boxId);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HUs are the CUs").containsExactlyInAnyOrder(cu3Id, cu2Id);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("5");

			job = undo(job, cu3Id, source.getQrCode(), null);

			assertThat(handlingUnitsBL.getById(cu3Id).getHUStatus()).as("the CU of 3 Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
			assertThat(handlingUnitsBL.getById(cu2Id).getHUStatus()).as("the CU of 2 stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(handlingUnitsBL.getById(boxId).getHUStatus()).as("box stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(cusOf(boxId)).as("only the CU of 2 is left in the box").extracting(cu -> cu.getM_HU_ID()).containsExactly(cu2Id.getRepoId());
			assertThat(palletOf(boxId)).as("the box stays on the pallet").isEqualTo(palletId);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("only the CU of 2 is still picked").containsExactly(cu2Id);
			assertThat(countQtyPickedRowsOfCU(cu3Id)).as("no QtyPicked row of the un-picked CU left").isEqualTo(0);
			assertThat(countQtyPickedRowsOfCU(cu2Id)).as("the QtyPicked row of the CU of 2 stays").isEqualTo(1);
			assertThat(pickedQtyOfLine(job)).as("the line's picked quantity dropped by 3").isEqualByComparingTo("2");
		}

		/** Scan each CU, the pallet already exists (made by pack 1) and a second new box is opened: pack 2 is the CU in the new box on the same pallet. */
		@Test
		void scanEachCU_existingPalletAndSecondNewBox_cuInNewBoxOnSamePallet()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-EACH-2");
			PickingJob job = jobWithPalletAndNewBox();
			job = pickEach(job, source, LOT_A, "1");
			final HuId cu1Id = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final HuId box1Id = boxOf(cu1Id);
			final HuId palletId = palletOf(box1Id);
			assertThat(job.getLuPickingTargetEffective(CollectionUtils.singleElement(job.getLines()).getId()).map(LUPickingTarget::isExistingLU))
					.as("the pallet made by pack 1 is now the pallet target").contains(true);

			job = openBoxFromLinePacking(job);
			job = pickEach(job, source, LOT_A, "2");

			final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
			assertThat(line.getPickedHUIds()).hasSize(2);
			final HuId cu2Id = line.getPickedHUIds().stream().filter(id -> !id.equals(cu1Id)).findFirst().orElseThrow(() -> new AssertionError("no 2nd picked HU"));
			final I_M_HU cu2 = handlingUnitsBL.getById(cu2Id);
			assertThat(handlingUnitsBL.isVirtual(cu2)).as("the picked HU is the booked CU").isTrue();
			assertThat(packIdOf(cu2)).isEqualTo("2");
			final HuId box2Id = boxOf(cu2Id);
			assertThat(box2Id).as("pack 2 is in a new box").isNotEqualTo(box1Id);
			assertThat(cusOf(box2Id)).as("the new box holds only pack 2").hasSize(1);
			assertThat(palletOf(box2Id)).as("the new box sits on the same pallet").isEqualTo(palletId);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("2");
		}
	}

	@Nested
	class ScanEachCU
	{
		/** Scan each CU, lots A, A, B (own pack IDs) into one open box: 3 CUs of 1, each with its own lot and PackId, the TU's lot empty. */
		@Test
		void scanEachCU_lotsAAB_threeCUsOfOne_ownLotAndPackId_tuLotEmpty()
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
			assertThat(cus).extracting(PickingJobCUQtyEntryPickTest.this::lotOf, PickingJobCUQtyEntryPickTest.this::packIdOf)
					.containsExactlyInAnyOrder(
							tuple(LOT_A, "1"),
							tuple(LOT_A, "2"),
							tuple(LOT_B, "3"));
			assertThat(lotOf(handlingUnitsBL.getById(boxId))).as("TU lot empty (mixed lots)").isNull();
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).hasSize(3);
		}

		/** the server books qty 1 per label, whatever quantity the client sent. */
		@Test
		void scanEachCU_clientSendsQty3_oneCUOfOneBooked()
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

		/** the same (product, lot, pack ID) again in the job: PickingCULabelService.MSG_CU_LABEL_ALREADY_PICKED, nothing booked. */
		@Test
		void repeatedLotAndPackId_refused_nothingBooked()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-O");
			PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));
			job = pickEach(job, source, LOT_A, "1");
			final PickingJob jobBefore = job;

			assertFails(() -> pickEach(jobBefore, source, LOT_A, "1"), PickingCULabelService.MSG_CU_LABEL_ALREADY_PICKED);

			assertThat(cusOf(openBoxId(job))).as("still one CU").hasSize(1);
			assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("99");
		}

		/** the same pack ID on another lot is a different pack: booked. */
		@Test
		void samePackIdOnAnotherLot_booked()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-P");
			PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

			job = pickEach(job, source, LOT_A, "1");
			job = pickEach(job, source, LOT_B, "1");

			final List<I_M_HU> cus = cusOf(openBoxId(job));
			assertThat(cus).hasSize(2);
			assertThat(cus).extracting(PickingJobCUQtyEntryPickTest.this::lotOf, PickingJobCUQtyEntryPickTest.this::packIdOf)
					.containsExactlyInAnyOrder(tuple(LOT_A, "1"), tuple(LOT_B, "1"));
		}

		/** the same label in another job is booked: the lookup reads only the job's own picked HUs. */
		@Test
		void sameLabelInAnotherJob_booked()
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

		/** the box's first pack: its picked HU is the CU; a rescan is refused; undo leaves only that CU Active (box and 2nd CU stay Picked, their QtyPicked rows stay, the line's picked qty drops by 1); the rescan books. */
		@Test
		void firstPackOfTheBox_duplicate_undo_rescan()
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
			assertThat(pickedQtyOfLine(job)).as("2 packs picked").isEqualByComparingTo("2");
			assertThat(pickedHUOfStepWithCU(job, cu1Id).getQrCode()).as("the picked CU carries its own QR code, not the box's").isNotEqualTo(helper.getQRCode(boxId));
			final PickingJob jobBeforeRescan = job;
			assertFails(() -> pickEach(jobBeforeRescan, source, LOT_A, "1"), PickingCULabelService.MSG_CU_LABEL_ALREADY_PICKED);
			assertThat(cusOf(boxId)).as("still 2 CU HUs").hasSize(2);
			assertThat(countQtyPickedRowsOfBox(boxId)).isEqualTo(2);

			job = undo(job, cu1Id, source.getQrCode(), null);

			assertThat(handlingUnitsBL.getById(cu1Id).getHUStatus()).as("CU of pack 1 Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
			assertThat(handlingUnitsBL.getById(boxId).getHUStatus()).as("box stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(handlingUnitsBL.getById(HuId.ofRepoId(cu2.getM_HU_ID())).getHUStatus()).as("CU of pack 2 stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("only the CU of pack 2 is still picked").containsExactly(HuId.ofRepoId(cu2.getM_HU_ID()));
			assertThat(countQtyPickedRowsOfBox(boxId)).as("QtyPicked row of the 2nd CU stays").isEqualTo(1);
			assertThat(pickedQtyOfLine(job)).as("the line's picked quantity dropped by 1").isEqualByComparingTo("1");

			job = pickEach(job, source, LOT_A, "1");

			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("rescan booked").hasSize(2);
			assertThat(pickedQtyOfLine(job)).as("rescan: 2 packs picked again").isEqualByComparingTo("2");
			final I_M_HU rescanned = cuWithPackId(boxId, "1");
			assertThat(lotOf(rescanned)).as("rescanned CU keeps its lot").isEqualTo(LOT_A);
			assertThat(countQtyPickedRowsOfBox(boxId)).as("QtyPicked rows of both CUs").isEqualTo(2);
		}

		/** the label carries no pack ID: PickingCULabelService.MSG_CU_LABEL_NO_PACK_ID, nothing booked. */
		@Test
		void labelWithoutPackId_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-S");
			final PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));

			assertFails(() -> pickEach(job, source, LOT_A, null), PickingCULabelService.MSG_CU_LABEL_NO_PACK_ID);

			assertNothingBooked(job, source, "100");
		}
	}

	@Nested
	class Undo
	{
		/** undo of a CU in a box with a scanned target (the CU is moved into it): box and the 2nd CU stay Picked, the target takes the pack. */
		@Test
		void undoWithTargetScan_cuMovedIntoTarget()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-T");
			final HUInfo target = helper.createVHUInfo(productId, "10", "QR-T-TARGET");
			PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));
			job = pickEach(job, source, LOT_A, "1");
			job = pickEach(job, source, LOT_A, "2");
			final HuId boxId = openBoxId(job);
			final HuId cu1Id = HuId.ofRepoId(cuWithPackId(boxId, "1").getM_HU_ID());
			final HuId cu2Id = HuId.ofRepoId(cuWithPackId(boxId, "2").getM_HU_ID());

			job = undo(job, cu1Id, source.getQrCode(), target.getQrCode());

			assertThat(qtyOf(handlingUnitsBL.getById(target.getId()))).as("target took the pack").isEqualByComparingTo("11");
			assertThat(handlingUnitsBL.getById(boxId).getHUStatus()).as("box stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(handlingUnitsBL.getById(cu2Id).getHUStatus()).as("CU of pack 2 stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).containsExactly(cu2Id);
			assertThat(countQtyPickedRowsOfBox(boxId)).as("QtyPicked row of the 2nd CU stays").isEqualTo(1);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("1");
		}

		/**
		 * (u) undo of the box's ONLY CU: nothing stays picked and no QtyPicked row of the CU is left. The emptied box is gone (in production
		 * the HU framework destroys a TU whose last CU leaves it), so the line keeps an open box of the same box type, like an LU target
		 * that an un-pick destroyed; the next pack is booked into a box of that type.
		 */
		@Test
		void undoOfTheBoxsOnlyCU_lineKeepsAnOpenBoxOfTheSameType()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-U");
			PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));
			job = pickEach(job, source, LOT_A, "1");
			final HuId boxId = openBoxId(job);
			final HuId cu1Id = HuId.ofRepoId(cuWithPackId(boxId, "1").getM_HU_ID());

			job = undo(job, cu1Id, source.getQrCode(), null);

			assertThat(handlingUnitsBL.getById(cu1Id).getHUStatus()).as("the CU is Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("nothing picked").isEmpty();
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("0");
			assertThat(countQtyPickedRowsOfBox(boxId)).as("no QtyPicked row left in the box").isEqualTo(0);
			assertThat(countQtyPickedRowsOfCU(cu1Id)).as("no QtyPicked row of the CU left").isEqualTo(0);
			final TUPickingTarget target = openBoxTarget(job);
			assertThat(target.isNewTU()).as("the emptied box is not the target any more: an open box of the same type is").isTrue();
			assertThat(target.getTuPIIdNotNull()).isEqualTo(tuPIId);

			job = pickEach(job, source, LOT_A, "2");

			final HuId nextBoxId = openBoxId(job);
			assertThat(cusOf(nextBoxId)).as("the next pack went into a box of that type").hasSize(1);
			assertThat(handlingUnitsBL.getEffectivePackingInstructionsIdAndCaption(handlingUnitsBL.getById(nextBoxId)).getId()).isEqualTo(tuPIId);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("1");
		}

		/** undo of the ONLY CU of a box opened by a GRAI scan: the line's open box keeps the GRAI, the next pack's box carries it. */
		@Test
		void undoOfTheOnlyCUOfAGRAIBox_openBoxKeepsTheGRAI()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			mapGRAIToTheBoxType();
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-W");
			PickingJob job = createJob(tuPiipId, true);
			job = helper.pickingJobService.createTUFromGRAI(job, targetScope(job), ScannedCode.ofString(GRAI_CANONICAL));
			job = pickEach(job, source, LOT_A, "1");
			final HuId boxId = openBoxId(job);
			final HuId cu1Id = HuId.ofRepoId(cuWithPackId(boxId, "1").getM_HU_ID());

			job = undo(job, cu1Id, source.getQrCode(), null);

			final TUPickingTarget target = openBoxTarget(job);
			assertThat(target.isNewTU()).isTrue();
			assertThat(target.getGrai()).as("the open box keeps the scanned GRAI").isEqualTo(GRAI.parse(GRAI_CANONICAL));

			job = pickEach(job, source, LOT_A, "2");

			final HuId nextBoxId = openBoxId(job);
			final GRAISet grais = CollectionUtils.singleElement(ImmutableList.copyOf(helper.huService.getGraiSnapshotsOfBoxes(ImmutableSet.of(nextBoxId)))).getAllGrais();
			assertThat(grais.toSet()).as("the next pack's box carries the GRAI").containsExactly(GRAI.parse(GRAI_CANONICAL));
		}

		/**
		 * (v) The un-pick of a CU in a box removes the CU's own QtyPicked rows from what was booked, not from the current profile: the same
		 * picked CU un-picked after the profile was switched to Manual still leaves no QtyPicked row of that CU (else the line would count a
		 * pack that is not picked any more); the other CU's row stays.
		 */
		@Test
		void profileSwitchedToManualAfterBooking_unpickStillRemovesTheCUsRows()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-V");
			PickingJob job = openBoxFromLinePacking(createJob(tuPiipId, true));
			job = pickEach(job, source, LOT_A, "1");
			job = pickEach(job, source, LOT_A, "2");
			final HuId boxId = openBoxId(job);
			final HuId cu1Id = HuId.ofRepoId(cuWithPackId(boxId, "1").getM_HU_ID());
			assertThat(countQtyPickedRowsOfBox(boxId)).isEqualTo(2);

			setProfile(CUQtyEntryMode.MANUAL);
			job = undo(job, cu1Id, source.getQrCode(), null);

			assertThat(countQtyPickedRowsOfCU(cu1Id)).as("no QtyPicked row of the un-picked CU left").isEqualTo(0);
			assertThat(countQtyPickedRowsOfBox(boxId)).as("the other CU's row stays").isEqualTo(1);
			assertThat(handlingUnitsBL.getById(cu1Id).getHUStatus()).as("the CU is Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("only the CU of pack 2 is still picked").hasSize(1).doesNotContain(cu1Id);
			assertThat(pickedQtyOfLine(job)).as("the line's picked quantity dropped by 1").isEqualByComparingTo("1");
		}
	}

	@Nested
	class CloseTargets
	{
		/**
		 * (x) A product job of two customers, each line in its own picking slot: when all targets are closed (on completion), the first
		 * customer's still open box is queued on its line's slot only, not also on the slot scanned last for the other customer (a box in two
		 * slot queues breaks the shipment: more than one slot found for the HU).
		 */
		@Test
		void closingAllTargets_queuesEachLinesBoxOnItsOwnLinesSlot()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceBox("QR-X");
			final BPartnerLocationId customerB = helper.createBPartnerAndLocationId("customerB");
			createPackageable("salesOrder-x-A", helper.shipToBPLocationId);
			createPackageable("salesOrder-x-B", customerB);
			PickingJob job = helper.pickingJobService.createPickingJob(
							PickingJobCreateRequest.builder()
									.aggregationType(PickingJobAggregationType.PRODUCT)
									.pickerId(UserId.ofRepoId(1234))
									.isAllowPickingAnyHU(true)
									.build())
					.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "SLOT-A"));
			assertThat(job.getLines()).as("one line per customer").hasSize(2);
			final PickingJobLine lineA = job.getLines().stream()
					.filter(line -> line.getCustomerId().equals(helper.shipToBPLocationId.getBpartnerId()))
					.findFirst()
					.orElseThrow(() -> new AssertionError("no line of customer A"));
			job = helper.pickingJobService.setTUPickingTarget(job, lineA.getId(), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
			job = pickOnLine(job, lineA.getId(), source, "3", label(PRODUCT_NO, LOT_A));
			final HuId boxA = job.getTuPickingTargetEffective(lineA.getId()).orElseThrow(() -> new AssertionError("no box")).getTuIdNotNull();
			final PickingSlotId slotB = createPickingSlot("SLOT-B");
			job = job.withPickingSlot(PickingSlotIdAndCaption.of(slotB, "SLOT-B")); // the picker scans customer B's slot

			helper.pickingJobService.closeLUAndTUPickingTargets(job);

			assertThat(queuedSlotsOf(boxA)).as("customer A's box is queued on customer A's slot only").containsExactly(helper.pickingSlotId);
		}

		/**
		 * (x2) Manual: the same two-customer product job queues the box on the slot scanned last, as today (the per-line slot is only for the
		 * new quantity entry modes).
		 */
		@Test
		void manual_closingAllTargets_queuesTheBoxOnTheLastScannedSlot_asToday()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final HUInfo source = sourceBox("QR-X2");
			final BPartnerLocationId customerB = helper.createBPartnerAndLocationId("customerB");
			createPackageable("salesOrder-x2-A", helper.shipToBPLocationId);
			createPackageable("salesOrder-x2-B", customerB);
			PickingJob job = helper.pickingJobService.createPickingJob(
							PickingJobCreateRequest.builder()
									.aggregationType(PickingJobAggregationType.PRODUCT)
									.pickerId(UserId.ofRepoId(1234))
									.isAllowPickingAnyHU(true)
									.build())
					.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "SLOT-A"));
			final PickingJobLine lineA = job.getLines().stream()
					.filter(line -> line.getCustomerId().equals(helper.shipToBPLocationId.getBpartnerId()))
					.findFirst()
					.orElseThrow(() -> new AssertionError("no line of customer A"));
			job = helper.pickingJobService.setTUPickingTarget(job, lineA.getId(), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
			job = pickOnLine(job, lineA.getId(), source, "1", null);
			final HuId boxA = job.getTuPickingTargetEffective(lineA.getId()).orElseThrow(() -> new AssertionError("no box")).getTuIdNotNull();
			final PickingSlotId slotB = createPickingSlot("SLOT-B");
			job = job.withPickingSlot(PickingSlotIdAndCaption.of(slotB, "SLOT-B"));

			helper.pickingJobService.closeLUAndTUPickingTargets(job);

			assertThat(queuedSlotsOf(boxA)).as("Manual: the close queues the box on the slot scanned last, as before").contains(slotB);
		}

		/**
		 * (x3) Mixed job: customer A uses a new mode (own job options row), customer B is Manual (the profile default). Closing all targets
		 * queues A's box on A's line slot only and B's box on the slot scanned last, as today; B's target is closed.
		 */
		@Test
		void mixedJob_newModeLineOnItsOwnSlot_manualLineOnTheLastScannedSlot()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final BPartnerLocationId customerB = helper.createBPartnerAndLocationId("customerB");
			final BPartnerId customerAId = helper.shipToBPLocationId.getBpartnerId();
			helper.updateMobileProfile(profile -> {
				final PickingJobOptionsId optionsA = helper.configService.createPickingJobOptions("customer A new mode", profile.getDefaultPickingJobOptions());
				helper.configService.updatePickingJobOptionsCUQtyEntry(optionsA, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId());
				return profile.toBuilder()
						.customerConfigs(PickingCustomerConfigsCollection.ofCollection(ImmutableList.of(
								PickingCustomerConfig.builder().customerId(customerAId).pickingJobOptionsId(optionsA).build())))
						.build();
			});
			final HUInfo sourceA = sourceBox("QR-X3A");
			final HUInfo sourceB = sourceBox("QR-X3B");
			createPackageable("salesOrder-x3-A", helper.shipToBPLocationId);
			createPackageable("salesOrder-x3-B", customerB);
			PickingJob job = helper.pickingJobService.createPickingJob(
							PickingJobCreateRequest.builder()
									.aggregationType(PickingJobAggregationType.PRODUCT)
									.pickerId(UserId.ofRepoId(1234))
									.isAllowPickingAnyHU(true)
									.build())
					.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "SLOT-A"));
			assertThat(job.getLines()).as("one line per customer").hasSize(2);
			final PickingJobLine lineA = job.getLines().stream()
					.filter(line -> line.getCustomerId().equals(customerAId))
					.findFirst()
					.orElseThrow(() -> new AssertionError("no line of customer A"));
			final PickingJobLine lineB = job.getLines().stream()
					.filter(line -> !line.getCustomerId().equals(customerAId))
					.findFirst()
					.orElseThrow(() -> new AssertionError("no line of customer B"));
			job = helper.pickingJobService.setTUPickingTarget(job, lineA.getId(), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
			job = pickOnLine(job, lineA.getId(), sourceA, "3", label(PRODUCT_NO, LOT_A));
			final HuId boxA = job.getTuPickingTargetEffective(lineA.getId()).orElseThrow(() -> new AssertionError("no box of A")).getTuIdNotNull();
			final PickingSlotId slotB = createPickingSlot("SLOT-B");
			job = job.withPickingSlot(PickingSlotIdAndCaption.of(slotB, "SLOT-B"));
			job = helper.pickingJobService.setTUPickingTarget(job, lineB.getId(), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
			job = pickOnLine(job, lineB.getId(), sourceB, "1", null);
			final HuId boxB = job.getTuPickingTargetEffective(lineB.getId()).orElseThrow(() -> new AssertionError("no box of B")).getTuIdNotNull();
			final PickingSlotId slotC = createPickingSlot("SLOT-C");
			job = job.withPickingSlot(PickingSlotIdAndCaption.of(slotC, "SLOT-C")); // the picker scans this slot last

			final PickingJob jobClosed = helper.pickingJobService.closeLUAndTUPickingTargets(job);

			assertThat(queuedSlotsOf(boxA)).as("new-mode customer A: own line slot only").containsExactly(helper.pickingSlotId);
			assertThat(queuedSlotsOf(boxB)).as("Manual customer B: the slot scanned last, as before").contains(slotC);
			assertThat(jobClosed.getTuPickingTargetEffective(lineB.getId())).as("Manual line's target closed").isEmpty();
		}
	}
}
