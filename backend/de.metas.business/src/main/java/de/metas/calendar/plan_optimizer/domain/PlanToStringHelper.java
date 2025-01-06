package de.metas.calendar.plan_optimizer.domain;

import ai.timefold.solver.core.api.score.ScoreExplanation;
import ai.timefold.solver.core.api.score.buildin.bendable.BendableScore;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.project.ProjectId;
import de.metas.resource.ResourceAvailabilityRange;
import de.metas.resource.ResourceAvailabilityRanges;
import lombok.NonNull;
import org.threeten.extra.YearWeek;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

final class PlanToStringHelper
{
	@NonNull private final Plan plan;

	private PlanToStringHelper(@NonNull final Plan plan)
	{
		this.plan = plan;
	}

	public static PlanToStringHelper of(@NonNull final Plan plan)
	{
		return new PlanToStringHelper(plan);
	}

	@Override
	public String toString()
	{
		// NOTE: keep it concise, important for timefold troubleshooting
		final StringBuilder sb = new StringBuilder();
		sb.append(nl())
				.append("simulationId: ").append(plan.getSimulationId())
				.append(nl())
				.append("Plan score: ").append(plan.getScore())
				.append(", Time spent: ").append(plan.getTimeSpent() != null ? toString(plan.getTimeSpent()) : null)
				.append(", IsFinalSolution=").append(plan.isFinalSolution());

		final ArrayList<StepAllocation> stepsList = plan.getStepsList();
		if (stepsList != null && !stepsList.isEmpty())
		{
			final ImmutableListMultimap<ProjectId, StepAllocation> stepsByProjectId = Multimaps.index(stepsList, StepAllocation::getProjectId);
			for (final ProjectId projectId : stepsByProjectId.keySet())
			{
				final StepAllocation firstStep = stepsByProjectId.get(projectId).get(0);

				sb.append(nl())
						.append("P").append(projectId.getRepoId())
						.append(" ").append(firstStep.getStepDef().getProjectPriority())
						.append(" startDateMin=").append(firstStep.getStartDateMin())
						.append(" dueDate=").append(firstStep.getStepDef().getDueDate());

				stepsByProjectId.get(projectId).forEach(step -> appendStep(sb, 1, step));
			}
		}
		else
		{
			sb.append("\n\t(No steps)");
		}

		final ScoreExplanation<Plan, BendableScore> scoreExplanation = plan.getScoreExplanation();
		if (scoreExplanation != null)
		{
			sb.append("\n").append(scoreExplanation.getSummary());
		}

		return sb.toString();
	}

	@SuppressWarnings("SameParameterValue")
	private static void appendStep(final StringBuilder sb, final int indentation, final StepAllocation step)
	{
		sb.append(nl(indentation)).append(step);

		final String resourcePrefix = rightAlign(step.getStepDef().getResource().toString(), 6) + " ";
		final String hrPrefix = rightAlign("HR" + step.getHumanResourceId(), 6) + " ";

		Optional.ofNullable(step.getResourceScheduledRange())
				.stream()
				.flatMap(ResourceAvailabilityRanges::stream)
				.forEach(range -> appendRange(sb, indentation + 1, resourcePrefix, range));

		Optional.ofNullable(step.getHumanResourceScheduledRange())
				.stream()
				.flatMap(ResourceAvailabilityRanges::stream)
				.forEach(range -> appendRange(sb, indentation + 1, hrPrefix, range));
	}

	private static void appendRange(final StringBuilder sb, final int indentation, final String prefix, final ResourceAvailabilityRange range)
	{
		final LocalDateTime startDate = range.getStartDate();
		final LocalDateTime endDate = range.getEndDate();
		final Duration duration = range.getDuration();

		sb.append(nl(indentation)).append(prefix)
				.append(toString(startDate, endDate))
				.append(" (WK").append(YearWeek.from(startDate).getWeek()).append(")")
				.append(": ")
				.append("duration=").append(toHoursString(duration))
		;
	}

	private static String nl() {return nl(0);}

	private static String nl(final int indentation) {return "\n" + "    ".repeat(indentation);}

	@SuppressWarnings("SameParameterValue")
	private static String rightAlign(@NonNull final String string, final int resultLength)
	{
		final int length = string.length();
		if (length >= resultLength)
		{
			return string;
		}

		return " ".repeat(resultLength - length) + string;
	}

	public static String toString(@Nullable LocalDateTime startDate, @Nullable LocalDateTime endDate)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(startDate).append(" -> ");

		if (startDate != null && endDate != null && startDate.toLocalDate().equals(endDate.toLocalDate()))
		{
			sb.append(endDate.toLocalTime());
		}
		else
		{
			sb.append(endDate);
		}

		return sb.toString();
	}

	public static String toHoursString(@NonNull final Duration duration)
	{
		return duration.toHours() + "h";
	}

	public static String toString(@NonNull final Duration duration)
	{
		long seconds = duration.toSeconds();

		long minutes = seconds / 60;
		seconds -= minutes * 60;

		long hours = minutes / 60;
		minutes -= hours * 60;

		long days = hours / 24;
		hours -= days * 24;

		StringBuilder sb = new StringBuilder();
		if (days != 0)
		{
			sb.append(days).append("d");
		}
		if (hours != 0)
		{
			sb.append(hours).append("h");
		}
		if (minutes != 0)
		{
			sb.append(minutes).append("min");
		}
		if (seconds != 0)
		{
			sb.append(seconds).append("s");
		}

		if (sb.isEmpty())
		{
			sb.append("0");
		}

		return sb.toString();
	}
}
