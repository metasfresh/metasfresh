package de.metas.frontend_testing.masterdata.compensation_group;

import de.metas.order.compensationGroup.calibration.CalibrationRuleId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonCalibrationRuleResponse
{
	@NonNull CalibrationRuleId id;
}
