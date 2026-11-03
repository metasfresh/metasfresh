package de.metas.order.compensationGroup.calibration;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

/**
 * Active calibration rules, ordered by SeqNo, then by ID.
 */
@EqualsAndHashCode
@ToString
public final class CalibrationRules
{
	// declared before EMPTY: static fields are initialized in textual order and the constructor sorts by ORDER
	private static final Comparator<CalibrationRule> ORDER = Comparator
			.comparingInt(CalibrationRule::getSeqNo)
			.thenComparing(CalibrationRule::getId, Comparator.nullsFirst(Comparator.naturalOrder()));

	public static final CalibrationRules EMPTY = new CalibrationRules(ImmutableList.of());

	private final ImmutableList<CalibrationRule> rules;

	public CalibrationRules(@NonNull final List<CalibrationRule> rules)
	{
		this.rules = rules.stream().sorted(ORDER).collect(ImmutableList.toImmutableList());
	}

	public static CalibrationRules of(@NonNull final List<CalibrationRule> rules)
	{
		return rules.isEmpty() ? EMPTY : new CalibrationRules(rules);
	}

	public static Collector<CalibrationRule, ?, CalibrationRules> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(CalibrationRules::of);
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
