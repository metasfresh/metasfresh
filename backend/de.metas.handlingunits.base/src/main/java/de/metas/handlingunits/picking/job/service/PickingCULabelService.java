package de.metas.handlingunits.picking.job.service;

import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryPolicy;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ParsedScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/** Reads a CU label with the picking line's effective label format only. */
@Service
@RequiredArgsConstructor
public class PickingCULabelService
{
	private static final Logger logger = LogManager.getLogger(PickingCULabelService.class);

	public static final AdMessageKey MSG_CU_LABEL_NO_PACK_ID = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_NO_PACK_ID");
	public static final AdMessageKey MSG_CU_LABEL_FORMAT_MISMATCH = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_FORMAT_MISMATCH");
	public static final AdMessageKey MSG_CU_LABEL_ALREADY_PICKED = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_ALREADY_PICKED");
	public static final AdMessageKey MSG_CU_LABEL_NO_FORMAT = AdMessageKey.of("de.metas.handlingunits.picking.job.CU_LABEL_NO_FORMAT");

	@NonNull private final PickingJobHUService huService;

	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public static PickingCULabelService newInstanceForUnitTesting()
	{
		SpringContextHolder.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(
				PickingCULabelService.class,
				() -> new PickingCULabelService(PickingJobHUService.newInstanceForUnitTesting()));
	}

	public ResolvedCULabel resolve(
			@NonNull final PickingJobLine line,
			@NonNull final ScannedCode label,
			@NonNull final CUQtyEntryPolicy policy,
			@NonNull final Optional<ScannableCodeFormatId> formatId)
	{
		final ScannableCodeFormat format = getFormat(formatId);

		final ExplainedOptional<ParsedScannedCode> parsed = format.parse(label);
		if (parsed.isPresent())
		{
			return resolve(line, toCULabelData(parsed.get()), policy);
		}

		logger.warn("CU label refused: label '{}' does not match format {}: {}", label, format.getId(), parsed.getExplanation().getDefaultValue());
		throw userErrorWithDetails(MSG_CU_LABEL_FORMAT_MISMATCH, parsed.getExplanation());
	}

	@NonNull
	private ScannableCodeFormat getFormat(@NonNull final Optional<ScannableCodeFormatId> formatId)
	{
		final ScannableCodeFormatId id = formatId.orElseThrow(() -> userError(MSG_CU_LABEL_NO_FORMAT));
		return huService.getScannableCodeFormat(id)
				.orElseThrow(() -> {
					logger.warn("CU label refused: label format {} not found or inactive", id.getRepoId());
					return userError(MSG_CU_LABEL_NO_FORMAT);
				});
	}

	ResolvedCULabel resolve(
			@NonNull final PickingJobLine line,
			@NonNull final CULabelData labelData,
			@NonNull final CUQtyEntryPolicy policy)
	{
		if (labelData.getProductNo() == null)
		{
			logger.warn("CU label refused: the format reads no product number (no ProductCode part): {}, line {}", labelData, line.getId());
			throw userError(MSG_CU_LABEL_FORMAT_MISMATCH);
		}
		if (!Objects.equals(labelData.getProductNo(), line.getProductNo()))
		{
			logger.warn("CU label refused: other product than the line's {}: {}, line {}", line.getProductNo(), labelData, line.getId());
			throw userError(MobileQRCodeMessages.HU_PRODUCT_NOT_MATCHING, line.getProductValueAndName().getName());
		}

		final boolean isPackIdRequired = policy.isPackIdRequired();
		final String packId = isPackIdRequired ? labelData.getPackId() : null;
		if (isPackIdRequired && packId == null)
		{
			logger.warn("CU label refused: the label has no pack ID: {}, line {}", labelData, line.getId());
			throw userError(MSG_CU_LABEL_NO_PACK_ID);
		}

		return ResolvedCULabel.builder()
				.productId(line.getProductId())
				.lotNo(labelData.getLotNo())
				.bestBeforeDate(labelData.getBestBeforeDate())
				.packId(packId)
				.build();
	}

	private CULabelData toCULabelData(@NonNull final ParsedScannedCode parsed)
	{
		return CULabelData.builder()
				.productNo(parsed.getProductNo())
				.lotNo(parsed.getLotNo())
				.bestBeforeDate(parsed.getBestBeforeDate())
				.packId(parsed.getPackId())
				.build();
	}

	private static AdempiereException userError(@NonNull final AdMessageKey messageKey, final Object... params)
	{
		return new AdempiereException(messageKey, params).markAsUserValidationError();
	}

	private AdempiereException userErrorWithDetails(@NonNull final AdMessageKey messageKey, @NonNull final ITranslatableString details)
	{
		final String errorCode = msgBL.getErrorCode(messageKey);
		return new AdempiereException(
				TranslatableStrings.builder().append(TranslatableStrings.adMessage(messageKey)).append(" ").append(details).build(),
				errorCode != null ? errorCode : messageKey.toAD_Message())
				.markAsUserValidationError();
	}
}
