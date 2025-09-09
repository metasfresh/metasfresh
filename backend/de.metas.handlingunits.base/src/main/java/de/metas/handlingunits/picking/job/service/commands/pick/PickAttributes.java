package de.metas.handlingunits.picking.job.service.commands.pick;

import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.i18n.AdMessageKey;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
class PickAttributes
{
	private final static AdMessageKey ERR_NegativeCatchWeight = AdMessageKey.of("de.metas.handlingunits.picking.job.NEGATIVE_CATCH_WEIGHT_ERROR_MSG");
	private final static AdMessageKey ERR_LMQ_CatchWeightNotMatching = AdMessageKey.of("de.metas.handlingunits.picking.job.CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG");

	@Nullable Quantity catchWeight;

	boolean isSetBestBeforeDate;
	@Nullable LocalDate bestBeforeDate;

	boolean isSetProductionDate;
	@Nullable LocalDate productionDate;

	boolean isSetLotNo;
	@Nullable String lotNo;

	@Builder(toBuilder = true)
	private PickAttributes(
			@Nullable final Quantity catchWeight,
			final boolean isSetBestBeforeDate,
			@Nullable final LocalDate bestBeforeDate,
			final boolean isSetProductionDate,
			@Nullable final LocalDate productionDate,
			final boolean isSetLotNo,
			@Nullable final String lotNo)
	{
		if (catchWeight != null && catchWeight.signum() <= 0)
		{
			throw new AdempiereException(ERR_NegativeCatchWeight);
		}

		this.catchWeight = catchWeight;

		this.isSetBestBeforeDate = isSetBestBeforeDate;
		this.bestBeforeDate = bestBeforeDate;

		this.isSetProductionDate = isSetProductionDate;
		this.productionDate = productionDate;

		this.isSetLotNo = isSetLotNo;
		this.lotNo = StringUtils.trimBlankToNull(lotNo);
	}

	public static PickAttributes ofHUQRCode(@NonNull final IHUQRCode pickFromHUQRCode, @Nullable final UomId catchWeightUomId)
	{
		final PickAttributesBuilder builder = builder();

		final BigDecimal catchWeightBD = pickFromHUQRCode.getWeightInKg().orElse(null);
		if (catchWeightBD != null && catchWeightUomId != null)
		{
			builder.catchWeight(Quantitys.of(catchWeightBD, catchWeightUomId));
		}

		pickFromHUQRCode.getBestBeforeDate().ifPresent(bestBeforeDate -> builder.isSetBestBeforeDate(true).bestBeforeDate(bestBeforeDate));
		pickFromHUQRCode.getProductionDate().ifPresent(productionDate -> builder.isSetProductionDate(true).productionDate(productionDate));
		pickFromHUQRCode.getLotNumber().ifPresent(lotNo -> builder.isSetLotNo(true).lotNo(lotNo));
		return builder.build();
	}

	public PickAttributes fallbackTo(@NonNull final PickAttributes fallback)
	{
		final PickAttributesBuilder builder = toBuilder();
		boolean changed = false;
		if (catchWeight == null && fallback.catchWeight != null)
		{
			builder.catchWeight(fallback.catchWeight);
			changed = true;
		}

		if (!isSetBestBeforeDate && fallback.isSetBestBeforeDate)
		{
			builder.isSetBestBeforeDate(true).bestBeforeDate(fallback.bestBeforeDate);
			changed = true;
		}
		if (!isSetProductionDate && fallback.isSetProductionDate)
		{
			builder.isSetProductionDate(true).productionDate(fallback.productionDate);
			changed = true;
		}
		if (!isSetLotNo && fallback.isSetLotNo)
		{
			builder.isSetLotNo(true).lotNo(fallback.lotNo);
			changed = true;
		}

		return changed ? builder.build() : this;
	}
}
