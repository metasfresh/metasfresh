package de.metas.order.compensationGroup.calibration;

import de.metas.util.Check;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Scales a compensation-group template quantity by a calibration factor.
 * Rounds HALF_UP to the UOM precision; a non-zero base quantity never calibrates down to zero
 * (floored to the smallest unit of the precision, keeping the sign).
 */
public final class CalibratedQtyCalculator
{
	private CalibratedQtyCalculator() {}

	/**
	 * @param factor must be &gt; 0; a factor of 0 means "skip the line" and is handled by the caller
	 */
	public static CalibratedQty compute(
			@NonNull final BigDecimal templateQty,
			@NonNull final BigDecimal menuQty,
			@NonNull final BigDecimal factor,
			final int uomPrecision)
	{
		Check.assume(factor.signum() > 0, "factor > 0");

		final BigDecimal base = templateQty.multiply(menuQty);
		final BigDecimal uncalibrated = base.setScale(uomPrecision, RoundingMode.HALF_UP);
		BigDecimal calibrated = base.multiply(factor).setScale(uomPrecision, RoundingMode.HALF_UP);
		if (uncalibrated.signum() != 0 && calibrated.signum() == 0)
		{
			calibrated = BigDecimal.ONE.movePointLeft(uomPrecision).multiply(BigDecimal.valueOf(base.signum()));
		}
		return CalibratedQty.of(calibrated, uncalibrated);
	}
}
