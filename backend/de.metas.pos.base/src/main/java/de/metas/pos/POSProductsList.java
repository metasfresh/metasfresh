package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class POSProductsList
{
	public static final POSProductsList EMPTY = new POSProductsList(ImmutableList.of());

	@NonNull private final ImmutableList<POSProduct> products;

	private POSProductsList(@NonNull final ImmutableList<POSProduct> products)
	{
		this.products = products;
	}

	public static POSProductsList ofList(@NonNull final List<POSProduct> products)
	{
		return !products.isEmpty()
				? new POSProductsList(ImmutableList.copyOf(products))
				: EMPTY;
	}

	public static Collector<POSProduct, ?, POSProductsList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(POSProductsList::ofList);
	}

	public Stream<POSProduct> stream() {return products.stream();}
}
