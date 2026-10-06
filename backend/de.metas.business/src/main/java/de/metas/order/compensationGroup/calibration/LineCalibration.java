package de.metas.order.compensationGroup.calibration;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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

	@Nullable BigDecimal calibratedQty;
	@Nullable BigDecimal uncalibratedQty;

	/** 1 when no rule matched */
	@Nullable BigDecimal factor;

	/** null when no rule matched */
	@Nullable CalibrationRuleId ruleId;
}
