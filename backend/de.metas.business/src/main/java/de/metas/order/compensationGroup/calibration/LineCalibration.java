package de.metas.order.compensationGroup.calibration;

import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * The calibration outcome for one regular line of a compensation group template.
 */
@Value
@Builder
public class LineCalibration
{
	@NonNull Quantity calibratedQty;
	@NonNull Quantity uncalibratedQty;

	/** 100% when no rule matched */
	@NonNull Percent factor;

	/** null when no rule matched */
	@Nullable CalibrationRuleId ruleId;
}
