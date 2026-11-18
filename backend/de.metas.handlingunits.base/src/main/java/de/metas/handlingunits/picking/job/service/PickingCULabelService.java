package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ParsedScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.ScannableCodeFormatPart;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
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
	public static final AdMessageKey MSG_CU_LABEL_NO_FORMAT = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_NO_FORMAT");
	public static final AdMessageKey MSG_PRODUCT_NOT_MATCHING = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");

	/** the constant marker in front of the pack ID in the label */
	private static final String PACK_ID_MARKER = "92";

	@NonNull private final ScannableCodeFormatService scannableCodeFormatService;

	public static PickingCULabelService newInstanceForUnitTesting()
	{
		return new PickingCULabelService(ScannableCodeFormatService.newInstanceForUnitTesting());
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
			return resolve(line, toCULabelData(parsed.get()), mode);
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
			throw userError(MSG_PRODUCT_NOT_MATCHING, line.getProductValueAndName().getName());
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

	/** The only reader of the pack ID part (filled from the label format's pack ID part type, task A12). */
	public CULabelData toCULabelData(@NonNull final ParsedScannedCode parsed)
	{
		return CULabelData.builder()
				.productNo(parsed.getProductNo())
				.lotNo(parsed.getLotNo())
				.bestBeforeDate(parsed.getBestBeforeDate())
				.packId(null) // TODO A12: parsed.getPackId()
				.build();
	}

	/**
	 * @return true if the label is too short to hold the pack ID zone (the "92" marker or, without a marker, the last part)
	 * while every part before that zone reads.
	 */
	private static boolean isEndingBeforePackId(@NonNull final ScannableCodeFormat format, @NonNull final ScannedCode label)
	{
		final ImmutableList<ScannableCodeFormatPart> parts = format.getParts();
		if (parts.isEmpty())
		{
			return false;
		}

		final int lastPartStart = parts.stream().mapToInt(ScannableCodeFormatPart::getStartPosition).max().orElse(0);
		final int zoneStart = parts.stream()
				.filter(part -> part.getType() == ScannableCodeFormatPartType.Constant && PACK_ID_MARKER.equals(part.getConstantValue()))
				.map(ScannableCodeFormatPart::getStartPosition)
				.min(Comparator.naturalOrder())
				.orElse(lastPartStart);

		if (label.length() >= lastPartStart)
		{
			return false;
		}

		final ImmutableList<ScannableCodeFormatPart> partsBefore = parts.stream()
				.filter(part -> part.getStartPosition() < zoneStart)
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
