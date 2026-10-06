package de.metas.order.compensationGroup.calibration;

import de.metas.order.compensationGroup.GroupTemplate;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CompensationGroupCalibrationService
{
	/** Skeleton: matching of calibration rules is added later; until then nothing is calibrated. */
	public GroupCalibrations computeCalibrations(
			@NonNull final I_C_Order order,
			@NonNull final GroupTemplate template,
			@NonNull final BigDecimal qtyMultiplier)
	{
		return GroupCalibrations.NONE;
	}
}
