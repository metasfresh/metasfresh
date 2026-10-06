package de.metas.order.compensationGroup.calibration;

import com.google.common.collect.ImmutableMap;
import de.metas.order.compensationGroup.GroupTemplateRegularLineId;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

/**
 * Calibration outcomes of a group's template regular lines.
 */
@Value(staticConstructor = "of")
public class GroupCalibrations
{
	public static final GroupCalibrations NONE = of(ImmutableMap.of());

	@NonNull ImmutableMap<GroupTemplateRegularLineId, LineCalibration> byTemplateLineId;

	public Optional<LineCalibration> getByTemplateLineId(@NonNull final GroupTemplateRegularLineId id)
	{
		return Optional.ofNullable(byTemplateLineId.get(id));
	}

	/** @return {@code true} if there is at least one entry and all of them are {@link LineCalibration#SKIP} */
	public boolean isAllSkipped()
	{
		return !byTemplateLineId.isEmpty() && byTemplateLineId.values().stream().allMatch(LineCalibration::isSkip);
	}
}
