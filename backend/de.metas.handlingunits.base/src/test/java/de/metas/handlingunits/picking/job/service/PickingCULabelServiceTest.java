package de.metas.handlingunits.picking.job.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.job.model.CULabelPackId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest.Part;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.ScannableCodeFormatPart;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PickingCULabelServiceTest
{
	private static final String GS = "\u001d";
	private static final String PRODUCT_NO = "PRD0001";
	private static final String LOT = "LOT123456";
	private static final String GTIN = "04012345678901";

	private static final AdMessageKey HU_PRODUCT_NOT_MATCHING = MobileQRCodeMessages.HU_PRODUCT_NOT_MATCHING;
	private static final AdMessageKey CU_LABEL_NO_PACK_ID = PickingCULabelService.MSG_CU_LABEL_NO_PACK_ID;
	private static final AdMessageKey CU_LABEL_FORMAT_MISMATCH = PickingCULabelService.MSG_CU_LABEL_FORMAT_MISMATCH;
	private static final AdMessageKey CU_LABEL_NO_FORMAT = PickingCULabelService.MSG_CU_LABEL_NO_FORMAT;

	private PickingCULabelService service;
	private ScannableCodeFormatService formatService;
	private I_C_UOM uom;
	private ScannableCodeFormat format;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		formatService = ScannableCodeFormatService.newInstanceForUnitTesting();
		service = PickingCULabelService.newInstanceForUnitTesting();

		uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("Stk");
		saveRecord(uom);

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
						part(47, ScannableCodeFormatPart.OPEN_END, ScannableCodeFormatPartType.PackId)
				))
				.build());
	}

	private static Part constant(final int start, final int end, final String value)
	{
		return Part.builder().startPosition(start).endPosition(end).type(ScannableCodeFormatPartType.Constant).constantValue(value).build();
	}

	private static Part part(final int start, final int end, final ScannableCodeFormatPartType type)
	{
		return Part.builder().startPosition(start).endPosition(end).type(type).build();
	}

	private PickingJobLine line(final String productNo)
	{
		return PickingJobLine.builder()
				.id(PickingJobLineId.ofRepoId(1))
				.caption(TranslatableStrings.constant("line"))
				.productId(ProductId.ofRepoId(10))
				.productNo(productNo)
				.productCategoryId(ProductCategoryId.ofRepoId(11))
				.productValueAndName(ProductValueAndName.of(productNo, TranslatableStrings.constant("product")))
				.packingInfo(HUPIItemProduct.builder()
						.id(HUPIItemProductId.VIRTUAL_HU)
						.name(TranslatableStrings.constant("packing"))
						.piItemId(HuPackingInstructionsItemId.ofRepoId(540001))
						.build())
				.qtyToPick(Quantity.of(BigDecimal.valueOf(8), uom))
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(20, 21))
				.salesOrderDocumentNo("SO-1")
				.orderLineSeqNo(10)
				.deliveryBPLocationId(BPartnerLocationId.ofRepoId(30, 31))
				.scheduleId(ShipmentScheduleAndJobScheduleId.ofRepoIds(40, 0))
				.steps(ImmutableList.of())
				.pickingUnit(PickingUnit.CU)
				.build();
	}

	private static String label(final String gtin, final String productNo, final String packId)
	{
		// the string the scanner sends (DataWedge without control characters): 47 characters for a one-digit pack ID, no separators
		return "01" + gtin + "91" + productNo + "17" + "261107" + "10" + LOT + "92" + packId;
	}

	/** The same label as a scanner that sends the GS separator (char 29) after the product and after the lot */
	private static String labelWithGS(final String gtin, final String productNo, final String packId)
	{
		return "01" + gtin + "91" + productNo + GS + "17" + "261107" + "10" + LOT + GS + "92" + packId;
	}

	private ResolvedCULabel resolve(final String label, final CUQtyEntryMode mode, final ScannableCodeFormatId formatId)
	{
		return service.resolve(line(PRODUCT_NO), ScannedCode.ofString(label), mode.getPolicy(), Optional.ofNullable(formatId));
	}

	private static void assertFails(final Runnable runnable, final AdMessageKey expected)
	{
		assertThatThrownBy(runnable::run)
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(expected.toAD_Message()));
	}

	@Test
	void formattedLabel_productMatches_isResolved()
	{
		final ResolvedCULabel resolved = resolve(label(GTIN, PRODUCT_NO, "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId());

		assertThat(resolved).isNotNull();
		assertThat(resolved.getProductId()).isEqualTo(ProductId.ofRepoId(10));
		assertThat(resolved.getLotNo()).isEqualTo(LOT);
		assertThat(resolved.getBestBeforeDate()).isEqualTo(LocalDate.of(2026, 11, 7));
	}

	@Test
	void productPartOfAnotherProduct_isRefused()
	{
		assertFails(() -> resolve(label(GTIN, "OTHER99", "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId()), HU_PRODUCT_NOT_MATCHING);
	}

	@Test
	void gtinOfAnotherProduct_isNeverRead()
	{
		final ResolvedCULabel resolved = resolve(label("09999999999994", PRODUCT_NO, "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId());

		assertThat(resolved).isNotNull();
		assertThat(resolved.getProductId()).isEqualTo(ProductId.ofRepoId(10));
	}

	@Test
	void symbologyPrefix_isFormatMismatch()
	{
		assertFails(() -> resolve("]d2" + label(GTIN, PRODUCT_NO, "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId()), CU_LABEL_FORMAT_MISMATCH);
	}

	@Test
	void formatMismatch_tellsWhyTheLabelDoesNotFit()
	{
		final String label = "]d2" + label(GTIN, PRODUCT_NO, "7");
		final String parserReason = format.parse(ScannedCode.ofString(label)).getExplanation().getDefaultValue();
		assertThat(parserReason).isNotBlank();

		assertThatThrownBy(() -> resolve(label, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId()))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final AdempiereException adempiereException = (AdempiereException)ex;
					assertThat(adempiereException.getErrorCode()).isEqualTo(CU_LABEL_FORMAT_MISMATCH.toAD_Message());
					assertThat(adempiereException.getMessage()).contains(parserReason);
				});
	}

	@Test
	void formatMismatch_logsTheParserExplanation()
	{
		final Logger logger = (Logger)LoggerFactory.getLogger(PickingCULabelService.class);
		final ListAppender<ILoggingEvent> logAppender = new ListAppender<>();
		logAppender.start();
		logger.addAppender(logAppender);
		try
		{
			final String label = "]d2" + label(GTIN, PRODUCT_NO, "7");
			assertFails(() -> resolve(label, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId()), CU_LABEL_FORMAT_MISMATCH);

			assertThat(logAppender.list).hasSize(1);
			final ILoggingEvent event = logAppender.list.get(0);
			assertThat(event.getLevel()).isEqualTo(Level.WARN);
			assertThat(event.getFormattedMessage()).contains(label).contains("Invalid constant marker");
		}
		finally
		{
			logger.detachAppender(logAppender);
		}
	}

	private static List<String> warningsLoggedBy(final Runnable refusedCall)
	{
		final Logger logger = (Logger)LoggerFactory.getLogger(PickingCULabelService.class);
		final ListAppender<ILoggingEvent> logAppender = new ListAppender<>();
		logAppender.start();
		logger.addAppender(logAppender);
		try
		{
			assertThatThrownBy(refusedCall::run).isInstanceOf(AdempiereException.class);
			return logAppender.list.stream()
					.filter(event -> event.getLevel() == Level.WARN)
					.map(ILoggingEvent::getFormattedMessage)
					.collect(Collectors.toList());
		}
		finally
		{
			logger.detachAppender(logAppender);
		}
	}

	@Test
	void labelOfAnotherProduct_logsTheDiscardedLabelData()
	{
		final List<String> warnings = warningsLoggedBy(() -> resolve(label(GTIN, "OTHER99", "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId()));

		assertThat(warnings).hasSize(1);
		assertThat(warnings.get(0)).contains("OTHER99").contains(PRODUCT_NO).contains(LOT);
	}

	@Test
	void labelDataWithoutProductNo_logsTheDiscardedLabelData()
	{
		final CULabelData noProduct = CULabelData.builder().lotNo(LOT).packId(CULabelPackId.ofString("7")).build();

		final List<String> warnings = warningsLoggedBy(() -> service.resolve(line(PRODUCT_NO), noProduct, CUQtyEntryMode.SCAN_EACH_CU.getPolicy()));

		assertThat(warnings).hasSize(1);
		assertThat(warnings.get(0)).contains(LOT).contains("no product");
	}

	@Test
	void labelDataWithoutPackId_logsTheDiscardedLabelData()
	{
		final CULabelData noPackId = CULabelData.builder().productNo(PRODUCT_NO).lotNo(LOT).build();

		final List<String> warnings = warningsLoggedBy(() -> service.resolve(line(PRODUCT_NO), noPackId, CUQtyEntryMode.SCAN_EACH_CU.getPolicy()));

		assertThat(warnings).hasSize(1);
		assertThat(warnings.get(0)).contains(PRODUCT_NO).contains(LOT).contains("no pack ID");
	}

	@Test
	void formatNotFound_logsItsId()
	{
		final List<String> warnings = warningsLoggedBy(() -> resolve(label(GTIN, PRODUCT_NO, "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, ScannableCodeFormatId.ofRepoId(999999)));

		assertThat(warnings).hasSize(1);
		assertThat(warnings.get(0)).contains("999999");
	}

	@Test
	void labelEndingBeforeANonPackIdPart_isFormatMismatch()
	{
		final String truncatedBeforeLot = label(GTIN, PRODUCT_NO, "7").substring(0, 35);

		assertFails(() -> resolve(truncatedBeforeLot, CUQtyEntryMode.SCAN_EACH_CU, format.getId()), CU_LABEL_FORMAT_MISMATCH);
	}

	@Test
	void noEffectiveFormat_isRefused()
	{
		assertFails(() -> resolve(label(GTIN, PRODUCT_NO, "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, null), CU_LABEL_NO_FORMAT);
	}

	@Test
	void onlyTheEffectiveFormatIsUsed()
	{
		final ScannableCodeFormat other = formatService.create(ScannableCodeFormatCreateRequest.builder()
				.name("Other")
				.parts(ImmutableList.of(
						part(1, 2, ScannableCodeFormatPartType.Ignored),
						part(3, 16, ScannableCodeFormatPartType.Ignored),
						part(17, 18, ScannableCodeFormatPartType.Ignored),
						part(19, 25, ScannableCodeFormatPartType.ProductCode)
				))
				.build());
		final String otherOnly = "XX" + GTIN + "XX" + PRODUCT_NO;

		final ResolvedCULabel resolved = resolve(otherOnly, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, other.getId());
		assertThat(resolved).isNotNull();
		assertThat(resolved.getProductId()).isEqualTo(ProductId.ofRepoId(10));
		assertFails(() -> resolve(otherOnly, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId()), CU_LABEL_FORMAT_MISMATCH);
	}

	@Test
	void formatWithConstantBeforeItsLastPart_labelEndingBeforeThatPart_isFormatMismatchNotNoPackId()
	{
		final ScannableCodeFormat constantBeforeLastPartFormat = formatService.create(ScannableCodeFormatCreateRequest.builder()
				.name("CU label with a constant before its last part")
				.parts(ImmutableList.of(
						constant(1, 2, "01"),
						part(3, 16, ScannableCodeFormatPartType.Ignored),
						constant(17, 18, "91"),
						part(19, 25, ScannableCodeFormatPartType.ProductCode),
						constant(26, 27, "17"),
						part(28, 33, ScannableCodeFormatPartType.BestBeforeDate),
						constant(34, 35, "10"),
						part(36, 44, ScannableCodeFormatPartType.LotNo)))
				.build());
		final String beforeLot = label(GTIN, PRODUCT_NO, "7").substring(0, 35);

		assertFails(() -> resolve(beforeLot, CUQtyEntryMode.SCAN_EACH_CU, constantBeforeLastPartFormat.getId()), CU_LABEL_FORMAT_MISMATCH);
	}

	@Test
	void realLayout_packIdOpenEnd()
	{
		final ScannableCodeFormat packIdFormat = formatService.create(ScannableCodeFormatCreateRequest.builder()
				.name("CU label with PackId")
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

		assertThat(resolve(label(GTIN, PRODUCT_NO, "2"), CUQtyEntryMode.SCAN_EACH_CU, packIdFormat.getId()).getPackId()).isEqualTo(CULabelPackId.ofString("2"));
		assertThat(resolve(label(GTIN, PRODUCT_NO, "10"), CUQtyEntryMode.SCAN_EACH_CU, packIdFormat.getId()).getPackId()).isEqualTo(CULabelPackId.ofString("10"));

		final String withoutPackId = label(GTIN, PRODUCT_NO, "2").substring(0, 44); // ends before "92"
		assertFails(() -> resolve(withoutPackId, CUQtyEntryMode.SCAN_EACH_CU, packIdFormat.getId()), CU_LABEL_FORMAT_MISMATCH);
		assertFails(() -> resolve(withoutPackId, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, packIdFormat.getId()), CU_LABEL_FORMAT_MISMATCH);
	}

	@Test
	void formatWithoutPackIdPart_labelEndingBeforeOrInsideAConstant_isAFormatMismatch()
	{
		final ScannableCodeFormat gapFormat = formatService.create(ScannableCodeFormatCreateRequest.builder()
				.name("CU label, constant 92 and a gap before the next part")
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
						constant(50, 51, "93")))
				.build());
		final String insideGap = label(GTIN, PRODUCT_NO, "2").substring(0, 47);
		final String beforeMarker = label(GTIN, PRODUCT_NO, "2").substring(0, 44);

		assertFails(() -> resolve(insideGap, CUQtyEntryMode.SCAN_EACH_CU, gapFormat.getId()), CU_LABEL_FORMAT_MISMATCH);
		assertFails(() -> resolve(beforeMarker, CUQtyEntryMode.SCAN_EACH_CU, gapFormat.getId()), CU_LABEL_FORMAT_MISMATCH);
	}

	@Test
	void packIdPartWithAnotherMarker_labelEndingBeforeIt_isAFormatMismatch()
	{
		final ScannableCodeFormat otherMarkerFormat = formatService.create(ScannableCodeFormatCreateRequest.builder()
				.name("CU label with PackId behind constant 93")
				.parts(ImmutableList.of(
						constant(1, 2, "01"),
						part(3, 16, ScannableCodeFormatPartType.Ignored),
						constant(17, 18, "91"),
						part(19, 25, ScannableCodeFormatPartType.ProductCode),
						constant(26, 27, "17"),
						part(28, 33, ScannableCodeFormatPartType.BestBeforeDate),
						constant(34, 35, "10"),
						part(36, 44, ScannableCodeFormatPartType.LotNo),
						constant(45, 46, "93"),
						part(47, ScannableCodeFormatPart.OPEN_END, ScannableCodeFormatPartType.PackId)))
				.build());
		final String endsBeforeMarker = label(GTIN, PRODUCT_NO, "2").substring(0, 44);

		assertFails(() -> resolve(endsBeforeMarker, CUQtyEntryMode.SCAN_EACH_CU, otherMarkerFormat.getId()), CU_LABEL_FORMAT_MISMATCH);
		assertFails(() -> resolve(endsBeforeMarker, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, otherMarkerFormat.getId()), CU_LABEL_FORMAT_MISMATCH);
	}

	@Test
	void formatIdSetButNotResolvable_hasNoFormat()
	{
		assertFails(() -> resolve(label(GTIN, PRODUCT_NO, "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, ScannableCodeFormatId.ofRepoId(999999)), CU_LABEL_NO_FORMAT);
	}

	@Nested
	class ScanEachCU
	{
		@Test
		void labelEndingBeforeTheMarker_isAFormatMismatch()
		{
			final String beforeMarker = label(GTIN, PRODUCT_NO, "7").substring(0, 44);

			assertFails(() -> resolve(beforeMarker, CUQtyEntryMode.SCAN_EACH_CU, format.getId()), CU_LABEL_FORMAT_MISMATCH);
		}

		@Test
		void labelEndingBeforeTheMarker_logsTheParserExplanation()
		{
			final Logger logger = (Logger)LoggerFactory.getLogger(PickingCULabelService.class);
			final ListAppender<ILoggingEvent> logAppender = new ListAppender<>();
			logAppender.start();
			logger.addAppender(logAppender);
			try
			{
				final String beforeMarker = label(GTIN, PRODUCT_NO, "7").substring(0, 44);
				assertFails(() -> resolve(beforeMarker, CUQtyEntryMode.SCAN_EACH_CU, format.getId()), CU_LABEL_FORMAT_MISMATCH);

				assertThat(logAppender.list).hasSize(1);
				final ILoggingEvent event = logAppender.list.get(0);
				assertThat(event.getLevel()).isEqualTo(Level.WARN);
				assertThat(event.getFormattedMessage()).contains("Cannot extract part Constant[45-46]");
			}
			finally
			{
				logger.detachAppender(logAppender);
			}
		}

		@Test
		void labelEndingBeforeThePackIdPart_isAFormatMismatch()
		{
			final String constantOnly = label(GTIN, PRODUCT_NO, "7").substring(0, 46);

			assertFails(() -> resolve(constantOnly, CUQtyEntryMode.SCAN_EACH_CU, format.getId()), CU_LABEL_FORMAT_MISMATCH);
		}

		@Test
		void withoutPackId_isRefused()
		{
			final CULabelData data = CULabelData.builder().productNo(PRODUCT_NO).lotNo(LOT).packId(null).build();

			assertFails(() -> service.resolve(line(PRODUCT_NO), data, CUQtyEntryMode.SCAN_EACH_CU.getPolicy()), CU_LABEL_NO_PACK_ID);
		}

		@Test
		void withPackId_returnsIt()
		{
			final CULabelData data = CULabelData.builder().productNo(PRODUCT_NO).lotNo(LOT).packId(CULabelPackId.ofString("1")).build();

			final ResolvedCULabel resolved = service.resolve(line(PRODUCT_NO), data, CUQtyEntryMode.SCAN_EACH_CU.getPolicy());
			assertThat(resolved).isNotNull();
			assertThat(resolved.getPackId()).isEqualTo(CULabelPackId.ofString("1"));
		}
	}

	@Nested
	class ScanOnce
	{
		@Test
		void labelEndingBeforeThePackIdPart_isFormatMismatch()
		{
			final String constantOnly = label(GTIN, PRODUCT_NO, "7").substring(0, 46);

			assertFails(() -> resolve(constantOnly, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId()), CU_LABEL_FORMAT_MISMATCH);
		}

		@Test
		void ignoresThePackId()
		{
			final CULabelData data = CULabelData.builder().productNo(PRODUCT_NO).lotNo(LOT).packId(CULabelPackId.ofString("1")).build();

			final ResolvedCULabel resolved = service.resolve(line(PRODUCT_NO), data, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY.getPolicy());
			assertThat(resolved).isNotNull();
			assertThat(resolved.getPackId()).isNull();
		}
	}

	@Nested
	class LabelWithGSSeparators
	{
		private ScannableCodeFormat gsFormat;

		@BeforeEach
		void beforeEach()
		{
			gsFormat = formatService.create(ScannableCodeFormatCreateRequest.builder()
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
		}

		@Test
		void labelWithGS_isReadByAFormatWithTheGSPositions()
		{
			final ResolvedCULabel resolved = resolve(labelWithGS(GTIN, PRODUCT_NO, "7"), CUQtyEntryMode.SCAN_EACH_CU, gsFormat.getId());

			assertThat(resolved.getProductId()).isEqualTo(ProductId.ofRepoId(10));
			assertThat(resolved.getLotNo()).isEqualTo(LOT);
			assertThat(resolved.getPackId()).isEqualTo(CULabelPackId.ofString("7"));
		}

		@Test
		void labelWithGS_otherProduct_isRefused()
		{
			assertFails(() -> resolve(labelWithGS(GTIN, "OTHER99", "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, gsFormat.getId()), HU_PRODUCT_NOT_MATCHING);
		}

		@Test
		void labelWithGS_isNotReadByTheFormatOfTheLabelWithoutSeparators()
		{
			assertFails(() -> resolve(labelWithGS(GTIN, PRODUCT_NO, "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId()), CU_LABEL_FORMAT_MISMATCH);
		}

		@Test
		void labelWithoutSeparators_isNotReadByTheFormatWithTheGSPositions()
		{
			assertFails(() -> resolve(label(GTIN, PRODUCT_NO, "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, gsFormat.getId()), CU_LABEL_FORMAT_MISMATCH);
		}
	}
}
