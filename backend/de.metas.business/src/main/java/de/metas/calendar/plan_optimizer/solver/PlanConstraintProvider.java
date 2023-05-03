package de.metas.calendar.plan_optimizer.solver;

import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.calendar.plan_optimizer.solver.weekly_capacities.AvailableCapacity;
import de.metas.calendar.plan_optimizer.solver.weekly_capacities.StepRequiredCapacity;
import de.metas.project.InternalPriority;
import de.metas.resource.HumanResourceTestGroupService;
import de.metas.util.Check;
import de.metas.util.time.DurationUtils;
import org.compiere.SpringContextHolder;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import org.optaplanner.core.api.score.stream.bi.BiJoiner;
import org.optaplanner.core.impl.score.stream.JoinerSupport;

import java.time.LocalDateTime;

public class PlanConstraintProvider implements ConstraintProvider
{
	public static final int HARD_LEVELS_SIZE = 2;
	public static final int SOFT_LEVELS_SIZE = 3;

	private static final BendableScore ONE_HARD = BendableScore.of(new int[] { 1, 0 }, new int[] { 0, 0, 0 });
	private static final BendableScore ONE_HARD_2 = BendableScore.of(new int[] { 0, 1 }, new int[] { 0, 0, 0 });
	private static final BendableScore ONE_SOFT_1 = BendableScore.of(new int[] { 0, 0 }, new int[] { 1, 0, 0 });
	private static final BendableScore ONE_SOFT_2 = BendableScore.of(new int[] { 0, 0 }, new int[] { 0, 1, 0 });
	private static final BendableScore ONE_SOFT_3 = BendableScore.of(new int[] { 0, 0 }, new int[] { 0, 0, 1 });

	@Override
	public Constraint[] defineConstraints(final ConstraintFactory constraintFactory)
	{
		return new Constraint[] {
				// Hard:
				resourceConflict(constraintFactory),
				startDateMin(constraintFactory),
				dueDate(constraintFactory),
				availableCapacity(constraintFactory),
				// Soft:
				stepsNotRespectingProjectPriority(constraintFactory),
				delayIsMinimum(constraintFactory),
				minDurationFromEndToDueDateIsMaximum(constraintFactory),
				sumOfDurationFromEndToDueDateIsMaximum(constraintFactory),
		};
	}

	Constraint resourceConflict(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						Step.class,
						Joiners.equal(Step::getResource),
						stepsOverlapping())
				.penalize(ONE_HARD, PlanConstraintProvider::getOverlappingDuration)
				.asConstraint("Resource conflict");
	}

	private static BiJoiner<Step, Step> stepsOverlapping() {return Joiners.overlapping(Step::getStartDate, Step::getEndDate);}

	Constraint startDateMin(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.filter(step -> !step.isStartDateMinRespected())
				.penalize(ONE_HARD, Step::getDurationBeforeStartDateMinAsInt)
				.asConstraint("StartDateMin not respected");
	}

	Constraint dueDate(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.filter(Step::isDueDateNotRespected)
				.penalize(ONE_HARD, Step::getDurationAfterDueAsInt)
				.asConstraint("DueDate not respected");
	}

	Constraint availableCapacity(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.groupBy(ConstraintCollectors.sum(
						StepRequiredCapacity::ofStep,
						StepRequiredCapacity.ZERO,
						StepRequiredCapacity::add,
						StepRequiredCapacity::subtract))
				.penalize(ONE_HARD_2, this::computePenaltyWeight_availableCapacity)
				.asConstraint("Available human resource test group capacity");
	}

	private int computePenaltyWeight_availableCapacity(StepRequiredCapacity requiredCapacity)
	{
		final HumanResourceTestGroupService humanResourceTestGroupService = SpringContextHolder.instance.getBean(HumanResourceTestGroupService.class);

		final AvailableCapacity availableCapacity = AvailableCapacity.of(humanResourceTestGroupService.getAll());
		availableCapacity.reserveCapacity(requiredCapacity);
		return DurationUtils.toInt(availableCapacity.getOverReservedCapacity(), Plan.PLANNING_TIME_PRECISION);
	}

	Constraint delayIsMinimum(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.filter(step -> step.getDelay() > 0)
				.penalize(ONE_SOFT_1, Step::getDelay)
				.asConstraint("Delay is minimum");
	}

	// select from the solution set S the solution s for which the minimum amount |tdi-tei| is maximum
	Constraint minDurationFromEndToDueDateIsMaximum(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.groupBy(ConstraintCollectors.min(Step::getDurationFromEndToDueDateInHoursAbs))
				.reward(ONE_SOFT_2, durationFromEndToDueDateInHours -> durationFromEndToDueDateInHours)
				.asConstraint("solution for which the minimum of |tdi-tei| is maximum");
	}

	// Choose from Z the solution where the sum of | tdi- tei | is maximum
	Constraint sumOfDurationFromEndToDueDateIsMaximum(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.groupBy(ConstraintCollectors.sum(Step::getDurationFromEndToDueDateInHoursAbs))
				.reward(ONE_SOFT_3, sum -> sum)
				.asConstraint("solution for which sum of |tdi-tei| is maximum");
	}

	Constraint stepsNotRespectingProjectPriority(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						Step.class,
						Joiners.equal(Step::getResource),
						stepsNotRespectingProjectPriority())
				.penalize(ONE_SOFT_1, PlanConstraintProvider::computePenaltyWeight_StepsNotRespectingProjectPriority)
				.asConstraint("Steps not respecting project priority");
	}

	private static BiJoiner<Step, Step> stepsNotRespectingProjectPriority() {return JoinerSupport.getJoinerService().newBiJoiner(PlanConstraintProvider::stepsNotRespectingProjectPriority);}

	static boolean stepsNotRespectingProjectPriority(Step step1, Step step2)
	{
		return computePenaltyWeight_StepsNotRespectingProjectPriority(step1, step2) > 0;
	}

	static int computePenaltyWeight_StepsNotRespectingProjectPriority(Step step1, Step step2)
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

	private static int getOverlappingDuration(final Step step1, final Step step2)
	{
		final LocalDateTime step1Start = step1.getStartDate();
		final LocalDateTime step1End = step1.getEndDate();
		final LocalDateTime step2Start = step2.getStartDate();
		final LocalDateTime step2End = step2.getEndDate();
		final LocalDateTime overlappingStart = (step1Start.isAfter(step2Start)) ? step1Start : step2Start; // MAX
		final LocalDateTime overlappingEnd = (step1End.isBefore(step2End)) ? step1End : step2End; // MIN
		return (int)Plan.PLANNING_TIME_PRECISION.between(overlappingStart, overlappingEnd);
	}
}
