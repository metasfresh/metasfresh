package de.metas.handlingunits.picking.job.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;
import de.metas.handlingunits.grai.HUGraiSnapshotsCollection;
import java.util.Set;
import com.google.common.collect.ImmutableList;
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
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.HUInfo;
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
import de.metas.handlingunits.picking.job.service.commands.pick.PickingJobPickCommand;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.i18n.AdMessageKey;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.product.ProductId;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The shared set-up and helpers of the CU quantity entry pick tests (the {@code PickingJobCUQtyEntryPick_*Test} classes): product, TU packing,
 * the CU label format, job and pick builders, and the HU / attribute / QR-code assertions.
 */
@ExtendWith(AdempiereTestWatcher.class)
abstract class CUQtyEntryPickFixture
{
	static final String GS = "\u001d";

	static final String PRODUCT_NO = "PRD0001";

	static final String LOT_A = "LOTAAAAAA";

	static final String LOT_B = "LOTBBBBBB";

	static final String GTIN = "04012345678901";

	static final String GRAI_CANONICAL = "7613204.00307.999999";

	PickingJobTestHelper helper;

	HUTestHelper huTestHelper;

	IHandlingUnitsBL handlingUnitsBL;

	IHandlingUnitsDAO handlingUnitsDAO;

	ScannableCodeFormatService formatService;

	ScannableCodeFormat format;

	ProductId productId;

	HuPackingInstructionsId tuPIId;

