package de.metas.money;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;

@EqualsAndHashCode
public final class MixedMoney
{
	public static final MixedMoney EMPTY = new MixedMoney(ImmutableMap.of());

	@NonNull private final ImmutableMap<CurrencyId, Money> map;

	private MixedMoney(@NonNull final Map<CurrencyId, Money> map)
	{
		this.map = ImmutableMap.copyOf(map);
	}

	public static Collector<Money, ?, MixedMoney> collectAndSum()
	{
		return GuavaCollectors.collectUsingListAccumulator(MixedMoney::sumOf);
	}

	public static MixedMoney sumOf(@NonNull final Collection<Money> collection)
	{
		if (collection.isEmpty())
		{
			return MixedMoney.EMPTY;
		}

		final HashMap<CurrencyId, Money> map = new HashMap<>();
		for (final Money money : collection)
		{
			map.compute(money.getCurrencyId(), (currencyId, currentMoney) -> currentMoney == null ? money : currentMoney.add(money));
		}

		return new MixedMoney(map);
	}

	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(map.values())
				.toString();
	}

	public Optional<Money> toNoneOrSingleValue()
	{
		if (map.isEmpty())
		{
			return Optional.empty();
		}
		else if (map.size() == 1)
		{
			return map.values().stream().findFirst();
		}
		else
		{
			throw new AdempiereException("Expected none or single value but got many: " + map.values());
		}
	}
}
