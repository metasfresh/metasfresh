package de.metas.calendar.plan_optimizer.domain;

import de.metas.i18n.BooleanWithReason;
import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.resource.ResourceAvailabilityRanges;
import de.metas.util.time.DurationUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;

@Value
@Builder
public class StepDef
{
	@NonNull StepId id;

	@Nullable @NonFinal @Setter(AccessLevel.PACKAGE) StepDef previous;
	@Nullable @NonFinal @Setter(AccessLevel.PACKAGE) StepDef next;

	@NonNull InternalPriority projectPriority;

	@NonNull Resource resource;
	@NonNull Duration requiredResourceCapacity;
	@NonNull Duration requiredHumanCapacity;

	@NonNull LocalDateTime startDateMin;
	@NonNull @NonFinal @Setter(AccessLevel.PACKAGE) LocalDateTime dueDate; // aka endDateMax
	@Nullable LocalDateTime pinnedStartDate;

	@Nullable LocalDateTime initialStartDate;
	@Nullable LocalDateTime initialEndDate;

	public BooleanWithReason checkProblemFactsValid()
	{
		if (!startDateMin.isBefore(dueDate))
		{
			return BooleanWithReason.falseBecause("StartDateMin shall be before DueDate");
		}
		final Duration durationMax = Duration.between(startDateMin, dueDate);

		if (requiredResourceCapacity.getSeconds() <= 0)
		{
			return BooleanWithReason.falseBecause("Duration must be set and must be positive");
		}
		if (requiredResourceCapacity.compareTo(durationMax) > 0)
		{
			return BooleanWithReason.falseBecause("Duration does not fit into StartDateMin/DueDate interval");
		}

		if (requiredHumanCapacity.getSeconds() > 0 && resource.getHumanResourceCapacity() == null)
		{
			return BooleanWithReason.falseBecause("Human Resource capacity shall be specified when step requires human capacity");
		}

		return BooleanWithReason.TRUE;
	}

	public ResourceId getResourceId() {return resource.getId();}

	public HumanResourceCapacity getHumanResourceCapacity() {return resource.getHumanResourceCapacity();}

	public int computeDelayMax()
	{
		return DurationUtils.toInt(Duration.between(startDateMin, dueDate), Plan.PLANNING_TIME_PRECISION)
				- DurationUtils.toInt(requiredResourceCapacity, Plan.PLANNING_TIME_PRECISION);
	}

	public boolean isRequiredHumanCapacity() {return requiredHumanCapacity.getSeconds() > 0;}

	@Nullable
	public ResourceAvailabilityRanges computeResourceScheduledRange(@NonNull final LocalDateTime startDate)
	{
		return resource.getAvailability().computeAvailabilityRanges(startDate, requiredResourceCapacity).orElse(null);
	}

	@Nullable
	public ResourceAvailabilityRanges computeHumanResourceScheduledRange(@NonNull final LocalDateTime startDate)
	{
		return resource.getHumanResourceAvailability().computeAvailabilityRanges(startDate, requiredHumanCapacity).orElse(null);
	}
}
