package de.metas.order.compensationGroup.calibration;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/** Active calibration rules, ordered by SeqNo, then by ID. */
@EqualsAndHashCode
@ToString
public final class CalibrationRules
{
	private static final Comparator<CalibrationRule> ORDER = Comparator
			.comparingInt(CalibrationRule::getSeqNo)
			.thenComparingInt(rule -> CalibrationRuleId.toRepoId(rule.getId()));

	private final ImmutableList<CalibrationRule> rules;

	public CalibrationRules(@NonNull final List<CalibrationRule> rules)
	{
		this.rules = rules.stream().sorted(ORDER).collect(ImmutableList.toImmutableList());
	}

	@VisibleForTesting
	List<CalibrationRule> asList()
	{
		return rules;
	}

	public Optional<CalibrationRule> findFirstMatching(@NonNull final CalibrationMatchKey key)
	{
		return rules.stream().filter(rule -> rule.appliesTo(key)).findFirst();
	}
}
