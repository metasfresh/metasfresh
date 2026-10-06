package de.metas.order.compensationGroup.calibration;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

/**
 * Result of {@link CalibratedQtyCalculator#compute}: the quantity after applying the calibration factor and the one without it,
 * both rounded to the UOM precision.
 */
@Value(staticConstructor = "of")
public class CalibratedQty
{
	@NonNull BigDecimal calibrated;
	@NonNull BigDecimal uncalibrated;
}
