package de.metas.order.compensationGroup.calibration;

import com.google.common.collect.ImmutableMap;
import de.metas.order.compensationGroup.GroupTemplateRegularLineId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;
import java.util.Optional;

/**
 * The calibration rule matched per template regular line of a group; a line without a matching rule is not calibrated.
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupCalibrations
{
	public static final GroupCalibrations NONE = new GroupCalibrations(ImmutableMap.of());

	@NonNull ImmutableMap<GroupTemplateRegularLineId, CalibrationRule> byTemplateLineId;

	public static GroupCalibrations of(@NonNull final Map<GroupTemplateRegularLineId, CalibrationRule> byTemplateLineId)
	{
		return byTemplateLineId.isEmpty()
				? NONE
				: new GroupCalibrations(ImmutableMap.copyOf(byTemplateLineId));
	}

	public Optional<CalibrationRule> getByTemplateLineId(@NonNull final GroupTemplateRegularLineId id)
	{
		return Optional.ofNullable(byTemplateLineId.get(id));
	}
}
