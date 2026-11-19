package de.metas.handlingunits.picking.job.service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ParsedScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.ScannableCodeFormatPart;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Reads a CU (pack) label with the picking line's effective label format only (never the global format list).
 */
@Service
@RequiredArgsConstructor
public class PickingCULabelService
{
	public static final AdMessageKey MSG_CU_LABEL_NO_PACK_ID = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_NO_PACK_ID");
	public static final AdMessageKey MSG_CU_LABEL_FORMAT_MISMATCH = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_FORMAT_MISMATCH");
	public static final AdMessageKey MSG_CU_LABEL_ALREADY_PICKED = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_ALREADY_PICKED");
	public static final AdMessageKey MSG_CU_LABEL_NO_FORMAT = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_NO_FORMAT");

	/** HU attribute that carries the pack ID of a CU picked in {@code SCAN_EACH_CU} mode (migration 5836530) */
	public static final AttributeCode ATTR_PackId = AttributeCode.ofString("PackId");

	/** the constant marker in front of the pack ID in the label */
	private static final String PACK_ID_MARKER = "92";

	@NonNull private final ScannableCodeFormatService scannableCodeFormatService;

	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

	public static PickingCULabelService newInstanceForUnitTesting()
	{
		SpringContextHolder.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(
				PickingCULabelService.class,
				() -> new PickingCULabelService(ScannableCodeFormatService.newInstanceForUnitTesting()));
	}

	/**
	 * @throws AdempiereException (user message) if there is no format, the label does not match it, has no pack ID (Scan each CU) or is of another product
	 */
	public ResolvedCULabel resolve(
			@NonNull final PickingJobLine line,
			@NonNull final ScannedCode label,
			@NonNull final CUQtyEntryMode mode,
			@Nullable final ScannableCodeFormatId formatId)
	{
		final ScannableCodeFormat format = formatId != null
				? scannableCodeFormatService.getById(formatId).orElse(null)
				: null;
		if (format == null)
		{
			throw userError(MSG_CU_LABEL_NO_FORMAT);
		}

		final ExplainedOptional<ParsedScannedCode> parsed = format.parse(label);
		if (parsed.isPresent())
		{
			return resolve(line, readCULabelData(parsed.get(), label), mode);
		}

		if (mode.isScanEachCU() && isEndingBeforePackId(format, label))
		{
			throw userError(MSG_CU_LABEL_NO_PACK_ID);
		}
		throw userError(MSG_CU_LABEL_FORMAT_MISMATCH);
	}

	/** Testable core: checks the label data against the line. */
	public ResolvedCULabel resolve(
			@NonNull final PickingJobLine line,
			@NonNull final CULabelData labelData,
			@NonNull final CUQtyEntryMode mode)
	{
		if (labelData.getProductNo() == null)
		{
			throw userError(MSG_CU_LABEL_FORMAT_MISMATCH);
		}
		if (!Objects.equals(labelData.getProductNo(), line.getProductNo()))
		{
			throw userError(MobileQRCodeMessages.HU_PRODUCT_NOT_MATCHING, line.getProductValueAndName().getName());
		}

		final String packId = mode.isScanEachCU() ? labelData.getPackId() : null;
		if (mode.isScanEachCU() && packId == null)
		{
			throw userError(MSG_CU_LABEL_NO_PACK_ID);
		}

		return ResolvedCULabel.builder()
				.productId(line.getProductId())
				.lotNo(labelData.getLotNo())
				.bestBeforeDate(labelData.getBestBeforeDate())
				.packId(packId)
				.build();
	}

	/** Seam for unit tests: they override it to supply fake {@link CULabelData} (the pack ID part type comes from the label reader, not from this task). */
	@VisibleForTesting
	protected CULabelData readCULabelData(@NonNull final ParsedScannedCode parsed, @NonNull final ScannedCode label)
	{
		return toCULabelData(parsed);
	}

