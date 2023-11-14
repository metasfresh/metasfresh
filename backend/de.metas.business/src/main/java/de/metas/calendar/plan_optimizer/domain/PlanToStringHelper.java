package de.metas.calendar.plan_optimizer.domain;

import ai.timefold.solver.core.api.score.ScoreExplanation;
import ai.timefold.solver.core.api.score.buildin.bendable.BendableScore;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.project.ProjectId;
import de.metas.resource.ResourceAvailabilityRange;
import de.metas.resource.ResourceAvailabilityRanges;
import lombok.NonNull;

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
		sb.append(nl()).append("\nsimulationId: ").append(plan.getSimulationId());
		sb.append(nl()).append("Plan score: ").append(plan.getScore()).append(", Time spent: ").append(plan.getTimeSpent()).append(", IsFinalSolution=").append(plan.isFinalSolution());

		final ArrayList<Step> stepsList = plan.getStepsList();
		if (stepsList != null && !stepsList.isEmpty())
		{
			final ImmutableListMultimap<ProjectId, Step> stepsByProjectId = Multimaps.index(stepsList, Step::getProjectId);
			for (final ProjectId projectId : stepsByProjectId.keySet())
			{
				final Step firstStep = stepsByProjectId.get(projectId).get(0);

				sb.append(nl())
						.append("P").append(projectId.getRepoId())
						.append(" ").append(firstStep.getProjectPriority())
						.append(" startDateMin=").append(firstStep.getStartDateMin())
						.append(" dueDate=").append(firstStep.getDueDate());

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
	private static void appendStep(final StringBuilder sb, final int identation, final Step step)
	{
		sb.append(nl(identation)).append(step);

		Optional.ofNullable(step.getResourceScheduledRange())
				.stream()
				.flatMap(ResourceAvailabilityRanges::stream)
				.forEach(range -> appendRange(sb, identation + 1, " M ", range));

		Optional.ofNullable(step.getHumanResourceScheduledRange())
				.stream()
				.flatMap(ResourceAvailabilityRanges::stream)
				.forEach(range -> appendRange(sb, identation + 1, "HR ", range));
	}

	private static void appendRange(final StringBuilder sb, final int identation, final String prefix, final ResourceAvailabilityRange range)
	{
		sb.append(nl(identation)).append(prefix)
				.append(range.getStartDate())
				.append(" - ")
				.append(range.getEndDate())
				.append(": ")
				.append("duration=").append(range.getDuration())
		;
	}

	private static String nl() {return nl(0);}

	private static String nl(final int identation) {return "\n" + "    ".repeat(identation);}
}
