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
}