	/** The only place that reads the pack ID of a parsed label. */
	public CULabelData toCULabelData(@NonNull final ParsedScannedCode parsed)
	{
		return CULabelData.builder()
				.productNo(parsed.getProductNo())
				.lotNo(parsed.getLotNo())
				.bestBeforeDate(parsed.getBestBeforeDate())
				.packId(null)
				.build();
	}

	/**
	 * @return true if one of the job's CURRENT picked HUs (any {@code HUStatus}, any step) of that product carries this lot and pack ID.
	 * One HU query on the PackId attribute value over the picked HU ids; only the (few) HUs with that pack ID are checked for the lot.
	 */
	public boolean isAlreadyPickedInJob(
			@NonNull final PickingJob job,
			@NonNull final ProductId productId,
			@Nullable final String lotNo,
			@NonNull final String packId)
	{
		final ImmutableSet<HuId> pickedHUIds = job.streamLines()
				.filter(line -> ProductId.equals(line.getProductId(), productId))
				.flatMap(line -> line.getPickedHUIds().stream())
				.collect(ImmutableSet.toImmutableSet());
		if (pickedHUIds.isEmpty())
		{
			return false;
		}

		// the picked HUs are CUs inside boxes: not top level
		final ImmutableSet<HuId> huIdsWithPackId = handlingUnitsBL.createHUQueryBuilder()
				.setOnlyTopLevelHUs(false)
				.addOnlyHUIds(pickedHUIds)
				.addOnlyWithAttribute(ATTR_PackId, packId)
				.listIds();
		if (huIdsWithPackId.isEmpty())
		{
			return false;
		}

		final IHUContext huContext = huContextFactory.createMutableHUContext();
		final String lotNoNorm = StringUtils.trimBlankToNull(lotNo);
		return huIdsWithPackId.stream()
				.map(handlingUnitsBL::getById)
				.anyMatch(hu -> {
					final IAttributeStorage attributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
					final String huLotNo = attributes.hasAttribute(AttributeConstants.ATTR_LotNumber)
							? StringUtils.trimBlankToNull(attributes.getValueAsString(AttributeConstants.ATTR_LotNumber))
							: null;
					return Objects.equals(huLotNo, lotNoNorm);
				});
	}

	/**
	 * @return true if the label is too short to hold the pack ID zone (the part after the "92" marker) while every part before the marker reads.
	 * A format without the marker has no pack ID zone: false.
	 */
	private static boolean isEndingBeforePackId(@NonNull final ScannableCodeFormat format, @NonNull final ScannedCode label)
	{
		final ImmutableList<ScannableCodeFormatPart> parts = format.getParts();
		final ScannableCodeFormatPart marker = parts.stream()
				.filter(part -> part.getType() == ScannableCodeFormatPartType.Constant && PACK_ID_MARKER.equals(part.getConstantValue()))
				.min(Comparator.comparingInt(ScannableCodeFormatPart::getStartPosition))
				.orElse(null);
		if (marker == null)
		{
			return false;
		}

		final int zoneEnd = parts.stream()
				.mapToInt(ScannableCodeFormatPart::getStartPosition)
				.filter(start -> start > marker.getStartPosition())
				.min()
				.orElse(marker.getStartPosition() + PACK_ID_MARKER.length());
		if (label.length() >= zoneEnd)
		{
			return false;
		}

		final ImmutableList<ScannableCodeFormatPart> partsBefore = parts.stream()
				.filter(part -> part.getStartPosition() < marker.getStartPosition())
				.filter(part -> part.getType() != ScannableCodeFormatPartType.Ignored) // separators: the scanned code is trimmed, a trailing one is lost
				.collect(ImmutableList.toImmutableList());
		if (partsBefore.isEmpty())
		{
			return false;
		}

		final ScannableCodeFormat prefixFormat = ScannableCodeFormat.builder()
				.id(format.getId())
				.name(format.getName())
				.parts(partsBefore)
				.build();
		return prefixFormat.parse(label).isPresent();
	}

	private static AdempiereException userError(@NonNull final AdMessageKey messageKey, final Object... params)
	{
		return new AdempiereException(messageKey, params).markAsUserValidationError();
	}
}
