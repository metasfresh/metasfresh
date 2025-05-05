package de.metas.pricing.service;

import com.google.common.collect.ImmutableList;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class ScaleProductPriceList
{
	public static final ScaleProductPriceList EMPTY = new ScaleProductPriceList(ImmutableList.of());

	private final ImmutableList<ScaleProductPrice> listOrderedByQtyDescending;

	private ScaleProductPriceList(@NonNull final List<ScaleProductPrice> list)
	{
		this.listOrderedByQtyDescending = list.stream()
				.sorted(Comparator.comparing(ScaleProductPrice::getQuantityMin).reversed())
				.collect(ImmutableList.toImmutableList());
	}

	public static ScaleProductPriceList ofList(@NonNull final List<ScaleProductPrice> list)
	{
		return !list.isEmpty() ? new ScaleProductPriceList(list) : EMPTY;
	}

	public static Collector<ScaleProductPrice, ?, ScaleProductPriceList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(ScaleProductPriceList::ofList);
	}

	public boolean isEmpty() {return listOrderedByQtyDescending.isEmpty();}

	public Stream<ScaleProductPrice> stream()
	{
		return listOrderedByQtyDescending.stream();
	}

	public Optional<ScaleProductPrice> getByQuantity(@NonNull final BigDecimal qty)
	{
		return listOrderedByQtyDescending.stream()
				.filter(scale -> scale.isMatching(qty))
				.findFirst();
	}
}
