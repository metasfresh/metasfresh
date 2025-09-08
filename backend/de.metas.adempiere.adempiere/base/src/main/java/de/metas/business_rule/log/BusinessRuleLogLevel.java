package de.metas.business_rule.log;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public enum BusinessRuleLogLevel
{
	WARN,
	INFO,
	DEBUG,
	;

	public static <T> Optional<BusinessRuleLogLevel> extractMaxLogLevel(final Collection<T> items, final Function<T, BusinessRuleLogLevel> logLevelMapper)
	{
		if (items.isEmpty())
		{
			return Optional.empty();
		}

		return items.stream()
				.map(logLevelMapper)
				.filter(Objects::nonNull)
				.max(Comparator.comparing(BusinessRuleLogLevel::ordinal));
	}

}
