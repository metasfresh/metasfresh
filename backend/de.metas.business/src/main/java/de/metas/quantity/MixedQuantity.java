package de.metas.quantity;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import de.metas.uom.UomId;
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
public final class MixedQuantity
{
	public static final MixedQuantity EMPTY = new MixedQuantity(ImmutableMap.of());

	@NonNull private final ImmutableMap<UomId, Quantity> map;

	private MixedQuantity(@NonNull final Map<UomId, Quantity> map)
	{
		this.map = ImmutableMap.copyOf(map);
	}

	public static Collector<Quantity, ?, MixedQuantity> collectAndSum()
	{
		return GuavaCollectors.collectUsingListAccumulator(MixedQuantity::sumOf);
	}

	public static MixedQuantity sumOf(@NonNull final Collection<Quantity> collection)
	{
		if (collection.isEmpty())
		{
			return EMPTY;
		}

		final HashMap<UomId, Quantity> map = new HashMap<>();
		for (final Quantity qty : collection)
		{
			map.compute(qty.getUomId(), (currencyId, currentQty) -> currentQty == null ? qty : currentQty.add(qty));
		}

		return new MixedQuantity(map);
	}

	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(map.values())
				.toString();
	}

	public Optional<Quantity> toNoneOrSingleValue()
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
