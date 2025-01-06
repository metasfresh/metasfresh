package de.metas.calendar.plan_optimizer.solver;

import ai.timefold.solver.core.api.score.buildin.bendable.BendableScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintCollectors;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import ai.timefold.solver.core.api.score.stream.bi.BiJoiner;
import ai.timefold.solver.core.impl.score.stream.JoinerSupport;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.StepAllocation;
import de.metas.project.InternalPriority;
import de.metas.util.Check;

import java.time.LocalDateTime;

// TODO handle pinned steps!

public class PlanConstraintProvider implements ConstraintProvider
{
	public static final int HARD_LEVELS_SIZE = 2;
	public static final int SOFT_LEVELS_SIZE = 4;

	private static final BendableScore ONE_HARD_1 = BendableScore.ofHard(HARD_LEVELS_SIZE, SOFT_LEVELS_SIZE, 0, 1);
	private static final BendableScore ONE_HARD_2 = BendableScore.ofHard(HARD_LEVELS_SIZE, SOFT_LEVELS_SIZE, 1, 1);
	private static final BendableScore ONE_SOFT_1 = BendableScore.ofSoft(HARD_LEVELS_SIZE, SOFT_LEVELS_SIZE, 0, 1);
	private static final BendableScore ONE_SOFT_2 = BendableScore.ofSoft(HARD_LEVELS_SIZE, SOFT_LEVELS_SIZE, 1, 1);
	private static final BendableScore ONE_SOFT_3 = BendableScore.ofSoft(HARD_LEVELS_SIZE, SOFT_LEVELS_SIZE, 2, 1);
	private static final BendableScore ONE_SOFT_4 = BendableScore.ofSoft(HARD_LEVELS_SIZE, SOFT_LEVELS_SIZE, 3, 1);

	@Override
	public Constraint[] defineConstraints(final ConstraintFactory constraintFactory)
	{
		return new Constraint[] {
				//
				// Hard:
				resourceConflict(constraintFactory),
				startDateMin(constraintFactory),
				dueDate(constraintFactory),
				//humanResourceAvailableCapacity(constraintFactory),
				humanResourceConflict(constraintFactory),
				//
				// Soft:
				penalizeNullableDelay(constraintFactory),
				stepsNotRespectingProjectPriority(constraintFactory),
				firstStepDelayIsMinimum(constraintFactory),
				delayIsMinimum(constraintFactory),
				//minDurationFromEndToDueDateIsMaximum(constraintFactory),
				//sumOfDurationFromEndToDueDateIsMaximum(constraintFactory),
		};
	}

