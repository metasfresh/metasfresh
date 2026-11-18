package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingUnit;
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
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PickingCULabelServiceTest
{
	private static final String GS = "\u001d";
	private static final String PRODUCT_NO = "PRD0001"; // 7 characters, as the real data shape
	private static final String LOT = "LOT123456"; // 9 characters
	private static final String GTIN = "04012345678901";

	private static final AdMessageKey HU_PRODUCT_NOT_MATCHING = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");
	private static final AdMessageKey CU_LABEL_NO_PACK_ID = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_NO_PACK_ID");
	private static final AdMessageKey CU_LABEL_FORMAT_MISMATCH = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_FORMAT_MISMATCH");
	private static final AdMessageKey CU_LABEL_NO_FORMAT = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_NO_FORMAT");

	private PickingCULabelService service;
	private ScannableCodeFormatService formatService;
	private I_C_UOM uom;
	private ScannableCodeFormat format;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		formatService = ScannableCodeFormatService.newInstanceForUnitTesting();
		service = new PickingCULabelService(formatService);

		uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("Stk");
		saveRecord(uom);

		// the label's marker parts and data positions (pack ID part at 49; written as Ignored until the PackId part type exists)
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
						part(49, 49, ScannableCodeFormatPartType.Ignored)
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
		return "01" + gtin + "91" + productNo + GS + "17" + "261107" + "10" + LOT + GS + "92" + packId;
	}

	private ResolvedCULabel resolve(final String label, final CUQtyEntryMode mode, final ScannableCodeFormatId formatId)
	{
		return service.resolve(line(PRODUCT_NO), ScannedCode.ofString(label), mode, formatId);
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
	void labelEndingBeforeANonPackIdPart_isFormatMismatch()
	{
		final String truncatedBeforeLot = label(GTIN, PRODUCT_NO, "7").substring(0, 36);

		assertFails(() -> resolve(truncatedBeforeLot, CUQtyEntryMode.SCAN_EACH_CU, format.getId()), CU_LABEL_FORMAT_MISMATCH);
	}

	@Test
	void scanEachCU_labelEndingBeforeTheMarker_hasNoPackId()
	{
		final String beforeMarker = label(GTIN, PRODUCT_NO, "7").substring(0, 46);

		assertFails(() -> resolve(beforeMarker, CUQtyEntryMode.SCAN_EACH_CU, format.getId()), CU_LABEL_NO_PACK_ID);
	}

	@Test
	void scanEachCU_labelEndingBeforeThePackIdPart_hasNoPackId()
	{
		final String markerOnly = label(GTIN, PRODUCT_NO, "7").substring(0, 48);

		assertFails(() -> resolve(markerOnly, CUQtyEntryMode.SCAN_EACH_CU, format.getId()), CU_LABEL_NO_PACK_ID);
	}

	@Test
	void scanOnce_labelEndingBeforeThePackIdPart_isFormatMismatch()
	{
		final String markerOnly = label(GTIN, PRODUCT_NO, "7").substring(0, 48);

		assertFails(() -> resolve(markerOnly, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId()), CU_LABEL_FORMAT_MISMATCH);
	}

	@Test
	void noEffectiveFormat_isRefused()
	{
		assertFails(() -> resolve(label(GTIN, PRODUCT_NO, "7"), CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, null), CU_LABEL_NO_FORMAT);
	}

	@Test
	void scanEachCU_withoutPackId_isRefused()
	{
		final CULabelData data = CULabelData.builder().productNo(PRODUCT_NO).lotNo(LOT).packId(null).build();

		assertFails(() -> service.resolve(line(PRODUCT_NO), data, CUQtyEntryMode.SCAN_EACH_CU), CU_LABEL_NO_PACK_ID);
	}

	@Test
	void scanEachCU_withPackId_returnsIt()
	{
		final CULabelData data = CULabelData.builder().productNo(PRODUCT_NO).lotNo(LOT).packId("1").build();

		final ResolvedCULabel resolved = service.resolve(line(PRODUCT_NO), data, CUQtyEntryMode.SCAN_EACH_CU);
		assertThat(resolved).isNotNull();
		assertThat(resolved.getPackId()).isEqualTo("1");
	}

	@Test
	void scanOnce_ignoresThePackId()
	{
		final CULabelData data = CULabelData.builder().productNo(PRODUCT_NO).lotNo(LOT).packId("1").build();

		final ResolvedCULabel resolved = service.resolve(line(PRODUCT_NO), data, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY);
		assertThat(resolved).isNotNull();
		assertThat(resolved.getPackId()).isNull();
	}

	@Test
	void onlyTheEffectiveFormatIsUsed()
	{
		// a second active format that would read the label
		final ScannableCodeFormat other = formatService.create(ScannableCodeFormatCreateRequest.builder()
				.name("Other")
				.parts(ImmutableList.of(
						part(1, 2, ScannableCodeFormatPartType.Ignored),
						part(3, 16, ScannableCodeFormatPartType.Ignored),
						part(17, 18, ScannableCodeFormatPartType.Ignored),
						part(19, 25, ScannableCodeFormatPartType.ProductCode)
				))
				.build());
		final String otherOnly = "XX" + GTIN + "XX" + PRODUCT_NO; // `format` cannot read it, `other` can

		final ResolvedCULabel resolved = resolve(otherOnly, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, other.getId());
		assertThat(resolved).isNotNull();
		assertThat(resolved.getProductId()).isEqualTo(ProductId.ofRepoId(10));
		assertFails(() -> resolve(otherOnly, CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, format.getId()), CU_LABEL_FORMAT_MISMATCH);
	}
}
