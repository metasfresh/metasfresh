package de.metas.handlingunits.picking.job.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;
import de.metas.handlingunits.grai.HUGraiSnapshotsCollection;
import java.util.Set;
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
import de.metas.handlingunits.picking.job.service.commands.grai.PickingJobGRAIValidator;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(AdempiereTestWatcher.class)
class PickingJobCUQtyEntryPickTest
{
	private static final String GS = "\u001d";
	private static final String PRODUCT_NO = "PRD0001";
	private static final String LOT_A = "LOTAAAAAA";
	private static final String LOT_B = "LOTBBBBBB";
	private static final String GTIN = "04012345678901";
	private static final String GRAI_CANONICAL = "7613204.00307.999999";

	private static final AdMessageKey CU_NO_OPEN_TU = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_NO_OPEN_TU");
	private static final AdMessageKey CU_EACH_ONLY_FOR_PIECES = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_EACH_ONLY_FOR_PIECES");
	private static final AdMessageKey PICK_OVERDELIVERY_BLOCKED = AdMessageKey.of("de.metas.handlingunits.picking.job.PICK_OVERDELIVERY_BLOCKED_ERROR_MSG");

	private PickingJobTestHelper helper;
	private HUTestHelper huTestHelper;
	private IHandlingUnitsBL handlingUnitsBL;
	private IHandlingUnitsDAO handlingUnitsDAO;
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

	private static ScannedCode eachLabel(final String lot, @Nullable final String packId)
	{
		return ScannedCode.ofString("01" + GTIN + "91" + PRODUCT_NO + "17" + "261107" + "10" + lot + "92" + (packId != null ? packId : ""));
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
		// the scanner string without separators (DataWedge without control characters)
		return ScannedCode.ofString("01" + GTIN + "91" + productNo + "17" + "261107" + "10" + lot + "92" + "1");
	}

	/** The same label as a scanner that sends the GS separator (char 29) after the product and after the lot */
	private static ScannedCode labelWithGS(final String productNo, final String lot)
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

	private static List<String> logOfPickCommand(final Runnable action)
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

	private PickingJob createJob(@Nullable final HUPIItemProductId packingPiipId, final boolean isAllowAnyHU)
	{
		return createJob("salesOrderCU", packingPiipId, isAllowAnyHU);
	}

	private PickingJob createJob(final String orderDocNo, @Nullable final HUPIItemProductId packingPiipId, final boolean isAllowAnyHU)
	{
		return createJob(orderDocNo, packingPiipId, isAllowAnyHU, "100");
	}

