package de.metas.order.compensationGroup.calibration;

import com.google.common.collect.ImmutableMap;
import de.metas.order.compensationGroup.GroupTemplateRegularLineId;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;
import java.util.Optional;

/**
 * Calibration outcomes of a group's template regular lines.
 */
@Value
public class GroupCalibrations
{
	public static final GroupCalibrations NONE = new GroupCalibrations(ImmutableMap.of());

	@NonNull ImmutableMap<GroupTemplateRegularLineId, CalibrationRule> byTemplateLineId;

	public static GroupCalibrations of(@NonNull final Map<GroupTemplateRegularLineId, CalibrationRule> byTemplateLineId)
	{
		return byTemplateLineId.isEmpty() ? NONE : new GroupCalibrations(ImmutableMap.copyOf(byTemplateLineId));
	}

	public Optional<CalibrationRule> getByTemplateLineId(@NonNull final GroupTemplateRegularLineId id)
	{
		return Optional.ofNullable(byTemplateLineId.get(id));
	}
}
