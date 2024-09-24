package de.metas.pos;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class POSProductsList
{
	public static final POSProductsList EMPTY = new POSProductsList(ImmutableList.of(), false);

	@NonNull private final ImmutableList<POSProduct> products;
	@Getter private final boolean isBarcodeMatched;

	private POSProductsList(
			@NonNull final ImmutableList<POSProduct> products,
			final boolean isBarcodeMatched)
	{
		this.products = products;
		this.isBarcodeMatched = isBarcodeMatched;
	}

	public static POSProductsList ofList(@NonNull final List<POSProduct> products)
	{
		return !products.isEmpty()
				? new POSProductsList(ImmutableList.copyOf(products), false)
				: EMPTY;
	}

	public static POSProductsList ofBarcodeMatchedProduct(@NonNull final POSProduct product)
	{
		return new POSProductsList(ImmutableList.of(product), true);
	}

	public Stream<POSProduct> stream() {return products.stream();}
}