	HUPIItemProductId tuPiipId;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		huTestHelper = helper.getHuTestHelper();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
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
						constant(26, 27, "17"),
						part(28, 33, ScannableCodeFormatPartType.BestBeforeDate),
						constant(34, 35, "10"),
						part(36, 44, ScannableCodeFormatPartType.LotNo),
						constant(45, 46, "92"),
						part(47, ScannableCodeFormatPart.OPEN_END, ScannableCodeFormatPartType.PackId)))
				.build());
	}

	static ScannedCode eachLabel(final String lot, @Nullable final String packId)
	{
		return ScannedCode.ofString("01" + GTIN + "91" + PRODUCT_NO + "17" + "261107" + "10" + lot + "92" + (packId != null ? packId : ""));
	}

	static Part constant(final int start, final int end, final String value)
	{
		return Part.builder().startPosition(start).endPosition(end).type(ScannableCodeFormatPartType.Constant).constantValue(value).build();
	}

	static Part part(final int start, final int end, final ScannableCodeFormatPartType type)
	{
		return Part.builder().startPosition(start).endPosition(end).type(type).build();
	}

	static ScannedCode label(final String productNo, final String lot)
	{
		// the scanner string without separators (DataWedge without control characters)
		return ScannedCode.ofString("01" + GTIN + "91" + productNo + "17" + "261107" + "10" + lot + "92" + "1");
	}

	/** The same label as a scanner that sends the GS separator (char 29) after the product and after the lot */
	static ScannedCode labelWithGS(final String productNo, final String lot)
	{
		return ScannedCode.ofString("01" + GTIN + "91" + productNo + GS + "17" + "261107" + "10" + lot + GS + "92" + "1");
	}

	void setProfile(final CUQtyEntryMode mode)
	{
		helper.updateMobileProfile(profile -> profile.toBuilder()
				.defaultPickingJobOptions(profile.getDefaultPickingJobOptions().toBuilder()
						.cuQtyEntryMode(mode)
						.cuLabelFormatId(format.getId())
						.build())
				.build());
	}

	static List<String> logOfPickCommand(final Runnable action)
	{
		final Logger logger = (Logger)LoggerFactory.getLogger(PickingJobPickCommand.class);
		final ListAppender<ILoggingEvent> logAppender = new ListAppender<>();
		logAppender.start();
		logger.addAppender(logAppender);
		try
		{
			action.run();
		}
		finally
		{
			logger.detachAppender(logAppender);
		}
		return logAppender.list.stream().map(ILoggingEvent::getFormattedMessage).collect(java.util.stream.Collectors.toList());
	}

	/** Builds the picking job (sales-order aggregation, picking slot set) the tests start from. */
	JobBuilder newJob()
	{
		return new JobBuilder();
	}

	final class JobBuilder
	{
		private String orderDocNo = "salesOrderCU";
		@Nullable private HUPIItemProductId packingPiipId;
		private String qtyToDeliver = "100";

		JobBuilder orderDocNo(final String orderDocNo)
		{
			this.orderDocNo = orderDocNo;
			return this;
		}

		JobBuilder packedWith(@Nullable final HUPIItemProductId packingPiipId)
		{
			this.packingPiipId = packingPiipId;
			return this;
		}

		JobBuilder qtyToDeliver(final String qtyToDeliver)
		{
			this.qtyToDeliver = qtyToDeliver;
			return this;
		}

		PickingJob create()
		{
			final OrderAndLineId orderAndLineId = helper.createOrderAndLineId(orderDocNo);
			helper.packageable()
					.orderAndLineId(orderAndLineId)
					.productId(productId)
					.huPIItemProductId(packingPiipId)
					.qtyToDeliver(qtyToDeliver)
					.build();

			return helper.pickingJobService.createPickingJob(
							PickingJobCreateRequest.builder()
									.aggregationType(PickingJobAggregationType.SALES_ORDER)
									.pickerId(UserId.ofRepoId(1234))
									.salesOrderId(orderAndLineId.getOrderId())
									.deliveryBPLocationId(helper.shipToBPLocationId)
									.isAllowPickingAnyHU(true)
									.build())
					.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
		}
	}

	PickingJob openTUFromLinePacking(final PickingJob job)
	{
		return helper.pickingJobService.setTUPickingTarget(job, targetScope(job), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
	}

	@Nullable
	static PickingJobLineId targetScope(final PickingJob job)
	{
		return job.isLineLevelPickTarget() ? CollectionUtils.singleElement(job.getLines()).getId() : null;
	}

	PickingJob pick(
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

	HUInfo sourceTU(final String qrCodeId)
	{
		final HuId tuId = helper.createHU(tuPIId, productId, helper.qty("4", productId));
		final HuId secondPiece = helper.createVHU(productId, "4");
		HUTransformService.newInstance(huTestHelper.createMutableHUContextOutOfTransaction())
				.addCUsToTU(ImmutableList.of(handlingUnitsBL.getById(secondPiece)), handlingUnitsBL.getById(tuId));
		return HUInfo.builder().id(tuId).qrCode(helper.createQRCode(tuId, qrCodeId)).build();
	}

	HuId openTUId(final PickingJob job)
	{
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		final TUPickingTarget target = job.getTuPickingTargetEffective(line.getId()).orElseThrow(() -> new AssertionError("no TU target"));
		assertThat(target.isNewTU()).as("the TU was materialised").isFalse();
		return target.getTuIdNotNull();
	}

	List<I_M_HU> cusOf(final HuId tuId)
	{
		return handlingUnitsBL.retrieveIncludedHUs(handlingUnitsBL.getById(tuId));
	}

	BigDecimal qtyOf(final I_M_HU hu)
	{
		final IHUStorage storage = huTestHelper.createMutableHUContextOutOfTransaction().getHUStorageFactory().getStorage(hu);
		return storage.getQuantity(productId, helper.uomEach).toBigDecimal();
	}

	IAttributeStorage attributesOf(final I_M_HU hu)
	{
		final IHUContext huContext = huTestHelper.createMutableHUContextOutOfTransaction();
		return huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
	}

	@Nullable
	String lotOf(final I_M_HU hu)
	{
		final IAttributeStorage storage = attributesOf(hu);
		assertThat(storage.hasAttribute(AttributeConstants.ATTR_LotNumber)).as("HU carries the lot attribute").isTrue();
		final String lot = storage.getValueAsString(AttributeConstants.ATTR_LotNumber);
		return lot == null || lot.trim().isEmpty() ? null : lot;
	}

	static void assertFails(final Runnable runnable, final AdMessageKey expected)
	{
		assertThatThrownBy(runnable::run)
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(expected.toAD_Message()));
	}

	I_M_HU cuWithQty(final HuId tuId, final String qty)
	{
		return cusOf(tuId).stream()
				.filter(cu -> qtyOf(cu).compareTo(new BigDecimal(qty)) == 0)
				.findFirst()
				.orElseThrow(() -> new AssertionError("no CU of " + qty + " in TU " + tuId));
	}

	void assertLabelRequiredLineCountsCUs(final PickingJob job)
	{
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		assertThat(line.getPickingUnit().isCU()).as("label-required mode: picking unit is CU").isTrue();
		assertThat(line.getQtyToPick().toBigDecimal()).as("qty to pick in CUs").isEqualByComparingTo("100");
		assertThat(line.getQtyRemainingToPick().toBigDecimal()).as("qty remaining in CUs").isEqualByComparingTo("100");
	}

	@Nullable
	String packIdOf(final I_M_HU hu)
	{
		final IAttributeStorage storage = attributesOf(hu);
		assertThat(storage.hasAttribute(AttributeConstants.PackId)).as("HU carries the PackId attribute").isTrue();
		final String packId = storage.getValueAsString(AttributeConstants.PackId);
		return packId == null || packId.trim().isEmpty() ? null : packId;
	}

	I_M_HU cuWithPackId(final HuId tuId, final String packId)
	{
		return cusOf(tuId).stream()
				.filter(cu -> packId.equals(packIdOf(cu)))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no CU with pack ID " + packId + " in TU " + tuId));
	}

	long countQtyPickedRowsOfTU(final HuId tuId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_TU_HU_ID, tuId)
				.addOnlyActiveRecordsFilter()
				.create()
				.count();
	}

	PickingJob pickEach(final PickingJob job, final HUInfo source, final String lot, @Nullable final String packId)
	{
		return pick(job, source, "1", eachLabel(lot, packId), false, null);
	}

	void assertNothingBooked(final PickingJob job, final HUInfo source, final String sourceQty)
	{
		assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).as("nothing booked").isEmpty();
		assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).as("source untouched").isEqualByComparingTo(sourceQty);
	}

	OrderAndLineId createPackageable(final String orderDocNo, final BPartnerLocationId shipToBPLocationId)
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

	PickingJob createTwoCustomerProductJob(final String testId, final BPartnerLocationId customerB)
	{
		createPackageable("salesOrder-" + testId + "-A", helper.shipToBPLocationId);
		createPackageable("salesOrder-" + testId + "-B", customerB);
		final PickingJob job = helper.pickingJobService.createPickingJob(
						PickingJobCreateRequest.builder()
								.aggregationType(PickingJobAggregationType.PRODUCT)
								.pickerId(UserId.ofRepoId(1234))
								.isAllowPickingAnyHU(true)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "SLOT-A"));
		assertThat(job.getLines()).as("one line per customer").hasSize(2);
		return job;
	}

	BPartnerId customerAId()
	{
		return helper.shipToBPLocationId.getBpartnerId();
	}

	PickingJobLine lineOfCustomerA(final PickingJob job)
	{
		return job.getLines().stream()
				.filter(line -> line.getCustomerId().equals(customerAId()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no line of customer A"));
	}

	PickingJobLine lineOfCustomerB(final PickingJob job)
	{
		return job.getLines().stream()
				.filter(line -> !line.getCustomerId().equals(customerAId()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no line of customer B"));
	}

	PickingJob pickOnLine(final PickingJob job, final PickingJobLineId lineId, final HUInfo source, final String qty, final ScannedCode cuLabel)
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

	static PickingSlotId createPickingSlot(final String name)
	{
		final I_M_PickingSlot pickingSlot = InterfaceWrapperHelper.newInstance(I_M_PickingSlot.class);
		pickingSlot.setPickingSlot(name);
		InterfaceWrapperHelper.save(pickingSlot);
		return PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID());
	}

	static List<PickingSlotId> queuedSlotsOf(final HuId huId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_PickingSlot_HU.class)
				.addEqualsFilter(I_M_PickingSlot_HU.COLUMNNAME_M_HU_ID, huId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(row -> PickingSlotId.ofRepoId(row.getM_PickingSlot_ID()))
				.collect(ImmutableList.toImmutableList());
	}

	void mapGRAIToTheTUType()
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

	static TUPickingTarget openTUTarget(final PickingJob job)
	{
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		return job.getTuPickingTargetEffective(line.getId()).orElseThrow(() -> new AssertionError("no TU target"));
	}

	static long countAllQtyPickedRows()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.count();
	}

	static long countQtyPickedRowsOfCU(final HuId cuId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID, cuId)
				.addOnlyActiveRecordsFilter()
				.create()
				.count();
	}

	PickingJob undo(final PickingJob job, final HuId cuId, final HUQRCode sourceQr, @Nullable final HUQRCode unpickToTargetQr)
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

	static HUInfo pickedHUOfStepWithCU(final PickingJob job, final HuId cuId)
	{
		return CollectionUtils.singleElement(job.getLines()).streamSteps()
				.flatMap(step -> step.getPickFrom(PickingJobStepPickFromKey.MAIN).getPickedTo().getActualPickedHUs().stream())
				.map(PickingJobStepPickedToHU::getActualPickedHU)
				.filter(hu -> hu.getId().equals(cuId))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no picked HU " + cuId));
	}

	static BigDecimal pickedQtyOfLine(final PickingJob job)
	{
		return CollectionUtils.singleElement(job.getLines()).getQtyPicked().toBigDecimal();
	}

	void setCustomerGraiRequired(final GRAIRequired graiRequired)
	{
		setCustomerGraiRequired(helper.shipToBPLocationId.getBpartnerId(), graiRequired);
	}

	static void setCustomerGraiRequired(final BPartnerId customerId, final GRAIRequired graiRequired)
	{
		final I_C_BPartner customer = InterfaceWrapperHelper.load(customerId, I_C_BPartner.class);
		customer.setGRAIRequired(graiRequired.getCode());
		InterfaceWrapperHelper.save(customer);
	}

	HUGraiSnapshotsCollection graiSnapshotsOfTUs(final Set<HuId> huIds)
	{
		return helper.huService.getGraiSnapshotsReplacingCUsBookedWithLabelByTheirTU(huIds, helper.huService.filterCUsInTU(huIds));
	}
}
