package de.metas.calendar.plan_optimizer.solver;

import de.metas.calendar.plan_optimizer.domain.Step;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import org.optaplanner.core.api.score.stream.bi.BiJoiner;
import org.optaplanner.core.impl.score.stream.JoinerSupport;

public class PlanConstraintProvider implements ConstraintProvider
{
	@Override
	public Constraint[] defineConstraints(final ConstraintFactory constraintFactory)
	{
		return new Constraint[] {
				resourceConflict(constraintFactory),
				projectStepsOverlap(constraintFactory),
				projectStepInOrder(constraintFactory),
				dueDate(constraintFactory)
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

	Constraint projectStepInOrder(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEachUniquePair(
						Step.class,
						Joiners.equal(Step::getProjectId),
						stepsNotInOrder())
				.penalize(HardSoftScore.ONE_HARD)
				.asConstraint("Project steps not in order");
	}

	private static BiJoiner<Step, Step> stepsOverlapping() {return Joiners.overlapping(Step::getStartDate, Step::getEndDate);}

	private static BiJoiner<Step, Step> stepsNotInOrder() {return JoinerSupport.getJoinerService().newBiJoiner(PlanConstraintProvider::stepsNotInOrder);}

	private static boolean stepsNotInOrder(Step step1, Step step2)
	{
		final boolean startDatesAscending = step1.getStartDate().compareTo(step2.getStartDate()) <= 0;
		final boolean seqNoAscending = step1.getProjectSeqNo() <= step2.getProjectSeqNo();
		return startDatesAscending != seqNoAscending;
	}

	Constraint dueDate(ConstraintFactory constraintFactory)
	{
		return constraintFactory.forEach(Step.class)
				.filter(Step::isDueDateNotRespected)
				.penalize(HardSoftScore.ONE_HARD)
				.asConstraint("DueDate not respected");
	}

}
