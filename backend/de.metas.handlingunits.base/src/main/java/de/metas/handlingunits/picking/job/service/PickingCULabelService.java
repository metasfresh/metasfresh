package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryPolicy;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ParsedScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
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
		if (policy.isPackIdRequired() && format.isEndingBeforePackId(label))
		{
			throw userError(MSG_CU_LABEL_NO_PACK_ID);
		}
		throw userErrorWithDetails(MSG_CU_LABEL_FORMAT_MISMATCH, parsed.getExplanation());
	}

	@NonNull
	private ScannableCodeFormat getFormat(@NonNull final Optional<ScannableCodeFormatId> formatId)
	{
		final ScannableCodeFormatId id = formatId.orElseThrow(() -> userError(MSG_CU_LABEL_NO_FORMAT));
		return scannableCodeFormatService.getById(id)
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

	public CULabelData toCULabelData(@NonNull final ParsedScannedCode parsed)
	{
		return CULabelData.builder()
				.productNo(parsed.getProductNo())
				.lotNo(parsed.getLotNo())
				.bestBeforeDate(parsed.getBestBeforeDate())
				.packId(parsed.getPackId())
				.build();
	}

	/** @return true if one of the job's picked HUs of that product carries this lot and pack ID */
	public boolean isAlreadyPickedInJob(
			@NonNull final PickingJob job,
			@NonNull final ProductId productId,
			@Nullable final String lotNo,
			@NonNull final String packId)
	{
		final ImmutableSet<HuId> pickedHUIds = job.getPickedHuIdsOfProduct(productId);
		if (pickedHUIds.isEmpty())
		{
			return false;
		}

		// the picked HUs are CUs inside TUs: not top level
		final ImmutableSet<HuId> huIdsWithPackId = handlingUnitsBL.createHUQueryBuilder()
				.setOnlyTopLevelHUs(false)
				.addOnlyHUIds(pickedHUIds)
				.addOnlyWithAttribute(AttributeConstants.PackId, packId)
				.listIds();
		if (huIdsWithPackId.isEmpty())
		{
			return false;
		}

		final IHUContext huContext = huContextFactory.createMutableHUContext();
		final String lotNoNorm = StringUtils.trimBlankToNull(lotNo);
		return handlingUnitsBL.getByIds(huIdsWithPackId).stream()
				.anyMatch(hu -> hasLotNo(huContext, hu, lotNoNorm));
	}

	private static boolean hasLotNo(@NonNull final IHUContext huContext, @NonNull final I_M_HU hu, @Nullable final String lotNoNorm)
	{
		final IAttributeStorage attributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
		final String huLotNo = attributes.hasAttribute(AttributeConstants.ATTR_LotNumber)
				? StringUtils.trimBlankToNull(attributes.getValueAsString(AttributeConstants.ATTR_LotNumber))
				: null;
		return Objects.equals(huLotNo, lotNoNorm);
	}

	private static AdempiereException userError(@NonNull final AdMessageKey messageKey, final Object... params)
	{
		return new AdempiereException(messageKey, params).markAsUserValidationError();
	}

	private static AdempiereException userErrorWithDetails(@NonNull final AdMessageKey messageKey, @NonNull final ITranslatableString details)
	{
		final String errorCode = Services.get(IMsgBL.class).getErrorCode(messageKey);
		return new AdempiereException(
				TranslatableStrings.builder().append(TranslatableStrings.adMessage(messageKey)).append(" ").append(details).build(),
				errorCode != null ? errorCode : messageKey.toAD_Message())
				.markAsUserValidationError();
	}
}