	Constraint resourceConflict(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						StepAllocation.class,
						Joiners.equal(StepAllocation::getResourceId),
						Joiners.overlapping(StepAllocation::getResourceScheduledStartDate, StepAllocation::getResourceScheduledEndDate))
				.penalize(ONE_HARD_1, PlanConstraintProvider::getMachineOverlappingDuration)
				.asConstraint("Resource conflict");
	}

	Constraint startDateMin(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(StepAllocation.class)
				.filter(step -> !step.isStartDateMinRespected())
				.penalize(ONE_HARD_1, StepAllocation::getDurationBeforeStartDateMinAsInt)
				.asConstraint("StartDateMin not respected");
	}

	Constraint dueDate(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(StepAllocation.class)
				.filter(StepAllocation::isDueDateNotRespected)
				.penalize(ONE_HARD_1, StepAllocation::getDurationAfterDueAsInt)
				.asConstraint("DueDate not respected");
	}

	Constraint humanResourceConflict(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						StepAllocation.class,
						Joiners.equal(StepAllocation::getHumanResourceId),
						Joiners.overlapping(StepAllocation::getHumanResourceScheduledStartDate, StepAllocation::getHumanResourceScheduledEndDate))
				.penalize(ONE_HARD_2, PlanConstraintProvider::getHumanOverlappingDuration)
				.asConstraint("Human Resource conflict");
	}

	Constraint penalizeNullableDelay(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachIncludingNullVars(StepAllocation.class)
				.filter(step -> step.getDelay() == null || step.getEndDate() == null)
				.penalize(ONE_SOFT_1, step -> step.getProjectPriority().toIntMinorToUrgent() * step.getProjectPriority().toIntMinorToUrgent())
				.asConstraint("nullable delay");
	}

	Constraint firstStepDelayIsMinimum(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(StepAllocation.class)
				.filter(StepAllocation::isFirstStep)
				.penalize(ONE_SOFT_2, StepAllocation::getDelayAsInt)
				.asConstraint("Delay of project first step is minimum");
	}

	Constraint delayIsMinimum(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(StepAllocation.class)
				.filter(stepAlloc -> !stepAlloc.isFirstStep()) // exclude those which are considered by firstStepDelayIsMinimum
				.penalize(ONE_SOFT_3, StepAllocation::getDelayAsInt)
				.asConstraint("Delay is minimum");
	}

	// select from the solution set S the solution s for which the minimum amount |tdi-tei| is maximum
	Constraint minDurationFromEndToDueDateIsMaximum(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(StepAllocation.class)
				.filter(StepAllocation::isScheduled)
				.groupBy(ConstraintCollectors.min(StepAllocation::getDurationFromEndToDueDateInHoursAbs))
				.reward(ONE_SOFT_3, durationFromEndToDueDateInHours -> durationFromEndToDueDateInHours)
				.asConstraint("solution for which the minimum of |tdi-tei| is maximum");
	}

	// Choose from Z the solution where the sum of | tdi- tei | is maximum
	Constraint sumOfDurationFromEndToDueDateIsMaximum(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(StepAllocation.class)
				.filter(StepAllocation::isScheduled)
				.groupBy(ConstraintCollectors.sum(StepAllocation::getDurationFromEndToDueDateInHoursAbs))
				.reward(ONE_SOFT_4, sum -> sum)
				.asConstraint("solution for which sum of |tdi-tei| is maximum");
	}

	Constraint stepsNotRespectingProjectPriority(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						StepAllocation.class,
						Joiners.equal(StepAllocation::getResourceId),
						stepsNotRespectingProjectPriority())
				.penalize(ONE_SOFT_2, PlanConstraintProvider::computePenaltyWeight_StepsNotRespectingProjectPriority)
				.asConstraint("Steps not respecting project priority");
	}

	private static BiJoiner<StepAllocation, StepAllocation> stepsNotRespectingProjectPriority() {return JoinerSupport.getJoinerService().newBiJoiner(PlanConstraintProvider::stepsNotRespectingProjectPriority);}

	static boolean stepsNotRespectingProjectPriority(final StepAllocation step1, final StepAllocation step2)
	{
		return computePenaltyWeight_StepsNotRespectingProjectPriority(step1, step2) > 0;
	}

	static int computePenaltyWeight_StepsNotRespectingProjectPriority(final StepAllocation step1, final StepAllocation step2)
	{
		final InternalPriority prio1 = step1.getProjectPriority();
		final InternalPriority prio2 = step2.getProjectPriority();
		if (prio1.equals(prio2))
		{
			return 0;
		}
		else if (prio1.isHigherThan(prio2))
		{
			final LocalDateTime endDate1 = Check.assumeNotNull(step1.getEndDate(), "end date not null: {}", step1);
			final LocalDateTime startDate2 = Check.assumeNotNull(step2.getStartDate(), "start date not null: {}", step2);

			final int duration = (int)Plan.PLANNING_TIME_PRECISION.between(endDate1, startDate2);
			return duration < 0 ? -duration : 0;
		}
		else
		{
			final LocalDateTime endDate2 = Check.assumeNotNull(step2.getEndDate(), "end date not null: {}", step1);
			final LocalDateTime startDate1 = Check.assumeNotNull(step1.getStartDate(), "start date not null: {}", step2);

			final int duration = (int)Plan.PLANNING_TIME_PRECISION.between(endDate2, startDate1);
			return duration < 0 ? -duration : 0;
		}
	}

	static int getMachineOverlappingDuration(final StepAllocation step1, final StepAllocation step2)
	{
		return getOverlappingDuration(step1, step2, true);
	}

	private static int getHumanOverlappingDuration(final StepAllocation step1, final StepAllocation step2)
	{
		return getOverlappingDuration(step1, step2, false);
	}

	private static int getOverlappingDuration(final StepAllocation step1, final StepAllocation step2, boolean isMachine)
	{
		final LocalDateTime step1Start = isMachine ? step1.getResourceScheduledStartDate() : step1.getHumanResourceScheduledStartDate();
		final LocalDateTime step1End = isMachine ? step1.getResourceScheduledEndDate() : step1.getHumanResourceScheduledEndDate();
		final LocalDateTime step2Start = isMachine ? step2.getResourceScheduledStartDate() : step2.getHumanResourceScheduledStartDate();
		final LocalDateTime step2End = isMachine ? step2.getResourceScheduledEndDate() : step2.getHumanResourceScheduledEndDate();

		if (step1Start == null || step1End == null || step2Start == null || step2End == null)
		{
			return 0;
		}

		final LocalDateTime overlappingStart = (step1Start.isAfter(step2Start)) ? step1Start : step2Start; // MAX
		final LocalDateTime overlappingEnd = (step1End.isBefore(step2End)) ? step1End : step2End; // MIN
		return (int)Math.abs(Plan.PLANNING_TIME_PRECISION.between(overlappingStart, overlappingEnd));
	}
}
