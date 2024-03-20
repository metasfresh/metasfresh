package de.metas.calendar.plan_optimizer.domain;

import de.metas.i18n.BooleanWithReason;
import de.metas.product.ResourceId;
import de.metas.project.InternalPriority;
import de.metas.resource.ResourceAvailabilityRanges;
import de.metas.resource.ResourceWeeklyAvailability;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;

@Value
@Builder
@ToString(exclude = { "previous", "next" })
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

		if (requiredHumanCapacity.getSeconds() > 0 && resource.getHumanResource() == null)
		{
			return BooleanWithReason.falseBecause("Human Resource capacity shall be specified when step requires human capacity");
		}

		return BooleanWithReason.TRUE;
	}

	public ResourceId getResourceId() {return resource.getId();}

	@Nullable
	public HumanResourceId getHumanResourceId() {return resource.getHumanResourceId();}

	public Duration computeDelayMax()
	{
		// Estimate how much a step could take at least
		// i.e. the maxium of resource capacity and human capacity required
		final Duration minStepDuration = TimeUtil.maxNotNull(requiredResourceCapacity, requiredHumanCapacity);

		final Duration maxAllowedStepDuration = Duration.between(startDateMin, dueDate);
		return maxAllowedStepDuration.minus(minStepDuration);
	}

	public boolean isRequiredHumanCapacity() {return requiredHumanCapacity.getSeconds() > 0;}

	@Nullable
	public ResourceAvailabilityRanges computeResourceScheduledRange(@NonNull final LocalDateTime startDate)
	{
		// IMPORTANT: step shall start when both machine & resource are available
		final LocalDateTime startDateEffective = findResourceAndHumanCommonStartDate(startDate);
		return resource.getAvailability().computeAvailabilityRanges(startDateEffective, requiredResourceCapacity).orElse(null);
	}

	@Nullable
	public ResourceAvailabilityRanges computeHumanResourceScheduledRange(@NonNull final LocalDateTime startDate)
	{
		final HumanResource humanResource = resource.getHumanResource();
		if (humanResource == null)
		{
			return null;
		}

		// IMPORTANT: step shall start when both machine & resource are available
		final LocalDateTime startDateEffective = findResourceAndHumanCommonStartDate(startDate);
		return humanResource.getAvailability().computeAvailabilityRanges(startDateEffective, requiredHumanCapacity).orElse(null);
	}

	private LocalDateTime findResourceAndHumanCommonStartDate(@NonNull final LocalDateTime startDate)
	{
		ResourceWeeklyAvailability availability = resource.getAvailability();

		final HumanResource humanResource = resource.getHumanResource();
		if (humanResource != null)
		{
			availability = availability.intersectWith(humanResource.getAvailability());
		}

		return availability.findNextSlotStart(startDate);
	}
}
