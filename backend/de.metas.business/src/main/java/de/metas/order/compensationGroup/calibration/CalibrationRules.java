package de.metas.order.compensationGroup.calibration;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;

/** Active calibration rules, ordered by SeqNo, then by ID. */
@EqualsAndHashCode
@ToString
public final class CalibrationRules
{
	private static final Comparator<CalibrationRule> ORDER = Comparator
			.comparingInt(CalibrationRule::getSeqNo)
			.thenComparingInt(rule -> rule.getId().getRepoId());

	private final ImmutableList<CalibrationRule> rules;

	public CalibrationRules(@NonNull final List<CalibrationRule> rules)
	{
		this.rules = rules.stream().sorted(ORDER).collect(ImmutableList.toImmutableList());
	}

	public List<CalibrationRule> asList()
	{
		return rules;
	}
}
