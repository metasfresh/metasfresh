package de.metas.calendar.plan_optimizer.solver;

import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.project.InternalPriority;
import de.metas.util.Check;
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
	public static final int HARD_LEVELS_SIZE = 1;
	public static final int SOFT_LEVELS_SIZE = 3;

	private static final BendableScore ONE_HARD = BendableScore.of(new int[] { 1 }, new int[] { 0, 0, 0 });
	private static final BendableScore ONE_SOFT_1 = BendableScore.of(new int[] { 0 }, new int[] { 1, 0, 0 });
	private static final BendableScore ONE_SOFT_2 = BendableScore.of(new int[] { 0 }, new int[] { 0, 1, 0 });
	private static final BendableScore ONE_SOFT_3 = BendableScore.of(new int[] { 0 }, new int[] { 0, 0, 1 });

	@Override
	public Constraint[] defineConstraints(final ConstraintFactory constraintFactory)
	{
		return new Constraint[] {
				// Hard:
				resourceConflict(constraintFactory),
				projectStepsOverlap(constraintFactory),
				projectStepsInOrder(constraintFactory),
				startDateMin(constraintFactory),
				dueDate(constraintFactory),
				// Soft:
				minDurationFromEndToDueDateIsMaximum(constraintFactory),
				sumOfDurationFromEndToDueDateIsMaximum(constraintFactory),
				stepsNotRespectingProjectPriority(constraintFactory),
		};
	}

	Constraint resourceConflict(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						Step.class,
						Joiners.equal(Step::getResource),
						stepsOverlapping())
				.penalize(ONE_HARD.multiply(10))
				.asConstraint("Resource conflict");
	}

	Constraint projectStepsOverlap(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						Step.class,
						Joiners.equal(Step::getProjectId),
						stepsOverlapping())
				.penalize(ONE_HARD.multiply(10))
				.asConstraint("Project steps overlap");
	}

	private static BiJoiner<Step, Step> stepsOverlapping() {return Joiners.overlapping(Step::getStartDate, Step::getEndDate);}

	Constraint projectStepsInOrder(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						Step.class,
						Joiners.equal(Step::getProjectId),
						projectStepsNotInOrder())
				.penalize(ONE_HARD.multiply(10))
				.asConstraint("Project steps not in order");
	}

	private static BiJoiner<Step, Step> projectStepsNotInOrder() {return JoinerSupport.getJoinerService().newBiJoiner(PlanConstraintProvider::projectStepsNotInOrder);}

	public static boolean projectStepsNotInOrder(Step step1, Step step2)
	{
		//noinspection DataFlowIssue
		final boolean startDatesAscending = isAscending(step1.getStartDate(), step2.getStartDate());
		final boolean seqNoAscending = step1.getProjectSeqNo() <= step2.getProjectSeqNo();
		return startDatesAscending != seqNoAscending;
	}

	private static boolean isAscending(final LocalDateTime date1, final LocalDateTime date2) {return !date1.isAfter(date2);}

	Constraint startDateMin(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.filter(step -> !step.isStartDateMinRespected())
				.penalize(ONE_HARD.multiply(13))
				.asConstraint("StartDateMin not respected");
	}

	Constraint dueDate(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.filter(Step::isDueDateNotRespected)
				.penalize(ONE_HARD.multiply(5))
				.asConstraint("DueDate not respected");
	}

	// select from the solution set S the solution s for which the minimum amount |tdi-tei| is maximum
	Constraint minDurationFromEndToDueDateIsMaximum(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.groupBy(ConstraintCollectors.min(Step::getDurationFromEndToDueDateInHoursAbs))
				.reward(ONE_SOFT_1, durationFromEndToDueDateInHours -> durationFromEndToDueDateInHours)
				.asConstraint("solution for which the minimum of |tdi-tei| is maximum");
	}

	// Choose from Z the solution where the sum of | tdi- tei | is maximum
	Constraint sumOfDurationFromEndToDueDateIsMaximum(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.groupBy(ConstraintCollectors.sum(Step::getDurationFromEndToDueDateInHoursAbs))
				.reward(ONE_SOFT_2, sum -> sum)
				.asConstraint("solution for which sum of |tdi-tei| is maximum");
	}

	Constraint stepsNotRespectingProjectPriority(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						Step.class,
						Joiners.equal(Step::getResource),
						stepsNotRespectingProjectPriority())
				.penalize(ONE_SOFT_3)
				.asConstraint("Steps not respecting project priority");
	}

	private static BiJoiner<Step, Step> stepsNotRespectingProjectPriority() {return JoinerSupport.getJoinerService().newBiJoiner(PlanConstraintProvider::stepsNotRespectingProjectPriority);}

	static boolean stepsNotRespectingProjectPriority(Step step1, Step step2)
	{
		final InternalPriority prio1 = step1.getProjectPriority();
		final InternalPriority prio2 = step2.getProjectPriority();
		if (prio1.equals(prio2))
		{
			return false;
		}

		final LocalDateTime startDate1 = Check.assumeNotNull(step1.getStartDate(), "start date not null: {}", step1);
		final LocalDateTime startDate2 = Check.assumeNotNull(step2.getStartDate(), "start date not null: {}", step2);

		final int startDatesDirection = Integer.signum(startDate1.compareTo(startDate2));
		final int priorityDirection = Integer.signum(prio1.getIntValue() - prio2.getIntValue()) * -1;
		return startDatesDirection != priorityDirection;
	}
}
