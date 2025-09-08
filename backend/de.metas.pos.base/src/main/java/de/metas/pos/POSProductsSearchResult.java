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
public class POSProductsSearchResult
{
	public static final POSProductsSearchResult EMPTY = new POSProductsSearchResult(ImmutableList.of(), false);

	@NonNull private final ImmutableList<POSProduct> products;
	@Getter private final boolean isBarcodeMatched;

	private POSProductsSearchResult(
			@NonNull final ImmutableList<POSProduct> products,
			final boolean isBarcodeMatched)
	{
		this.products = products;
		this.isBarcodeMatched = isBarcodeMatched;
	}

	public static POSProductsSearchResult ofList(@NonNull final List<POSProduct> products)
	{
		return !products.isEmpty()
				? new POSProductsSearchResult(ImmutableList.copyOf(products), false)
				: EMPTY;
	}

	public static POSProductsSearchResult ofBarcodeMatchedProduct(@NonNull final POSProduct product)
	{
		return new POSProductsSearchResult(ImmutableList.of(product), true);
	}

	public Stream<POSProduct> stream() {return products.stream();}

	public List<POSProduct> toList() {return products;}
}
