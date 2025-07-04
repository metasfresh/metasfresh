package de.metas.handlingunits.picking.job.service.commands.pick;

import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.i18n.AdMessageKey;
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

	@Nullable BigDecimal catchWeightBD;
	boolean isSetBestBeforeDate;
	LocalDate bestBeforeDate;
	boolean isSetLotNo;
	String lotNo;

	@Builder(toBuilder = true)
	private PickAttributes(
			@Nullable final BigDecimal catchWeightBD,
			final boolean isSetBestBeforeDate,
			@Nullable final LocalDate bestBeforeDate,
			final boolean isSetLotNo,
			@Nullable final String lotNo)
	{
		if (catchWeightBD != null && catchWeightBD.signum() <= 0)
		{
			throw new AdempiereException(ERR_NegativeCatchWeight);
		}

		this.catchWeightBD = catchWeightBD;
		this.isSetBestBeforeDate = isSetBestBeforeDate;
		this.bestBeforeDate = bestBeforeDate;
		this.isSetLotNo = isSetLotNo;
		this.lotNo = StringUtils.trimBlankToNull(lotNo);
	}

	public static PickAttributes ofHUQRCode(@NonNull final IHUQRCode pickFromHUQRCode)
	{
		final PickAttributesBuilder builder = builder();
		pickFromHUQRCode.getWeightInKg().ifPresent(builder::catchWeightBD);
		pickFromHUQRCode.getBestBeforeDate().ifPresent(bestBeforeDate -> builder.isSetBestBeforeDate(true).bestBeforeDate(bestBeforeDate));
		pickFromHUQRCode.getLotNumber().ifPresent(lotNo -> builder.isSetLotNo(true).lotNo(lotNo));
		return builder.build();
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean hasAttributesToSetExcludingWeight()
	{
		return isSetBestBeforeDate || isSetLotNo;
	}

	public PickAttributes fallbackTo(@NonNull final PickAttributes fallback)
	{
		final PickAttributesBuilder builder = toBuilder();
		boolean changed = false;
		if (catchWeightBD == null && fallback.catchWeightBD != null)
		{
			builder.catchWeightBD(fallback.catchWeightBD);
			changed = true;
		}
		else if (catchWeightBD != null && fallback.catchWeightBD != null && catchWeightBD.compareTo(fallback.catchWeightBD) != 0)
		{
			throw new AdempiereException(ERR_LMQ_CatchWeightNotMatching)
					.appendParametersToMessage()
					.setParameter("CatchWeight1", catchWeightBD)
					.setParameter("CatchWeight2", fallback.catchWeightBD);
		}

		if (!isSetBestBeforeDate && fallback.isSetBestBeforeDate)
		{
			builder.isSetBestBeforeDate(true).bestBeforeDate(fallback.bestBeforeDate);
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
