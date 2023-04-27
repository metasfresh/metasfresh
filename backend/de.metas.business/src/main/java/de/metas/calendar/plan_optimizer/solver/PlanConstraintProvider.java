package de.metas.calendar.plan_optimizer.solver;

import de.metas.calendar.plan_optimizer.domain.Step;
import de.metas.project.InternalPriority;
import de.metas.util.Check;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
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
	@Override
	public Constraint[] defineConstraints(final ConstraintFactory constraintFactory)
	{
		return new Constraint[] {
				// Hard:
				resourceConflict(constraintFactory),
				projectStepsOverlap(constraintFactory),
				projectStepsInOrder(constraintFactory),
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
				.penalize(HardSoftScore.ONE_HARD)
				.asConstraint("Resource conflict");
	}

	Constraint projectStepsOverlap(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						Step.class,
						Joiners.equal(Step::getProjectId),
						stepsOverlapping())
				.penalize(HardSoftScore.ONE_HARD)
				.asConstraint("Project steps overlap");
	}

	private static BiJoiner<Step, Step> stepsOverlapping() {return Joiners.overlapping(Step::getStartDate, Step::getEndDate);}

	Constraint projectStepsInOrder(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						Step.class,
						Joiners.equal(Step::getProjectId),
						projectStepsNotInOrder())
				.penalize(HardSoftScore.ONE_HARD)
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

	Constraint dueDate(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.filter(Step::isDueDateNotRespected)
				.penalize(HardSoftScore.ONE_HARD)
				.asConstraint("DueDate not respected");
	}

	// select from the solution set S the solution s for which the minimum amount |tdi-tei| is maximum
	Constraint minDurationFromEndToDueDateIsMaximum(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.groupBy(ConstraintCollectors.min(Step::getDurationFromEndToDueDateInHoursAbs))
				.reward(HardSoftScore.ONE_SOFT, durationFromEndToDueDateInHours -> durationFromEndToDueDateInHours)
				.asConstraint("solution for which the minimum of |tdi-tei| is maximum");
	}

	// Choose from Z the solution where the sum of | tdi- tei | is maximum
	Constraint sumOfDurationFromEndToDueDateIsMaximum(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.groupBy(ConstraintCollectors.sum(Step::getDurationFromEndToDueDateInHoursAbs))
				.reward(HardSoftScore.ONE_SOFT, sum -> sum)
				.asConstraint("solution for which sum of |tdi-tei| is maximum");
	}

	Constraint stepsNotRespectingProjectPriority(final ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						Step.class,
						Joiners.equal(Step::getResource),
						stepsNotRespectingProjectPriority())
				.penalize(HardSoftScore.ONE_SOFT)
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
