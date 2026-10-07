package de.metas.order.compensationGroup.calibration;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.order.compensationGroup.GroupTemplateRegularLineId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Calibration of a group's template regular lines: which lines are calibrated, and the rule matched for each of them.
 */
@Value
public class GroupCalibrations
{
	public static final GroupCalibrations NONE = new GroupCalibrations(ImmutableSet.of(), ImmutableMap.of());

	/**
	 * Template lines that are calibrated, with or without a matching rule; a calibrated line stores its factor (100 when no rule matched).
	 */
	@NonNull ImmutableSet<GroupTemplateRegularLineId> calibratedTemplateLineIds;

	/**
	 * The rule matched per calibrated template line; a calibrated line without a matching rule has no entry.
	 */
	@NonNull ImmutableMap<GroupTemplateRegularLineId, CalibrationRule> byTemplateLineId;

	private GroupCalibrations(
			@NonNull final ImmutableSet<GroupTemplateRegularLineId> calibratedTemplateLineIds,
			@NonNull final ImmutableMap<GroupTemplateRegularLineId, CalibrationRule> byTemplateLineId)
	{
		if (!calibratedTemplateLineIds.containsAll(byTemplateLineId.keySet()))
		{
			throw new AdempiereException("Every template line with a rule must be calibrated")
					.appendParametersToMessage()
					.setParameter("calibratedTemplateLineIds", calibratedTemplateLineIds)
					.setParameter("byTemplateLineId", byTemplateLineId);
		}
		this.calibratedTemplateLineIds = calibratedTemplateLineIds;
		this.byTemplateLineId = byTemplateLineId;
	}

	public static GroupCalibrations of(
			@NonNull final Set<GroupTemplateRegularLineId> calibratedTemplateLineIds,
			@NonNull final Map<GroupTemplateRegularLineId, CalibrationRule> byTemplateLineId)
	{
		return calibratedTemplateLineIds.isEmpty() && byTemplateLineId.isEmpty()
				? NONE
				: new GroupCalibrations(ImmutableSet.copyOf(calibratedTemplateLineIds), ImmutableMap.copyOf(byTemplateLineId));
	}

	public boolean isCalibrated(@NonNull final GroupTemplateRegularLineId id)
	{
		return calibratedTemplateLineIds.contains(id);
	}

	public Optional<CalibrationRule> getByTemplateLineId(@NonNull final GroupTemplateRegularLineId id)
	{
		return Optional.ofNullable(byTemplateLineId.get(id));
	}
}
