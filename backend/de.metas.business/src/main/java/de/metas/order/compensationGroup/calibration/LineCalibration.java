package de.metas.order.compensationGroup.calibration;

import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * The calibration outcome for one regular line of a compensation group template.
 */
@Value
@Builder
public class LineCalibration
{
	public static final LineCalibration SKIP = builder().skip(true).build();

	/** matched factor 0 -> the line is not created */
	boolean skip;

	@Nullable Quantity calibratedQty;
	@Nullable Quantity uncalibratedQty;

	/** 100% when no rule matched */
	@Nullable Percent factor;

	/** null when no rule matched */
	@Nullable CalibrationRuleId ruleId;
}
