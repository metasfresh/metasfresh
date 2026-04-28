package de.metas.quantity;

import com.google.common.collect.ImmutableMap;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

	public static <T> Collector<T, ?, MixedQuantity> collectAndSum(@NonNull final Function<T, Quantity> qtyExtractor)
	{
		final Supplier<Map<UomId, Quantity>> supplier = HashMap::new;
		final BiConsumer<Map<UomId, Quantity>, T> accumulator = (acc, item) -> {
			final Quantity qty = qtyExtractor.apply(item);
			if (qty != null)
			{
				acc.compute(qty.getUomId(), (uomId, currentQty) -> currentQty == null ? qty : currentQty.add(qty));
			}
		};
		final BinaryOperator<Map<UomId, Quantity>> combiner = (acc1, acc2) -> {
			acc2.forEach((uomId, qty) -> acc1.merge(uomId, qty, Quantity::add));
			return acc1;
		};
		final Function<Map<UomId, Quantity>, MixedQuantity> finisher = acc -> acc.isEmpty() ? EMPTY : new MixedQuantity(acc);

		return Collector.of(supplier, accumulator, combiner, finisher);
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

	@Override
	public String toString()
	{
		if (map.isEmpty())
		{
			return "0";
		}

		return map.values()
				.stream()
				.map(Quantity::toShortString)
				.collect(Collectors.joining("+"));
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

	public MixedQuantity add(@NonNull final Quantity qtyToAdd)
	{
		if (qtyToAdd.isZero())
		{
			return this;
		}

		return new MixedQuantity(
				CollectionUtils.merge(map, qtyToAdd.getUomId(), qtyToAdd, Quantity::add)
		);
	}

	public MixedQuantity subtract(@NonNull final Quantity qtyToAdd)
	{
		return add(qtyToAdd.negate());
	}

	public boolean hasUOM(@NonNull final UomId uomId)
	{
		return map.containsKey(uomId);
	}

	public Quantity getByUOM(@NonNull final UomId uomId)
	{
		final Quantity qty = map.get(uomId);
		return qty != null ? qty : Quantitys.zero(uomId);
	}
}