	private PickingJob createJob(final String orderDocNo, @Nullable final HUPIItemProductId packingPiipId, final boolean isAllowAnyHU, final String qtyToDeliver)
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
								.isAllowPickingAnyHU(isAllowAnyHU)
								.build())
				.withPickingSlot(PickingSlotIdAndCaption.of(helper.pickingSlotId, "TEST"));
	}

	private PickingJob openTUFromLinePacking(final PickingJob job)
	{
		return helper.pickingJobService.setTUPickingTarget(job, targetScope(job), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
	}

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

	private HUInfo sourceTU(final String qrCodeId)
	{
		final HuId tuId = helper.createHU(tuPIId, productId, helper.qty("4", productId));
		final HuId secondPiece = helper.createVHU(productId, "4");
		HUTransformService.newInstance(huTestHelper.createMutableHUContextOutOfTransaction())
				.addCUsToTU(ImmutableList.of(handlingUnitsBL.getById(secondPiece)), handlingUnitsBL.getById(tuId));
		return HUInfo.builder().id(tuId).qrCode(helper.createQRCode(tuId, qrCodeId)).build();
	}

	private HuId openTUId(final PickingJob job)
	{
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		final TUPickingTarget target = job.getTuPickingTargetEffective(line.getId()).orElseThrow(() -> new AssertionError("no TU target"));
		assertThat(target.isNewTU()).as("the TU was materialised").isFalse();
		return target.getTuIdNotNull();
	}

	private List<I_M_HU> cusOf(final HuId tuId)
	{
		return handlingUnitsBL.retrieveIncludedHUs(handlingUnitsBL.getById(tuId));
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

	private I_M_HU cuWithQty(final HuId tuId, final String qty)
	{
		return cusOf(tuId).stream()
				.filter(cu -> qtyOf(cu).compareTo(new BigDecimal(qty)) == 0)
				.findFirst()
				.orElseThrow(() -> new AssertionError("no CU of " + qty + " in TU " + tuId));
	}

	private void assertLabelBasedLineCountsCUs(final PickingJob job)
	{
		final PickingJobLine line = CollectionUtils.singleElement(job.getLines());
		assertThat(line.getPickingUnit().isCU()).as("label-based mode: picking unit is CU").isTrue();
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

	private I_M_HU cuWithPackId(final HuId tuId, final String packId)
	{
		return cusOf(tuId).stream()
				.filter(cu -> packId.equals(packIdOf(cu)))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no CU with pack ID " + packId + " in TU " + tuId));
	}

	private long countQtyPickedRowsOfTU(final HuId tuId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_TU_HU_ID, tuId)
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

	private PickingJob createTwoCustomerProductJob(final String testId, final BPartnerLocationId customerB)
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

	private BPartnerId customerAId()
	{
		return helper.shipToBPLocationId.getBpartnerId();
	}

	private PickingJobLine lineOfCustomerA(final PickingJob job)
	{
		return job.getLines().stream()
				.filter(line -> line.getCustomerId().equals(customerAId()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no line of customer A"));
	}

	private PickingJobLine lineOfCustomerB(final PickingJob job)
	{
		return job.getLines().stream()
				.filter(line -> !line.getCustomerId().equals(customerAId()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no line of customer B"));
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

	private void mapGRAIToTheTUType()
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

	private static TUPickingTarget openTUTarget(final PickingJob job)
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
		setCustomerGraiRequired(helper.shipToBPLocationId.getBpartnerId(), graiRequired);
	}

	private static void setCustomerGraiRequired(final BPartnerId customerId, final GRAIRequired graiRequired)
	{
		final I_C_BPartner customer = InterfaceWrapperHelper.load(customerId, I_C_BPartner.class);
		customer.setGRAIRequired(graiRequired.getCode());
		InterfaceWrapperHelper.save(customer);
	}

	@Nested
	class Completion
	{
		@Test
		void labelBasedMode_cuPickedInTUWithoutGrai_graiRequiredYes_isRefused()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			setCustomerGraiRequired(GRAIRequired.Yes);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-GRAI-N");
			final PickingJob job = pickEach(openTUFromLinePacking(createJob(tuPiipId, true)), source, LOT_A, "1");
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HU is the CU, not the TU").doesNotContain(openTUId(job));

			assertThatThrownBy(() -> helper.pickingJobService.complete(job))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(GRAI_COUNT_MISMATCH.toAD_Message());
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
					.hasMessageContaining(GRAI_COUNT_MISMATCH.toAD_Message());

			@SuppressWarnings("unchecked") final ArgumentCaptor<Collection<HUInfo>> lookedUp = ArgumentCaptor.forClass(Collection.class);
			Mockito.verify(huServiceSpy).filterBookedWithLabelScan(lookedUp.capture());
			assertThat(lookedUp.getValue()).extracting(HUInfo::getId).as("only the CUs of the GRAI customer are looked up").containsExactlyElementsOf(cusOfA);
		}

		@Test
		void labelBasedModePick_thenModeSwitchedToManual_cuPickedInTUWithoutGrai_isStillRefused()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			setCustomerGraiRequired(GRAIRequired.Yes);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-GRAI-S");
			final PickingJob job = pickEach(openTUFromLinePacking(createJob(tuPiipId, true)), source, LOT_A, "1");
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HU is the CU, not the TU").doesNotContain(openTUId(job));

			setProfile(CUQtyEntryMode.MANUAL);

			assertThatThrownBy(() -> helper.pickingJobService.complete(job))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(GRAI_COUNT_MISMATCH.toAD_Message());
		}

		@Test
		void manualPick_thenModeSwitchedToLabelBasedMode_tuWithoutGrai_isRefused()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			setCustomerGraiRequired(GRAIRequired.Yes);
			final HUInfo source = sourceTU("QR-GRAI-R");
			final PickingJob job = pick(openTUFromLinePacking(createJob(null, true)), source, "5", null, false, null);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("Manual: the picked HU is the TU").containsExactly(openTUId(job));

			setProfile(CUQtyEntryMode.SCAN_EACH_CU);

			assertThatThrownBy(() -> helper.pickingJobService.complete(job))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(GRAI_COUNT_MISMATCH.toAD_Message());
		}

		@Test
		void cuInTU_notBookedWithOwnQRCode_isCheckedAsItIs_bookedWithOwnQRCode_viaItsTU()
		{
			final HuId tuId = helper.createHU(tuPIId, productId, helper.qty("4", productId));
			final HuId cuId = HuId.ofRepoId(cusOf(tuId).get(0).getM_HU_ID());
			assertThat(helper.huService.isCUInTU(handlingUnitsBL.getById(cuId))).as("the CU sits in the TU").isTrue();

			assertThat(helper.huService.getGraiSnapshotsReplacingLabelBookedCUsByTheirTU(ImmutableSet.of(cuId), ImmutableSet.of()))
					.as("Manual-shaped CU: checked as it is, the CU has no GRAI snapshot").isEmpty();
			assertThat(helper.huService.getGraiSnapshotsReplacingLabelBookedCUsByTheirTU(ImmutableSet.of(cuId), ImmutableSet.of(cuId)))
					.as("CU booked with its own QR code: its TU is checked").extracting(snapshot -> snapshot.getHuId()).containsExactly(tuId);
		}

		@Test
		void pickedHUWithQRCodeSharedBySeveralHUs_graiNotRequired_completes()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			setCustomerGraiRequired(GRAIRequired.No);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-SHARED-N");
			final PickingJob job = pickEach(openTUFromLinePacking(createJob(tuPiipId, true)), source, LOT_A, "1");
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
			final PickingJob job = pickEach(openTUFromLinePacking(createJob(tuPiipId, true)), source, LOT_A, "1");
			final HuId otherHuSharingTheQRCode = helper.createVHU(productId, "4");
			final HUInfo pickedCU = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUInfos());
			helper.huService.getHuQRCodesService().assign(pickedCU.getQrCode(), ImmutableSet.of(pickedCU.getId(), otherHuSharingTheQRCode));

			assertThatThrownBy(() -> helper.pickingJobService.complete(job))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(GRAI_COUNT_MISMATCH.toAD_Message());
		}

		@Test
		void manualMode_tuWithoutGrai_graiRequiredYes_isRefused()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			setCustomerGraiRequired(GRAIRequired.Yes);
			final HUInfo source = sourceTU("QR-GRAI-M");
			final PickingJob job = pick(openTUFromLinePacking(createJob(null, true)), source, "5", null, false, null);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("Manual: the picked HU is the TU").containsExactly(openTUId(job));

			assertThatThrownBy(() -> helper.pickingJobService.complete(job))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(GRAI_COUNT_MISMATCH.toAD_Message());
		}
	}

	@Nested
	class ScanOnce
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
			PickingJob job = openTUFromLinePacking(createJob("salesOrderCUGS", tuPiipId, true, "2"));

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
			PickingJob job = openTUFromLinePacking(createJob("salesOrderCU", tuPiipId, true, "2"));
			job = pick(job, source, "2", label(PRODUCT_NO, LOT_A), false, null);
			assertThat(pickedQtyOfLine(job)).as("line done: 2 of 2").isEqualByComparingTo("2");
			final PickingJob jobWhenDone = job;

			final List<String> log = logOfPickCommand(() -> assertThatThrownBy(() -> pick(jobWhenDone, source, "1", label(PRODUCT_NO, LOT_A), false, null))
					.isInstanceOf(AdempiereException.class)
					.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(PICK_OVERDELIVERY_BLOCKED.toAD_Message()))
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
			PickingJob job = createJob(tuPiipId, true);
			assertLabelBasedLineCountsCUs(job);
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
			PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));
			job = pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null);

			job = pick(job, source, "3", label(PRODUCT_NO, LOT_A), false, null);

			final HuId tuId = openTUId(job);
			assertThat(cusOf(tuId)).extracting(PickingJobCUQtyEntryPickTest.this::qtyOf).usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
					.containsExactlyInAnyOrder(new BigDecimal("5"), new BigDecimal("3"));
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).hasSize(2);
		}

		@Test
		void labelOfAnotherProduct_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceTU("QR-F");
			final PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));

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
			PickingJob job = createJob(tuPiipId, true);
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
			PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));

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
		void labelBasedMode_noCuLabel_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceTU("QR-J");
			final PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));

			assertThatThrownBy(() -> pick(job, source, "5", null, false, null))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("A CU label is required");

			assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
			assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("8");
		}

		@Test
		void lotsAThenB_intoOneTU_eachCUKeepsItsLot_tuLotEmpty()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceTU("QR-K");
			PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));

			job = pick(job, source, "2", label(PRODUCT_NO, LOT_A), false, null);
			job = pick(job, source, "3", label(PRODUCT_NO, LOT_B), false, null);

			final HuId tuId = openTUId(job);
			assertThat(lotOf(cuWithQty(tuId, "2"))).isEqualTo(LOT_A);
			assertThat(lotOf(cuWithQty(tuId, "3"))).isEqualTo(LOT_B);
			assertThat(lotOf(handlingUnitsBL.getById(tuId))).as("TU lot empty (mixed lots)").isNull();
		}
	}

	@Nested
	class OpenTURequired
	{
		@Test
		void unlimitedCapacityLineNamingATuPI_noTarget_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final I_M_HU_PI_Item_Product piip = InterfaceWrapperHelper.load(tuPiipId.getRepoId(), I_M_HU_PI_Item_Product.class);
			piip.setIsInfiniteCapacity(true);
			InterfaceWrapperHelper.save(piip);
			final HUInfo source = sourceTU("QR-C");
			final PickingJob job = createJob(tuPiipId, true);

			assertFails(() -> pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null), CU_NO_OPEN_TU);

			assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).as("nothing booked").isEmpty();
			assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).as("source untouched").isEqualByComparingTo("8");
		}

		@Test
		void virtualPackingLine_noTarget_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = sourceTU("QR-D");
			final PickingJob job = createJob(null, true);

			assertFails(() -> pick(job, source, "5", label(PRODUCT_NO, LOT_A), false, null), CU_NO_OPEN_TU);

			assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
			assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("8");
		}

		@Test
		void manual_noTarget_notRefused_fullTU()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-E");
			PickingJob job = createJob(tuPiipId, true);
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
			PickingJob job = createJob(null, true);

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
			PickingJob job = openTUFromLinePacking(createJob(null, true));

			job = pick(job, source, "5", null, false, null);

			final HuId pickedTUId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			assertThat(cusOf(pickedTUId)).as("one CU per source piece").extracting(PickingJobCUQtyEntryPickTest.this::qtyOf).usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
					.containsExactlyInAnyOrder(new BigDecimal("4"), new BigDecimal("1"));
		}

		@Test
		void scanEachCU_noTarget_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = sourceTU("QR-H");
			final PickingJob job = createJob(tuPiipId, true);

			assertFails(() -> pick(job, source, "1", label(PRODUCT_NO, LOT_A), false, null), CU_NO_OPEN_TU);

			assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).isEmpty();
			assertThat(qtyOf(handlingUnitsBL.getById(source.getId()))).isEqualByComparingTo("8");
		}
	}

	@Nested
	class ManualAndPallet
	{
		@Test
		void manual_lotsAThenB_intoOneTU_lotOverwritten()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-L");
			final HUInfo source2 = helper.createVHUInfo(productId, "100", "QR-L2");
			PickingJob job = createJob(null, true);
			job = openTUFromLinePacking(job);

			job = pick(job, source, "2", null, false, LOT_A);
			final HuId tuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			assertThat(handlingUnitsBL.isTransportUnitOrAggregate(handlingUnitsBL.getById(tuId))).as("the first booking's picked HU is the TU").isTrue();
			job = helper.pickingJobService.setTUPickingTarget(job, targetScope(job), TUPickingTarget.ofExistingHU(tuId, helper.huService.getQRCodeByHuId(tuId)));
			job = pick(job, source2, "3", null, false, LOT_B);

			assertThat(lotOf(handlingUnitsBL.getById(tuId))).as("TU lot overwritten by the second booking").isEqualTo(LOT_B);
			assertThat(cusOf(tuId)).extracting(PickingJobCUQtyEntryPickTest.this::lotOf).as("the second lot is written on the whole TU").containsOnly(LOT_B);
		}

		@Test
		void manual_palletAndTU_pickedHUIsTheTU_undoLeavesNothingPicked()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-Y");
			final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-Y", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			huTestHelper.createHU_PI_Item_IncludedHU(luPI, InterfaceWrapperHelper.load(tuPIId.getRepoId(), I_M_HU_PI.class), new BigDecimal("10"));
			PickingJob job = createJob(null, true);
			job = helper.pickingJobService.setLUPickingTarget(job, targetScope(job), LUPickingTarget.ofPackingInstructions(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()), "LU-Y"));
			job = openTUFromLinePacking(job);

			job = pick(job, source, "2", null, false, LOT_A);

			final HuId tuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final I_M_HU tu = handlingUnitsBL.getById(tuId);
			assertThat(handlingUnitsBL.isTransportUnit(tu)).as("Manual: the picked HU is the TU").isTrue();
			assertThat(helper.huService.isCUInTU(tu)).isFalse();
			assertThat(countQtyPickedRowsOfTU(tuId)).isEqualTo(1);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("2");

			job = undo(job, tuId, source.getQrCode(), null);

			assertThat(countQtyPickedRowsOfTU(tuId)).as("no QtyPicked row of the TU left").isEqualTo(0);
			assertThat(pickedQtyOfLine(job)).as("nothing picked").isEqualByComparingTo("0");
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).isEmpty();
		}

		/** Pins a known base defect: un-picking a CU picked directly onto an LU leaves its QtyPicked row. */
		@Test
		void manual_cuPickedOntoPallet_unpick_restoresAsBase()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LUCU");
			final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-CU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			huTestHelper.createHU_PI_Item_IncludedHU(luPI, huTestHelper.huDefVirtual, new BigDecimal("10"));
			PickingJob job = createJob(null, true);
			job = helper.pickingJobService.setLUPickingTarget(job, targetScope(job), LUPickingTarget.ofPackingInstructions(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()), "LU-CU"));

			job = pick(job, source, "2", null, false, LOT_A);

			final HuId pickedHuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final I_M_HU pickedHU = handlingUnitsBL.getById(pickedHuId);
			assertThat(handlingUnitsBL.isVirtual(pickedHU)).as("the picked HU is a CU").isTrue();
			assertThat(handlingUnitsBL.getLoadingUnitHU(pickedHU)).as("on the pallet").isNotNull();
			assertThat(handlingUnitsBL.getTransportUnitHU(pickedHU)).as("no TU around it").satisfies(tu -> assertThat(tu == null || !handlingUnitsBL.isTransportUnit(tu)).isTrue());
			assertThat(helper.huService.isCUInTU(pickedHU)).isFalse();
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
		void cuDirectlyOnPallet_isNotACUInATU()
		{
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

	@Nested
	class LabelBasedModeAndPallet
	{
		private PickingJob jobWithPalletAndTU()
		{
			final I_M_HU_PI luPI = huTestHelper.createHUDefinition("LU-NM", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			huTestHelper.createHU_PI_Item_IncludedHU(luPI, InterfaceWrapperHelper.load(tuPIId.getRepoId(), I_M_HU_PI.class), new BigDecimal("10"));
			PickingJob job = createJob(tuPiipId, true);
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

		private HuId palletOf(final HuId huId)
		{
			final I_M_HU pallet = handlingUnitsBL.getLoadingUnitHU(handlingUnitsBL.getById(huId));
			assertThat(pallet).as("on a pallet").isNotNull();
			return HuId.ofRepoId(pallet.getM_HU_ID());
		}

		@Test
		void scanEachCU_palletAndTU_firstScan_oneCUOfOneBooked()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-EACH");
			PickingJob job = jobWithPalletAndTU();

			job = pickEach(job, source, LOT_A, "1");

			final HuId pickedHuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final I_M_HU pickedHU = handlingUnitsBL.getById(pickedHuId);
			assertThat(handlingUnitsBL.isVirtual(pickedHU)).as("the picked HU is the booked CU").isTrue();
			assertThat(qtyOf(pickedHU)).isEqualByComparingTo("1");
			assertThat(packIdOf(pickedHU)).isEqualTo("1");
			final HuId tuId = tuOf(pickedHuId);
			assertThat(cusOf(tuId)).as("the TU holds only the booked CU").hasSize(1);
			assertThat(palletOf(tuId)).as("TU on the pallet").isNotNull();
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("1");
		}

		@Test
		void scanOnce_palletAndTU_firstBooking_pickedHUIsTheCU()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-ONCE");
			PickingJob job = jobWithPalletAndTU();

			job = pick(job, source, "3", label(PRODUCT_NO, LOT_A), false, null);

			final HuId pickedHuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final I_M_HU pickedHU = handlingUnitsBL.getById(pickedHuId);
			assertThat(handlingUnitsBL.isVirtual(pickedHU)).as("the picked HU is the booked CU, not the TU").isTrue();
			assertThat(qtyOf(pickedHU)).isEqualByComparingTo("3");
			final HuId tuId = tuOf(pickedHuId);
			assertThat(cusOf(tuId)).as("the TU holds only the booked CU").hasSize(1);
			assertThat(palletOf(tuId)).as("TU on the pallet").isNotNull();
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("3");
		}

		@Test
		void scanOnce_palletAndTU_firstBookingFillsTheTU_oneCUOf4_undo()
		{
			assertPalletAndTU_firstBookingOfQty_oneCU_undo("4", "QR-LU-ONCE-FULL");
		}

		@Test
		void scanOnce_palletAndTU_firstBookingAboveTUCapacity_oneCUOf5_undo()
		{
			assertPalletAndTU_firstBookingOfQty_oneCU_undo("5", "QR-LU-ONCE-OVER");
		}

		private void assertPalletAndTU_firstBookingOfQty_oneCU_undo(final String qty, final String sourceQRCode)
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = helper.createVHUInfo(productId, "100", sourceQRCode);
			PickingJob job = jobWithPalletAndTU();

			job = pick(job, source, qty, label(PRODUCT_NO, LOT_A), false, null);

			final HuId cuId = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final I_M_HU cu = handlingUnitsBL.getById(cuId);
			assertThat(handlingUnitsBL.isVirtual(cu)).as("the picked HU is the booked CU, not the TU").isTrue();
			assertThat(qtyOf(cu)).isEqualByComparingTo(qty);
			final HuId tuId = tuOf(cuId);
			assertThat(handlingUnitsBL.isAggregateHU(handlingUnitsBL.getById(tuId))).as("a real TU, not an aggregated TU").isFalse();
			assertThat(cusOf(tuId)).as("the TU holds only the booked CU").extracting(I_M_HU::getM_HU_ID).containsExactly(cuId.getRepoId());
			assertThat(palletOf(tuId)).as("the TU sits on the pallet").isNotNull();
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
		void scanEachCU_palletAndTU_twoCUs_undoFirst_removesOnlyThatCU()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-EACH-U");
			PickingJob job = jobWithPalletAndTU();
			job = pickEach(job, source, LOT_A, "1");
			job = pickEach(job, source, LOT_A, "2");

			final HuId tuId = openTUId(job);
			final HuId cu1Id = HuId.ofRepoId(cuWithPackId(tuId, "1").getM_HU_ID());
			final HuId cu2Id = HuId.ofRepoId(cuWithPackId(tuId, "2").getM_HU_ID());
			assertThat(tuOf(cu1Id)).as("CU 1 in the TU").isEqualTo(tuId);
			final HuId palletId = palletOf(tuId);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HUs are the CUs").containsExactlyInAnyOrder(cu1Id, cu2Id);
			assertThat(countQtyPickedRowsOfTU(tuId)).isEqualTo(2);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("2");

			job = undo(job, cu1Id, source.getQrCode(), null);

			assertThat(handlingUnitsBL.getById(cu1Id).getHUStatus()).as("CU 1 Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
			assertThat(handlingUnitsBL.getById(cu2Id).getHUStatus()).as("CU 2 stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(handlingUnitsBL.getById(tuId).getHUStatus()).as("TU stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(handlingUnitsBL.getById(palletId).getHUStatus()).as("pallet stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(cusOf(tuId)).as("only CU 2 is left in the TU").extracting(cu -> cu.getM_HU_ID()).containsExactly(cu2Id.getRepoId());
			assertThat(palletOf(tuId)).as("the TU stays on the pallet").isEqualTo(palletId);
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
			PickingJob job = jobWithPalletAndTU();
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
		void scanOnce_palletAndTU_twoBookings_undoFirst_removesOnlyThatCU()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-ONCE-U");
			PickingJob job = jobWithPalletAndTU();
			job = pick(job, source, "3", label(PRODUCT_NO, LOT_A), false, null);
			final HuId cu3Id = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			job = pick(job, source, "2", label(PRODUCT_NO, LOT_A), false, null);

			final HuId tuId = openTUId(job);
			assertThat(tuOf(cu3Id)).as("the CU of 3 in the TU").isEqualTo(tuId);
			final HuId cu2Id = HuId.ofRepoId(cuWithQty(tuId, "2").getM_HU_ID());
			final HuId palletId = palletOf(tuId);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("the picked HUs are the CUs").containsExactlyInAnyOrder(cu3Id, cu2Id);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("5");

			job = undo(job, cu3Id, source.getQrCode(), null);

			assertThat(handlingUnitsBL.getById(cu3Id).getHUStatus()).as("the CU of 3 Active again").isEqualTo(X_M_HU.HUSTATUS_Active);
			assertThat(handlingUnitsBL.getById(cu2Id).getHUStatus()).as("the CU of 2 stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(handlingUnitsBL.getById(tuId).getHUStatus()).as("TU stays Picked").isEqualTo(X_M_HU.HUSTATUS_Picked);
			assertThat(cusOf(tuId)).as("only the CU of 2 is left in the TU").extracting(cu -> cu.getM_HU_ID()).containsExactly(cu2Id.getRepoId());
			assertThat(palletOf(tuId)).as("the TU stays on the pallet").isEqualTo(palletId);
			assertThat(CollectionUtils.singleElement(job.getLines()).getPickedHUIds()).as("only the CU of 2 is still picked").containsExactly(cu2Id);
			assertThat(countQtyPickedRowsOfCU(cu3Id)).as("no QtyPicked row of the un-picked CU left").isEqualTo(0);
			assertThat(countQtyPickedRowsOfCU(cu2Id)).as("the QtyPicked row of the CU of 2 stays").isEqualTo(1);
			assertThat(pickedQtyOfLine(job)).as("the line's picked quantity dropped by 3").isEqualByComparingTo("2");
		}

		@Test
		void scanEachCU_existingPalletAndSecondTU_cuInNewTUOnSamePallet()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-LU-EACH-2");
			PickingJob job = jobWithPalletAndTU();
			job = pickEach(job, source, LOT_A, "1");
			final HuId cu1Id = CollectionUtils.singleElement(CollectionUtils.singleElement(job.getLines()).getPickedHUIds());
			final HuId tu1Id = tuOf(cu1Id);
			final HuId palletId = palletOf(tu1Id);
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
			assertThat(palletOf(tu2Id)).as("the new TU sits on the same pallet").isEqualTo(palletId);
			assertThat(pickedQtyOfLine(job)).isEqualByComparingTo("2");
		}
	}

	@Nested
	class ScanEachCU
	{
		@Test
		void scanEachCU_lotsAAB_threeCUsOfOne_ownLotAndPackId_tuLotEmpty()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-M");
			PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));

			job = pickEach(job, source, LOT_A, "1");
			job = pickEach(job, source, LOT_A, "2");
			job = pickEach(job, source, LOT_B, "3");

			final HuId tuId = openTUId(job);
			final List<I_M_HU> cus = cusOf(tuId);
			assertThat(cus).as("3 CUs in the TU").hasSize(3);
			cus.forEach(cu -> assertThat(qtyOf(cu)).as("each CU has quantity 1").isEqualByComparingTo("1"));
			assertThat(cus).extracting(PickingJobCUQtyEntryPickTest.this::lotOf, PickingJobCUQtyEntryPickTest.this::packIdOf)
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
			PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));

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
			PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));
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
			PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));

			job = pickEach(job, source, LOT_A, "1");
			job = pickEach(job, source, LOT_B, "1");

			final List<I_M_HU> cus = cusOf(openTUId(job));
			assertThat(cus).hasSize(2);
			assertThat(cus).extracting(PickingJobCUQtyEntryPickTest.this::lotOf, PickingJobCUQtyEntryPickTest.this::packIdOf)
					.containsExactlyInAnyOrder(tuple(LOT_A, "1"), tuple(LOT_B, "1"));
		}

		@Test
		void sameLabelInAnotherJob_booked()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-Q");
			PickingJob job1 = openTUFromLinePacking(createJob("salesOrderCU1", tuPiipId, true));
			job1 = pickEach(job1, source, LOT_A, "1");
			PickingJob job2 = openTUFromLinePacking(createJob("salesOrderCU2", tuPiipId, true));

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
			PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));
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
			PickingJob job = openTUFromLinePacking(createJob("salesOrderCU", tuPiipId, true, "2"));
			job = pickEach(job, source, LOT_A, "1");
			job = pickEach(job, source, LOT_A, "2");
			assertThat(pickedQtyOfLine(job)).as("line done: 2 of 2").isEqualByComparingTo("2");
			final PickingJob jobWhenDone = job;

			final List<String> log = logOfPickCommand(() -> assertThatThrownBy(() -> pickEach(jobWhenDone, source, LOT_A, "3"))
					.isInstanceOf(AdempiereException.class)
					.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(PICK_OVERDELIVERY_BLOCKED.toAD_Message()))
					.as("no technical parameters in the message the picker sees")
					.satisfies(ex -> assertThat(((AdempiereException)ex).getMessage()).doesNotContain("pickingJobId", "pickingJobLineId", "qtyToPick", "qtyOrdered", "qtyAlreadyPicked")));
			assertThat(log).as("the discarded scan is logged with its values")
					.anySatisfy(msg -> assertThat(msg).contains("Over-pick refused", "SCAN_EACH_CU", "pickingJobId=" + jobWhenDone.getId().getRepoId(), "pickingJobLineId=", "qtyToPick=1", "qtyOrdered=2", "qtyAlreadyPicked=2"));

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
			PickingJob job = createJob("salesOrderCU", null, true, "2");
			job = pick(job, source, "2", null, false, null);
			assertThat(pickedQtyOfLine(job)).as("line done: 2 of 2").isEqualByComparingTo("2");
			final PickingJob jobWhenDone = job;

			assertThatThrownBy(() -> pick(jobWhenDone, source, "1", null, false, null))
					.isInstanceOf(AdempiereException.class)
					.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(PICK_OVERDELIVERY_BLOCKED.toAD_Message()))
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
			final PickingJob job = openTUFromLinePacking(createJob(HUPIItemProductId.ofRepoId(kgPiip.getM_HU_PI_Item_Product_ID()), true));

			assertFails(() -> pick(job, source, "1", label("PRD0002", LOT_A), false, null), CU_EACH_ONLY_FOR_PIECES);

			assertThat(helper.pickingJobService.getById(job.getId()).getLines().get(0).getPickedHUIds()).as("nothing booked").isEmpty();
			final IHUStorage sourceStorage = huTestHelper.createMutableHUContextOutOfTransaction().getHUStorageFactory().getStorage(handlingUnitsBL.getById(sourceId));
			assertThat(sourceStorage.getQuantity(kgProductId, huTestHelper.uomKg).toBigDecimal()).as("source untouched").isEqualByComparingTo("100");
		}

		@Test
		void labelWithoutPackId_refused()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-S");
			final PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));

			assertFails(() -> pickEach(job, source, LOT_A, null), PickingCULabelService.MSG_CU_LABEL_FORMAT_MISMATCH);

			assertNothingBooked(job, source, "100");
		}
	}

	@Nested
	class Undo
	{
		@Test
		void undoWithTargetScan_cuMovedIntoTarget()
		{
			setProfile(CUQtyEntryMode.SCAN_EACH_CU);
			final HUInfo source = helper.createVHUInfo(productId, "100", "QR-T");
			final HUInfo target = helper.createVHUInfo(productId, "10", "QR-T-TARGET");
			PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));
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
			PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));
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
			PickingJob job = createJob(tuPiipId, true);
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
			PickingJob job = openTUFromLinePacking(createJob(tuPiipId, true));
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
	}

	@Nested
	class CloseTargets
	{
		@Test
		void closingAllTargets_queuesEachLinesTUOnItsOwnLinesSlot()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final TUOfCustomerA given = pickIntoTUOfCustomerA_thenScanSlotB("x", "3", label(PRODUCT_NO, LOT_A));

			helper.pickingJobService.closeLUAndTUPickingTargets(given.job);

			assertThat(queuedSlotsOf(given.tuA)).as("customer A's TU is queued on customer A's slot only").containsExactly(helper.pickingSlotId);
		}

		@Test
		void labelBasedModeBooking_thenModeSwitchedToManual_closingAllTargets_queuesTheTUOnItsOwnLinesSlot()
		{
			setProfile(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
			final TUOfCustomerA given = pickIntoTUOfCustomerA_thenScanSlotB("x4", "3", label(PRODUCT_NO, LOT_A));

			setProfile(CUQtyEntryMode.MANUAL);

			helper.pickingJobService.closeLUAndTUPickingTargets(given.job);

			assertThat(queuedSlotsOf(given.tuA)).as("customer A's TU, booked in Scan once, is queued on customer A's slot only").containsExactly(helper.pickingSlotId);
		}

		@Test
		void manual_closingAllTargets_queuesTheTUOnTheLastScannedSlot()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final TUOfCustomerA given = pickIntoTUOfCustomerA_thenScanSlotB("x2", "1", null);

			helper.pickingJobService.closeLUAndTUPickingTargets(given.job);

			assertThat(queuedSlotsOf(given.tuA)).as("Manual: the close queues the TU on the slot scanned last").contains(given.slotB);
		}

		@Test
		void mixedJob_labelBasedModeLineOnItsOwnSlot_manualLineOnTheLastScannedSlot()
		{
			setProfile(CUQtyEntryMode.MANUAL);
			final BPartnerLocationId customerB = helper.createBPartnerAndLocationId("customerB");
			final BPartnerId customerAId = customerAId();
			helper.updateMobileProfile(profile -> {
				final PickingJobOptionsId optionsA = helper.configService.createPickingJobOptions("customer A label-based mode", profile.getDefaultPickingJobOptions());
				helper.configService.updatePickingJobOptionsCUQtyEntry(optionsA, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId());
				return profile.toBuilder()
						.customerConfigs(PickingCustomerConfigsCollection.ofCollection(ImmutableList.of(
								PickingCustomerConfig.builder().customerId(customerAId).pickingJobOptionsId(optionsA).build())))
						.build();
			});
			final HUInfo sourceA = sourceTU("QR-X3A");
			final HUInfo sourceB = sourceTU("QR-X3B");
			PickingJob job = createTwoCustomerProductJob("x3", customerB);
			final PickingJobLine lineA = lineOfCustomerA(job);
			final PickingJobLine lineB = lineOfCustomerB(job);
			job = helper.pickingJobService.setTUPickingTarget(job, lineA.getId(), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
			job = pickOnLine(job, lineA.getId(), sourceA, "3", label(PRODUCT_NO, LOT_A));
			final HuId tuA = job.getTuPickingTargetEffective(lineA.getId()).orElseThrow(() -> new AssertionError("no TU of A")).getTuIdNotNull();
			final PickingSlotId slotB = createPickingSlot("SLOT-B");
			job = job.withPickingSlot(PickingSlotIdAndCaption.of(slotB, "SLOT-B"));
			job = helper.pickingJobService.setTUPickingTarget(job, lineB.getId(), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
			job = pickOnLine(job, lineB.getId(), sourceB, "1", null);
			final HuId tuB = job.getTuPickingTargetEffective(lineB.getId()).orElseThrow(() -> new AssertionError("no TU of B")).getTuIdNotNull();
			final PickingSlotId slotC = createPickingSlot("SLOT-C");
			job = job.withPickingSlot(PickingSlotIdAndCaption.of(slotC, "SLOT-C"));

			final PickingJob jobClosed = helper.pickingJobService.closeLUAndTUPickingTargets(job);

			assertThat(queuedSlotsOf(tuA)).as("label-based-mode customer A: own line slot only").containsExactly(helper.pickingSlotId);
			assertThat(queuedSlotsOf(tuB)).as("Manual customer B: the slot scanned last").contains(slotC);
			assertThat(jobClosed.getTuPickingTargetEffective(lineB.getId())).as("Manual line's target closed").isEmpty();
		}

		private final class TUOfCustomerA
		{
			private final PickingJob job;
			private final HuId tuA;
			private final PickingSlotId slotB;

			private TUOfCustomerA(final PickingJob job, final HuId tuA, final PickingSlotId slotB)
			{
				this.job = job;
				this.tuA = tuA;
				this.slotB = slotB;
			}
		}

		private TUOfCustomerA pickIntoTUOfCustomerA_thenScanSlotB(final String testId, final String qty, @Nullable final ScannedCode cuLabel)
		{
			final HUInfo source = sourceTU("QR-" + testId.toUpperCase());
			PickingJob job = createTwoCustomerProductJob(testId, helper.createBPartnerAndLocationId("customerB"));
			final PickingJobLine lineA = lineOfCustomerA(job);
			job = helper.pickingJobService.setTUPickingTarget(job, lineA.getId(), TUPickingTarget.ofPackingInstructions(tuPIId, "TU-4"));
			job = pickOnLine(job, lineA.getId(), source, qty, cuLabel);
			final HuId tuA = job.getTuPickingTargetEffective(lineA.getId()).orElseThrow(() -> new AssertionError("no TU")).getTuIdNotNull();
			final PickingSlotId slotB = createPickingSlot("SLOT-B");
			job = job.withPickingSlot(PickingSlotIdAndCaption.of(slotB, "SLOT-B"));
			return new TUOfCustomerA(job, tuA, slotB);
		}
	}

	private HUGraiSnapshotsCollection graiSnapshotsOfTUs(final Set<HuId> huIds)
	{
		return helper.huService.getGraiSnapshotsReplacingLabelBookedCUsByTheirTU(huIds, helper.huService.filterCUsInTU(huIds));
	}
}
